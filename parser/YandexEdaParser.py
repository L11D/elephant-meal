import copy
import math
import time
from datetime import datetime
import os
import re

import pandas as pd
from selenium import webdriver
from selenium.common import TimeoutException, NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from Embedder import embedding_products
from utils.PictureParser import parse_picture

save_path = 'data//products//'
save_pictures_path = 'data//products//pictures//'

def create_options():
    options = ChromeOptions()
    # if HEADLESS:
    # options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('--disable-dev-shm-usage')
    options.add_argument('--disable-gpu')
    options.add_argument('--disable-infobars')
    options.add_argument(
        'user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36')
    options.add_argument('accept=text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8')
    options.add_argument('accept-language=ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7')
    return options


def get_product(product_name, url, category_chain):
    split_name = product_name.split(',')
    name = ''.join(split_name[0:-2])
    cost = re.findall( r'\d+', split_name[-2].replace(' ', ''))
    cost = ''.join(cost)
    cost = float(cost)

    value_raw = split_name[-1].replace(' ', '')
    value_type = None

    m = re.match(r"(\d+)(\D+)", value_raw)
    value = float(m.group(1))
    value_type_raw = m.group(2)
    match value_type_raw:
        case 'г':
            value_type = 'g'
        case 'кг':
            value_type = 'kg'
        case 'л':
            value_type = 'l'
        case 'мл':
            value_type = 'ml'
        case _:
            value_type = None

    return {
        'url': url,
        'name': name,
        'cost': cost,
        'value': value,
        'value_type': value_type,
        'category_chain': category_chain
    }


def parse_visible_products(driver, products, category_chain):
    products_elements = WebDriverWait(driver, 20).until(
        EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitDesktopProductCard_root'))
    )
    for product_element in products_elements:
        product_element_inner = product_element.find_element(By.CSS_SELECTOR, '.UiKitDesktopProductCard_fakeWrapper')
        product_name = product_element_inner.get_attribute('aria-label')
        product_link = product_element_inner.get_attribute('href')

        if any(item.get('url') == product_link for item in products):
            continue

        image_url = product_element.find_element(By.TAG_NAME, 'img').get_attribute('src')
        product = get_product(product_name, product_link, category_chain)
        parse_picture(product, image_url, save_pictures_path)
        products.append(product)


def scroll_and_parse(driver, products, category_chain):
    products_elements = WebDriverWait(driver, 20).until(
        EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitDesktopProductCard_fakeWrapper'))
    )

    total_height = driver.execute_script("return document.body.scrollHeight")
    scroll_size = driver.execute_script("return window.innerHeight")
    count_of_scrolls = math.ceil(total_height / scroll_size)
    parse_visible_products(driver, products, category_chain)

    for i in range(count_of_scrolls):
        driver.execute_script(f'window.scrollBy(0, {scroll_size});')
        time.sleep(1)
        parse_visible_products(driver, products, category_chain)


def find_sub_categories(driver):
    sub_categories = []
    try:
        category_elements = WebDriverWait(driver, 5).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitRetailDesktopCategoryHeader_title'))
        )
        for category_element in category_elements:
            try:
                category_link_tag = category_element.find_element(By.TAG_NAME, 'a')
                category_link = category_link_tag.get_attribute('href')
                category_name = category_link_tag.text
                if category_name != 'Скидки и акции':
                    sub_categories.append({'name': category_name, 'link': category_link})
            except NoSuchElementException:
                pass
    except TimeoutException:
        pass
    return sub_categories


def parse_category(driver, url, products, category_chain):
    driver.get(url)
    sub_categories = find_sub_categories(driver)

    top_category_tag = driver.find_element(By.CSS_SELECTOR, '.UiKitText_Title1Tight')
    top_category = None
    if top_category_tag:
        top_category = top_category_tag.text

    sub_categories = [value for value in sub_categories if value['name'] != top_category]

    category_chain = copy.deepcopy(category_chain)
    category_chain.append(top_category)

    for sub_category in sub_categories:
        parse_category(driver, sub_category['link'], products, category_chain)

    if len(sub_categories) == 0:
        scroll_and_parse(driver, products, category_chain)


def parse_page(driver, url, products):
    # driver.get(url)
    category_chain = []
    parse_category(driver, url, products, category_chain)
    # scroll_and_parse(driver, products)


def add_embeddings(products_df):
    products_embeddings = embedding_products(list(products_df['name']))
    products_df['embedding'] = products_embeddings
    return products_df

def parse_energy_value(driver, product):
    energy_value_elements = []
    try:
        energy_value_elements = WebDriverWait(driver, 5).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitProductCardEnergyValues_item'))
        )
    except NoSuchElementException: pass

    calories = None
    proteins = None
    carb = None
    fats = None

    for energy_value_element in energy_value_elements:
        element_name_tag = None
        element_value_tag = None
        try:
            element_name_tag = energy_value_element.find_element(By.CSS_SELECTOR, '.UiKitProductCardEnergyValues_name')
            element_value_tag = energy_value_element.find_element(By.CSS_SELECTOR, '.UiKitProductCardEnergyValues_value')
        except NoSuchElementException: pass

        element_name = None
        element_value = None
        if element_name_tag:
            element_name = element_name_tag.text
        if element_value_tag:
            element_value = element_value_tag.text

        element_value = float(element_value.split(' ')[0])

        if element_name == 'белки':
            proteins = element_value
        elif element_name == 'жиры':
            fats = element_value
        elif element_name == 'углеводы':
            carb = element_value
        elif element_name == 'ккал':
            calories = element_value

    product['calories'] = calories
    product['proteins'] = proteins
    product['fats'] = fats
    product['carb'] = carb


def parse_details_data(driver, products):
    for product in products:
        driver.get(product['url'])
        parse_composition_and_brand(driver, product)
        parse_energy_value(driver, product)

def parse_composition_and_brand(driver, product):
    description_elements = []
    try:
        description_elements = WebDriverWait(driver, 5).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitProductCardDescriptions_collapseItem'))
        )
    except TimeoutException: pass

    composition = None
    brand = None

    for description_element in description_elements:
        header_tag = description_element.find_element(By.TAG_NAME, 'h3')
        header_text = None
        if header_tag:
            header_text = header_tag.text

        if header_text == 'Состав':
            composition_tag = description_element.find_element(By.TAG_NAME, 'div')
            if composition_tag:
                composition = composition_tag.text
        elif header_text == 'Бренд':
            brand_tag = description_element.find_element(By.TAG_NAME, 'div')
            if brand_tag:
                brand = brand_tag.text

    product['composition'] = composition
    product['brand'] = brand




catalog_pages = {
    'Молоко и яйца': 'https://eda.yandex.ru/retail/lenta/catalog/36892?placeSlug=lenta_hnpmw',
    # 'Овощи и зелень': 'https://eda.yandex.ru/retail/lenta/catalog/1034?placeSlug=giper_gwhxm',
    # 'Фрукты и ягоды': 'https://eda.yandex.ru/retail/lenta/catalog/1033?placeSlug=giper_gwhxm',
    # 'Сладости': 'https://eda.yandex.ru/retail/lenta/catalog/139?placeSlug=giper_gwhxm',
    # 'Мясо и птица': 'https://eda.yandex.ru/retail/lenta/catalog/1029?placeSlug=giper_gwhxm',
    # 'Рыба и морепродукты': 'https://eda.yandex.ru/retail/lenta/catalog/178?placeSlug=giper_gwhxm',
    # 'Заморозка': 'https://eda.yandex.ru/retail/lenta/catalog/147?placeSlug=giper_gwhxm',
    # 'Вода и напитки': 'https://eda.yandex.ru/retail/lenta/catalog/12881?placeSlug=giper_gwhxm',
    # 'Колбасы и сосиски': 'https://eda.yandex.ru/retail/lenta/catalog/148?placeSlug=giper_gwhxm',
    # 'Хлеб и выпечка': 'https://eda.yandex.ru/retail/lenta/catalog/182?placeSlug=giper_gwhxm',
    # 'Сыры': 'https://eda.yandex.ru/retail/lenta/catalog/167?placeSlug=giper_gwhxm',
    # 'Макароны и крупы': 'https://eda.yandex.ru/retail/lenta/catalog/19288?placeSlug=giper_gwhxm',
    # 'Кофе и чай': 'https://eda.yandex.ru/retail/lenta/catalog/19289?placeSlug=giper_gwhxm',
    # 'Все для выпечки и десертов': 'https://eda.yandex.ru/retail/lenta/catalog/38776?placeSlug=giper_gwhxm',
    # 'Масло, соусы и специи': 'https://eda.yandex.ru/retail/lenta/catalog/19283?placeSlug=giper_gwhxm',
    # 'Консервы и соления': 'https://eda.yandex.ru/retail/lenta/catalog/19291?placeSlug=giper_gwhxm',
    # 'Орехи, снеки и чипсы': 'https://eda.yandex.ru/retail/lenta/catalog/30191?placeSlug=giper_gwhxm',
}



current_datetime = datetime.today().strftime('%Y_%m_%d')
os.makedirs(os.path.dirname(save_path), exist_ok=True)
os.makedirs(os.path.dirname(save_pictures_path), exist_ok=True)

driver_options = create_options()
driver = webdriver.Chrome(options=driver_options)
driver.maximize_window()
products = []
try:
    for key, value in catalog_pages.items():
        parse_page(driver, value, products)
    parse_details_data(driver, products)
finally:
    driver.quit()



products_data = pd.DataFrame(products)
# products_data = add_embeddings(products_data)

products_data.to_csv(f'{save_path}yeda_lenta_products{current_datetime}.csv', index=False)

import math
import time
from datetime import datetime
import os

import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


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


def get_product(product_name, url):
    # Молоко сгущенное ГЛАВПРОДУКТ Экстра цельное с сахаром, без змж, ГОСТ, 129RUB, 270г

    split_name = product_name.split(',')
    name = ''.join(split_name[0:-2])
    price = split_name[-2].replace(' ', '')
    value = split_name[-1].replace(' ', '')
    return {
        'url': url,
        'name': name,
        'price': price,
        'value': value
    }

def parse_visible_products(driver, products):
    products_elements = WebDriverWait(driver, 20).until(
        EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitDesktopProductCard_fakeWrapper'))
    )
    for product_element in products_elements:
        product_name = product_element.get_attribute('aria-label')
        product_link = product_element.get_attribute('href')
        if any(item.get('url') == product_link for item in products):
            continue
        products.append(get_product(product_name, product_link))

def scroll_and_parse(driver, products):
    products_elements = WebDriverWait(driver, 20).until(
        EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitDesktopProductCard_fakeWrapper'))
    )

    total_height = driver.execute_script("return document.body.scrollHeight")
    scroll_size = driver.execute_script("return window.innerHeight")
    count_of_scrolls = math.ceil(total_height / scroll_size)
    parse_visible_products(driver, products)

    for i in range(count_of_scrolls):
        driver.execute_script(f'window.scrollBy(0, {scroll_size});')
        time.sleep(1)  # Ожидание загрузки контента
        parse_visible_products(driver, products)


def parse_page(driver, url, products):
    driver.get(url)
    scroll_and_parse(driver, products)

# def parse_page(driver, url, products):
#     driver.get(url)
#     products_elements = WebDriverWait(driver, 20).until(
#         EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitDesktopProductCard_fakeWrapper'))
#     )
#     for product_element in products_elements:
#         product_name = product_element.get_attribute('aria-label')
#         product_link = product_element.get_attribute('href')
#         if product_link is None or product_name is None:
#             print('Product is none')
#         if any(item.get('url') == product_link for item in products):
#             continue
#         products.append(get_product(product_name, product_link))
#
#         # print(get_product(product_name, product_link))
#         # print(f"Название: {product_name}, Ссылка: {product_link}")


catalog_pages = {
    'Молоко и яйца': 'https://eda.yandex.ru/retail/lenta/catalog/19280?placeSlug=giper_gwhxm',
    'Овощи и зелень': 'https://eda.yandex.ru/retail/lenta/catalog/1034?placeSlug=giper_gwhxm',
    'Фрукты и ягоды': 'https://eda.yandex.ru/retail/lenta/catalog/1033?placeSlug=giper_gwhxm',
    'Сладости': 'https://eda.yandex.ru/retail/lenta/catalog/139?placeSlug=giper_gwhxm',
    'Мясо и птица': 'https://eda.yandex.ru/retail/lenta/catalog/1029?placeSlug=giper_gwhxm',
    'Рыба и морепродукты': 'https://eda.yandex.ru/retail/lenta/catalog/178?placeSlug=giper_gwhxm',
    'Заморозка': 'https://eda.yandex.ru/retail/lenta/catalog/147?placeSlug=giper_gwhxm',
    'Вода и напитки': 'https://eda.yandex.ru/retail/lenta/catalog/12881?placeSlug=giper_gwhxm',
    'Колбасы и сосиски': 'https://eda.yandex.ru/retail/lenta/catalog/148?placeSlug=giper_gwhxm',
    'Хлеб и выпечка': 'https://eda.yandex.ru/retail/lenta/catalog/182?placeSlug=giper_gwhxm',
    'Сыры': 'https://eda.yandex.ru/retail/lenta/catalog/167?placeSlug=giper_gwhxm',
    'Макароны и крупы': 'https://eda.yandex.ru/retail/lenta/catalog/19288?placeSlug=giper_gwhxm',
    'Кофе и чай': 'https://eda.yandex.ru/retail/lenta/catalog/19289?placeSlug=giper_gwhxm',
    'Все для выпечки и десертов': 'https://eda.yandex.ru/retail/lenta/catalog/38776?placeSlug=giper_gwhxm',
    'Масло, соусы и специи': 'https://eda.yandex.ru/retail/lenta/catalog/19283?placeSlug=giper_gwhxm',
    'Консервы и соления': 'https://eda.yandex.ru/retail/lenta/catalog/19291?placeSlug=giper_gwhxm',
    'Орехи, снеки и чипсы': 'https://eda.yandex.ru/retail/lenta/catalog/30191?placeSlug=giper_gwhxm',
}

driver_options = create_options()
driver = webdriver.Chrome(options=driver_options)
driver.maximize_window()
products = []
try:
    for key, value in catalog_pages.items():
        parse_page(driver, value, products)
finally:
    driver.quit()

save_path = 'data//products//'
current_datetime = datetime.today().strftime('%Y_%m_%d')
os.makedirs(os.path.dirname(save_path), exist_ok=True)
products_data = pd.DataFrame(products)
products_data.to_csv(f'{save_path}yeda_lenta_products{current_datetime}.csv', index=False)
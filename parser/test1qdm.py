from concurrent.futures import ThreadPoolExecutor, as_completed
from selenium import webdriver
from queue import Queue
import pandas as pd

from selenium.common import TimeoutException, NoSuchElementException
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from tqdm import tqdm


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

def create_driver():
    driver_options = create_options()
    driver = webdriver.Chrome(options=driver_options)
    driver.maximize_window()
    return driver

def parse_energy_value(driver, product):
    energy_value_elements = []
    try:
        energy_value_elements = WebDriverWait(driver, 5).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitProductCardEnergyValues_item'))
        )
    except TimeoutException:
        pass

    calories = None
    proteins = None
    carb = None
    fats = None

    for energy_value_element in energy_value_elements:
        element_name_tag = None
        element_value_tag = None
        try:
            element_name_tag = energy_value_element.find_element(By.CSS_SELECTOR, '.UiKitProductCardEnergyValues_name')
            element_value_tag = energy_value_element.find_element(By.CSS_SELECTOR,
                                                                  '.UiKitProductCardEnergyValues_value')
        except NoSuchElementException:
            pass

        element_name = None
        element_value = None
        if element_name_tag:
            element_name = element_name_tag.text
        if element_value_tag:
            element_value = element_value_tag.text
        try:
            element_value = float(element_value.split(' ')[0])
        except ValueError:
            pass

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



def parse_composition_and_brand(driver, product):
    description_elements = []
    try:
        description_elements = WebDriverWait(driver, 5).until(
            EC.presence_of_all_elements_located((By.CSS_SELECTOR, '.UiKitProductCardDescriptions_collapseItem'))
        )
    except TimeoutException:
        pass

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


def parse_details_data(driver, url):
    product = {
        'url': url
    }
    driver.get(url)
    parse_composition_and_brand(driver, product)
    parse_energy_value(driver, product)
    return product

def worker(url_queue, result_queue, driver_queue, progress_bar):
    while not url_queue.empty():
        url = url_queue.get()
        driver = driver_queue.get()
        try:
            result = parse_details_data(driver, url)
            result_queue.put(result)
        except Exception as exc:
            print(f'URL {url} generated an exception: {exc}')
            result_queue.put(None)
        finally:
            driver_queue.put(driver)
            progress_bar.update(1)
        url_queue.task_done()


def main(urls):
    num_drivers = 6
    url_queue = Queue()
    result_queue = Queue()
    driver_queue = Queue()

    for url in urls:
        url_queue.put(url)

    drivers = [create_driver() for _ in range(num_drivers)]
    for driver in drivers:
        driver_queue.put(driver)

    # Создайте объект tqdm
    with tqdm(total=len(urls), desc='Processing') as progress_bar:
        with ThreadPoolExecutor(max_workers=num_drivers) as executor:
            for _ in range(num_drivers):
                executor.submit(worker, url_queue, result_queue, driver_queue, progress_bar)

        url_queue.join()

    results = []
    while not result_queue.empty():
        results.append(result_queue.get())

    for driver in drivers:
        driver.quit()

    return results

dataset_path = 'data/products/yeda_lenta_products2024_06_26.csv'

products_df = pd.read_csv(dataset_path)
urls = products_df['url'].tolist()

results = main(urls)

results = [result for result in results if result is not None]

results_df = pd.DataFrame(results)
merged_df = products_df.merge(results_df, on='url', how='inner')

merged_df.to_csv(f'data/products/yeda_lenta_products2024_06_26_parsed.csv', index=False)


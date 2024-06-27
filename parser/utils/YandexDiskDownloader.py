import requests
from urllib.parse import urlencode
from tqdm import tqdm


def download_file_from_yandex_disk(public_url, save_path):
    print('Downloading dataset from Yandex Disk...')
    base_url = 'https://cloud-api.yandex.net/v1/disk/public/resources/download?'
    final_url = base_url + urlencode(dict(public_key=public_url))

    response = requests.get(final_url)
    response.raise_for_status()
    download_url = response.json()['href']

    download_response = requests.get(download_url, stream=True)
    download_response.raise_for_status()

    total_size = int(download_response.headers.get('content-length', 0))

    with open(save_path, 'wb') as file, tqdm(
            desc=save_path,
            total=total_size,
            unit='iB',
            unit_scale=True,
            unit_divisor=1024,
    ) as bar:
        for data in download_response.iter_content(chunk_size=1024):
            file.write(data)
            bar.update(len(data))

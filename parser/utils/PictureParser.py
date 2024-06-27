import os
import requests
import uuid


def parse_picture(product, url, path):
    image_uuid = uuid.uuid5(uuid.NAMESPACE_DNS, product['name'])
    image_path = f'{path}{image_uuid}.png'
    success = True

    try:
        response = requests.get(url)
        if response.status_code == 200:
            with open(image_path, 'wb') as file:
                file.write(response.content)
        else:
            print(response.status_code)
        if not os.path.exists(image_path):
            print('os.path.exists(image_path)')
            success = False
    except Exception as e:
        print('Exception occurred')
        success = False

    if success:
        product['image_id'] = image_uuid
    else:
        product['image_id'] = None

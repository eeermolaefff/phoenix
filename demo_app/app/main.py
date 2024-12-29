import datetime
import json
import os
from time import sleep

BASE_DIR = os.path.dirname(os.path.abspath(__file__)) + "/logs"

while True:
    log = {
        'load_avg': 123,
        'ram_usage_percent': 456,
        'asctime': datetime.datetime.utcnow().isoformat(timespec='milliseconds', sep=' ')
    }

    print(BASE_DIR, log)

    with open(os.path.join(BASE_DIR, 'app.log'), 'a') as f:
        f.write(json.dumps(log) + '\n')

    sleep(7)
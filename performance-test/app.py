import os

import locust
from locust import HttpUser, task, between
# from locust import
import uuid
import json

# with open("custom_config.conf", "r") as f:
#     config = json.load(f)
virtual_threads_base_url = "http://localhost:8099/virtualThreads"
regular_threads_base_url = "http://localhost:8098/regularThreads"


class UserBehavior(locust.TaskSet):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.client_virtual = self.client
        self.client_virtual.base_url = "http://localhost:8099/virtualThreads"

        self.client_regular = self.client
        self.client_regular.base_url = "http://localhost:8098/regularThreads"


    #
    # def __init__(self, *args, **kwargs):
    #     super().__init__(*args, **kwargs)
    #     # self.virtual_threads_base_url = self.client.get_client(base_url=get_config["virtual_threads_base_url"])
    #     # self.regular_threads_base_url = self.client.get_client(base_url=get_config["regular_threads_base_url"])
    #     self.virtual_threads_base_url = self.client.get_client(base_url="http://localhost:8099/virtualThreads")
    #     self.regular_threads_base_url = self.client.get_client(base_url="http://localhost:8098/regularThreads")

    # @task
    # def my_task(self):
    #     sample_request = {"responseField1": "sample value", "responseField2": 1}
    #     headers = {'content-type': 'application/json'}
    #     response = self.client.post("/api/provider/generateData", json=sample_request, headers=headers)
    #     print(response.status_code)
    #     print(response.text)

    @task
    def virtual_threads(self):
        response = self.client_virtual.post(f"{virtual_threads_base_url}/request/demo-example-1-{uuid.uuid4()}")
        print(response.status_code)
        print(response.text)

    @task
    def regular_threads(self):
        response = self.client_regular.post(f"{regular_threads_base_url}/request/demo-example-1-{uuid.uuid4()}")
        print(response.status_code)
        print(response.text)


class WebsiteUser(HttpUser):
    tasks = [UserBehavior]
    #wait_time = between(5, 15)

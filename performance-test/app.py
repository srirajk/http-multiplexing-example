import os

import locust
from locust import HttpUser, task, between, tag
# from locust import
import uuid
import json




class UserBehavior(locust.TaskSet):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.client_virtual = self.client
        self.client_virtual.base_url = os.getenv("VIRTUAL_THREADS_BASE_URL", "http://localhost:8099/virtualThreads")
        self.client_regular = self.client
        self.client_regular.base_url = os.getenv("REGULAR_THREADS_BASE_URL", "http://localhost:8098/regularThreads")
        self.reactive = self.client
        self.reactive.base_url = os.getenv("REACTIVE_THREADS_BASE_URL", "http://localhost:8080/reactive")


    @task
    @tag("reactive")
    def reactive_threads(self):
        response = self.reactive.post(f"/request/demo-example-1-{uuid.uuid4()}", name="reactive_threads")
        status_code = response.status_code
        if status_code == 200:
            data = response.json()
            is_error = data["error"]
            if is_error:
                response.failure(f"Some Error Occurred :: {data}")
        else:
            response.failure(f"Some Error Occurred && http status code  :: {status_code}")

    @task
    @tag("virtual")
    def virtual_threads(self):
        response = self.client_virtual.post(f"/request/demo-example-1-{uuid.uuid4()}", name="virtual_threads")
        status_code = response.status_code
        if status_code == 200:
            data = response.json()
            is_error = data["error"]
            if is_error:
                response.failure(f"Some Error Occurred :: {data}")
        else:
            response.failure(f"Some Error Occurred && http status code  :: {status_code}")

    @task
    @tag("regular")
    def regular_threads(self):
        response = self.client_regular.post(f"/request/demo-example-1-{uuid.uuid4()}", name="regular_threads")
        status_code = response.status_code
        if status_code == 200:
            data = response.json()
            is_error = data["error"]
            if is_error:
                response.failure(f"Some Error Occurred :: {data}")
        else:
            response.failure(f"Some Error Occurred && http status code  :: {status_code}")


class WebsiteUser(HttpUser):
    tasks = [UserBehavior]
    #wait_time = between(5, 15)

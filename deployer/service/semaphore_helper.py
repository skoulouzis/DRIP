import polemarch_client
from polemarch_client import Project
from semaphore_client import Configuration, ApiClient, api, ProjectRequest, Login
from polemarch_client.api import project_api


class SemaphoreHelper:

    def __init__(self, polemarch_base_url,username,password):
        self.init_semaphore_client(polemarch_base_url, username, password)


    def init_semaphore_client(self, polemarch_base_url, username, password):
        configuration = Configuration()
        configuration.host = polemarch_base_url
        configuration.username = username
        configuration.password = password
        api_client = ApiClient(configuration=configuration)
        self.authentication_api = api.AuthenticationApi(api_client=api_client)
        login_body = Login(auth=username, password=password)
        self.authentication_api.auth_login_post(login_body)
        self.authentication_api.user_tokens_get()

        self.default_api = api.DefaultApi(api_client=api_client)
        self.project_api = api.ProjectApi(api_client=api_client)
        self.user_api = api.UserApi (api_client=api_client)
        self.projects_api = api.ProjectsApi(api_client=api_client)


    def create_project(self,name):
        project_request = ProjectRequest(name=name)
        res = self.projects_api.projects_post(project_request)
        print(res)

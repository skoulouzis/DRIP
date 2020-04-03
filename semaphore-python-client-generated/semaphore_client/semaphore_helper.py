import os
import sys
import urllib3

from semaphore_client import Configuration, ApiClient, api, ProjectRequest, Login, Repository, Inventory, \
    AccessKeyRequest, InventoryRequest, RepositoryRequest, TemplateRequest, Task


class SemaphoreHelper:

    def __init__(self, semaphore_base_url, username, password):
        self.init_semaphore_client(semaphore_base_url, username, password)


    def init_semaphore_client(self, polemarch_base_url, username, password):
        configuration = Configuration()
        configuration.host = polemarch_base_url
        configuration.username = username
        configuration.password = password
        api_client = ApiClient(configuration=configuration)
        if not api_client.cookie:
            self.authentication_api = api.AuthenticationApi(api_client=api_client)
            login_body = Login(auth=username, password=password)
            self.authentication_api.auth_login_post(login_body)
            tokens = self.authentication_api.user_tokens_get()

        self.default_api = api.DefaultApi(api_client=api_client)
        self.project_api = api.ProjectApi(api_client=api_client)
        self.user_api = api.UserApi (api_client=api_client)
        self.projects_api = api.ProjectsApi(api_client=api_client)


    def find_projects_by_name(self, name):
        projects = self.projects_api.projects_get()
        projects_with_name = []
        for project in projects:
            if project.name == name:
                projects_with_name.append(project)
        return projects_with_name

    def project_with_name_exists(self, name):
        projects = self.find_projects_by_name(name)
        if not projects:
            return False
        return True

    def create_project(self, name):
        project_request = ProjectRequest(name=name)
        self.projects_api.projects_post(project_request)
        projects = self.find_projects_by_name(name)
        return projects[len(projects)-1].id

    def create_ssh_key(self, name, project_id, private_key):
        key_request = AccessKeyRequest(name=name, type='ssh', project_id=project_id, secret=private_key)
        self.project_api.project_project_id_keys_post(project_id, key_request)
        keys = self.project_api.project_project_id_keys_get(project_id, name, 'asc', key_type='ssh')
        return keys[len(keys) - 1].id

    def create_inventory(self, name, project_id,ssh_key_id, inventory_contents):
        inventory_request = InventoryRequest( name=name, project_id=project_id, inventory=inventory_contents,
                                              ssh_key_id=ssh_key_id, type='static')
        self.project_api.project_project_id_inventory_post(project_id,inventory_request)

        inventories = self.project_api.project_project_id_inventory_get(project_id, name, 'asc')
        return inventories[len(inventories) - 1].id

    def create_repository(self, name, project_id, key_id, git_url):
        repository_request = RepositoryRequest(name=name, project_id=project_id, git_url=git_url, ssh_key_id=key_id)
        self.project_api.project_project_id_repositories_post(project_id, repository=repository_request)

        repositories = self.project_api.project_project_id_repositories_get(project_id, name, 'asc')
        return repositories[len(repositories) - 1].id

    def create_template(self, project_id,key_id,inventory_id,repository_id,playbook_name):
        template_request = TemplateRequest(ssh_key_id=key_id, project_id=project_id, inventory_id=inventory_id,
                                           repository_id=repository_id, alias=playbook_name, playbook=playbook_name)
        self.project_api.project_project_id_templates_post(project_id, template_request)
        templates = self.project_api.project_project_id_templates_get(project_id, playbook_name, 'asc')
        return templates[len(templates) - 1].id

    def execute_task(self, project_id, template_id, playbook_name):
        task = Task(template_id=template_id, playbook=playbook_name)
        self.project_api.project_project_id_tasks_post(project_id, task)
        tasks = self.project_api.project_project_id_tasks_get(project_id)
        return tasks[len(tasks) - 1].id

    def get_task(self,project_id,task_id):
        return self.project_api.project_project_id_tasks_task_id_get(project_id,task_id)


    @classmethod
    def service_is_up(cls, url):
        code = None
        try:
            http = urllib3.PoolManager()
            r = http.request('HEAD', url)
        except Exception as e:
            return False

        return True
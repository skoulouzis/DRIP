import polemarch_client
from polemarch_client import Configuration, ApiClient, api, ProjectCreateMaster, Empty, OneInventory, User, OneProject
from polemarch_client.api import project_api


class PolemarchHelper:

    def __init__(self, polemarch_base_url,username,password):
        self.init_polemarch_client(polemarch_base_url,username,password)


    def init_polemarch_client(self,polemarch_base_url,username,password):
        configuration = Configuration()
        configuration.host = polemarch_base_url
        configuration.username = username
        configuration.password = password
        api_client = ApiClient(configuration=configuration)

        self.community_api = api.CommunityTemplateApi(api_client=api_client)
        self.group_api = api.GroupApi(api_client=api_client)
        self.history_api = api.HistoryApi(api_client=api_client)
        self.hook_api = api.HookApi(api_client=api_client)
        self.host_api = api.HostApi(api_client=api_client)
        self.inventory_api = api.InventoryApi(api_client=api_client)
        self.project_api = api.ProjectApi(api_client=api_client)
        self.team_api = api.TeamApi(api_client=api_client)
        self.user_api = api.UserApi(api_client=api_client)
        users = self.user_api.user_list(username=username)
        self.user = users.results[0]



    def get_project(self,project_id):
        project = self.project_api.project_get(project_id)
        return project


    def create_project(self,name,type,repository):
        project_create_master = ProjectCreateMaster(name=name,type=type, repository=repository)
        project_create_master =  self.project_api.project_add(project_create_master)
        empty = Empty()
        self.project_api.project_sync(project_create_master.id,empty)

        # pr = OneProject(name=name,type=type, repository=repository)
        # pr.owner = self.user
        # view_data = {}
        # playbook = {}
        # playbook['help'] = 'some help'
        # playbook['title'] = 'title'
        # playbooks = {}
        # playbooks['main.yml'] = playbook
        # view_data['fields'] = playbooks
        # pr.execute_view_data = view_data
        # return project_create_master

    def add_inventory(self,project_id,name,vms):
        one_inventory = OneInventory()
        one_inventory.name = name
        one_inventory.owner = self.user
        one_inventory =  self.project_api.project_inventory_add(project_id,one_inventory)
        return one_inventory

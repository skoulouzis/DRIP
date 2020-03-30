import logging

from service.polemarch_helper import PolemarchHelper
from service.semaphore_helper import SemaphoreHelper

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


class AnsibleService:

    def __init__(self, polemarch_base_url=None,polemarch_username=None,polemarch_password=None,
                 semaphore_base_url=None,semaphore_username=None,semaphore_password=None):
        self.polemarch_base_url = polemarch_base_url
        self.polemarch_username  = polemarch_username
        self.polemarch_password = polemarch_password
        self.semaphore_base_url = semaphore_base_url
        self.semaphore_username = semaphore_username
        self.semaphore_password = semaphore_password



    def execute(self,nodes_pair):
        vms = nodes_pair[0]
        orchestartor = nodes_pair[1]

        # polemarch_helper = PolemarchHelper(self.polemarch_base_url,self.polemarch_username,self.polemarch_password)
        # project = polemarch_helper.create_project('test1','GIT','https://github.com/skoulouzis/playbooks.git')
        # inventory = polemarch_helper.add_inventory(project.id,'test_inventory',vms)
        semaphore_helper = SemaphoreHelper(self.semaphore_base_url,self.semaphore_username,self.semaphore_password)
        semaphore_helper.create_project('test1')
        pass
#! /usr/bin/env python

# Copyright 2017 --Yang Hu--
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


__author__ = 'Yang Hu'

import paramiko, os
from vm_info import VmInfo
import logging
from drip_logging.drip_logging_handler import *
import multiprocessing

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


retry=0

def scale_service(vm, application_name, service_name, service_num):
    try:
        logger.info("Starting docker service scaling on: "+vm.ip)
        paramiko.util.log_to_file("deployment.log")
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
        stdin, stdout, stderr = ssh.exec_command("sudo docker service scale %s_%s=%s" % (application_name, service_name, service_num))
        stdout.read()
        logger.info("Finished docker service scaling on: "+vm.ip)        
    except Exception as e:
        global retry        
        if retry < 10:
            logger.warning(vm.ip + " " + str(e)+". Retrying")
            retry+=1
            return scale_service(vm, application_name, service_name, service_num)     
        logger.error(vm.ip + " " + str(e))
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    return "SUCCESS"




def run(vm_list, application_name, service_name, service_num,rabbitmq_host,owner):
    rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672,user=owner)
    logger.addHandler(rabbit)    
    for i in vm_list:
        if i.role == "master":
            ret = scale_service(i, application_name, service_name, service_num)
            if "ERROR" in ret:
                return ret
            break


    return ret
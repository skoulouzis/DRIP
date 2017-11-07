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
import threading
import logging
from drip_logging.drip_logging_handler import *

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True
        


def install_engine(vm):
	try:
		logger.info("Starting docker engine installation on: "+(vm.ip))
		paramiko.util.log_to_file("deployment.log")
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
		stdin, stdout, stderr = ssh.exec_command("sudo dpkg --get-selections | grep docker")
		temp_list = stdout.readlines()
		temp_str = ""
		for i in temp_list: temp_str += i
		if temp_str.find("docker") != -1: return "SUCCESS"
		sftp = ssh.open_sftp()
		sftp.chdir('/tmp/')
		file_path = os.path.dirname(os.path.abspath(__file__))
		install_script = file_path + "/" + "docker_engine.sh"
		sftp.put(install_script, "engine_setup.sh")
		stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/engine_setup.sh")
		stdout.read()
		logger.info("Finised docker engine installation on: "+(vm.ip))
	except Exception as e:
		logger.error(vm.ip + " " + str(e))
		return "ERROR:"+vm.ip+" "+str(e)
	ssh.close()
	return "SUCCESS"

def run(vm_list,rabbitmq_host,owner):
    rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672,user=owner)
    logger.addHandler(rabbit)
    for i in vm_list:
            ret = install_engine(i)
    if "ERROR" in ret: return ret
    return "SUCCESS"
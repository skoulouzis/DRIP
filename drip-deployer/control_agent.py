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
import logging
# from drip_logging.drip_logging_handler import *


logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True


retry=0

def install_agent(vm, vm_list):
    try:
        logger.info("Starting control agent installation on: "+(vm.ip))
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key,timeout=30)
        sftp = ssh.open_sftp()
        sftp.chdir('/tmp/')
        vm_cnt = 0
        file_path = os.path.dirname(os.path.abspath(__file__))
        fo = open(file_path + "/cluster_file", "w")
        for i in vm_list:
            vm_cnt += 1
            sftp.put(i.key, "%d" % (vm_cnt))
            fo.write("%s %s /tmp/%d %s\n" % (i.ip, i.user, vm_cnt, i.role))
        fo.close()
        sftp.put(file_path + "/cluster_file", "cluster_file")
        sftp.put(file_path + "/control_agent.sh", "control_agent.sh")
        stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/control_agent.sh")
        stdout.read()
        stdin, stdout, stderr = ssh.exec_command("nohup sudo python /root/Swarm-Agent/run.py>/dev/null 2>&1 &")
        stdout.read()
        logger.info("Finished control agent installation on: "+(vm.ip))
    except Exception as e:
        global retry
        if retry < 10:
            logger.warning(vm.ip + " " + str(e)+". Retrying")
            retry+=1
            return install_agent(vm, vm_list)            
        logger.error(vm.ip + " " + str(e))        
        print '%s: %s' % (vm.ip, e)
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    return "SUCCESS"


def run(vm_list):
    for i in vm_list:
        parentDir = os.path.dirname(os.path.abspath(i.key))
        os.chmod(parentDir, 0o700)
        os.chmod(i.key, 0o600)
        if i.role == "master":
            ret = install_agent(i, vm_list)
            if "ERROR" in ret: return ret
    return "SUCCESS"

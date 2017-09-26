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
def install_manager(vm):
	try:
		print "%s: ====== Start Swarm Manager Installing ======" % (vm.ip)
		paramiko.util.log_to_file("deployment.log")
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
		stdin, stdout, stderr = ssh.exec_command("sudo docker info | grep 'Swarm'")
		temp_list1 = stdout.readlines()
		stdin, stdout, stderr = ssh.exec_command("sudo docker info | grep 'Is Manager'")
		temp_list2 = stdout.readlines()
		if temp_list1[0].find("Swarm: active") != -1:
			if temp_list2[0].find("Is Manager: true") != -1:
				stdin, stdout, stderr = ssh.exec_command("sudo docker swarm join-token worker")
				retstr = stdout.readlines()
				return retstr[2].encode()
		stdin, stdout, stderr = ssh.exec_command("sudo docker swarm leave --force")
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo docker swarm init --advertise-addr %s" % (vm.ip))
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo docker swarm join-token worker")
		retstr = stdout.readlines()
		ret = retstr[2].encode()
		print "%s: ========= Swarm Manager Installed =========" % (vm.ip)
	except Exception as e:
		print '%s: %s' % (vm.ip, e)
		return "ERROR:" + vm.ip + " " + str(e)
	ssh.close()
	return ret

def install_worker(join_cmd, vm):
	try:
		print "%s: ====== Start Swarm Worker Installing ======" % (vm.ip)
		paramiko.util.log_to_file("deployment.log")
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
		stdin, stdout, stderr = ssh.exec_command("sudo docker info | grep 'Swarm'")
		temp_list1 = stdout.readlines()
		if temp_list1[0].find("Swarm: active") != -1: return "SUCCESS"
		stdin, stdout, stderr = ssh.exec_command("sudo docker swarm leave --force")
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo %s" % (join_cmd))
		stdout.read()
		print "%s: ========= Swarm Worker Installed =========" % (vm.ip)
	except Exception as e:
		print '%s: %s' % (vm.ip, e)
		return "ERROR:" + vm.ip + " " + str(e)
	ssh.close()
	return "SUCCESS"

def run(vm_list):
	for i in vm_list:
		if i.role == "master":
			join_cmd = install_manager(i)
			if "ERROR:" in join_cmd:
				return join_cmd
			parentDir = os.path.dirname(os.path.abspath(i.key))
			os.chmod(parentDir, 0o700)
			os.chmod(i.key, 0o600)
			swarm_file = open(i.key)
			swarm_string = swarm_file.read()
			swarm_file.close()
			break

	for i in vm_list:
		if i.role == "slave":
			worker_cmd = install_worker(join_cmd, i)
			if "ERROR:" in worker_cmd:
				return worker_cmd

	return swarm_string
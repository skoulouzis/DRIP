 #! /usr/bin/env python

 # Copyright 2017 S. Koulouzis
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
 

__author__ = 'S. Koulouzis'

import paramiko, os
import threading

def install_prerequisites(vm):
	try:
		print "Installing ansible prerequisites in: %s" % (vm.ip)
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
		sftp = ssh.open_sftp()
		sftp.chdir('/tmp/')
		stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/ansible_setup.sh")
		stdout.read()
		print "Ansible prerequisites installed in: %s " % (vm.ip)
	except Exception as e:
		print '%s: %s' % (vm.ip, e)
		return "ERROR:"+vm.ip+" "+str(e)
	ssh.close()
	return "SUCCESS"

def run(vm_list):
	for i in vm_list:
		ret = install_prerequisites(i)
        if "ERROR" in ret: return ret
	return "SUCCESS"
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
import linecache
import sys

def PrintException():
    exc_type, exc_obj, tb = sys.exc_info()
    f = tb.tb_frame
    lineno = tb.tb_lineno
    filename = f.f_code.co_filename
    linecache.checkcache(filename)
    line = linecache.getline(filename, lineno, f.f_globals)
    print 'EXCEPTION IN ({}, LINE {} "{}"): {}'.format(filename, lineno, line.strip(), exc_obj)
    
    

def install_manager(vm):
	try:
		print "%s: ====== Start Kubernetes Master Installing ======" % (vm.ip)
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
		sftp = ssh.open_sftp()
		file_path = os.path.dirname(os.path.abspath(__file__))
		sftp.chdir('/tmp/')
		install_script = file_path + "/" + "docker_kubernetes.sh"
		sftp.put(install_script, "kubernetes_setup.sh")
		
		stdin, stdout, stderr = ssh.exec_command("sudo hostname ip-%s" % (vm.ip.replace('.','-')))
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/kubernetes_setup.sh")
		stdout.read()
				
		stdin, stdout, stderr = ssh.exec_command("sudo kubeadm init --api-advertise-addresses=%s" % (vm.ip))
		retstr = stdout.readlines()
				
		stdin, stdout, stderr = ssh.exec_command("sudo cp /etc/kubernetes/admin.conf /tmp/")
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo chown %s /tmp/admin.conf" % (vm.user))
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo chgrp %s /tmp/admin.conf" % (vm.user))
		stdout.read()
		sftp.get("/tmp/admin.conf", file_path+"/admin.conf")
		print "%s: ========= Kubernetes Master Installed =========" % (vm.ip)
	except Exception as e:
		print '%s: %s' % (vm.ip, e)
		PrintException()
		return "ERROR:"+vm.ip+" "+str(e)
	ssh.close()
	return retstr[-1]

def install_worker(join_cmd, vm):
	try:
		print "%s: ====== Start Kubernetes Slave Installing ======" % (vm.ip)
		ssh = paramiko.SSHClient()
		ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
		ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
                
                parentDir = os.path.dirname(os.path.abspath(vm.key))
                os.chmod(parentDir, 0o700)
                os.chmod(vm.key, 0o600)
		
		sftp = ssh.open_sftp()
		sftp.chdir('/tmp/')
		file_path = os.path.dirname(os.path.abspath(__file__))
		install_script = file_path + "/" + "docker_kubernetes.sh"
		sftp.put(install_script, "kubernetes_setup.sh")
		stdin, stdout, stderr = ssh.exec_command("sudo hostname ip-%s" % (vm.ip.replace('.','-')))
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/kubernetes_setup.sh")
		stdout.read()
		stdin, stdout, stderr = ssh.exec_command("sudo %s" % (join_cmd))
		stdout.read()
		print "%s: ========= Kubernetes Slave Installed =========" % (vm.ip)
	except Exception as e:
		print '%s: %s' % (vm.ip, e)
		return "ERROR:"+vm.ip+" "+str(e)
	ssh.close()
	return "SUCCESS"

def run(vm_list):
	for i in vm_list:
		if i.role == "master": 
			join_cmd = install_manager(i)
			if "ERROR" in join_cmd:
				return join_cmd
			else:
				join_cmd = join_cmd.encode()
				join_cmd = join_cmd.strip()
			break

	for i in vm_list:
		if i.role == "slave": 
			worker_cmd = install_worker(join_cmd, i)
			if "ERROR" in worker_cmd:
				return worker_cmd

	kuber_file = open(file_path + "/admin.conf", "r")
	kuber_string = kuber_file.read()
	kuber_file.close()
	return kuber_string
# ! /usr/bin/env python

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
import logging
import time

# from drip_logging.drip_logging_handler import *
from os import listdir
from os.path import isfile, join

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True

retry = 0


def print_exception():
    exc_type, exc_obj, tb = sys.exc_info()
    f = tb.tb_frame
    lineno = tb.tb_lineno
    filename = f.f_code.co_filename
    linecache.checkcache(filename)
    line = linecache.getline(filename, lineno, f.f_globals)
    print 'EXCEPTION IN ({}, LINE {} "{}"): {}'.format(filename, lineno, line.strip(), exc_obj)


def install_manager(vm):
    try:
        logger.info("Starting kubernetes master installation on: " + (vm.ip))
        parentDir = os.path.dirname(os.path.abspath(vm.key))
        os.chmod(parentDir, 0o700)
        os.chmod(vm.key, 0o600)

        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
        sftp = ssh.open_sftp()
        file_path = os.path.dirname(os.path.abspath(__file__))
        sftp.chdir('/tmp/')
        install_script = file_path + "/" + "docker_kubernetes.sh"
        sftp.put(install_script, "kubernetes_setup.sh")
        # stdin, stdout, stderr = ssh.exec_command("sudo hostname ip-%s" % (vm.ip.replace('.','-')))
        # stdout.read()
        stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/kubernetes_setup.sh > log 2>&1")
        out = stdout.read()
        out = stderr.read()
        # stdin, stdout, stderr = ssh.exec_command("sudo kubeadm kubernetes-xenialreset --force")
        # stdout.read()
        stdin, stdout, stderr = ssh.exec_command("sudo kubeadm reset --force >> log 2>&1")
        stdout.read()

        # stdin, stdout, stderr = ssh.exec_command("sudo kubeadm init --apiserver-advertise-address=%s" % (vm.ip))
        stdin, stdout, stderr = ssh.exec_command("sudo kubeadm init")
        retstr = stdout.readlines()

        stdin, stdout, stderr = ssh.exec_command("mkdir -p $HOME/.kube")
        stdout.read()

        stdin, stdout, stderr = ssh.exec_command("sudo cp /etc/kubernetes/admin.conf $HOME/.kube/config")
        stdout.read()

        stdin, stdout, stderr = ssh.exec_command("sudo chown $(id -u):$(id -g) $HOME/.kube/config")
        stdout.read()

        stdin, stdout, stderr = ssh.exec_command("sudo sysctl net.bridge.bridge-nf-call-iptables=1")
        retstr = stdout.readlines()

        stdin, stdout, stderr = ssh.exec_command(
            "kubectl apply -f \"https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')\"")
        retstr = stdout.readlines()

        stdin, stdout, stderr = ssh.exec_command(
            "kubectl taint nodes --all node-role.kubernetes.io/master-")
        retstr = stdout.readlines()

        stdin, stdout, stderr = ssh.exec_command("sudo chown %s /tmp/admin.conf" % (vm.user))
        stdout.read()
        stdin, stdout, stderr = ssh.exec_command("sudo chgrp %s /tmp/admin.conf" % (vm.user))
        stdout.read()
        # sftp.get("/tmp/admin.conf", file_path + "/admin.conf")
        logger.info("Finished kubernetes master installation on: " + (vm.ip))
    except Exception as e:
        global retry
        # print '%s: %s' % (vm.ip, e)
        logger.error(vm.ip + " " + str(e))
        print_exception()
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    return retstr[-1]


def install_worker(join_cmd, vm):
    try:
        logger.info("Starting kubernetes slave installation on: " + (vm.ip))
        logger.info("User: " + vm.user + " key file: " + vm.key)
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key, timeout=30)

        parentDir = os.path.dirname(os.path.abspath(vm.key))
        os.chmod(parentDir, 0o700)
        os.chmod(vm.key, 0o600)

        sftp = ssh.open_sftp()
        sftp.chdir('/tmp/')
        file_path = os.path.dirname(os.path.abspath(__file__))
        install_script = file_path + "/" + "docker_kubernetes.sh"
        sftp.put(install_script, "kubernetes_setup.sh")
        # stdin, stdout, stderr = ssh.exec_command("sudo hostname ip-%s" % (vm.ip.replace('.', '-')))
        # stdout.read()
        stdin, stdout, stderr = ssh.exec_command("sudo sh /tmp/kubernetes_setup.sh")
        stdout.read()
        stdin, stdout, stderr = ssh.exec_command("sudo kubeadm reset")
        stdout.read()
        stdin, stdout, stderr = ssh.exec_command("sudo %s" % (join_cmd))
        stdout.read()
        logger.info("Finished kubernetes slave installation on: " + (vm.ip))
    except Exception as e:
        # print '%s: %s' % (vm.ip, e)
        logger.error(vm.ip + " " + str(e))
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    return "SUCCESS"


def deploy_on_master(deployment_file, vm):
    try:
        k8s_files = [f for f in listdir(deployment_file) if isfile(join(deployment_file, f))]
        logger.info("Starting deployment on: " + (vm.ip))
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key, timeout=30)

        parentDir = os.path.dirname(os.path.abspath(vm.key))
        os.chmod(parentDir, 0o700)
        os.chmod(vm.key, 0o600)

        stdin, stdout, stderr = ssh.exec_command("mkdir /tmp/k8s")
        stdout.read()
        sftp = ssh.open_sftp()
        sftp.chdir('/tmp/k8s')
        file_path = os.path.dirname(os.path.abspath(__file__))
        for f in k8s_files:
            k8s_file = deployment_file + "/" + f
            sftp.put(k8s_file, f)

        stdin, stdout, stderr = ssh.exec_command("kubectl create -f /tmp/k8s/ >> log 2>&1")
        s_out = stdout.read()
        e_out = stderr.read()

        time.sleep(2)
        # cmd = 'kubectl get svc --all-namespaces -o go-template=\'{{range .items}}{{range.spec.ports}}{{if .nodePort}}{{.nodePort}}{{"\\n"}}{{end}}{{end}}{{end}}\''
        cmd = 'kubectl get svc --output json'
        stdin, stdout, stderr = ssh.exec_command(cmd)
        e_out = stderr.read()
        json_output = stdout.read()

        # exposed_ports_str = ''
        # for port in exposed_ports:
        #     exposed_ports_str += port + ','
        # exposed_ports_str = exposed_ports_str[:-1]
    except Exception as e:
        # print '%s: %s' % (vm.ip, e)
        logger.error(vm.ip + " " + str(e))
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    return json_output


def deploy(vm_list, deployment_file):
    for i in vm_list:
        if i.role == "master":
            return deploy_on_master(deployment_file, i)


def run(vm_list, rabbitmq_host, owner):
    # rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672,user=owner)
    # logger.addHandler(rabbit)
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
    file_path = os.path.dirname(os.path.abspath(__file__))
    kuber_file = open(file_path + "/admin.conf", "r")
    kuber_string = kuber_file.read()
    kuber_file.close()
    return kuber_string

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
import json


def docker_check(vm, compose_name):
    try:
        print "%s: ====== Start Check Docker Services  ======" % (vm.ip)
        paramiko.util.log_to_file("deployment.log")
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
        node_format = '\'{\"ID\":\"{{.ID}}\",\"hostname\":\"{{.Hostname}}\",\"status\":\"{{.Status}}\",\"availability\":\"{{.Availability}}\",\"status\":\"{{.Status}}\"}\''
        cmd = 'sudo docker node ls --format ' + (node_format)
        json_response = {}
        cluster_node_info = []
        stdin, stdout, stderr = ssh.exec_command(cmd)
        node_ls_resp = stdout.readlines()
        for i in node_ls_resp: 
            if i.encode():
                json_str = json.loads(i.encode().strip('\n'))
                cluster_node_info.append(json_str)
        
        json_response ['cluster_node_info'] = cluster_node_info
        services_format = '\'{\"ID\":\"{{.ID}}\",\"name\":\"{{.Name}}\",\"image\":\"{{.Image}}\",\"node\":\"{{.Node}}\",\"desired_state\":\"{{.DesiredState}}\",\"current_state\":\"{{.CurrentState}}\",\"error\":\"{{.Error}}\",\"ports\":\"{{.Ports}}\"}\''
        cmd = 'sudo docker stack ps '+ compose_name +' --format ' + services_format
        stdin, stdout, stderr = ssh.exec_command(cmd)
        stack_ps_resp = stdout.readlines()
        services_info = []
        for i in stack_ps_resp: 
            if i.encode():
                json_str = json.loads(i.encode().strip('\n'))
                services_info.append(json_str)
        json_response ['services_info'] = services_info
        stack_format = '\'{"ID":"{{.ID}}","name":"{{.Name}}","mode":"{{.Mode}}","replicas":"{{.Replicas}}","image":"{{.Image}}"}\''
        cmd = 'sudo docker stack services '+ compose_name +' --format ' + (stack_format)
        stdin, stdout, stderr = ssh.exec_command(cmd)
        stack_resp = stdout.readlines()
        stack_info = []
        for i in stack_resp:
            if i.encode():
                json_str = json.loads(i.encode().strip('\n'))
                stack_info.append(json_str)
        json_response ['stack_info'] = stack_info   
        
        print "%s: =========== Check Finished ==============" % (vm.ip)
    except Exception as e:
        print '%s: %s' % (vm.ip, e)
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    return json_response




def run(vm_list, compose_name):
    for i in vm_list:
        if i.role == "master":
            ret = docker_check(i, compose_name)
            if "ERROR:" in ret:
                return ret
    return ret
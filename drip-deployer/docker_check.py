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
import logging
import linecache
import sys
import ast
import re
from drip_logging.drip_logging_handler import *

logger = logging.getLogger(__name__)
if not getattr(logger, 'handler_set', None):
    #logger.setLevel(logging.INFO)
    h = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    h.setFormatter(formatter)
    logger.addHandler(h)
    logger.handler_set = True
    

retry=0    


def get_resp_line(line):
    line = line.encode('utf-8').strip('\n').encode('string_escape')
    return json.dumps(line)
    

def docker_check(vm, compose_name):
    try:
        logger.info("Starting docker info services on: "+vm.ip)        
        paramiko.util.log_to_file("deployment.log")
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(vm.ip, username=vm.user, key_filename=vm.key)
        
        node_format = '\'{\"ID\":\"{{.ID}}\",\"hostname\":\"{{.Hostname}}\",\"status\":\"{{.Status}}\",\"availability\":\"{{.Availability}}\",\"status\":\"{{.Status}}\"}\''
        cmd = 'sudo docker node ls --format ' + (node_format)
                
        json_response = {}
        cluster_node_info = []
        stdin, stdout, stderr = ssh.exec_command(cmd)
        logger.info("Got response running \"docker node ls\"")        
        node_ls_resp = stdout.readlines()
        for i in node_ls_resp:
            line = get_resp_line(i)
            if line:
                node_info = json.loads(line)
                if not isinstance(node_info, dict):
                    node_info = ast.literal_eval(node_info)
                cluster_node_info.append(node_info)
        
        json_response ['cluster_node_info'] = cluster_node_info
        services_format = '\'{\"ID\":\"{{.ID}}\",\"name\":\"{{.Name}}\",\"image\":\"{{.Image}}\",\"node\":\"{{.Node}}\",\"desired_state\":\"{{.DesiredState}}\",\"current_state\":\"{{.CurrentState}}\",\"error\":\"{{.Error}}\",\"ports\":\"{{.Ports}}\"}\''
        
        cmd = 'sudo docker stack ps '+ compose_name +' --format ' + services_format
        logger.info("Got response running \"docker stack ps\"")  
        stdin, stdout, stderr = ssh.exec_command(cmd)
        stack_ps_resp = stdout.readlines()
        services_info = []
        services_ids = []
        nodes_hostname = set()
        for i in stack_ps_resp:      
            line = get_resp_line(i)
            if line:
                json_dict = {}
                json_dict = json.loads(line)
                json_dict = json.loads(json.dumps(json_dict))
                if not isinstance(json_dict, dict):
                    try:
                        json_dict = json.loads(json_dict)
                    except Exception as e:
                        json_dict = json_dict.replace('\"ports\":\"\"', '\"ports\":null').replace('\"\"', '\"')
                        json_dict = json_dict.replace("\\", "").replace("Noxe2x80xa6", "No...")
                        json_dict = json.loads(json_dict)
                nodes_hostname.add(json_dict['node'])
                services_info.append(json_dict)
                services_ids.append(json_dict['ID'])
        json_response ['services_info'] = services_info
        
        stack_format = '\'{"ID":"{{.ID}}","name":"{{.Name}}","mode":"{{.Mode}}","replicas":"{{.Replicas}}","image":"{{.Image}}"}\''
        cmd = 'sudo docker stack services '+ compose_name +' --format ' + (stack_format)
        stdin, stdout, stderr = ssh.exec_command(cmd)
        logger.info("Got response running \"docker stack services\"")  
        stack_resp = stdout.readlines()
        stack_info = []
        for i in stack_resp:
            line = get_resp_line(i)
            if line:
                json_dict = {}
                json_dict = json.loads(line)
                if not isinstance(json_dict, dict):
                     json_dict = json.loads(json_dict)
                stack_info.append(json_dict)
        json_response ['stack_info'] = stack_info
        
        cmd = 'sudo docker node inspect '
        for hostname in nodes_hostname:
             cmd += ' '+hostname        
        stdin, stdout, stderr = ssh.exec_command(cmd)
        logger.info("Got response running \"docker node inspect\"")  
        inspect_resp = stdout.readlines()
        
        response_str = ""
        for i in inspect_resp:
            line = i.rstrip("\n\r").encode()
            if line:
                response_str+=line
        json_dict = {}  
        response_str =  response_str.rstrip("\n\r").strip(' \t\n\r').strip().encode('string_escape')
        json_dict = json.loads(response_str)
        json_response['nodes_info'] = json_dict
        
        #"{{.Status.ContainerStatus.ContainerID}}"
        cmd = 'sudo docker inspect '
        for id in services_ids:
             cmd += ' '+id 
                    
        stdin, stdout, stderr = ssh.exec_command(cmd)
        logger.info("Got response running \"docker inspect\"")  
        inspect_resp = stdout.readlines()             
             
        response_str = ""
        for i in inspect_resp:
            line = i.rstrip("\n\r").encode()
            if line:
                response_str+=line
        json_dict = {}  
        response_str =  response_str.rstrip("\n\r").strip(' \t\n\r').strip().encode('string_escape')
        
        
        
        json_dict = json.loads(response_str)
        json_response['inspect_info'] = json_dict             
        
        
        logger.info("Finished docker info services on: "+vm.ip)                
    except Exception as e:
        global retry
        if retry < 10:
            logger.warning(vm.ip + " " + str(e)+". Retrying")
            retry+=1
            return docker_check(vm, compose_name)
        exc_type, exc_obj, tb = sys.exc_info()
        f = tb.tb_frame
        lineno = tb.tb_lineno
        filename = f.f_code.co_filename
        linecache.checkcache(filename)
        line = linecache.getline(filename, lineno, f.f_globals)

        print 'EXCEPTION IN ({}, LINE {} "{}"): {}'.format(filename, lineno, line.strip(), exc_obj)
        
        #logger.error(vm.ip + " " + str(e)+ " line:" +lineno)
        return "ERROR:" + vm.ip + " " + str(e)
    ssh.close()
    retry = 0
    return json_response




def run(vm_list, compose_name,rabbitmq_host,owner):
    rabbit = DRIPLoggingHandler(host=rabbitmq_host, port=5672,user=owner)
    logger.addHandler(rabbit)
    for i in vm_list:
        if i.role == "master":
            ret = docker_check(i, compose_name)
            if "ERROR:" in ret:
                return ret
    return ret
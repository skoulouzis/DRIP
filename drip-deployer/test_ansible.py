#!/usr/bin/env python
import pika
import json
import os
import time
from vm_info import VmInfo
import docker_kubernetes
import docker_engine
import docker_swarm
import docker_compose
import docker_service
import docker_check
import control_agent
import ansible_playbook
import sys, argparse
from threading import Thread
from time import sleep
import os.path
import logging
from os.path import expanduser




home = expanduser("~")
playbook_path=home+"/Downloads/playbook.yml"

playbook_path=sys.argv[1] #home+"/Downloads/playbook.yml"

ip = "147.228.242.97"
ip = sys.argv[2] #"147.228.242.97"

user="vm_user"
role = "master"
ssh_key_file=home+"/Downloads/id_rsa"

ssh_key_file = sys.argv[3] #home+"/Downloads/id_rsa"


vm_list = set()
vm = VmInfo(ip, user, ssh_key_file, role)
vm_list.add(vm)


rabbit_mq_host = sys.argv[4] #rabbit_mq_host


print sys.argv
print "playbook_path: "+playbook_path
print "ip: "+ip
print "ssh_key_file: "+ssh_key_file
print "rabbit_mq_host: "+rabbit_mq_host

ret = ansible_playbook.run(vm_list,playbook_path,rabbit_mq_host,"owner")
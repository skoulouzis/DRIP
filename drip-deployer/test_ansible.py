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

ip = "147.228.242.81"
user="vm_user"
role = "master"
ssh_key_file=home+"/Downloads/id_rsa"

vm_list = set()
vm = VmInfo(ip, user, ssh_key_file, role)
vm_list.add(vm)

ret = ansible_playbook.run(vm_list,playbook_path,"localhost","owner")
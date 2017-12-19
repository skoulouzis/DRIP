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




hosts="localhost,"
home = expanduser("~")
playbook_path=home+"/workspace/DRIP/drip-deployer/deployer_files/1513695133139/playbook.yml"
user="vm_user"
ssh_key_file=home+"/workspace/DRIP/drip-deployer/deployer_files/1513695133139/1.txt"
extra_vars = {}
passwords = {}

ansible_playbook.execute_playbook(hosts,playbook_path,user,ssh_key_file,extra_vars,passwords)
#!/usr/bin/env python

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
 
from ansible.plugins.callback import CallbackBase
 

__author__ = 'S. Koulouzis'


class ResultsCollector(CallbackBase):
    def __init__(self, *args, **kwargs):
        super(ResultsCollector, self).__init__(*args, **kwargs)
        self.host_ok = []
        self.host_unreachable = []
        self.host_failed = []

    def v2_runner_on_unreachable(self, result, ignore_errors=False):
        name = result._host.get_name()
        task = result._task.get_name()
        #self.host_unreachable[result._host.get_name()] = result
        self.host_unreachable.append(dict(ip=name, task=task, result=result))

    def v2_runner_on_ok(self, result,  *args, **kwargs):
        name = result._host.get_name()
        task = result._task.get_name()
        if task == "setup":
            pass
        elif "Info" in task:
            self.host_ok.append(dict(ip=name, task=task, result=result))
        else:
            self.host_ok.append(dict(ip=name, task=task, result=result))

    def v2_runner_on_failed(self, result,   *args, **kwargs):
        name = result._host.get_name()
        task = result._task.get_name()
        self.host_failed.append(dict(ip=name, task=task, result=result))
        
        
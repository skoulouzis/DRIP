# Code modified from: https://github.com/dibenede/ndn-tutorial-gec21
# -*- Mode:python; c-file-style:"gnu"; indent-tabs-mode:nil -*- */
#
# Copyright (c) 2013-2014 Regents of the University of California.
# Copyright (c) 2014 Susmit Shannigrahi, Steve DiBenedetto
#
# This file is part of ndn-cxx library (NDN C++ library with eXperimental eXtensions).
#
# ndn-cxx library is free software: you can redistribute it and/or modify it under the
# terms of the GNU Lesser General Public License as published by the Free Software
# Foundation, either version 3 of the License, or (at your option) any later version.
#
# ndn-cxx library is distributed in the hope that it will be useful, but WITHOUT ANY
# WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
# PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
#
# You should have received copies of the GNU General Public License and GNU Lesser
# General Public License along with ndn-cxx, e.g., in COPYING.md file.  If not, see
# <http://www.gnu.org/licenses/>.
#
# See AUTHORS.md for complete list of ndn-cxx authors and contributors.
#
# @author Wentao Shang <http://irl.cs.ucla.edu/~wentao/>
# @author Steve DiBenedetto <http://www.cs.colostate.edu/~dibenede>
# @author Susmit Shannigrahi <http://www.cs.colostate.edu/~susmit>
# pylint: disable=line-too-long

import sys
import time
import argparse
import traceback

from pyndn import Interest
from pyndn import Name
from sim.my_face import *
import json
import random
import timeit
import datetime
from collections import OrderedDict


class Consumer(object):
    '''Sends Interest, listens for data'''

    def __init__(self, prefix, pipeline, count,window,sequence):
        self.prefix = prefix
        self.pipeline = pipeline
        self.count = count
        self.nextSegment = 0
        self.outstanding = dict()
        self.isDone = False
        self.face = MyFace(False)
        self.window = window
        self.seq = sequence
        self.name_index = 0
        self.interest_count=0
        self.request_elapsed_min = sys.maxint
        self.request_elapsed_max = -sys.maxint - 1
        self.request_elapsed_avg = 0
        self.request_elapsed_sum = 0
        self.names = []

    def run(self):
        try:
            while self.nextSegment < self.count:
                self.request_start_time = timeit.default_timer()
                name = self.get_next_name()
                self._sendNextInterest(name)
                self.nextSegment += 1
                self.request_elapsed = timeit.default_timer() - self.request_start_time
                if self.request_elapsed > self.request_elapsed_max:
                    self.request_elapsed_max = self.request_elapsed
                
                if self.request_elapsed < self.request_elapsed_min:
                    self.request_elapsed_min = self.request_elapsed
                    
                self.request_elapsed_sum += self.request_elapsed
                self.request_elapsed_avg = float(self.request_elapsed_sum) / float(self.nextSegment)
            
        except RuntimeError as e:
            print "ERROR: %s" % e
#        finally:
            



    def _onData(self, interest, data):
        data = json.loads(data)
        interest = Interest(data['name'])
        ndn_data = Data(data['name'])
        ndn_data.setContent(data['content'])
        payload = ndn_data.getContent()
        name = Name(data['name'])
#        print "Received data: %s\n" % payload.toRawStr()
        del self.outstanding[name.toUri()]


    
        
    def get_next_name(self):
        batch_start_time = timeit.default_timer()
        
        if not self.names:
            self.names = self.smart_alg()
        if self.name_index >  (len(self.names)-1):
            self.names = self.smart_alg()
            self.name_index = 0
            batch_elapsed = timeit.default_timer() - batch_start_time
            
            filename = str('consumer_request_results.csv')
            if os.path.exists(filename):
                append_write = 'a'
                line = (str(batch_elapsed)+","+str(self.nextSegment)+","+str(self.request_elapsed_min)+","+str(self.request_elapsed_max)+","+str(self.request_elapsed_avg)+","+str(datetime.datetime.now()))
            else:
                append_write = 'w'
                line = "batch_elapsed,interest_count,request_elapsed_min,request_elapsed_max,request_elapsed_avg, date\n"+(str(batch_elapsed)+","+str(self.nextSegment)+","+str(self.request_elapsed_min)+","+str(self.request_elapsed_max)+","+str(self.request_elapsed_avg)+","+str(datetime.datetime.now()))
#
            report = open(filename,append_write)
            report.write(line + '\n')
            report.close()

        name = self.names[self.name_index]
        name = Name(name)
#        suf = str(random.randint(0,10))
#        name = Name("/ndn/"+suf)
#        print "Requesting: %s"%(name)
        self.name_index +=1
        return name
    
    def dump_client(self):
        size_list = [50,70,90,1000,3000,15000,20000,30000,50000,100000,200000,250000,400000,500000,700000,750000,800000,850000,1000000,1500000,1600000,1700000,1900000,2500000,5000000,5500000,7500000,8200000,9000000,10000000]
        names = []
        for i in range(0,30,1):
            names.append(size_list[i])
#        print names
#        pick = random.choice(names.keys())
#        print k,v 
        #print pick, str(names[pick])
        #pick random element in the dictionery every 0.1 second
        # while True:
        #     pick = random.choice(names.keys())
        #     print pick, str(names[pick])
        #     time.sleep(0.1)
        return random.choice(names)
    
    def smart_alg(self):
        batch = []
        for i in xrange (self.window):
            batch.append(self.dump_client())
        keys = []
        if self.seq == 1:
            for key in batch:
                name = "/ndn/"+str(key)
                keys.append(name)
            return keys
        elif self.seq == 2:
#            batch = OrderedDict(sorted(batch.items(),key=lambda x:x[1]))
            batch = sorted(batch, key=int)               # SF
            for key in batch:
                name = "/ndn/"+str(key)
                keys.append(name) 
            return keys
        elif self.seq == 3:
#            batch = OrderedDict(sorted(batch.items(), key=lambda x:x[1]))
#            batch = OrderedDict(reversed(batch.items()))
            batch = sorted(batch, key=int, reverse=True) # BF
            for key in batch:
                name = "/ndn/"+str(key)
                keys.append(name) 
            return keys
        else:
            print "Not a method!" 
            return None
        

    def _sendNextInterest(self, name):
        interest =  {}
        interest['name'] = str(name)
#        interest = Interest(name)
#        uri = name.toUri()
#        interest.setInterestLifetimeMilliseconds(4000)
        interest['interest_lifetime_milliseconds'] = 4000
#        interest.setMustBeFresh(True)
        interest['must_be_fresh'] = False
        interest = json.dumps(interest)
        if name.toUri() not in self.outstanding:
            self.outstanding[name.toUri()] = 1
        self.face.expressInterest(interest, self._onData, self._onTimeout)


    def _onTimeout(self, interest):
        name = interest.getName()
        uri = name.toUri()
        
        print "TIMEOUT #%d: segment #%s" % (self.outstanding[uri], name[-1].toNumber())
        self.outstanding[uri] += 1

        if self.outstanding[uri] <= 3:
            self._sendNextInterestWithSegment(name)
        else:
            self.isDone = True
10


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Parse command line args for ndn consumer')

#    parser.add_argument("-u", "--uri", required=True, help='ndn URI to retrieve')
    parser.add_argument("-c", "--count", required=False, help='number of (unique) Interests to send before exiting, default = repeat until final block', nargs='?', const=1,  type=int, default=None)
    parser.add_argument("-w", "--window", required=True, help='Size of window', nargs='?', const=1,  type=int, default=1)
    parser.add_argument("-s", "--sequence", required=True, help='sorting sequence', nargs='?', const=1,  type=int, default=1)

    arguments = parser.parse_args()

    try:
        count = arguments.count
        window = arguments.window
        sequence = arguments.sequence
        if count <= window:
            print "count must be larger than window"
        Consumer(Name("uri"), None, count,window,sequence).run()

    except:
        traceback.print_exc(file=sys.stdout)
        print "Error parsing command line arguments"

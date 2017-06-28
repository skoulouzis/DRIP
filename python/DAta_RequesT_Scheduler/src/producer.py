# Code modified from: https://github.com/dibenede/ndn-tutorial-gec21
# -*- Mode:python; c-file-style:"gnu"; indent-tabs-mode:nil -*- */
#
# Copyright (C) 2014 Regents of the University of California.
# Copyright (c) 2014 Susmit Shannigrahi, Steve DiBenedetto
#
# Author: Jeff Thompson <jefft0@remap.ucla.edu>
# Author Steve DiBenedetto <http://www.cs.colostate.edu/~dibenede>
# Author Susmit Shannigrahi <http://www.cs.colostate.edu/~susmit>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
# A copy of the GNU General Public License is in the file COPYING.

import sys
import time
import argparse
import traceback
import random

from pyndn import Name
from pyndn import Data
from pyndn import Face
from pyndn.security import KeyChain
from sim.my_face import *
import collections
import datetime



class Producer(object):

    def __init__(self, delay=None,eviction_alg='fifo',cache_size=10):
#        self.keyChain = KeyChain()
        self.delay = delay
        self.nDataServed = 0
        self.cache = collections.OrderedDict()
        self.cache_miss = 0
        self.cache_hit = 0
        self.current_cache_size = 0
        self.max_cache_size = cache_size
        self.eviction_alg = eviction_alg
        self.interest_count=0
        print "max_cache_size: %d, eviction_algorithm: %s, cache_miss_delay: %d"%(self.max_cache_size,self.eviction_alg,self.delay)


    def run(self, namespace):

        # The default Face will connect using a Unix socket
        face = MyFace(True)
        prefix = Name(namespace)

        # Use the system default key chain and certificate name to sign commands.
#        face.setCommandSigningInfo(self.keyChain, self.keyChain.getDefaultCertificateName())
        # Also use the default certificate name to sign data packets.
        face.registerPrefix(prefix, self.onInterest, self.onRegisterFailed)

        print "Registered prefix: ", prefix.toUri()

        while True:
            face.processEvents()
            time.sleep(0.01)



    def onInterest(self, prefix, interest, transport, registeredPrefixId):
        self.interest_count +=1
        interestName = interest.getName()
        if str(interest.getName()) in self.cache:      
            self.cache_hit+=1
            cache_entry = self.cache[str(interest.getName())]
            cache_entry['use_count'] = cache_entry['use_count']+1
            cache_entry['use_date'] = datetime.datetime.now()
            new_cache_entry = cache_entry.copy()
            self.cache[str(interest.getName())] = new_cache_entry
        else:
            self.cache_miss+=1
            while self.current_cache_size > self.max_cache_size:
                self.evict()
            cache_entry = {}
            cache_entry['name'] = str(interest.getName())
            cache_entry['size'] = int(os.path.basename(os.path.normpath(str(interest.getName()))))
            cache_entry['use_count'] = 0
            cache_entry['use_date'] = datetime.datetime.now()
            cache_entry['import_date'] = datetime.datetime.now()
            
            self.cache[str(interest.getName())] = cache_entry
            self.current_cache_size += int(os.path.basename(os.path.normpath(str(interest.getName()))))
            time.sleep(self.delay)
            
        data={}
        data['name'] = str(interest.getName())
        data['content'] = str("Hello " + interestName.toUri())
        data['freshness_period'] = 3600 * 1000
        data = json.dumps(data)
#        interestName = interest.getName()
#        data = Data(interestName)
#        data.setContent("Hello " + interestName.toUri())
#        data.getMetaInfo().setFreshnessPeriod(3600 * 1000)

#        self.keyChain.sign(data, self.keyChain.getDefaultCertificateName())
#        transport.send(data.wireEncode().toBuffer())       
        self.nDataServed += 1
#        print "Replied to: %s (#%d)" % (interestName.toUri(), self.nDataServed)      
        self.calculate_hit_ratio()
        transport.send(data)


    def onRegisterFailed(self, prefix):
        print "Register failed for prefix", prefix.toUri()
        
        
    def calculate_hit_ratio(self):
        cache_hit_ratio = ( float(self.cache_hit) / (float(self.cache_hit) + float(self.cache_miss)) ) * 100
        print "cache_hit_ratio: %s, total_interest_count: %d, date: %s"%(cache_hit_ratio,self.interest_count,datetime.datetime.now())
        
        
    def evict(self):
        if self.eviction_alg == 'fifo':
            item = self.fifo()
        if self.eviction_alg == 'lifo':            
            item = self.lifo()
        if self.eviction_alg == 'lru':        
            item = self.lru()     
        if self.eviction_alg == 'mru':        
            item = self.mru()                    
        if self.eviction_alg == 'rr':        
            item = self.rr()    
        if self.eviction_alg == 'lfu':        
            item = self.lfu()               
        self.current_cache_size-= int(item['size'])
        

    def fifo(self):
        item = self.cache.popitem(last=False)
        return item[1]
    
    def lifo(self):
        item = self.cache.popitem(last=True)
        return item[1]  
    
    def lru(self):
        oldest_date = datetime.datetime(3000, 1, 1)
        for name in self.cache:
            cache_line = self.cache[name]
            if cache_line['use_date'] < oldest_date:
                oldest_date = cache_line['use_date']
                item = cache_line
        self.cache.pop(item['name'])
        return item
    
    
    def mru(self):
        newest_date = datetime.datetime(10, 1, 1)
        for name in self.cache:
            cache_line = self.cache[name]
            if cache_line['use_date'] > newest_date:
                newest_date = cache_line['use_date']
                item = cache_line
        self.cache.pop(item['name'])                
        return item
    
    def rr(self):
        item_key = random.choice(self.cache.keys())
        item = self.cache.pop(item_key)
        return item
            
            
    def lfu(self):
        times_used = sys.maxint
        for name in self.cache:
            cache_line = self.cache[name]
            if cache_line['use_count'] < times_used:
                times_used = cache_line['use_count']
                item = cache_line
        self.cache.pop(item['name'])
        return item            
        



if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Parse command line args for ndn producer')
    parser.add_argument("-n", "--namespace", required=True, help='namespace to listen under')
    parser.add_argument("-d", "--delay", required=False, help='namespace to listen under', nargs= '?', const=1, type=float, default=0.1)
    parser.add_argument("-e", "--eviction", required=False, help='eviction algorithm',nargs= '?', const=1, type=str, default='fifo')
    parser.add_argument("-c", "--cache_size", required=False, help='cache size',nargs= '?', const=1, type=int, default=10)
    args = parser.parse_args()

    try:
        namespace = args.namespace
        delay = args.delay
        eviction_alg = args.eviction
        cache_size = args.cache_size
        Producer(delay,eviction_alg,cache_size).run(namespace)

    except:
        traceback.print_exc(file=sys.stdout)
sys.exit(1)
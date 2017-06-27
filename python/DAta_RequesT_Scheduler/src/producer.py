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



class Producer(object):

    def __init__(self, delay=None,eviction_alg='fifo'):
#        self.keyChain = KeyChain()
        self.delay = delay
        self.nDataServed = 0
        self.cache = collections.OrderedDict()
        self.cache_miss = 0
        self.cache_hit = 0
        self.current_cache_size = 0
        self.max_cache_size = 10
        self.eviction_alg = eviction_alg


    def run(self, namespace):

        # The default Face will connect using a Unix socket
        face = MyFace(True)
        prefix = Name(namespace)

        # Use the system default key chain and certificate name to sign commands.
#        face.setCommandSigningInfo(self.keyChain, self.keyChain.getDefaultCertificateName())
        # Also use the default certificate name to sign data packets.
        face.registerPrefix(prefix, self.onInterest, self.onRegisterFailed)

        print "Registering prefix", prefix.toUri()

        while True:
            face.processEvents()
            time.sleep(0.01)



    def onInterest(self, prefix, interest, transport, registeredPrefixId):
        if self.delay is not None:
            time.sleep(self.delay)
            
        interestName = interest.getName()
        if str(interest.getName()) in self.cache:      
            self.cache_hit+=1
            self.calculate_hit_ratio()
        else:
            self.cache_miss+=1
            while self.current_cache_size > self.max_cache_size:
                self.evict()
            self.cache[str(interest.getName())] = int(os.path.basename(os.path.normpath(str(interest.getName()))))
            self.current_cache_size += int(os.path.basename(os.path.normpath(str(interest.getName()))))
            time.sleep(0.1)
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
#            print "Replied to: %s (#%d)" % (interestName.toUri(), self.nDataServed)            
        transport.send(data)


    def onRegisterFailed(self, prefix):
        print "Register failed for prefix", prefix.toUri()
        
        
    def calculate_hit_ratio(self):
        cache_hit_ratio = ( float(self.cache_hit) / (float(self.cache_hit) + float(self.cache_miss)) ) * 100
        print "cache_hit_ratio: %s"%cache_hit_ratio
        
        
    def evict(self):
        if self.eviction_alg == 'fifo':
            item = self.fifo()
        self.current_cache_size-= int(item[1])
        

    def fifo(self):
        item = self.cache.popitem(last=False)
        return item
        



if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Parse command line args for ndn producer')
    parser.add_argument("-n", "--namespace", required=True, help='namespace to listen under')
    parser.add_argument("-d", "--delay", required=False, help='namespace to listen under', nargs= '?', const=1, type=float, default=None)
    parser.add_argument("-e", "--eviction", required=False, help='eviction algorithm',nargs= '?', const=1, type=str, default='fifo')
    args = parser.parse_args()

    try:
        namespace = args.namespace
        delay = args.delay
        eviction_alg = args.eviction
        Producer(delay,eviction_alg).run(namespace)

    except:
        traceback.print_exc(file=sys.stdout)
sys.exit(1)
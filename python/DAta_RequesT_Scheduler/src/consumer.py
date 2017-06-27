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


class Consumer(object):
    '''Sends Interest, listens for data'''

    def __init__(self, prefix, pipeline, count):
        self.prefix = prefix
        self.pipeline = pipeline
        self.count = count
        self.nextSegment = 0
        self.outstanding = dict()
        self.isDone = False
        self.face = MyFace(False)
        self.start_time = timeit.default_timer()
        

    def run(self):
        try:
            while not self.isDone:
                name = self.get_next_name()
                self._sendNextInterest(name)
                self.nextSegment += 1
            
#            self.face.processEvents()
#            time.sleep(0.01)
            elapsed = timeit.default_timer() - self.start_time
            print "elapsed (sec): %s" %elapsed
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
        self.nextSegment += 1
#        print "Received data: %s\n" % payload.toRawStr()
        del self.outstanding[name.toUri()]


    
        
    def get_next_name(self):
        suf = str(random.randint(0,10))
        name = Name("/ndn/"+suf)
        
        if self.nextSegment > self.count:
             self.isDone = True
        return name
        

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



if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Parse command line args for ndn consumer')

#    parser.add_argument("-u", "--uri", required=True, help='ndn URI to retrieve')
    parser.add_argument("-c", "--count", required=False, help='number of (unique) Interests to send before exiting, default = repeat until final block', nargs='?', const=1,  type=int, default=None)

    arguments = parser.parse_args()

    try:
        count = arguments.count
        
        Consumer(Name("uri"), None, count).run()

    except:
        traceback.print_exc(file=sys.stdout)
        print "Error parsing command line arguments"
sys.exit(1)
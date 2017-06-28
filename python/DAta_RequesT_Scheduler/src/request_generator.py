# the space of objects is 30 objects which has different sizes between 50kB-50GB (look for generating normal distribution)
# 1- A client generates 100 requests with sequence of numbers /ndn/1,ndn/2 ...
# 2- In the client, there is an algorithm that rearranges the objects names by size and sends them to the router
# 3- The algorithm uses different window sizes meaning the number of requests it groups and rearranges
# 4- The router has a limited cache the is equal or larger than the largest object.
# 5- The router receives a request and looks in the cache for a total match, if found, the object is retrieved immediatly
# 6- If not found, a delay is generated and a new object is created with the same name in the request and put in the cache
# 7- When a new object arrives and the cache is full one of the eviction algorithms is used to remove one or more older objects.
# Measurements: The time it takes for retrieving each set of objects


import random
import time
from collections import OrderedDict

# the space of objects is 30 objects which has different sizes between 50kB-50GB (look for generating normal distribution)
# 1- A client generates 100 requests with sequence of numbers /ndn/1,ndn/2 ...

def dump_client():
   size_list = [50,70,90,1000,3000,15000,20000,30000,50000,100000,200000,250000,400000,500000,700000,750000,800000,850000,1000000,1500000,1600000,1700000,1900000,2500000,5000000,5500000,7500000,8200000,9000000,10000000]
   names = {}
   for i in range(0,30,1):
       key = "/ndn/" + str(i)
       value = size_list[i]
       names[key] = value
   #print names
   pick = random.choice(names.keys())
   #print pick, str(names[pick])
   #pick random element in the dictionery every 0.1 second
   # while True:
   #     pick = random.choice(names.keys())
   #     print pick, str(names[pick])
   #     time.sleep(0.1)
   return pick + ":" + str(names[pick])

#dump_client()

# 2- In the client, there is an algorithm that rearranges the objects names by size and sends them to the router
# 3- The algorithm uses different window sizes meaning the number of requests it groups and rearranges

def smart_alg():
  window = int(raw_input("Enter window size:" ))
  seq = raw_input("Enter elements arrangement method (1 for random, 2 for ascending or 3 for descending):")

  while True:
      patch = {}
      for i in xrange (window):
          from_dump = dump_client()
          key,value= from_dump.split(':')
          value = int(value)
          patch[key] = value
          time.sleep(0.1)
      # print key
      # print value
      #print patch
      if seq == "1":
          for key in patch:
              print key
          time.sleep(2)
          print '\n'
      elif seq == "2":
          patch = OrderedDict(sorted(patch.items(),key=lambda x:x[1]))
          for key in patch:
              print key
          time.sleep(2)
          print '\n'
      elif seq == "3":
          patch = OrderedDict(sorted(patch.items(), key=lambda x:x[1]))
          patch = OrderedDict(reversed(patch.items()))
          for key in patch:
              print key
          time.sleep(2)
          print '\n'
      else:
          print "Not a method!"

smart_alg()

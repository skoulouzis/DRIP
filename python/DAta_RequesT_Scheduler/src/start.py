from producer import *
from subprocess import Popen
from time import sleep
import os
from threading import Thread


def threaded(fn):
    def wrapper(*args, **kwargs):
        thread = threading.Thread(target=fn, args=args, kwargs=kwargs)
        thread.start()
        return thread
    return wrapper


def run_exp(cache_eviction, cache_size, window, arr_method):
    prod = ['python', 'src/producer.py', '-n', '/ndn/', '-d', '0.01', 
    '-e', cache_eviction, '-c', arr_method]
    prod_file = ('./experiments-results/prod/'
                 'exp-evict-%s-size-%s-win-%s-meth-%s-prod.txt') % (cache_eviction, cache_size, window, arr_method)
    prod_file = open(prod_file, 'a')
#
#    cons = ['python', 'src/consumer.py', '-c', '10', '-w', window, '-s', arr_method]
#    cons_file = ('./experiments-results/cons/'
#                 'exp-evict-%s-size-%s-win-%s-meth-%s-cons.txt') % (cache_eviction, cache_size, window, arr_method)
#    cons_file = open(cons_file, 'a')
#
    p = Popen(prod, stdout=prod_file, stderr=prod_file)
    print p.pid
#    sleep(1) #or else consumer will start too fast and face an error
##    c = Popen(cons, stdout=cons_file, stderr=cons_file)
##    c.wait()
#    print('Consumer ended')
##    sleep(10)
##    p.kill()
    print('Producer killed')


cache_evictions = ('lru', 'lfu', 'fifo', 'rr')
cache_sizes = ('10000000', '15000000', '20000000', '50000000', '100000000')
windows = ('5', '10', '15', '20', '25', '30', '35', '40', '45', '50')
arr_methods = ('1', '2', '3')

if not os.path.exists('./experiments-results/prod/'):
    os.makedirs('./experiments-results/prod/')
    
if not os.path.exists('./experiments-results/cons/'):
    os.makedirs('./experiments-results/cons/')    


run_exp(cache_evictions[0], cache_sizes[0], windows[0], arr_methods[0])

#for cache_eviction in cache_evictions:
#    for cache_size in cache_sizes:
#        for window in windows:
#            for arr_method in arr_methods:
#                run_exp(cache_eviction, cache_size, window, arr_method)
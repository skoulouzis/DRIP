#!/bin/bash

cache_eviction=(lru lfu fifo rr)
cache_size=("10000000" "15000000")
window=("5" "10")
arr_method=("1")

for a in "${cache_eviction[@]}"; do
  for b in "${cache_size[@]}"; do
    for c in "${window[@]}"; do
      for d in "${arr_method[@]}"; do
         python src/producer.py -n /ndn/ -d 0.01 -e $a -c $b &
         sleep 1
	 python src/consumer.py -c 10 -w $c -s $d &
         pid_prod=`ps -ef | grep producer | grep -v grep | awk '{print $2}'`
         pid_cons=`ps -ef | grep consumer | grep -v grep | awk '{print $2}'`
         echo $pid_cons
         echo $pid_prod
         wait $pid_cons
         echo "consumer experiment ended!"
         mv producer_cache_size*.csv experiments-results/prod/exp-evict-$a-size-$b-win-$c-meth-$d-prod.csv
         
         mv consumer_request_results.csv ./experiments-results/cons/exp-evict-$a-size-$b-win-$c-meth-$d-cons.txt
#          sleep 1
         kill -9 $pid_prod
         echo "Producer killed"
# 	 sleep 5
      done
    done 
  done 
done 


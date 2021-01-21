#!/bin/bash

TOSCA_ID="5f8eb46c8ff9431bd55e931a"
BASE_URL="https://fry.lab.uvalight.net:30001/manager/"

USERNAME=deploy_tester
PASSWORD=edvbafeabvafdb

# 
# # curl -s -D - -k -u $USERNAME:$PASSWORD -X GET "$BASE_URL/tosca_template/$TOSCA_ID" &> tosca_output  
# RESPONSE=`cat tosca_output | head -n 1 | cut '-d ' '-f2'`
# 
# if [ $RESPONSE != "200" ];
# then
#     cat  tosca_output
#     exit -1
# fi
# TOSCA_CONTENTS=`cat tosca_output | tail -n +11`
# echo $TOSCA_CONTENTS
# 
# 
# # curl -s -D - -k -u $USERNAME:$PASSWORD -X GET "$BASE_URL/provisioner/provision/$TOSCA_ID" &> provision_output  
# RESPONSE=`cat provision_output | head -n 1 | cut '-d ' '-f2'`
# 
# if [ $RESPONSE != "200" ];
# then
#     cat  provision_output
#     exit -1
# fi
# PROVISION_ID=`cat provision_output | tail -n +11`
# PROVISION_ID=5f8ec0a68ff9431bd55e931e
# echo $PROVISION_ID
# 
# 
# curl -s -D - -k -u $USERNAME:$PASSWORD -X GET "$BASE_URL/deployer/deploy/$PROVISION_ID" &> deploy_output  
# RESPONSE=`cat deploy_output | head -n 1 | cut '-d ' '-f2'`
# 
# if [ $RESPONSE != "200" ];
# then
#     cat  deploy_output
#     exit -1
# fi
# DEPLOY_ID=`cat deploy_output | tail -n +11`
# echo $DEPLOY_ID
# 
# # 
# 
# # curl -u $USERNAME:$PASSWORD -X GET "$BASE_URL/tosca_template/$DEPLOY_ID"
# # 
# # curl -u $USERNAME:$PASSWORD -X DELETE "$BASE_URL/tosca_template/$DEPLOY_ID"
# 
# 

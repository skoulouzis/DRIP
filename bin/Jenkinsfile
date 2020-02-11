pipeline {
    agent none
    stages {
        stage('Build java-code') {
            agent any
            tools {
                maven "maven-3.6.2"
                jdk "openjdk-11"
            }
            steps {
                git branch: 'DRIP_3.0', url: 'https://github.com/skoulouzis/DRIP.git'
                sh "mvn -Dmaven.test.skip=true install"    
            }
        }
        stage('Build python-code') {
            agent {
                docker { image 'python:3.7-buster' }
            }
            steps {
                sh 'cd drip-planner && python3.7 -m venv venv && venv/bin/pip3 install -r requirements.txt'
                sh "cd ../"
                sh "cd sure_tosca-flask-server && python3.7 -m venv venv && venv/bin/pip3 install -r requirements.txt && venv/bin/pip3 install -r test-requirements.txt" 
            }
        }
        stage('Test java-code') {
            agent any
            tools {
                maven "maven-3.6.2"
                jdk "openjdk-11"
            }
            steps {
                sh "mvn test"
            }
        }     
        stage('Test python-code') {
            agent {
                docker { image 'python:3.7-buster' }
            }
            steps {
                sh "cd drip-planner && venv/bin/python3.7 -m unittest discover"   
                sh "cd sure_tosca-flask-server && venv/bin/python3.7 -m unittest discover"   
            }
        }  
        stage('Package java-code') {
            agent any
            tools {
                maven "maven-3.6.2"
                jdk "openjdk-11"
            }
            steps {
                sh "cd drip-manager && mvn -Dmaven.test.skip=true install dockerfile:build"   
            }
        }    
        stage('Package python-code') {
            agent any
            steps {
                sh "cd sure_tosca-flask-server && docker build -t sure-tosca:3.0.0 ."
                sh "cd ../"
                sh "cd drip-planner && docker build -t drip-planner:3.0.0 ."
            }
        }     
    }
}

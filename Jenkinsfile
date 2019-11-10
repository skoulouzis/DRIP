pipeline {
   agent any
   tools {
      // Install the Maven version configured as "M3" and add it to the path.
      maven "maven-3.6.2"
      jdk "openjdk-11"
   }

   stages {
      stage('Build') {
            steps {
                echo 'Building'
                git branch: 'DRIP_3.0', url: 'https://github.com/skoulouzis/DRIP.git'
                sh "mvn -Dmaven.test.skip=true install"    
                sh "cd drip-planner && python3.7 -m venv venv && venv/bin/pip3 install -r requirements.txt"   
                sh "cd ../"
                sh "cd sure_tosca-flask-server && python3.7 -m venv venv && venv/bin/pip3 install -r requirements.txt && venv/bin/pip3 install -r test-requirements.txt"   
                sh "pwd && ls"
        }
      }
      stage('Test') {
          steps {
              echo 'Testing'
              sh "mvn test"
              sh "cd drip-planner && venv/bin/python3.7 -m unittest discover"   
              sh "cd sure_tosca-flask-server && venv/bin/python3.7 -m unittest discover"   
          }
        }
        stage('Package') {
            steps {
                echo 'Deploying'
                sh "cd drip-manager && mvn -Dmaven.test.skip=true install dockerfile:build"
                
            }
        }             
        stage('Deploy') {
            steps {
                echo 'Deploying'
                
            }
        }        
   }
}

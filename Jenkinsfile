node {
    stage('Clone sources') {
        git branch: 'DRIP_3.0', url: 'https://github.com/skoulouzis/DRIP.git'
    }
    stage('Build') {
            steps {
                echo 'Building'
                git branch: 'DRIP_3.0', url: 'https://github.com/skoulouzis/DRIP.git'
                sh "mvn -Dmaven.test.skip=true install"    
                sh "cd drip-planner && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt"   
                sh "cd ../"
                sh "cd sure_tosca-flask-server && python3 -m venv venv && venv/bin/pip3 install -r requirements.txt && venv/bin/pip3 install -r test-requirements.txt"
        }    
    }
}

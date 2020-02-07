pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'twwo', url: 'https://github.com/hkoscielski/entrustments-system.git'
                sh 'chmod +x mvnw'
                sh 'chmod +x ./entrustments-webservice/mvnw'
            }
        }



        stage('Upgrade database') {
           steps {
                sh 'docker start app-team14-mysql || true'

            }
        }

        stage('Start applications') {
           steps {
                sh 'docker stop entrustments-webservice || true && docker rm entrustments-webservice || true && docker run -p 8081:8081 --name entrustments-webservice --network=database --link=app-team14-mysql entrustments-webservice && sleep 60'
                sh 'docker stop entrustments-webapp || true && docker rm entrustments-webapp || true && docker run -p 4200:80 --name entrustments-webapp --network=database --link=entrustments-webservice entrustments-webapp || true'
            }
        }
    }
}
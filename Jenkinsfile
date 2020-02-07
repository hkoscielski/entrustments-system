pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'twwo', url: 'https://github.com/hkoscielski/entrustments-system.git'
                sh 'chmod +x mvnw'
                script {
                    version = sh (script: './mvnw help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
                }
            }
        }

        stage('Compile') {
            steps {
                sh './mvnw compile'
                sh 'echo ${version}'
            }
        }

        stage('Test') {
           steps {
                sh './mvnw test'
            }
        }

        stage('Package') {
           steps {
                sh './mvnw clean package'
            }
        }

        stage('Build Docker images') {
           steps {
                sh 'docker build -t entrustments-webservice entrustments-webservice/'
                sh 'docker build -t entrustments-webapp entrustments-webapp/'
            }
        }

        stage('Start applications') {
           steps {
                sh 'docker stop entrustments-webservice || true && docker rm entrustments-webservice || true && docker run -p 8081:8081 --name entrustments-webservice entrustments-webservice && sleep 60'
                sh 'docker stop entrustments-webapp || true && docker rm entrustments-webapp || true && docker run -p 4200:80 --name entrustments-webapp entrustments-webapp || true'
            }
        }
    }
}
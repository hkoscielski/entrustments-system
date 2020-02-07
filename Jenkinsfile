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

        stage('Compile') {
            steps {
                sh './mvnw compile'
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
                sh 'docker-compose build'
            }
        }

        stage('Start applications') {
           steps {
                sh 'docker-compose up'
            }
        }
    }
}
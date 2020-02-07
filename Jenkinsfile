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
                sh 'docker-compose build'
            }
        }
    }
}
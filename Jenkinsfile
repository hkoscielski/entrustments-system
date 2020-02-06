pipeline {
    agent any

    stages {
        stage('Checkout code') {
            steps {
                git 'https://github.com/hkoscielski/entrustments-system.git'
                sh 'chmod +x mvnw'
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
    }
}
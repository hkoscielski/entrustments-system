pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'twwo', url: 'https://github.com/hkoscielski/entrustments-system.git'
                sh 'chmod +x mvnw'
                VERSION = sh (script: './mvnw help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true)
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
    }
}
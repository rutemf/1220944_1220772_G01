pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking Out Repo...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }
}

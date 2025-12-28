pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking Out Repo...'
                checkout scm
            }
        }

        stage('Build Microservices') {
            steps {
                parallel(
                    "Auth Users": { build job: "books", wait: true },
                    "Books": { build job: 'books', wait: true },
                    "Genres": { build job: 'genres', wait: true },
                    "Readers": { build job: 'readers', wait: true }
                )
            }
        }

        stage('Build Docker Images') {
            steps {
                echo 'Building Docker Images...'
                sh 'docker build -t miguel04cardoso/auth-users-service:latest ./auth-users'
                sh 'docker build -t miguel04cardoso/books-service:latest ./books'
                sh 'docker build -t miguel04cardoso/readers-service:${BUILD_NUMBER} ./readers'
                sh 'docker tag miguel04cardoso/readers-service:${BUILD_NUMBER} miguel04cardoso/readers-service:canary'
            }
        }

        stage('Push to Docker Registry') {
          steps {
            withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
              sh 'docker login -u $USER -p $PASS'
              sh 'docker push miguel04cardoso/auth-users-service:latest'
              sh 'docker push miguel04cardoso/books-service:latest'
              sh 'docker push miguel04cardoso/readers-service:${BUILD_NUMBER}'
              sh 'docker push miguel04cardoso/readers-service:canary'
            }
          }
        }
    }
}

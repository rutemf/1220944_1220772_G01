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
                    "Auth Users": { build job: "auth-users", wait: true },
                    "Authors": { build job: "authors", wait: true },
                    "Books": { build job: 'books', wait: true },
                    "Genres": { build job: 'genres', wait: true },
                    "Readers": { build job: 'readers', wait: true }
                )
            }
        }

        stage('Build Docker Images') {
            steps {
                parallel(
                    "Auth Users Image": {
                        sh 'docker build -t miguel04cardoso/auth-users-service:latest ./auth-users'
                    },
                    "Authors Image": {
                        sh 'docker build -t miguel04cardoso/authors-service:latest ./authors'
                    },
                    "Books Image": {
                        sh 'docker build -t miguel04cardoso/books-service:latest ./books'
                    },
                    "Genres Image": {
                        sh 'docker build -t miguel04cardoso/genres-service:latest ./genres'
                    },
                    "Readers Image": {
                        sh 'docker build -t miguel04cardoso/readers-service:${BUILD_NUMBER} ./readers'
                        sh 'docker tag miguel04cardoso/readers-service:${BUILD_NUMBER} miguel04cardoso/readers-service:canary'
                    }
                )
            }
        }

        stage('Push to Docker Registry') {
          steps {
            withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
              sh 'docker login -u $USER -p $PASS'
              sh 'docker push miguel04cardoso/auth-users-service:latest'
              sh 'docker push miguel04cardoso/authors-service:latest'
              sh 'docker push miguel04cardoso/books-service:latest'
              sh 'docker push miguel04cardoso/genres-service:latest'
              sh 'docker push miguel04cardoso/readers-service:${BUILD_NUMBER}'
              sh 'docker push miguel04cardoso/readers-service:canary'
            }
          }
        }
    }
}

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking Out Repo...'
                checkout scm
            }
        }

        stage('Build Microservices - Dev') {
            steps {
                when {
                    branch 'dev'
                }
                parallel(
                    "Auth Users": { build job: "books/dev", wait: true },
                    "Books": { build job: 'books/dev', wait: true },
                    "Genres": { build job: 'genres/dev', wait: true },
                    "Readers": { build job: 'readers/dev', wait: true }
                )
            }
        }

        stage('Build Microservices - Staging') {
            steps {
                when {
                    branch 'staging'
                }
                parallel(
                    "Auth Users": { build job: "books/staging", wait: true },
                    "Books": { build job: 'books/staging', wait: true },
                    "Genres": { build job: 'genres/staging', wait: true },
                    "Readers": { build job: 'readers/staging', wait: true }
                )
            }
        }

        stage('Build Microservices - Prod') {
            steps {
                when {
                    branch 'prod'
                }
                parallel(
                    "Auth Users": { build job: "books/prod", wait: true },
                    "Books": { build job: 'books/prod', wait: true },
                    "Genres": { build job: 'genres/prod', wait: true },
                    "Readers": { build job: 'readers/prod', wait: true }
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

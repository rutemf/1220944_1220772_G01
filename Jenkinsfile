pipeline {
    agent any

    environment {
        DOCKER_USER = "miguel04cardoso"
    }

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
                        sh 'docker build -t ${DOCKER_USER}/auth-users-service:latest ./auth-users'
                    },
                    "Authors Image": {
                        sh 'docker build -t ${DOCKER_USER}/authors-service:latest ./authors'
                    },
                    "Books Image": {
                        sh 'docker build -t ${DOCKER_USER}/books-service:latest ./books'
                    },
                    "Genres Image": {
                        sh 'docker build -t ${DOCKER_USER}/genres-service:latest ./genres'
                    },
                    "Readers Image": {
                        sh 'docker build -t ${DOCKER_USER}/readers-service:latest ./readers'
                        sh 'docker tag ${DOCKER_USER}/readers-service:latest ${DOCKER_USER}/readers-service:canary'
                    }
                )
            }
        }

        stage('Push to Docker Registry') {
          steps {
            withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
              sh 'docker login -u $USER -p $PASS'
              sh 'docker push ${DOCKER_USER}/auth-users-service:latest'
              sh 'docker push ${DOCKER_USER}/authors-service:latest'
              sh 'docker push ${DOCKER_USER}/books-service:latest'
              sh 'docker push ${DOCKER_USER}/genres-service:latest'
              sh 'docker push ${DOCKER_USER}/readers-service:latest'
              sh 'docker push ${DOCKER_USER}/readers-service:canary'
            }
          }
        }

        stage('Deploy Stack (Swarm)') {
            steps {
                sh '''
                  docker stack deploy \
                    -c docker-compose.yml \
                    library-management-system
                '''
            }
        }
    }
}

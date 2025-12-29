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

        stage('Execute Microservices Pipelines') {
            steps {
               build job: "auth-users", wait: true
               build job: "authors", wait: true
               build job: "books", wait: true
               build job: "genres", wait: true
               build job: "readers", wait: true
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

        stage('Deploy Stack (Docker Swarm)') {
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

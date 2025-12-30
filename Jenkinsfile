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

pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking Out Repo...'
                checkout scm
            }
        }

        stage('Detect Changes On Microservices') {
            steps {
                script {
                    def services = [
                        'auth-users',
                        'authors',
                        'books',
                        'genres',
                        'readers'
                    ]

                    for (service in services) {

                        def changed = sh(
                            script: """
                                git diff --name-only HEAD~1...HEAD | grep "^${service}/"
                            """,
                            returnStatus: true
                        ) == 0

                        if (changed) {
                            echo "Changes Detected In ${service}, Triggering Pipeline..."
                            build job: service, wait: true, parameters: [string(name: 'BRANCH', value: env.BRANCH_NAME)]
                        } else {
                            echo "No Changes Detected In ${service}."
                        }
                    }
                }
            }
        }

        stage('Deploy Stack (Docker Swarm)') {
            steps {
                script {
                    def composeFile = "docker-compose.yml"

                    if (env.BRANCH_NAME == 'prod') {
                        composeFile = "docker-compose-prod.yml"
                    }

                    sh """
                        docker stack deploy \
                          -c ${composeFile} \
                          library-management-system
                    """
                }
            }
        }
    }
}

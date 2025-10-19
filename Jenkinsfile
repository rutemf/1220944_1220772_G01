pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking Out Repo...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Validating...'
                sh 'mvn validate'

                echo 'Building...'
                sh 'mvn clean compile'

                echo 'Static Code Analysis...'
                sh 'mvn -B spotbugs:spotbugs spotbugs:check -DskipTests'
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Unit Testing...'
                sh 'mvn test'

                echo 'Code Coverage...'
                sh 'mvn jacoco:report'
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage Report'
                ])

                echo 'Mutation Testing...'
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'

                echo 'Reporting Results...'
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/pit-reports',
                    reportFiles: '**/index.html',
                    reportName: 'PITest Mutation Report'
                ])
            }
        }

        stage ('Integration Test') {
            steps {
                echo 'Integration Testing...'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker Image...'
                sh 'docker build -t psoft-g1-app:latest .'
            }
        }

        stage('Deploy Locally') {
            when {
                branch 'dev'
            }
            steps {
                echo 'Deploying Dev Container...'
            }
        }

        stage('Deploy to Oracle - Staging') {
            when {
                branch 'staging'
            }
            environment {
                CONTAINER_NAME = "psoft-g1-staging"
                HOST_PORT = "7746"
                CONTAINER_PORT = "4677"
            }
            steps {
                echo 'Deploying Staging Container...'

                sh '''
                    if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
                        docker stop $CONTAINER_NAME || true
                        docker rm $CONTAINER_NAME || true
                    fi

                    docker run -d \
                        --name $CONTAINER_NAME \
                        -p $HOST_PORT:$CONTAINER_PORT \
                        psoft-g1-app:latest
                '''
            }
        }

        stage('Deploy to Oracle - prod') {
            when {
                branch 'prod'
            }
            environment {
                CONTAINER_NAME = "psoft-g1-prod"
                HOST_PORT = "4677"
                CONTAINER_PORT = "4677"
            }
            steps {
                echo 'Deploying Prod Container...'

                sh '''
                    if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
                        docker stop $CONTAINER_NAME || true
                        docker rm $CONTAINER_NAME || true
                    fi

                    docker run -d \
                        --name $CONTAINER_NAME \
                        -p $HOST_PORT:$CONTAINER_PORT \
                        psoft-g1-app:latest
                '''
            }
        }
    }
}

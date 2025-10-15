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

                echo 'Mutation Testing...'
                sh 'mvn org.pitest:pitest-maven:mutationCoverage'

                echo 'Reporting Results...'
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

        stage('Merge Dev to Staging') {
            when {
                branch 'dev'
            }
            steps {
                echo 'Merging dev into staging...'
                withCredentials([usernamePassword(
                    credentialsId: 'github-token',
                    usernameVariable: 'GIT_USER',
                    passwordVariable: 'GIT_TOKEN'
                )]) {
                    sh '''
                        set -eu
                        git config user.email "jenkins@odsoft-g1.com"
                        git config user.name "Jenkins CI"
                        git fetch --unshallow || true
                        git fetch --prune origin +refs/heads/*:refs/remotes/origin/*
                        git show-ref --verify --quiet refs/remotes/origin/dev || { echo "origin/dev missing"; exit 1; }
                        if git show-ref --verify --quiet refs/remotes/origin/staging; then
                          git checkout -B staging origin/staging
                        else
                          git checkout -B staging origin/dev
                        fi
                        git merge --no-ff origin/dev -m "Automated merge from dev to staging by Jenkins."
                        git push https://$GIT_USER:$GIT_TOKEN@github.com/rutemf/1220944_1220772_G01.git staging
                    '''
                }
            }
        }

        stage('Deploy Locally') {
            when {
                branch 'staging'
            }
            steps {
                echo 'Deploying...'
            }
        }

        stage('Deploy to Oracle') {
            when {
                branch 'prod'
            }
            environment {
                APP_NAME = "psoft-g1-app"
                IMAGE_NAME = "psoft-g1-app:latest"
                CONTAINER_NAME = "psoft-g1-container"
                EXPOSE_PORT = "4677"
            }
            steps {
                echo 'Deploying to Oracle VM...'

                sh 'docker build -t $IMAGE_NAME .'

                sh '''
                    if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
                        echo "Stopping old container..."
                        docker stop $CONTAINER_NAME || true
                        docker rm $CONTAINER_NAME || true
                    fi
                '''

                sh '''
                    echo "Starting new container..."
                    docker run -d \
                        --name $CONTAINER_NAME \
                        -p 4677:4677 \
                        $IMAGE_NAME
                '''

                echo '✅ Deploy completed successfully!'
            }
        }
    }
}

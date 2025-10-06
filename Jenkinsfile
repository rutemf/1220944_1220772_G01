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
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Unit Testing...'
                sh 'mvn test'

                echo 'Code Coverage...'

                echo 'Mutation Testing...'

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
                sh '''
                    git config user.email "jenkins@odsoft-g1.com"
                    git config user.name "Jenkins CI"
                    git checkout staging
                    git pull origin staging
                    git merge --no-ff dev -m "Automated Merge from dev to staging by Jenkins."
                    git push origin staging
                '''
            }
        }

        stage('Deploy to DEI') {
            when {
                branch 'staging'
            }
            steps {
                echo 'Deploying to DEI...'
            }
        }

        stage('Deploy to Oracle') {
            when {
                branch 'prod'
            }
            steps {
                echo 'Deploying to Oracle...'
            }
        }
    }
}

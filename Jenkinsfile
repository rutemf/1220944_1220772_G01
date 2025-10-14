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
                        git config user.email "jenkins@odsoft-g1.com"
                        git config user.name "Jenkins CI"
                        git fetch --all
                        git checkout staging || git checkout -b staging dev
                        git push -u origin staging
                        git merge --no-ff dev -m "Automated Merge from dev to staging by Jenkins."
                        git push origin staging
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
            steps {
                echo 'Deploying to Oracle...'
            }
        }
    }
}

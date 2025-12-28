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
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site',
                    reportFiles: 'spotbugs.html',
                    reportName: 'SpotBugs Report'
                ])
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Unit Testing...'
                sh 'mvn test'

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
                sh 'mvn verify -DskipUnitTests'

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
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging...'
                sh 'mvn package -DskipTests'
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

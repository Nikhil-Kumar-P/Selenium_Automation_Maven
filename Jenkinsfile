pipeline {
    agent {
        docker {
            image 'maven:3.8.1-openjdk-11'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    environment {
        DISPLAY = ':99' // For running Selenium tests with Xvfb
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Start Xvfb for headless browser testing
                sh 'Xvfb :99 -ac &'
                // Run your Selenium tests
                sh 'mvn test'
            }
        }
    }
    post {
        always {
            // Cleanup actions, if necessary
            sh 'pkill Xvfb || true'
        }
        success {
            echo 'Build and tests were successful!'
        }
        failure {
            echo 'Build or tests failed.'
        }
    }
}

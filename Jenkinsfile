pipeline {
    agent any // Use 'any' agent type to run directly on Jenkins server

    environment {
        DISPLAY = ':99' // For running Selenium tests with Xvfb
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm // Checkout the source code from your version control system
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install' // Build the Maven project
            }
        }
        stage('Test') {
            steps {
                // Start Xvfb for headless browser testing
                sh 'Xvfb :99 -ac &'
                // Run your Selenium tests or other tests
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            // Cleanup actions, if necessary
            sh 'pkill Xvfb || true' // Kill Xvfb process after tests
        }
        success {
            echo 'Build and tests were successful!'
        }
        failure {
            echo 'Build or tests failed.'
        }
    }
}

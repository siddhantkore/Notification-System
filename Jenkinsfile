
pipeline {

    agent any

    environment {

        // Define the name of Docker image
        DOCKER_IMAGE_NAME = "notification-service"

        // Get the current Git branch name
        BRANCH_NAME = "${env.GIT_BRANCH}"

        // Set the Docker image tag based on branch or commit hash
        DOCKER_IMAGE_TAG = "${BRANCH_NAME == 'main' ? 'latest' : env.GIT_COMMIT[0..7]}"

    }

    stages {

        stage('Checkout') {
            steps {
                script {

                    // Check out the SCM (Source Code Management) repository
                    checkout scm
                    echo "Checked out branch: ${BRANCH_NAME}"
                }
            }
        }

        stage('Build Spring Boot App & Docker Image') {
            steps {
                script {
                    echo "Building Spring Boot application and Docker image..."
                    docker.build("${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}", "-f Dockerfile .")
                    echo "Docker image built: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                }
            }
        }

        stage('Run Development Environment (Main Branch Only)') {

//            when {
//                branch 'main' // This stage only runs for the 'main' branch
//            }
            steps {
                script {
                    echo "Running Docker Compose for development environment..."

                    sh 'docker-compose -f docker-compose.yml up -d --build --force-recreate'

                    echo "Waiting for services to become healthy (might take a few minutes)..."
                    sleep 60
                    sh 'docker ps'
                    
                    echo "Development environment brought up. You can access services via specified ports locally."
                    echo "Remember to clean up services after the pipeline run if they are not meant to persist."

                }
            }
        }
    }

    post {
        always {
            script {

                if ("${BRANCH_NAME}" == "main") {
                    echo "Cleaning up Docker Compose services..."
                    sh 'docker-compose -f docker-compose.yml down'
                    echo "Docker Compose services cleaned up."
                }

                echo "Cleaning up local Docker image: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                sh 'docker-compose -f docker-compose.yml down'

                try {
                    docker.image("${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}").remove()
                } catch (Exception e) {
                    echo "Failed to remove image (might not exist or in use): ${e.getMessage()}"
                }
            }
        }
        failure {
            echo "Pipeline failed! Please check logs."
            script {

                emailext (
                    to: 'megastorage2112@gmail.com, kore7447@gmail.com',
                    subject: "Jenkins Build Success: ${env.JOB_NAME} - #${env.BUILD_NUMBER}",
                    body: """
                        <p>Build ${env.JOB_NAME} - #${env.BUILD_NUMBER} is SUCCESSFUL!</p>
                        <p>Branch: ${env.BRANCH_NAME}</p>
                        <p>Console Output: <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                    """,
                    mimeType: 'text/html'
                )
            }

        }
        success {
            echo "Pipeline completed successfully!"

            script {
                emailext (
                    to: 'megastorage2112@gmail.com, kore7447@gmail.com',
                    subject: "Jenkins Build FAILURE: ${env.JOB_NAME} - #${env.BUILD_NUMBER}",
                    body: """
                        <p style="color:red">Build ${env.JOB_NAME} - #${env.BUILD_NUMBER} has FAILED!</p>
                        <p>Branch: ${env.BRANCH_NAME}</p>
                        <p>Console Output: <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                        <p>Check the build logs for details.</p>
                    """,
                    mimeType: 'text/html'
                )
            }
        }
    }
}
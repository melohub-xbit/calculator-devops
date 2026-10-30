pipeline {
    agent any

    environment {
        IMAGE_NAME = 'mysticmoose/calculator'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
    }

    options {
        skipDefaultCheckout()
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .'
            }
        }

        stage('Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub',
                        usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $IMAGE_NAME:$IMAGE_TAG
                        docker push $IMAGE_NAME:latest
                    '''
                }
            }
            post {
                always {
                    sh 'docker logout'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose build nginx'
                // --wait blocks until the healthchecks pass
                sh 'docker compose up -d --wait --wait-timeout 120'
                sh 'docker compose ps'
                // call the app through nginx to check the whole path works
                sh 'docker compose exec -T nginx wget -qO- http://localhost/actuator/health'
                sh 'docker compose exec -T nginx wget -qO- "http://localhost/api/v1/add?a=2&b=3"'
            }
        }
    }

    post {
        success {
            mail to: "${env.NOTIFY_EMAIL}",
                 subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build passed and image ${env.IMAGE_NAME}:${env.IMAGE_TAG} was deployed.\n\nDetails: ${env.BUILD_URL}"
        }
        failure {
            mail to: "${env.NOTIFY_EMAIL}",
                 subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: "Build failed. Check the console output:\n${env.BUILD_URL}console"
        }
        always {
            sh 'docker image prune -f'
        }
    }
}

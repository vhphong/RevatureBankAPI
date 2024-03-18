pipeline {
    agent any

    tools {
        maven 'maven_3_8_8'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Stage of Checkout starting..'
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: 'bitbucket_credentials', url: 'https://bitbucket.org/phongvo1/bankapi-backend/src/master/']])
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Stage of Build and Test starting..'
                bat 'dir'
                // build the project and create a JAR file
                bat 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_URL = 'http://localhost:9000'
            }
            steps {
                echo 'Stage of SonarQube Analysis starting..'
				bat "mvn clean verify sonar:sonar \
						-Dsonar.projectKey=BankAPI-backend \
						-Dsonar.projectName='BankAPI-backend' \
						-Dsonar.host.url=http://localhost:9000 \
						-Dsonar.token=sqp_a88fc863c40bf78d230f1120e4f3275dc6886402"
            }
        }

        stage('Build Docker image') {
            steps {
                script {
                    echo 'Stage of Build Docker image starting..'
                    bat 'docker build -t phongvo0222/bank-api .'
                }
            }
        }

        stage('Push image to DockerHub') {
            steps {
                script {
                    echo 'Stage of Push image to DockerHub starting..'
                    echo 'Ensure Docker is logged in..'
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        bat "docker login -u phongvo0222 -p ${dockerhubpwd}"
                    }
                    echo 'Push the tagged image..'
                    bat "docker push phongvo0222/bank-api"
                }
            }
        }
    }
}
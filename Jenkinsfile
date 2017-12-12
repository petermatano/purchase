pipeline {
    agent any

    node {
        checkout scm
    }

    stages {
        stage('Build') {
            steps {
                sh '''
                    mvn -B -DskipTests clean package
                    make build
                '''
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                  junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    name="purchase-deployment"
                    kubectl create -f ${name}.yaml
                    kubectl expose deployment ${name} --type=NodePort
                    minikube service ${name} --url
                '''
            }
        }
    }
}
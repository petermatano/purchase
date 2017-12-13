pipeline {
    agent {
        docker { image 'maven:3-alpine' }
    }
    stages {
        stage('Build') {
            steps {
                sh 'make build'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Test') {
            steps {
                sh 'make check || true'
                junit '**/target/*.xml'
            }
        }
        stage('Deploy') {
            when {
              expression {
                currentBuild.result == null || currentBuild.result == 'SUCCESS'
              }
            }
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
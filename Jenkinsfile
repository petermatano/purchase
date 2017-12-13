pipeline {
    agent { docker 'maven:3.3.3' }
    stages {
        stage('Build') {
            steps {
                sh 'make'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Test') {
                sh 'make check || true'
                junit '**/target/*.xml'
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
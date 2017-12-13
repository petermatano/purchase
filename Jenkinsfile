pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'docker build --no-cache -t $(squaretrade/purchase):$(0.0.1) --rm'
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
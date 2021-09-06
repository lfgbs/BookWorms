pipeline {
    agent any

    stages {

        stage('Quality Gate Check') {
         agent any
            steps {
                script{
                    withSonarQubeEnv('SonarQube'){
                    sh "mvn clean verify sonar:sonar -Dsonar.login=c3e5e05fcd63afada79ba8fabc39c2004d503d9f"
                    }
                    timeout(time: 1, unit: 'HOURS'){
                    def qg = waitForQualityGate()
                        if (qg.status!='OK'){
                        error "Pipeline aborted: ${qg.status}"
                        }

                    }
                    sh "mvn clean install"
                }
            }
        }

    }
}
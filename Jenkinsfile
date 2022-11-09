pipeline {
    agent any
   
    stages{
        stage('Compile-package'){
            steps{
                script{
                    sh 'mvn package'
                }
            }
        }
        stage('Sonarqube'){
            steps{
                script{
                    jacoco()
                    def mvnHome = tool name: 'maven', type: 'maven'
                    withSonarQubeEnv('sonarqube'){
                        sh "${mvnHome}/bin/mvn verify sonar:sonar"
                    }
                }
            }
        }
        stage("Quality gate") {
            steps {
                script{
                sleep(10)
              timeout(time: 1, unit: 'HOURS') {
                def qg = waitForQualityGate()
                if(qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
                }
              }
            }
          }
        stage("Nexus"){
            steps{
                script{
                     nexusPublisher nexusInstanceId: 'nexus', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: './target/achat-1.0.jar']],mavenCoordinate: [artifactId: 'achat', groupId: 'tn.esprit.rh', packaging: 'jar', version: '1']]]
                }
            }
        }
       
        stage('Docker Image'){
                    steps{
                        script{
                            echo "deploying the application"
                            withCredentials([usernamePassword(credentialsId:'dockerhub',usernameVariable:'USER',passwordVariable:'PWD')]) {
                                sh "echo $PWD | docker login -u $USER --password-stdin"
                                sh "docker build -t wissemjerbi/spring-app:1.0 ."
                                sh "docker push wissemjerbi/spring-app:1.0"

                        }
                    }
                }
            }
    }
}

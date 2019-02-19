pipeline {
  	environment {
    		registry = "mrom42/calculator"
    		registryCredential = 'dockerhub'
    		dockerImage = ''
  	}
	agent any
	stages {
		stage("Compile") {
			steps {
				sh "./gradlew compileJava"
			}
		}
		stage("Unit test") {
			steps {
				sh "./gradlew test"
			}
		}
		stage("Code coverage") {
			steps {
				sh "./gradlew jacocoTestReport"
				publishHTML (target: [
					reportDir: 'build/reports/jacoco/test/html',
					reportFiles: 'index.html',
					reportName: "JaCoCo Report"
				])
				sh "./gradlew jacocoTestCoverageVerification"
			}
		}
		stage("Static code analysis") {
			steps {
				sh "./gradlew checkstyleMain"
			}
		}		
		stage("build on brik") {
			agent { node { label 'brik' } } 
			steps {
				sh "./gradlew build"
			}
		}
  		stage('Building image') {
			agent { node { label 'brik' } }
      			steps{
        			script {
          				dockerImage = docker.build registry + ":$BUILD_NUMBER"
       			 	}
      			}
    		}
    		stage('Deploy Image') {
			agent { node { label 'brik' } }
      			steps{
         			script {
            				docker.withRegistry( '', registryCredential ) {
            					dockerImage.push()
          				}
        			}
      			}
    		}
    		stage('Remove Unused docker image') {
			agent { node { label 'brik' } }
      			steps{
        			sh "docker rmi $registry:$BUILD_NUMBER"
      			}
    		}  
                stage('Deploy to stageing') {
                        agent { node { label 'brik' } }
                        steps{
                                sh "docker run -d -rm -p 192.168.56.103:8080:8080 --name calculator $registry:$BUILD_NUMBER"
                        }
                }
                stage('stop stageing') {
                        agent { node { label 'brik' } }
                        steps{
                                sh "docker stop calculator"
                        }
                }
	}
}

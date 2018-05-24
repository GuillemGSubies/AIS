node {
	checkout scm

	pipeline {
    	tools {
        	maven "M3"
    	}
    	agent any
    	stages {
       		stage("Preparation") {
            	steps {
                	git 'https://github.com/GuillemGSubies/AIS.git'
            	}
       		}
       		stage("Test") {
          		steps {
            		script {
                		if(isUnix()) {
                    		sh "mvn test"
                		} else {
                    		bat(/${M2_HOME}\bin\mvn -f pom.xml test/)
                		}
            		}
          		}
        	}
     	}
     	post {
        	always {
            	junit "./**/target/surefire-reports/TEST-*.xml"
        	}
     	}
  	}
}
pipeline {
  agent any

  stages {

    stage('Fail Fast'){

        when { 
          branch 'spike/*' 
        }

        steps {
          sh './gradlew -w test'
        }
    }

    stage('Build'){
      steps {    
        echo 'Building...'
        sh './gradlew -w assemble'
      }
    }

    stage('Quality'){
      environment{
        scannerHome = tool 'sonarScanner'
      }

      steps{
        withSonarQubeEnv('SonarQube'){
          sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=weather-app -Dsonar.sources=. -Dsonar.java.binaries=."
        }

        timeout(time: 10, unit: 'MINUTES'){
          waitForQualityGate abortPipeline: true
        }
      }
    }

    stage('Test'){
      steps {  
        sh './gradlew -w check'
      }
    }

    stage('Deploy'){

      when { 
        allOf {
          branch 'master'
        }
      }

      input {
          message "Should we deploy?"
          ok "Yes, we should."
          submitter "alice,bob"
          parameters {
              string(name: 'CONTINUE', defaultValue: 'no')
          }
      }

      steps {
        script {
            if(${CONTINUE} == "yes"){
                echo 'Deploying...'
            } else {
                echo 'Skipping deployment'
            }
        }
      }
    }
  }
}

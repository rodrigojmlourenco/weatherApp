pipeline {
  agent any

  stages {

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

      when { branch 'master' }

      input {
          message "Should we continue?"
          ok "Yes, we should."
          submitter "alice,bob"
          parameters {
              string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
          }
      }

      steps {
        echo 'Deploying...'
        echo "Hello, ${PERSON}, nice to meet you."
      }
    }
  }

}

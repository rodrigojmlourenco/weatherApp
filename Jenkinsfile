pipeline {
  agent any

  stages {

    stage('Warm Up'){
      steps {
        sh './gradlew tasks'
      }
    }

    stage('Build'){
      steps {    
        echo 'Building...'
        sh './gradlew -w build'
      }
    }

    stage('Test'){
      steps {
        sh './gradlew -w check'
      }
    }

    stage('Deploy'){
      steps {
        echo 'Deploying...'
        sh './gradlew -w check'
      }
    }
  }
  
}

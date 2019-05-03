pipeline {
  agent any

  triggers {
    pollSCM('*/5 * * * *')
  }

  stages {

    stage('Warm Up'){
      steps {
        sh './gradlew tasks'
      }
    }

    stage('Build'){
      steps {    
        echo 'Building...'
        sh './gradlew build'
      }
    }

    stage('Test'){
      steps {
        sh './gradlew check'
      }
    }

    stage('Deploy'){
      steps {
        echo 'Deploying...'
        sh './gradlew check'
      }
    }
  }
}

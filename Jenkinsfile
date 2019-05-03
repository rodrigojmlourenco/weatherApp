pipeline {
  agent any

  stages {
    stage('Build'){
      steps {
        echo 'Building...'
        sh 'ls'
        sh './gradlew build'
      }
    }

    stage('Test'){
      steps {
        echo 'Testing...'
        sh 'gradle check'
      }
    }

    stage('Deploy'){
      steps {
        echo 'Deploying...'
      }
    }
  }
}

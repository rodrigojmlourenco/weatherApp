pipeline {
  agent any

  stages {
    stage('Build'){
      steps {
        echo 'Building...'
        sh 'ls'    
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

pipeline {
  agent any

  stages {

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

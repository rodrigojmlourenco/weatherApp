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
      when {
        branch 'master'
      }
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
      when {
        branch 'master'
      }
      steps {
        echo 'Deploying...'
        sh './gradlew -w check'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true
    }
  }
}

#!/usr/bin/env groovy

node('docker') {
    stage 'Cleanup workspace'
    deleteDir()

    stage 'Checkout source'
    checkout scm

    stage 'Build and test'
    docker.image('java:8-jdk').inside {
        timeout(30) {
            sh './gradlew -iS'
        }
    }

    stage 'Capture test results and artifacts'
    junit 'build/test-results/**/*.xml'
    archiveArtifacts artifacts: 'build/libs/*.jar,build/distributions/*.zip', fingerprint: true
}

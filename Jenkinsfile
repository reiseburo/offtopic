node {
    stage 'Clean workspace'
    /* Running on a fresh Docker instance makes this redundant, but just in
     * case the host isn't configured to give us a new Docker image for every
     * build, make sure we clean things before we do anything
     */
    deleteDir()


    stage 'Checkout source'
    checkout scm


    stage 'Build and test'
    /* if we can't install everything we need for Ruby in less than 15 minutes
     * we might as well just give up
     */
    timeout(30) {
        sh './gradlew -iS'
    }

    stage 'Capture test results and artifacts'
    step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/*.xml'])
    step([$class: 'ArtifactArchiver', artifacts: 'build/libs/*.jar,build/distributions/*.zip', fingerprint: true])
}

// vim: ft=groovy

pipeline {
agent any

```
tools {
    jdk 'JDK-21'
    maven 'Maven-3'
}

stages {
    stage('Checkout') {
        steps {
            checkout scm
        }
    }

    stage('Build and API Tests') {
        steps {
            sh 'mvn -B clean test -Dapi.base.url=http://host.docker.internal:8080'
        }
    }
}

post {
    always {
        junit testResults: 'target/surefire-reports/TEST-*.xml',
              allowEmptyResults: false
    }
}
```

}

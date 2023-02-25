pipeline {
    agent any
    tools {
        maven 'maven3.9.0'
        jdk 'openjdk-19.0.2'
    }
    stages {
        stage('Build all') {
            steps {
               sh 'mvn -DskipTests clean package'
            }
        }
        stage('Unit Tests') {
            steps {
               sh 'mvn clean test'
            }
        }
    }
}

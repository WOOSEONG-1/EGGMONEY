pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-credentials')
        MATTERMOST_ENDPOINT = 'https://meeting.ssafy.com/hooks/o4ew547m77rqt873m9j4n3f43a'
        MATTERMOST_CHANNEL = 'Jenkins'
        BACKEND_IMAGE = 'soyo/eggmoney_back'
        FRONTEND_IMAGE = 'soyo/eggmoney_front'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }

    stages {
        stage('Start Notification') {
            steps {
                script {
                    sendNotification('warning', '젠킨스 시작')
                }
            }
        }

        stage('Checkout') {
            steps {
                git url: 'https://lab.ssafy.com/s11-fintech-finance-sub1/S11P21C204.git', branch: 'develop', credentialsId: 'gitlab'
            }
        }

        stage('Build Backend') {
            when {
                changeset "**/backend/**"
            }
            steps {
    
                    buildBackend()

            }
        }

        stage('Build Backend Docker Image') {
            when {
                changeset "**/backend/**"
            }
            steps {
     
                    buildDockerImage('backend', BACKEND_IMAGE)
                
            }
        }

        stage('Push Backend Docker Image') {
            when {
                changeset "**/backend/**"
            }
            steps {
     
                    pushDockerImage(BACKEND_IMAGE)
                    deployBackend()
                
            }
        }

        stage('Build Frontend Docker Image') {
            when {
                changeset "**/frontend/**"
            }
            steps {
    
                    buildDockerImage('frontend', FRONTEND_IMAGE)
                
            }
        }

        stage('Push Frontend Docker Image') {
            when {
                changeset "**/frontend/**"
            }
            steps {
               
                    pushDockerImage(FRONTEND_IMAGE)
                    deployFrontend()
                
            }
        }
    }

    post {
        success {
            node {
                script {
                    sendNotification('good', '빌드 성공')
                    cleanWs()
                }
            }
        }
        failure {
            node {
                script {
                    sendNotification('danger', '빌드 실패')
                    cleanWs()
                }
            }
        }
    }
}

def sendNotification(String color, String status) {
    def gitCommitterName = sh(script: "git log -1 --pretty=format:'%an'", returnStdout: true).trim()
    def gitCommitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()
    
    mattermostSend(
        color: color,
        message: """${status}: Egg Money 🐤🍀 #${env.BUILD_NUMBER}
        커밋 작성자 👨‍💻: ${gitCommitterName}
        커밋 메시지 📩: ${gitCommitMessage}
        (<${env.BUILD_URL}|Details>)""",
        endpoint: MATTERMOST_ENDPOINT,
        channel: MATTERMOST_CHANNEL
    )
}

def buildBackend() {
    dir('backend') {
        sh 'chmod +x ./gradlew'
        sh './gradlew clean build'
    }
}

def buildDockerImage(String dirPath, String imageName) {
    dir(dirPath) {
        sh "docker build --no-cache -t ${imageName} ."
    }
}

def pushDockerImage(String imageName) {
    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
    sh "docker push ${imageName}"
}

def deployBackend() {
    sh 'ssh deployuser@j11c204.p.ssafy.io "bash /home/deployuser/deploy_back.sh"'
}

def deployFrontend() {
    sh 'ssh deployuser@j11c204.p.ssafy.io "bash /home/deployuser/deploy_front.sh"'
}
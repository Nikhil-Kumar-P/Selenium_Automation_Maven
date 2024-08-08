node ('jenkins-slave') {

// Declaration of Constants and Variables
  def app_name = 'automation-testing'
  def registry_url = 'https://ldr.lifafa.team:5000'
  def registry_creds = 'dr_lifafa_team'
  def registry_host = 'ldr.lifafa.team:5000'
  def lifafa_script_path = '/vol/service-deployment/docker/lifafa/staging/deployment/tools/jenkins/jenkins-app-deploy.sh'

  def lifafa = [:]
    lifafa.name = "root"
    lifafa.host = "10.50.20.45"
    lifafa.user = "root"
    lifafa.password = "lifafa123"
    lifafa.allowAnyHosts = true
    lifafa.dir = "${app_name}"
    lifafa.image = "${registry_host}/${app_name}:"
    lifafa.imageid

  def tag
  def image
  def failedCount = 0
  def failedException = ""

// Checkout of latest code from Bit-bucket
  stage('Checkout') {
    echo "#######################################################################################"
    echo "================================ Fetching Updated Code ================================"
    echo "#######################################################################################"

    checkout scm
    sshagent (credentials: ['goomo-bitbucket']){
      sh 'git submodule update --init'
    }

    tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
    lifafa.imageid = lifafa.image+tag
  }

// Docker build of latest comitted Code
  stage('Build') {
    echo "#######################################################################################"
    echo "============================== Code is now getting Build =============================="
    echo "#######################################################################################"

    docker.withRegistry("${registry_url}","${registry_creds}") {
      image = docker.build("${app_name}:${tag}")
      }
  }

// Promotes built Docker Id to Docker Registry  
  stage('Promote') {
    echo "#######################################################################################"
    echo "================== New Docker Id is getting promoted to our Registry =================="
    echo "#######################################################################################"

    docker.withRegistry("${registry_url}","${registry_creds}"){
    docker.build("${app_name}:${tag}").push("${tag}")
    }
  }
  
// Exit Pipeline after Promoting Built Imgage to Docker Registry
//  if (isOnDevelop() || isOnMaster() || !isOnDevelop()) {
//    return;
//  }

  stage('Test') {
    echo "#######################################################################################"
    echo "======================= Hurray!!! Finally code is getting Tested ======================"
    echo "#######################################################################################"

  try {
    stage('Lifafa Deployment') {
      slackSend color: "#0827F5", message: "Started Deployment for ${app_name} in Lifafa Staging"
      sshCommand remote: lifafa, command: "docker run --rm ${lifafa.imageid}"
    }
    slackSend color: "#28A828", message: "Deployment Completed for ${app_name} in Lifafa Staging"
  } catch (e) {
    slackSend color: "#A82828", message: "Deployment Failed for ${app_name} in Lifafa Staging"
    // Since we're catching the exception in order to report on it,
    // we need to re-throw it, to ensure that the build is marked as failed
    // throw e
    failedCount++
    failedException = failedException + " ::  Exception On Lifafa Deployment :: " + e
  }   
}
  
 
  if (failedCount > 0) {
    throw new Exception(failedException)
  }
}
// Functions
def isOnDevelop() {
  return !env.BRANCH_NAME || env.BRANCH_NAME == 'develop';
}
def isOnMaster() {
  return !env.BRANCH_NAME || env.BRANCH_NAME == 'master';
}
/*
* Deploy an application to a single environment
*
* Jenkins Environment Variables:
*   AWS_KEYS
*   AWS_DEFAULT_REGION
*
* Jenkins Job Parameters:
*   gitUrl
*   gitBranch
*   gitCreds
*   terraformDir
*   terraformBucket
*   terraformKey
*   terraformApplyPlan
*/

node () {

if(pipelinename =='Infra-deploy')
{
  def terraformDir = "$WORKSPACE/FCII-Terraform-Jenkins/Infra-Deploy/"
  stage ('Checkout'){
    deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }
  stage('Set Terraform path') {
      script {
            def tfHome = tool name: 'Terraform'
            env.PATH = "${tfHome}:${env.PATH}"
              }
          sh 'terraform --version'
  }

  stage ('Terraform Initialize'){
    dir(terraformDir) {
    sh"pwd"
    sh "terraform init -backend=true -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }

  stage ('Terraform Plan'){
    dir(terraformDir) {
      sh"echo ${vpc_cidr}"
      sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var public_subnet_cidr_az1=${public_subnet_cidr_az1} -var private_subnet_cidr_az1_internal=${private_subnet_cidr_az1_internal} -var private_subnet_cidr_az2_internal=${private_subnet_cidr_az2_internal} "
    }
  }

  if (terraformApplyPlan == 'true') {

    stage ('Review Apply'){
    timeout(5) {
      input id: 'approve', message:'Apply ?', ok: 'Apply'
    }
  }
    stage ('Terraform Apply'){
      dir(terraformDir) {
        sh "terraform apply -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var public_subnet_cidr_az1=${public_subnet_cidr_az1} -var private_subnet_cidr_az1_internal=${private_subnet_cidr_az1_internal} -var private_subnet_cidr_az2_internal=${private_subnet_cidr_az2_internal} -auto-approve"
     }
    }
  }
}

/** Commenting Out Since not being used RiskMaster now
if(pipelinename =='Riskmaster-deploy')
{
  def terraformDir = "$WORKSPACE/FCII-Terraform-Jenkins/Riskmaster-Deploy/"
  stage ('Checkout'){
    deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }
  stage('Set Terraform path') {
      script {
            def tfHomev11 = tool name: 'Terraformv11'
            env.PATH = "${tfHomev11}:${env.PATH}"
              }
          sh 'terraform --version'
  }

  stage ('Terraform Initialize'){
    dir(terraformDir) {
    sh"pwd"
    sh "terraform init -backend=true -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }

  stage ('Terraform Plan'){
    dir(terraformDir) {
      //sh "terraform plan "
      sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var vpc_cidr2=${vpc_cidr2} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr}"
    }
  }

  if (terraformApplyPlan == 'true') {

    stage ('Review Apply'){
    //timeout(5) {
    //  input id: 'approve', message:'Apply ?', ok: 'Apply'
    //}
  }
    stage ('Terraform Apply'){
      dir(terraformDir) {
     //   sh "terraform apply"
     sh "terraform apply -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var vpc_cidr2=${vpc_cidr2} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr} -auto-approve"
     }
    }
  }
}
**/

if(pipelinename =='FCII-deploy')
{
  def terraformDir = "$WORKSPACE/FCII-Terraform-Jenkins/FCII-Deploy/"
  stage ('Checkout'){
    deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }
  stage('Set Terraform path') {
      script {
            def tfHomev11 = tool name: 'Terraformv11'
            env.PATH = "${tfHomev11}:${env.PATH}"
              }
          sh 'terraform --version'
  }

  stage ('Terraform Initialize'){
    dir(terraformDir) {
    sh"pwd"
    sh "terraform init -backend=true -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }

  stage ('Terraform Plan'){
    dir(terraformDir) {
      //sh "terraform plan "
      sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr}"
    }
  }

  if (terraformApplyPlan == 'true') {

    stage ('Review Apply'){
    timeout(5) {
      input id: 'approve', message:'Apply ?', ok: 'Apply'
    }
  }
    stage ('Terraform Apply'){
      dir(terraformDir) {
     //   sh "terraform apply"
     sh "terraform apply -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr} -auto-approve"
     }
    }
  }
}

/** Commenting Out Since not being used FCIICloudwatch_Dashboard ,workspace-deploy and FCII Deploy now
if(pipelinename =='FCIICloudwatch_Dashboard-deploy')
{
  def terraformDir = "$WORKSPACE/FCII-Terraform-Jenkins/FCII-Cloudwatch-Dashboard/"
  stage ('Checkout'){
    deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }
  stage('Set Terraform path') {
      script {
            def tfHomev11 = tool name: 'Terraformv11'
            env.PATH = "${tfHomev11}:${env.PATH}"
              }
          sh 'terraform --version'
  }

  stage ('Terraform Initialize'){
    dir(terraformDir) {
    sh"pwd"
    sh "terraform init -backend=true -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }

  stage ('Terraform Plan'){
    dir(terraformDir) {
      //sh "terraform plan "
      sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr}"
    }
  }

  if (terraformApplyPlan == 'true') {

    stage ('Review Apply'){
    timeout(5) {
      input id: 'approve', message:'Apply ?', ok: 'Apply'
    }
  }
    stage ('Terraform Apply'){
      dir(terraformDir) {
     //   sh "terraform apply"
     sh "terraform apply -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr} -auto-approve"
     }
    }
  }
}


if(pipelinename =='FCII-deploy-sandbox')
{
  def terraformDir = "$WORKSPACE/FCII-Terraform-Jenkins/FCII-Deploy-Sandbox/"
  stage ('Checkout'){
    deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }
  stage('Set Terraform path') {
      script {
            def tfHomev11 = tool name: 'Terraformv11'
            env.PATH = "${tfHomev11}:${env.PATH}"
              }
          sh 'terraform --version'
  }

  stage ('Terraform Initialize'){
    dir(terraformDir) {
    sh"pwd"
    sh "terraform init -backend=true -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }

  stage ('Terraform Plan'){
    dir(terraformDir) {
      //sh "terraform plan "
      sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr}"
    }
  }

  if (terraformApplyPlan == 'true') {

    stage ('Review Apply'){
    timeout(5) {
      input id: 'approve', message:'Apply ?', ok: 'Apply'
    }
  }
    stage ('Terraform Apply'){
      dir(terraformDir) {
     //   sh "terraform apply"
     sh "terraform apply -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var tag_Schedule=${tag_Schedule} -var tag_env=${tag_env} -var vpc_cidr=${vpc_cidr} -var vpc_mgt_cidr=${vpc_mgt_cidr} -auto-approve"
     }
    }
  }
}


if(pipelinename =='workspace-deploy')
{
def terraformDir = "$WORKSPACE/FCII-Terraform-Jenkins/Workspaces-Deploy/"
  stage ('Checkout'){
    deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }
  stage('Set Terraform path') {
      script {
            def tfHomev11 = tool name: 'Terraformv11'
            env.PATH = "${tfHomev11}:${env.PATH}"
              }
          sh 'terraform --version'
  }

  stage ('Terraform Initialize'){
    dir(terraformDir) {
    sh"pwd"
    sh "terraform init -backend=true -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }

  stage ('Terraform Plan'){
    dir(terraformDir) {
      //sh "terraform plan "
      sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr}  -var UserName=${UserName} -var GivenName=${GivenName} -var SurName=${SurName} -var vpc_mgt_cidr=${vpc_mgt_cidr} -var EmailAddress=${EmailAddress} -var ActiveDirectoryID=${ActiveDirectoryID} -var BundleID=${BundleID}"
    }
  }

  if (terraformApplyPlan == 'true') {

    stage ('Review Apply'){
    timeout(5) {
      input id: 'approve', message:'Apply ?', ok: 'Apply'
    }
  }
    stage ('Terraform Apply'){
      dir(terraformDir) {
     //   sh "terraform apply"
     //sh "terraform plan -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr} -var vpc_cidr2=${vpc_cidr2} -var UserName=${UserName} -var GivenName=${GivenName} -var Surname=${Surname} -var vpc_mgt_cidr=${vpc_mgt_cidr} -var EmailAddress=${EmailAddress}"
     sh "terraform apply -var demonumber=${demonumber} -var vpc_cidr=${vpc_cidr}  -var UserName=${UserName} -var GivenName=${GivenName} -var SurName=${SurName} -var vpc_mgt_cidr=${vpc_mgt_cidr} -var EmailAddress=${EmailAddress} -var ActiveDirectoryID=${ActiveDirectoryID} -var BundleID=${BundleID} -auto-approve"
     }
    }
  }

}
**/

}

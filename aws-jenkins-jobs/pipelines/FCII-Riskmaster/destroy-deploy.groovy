/*
* Deploy an application to a single environment
*
* Jenkins Environment Variables:
*   AWS_MANAGEMENT_PROXY
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

node ('deploy-client') {

  
  stage ('Checkout'){
    //deleteDir()
    checkout([$class: 'GitSCM', branches: [[name: gitBranch]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: true, recursiveSubmodules: true, reference: '', trackingSubmodules: false]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: gitCreds, url: gitUrl]]])
  }

  stage ('Remote State'){
    dir(terraformDir) {
      sh "terraform remote config -backend=s3 -backend-config='bucket=${terraformBucket}' -backend-config='key=${terraformKey}'"
    }
  }
  
  stage ('Plan Destroy'){
    dir(terraformDir) {
      sh 'terraform get --update'
      sh 'terraform plan -destroy'
    }
  }
  stage ('Review Destroy'){
    timeout(5) {
      input id: 'approve', message:'Destroy ?', ok: 'Destroy'
    }
  }

  stage ('Destroy'){
    dir(terraformDir) {
      sh "terraform get --update"
      sh "terraform destroy -force"
    }
  }


}

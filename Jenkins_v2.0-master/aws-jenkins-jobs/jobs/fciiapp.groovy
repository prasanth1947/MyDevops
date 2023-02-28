import org.jenkinsci.plugins.scriptsecurity.scripts.*
toApprove = ScriptApproval.get().getPendingScripts().collect()
toApprove.each {pending -> ScriptApproval.get().approveScript(pending.getHash())}

def gitCreds           = 'fde4ec2e-eabc-433a-8c14-763aeeca10ec'
def gitjenkinsRepo      ='https://github.dxc.com/dtc-financial-crimes/Jenkins_v2.0.git'
def gitDeployRepo      = 'https://github.dxc.com/dtc-financial-crimes/Terraform_V2.0.git'

// Environment specific parameters
def environment        = 'Demo'+DemoNumber
def cidr			         = '193.'+DemoNumber+'.0.0/20'
def pubsub1cidr		     = '193.'+DemoNumber+'.10.0/24'
def prvsub1cidr		     = '193.'+DemoNumber+'.11.0/24'
def prvsub2cidr		     = '193.'+DemoNumber+'.12.0/24'
def vpc_mgt_cidr       = '172.16.0.0/20'

pipelineJob('Infra-deploy-demo-'+ DemoNumber) {
  description('Infra-deploy-demo')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master'], '')
    //choiceParam('terraformDir', ["$WORKSPACE/FCII-Terraform-Jenkins/Infra-Deploy/"], '')
    choiceParam('terraformBucket', ["infra-fciirm-tfstate"], '')
    choiceParam('terraformKey',  ["infra"+DemoNumber+".tfstate"], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
	  stringParam('vpc_cidr', cidr,'Do not edit')
    //labelParam('vpc_cidr', cidr,'Do not edit')
    stringParam('public_subnet_cidr_az1', pubsub1cidr,'Do not edit')
    stringParam('private_subnet_cidr_az1_internal',prvsub1cidr,'Do not edit')
    stringParam('private_subnet_cidr_az2_internal',prvsub2cidr,'Do not edit')
    stringParam('demonumber',DemoNumber,'')
    stringParam('pipelinename',"Infra-deploy")
  }
  definition {
    cps {
      script(readFileFromWorkspace('aws-jenkins-jobs/FCII-pipelines/deploy.groovy'))
    }
  }
}

pipelineJob('Infra-destroy-demo-'+ DemoNumber) {
  description('Infra-destroy')
  logRotator(5, 5)
  parameters {
     choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master'], '')
    //choiceParam('terraformDir', ["$WORKSPACE/FCII-Terraform-Jenkins/Infra-Deploy/"], '')
    choiceParam('terraformBucket', ["infra-fciirm-tfstate"], '')
    choiceParam('terraformKey',  ["infra"+DemoNumber+".tfstate"], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
	  //stringParam('vpc_cidr', cidr,'Do not edit')
    //labelParam('vpc_cidr', cidr,'Do not edit')
    //stringParam('public_subnet_cidr_az1', pubsub1cidr,'Do not edit')
    //stringParam('private_subnet_cidr_az1_internal',prvsub1cidr,'Do not edit')
    //stringParam('private_subnet_cidr_az2_internal',prvsub2cidr,'Do not edit')
    stringParam('demonumber',DemoNumber,'')
    stringParam('pipelinename',"Infra-destroy")
  }
  definition {
    cps {
      script(readFileFromWorkspace('aws-jenkins-jobs/FCII-pipelines/destroy-deploy.groovy'))
    }
  }
}
/** These functions are created for RiskMaster , Currently not being used , however kept it incase required
pipelineJob('riskmaster-deploy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master'],'')
    //choiceParam('terraformDir', ["environments/${environment}"], '')
    stringParam('demonumber',DemoNumber,'')
    stringParam('tag_Schedule',"true",'')
    stringParam('tag_env',"demo",'')
    stringParam('vpc_cidr',prvsub1cidr,'Do not edit')
    stringParam('vpc_cidr2',prvsub2cidr,'Do not edit')
    stringParam('vpc_mgt_cidr',vpc_mgt_cidr,'Do not edit')
    choiceParam('terraformBucket', ["riskmaster-prod-tfstate"], '')
    choiceParam('terraformKey',  ["Riskmaster-"+DemoNumber+".tfstate"], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
    stringParam('pipelinename',"Riskmaster-deploy")
  }
  definition {
    cps {
      script(readFileFromWorkspace('aws-jenkins-jobs/FCII-pipelines/deploy.groovy'))
      sandbox()
    }
  }
}
pipelineJob('riskmaster-destroy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master'],'')
    //choiceParam('terraformDir', ["environments/${environment}"], '')
    stringParam('demonumber',DemoNumber,'')
    stringParam('tag_Schedule',"true",'')
    stringParam('tag_env',"demo",'')
    stringParam('vpc_cidr',prvsub1cidr,'Do not edit')
    stringParam('vpc_cidr2',prvsub2cidr,'Do not edit')
    stringParam('vpc_mgt_cidr',vpc_mgt_cidr,'Do not edit')
    choiceParam('terraformBucket', ["riskmaster-prod-tfstate"], '')
    choiceParam('terraformKey',  ["Riskmaster-"+DemoNumber+".tfstate"], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
    stringParam('pipelinename',"Riskmaster-deploy-destroy")
  }
  definition {
    cps {
      script(readFileFromWorkspace('aws-jenkins-jobs/FCII-pipelines/destroy-deploy.groovy'))
    }
  }
}
**/


pipelineJob('fcii-deploy-demo-'+ DemoNumber) {
description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master'],'')
    //choiceParam('terraformDir', ["environments/${environment}"], '')
    stringParam('demonumber',DemoNumber,'')
    stringParam('tag_Schedule',"true",'')
    stringParam('tag_env',"demo",'')
    stringParam('vpc_cidr',cidr,'Do not edit')
    stringParam('vpc_mgt_cidr',vpc_mgt_cidr,'Do not edit')
    choiceParam('terraformBucket', ["fcii-prod-tfstate"], '')
    choiceParam('terraformKey',  ["fcii-"+DemoNumber+".tfstate"], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
    stringParam('pipelinename',"FCII-deploy")  }
  definition {
    cps {
      script(readFileFromWorkspace('aws-jenkins-jobs/FCII-pipelines/deploy.groovy'))
    }
  }
}

pipelineJob('fcii-destroy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
   choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master'],'')
    //choiceParam('terraformDir', ["environments/${environment}"], '')
    stringParam('demonumber',DemoNumber,'')
    stringParam('tag_Schedule',"true",'')
    stringParam('tag_env',"demo",'')
    stringParam('vpc_cidr',cidr,'Do not edit')
    stringParam('vpc_mgt_cidr',vpc_mgt_cidr,'Do not edit')
    choiceParam('terraformBucket', ["fcii-prod-tfstate"], '')
    choiceParam('terraformKey',  ["fcii-"+DemoNumber+".tfstate"], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
    stringParam('pipelinename',"FCII-destroy")
    }

  definition {
    cps {
      script(readFileFromWorkspace('aws-jenkins-jobs/FCII-pipelines/destroy-deploy.groovy'))
    }
  }
}


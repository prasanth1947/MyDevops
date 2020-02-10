/*import org.jenkinsci.plugins.scriptsecurity.scripts.*
toApprove = ScriptApproval.get().getPendingScripts().collect()
toApprove.each {pending -> ScriptApproval.get().approveScript(pending.getHash())}*/

def gitCreds           = 'fde4ec2e-eabc-433a-8c14-763aeeca10ec'
def gitjenkinsRepo      ='https://github.dxc.com/dtc-financial-crimes/Jenkins'
def gitDeployRepo      = 'https://github.dxc.com/dtc-financial-crimes/Terraform.git'

// Environment specific parameters
def environment        = 'Demo'+DemoNumber
def cidr			         = '193.'+DemoNumber+'.0.0/20'
def pubsub1cidr		     = '193.'+DemoNumber+'.10.0/24'
def prvsub1cidr		     = '193.'+DemoNumber+'.11.0/24'
def prvsub2cidr		     = '193.'+DemoNumber+'.12.0/24'

pipelineJob('Infra-deploy-demo-'+ DemoNumber) {
  description('')
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
      script(readFileFromWorkspace('aws-jenkins-jobs/pipelines/FCII-Riskmaster/deploy.groovy'))      
    }
  }
}

pipelineJob('Infra-destroy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master','Suman'], '')
    choiceParam('terraformDir', ["environments/${environment}"], '')
    choiceParam('terraformBucket', ["Infra-"+DemoNumber+"-tfstate"], '')
    choiceParam('terraformKey',  ['/Infra.tfstate'], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
  }
  definition {
    cps {
      //script(readFileFromWorkspace('destroy-deploy.groovy'))      
    }
  }
}

pipelineJob('riskmaster-deploy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master','Suman'], '')
    choiceParam('terraformDir', ["environments/${environment}"], '')
    choiceParam('terraformBucket', ["Riskmaster-nonprod-tfstate"], '')
    choiceParam('terraformKey',  ['xxx/Riskmaster.tfstate'], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
    stringParam('pipelinename',"Riskmaster-deploy")
  }
  definition {
    cps {
      //script(readFileFromWorkspace('deploy.groovy'))      
    }
  }
}
pipelineJob('riskmaster-destroy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master','Suman'], '')
    choiceParam('terraformDir', ["environments/${environment}"], '')
    choiceParam('terraformBucket', ["Riskmaster-nonprod-tfstate"], '')
    choiceParam('terraformKey',  ['xxx/Riskmaster.tfstate'], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
  }
  definition {
    cps {
      //script(readFileFromWorkspace('destroy-deploy.groovy'))      
    }
  }
}
pipelineJob('fcii-deploy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master','Suman'], '')
    choiceParam('terraformDir', ["environments/${environment}"], '')
    choiceParam('terraformBucket', ["Riskmaster-nonprod-tfstate"], '')
    choiceParam('terraformKey',  ['xxx/Riskmaster.tfstate'], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
  }
  definition {
    cps {
      //script(readFileFromWorkspace('deploy.groovy'))      
    }
  }
}

pipelineJob('fcii-destroy-demo-'+ DemoNumber) {
  description('')
  logRotator(5, 5)
  parameters {
    choiceParam('gitCreds', [gitCreds], '')
    choiceParam('gitUrl', [gitDeployRepo], '')
    choiceParam('gitBranch', ['master','Suman'], '')
    choiceParam('terraformDir', ["environments/${environment}"], '')
    choiceParam('terraformBucket', ["Riskmaster-nonprod-tfstate"], '')
    choiceParam('terraformKey',  ['xxx/Riskmaster.tfstate'], '')
    choiceParam('terraformApplyPlan', ['false','true'], '')
  }
  definition {
    cps {
      //script(readFileFromWorkspace('destroy-deploy.groovy'))      
    }
  }
}

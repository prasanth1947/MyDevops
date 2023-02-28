# FCII Wake/Slumber Pipeline
This repository contains Ansible roles and playbooks to manage application in the FCII eco system.

## Requirements
- Docker image with required tools installed - currently stored in PDXC Artifactroy as jenkins_ansible_slave
- Docker proxy to allow Jenkins to build containers on host instance - image currently stored in PDXC Artifactory
- Jenkins configured to build and execute code on ephemeral nodes

## Playbook Variables

As this repository connects to the specified region and either hibernates or wakes the selected components e.g. openshift or hadoop. Below are the three playbooks that are currently configured and the variables they require:

#### **openshift-status.yml**
The below playbook is set up to test the connection to Openshift by doing a simple login from the oc CLI.
```yaml
- name: FCII | Openshift | Status
  hosts: localhost
  gather_facts: False

  roles:
    - ansible-role-oc-status
```

In order to execute the above playbook you will require the below variables.

```yaml
operation: "hibernate/wake"
oc_host: "api endpoint of the openshift cluster"
oc_user: "openshift username"
oc_pass: "openshift password"
```

#### **hadoop-manage-services.yml**
The below playbook will connect to the amber server and execute the scripts based on the operation (hibernate/wake).
```yaml
- name: FCII | Hadoop | Start/Stop services
  hosts: localhost
  gather_facts: False

  roles:
    - ansible-role-hadoop-manage-services
```

In order to execute the above playbook you will require the below variables.

```yaml
operation: "hibernate/wake"
access_key_id: "aws automation account username"
secret_access_key: "aws automation account password"
region: "region to run the playbook against"
client_name: "name of the client to manage"
env: "p/np1/np2/gcp/rsp"
control_server: "IP for control server"
```

#### **application-lifecycle.yml**
The below playbook is set up to stop/start all selected components (openshift or hadoop). The process of hibernation will power down the instance(s), create a snapshot(s) from the volume(s) and detach the volume(s). The wake process will create a volume from the snapshot, attach it to the instance and power the instance on.
```yaml
- name: FCII | Manage Applications
  hosts: localhost
  gather_facts: False

  roles:
    - ansible-role-manage-applications
```

In order to execute the above playbook you will require the below variables.

```yaml
operation: "hibernate/wake"
access_key_id: "aws automation account username"
secret_access_key: "aws automation account password"
region: "region to run the playbook against"
client_name: "name of the client to manage"
env: "p/np1/np2/gcp/rsp"
application: "hadoop-ambari/hadoop-gateway/hadoop-master/openshift-worker/openshift-master"
```

## Jenkins Pipeline
The pipeline stitches the above playbooks together to execute in a specific order. The process of hibernation will first stop all the Hadoop services, hibernate the instances and test the connection to Openshift has been severed. The wake process will wake the instances, start all the Hadoop services and check connection to the Openshift API endpoint.
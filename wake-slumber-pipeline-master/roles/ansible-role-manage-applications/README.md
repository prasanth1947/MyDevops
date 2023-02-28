Role Name
=========

Hibernates or wakes application in the FCII eco-system.

Requirements
------------

[Environment setup](https://github.dxc.com/FCI-PaaS/wake-slumber-pipeline/blob/master/README.md)

Role Variables
--------------

As this role connects to AWS, you will need the credentials for the AWS to be used. These are represented using the following variables:

```yaml
access_key_id: "{{ access_key_id }}"
secret_access_key: "{{ secret_access_key }}"
```
In order to execute this role, you will need to specify the following variables.

```yaml
operation: "hibernate/wake"
env: "prod/nonprod1/nonprod2/control-plane/regional-services-plane"
region: "eu-west-1/eu-west-2/us-east-1/us-east-2"
client_name: "erie/mib/shared"
hadoop_ambari: "true/false"
hadoop_gateway: "true/false"
hadoop_master: "true/false"
openshift_master: "true/false"
openshift_worker: "true/false"
```

Dependencies
------------

n/a

Example Playbook
----------------

```yaml
- hosts: localhost
  gather_facts: False

  roles:
    - ansible-role-manage-applications
```

License
-------

BSD

Author Information
------------------

Author: [Sami Viegas](viegas@dxc.com)
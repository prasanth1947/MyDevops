Role Name
=========

Manages additional openshift operation as part of the hibernate/wake pipeline.

Requirements
------------

[Environment setup](https://github.dxc.com/FCI-PaaS/wake-slumber-pipeline/blob/master/README.md)

Role Variables
--------------

In order to execute this role, you will need to specify the following variables.

```yaml
operation: "hibernate/wake"
oc_host: "openshift api endpoint"
oc_user: "openshift username"
oc_pass: "openshift password"
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
    - ansible-role-oc-status

```

License
-------

BSD

Author Information
------------------

Author: [Sami Viegas](viegas@dxc.com)
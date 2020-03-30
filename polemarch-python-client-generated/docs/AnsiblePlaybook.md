# AnsiblePlaybook

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**playbook** | **str** |  | 
**args** | **str** | Playbook(s) | [optional] 
**become** | **bool** | run operations with become (does not imply password prompting) | [optional] [default to False]
**become_method** | **str** | privilege escalation method to use (default&#x3D;sudo), use &#x60;ansible-doc -t become -l&#x60; to list valid choices. | [optional] 
**become_user** | **str** | run operations as this user (default&#x3D;root) | [optional] 
**check** | **bool** | don&#39;t make any changes; instead, try to predict some of the changes that may occur | [optional] [default to False]
**connection** | **str** | connection type to use (default&#x3D;smart) | [optional] 
**diff** | **bool** | when changing (small) files and templates, show the differences in those files; works great with --check | [optional] [default to False]
**extra_vars** | **str** | set additional variables as key&#x3D;value or YAML/JSON, if filename prepend with @ | [optional] 
**flush_cache** | **bool** | clear the fact cache for every host in inventory | [optional] [default to False]
**force_handlers** | **bool** | run handlers even if a task fails | [optional] [default to False]
**forks** | **int** | specify number of parallel processes to use (default&#x3D;5) | [optional] 
**inventory** | **str** | specify inventory host path or comma separated host list. --inventory-file is deprecated | [optional] 
**limit** | **str** | further limit selected hosts to an additional pattern | [optional] 
**list_hosts** | **bool** | outputs a list of matching hosts; does not execute anything else | [optional] [default to False]
**list_tags** | **bool** | list all available tags | [optional] [default to False]
**list_tasks** | **bool** | list all tasks that would be executed | [optional] [default to False]
**module_path** | **str** | prepend colon-separated path(s) to module library (default&#x3D;~/.ansible/plugins/modules:/usr/share/ansible/plugins/modules) | [optional] 
**private_key** | **str** | use this file to authenticate the connection | [optional] 
**scp_extra_args** | **str** | specify extra arguments to pass to scp only (e.g. -l) | [optional] 
**sftp_extra_args** | **str** | specify extra arguments to pass to sftp only (e.g. -f, -l) | [optional] 
**skip_tags** | **str** | only run plays and tasks whose tags do not match these values | [optional] 
**ssh_common_args** | **str** | specify common arguments to pass to sftp/scp/ssh (e.g. ProxyCommand) | [optional] 
**ssh_extra_args** | **str** | specify extra arguments to pass to ssh only (e.g. -R) | [optional] 
**start_at_task** | **str** | start the playbook at the task matching this name | [optional] 
**step** | **bool** | one-step-at-a-time: confirm each task before running | [optional] [default to False]
**syntax_check** | **bool** | perform a syntax check on the playbook, but do not execute it | [optional] [default to False]
**tags** | **str** | only run plays and tasks tagged with these values | [optional] 
**timeout** | **int** | override the connection timeout in seconds (default&#x3D;10) | [optional] 
**user** | **str** | connect as this user (default&#x3D;None) | [optional] 
**vault_id** | **str** | the vault identity to use | [optional] 
**vault_password_file** | **str** | vault password file | [optional] 
**verbose** | **int** | verbose mode (-vvv for more, -vvvv to enable connection debugging) | [optional] 

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)



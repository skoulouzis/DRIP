# polemarch_client.ProjectApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**project_add**](ProjectApi.md#project_add) | **POST** /project/ | 
[**project_copy**](ProjectApi.md#project_copy) | **POST** /project/{id}/copy/ | 
[**project_edit**](ProjectApi.md#project_edit) | **PATCH** /project/{id}/ | 
[**project_execute_module**](ProjectApi.md#project_execute_module) | **POST** /project/{id}/execute_module/ | 
[**project_execute_playbook**](ProjectApi.md#project_execute_playbook) | **POST** /project/{id}/execute_playbook/ | 
[**project_get**](ProjectApi.md#project_get) | **GET** /project/{id}/ | 
[**project_history_cancel**](ProjectApi.md#project_history_cancel) | **POST** /project/{id}/history/{history_id}/cancel/ | 
[**project_history_clear**](ProjectApi.md#project_history_clear) | **DELETE** /project/{id}/history/{history_id}/clear/ | 
[**project_history_facts**](ProjectApi.md#project_history_facts) | **GET** /project/{id}/history/{history_id}/facts/ | 
[**project_history_get**](ProjectApi.md#project_history_get) | **GET** /project/{id}/history/{history_id}/ | 
[**project_history_list**](ProjectApi.md#project_history_list) | **GET** /project/{id}/history/ | 
[**project_history_remove**](ProjectApi.md#project_history_remove) | **DELETE** /project/{id}/history/{history_id}/ | 
[**project_inventory_add**](ProjectApi.md#project_inventory_add) | **POST** /project/{id}/inventory/ | 
[**project_inventory_all_groups_get**](ProjectApi.md#project_inventory_all_groups_get) | **GET** /project/{id}/inventory/{inventory_id}/all_groups/{all_groups_id}/ | 
[**project_inventory_all_groups_list**](ProjectApi.md#project_inventory_all_groups_list) | **GET** /project/{id}/inventory/{inventory_id}/all_groups/ | 
[**project_inventory_all_hosts_get**](ProjectApi.md#project_inventory_all_hosts_get) | **GET** /project/{id}/inventory/{inventory_id}/all_hosts/{all_hosts_id}/ | 
[**project_inventory_all_hosts_list**](ProjectApi.md#project_inventory_all_hosts_list) | **GET** /project/{id}/inventory/{inventory_id}/all_hosts/ | 
[**project_inventory_copy**](ProjectApi.md#project_inventory_copy) | **POST** /project/{id}/inventory/{inventory_id}/copy/ | 
[**project_inventory_edit**](ProjectApi.md#project_inventory_edit) | **PATCH** /project/{id}/inventory/{inventory_id}/ | 
[**project_inventory_get**](ProjectApi.md#project_inventory_get) | **GET** /project/{id}/inventory/{inventory_id}/ | 
[**project_inventory_group_add**](ProjectApi.md#project_inventory_group_add) | **POST** /project/{id}/inventory/{inventory_id}/group/ | 
[**project_inventory_group_edit**](ProjectApi.md#project_inventory_group_edit) | **PATCH** /project/{id}/inventory/{inventory_id}/group/{group_id}/ | 
[**project_inventory_group_get**](ProjectApi.md#project_inventory_group_get) | **GET** /project/{id}/inventory/{inventory_id}/group/{group_id}/ | 
[**project_inventory_group_inventory_copy**](ProjectApi.md#project_inventory_group_inventory_copy) | **POST** /project/{id}/inventory/{inventory_id}/group/{group_id}/copy/ | 
[**project_inventory_group_inventory_set_owner**](ProjectApi.md#project_inventory_group_inventory_set_owner) | **POST** /project/{id}/inventory/{inventory_id}/group/{group_id}/set_owner/ | 
[**project_inventory_group_list**](ProjectApi.md#project_inventory_group_list) | **GET** /project/{id}/inventory/{inventory_id}/group/ | 
[**project_inventory_group_remove**](ProjectApi.md#project_inventory_group_remove) | **DELETE** /project/{id}/inventory/{inventory_id}/group/{group_id}/ | 
[**project_inventory_group_update**](ProjectApi.md#project_inventory_group_update) | **PUT** /project/{id}/inventory/{inventory_id}/group/{group_id}/ | 
[**project_inventory_group_variables_add**](ProjectApi.md#project_inventory_group_variables_add) | **POST** /project/{id}/inventory/{inventory_id}/group/{group_id}/variables/ | 
[**project_inventory_group_variables_edit**](ProjectApi.md#project_inventory_group_variables_edit) | **PATCH** /project/{id}/inventory/{inventory_id}/group/{group_id}/variables/{variables_id}/ | 
[**project_inventory_group_variables_get**](ProjectApi.md#project_inventory_group_variables_get) | **GET** /project/{id}/inventory/{inventory_id}/group/{group_id}/variables/{variables_id}/ | 
[**project_inventory_group_variables_list**](ProjectApi.md#project_inventory_group_variables_list) | **GET** /project/{id}/inventory/{inventory_id}/group/{group_id}/variables/ | 
[**project_inventory_group_variables_remove**](ProjectApi.md#project_inventory_group_variables_remove) | **DELETE** /project/{id}/inventory/{inventory_id}/group/{group_id}/variables/{variables_id}/ | 
[**project_inventory_group_variables_update**](ProjectApi.md#project_inventory_group_variables_update) | **PUT** /project/{id}/inventory/{inventory_id}/group/{group_id}/variables/{variables_id}/ | 
[**project_inventory_host_add**](ProjectApi.md#project_inventory_host_add) | **POST** /project/{id}/inventory/{inventory_id}/host/ | 
[**project_inventory_host_edit**](ProjectApi.md#project_inventory_host_edit) | **PATCH** /project/{id}/inventory/{inventory_id}/host/{host_id}/ | 
[**project_inventory_host_get**](ProjectApi.md#project_inventory_host_get) | **GET** /project/{id}/inventory/{inventory_id}/host/{host_id}/ | 
[**project_inventory_host_inventory_copy**](ProjectApi.md#project_inventory_host_inventory_copy) | **POST** /project/{id}/inventory/{inventory_id}/host/{host_id}/copy/ | 
[**project_inventory_host_inventory_set_owner**](ProjectApi.md#project_inventory_host_inventory_set_owner) | **POST** /project/{id}/inventory/{inventory_id}/host/{host_id}/set_owner/ | 
[**project_inventory_host_list**](ProjectApi.md#project_inventory_host_list) | **GET** /project/{id}/inventory/{inventory_id}/host/ | 
[**project_inventory_host_remove**](ProjectApi.md#project_inventory_host_remove) | **DELETE** /project/{id}/inventory/{inventory_id}/host/{host_id}/ | 
[**project_inventory_host_update**](ProjectApi.md#project_inventory_host_update) | **PUT** /project/{id}/inventory/{inventory_id}/host/{host_id}/ | 
[**project_inventory_host_variables_add**](ProjectApi.md#project_inventory_host_variables_add) | **POST** /project/{id}/inventory/{inventory_id}/host/{host_id}/variables/ | 
[**project_inventory_host_variables_edit**](ProjectApi.md#project_inventory_host_variables_edit) | **PATCH** /project/{id}/inventory/{inventory_id}/host/{host_id}/variables/{variables_id}/ | 
[**project_inventory_host_variables_get**](ProjectApi.md#project_inventory_host_variables_get) | **GET** /project/{id}/inventory/{inventory_id}/host/{host_id}/variables/{variables_id}/ | 
[**project_inventory_host_variables_list**](ProjectApi.md#project_inventory_host_variables_list) | **GET** /project/{id}/inventory/{inventory_id}/host/{host_id}/variables/ | 
[**project_inventory_host_variables_remove**](ProjectApi.md#project_inventory_host_variables_remove) | **DELETE** /project/{id}/inventory/{inventory_id}/host/{host_id}/variables/{variables_id}/ | 
[**project_inventory_host_variables_update**](ProjectApi.md#project_inventory_host_variables_update) | **PUT** /project/{id}/inventory/{inventory_id}/host/{host_id}/variables/{variables_id}/ | 
[**project_inventory_import_inventory**](ProjectApi.md#project_inventory_import_inventory) | **POST** /project/{id}/inventory/import_inventory/ | 
[**project_inventory_list**](ProjectApi.md#project_inventory_list) | **GET** /project/{id}/inventory/ | 
[**project_inventory_remove**](ProjectApi.md#project_inventory_remove) | **DELETE** /project/{id}/inventory/{inventory_id}/ | 
[**project_inventory_set_owner**](ProjectApi.md#project_inventory_set_owner) | **POST** /project/{id}/inventory/{inventory_id}/set_owner/ | 
[**project_inventory_update**](ProjectApi.md#project_inventory_update) | **PUT** /project/{id}/inventory/{inventory_id}/ | 
[**project_inventory_variables_add**](ProjectApi.md#project_inventory_variables_add) | **POST** /project/{id}/inventory/{inventory_id}/variables/ | 
[**project_inventory_variables_edit**](ProjectApi.md#project_inventory_variables_edit) | **PATCH** /project/{id}/inventory/{inventory_id}/variables/{variables_id}/ | 
[**project_inventory_variables_get**](ProjectApi.md#project_inventory_variables_get) | **GET** /project/{id}/inventory/{inventory_id}/variables/{variables_id}/ | 
[**project_inventory_variables_list**](ProjectApi.md#project_inventory_variables_list) | **GET** /project/{id}/inventory/{inventory_id}/variables/ | 
[**project_inventory_variables_remove**](ProjectApi.md#project_inventory_variables_remove) | **DELETE** /project/{id}/inventory/{inventory_id}/variables/{variables_id}/ | 
[**project_inventory_variables_update**](ProjectApi.md#project_inventory_variables_update) | **PUT** /project/{id}/inventory/{inventory_id}/variables/{variables_id}/ | 
[**project_list**](ProjectApi.md#project_list) | **GET** /project/ | 
[**project_module_get**](ProjectApi.md#project_module_get) | **GET** /project/{id}/module/{module_id}/ | 
[**project_module_list**](ProjectApi.md#project_module_list) | **GET** /project/{id}/module/ | 
[**project_periodic_task_add**](ProjectApi.md#project_periodic_task_add) | **POST** /project/{id}/periodic_task/ | 
[**project_periodic_task_edit**](ProjectApi.md#project_periodic_task_edit) | **PATCH** /project/{id}/periodic_task/{periodic_task_id}/ | 
[**project_periodic_task_execute**](ProjectApi.md#project_periodic_task_execute) | **POST** /project/{id}/periodic_task/{periodic_task_id}/execute/ | 
[**project_periodic_task_get**](ProjectApi.md#project_periodic_task_get) | **GET** /project/{id}/periodic_task/{periodic_task_id}/ | 
[**project_periodic_task_list**](ProjectApi.md#project_periodic_task_list) | **GET** /project/{id}/periodic_task/ | 
[**project_periodic_task_remove**](ProjectApi.md#project_periodic_task_remove) | **DELETE** /project/{id}/periodic_task/{periodic_task_id}/ | 
[**project_periodic_task_update**](ProjectApi.md#project_periodic_task_update) | **PUT** /project/{id}/periodic_task/{periodic_task_id}/ | 
[**project_periodic_task_variables_add**](ProjectApi.md#project_periodic_task_variables_add) | **POST** /project/{id}/periodic_task/{periodic_task_id}/variables/ | 
[**project_periodic_task_variables_edit**](ProjectApi.md#project_periodic_task_variables_edit) | **PATCH** /project/{id}/periodic_task/{periodic_task_id}/variables/{variables_id}/ | 
[**project_periodic_task_variables_get**](ProjectApi.md#project_periodic_task_variables_get) | **GET** /project/{id}/periodic_task/{periodic_task_id}/variables/{variables_id}/ | 
[**project_periodic_task_variables_list**](ProjectApi.md#project_periodic_task_variables_list) | **GET** /project/{id}/periodic_task/{periodic_task_id}/variables/ | 
[**project_periodic_task_variables_remove**](ProjectApi.md#project_periodic_task_variables_remove) | **DELETE** /project/{id}/periodic_task/{periodic_task_id}/variables/{variables_id}/ | 
[**project_periodic_task_variables_update**](ProjectApi.md#project_periodic_task_variables_update) | **PUT** /project/{id}/periodic_task/{periodic_task_id}/variables/{variables_id}/ | 
[**project_playbook_get**](ProjectApi.md#project_playbook_get) | **GET** /project/{id}/playbook/{playbook_id}/ | 
[**project_playbook_list**](ProjectApi.md#project_playbook_list) | **GET** /project/{id}/playbook/ | 
[**project_remove**](ProjectApi.md#project_remove) | **DELETE** /project/{id}/ | 
[**project_set_owner**](ProjectApi.md#project_set_owner) | **POST** /project/{id}/set_owner/ | 
[**project_sync**](ProjectApi.md#project_sync) | **POST** /project/{id}/sync/ | 
[**project_template_add**](ProjectApi.md#project_template_add) | **POST** /project/{id}/template/ | 
[**project_template_edit**](ProjectApi.md#project_template_edit) | **PATCH** /project/{id}/template/{template_id}/ | 
[**project_template_execute**](ProjectApi.md#project_template_execute) | **POST** /project/{id}/template/{template_id}/execute/ | 
[**project_template_get**](ProjectApi.md#project_template_get) | **GET** /project/{id}/template/{template_id}/ | 
[**project_template_list**](ProjectApi.md#project_template_list) | **GET** /project/{id}/template/ | 
[**project_template_remove**](ProjectApi.md#project_template_remove) | **DELETE** /project/{id}/template/{template_id}/ | 
[**project_template_update**](ProjectApi.md#project_template_update) | **PUT** /project/{id}/template/{template_id}/ | 
[**project_update**](ProjectApi.md#project_update) | **PUT** /project/{id}/ | 
[**project_variables_add**](ProjectApi.md#project_variables_add) | **POST** /project/{id}/variables/ | 
[**project_variables_edit**](ProjectApi.md#project_variables_edit) | **PATCH** /project/{id}/variables/{variables_id}/ | 
[**project_variables_get**](ProjectApi.md#project_variables_get) | **GET** /project/{id}/variables/{variables_id}/ | 
[**project_variables_list**](ProjectApi.md#project_variables_list) | **GET** /project/{id}/variables/ | 
[**project_variables_remove**](ProjectApi.md#project_variables_remove) | **DELETE** /project/{id}/variables/{variables_id}/ | 
[**project_variables_update**](ProjectApi.md#project_variables_update) | **PUT** /project/{id}/variables/{variables_id}/ | 


# **project_add**
> ProjectCreateMaster project_add(data)



Create a new project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.ProjectCreateMaster() # ProjectCreateMaster | 

try:
    api_response = api_instance.project_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**ProjectCreateMaster**](ProjectCreateMaster.md)|  | 

### Return type

[**ProjectCreateMaster**](ProjectCreateMaster.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_copy**
> Project project_copy(id, data)



Endpoint which copy instance with deps.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.Project() # Project | 

try:
    api_response = api_instance.project_copy(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**Project**](Project.md)|  | 

### Return type

[**Project**](Project.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_edit**
> OneProject project_edit(id, data)



Update one or more fields on an existing project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.OneProject() # OneProject | 

try:
    api_response = api_instance.project_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**OneProject**](OneProject.md)|  | 

### Return type

[**OneProject**](OneProject.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_execute_module**
> ExecuteResponse project_execute_module(id, data)



Execute `ansible -m [module]` with arguments.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.AnsibleModule() # AnsibleModule | 

try:
    api_response = api_instance.project_execute_module(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_execute_module: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**AnsibleModule**](AnsibleModule.md)|  | 

### Return type

[**ExecuteResponse**](ExecuteResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_execute_playbook**
> ExecuteResponse project_execute_playbook(id, data)



Execute `ansible-playbook` with arguments.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.AnsiblePlaybook() # AnsiblePlaybook | 

try:
    api_response = api_instance.project_execute_playbook(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_execute_playbook: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**AnsiblePlaybook**](AnsiblePlaybook.md)|  | 

### Return type

[**ExecuteResponse**](ExecuteResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_get**
> OneProject project_get(id)



Return a project instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.

try:
    api_response = api_instance.project_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 

### Return type

[**OneProject**](OneProject.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_history_cancel**
> ActionResponse project_history_cancel(history_id, id, data)



Cencel working task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
history_id = 56 # int | A unique integer value identifying instance of this history sublist.
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.Empty() # Empty | 

try:
    api_response = api_instance.project_history_cancel(history_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_history_cancel: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **history_id** | **int**| A unique integer value identifying instance of this history sublist. | 
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**Empty**](Empty.md)|  | 

### Return type

[**ActionResponse**](ActionResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_history_clear**
> project_history_clear(history_id, id)



Clear history output.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
history_id = 56 # int | A unique integer value identifying instance of this history sublist.
id = 56 # int | A unique integer value identifying this project.

try:
    api_instance.project_history_clear(history_id, id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_history_clear: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **history_id** | **int**| A unique integer value identifying instance of this history sublist. | 
 **id** | **int**| A unique integer value identifying this project. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_history_facts**
> Data project_history_facts(history_id, id)



Get compilated history facts (only for execution 'module' with module 'setup').

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
history_id = 56 # int | A unique integer value identifying instance of this history sublist.
id = 56 # int | A unique integer value identifying this project.

try:
    api_response = api_instance.project_history_facts(history_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_history_facts: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **history_id** | **int**| A unique integer value identifying instance of this history sublist. | 
 **id** | **int**| A unique integer value identifying this project. | 

### Return type

[**Data**](Data.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_history_get**
> OneHistory project_history_get(history_id, id)





### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
history_id = 56 # int | A unique integer value identifying instance of this history sublist.
id = 56 # int | A unique integer value identifying this project.

try:
    api_response = api_instance.project_history_get(history_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_history_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **history_id** | **int**| A unique integer value identifying instance of this history sublist. | 
 **id** | **int**| A unique integer value identifying this project. | 

### Return type

[**OneHistory**](OneHistory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_history_list**
> InlineResponse2008 project_history_list(id, id2=id2, mode=mode, kind=kind, status=status, id__not=id__not, name__not=name__not, name=name, older=older, newer=newer, ordering=ordering, limit=limit, offset=offset)





### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
mode = 'mode_example' # str | Module or playbook name. (optional)
kind = 'kind_example' # str | Kind of execution. (optional)
status = 'status_example' # str | Status of execution. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
older = 'older_example' # str | Older then this time (optional)
newer = 'newer_example' # str | Newer then this time (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_history_list(id, id2=id2, mode=mode, kind=kind, status=status, id__not=id__not, name__not=name__not, name=name, older=older, newer=newer, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_history_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **mode** | **str**| Module or playbook name. | [optional] 
 **kind** | **str**| Kind of execution. | [optional] 
 **status** | **str**| Status of execution. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **older** | **str**| Older then this time | [optional] 
 **newer** | **str**| Newer then this time | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2008**](InlineResponse2008.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_history_remove**
> project_history_remove(history_id, id)





### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
history_id = 56 # int | A unique integer value identifying instance of this history sublist.
id = 56 # int | A unique integer value identifying this project.

try:
    api_instance.project_history_remove(history_id, id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_history_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **history_id** | **int**| A unique integer value identifying instance of this history sublist. | 
 **id** | **int**| A unique integer value identifying this project. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_add**
> OneInventory project_inventory_add(id, data)



Create a new inventory.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.OneInventory() # OneInventory | 

try:
    api_response = api_instance.project_inventory_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**OneInventory**](OneInventory.md)|  | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_all_groups_get**
> OneGroup project_inventory_all_groups_get(all_groups_id, id, inventory_id)



Return a group instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
all_groups_id = 'all_groups_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_response = api_instance.project_inventory_all_groups_get(all_groups_id, id, inventory_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_all_groups_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **all_groups_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_all_groups_list**
> InlineResponse2001 project_inventory_all_groups_list(id, inventory_id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



Return all groups.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_all_groups_list(id, inventory_id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_all_groups_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **variables** | **str**| List of variables to filter. Comma separeted \&quot;key:value\&quot; list. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_all_hosts_get**
> OneHost project_inventory_all_hosts_get(all_hosts_id, id, inventory_id)



Return a host instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
all_hosts_id = 'all_hosts_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_response = api_instance.project_inventory_all_hosts_get(all_hosts_id, id, inventory_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_all_hosts_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **all_hosts_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_all_hosts_list**
> InlineResponse2003 project_inventory_all_hosts_list(id, inventory_id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



Return all hosts.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
type = 'type_example' # str | Instance type. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_all_hosts_list(id, inventory_id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_all_hosts_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **type** | **str**| Instance type. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **variables** | **str**| List of variables to filter. Comma separeted \&quot;key:value\&quot; list. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2003**](InlineResponse2003.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_copy**
> Inventory project_inventory_copy(id, inventory_id, data)



Endpoint which copy instance with deps.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.Inventory() # Inventory | 

try:
    api_response = api_instance.project_inventory_copy(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**Inventory**](Inventory.md)|  | 

### Return type

[**Inventory**](Inventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_edit**
> OneInventory project_inventory_edit(id, inventory_id, data)



Update one or more fields on an existing inventory.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneInventory() # OneInventory | 

try:
    api_response = api_instance.project_inventory_edit(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneInventory**](OneInventory.md)|  | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_get**
> OneInventory project_inventory_get(id, inventory_id)



Return a inventory instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_response = api_instance.project_inventory_get(id, inventory_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_add**
> GroupCreateMaster project_inventory_group_add(id, inventory_id, data)



Create a new group.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.GroupCreateMaster() # GroupCreateMaster | 

try:
    api_response = api_instance.project_inventory_group_add(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**GroupCreateMaster**](GroupCreateMaster.md)|  | 

### Return type

[**GroupCreateMaster**](GroupCreateMaster.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_edit**
> OneGroup project_inventory_group_edit(group_id, id, inventory_id, data)



Update one or more fields on an existing group.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.project_inventory_group_edit(group_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_get**
> OneGroup project_inventory_group_get(group_id, id, inventory_id)



Return a group instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_response = api_instance.project_inventory_group_get(group_id, id, inventory_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_inventory_copy**
> Group project_inventory_group_inventory_copy(group_id, id, inventory_id, data)



Endpoint which copy instance with deps.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.Group() # Group | 

try:
    api_response = api_instance.project_inventory_group_inventory_copy(group_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_inventory_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**Group**](Group.md)|  | 

### Return type

[**Group**](Group.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_inventory_set_owner**
> SetOwner project_inventory_group_inventory_set_owner(group_id, id, inventory_id, data)



Change instance owner.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.project_inventory_group_inventory_set_owner(group_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_inventory_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_list**
> InlineResponse2001 project_inventory_group_list(id, inventory_id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



Return all groups.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_group_list(id, inventory_id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **variables** | **str**| List of variables to filter. Comma separeted \&quot;key:value\&quot; list. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_remove**
> project_inventory_group_remove(group_id, id, inventory_id)



Remove an existing group.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_instance.project_inventory_group_remove(group_id, id, inventory_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_update**
> OneGroup project_inventory_group_update(group_id, id, inventory_id, data)



Update a group.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.project_inventory_group_update(group_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_variables_add**
> InventoryVariable project_inventory_group_variables_add(group_id, id, inventory_id, data)



Create a new variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_group_variables_add(group_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_variables_edit**
> InventoryVariable project_inventory_group_variables_edit(group_id, id, inventory_id, variables_id, data)



Update one or more fields on an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_group_variables_edit(group_id, id, inventory_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_variables_get**
> InventoryVariable project_inventory_group_variables_get(group_id, id, inventory_id, variables_id)



Return a variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.project_inventory_group_variables_get(group_id, id, inventory_id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_variables_list**
> InlineResponse2002 project_inventory_group_variables_list(group_id, id, inventory_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



Return all variables of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_group_variables_list(group_id, id, inventory_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **key** | **str**| A key name string value (or comma separated list) of instance. | [optional] 
 **value** | **str**| A value of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_variables_remove**
> project_inventory_group_variables_remove(group_id, id, inventory_id, variables_id)



Remove an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.project_inventory_group_variables_remove(group_id, id, inventory_id, variables_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_group_variables_update**
> InventoryVariable project_inventory_group_variables_update(group_id, id, inventory_id, variables_id, data)



Update variable value.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
group_id = 'group_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_group_variables_update(group_id, id, inventory_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_group_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_add**
> OneHost project_inventory_host_add(id, inventory_id, data)



Create a new host.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.project_inventory_host_add(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_edit**
> OneHost project_inventory_host_edit(host_id, id, inventory_id, data)



Update one or more fields on an existing host.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.project_inventory_host_edit(host_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_get**
> OneHost project_inventory_host_get(host_id, id, inventory_id)



Return a host instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_response = api_instance.project_inventory_host_get(host_id, id, inventory_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_inventory_copy**
> Host project_inventory_host_inventory_copy(host_id, id, inventory_id, data)



Endpoint which copy instance with deps.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.Host() # Host | 

try:
    api_response = api_instance.project_inventory_host_inventory_copy(host_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_inventory_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**Host**](Host.md)|  | 

### Return type

[**Host**](Host.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_inventory_set_owner**
> SetOwner project_inventory_host_inventory_set_owner(host_id, id, inventory_id, data)



Change instance owner.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.project_inventory_host_inventory_set_owner(host_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_inventory_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_list**
> InlineResponse2003 project_inventory_host_list(id, inventory_id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



Return all hosts.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
type = 'type_example' # str | Instance type. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_host_list(id, inventory_id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **type** | **str**| Instance type. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **variables** | **str**| List of variables to filter. Comma separeted \&quot;key:value\&quot; list. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2003**](InlineResponse2003.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_remove**
> project_inventory_host_remove(host_id, id, inventory_id)



Remove an existing host.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_instance.project_inventory_host_remove(host_id, id, inventory_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_update**
> OneHost project_inventory_host_update(host_id, id, inventory_id, data)



Update a host.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.project_inventory_host_update(host_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_variables_add**
> InventoryVariable project_inventory_host_variables_add(host_id, id, inventory_id, data)



Create a new variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_host_variables_add(host_id, id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_variables_edit**
> InventoryVariable project_inventory_host_variables_edit(host_id, id, inventory_id, variables_id, data)



Update one or more fields on an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_host_variables_edit(host_id, id, inventory_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_variables_get**
> InventoryVariable project_inventory_host_variables_get(host_id, id, inventory_id, variables_id)



Return a variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.project_inventory_host_variables_get(host_id, id, inventory_id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_variables_list**
> InlineResponse2002 project_inventory_host_variables_list(host_id, id, inventory_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



Return all variables of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_host_variables_list(host_id, id, inventory_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **key** | **str**| A key name string value (or comma separated list) of instance. | [optional] 
 **value** | **str**| A value of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_variables_remove**
> project_inventory_host_variables_remove(host_id, id, inventory_id, variables_id)



Remove an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.project_inventory_host_variables_remove(host_id, id, inventory_id, variables_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_host_variables_update**
> InventoryVariable project_inventory_host_variables_update(host_id, id, inventory_id, variables_id, data)



Update variable value.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
host_id = 'host_id_example' # str | 
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_host_variables_update(host_id, id, inventory_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_host_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_import_inventory**
> InventoryImport project_inventory_import_inventory(id, data)



Create a new inventory.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.InventoryImport() # InventoryImport | 

try:
    api_response = api_instance.project_inventory_import_inventory(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_import_inventory: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**InventoryImport**](InventoryImport.md)|  | 

### Return type

[**InventoryImport**](InventoryImport.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_list**
> InlineResponse2006 project_inventory_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



Return all inventories.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **variables** | **str**| List of variables to filter. Comma separeted \&quot;key:value\&quot; list. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2006**](InlineResponse2006.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_remove**
> project_inventory_remove(id, inventory_id)



Remove an existing inventory.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.

try:
    api_instance.project_inventory_remove(id, inventory_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_set_owner**
> SetOwner project_inventory_set_owner(id, inventory_id, data)



Change instance owner.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.project_inventory_set_owner(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_update**
> OneInventory project_inventory_update(id, inventory_id, data)



Update a inventory.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.OneInventory() # OneInventory | 

try:
    api_response = api_instance.project_inventory_update(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**OneInventory**](OneInventory.md)|  | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_variables_add**
> InventoryVariable project_inventory_variables_add(id, inventory_id, data)



Create a new variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_variables_add(id, inventory_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_variables_edit**
> InventoryVariable project_inventory_variables_edit(id, inventory_id, variables_id, data)



Update one or more fields on an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_variables_edit(id, inventory_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_variables_get**
> InventoryVariable project_inventory_variables_get(id, inventory_id, variables_id)



Return a variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.project_inventory_variables_get(id, inventory_id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_variables_list**
> InlineResponse2002 project_inventory_variables_list(id, inventory_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



Return all variables of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_inventory_variables_list(id, inventory_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **key** | **str**| A key name string value (or comma separated list) of instance. | [optional] 
 **value** | **str**| A value of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_variables_remove**
> project_inventory_variables_remove(id, inventory_id, variables_id)



Remove an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.project_inventory_variables_remove(id, inventory_id, variables_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_inventory_variables_update**
> InventoryVariable project_inventory_variables_update(id, inventory_id, variables_id, data)



Update variable value.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
inventory_id = 56 # int | A unique integer value identifying instance of this inventories sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.project_inventory_variables_update(id, inventory_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_inventory_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **inventory_id** | **int**| A unique integer value identifying instance of this inventories sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_list**
> InlineResponse2007 project_list(id=id, name=name, status=status, id__not=id__not, name__not=name__not, variables=variables, status__not=status__not, ordering=ordering, limit=limit, offset=offset)



Return all projects.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
status = 'status_example' # str | Project sync status. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
status__not = 'status__not_example' # str | Project sync status. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_list(id=id, name=name, status=status, id__not=id__not, name__not=name__not, variables=variables, status__not=status__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **status** | **str**| Project sync status. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **variables** | **str**| List of variables to filter. Comma separeted \&quot;key:value\&quot; list. | [optional] 
 **status__not** | **str**| Project sync status. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2007**](InlineResponse2007.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_module_get**
> OneModule project_module_get(id, module_id)



Return a module details of project instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
module_id = 'module_id_example' # str | 

try:
    api_response = api_instance.project_module_get(id, module_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_module_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **module_id** | **str**|  | 

### Return type

[**OneModule**](OneModule.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_module_list**
> InlineResponse2009 project_module_list(id, path=path, name=name, path__not=path__not, ordering=ordering, limit=limit, offset=offset)



Return all available modules of project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
path = 'path_example' # str | Full path to module. (optional)
name = 'name_example' # str | Name of module. (optional)
path__not = 'path__not_example' # str | Full path to module. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_module_list(id, path=path, name=name, path__not=path__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_module_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **path** | **str**| Full path to module. | [optional] 
 **name** | **str**| Name of module. | [optional] 
 **path__not** | **str**| Full path to module. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2009**](InlineResponse2009.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_add**
> OnePeriodictask project_periodic_task_add(id, data)



Create a new periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.OnePeriodictask() # OnePeriodictask | 

try:
    api_response = api_instance.project_periodic_task_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**OnePeriodictask**](OnePeriodictask.md)|  | 

### Return type

[**OnePeriodictask**](OnePeriodictask.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_edit**
> OnePeriodictask project_periodic_task_edit(id, periodic_task_id, data)



Update one or more fields on an existing periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
data = polemarch_client.OnePeriodictask() # OnePeriodictask | 

try:
    api_response = api_instance.project_periodic_task_edit(id, periodic_task_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **data** | [**OnePeriodictask**](OnePeriodictask.md)|  | 

### Return type

[**OnePeriodictask**](OnePeriodictask.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_execute**
> ExecuteResponse project_periodic_task_execute(id, periodic_task_id, data)



Ad-hoc execute periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
data = polemarch_client.Empty() # Empty | 

try:
    api_response = api_instance.project_periodic_task_execute(id, periodic_task_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_execute: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **data** | [**Empty**](Empty.md)|  | 

### Return type

[**ExecuteResponse**](ExecuteResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_get**
> OnePeriodictask project_periodic_task_get(id, periodic_task_id)



Return a perodic task instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.

try:
    api_response = api_instance.project_periodic_task_get(id, periodic_task_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 

### Return type

[**OnePeriodictask**](OnePeriodictask.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_list**
> InlineResponse20010 project_periodic_task_list(id, id2=id2, mode=mode, kind=kind, type=type, template=template, id__not=id__not, name__not=name__not, name=name, ordering=ordering, limit=limit, offset=offset)



Return all periodic tasks in project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
mode = 'mode_example' # str | Periodic task module or playbook name. (optional)
kind = 'kind_example' # str | Kind of periodic task. (optional)
type = 'type_example' # str | Instance type. (optional)
template = 8.14 # float | A unique integer id of template used in periodic task. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_periodic_task_list(id, id2=id2, mode=mode, kind=kind, type=type, template=template, id__not=id__not, name__not=name__not, name=name, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **mode** | **str**| Periodic task module or playbook name. | [optional] 
 **kind** | **str**| Kind of periodic task. | [optional] 
 **type** | **str**| Instance type. | [optional] 
 **template** | **float**| A unique integer id of template used in periodic task. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20010**](InlineResponse20010.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_remove**
> project_periodic_task_remove(id, periodic_task_id)



Remove an existing periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.

try:
    api_instance.project_periodic_task_remove(id, periodic_task_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_update**
> OnePeriodictask project_periodic_task_update(id, periodic_task_id, data)



Update a periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
data = polemarch_client.OnePeriodictask() # OnePeriodictask | 

try:
    api_response = api_instance.project_periodic_task_update(id, periodic_task_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **data** | [**OnePeriodictask**](OnePeriodictask.md)|  | 

### Return type

[**OnePeriodictask**](OnePeriodictask.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_variables_add**
> PeriodicTaskVariable project_periodic_task_variables_add(id, periodic_task_id, data)



Create a new variable of periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
data = polemarch_client.PeriodicTaskVariable() # PeriodicTaskVariable | 

try:
    api_response = api_instance.project_periodic_task_variables_add(id, periodic_task_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **data** | [**PeriodicTaskVariable**](PeriodicTaskVariable.md)|  | 

### Return type

[**PeriodicTaskVariable**](PeriodicTaskVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_variables_edit**
> PeriodicTaskVariable project_periodic_task_variables_edit(id, periodic_task_id, variables_id, data)



Update one or more fields on an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.PeriodicTaskVariable() # PeriodicTaskVariable | 

try:
    api_response = api_instance.project_periodic_task_variables_edit(id, periodic_task_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**PeriodicTaskVariable**](PeriodicTaskVariable.md)|  | 

### Return type

[**PeriodicTaskVariable**](PeriodicTaskVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_variables_get**
> PeriodicTaskVariable project_periodic_task_variables_get(id, periodic_task_id, variables_id)



Return a variable of periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.project_periodic_task_variables_get(id, periodic_task_id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**PeriodicTaskVariable**](PeriodicTaskVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_variables_list**
> InlineResponse20011 project_periodic_task_variables_list(id, periodic_task_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



Return all variables of periodic task.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_periodic_task_variables_list(id, periodic_task_id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **key** | **str**| A key name string value (or comma separated list) of instance. | [optional] 
 **value** | **str**| A value of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20011**](InlineResponse20011.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_variables_remove**
> project_periodic_task_variables_remove(id, periodic_task_id, variables_id)



Remove an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.project_periodic_task_variables_remove(id, periodic_task_id, variables_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_periodic_task_variables_update**
> PeriodicTaskVariable project_periodic_task_variables_update(id, periodic_task_id, variables_id, data)



Update variable value.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
periodic_task_id = 56 # int | A unique integer value identifying instance of this periodic_task sublist.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.PeriodicTaskVariable() # PeriodicTaskVariable | 

try:
    api_response = api_instance.project_periodic_task_variables_update(id, periodic_task_id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_periodic_task_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **periodic_task_id** | **int**| A unique integer value identifying instance of this periodic_task sublist. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**PeriodicTaskVariable**](PeriodicTaskVariable.md)|  | 

### Return type

[**PeriodicTaskVariable**](PeriodicTaskVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_playbook_get**
> OnePlaybook project_playbook_get(id, playbook_id)



Return a playbook of project instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
playbook_id = 56 # int | A unique integer value identifying instance of this playbook sublist.

try:
    api_response = api_instance.project_playbook_get(id, playbook_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_playbook_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **playbook_id** | **int**| A unique integer value identifying instance of this playbook sublist. | 

### Return type

[**OnePlaybook**](OnePlaybook.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_playbook_list**
> InlineResponse20012 project_playbook_list(id, id2=id2, name=name, playbook=playbook, pb_filter=pb_filter, id__not=id__not, name__not=name__not, playbook__not=playbook__not, ordering=ordering, limit=limit, offset=offset)



Return all playbooks of project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
playbook = 'playbook_example' # str | Playbook filename. (optional)
pb_filter = 'pb_filter_example' # str | Playbook filename - filter for prefetch. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
playbook__not = 'playbook__not_example' # str | Playbook filename. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_playbook_list(id, id2=id2, name=name, playbook=playbook, pb_filter=pb_filter, id__not=id__not, name__not=name__not, playbook__not=playbook__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_playbook_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **playbook** | **str**| Playbook filename. | [optional] 
 **pb_filter** | **str**| Playbook filename - filter for prefetch. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **playbook__not** | **str**| Playbook filename. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20012**](InlineResponse20012.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_remove**
> project_remove(id)



Remove an existing project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.

try:
    api_instance.project_remove(id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_set_owner**
> SetOwner project_set_owner(id, data)



Change instance owner.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.project_set_owner(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_sync**
> ActionResponse project_sync(id, data)



Sync project with repository.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.Empty() # Empty | 

try:
    api_response = api_instance.project_sync(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_sync: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**Empty**](Empty.md)|  | 

### Return type

[**ActionResponse**](ActionResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_add**
> OneTemplate project_template_add(id, data)



Create a new execute template.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.OneTemplate() # OneTemplate | 

try:
    api_response = api_instance.project_template_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**OneTemplate**](OneTemplate.md)|  | 

### Return type

[**OneTemplate**](OneTemplate.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_edit**
> OneTemplate project_template_edit(id, template_id, data)



Update one or more fields on an existing execute template.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
template_id = 56 # int | A unique integer value identifying instance of this template sublist.
data = polemarch_client.OneTemplate() # OneTemplate | 

try:
    api_response = api_instance.project_template_edit(id, template_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **template_id** | **int**| A unique integer value identifying instance of this template sublist. | 
 **data** | [**OneTemplate**](OneTemplate.md)|  | 

### Return type

[**OneTemplate**](OneTemplate.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_execute**
> ExecuteResponse project_template_execute(id, template_id, data)



Execute template with option.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
template_id = 56 # int | A unique integer value identifying instance of this template sublist.
data = polemarch_client.TemplateExec() # TemplateExec | 

try:
    api_response = api_instance.project_template_execute(id, template_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_execute: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **template_id** | **int**| A unique integer value identifying instance of this template sublist. | 
 **data** | [**TemplateExec**](TemplateExec.md)|  | 

### Return type

[**ExecuteResponse**](ExecuteResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_get**
> OneTemplate project_template_get(id, template_id)



Return a execute template instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
template_id = 56 # int | A unique integer value identifying instance of this template sublist.

try:
    api_response = api_instance.project_template_get(id, template_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **template_id** | **int**| A unique integer value identifying instance of this template sublist. | 

### Return type

[**OneTemplate**](OneTemplate.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_list**
> InlineResponse20013 project_template_list(id, id2=id2, name=name, kind=kind, inventory=inventory, id__not=id__not, name__not=name__not, ordering=ordering, limit=limit, offset=offset)



Return all execute templates in project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
kind = 'kind_example' # str | A kind of template. (optional)
inventory = 'inventory_example' # str | The inventory id or path in project. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_template_list(id, id2=id2, name=name, kind=kind, inventory=inventory, id__not=id__not, name__not=name__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **kind** | **str**| A kind of template. | [optional] 
 **inventory** | **str**| The inventory id or path in project. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20013**](InlineResponse20013.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_remove**
> project_template_remove(id, template_id)



Remove an existing execute template.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
template_id = 56 # int | A unique integer value identifying instance of this template sublist.

try:
    api_instance.project_template_remove(id, template_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **template_id** | **int**| A unique integer value identifying instance of this template sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_template_update**
> OneTemplate project_template_update(id, template_id, data)



Update a execute template.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
template_id = 56 # int | A unique integer value identifying instance of this template sublist.
data = polemarch_client.OneTemplate() # OneTemplate | 

try:
    api_response = api_instance.project_template_update(id, template_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_template_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **template_id** | **int**| A unique integer value identifying instance of this template sublist. | 
 **data** | [**OneTemplate**](OneTemplate.md)|  | 

### Return type

[**OneTemplate**](OneTemplate.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_update**
> OneProject project_update(id, data)



Update a project.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.OneProject() # OneProject | 

try:
    api_response = api_instance.project_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**OneProject**](OneProject.md)|  | 

### Return type

[**OneProject**](OneProject.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_variables_add**
> ProjectVariable project_variables_add(id, data)



Create a new variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
data = polemarch_client.ProjectVariable() # ProjectVariable | 

try:
    api_response = api_instance.project_variables_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **data** | [**ProjectVariable**](ProjectVariable.md)|  | 

### Return type

[**ProjectVariable**](ProjectVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_variables_edit**
> ProjectVariable project_variables_edit(id, variables_id, data)



Update one or more fields on an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.ProjectVariable() # ProjectVariable | 

try:
    api_response = api_instance.project_variables_edit(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**ProjectVariable**](ProjectVariable.md)|  | 

### Return type

[**ProjectVariable**](ProjectVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_variables_get**
> ProjectVariable project_variables_get(id, variables_id)



Return a variable of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.project_variables_get(id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**ProjectVariable**](ProjectVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_variables_list**
> InlineResponse20014 project_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



Return all variables of instance.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.project_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **key** | **str**| A key name string value (or comma separated list) of instance. | [optional] 
 **value** | **str**| A value of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20014**](InlineResponse20014.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_variables_remove**
> project_variables_remove(id, variables_id)



Remove an existing variable.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.project_variables_remove(id, variables_id)
except ApiException as e:
    print("Exception when calling ProjectApi->project_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **project_variables_update**
> ProjectVariable project_variables_update(id, variables_id, data)



Update variable value.

### Example
```python
from __future__ import print_function
import time
import polemarch_client
from polemarch_client.rest import ApiException
from pprint import pprint

# Configure HTTP basic authorization: basic
configuration = polemarch_client.Configuration()
configuration.username = 'YOUR_USERNAME'
configuration.password = 'YOUR_PASSWORD'

# create an instance of the API class
api_instance = polemarch_client.ProjectApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this project.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.ProjectVariable() # ProjectVariable | 

try:
    api_response = api_instance.project_variables_update(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling ProjectApi->project_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this project. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 
 **data** | [**ProjectVariable**](ProjectVariable.md)|  | 

### Return type

[**ProjectVariable**](ProjectVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


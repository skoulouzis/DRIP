# polemarch_client.InventoryApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**inventory_add**](InventoryApi.md#inventory_add) | **POST** /inventory/ | 
[**inventory_all_groups_get**](InventoryApi.md#inventory_all_groups_get) | **GET** /inventory/{id}/all_groups/{all_groups_id}/ | 
[**inventory_all_groups_list**](InventoryApi.md#inventory_all_groups_list) | **GET** /inventory/{id}/all_groups/ | 
[**inventory_all_hosts_get**](InventoryApi.md#inventory_all_hosts_get) | **GET** /inventory/{id}/all_hosts/{all_hosts_id}/ | 
[**inventory_all_hosts_list**](InventoryApi.md#inventory_all_hosts_list) | **GET** /inventory/{id}/all_hosts/ | 
[**inventory_copy**](InventoryApi.md#inventory_copy) | **POST** /inventory/{id}/copy/ | 
[**inventory_edit**](InventoryApi.md#inventory_edit) | **PATCH** /inventory/{id}/ | 
[**inventory_get**](InventoryApi.md#inventory_get) | **GET** /inventory/{id}/ | 
[**inventory_group_add**](InventoryApi.md#inventory_group_add) | **POST** /inventory/{id}/group/ | 
[**inventory_group_copy**](InventoryApi.md#inventory_group_copy) | **POST** /inventory/{id}/group/{group_id}/copy/ | 
[**inventory_group_edit**](InventoryApi.md#inventory_group_edit) | **PATCH** /inventory/{id}/group/{group_id}/ | 
[**inventory_group_get**](InventoryApi.md#inventory_group_get) | **GET** /inventory/{id}/group/{group_id}/ | 
[**inventory_group_list**](InventoryApi.md#inventory_group_list) | **GET** /inventory/{id}/group/ | 
[**inventory_group_remove**](InventoryApi.md#inventory_group_remove) | **DELETE** /inventory/{id}/group/{group_id}/ | 
[**inventory_group_set_owner**](InventoryApi.md#inventory_group_set_owner) | **POST** /inventory/{id}/group/{group_id}/set_owner/ | 
[**inventory_group_update**](InventoryApi.md#inventory_group_update) | **PUT** /inventory/{id}/group/{group_id}/ | 
[**inventory_group_variables_add**](InventoryApi.md#inventory_group_variables_add) | **POST** /inventory/{id}/group/{group_id}/variables/ | 
[**inventory_group_variables_edit**](InventoryApi.md#inventory_group_variables_edit) | **PATCH** /inventory/{id}/group/{group_id}/variables/{variables_id}/ | 
[**inventory_group_variables_get**](InventoryApi.md#inventory_group_variables_get) | **GET** /inventory/{id}/group/{group_id}/variables/{variables_id}/ | 
[**inventory_group_variables_list**](InventoryApi.md#inventory_group_variables_list) | **GET** /inventory/{id}/group/{group_id}/variables/ | 
[**inventory_group_variables_remove**](InventoryApi.md#inventory_group_variables_remove) | **DELETE** /inventory/{id}/group/{group_id}/variables/{variables_id}/ | 
[**inventory_group_variables_update**](InventoryApi.md#inventory_group_variables_update) | **PUT** /inventory/{id}/group/{group_id}/variables/{variables_id}/ | 
[**inventory_host_add**](InventoryApi.md#inventory_host_add) | **POST** /inventory/{id}/host/ | 
[**inventory_host_copy**](InventoryApi.md#inventory_host_copy) | **POST** /inventory/{id}/host/{host_id}/copy/ | 
[**inventory_host_edit**](InventoryApi.md#inventory_host_edit) | **PATCH** /inventory/{id}/host/{host_id}/ | 
[**inventory_host_get**](InventoryApi.md#inventory_host_get) | **GET** /inventory/{id}/host/{host_id}/ | 
[**inventory_host_list**](InventoryApi.md#inventory_host_list) | **GET** /inventory/{id}/host/ | 
[**inventory_host_remove**](InventoryApi.md#inventory_host_remove) | **DELETE** /inventory/{id}/host/{host_id}/ | 
[**inventory_host_set_owner**](InventoryApi.md#inventory_host_set_owner) | **POST** /inventory/{id}/host/{host_id}/set_owner/ | 
[**inventory_host_update**](InventoryApi.md#inventory_host_update) | **PUT** /inventory/{id}/host/{host_id}/ | 
[**inventory_host_variables_add**](InventoryApi.md#inventory_host_variables_add) | **POST** /inventory/{id}/host/{host_id}/variables/ | 
[**inventory_host_variables_edit**](InventoryApi.md#inventory_host_variables_edit) | **PATCH** /inventory/{id}/host/{host_id}/variables/{variables_id}/ | 
[**inventory_host_variables_get**](InventoryApi.md#inventory_host_variables_get) | **GET** /inventory/{id}/host/{host_id}/variables/{variables_id}/ | 
[**inventory_host_variables_list**](InventoryApi.md#inventory_host_variables_list) | **GET** /inventory/{id}/host/{host_id}/variables/ | 
[**inventory_host_variables_remove**](InventoryApi.md#inventory_host_variables_remove) | **DELETE** /inventory/{id}/host/{host_id}/variables/{variables_id}/ | 
[**inventory_host_variables_update**](InventoryApi.md#inventory_host_variables_update) | **PUT** /inventory/{id}/host/{host_id}/variables/{variables_id}/ | 
[**inventory_import_inventory**](InventoryApi.md#inventory_import_inventory) | **POST** /inventory/import_inventory/ | 
[**inventory_list**](InventoryApi.md#inventory_list) | **GET** /inventory/ | 
[**inventory_remove**](InventoryApi.md#inventory_remove) | **DELETE** /inventory/{id}/ | 
[**inventory_set_owner**](InventoryApi.md#inventory_set_owner) | **POST** /inventory/{id}/set_owner/ | 
[**inventory_update**](InventoryApi.md#inventory_update) | **PUT** /inventory/{id}/ | 
[**inventory_variables_add**](InventoryApi.md#inventory_variables_add) | **POST** /inventory/{id}/variables/ | 
[**inventory_variables_edit**](InventoryApi.md#inventory_variables_edit) | **PATCH** /inventory/{id}/variables/{variables_id}/ | 
[**inventory_variables_get**](InventoryApi.md#inventory_variables_get) | **GET** /inventory/{id}/variables/{variables_id}/ | 
[**inventory_variables_list**](InventoryApi.md#inventory_variables_list) | **GET** /inventory/{id}/variables/ | 
[**inventory_variables_remove**](InventoryApi.md#inventory_variables_remove) | **DELETE** /inventory/{id}/variables/{variables_id}/ | 
[**inventory_variables_update**](InventoryApi.md#inventory_variables_update) | **PUT** /inventory/{id}/variables/{variables_id}/ | 


# **inventory_add**
> OneInventory inventory_add(data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.OneInventory() # OneInventory | 

try:
    api_response = api_instance.inventory_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**OneInventory**](OneInventory.md)|  | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_all_groups_get**
> OneGroup inventory_all_groups_get(all_groups_id, id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
all_groups_id = 'all_groups_id_example' # str | 
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_response = api_instance.inventory_all_groups_get(all_groups_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_all_groups_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **all_groups_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_all_groups_list**
> InlineResponse2001 inventory_all_groups_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.inventory_all_groups_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_all_groups_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_all_hosts_get**
> OneHost inventory_all_hosts_get(all_hosts_id, id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
all_hosts_id = 'all_hosts_id_example' # str | 
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_response = api_instance.inventory_all_hosts_get(all_hosts_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_all_hosts_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **all_hosts_id** | **str**|  | 
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_all_hosts_list**
> InlineResponse2003 inventory_all_hosts_list(id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
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
    api_response = api_instance.inventory_all_hosts_list(id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_all_hosts_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_copy**
> Inventory inventory_copy(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.Inventory() # Inventory | 

try:
    api_response = api_instance.inventory_copy(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**Inventory**](Inventory.md)|  | 

### Return type

[**Inventory**](Inventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_edit**
> OneInventory inventory_edit(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneInventory() # OneInventory | 

try:
    api_response = api_instance.inventory_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneInventory**](OneInventory.md)|  | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_get**
> OneInventory inventory_get(id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_response = api_instance.inventory_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_add**
> GroupCreateMaster inventory_group_add(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.GroupCreateMaster() # GroupCreateMaster | 

try:
    api_response = api_instance.inventory_group_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**GroupCreateMaster**](GroupCreateMaster.md)|  | 

### Return type

[**GroupCreateMaster**](GroupCreateMaster.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_copy**
> Group inventory_group_copy(group_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.Group() # Group | 

try:
    api_response = api_instance.inventory_group_copy(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**Group**](Group.md)|  | 

### Return type

[**Group**](Group.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_edit**
> OneGroup inventory_group_edit(group_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.inventory_group_edit(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_get**
> OneGroup inventory_group_get(group_id, id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_response = api_instance.inventory_group_get(group_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_list**
> InlineResponse2001 inventory_group_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.inventory_group_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_group_remove**
> inventory_group_remove(group_id, id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_instance.inventory_group_remove(group_id, id)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_set_owner**
> SetOwner inventory_group_set_owner(group_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.inventory_group_set_owner(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_update**
> OneGroup inventory_group_update(group_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.inventory_group_update(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_variables_add**
> InventoryVariable inventory_group_variables_add(group_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_group_variables_add(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_variables_edit**
> InventoryVariable inventory_group_variables_edit(group_id, id, variables_id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_group_variables_edit(group_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_group_variables_get**
> InventoryVariable inventory_group_variables_get(group_id, id, variables_id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.inventory_group_variables_get(group_id, id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_variables_list**
> InlineResponse2002 inventory_group_variables_list(group_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.inventory_group_variables_list(group_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_group_variables_remove**
> inventory_group_variables_remove(group_id, id, variables_id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.inventory_group_variables_remove(group_id, id, variables_id)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_group_variables_update**
> InventoryVariable inventory_group_variables_update(group_id, id, variables_id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_group_variables_update(group_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_group_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_host_add**
> OneHost inventory_host_add(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.inventory_host_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_copy**
> Host inventory_host_copy(host_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.Host() # Host | 

try:
    api_response = api_instance.inventory_host_copy(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**Host**](Host.md)|  | 

### Return type

[**Host**](Host.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_edit**
> OneHost inventory_host_edit(host_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.inventory_host_edit(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_get**
> OneHost inventory_host_get(host_id, id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_response = api_instance.inventory_host_get(host_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_list**
> InlineResponse2003 inventory_host_list(id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
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
    api_response = api_instance.inventory_host_list(id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_host_remove**
> inventory_host_remove(host_id, id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_instance.inventory_host_remove(host_id, id)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_set_owner**
> SetOwner inventory_host_set_owner(host_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.inventory_host_set_owner(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_update**
> OneHost inventory_host_update(host_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.inventory_host_update(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_variables_add**
> InventoryVariable inventory_host_variables_add(host_id, id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_host_variables_add(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_variables_edit**
> InventoryVariable inventory_host_variables_edit(host_id, id, variables_id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_host_variables_edit(host_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_host_variables_get**
> InventoryVariable inventory_host_variables_get(host_id, id, variables_id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.inventory_host_variables_get(host_id, id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_variables_list**
> InlineResponse2002 inventory_host_variables_list(host_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.inventory_host_variables_list(host_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_host_variables_remove**
> inventory_host_variables_remove(host_id, id, variables_id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.inventory_host_variables_remove(host_id, id, variables_id)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_host_variables_update**
> InventoryVariable inventory_host_variables_update(host_id, id, variables_id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_host_variables_update(host_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_host_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_import_inventory**
> InventoryImport inventory_import_inventory(data)





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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.InventoryImport() # InventoryImport | 

try:
    api_response = api_instance.inventory_import_inventory(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_import_inventory: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**InventoryImport**](InventoryImport.md)|  | 

### Return type

[**InventoryImport**](InventoryImport.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_list**
> InlineResponse2006 inventory_list(id=id, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.inventory_list(id=id, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
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

# **inventory_remove**
> inventory_remove(id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.

try:
    api_instance.inventory_remove(id)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_set_owner**
> SetOwner inventory_set_owner(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.inventory_set_owner(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_update**
> OneInventory inventory_update(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.OneInventory() # OneInventory | 

try:
    api_response = api_instance.inventory_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**OneInventory**](OneInventory.md)|  | 

### Return type

[**OneInventory**](OneInventory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_variables_add**
> InventoryVariable inventory_variables_add(id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_variables_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_variables_edit**
> InventoryVariable inventory_variables_edit(id, variables_id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_variables_edit(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_variables_get**
> InventoryVariable inventory_variables_get(id, variables_id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.inventory_variables_get(id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_variables_list**
> InlineResponse2002 inventory_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.inventory_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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

# **inventory_variables_remove**
> inventory_variables_remove(id, variables_id)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.inventory_variables_remove(id, variables_id)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **inventory_variables_update**
> InventoryVariable inventory_variables_update(id, variables_id, data)



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
api_instance = polemarch_client.InventoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this inventory.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.inventory_variables_update(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling InventoryApi->inventory_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this inventory. | 
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


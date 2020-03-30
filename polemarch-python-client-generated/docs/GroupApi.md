# polemarch_client.GroupApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**group_add**](GroupApi.md#group_add) | **POST** /group/ | 
[**group_copy**](GroupApi.md#group_copy) | **POST** /group/{id}/copy/ | 
[**group_edit**](GroupApi.md#group_edit) | **PATCH** /group/{id}/ | 
[**group_get**](GroupApi.md#group_get) | **GET** /group/{id}/ | 
[**group_group_add**](GroupApi.md#group_group_add) | **POST** /group/{id}/group/ | 
[**group_group_copy**](GroupApi.md#group_group_copy) | **POST** /group/{id}/group/{group_id}/copy/ | 
[**group_group_edit**](GroupApi.md#group_group_edit) | **PATCH** /group/{id}/group/{group_id}/ | 
[**group_group_get**](GroupApi.md#group_group_get) | **GET** /group/{id}/group/{group_id}/ | 
[**group_group_list**](GroupApi.md#group_group_list) | **GET** /group/{id}/group/ | 
[**group_group_remove**](GroupApi.md#group_group_remove) | **DELETE** /group/{id}/group/{group_id}/ | 
[**group_group_set_owner**](GroupApi.md#group_group_set_owner) | **POST** /group/{id}/group/{group_id}/set_owner/ | 
[**group_group_update**](GroupApi.md#group_group_update) | **PUT** /group/{id}/group/{group_id}/ | 
[**group_group_variables_add**](GroupApi.md#group_group_variables_add) | **POST** /group/{id}/group/{group_id}/variables/ | 
[**group_group_variables_edit**](GroupApi.md#group_group_variables_edit) | **PATCH** /group/{id}/group/{group_id}/variables/{variables_id}/ | 
[**group_group_variables_get**](GroupApi.md#group_group_variables_get) | **GET** /group/{id}/group/{group_id}/variables/{variables_id}/ | 
[**group_group_variables_list**](GroupApi.md#group_group_variables_list) | **GET** /group/{id}/group/{group_id}/variables/ | 
[**group_group_variables_remove**](GroupApi.md#group_group_variables_remove) | **DELETE** /group/{id}/group/{group_id}/variables/{variables_id}/ | 
[**group_group_variables_update**](GroupApi.md#group_group_variables_update) | **PUT** /group/{id}/group/{group_id}/variables/{variables_id}/ | 
[**group_host_add**](GroupApi.md#group_host_add) | **POST** /group/{id}/host/ | 
[**group_host_copy**](GroupApi.md#group_host_copy) | **POST** /group/{id}/host/{host_id}/copy/ | 
[**group_host_edit**](GroupApi.md#group_host_edit) | **PATCH** /group/{id}/host/{host_id}/ | 
[**group_host_get**](GroupApi.md#group_host_get) | **GET** /group/{id}/host/{host_id}/ | 
[**group_host_list**](GroupApi.md#group_host_list) | **GET** /group/{id}/host/ | 
[**group_host_remove**](GroupApi.md#group_host_remove) | **DELETE** /group/{id}/host/{host_id}/ | 
[**group_host_set_owner**](GroupApi.md#group_host_set_owner) | **POST** /group/{id}/host/{host_id}/set_owner/ | 
[**group_host_update**](GroupApi.md#group_host_update) | **PUT** /group/{id}/host/{host_id}/ | 
[**group_host_variables_add**](GroupApi.md#group_host_variables_add) | **POST** /group/{id}/host/{host_id}/variables/ | 
[**group_host_variables_edit**](GroupApi.md#group_host_variables_edit) | **PATCH** /group/{id}/host/{host_id}/variables/{variables_id}/ | 
[**group_host_variables_get**](GroupApi.md#group_host_variables_get) | **GET** /group/{id}/host/{host_id}/variables/{variables_id}/ | 
[**group_host_variables_list**](GroupApi.md#group_host_variables_list) | **GET** /group/{id}/host/{host_id}/variables/ | 
[**group_host_variables_remove**](GroupApi.md#group_host_variables_remove) | **DELETE** /group/{id}/host/{host_id}/variables/{variables_id}/ | 
[**group_host_variables_update**](GroupApi.md#group_host_variables_update) | **PUT** /group/{id}/host/{host_id}/variables/{variables_id}/ | 
[**group_list**](GroupApi.md#group_list) | **GET** /group/ | 
[**group_remove**](GroupApi.md#group_remove) | **DELETE** /group/{id}/ | 
[**group_set_owner**](GroupApi.md#group_set_owner) | **POST** /group/{id}/set_owner/ | 
[**group_update**](GroupApi.md#group_update) | **PUT** /group/{id}/ | 
[**group_variables_add**](GroupApi.md#group_variables_add) | **POST** /group/{id}/variables/ | 
[**group_variables_edit**](GroupApi.md#group_variables_edit) | **PATCH** /group/{id}/variables/{variables_id}/ | 
[**group_variables_get**](GroupApi.md#group_variables_get) | **GET** /group/{id}/variables/{variables_id}/ | 
[**group_variables_list**](GroupApi.md#group_variables_list) | **GET** /group/{id}/variables/ | 
[**group_variables_remove**](GroupApi.md#group_variables_remove) | **DELETE** /group/{id}/variables/{variables_id}/ | 
[**group_variables_update**](GroupApi.md#group_variables_update) | **PUT** /group/{id}/variables/{variables_id}/ | 


# **group_add**
> GroupCreateMaster group_add(data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.GroupCreateMaster() # GroupCreateMaster | 

try:
    api_response = api_instance.group_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**GroupCreateMaster**](GroupCreateMaster.md)|  | 

### Return type

[**GroupCreateMaster**](GroupCreateMaster.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_copy**
> Group group_copy(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.Group() # Group | 

try:
    api_response = api_instance.group_copy(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**Group**](Group.md)|  | 

### Return type

[**Group**](Group.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_edit**
> OneGroup group_edit(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.group_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_get**
> OneGroup group_get(id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.

try:
    api_response = api_instance.group_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_add**
> GroupCreateMaster group_group_add(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.GroupCreateMaster() # GroupCreateMaster | 

try:
    api_response = api_instance.group_group_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**GroupCreateMaster**](GroupCreateMaster.md)|  | 

### Return type

[**GroupCreateMaster**](GroupCreateMaster.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_copy**
> Group group_group_copy(group_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.Group() # Group | 

try:
    api_response = api_instance.group_group_copy(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**Group**](Group.md)|  | 

### Return type

[**Group**](Group.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_edit**
> OneGroup group_group_edit(group_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.group_group_edit(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_get**
> OneGroup group_group_get(group_id, id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.

try:
    api_response = api_instance.group_group_get(group_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_list**
> InlineResponse2001 group_group_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.group_group_list(id, id2=id2, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_group_remove**
> group_group_remove(group_id, id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.

try:
    api_instance.group_group_remove(group_id, id)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_set_owner**
> SetOwner group_group_set_owner(group_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.group_group_set_owner(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_update**
> OneGroup group_group_update(group_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.group_group_update(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_variables_add**
> InventoryVariable group_group_variables_add(group_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_group_variables_add(group_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_variables_edit**
> InventoryVariable group_group_variables_edit(group_id, id, variables_id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_group_variables_edit(group_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_group_variables_get**
> InventoryVariable group_group_variables_get(group_id, id, variables_id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.group_group_variables_get(group_id, id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_variables_list**
> InlineResponse2002 group_group_variables_list(group_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.group_group_variables_list(group_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_group_variables_remove**
> group_group_variables_remove(group_id, id, variables_id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.group_group_variables_remove(group_id, id, variables_id)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_group_variables_update**
> InventoryVariable group_group_variables_update(group_id, id, variables_id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
group_id = 56 # int | A unique integer value identifying instance of this groups sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_group_variables_update(group_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_group_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **group_id** | **int**| A unique integer value identifying instance of this groups sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_host_add**
> OneHost group_host_add(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.group_host_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_copy**
> Host group_host_copy(host_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.Host() # Host | 

try:
    api_response = api_instance.group_host_copy(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**Host**](Host.md)|  | 

### Return type

[**Host**](Host.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_edit**
> OneHost group_host_edit(host_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.group_host_edit(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_get**
> OneHost group_host_get(host_id, id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.

try:
    api_response = api_instance.group_host_get(host_id, id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_list**
> InlineResponse2003 group_host_list(id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
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
    api_response = api_instance.group_host_list(id, id2=id2, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_host_remove**
> group_host_remove(host_id, id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.

try:
    api_instance.group_host_remove(host_id, id)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_set_owner**
> SetOwner group_host_set_owner(host_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.group_host_set_owner(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_update**
> OneHost group_host_update(host_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.group_host_update(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_variables_add**
> InventoryVariable group_host_variables_add(host_id, id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_host_variables_add(host_id, id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_variables_edit**
> InventoryVariable group_host_variables_edit(host_id, id, variables_id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_host_variables_edit(host_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_host_variables_get**
> InventoryVariable group_host_variables_get(host_id, id, variables_id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.group_host_variables_get(host_id, id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_variables_list**
> InlineResponse2002 group_host_variables_list(host_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.group_host_variables_list(host_id, id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_host_variables_remove**
> group_host_variables_remove(host_id, id, variables_id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.group_host_variables_remove(host_id, id, variables_id)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_host_variables_update**
> InventoryVariable group_host_variables_update(host_id, id, variables_id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
host_id = 56 # int | A unique integer value identifying instance of this hosts sublist.
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_host_variables_update(host_id, id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_host_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **host_id** | **int**| A unique integer value identifying instance of this hosts sublist. | 
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_list**
> InlineResponse2001 group_list(id=id, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.group_list(id=id, name=name, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_list: %s\n" % e)
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

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_remove**
> group_remove(id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.

try:
    api_instance.group_remove(id)
except ApiException as e:
    print("Exception when calling GroupApi->group_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_set_owner**
> SetOwner group_set_owner(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.group_set_owner(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_update**
> OneGroup group_update(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.OneGroup() # OneGroup | 

try:
    api_response = api_instance.group_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**OneGroup**](OneGroup.md)|  | 

### Return type

[**OneGroup**](OneGroup.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_variables_add**
> InventoryVariable group_variables_add(id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_variables_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_variables_edit**
> InventoryVariable group_variables_edit(id, variables_id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_variables_edit(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_variables_get**
> InventoryVariable group_variables_get(id, variables_id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.group_variables_get(id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_variables_list**
> InlineResponse2002 group_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.group_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
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

# **group_variables_remove**
> group_variables_remove(id, variables_id)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.group_variables_remove(id, variables_id)
except ApiException as e:
    print("Exception when calling GroupApi->group_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **group_variables_update**
> InventoryVariable group_variables_update(id, variables_id, data)



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
api_instance = polemarch_client.GroupApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this group.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.group_variables_update(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling GroupApi->group_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this group. | 
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


# polemarch_client.HostApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**host_add**](HostApi.md#host_add) | **POST** /host/ | 
[**host_copy**](HostApi.md#host_copy) | **POST** /host/{id}/copy/ | 
[**host_edit**](HostApi.md#host_edit) | **PATCH** /host/{id}/ | 
[**host_get**](HostApi.md#host_get) | **GET** /host/{id}/ | 
[**host_list**](HostApi.md#host_list) | **GET** /host/ | 
[**host_remove**](HostApi.md#host_remove) | **DELETE** /host/{id}/ | 
[**host_set_owner**](HostApi.md#host_set_owner) | **POST** /host/{id}/set_owner/ | 
[**host_update**](HostApi.md#host_update) | **PUT** /host/{id}/ | 
[**host_variables_add**](HostApi.md#host_variables_add) | **POST** /host/{id}/variables/ | 
[**host_variables_edit**](HostApi.md#host_variables_edit) | **PATCH** /host/{id}/variables/{variables_id}/ | 
[**host_variables_get**](HostApi.md#host_variables_get) | **GET** /host/{id}/variables/{variables_id}/ | 
[**host_variables_list**](HostApi.md#host_variables_list) | **GET** /host/{id}/variables/ | 
[**host_variables_remove**](HostApi.md#host_variables_remove) | **DELETE** /host/{id}/variables/{variables_id}/ | 
[**host_variables_update**](HostApi.md#host_variables_update) | **PUT** /host/{id}/variables/{variables_id}/ | 


# **host_add**
> OneHost host_add(data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.host_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_copy**
> Host host_copy(id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
data = polemarch_client.Host() # Host | 

try:
    api_response = api_instance.host_copy(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **data** | [**Host**](Host.md)|  | 

### Return type

[**Host**](Host.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_edit**
> OneHost host_edit(id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.host_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_get**
> OneHost host_get(id)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.

try:
    api_response = api_instance.host_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_list**
> InlineResponse2003 host_list(id=id, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
type = 'type_example' # str | Instance type. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
variables = 'variables_example' # str | List of variables to filter. Comma separeted \"key:value\" list. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.host_list(id=id, name=name, type=type, id__not=id__not, name__not=name__not, variables=variables, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
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

# **host_remove**
> host_remove(id)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.

try:
    api_instance.host_remove(id)
except ApiException as e:
    print("Exception when calling HostApi->host_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_set_owner**
> SetOwner host_set_owner(id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.host_set_owner(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_update**
> OneHost host_update(id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
data = polemarch_client.OneHost() # OneHost | 

try:
    api_response = api_instance.host_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **data** | [**OneHost**](OneHost.md)|  | 

### Return type

[**OneHost**](OneHost.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_variables_add**
> InventoryVariable host_variables_add(id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.host_variables_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_variables_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **data** | [**InventoryVariable**](InventoryVariable.md)|  | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_variables_edit**
> InventoryVariable host_variables_edit(id, variables_id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.host_variables_edit(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_variables_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
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

# **host_variables_get**
> InventoryVariable host_variables_get(id, variables_id)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_response = api_instance.host_variables_get(id, variables_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_variables_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

[**InventoryVariable**](InventoryVariable.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_variables_list**
> InlineResponse2002 host_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
key = 'key_example' # str | A key name string value (or comma separated list) of instance. (optional)
value = 'value_example' # str | A value of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.host_variables_list(id, id2=id2, key=key, value=value, id__not=id__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_variables_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
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

# **host_variables_remove**
> host_variables_remove(id, variables_id)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.

try:
    api_instance.host_variables_remove(id, variables_id)
except ApiException as e:
    print("Exception when calling HostApi->host_variables_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
 **variables_id** | **int**| A unique integer value identifying instance of this variables sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **host_variables_update**
> InventoryVariable host_variables_update(id, variables_id, data)



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
api_instance = polemarch_client.HostApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this host.
variables_id = 56 # int | A unique integer value identifying instance of this variables sublist.
data = polemarch_client.InventoryVariable() # InventoryVariable | 

try:
    api_response = api_instance.host_variables_update(id, variables_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HostApi->host_variables_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this host. | 
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


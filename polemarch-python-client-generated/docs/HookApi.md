# polemarch_client.HookApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**hook_add**](HookApi.md#hook_add) | **POST** /hook/ | 
[**hook_edit**](HookApi.md#hook_edit) | **PATCH** /hook/{id}/ | 
[**hook_get**](HookApi.md#hook_get) | **GET** /hook/{id}/ | 
[**hook_list**](HookApi.md#hook_list) | **GET** /hook/ | 
[**hook_remove**](HookApi.md#hook_remove) | **DELETE** /hook/{id}/ | 
[**hook_update**](HookApi.md#hook_update) | **PUT** /hook/{id}/ | 


# **hook_add**
> Hook hook_add(data)



Create a new hook.

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
api_instance = polemarch_client.HookApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.Hook() # Hook | 

try:
    api_response = api_instance.hook_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HookApi->hook_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**Hook**](Hook.md)|  | 

### Return type

[**Hook**](Hook.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **hook_edit**
> Hook hook_edit(id, data)



Update one or more fields on an existing hook.

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
api_instance = polemarch_client.HookApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this hook.
data = polemarch_client.Hook() # Hook | 

try:
    api_response = api_instance.hook_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HookApi->hook_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this hook. | 
 **data** | [**Hook**](Hook.md)|  | 

### Return type

[**Hook**](Hook.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **hook_get**
> Hook hook_get(id)



Return a hook instance.

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
api_instance = polemarch_client.HookApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this hook.

try:
    api_response = api_instance.hook_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HookApi->hook_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this hook. | 

### Return type

[**Hook**](Hook.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **hook_list**
> InlineResponse2005 hook_list(id=id, name=name, type=type, id__not=id__not, name__not=name__not, ordering=ordering, limit=limit, offset=offset)



Return all hooks.

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
api_instance = polemarch_client.HookApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
type = 'type_example' # str | Instance type. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.hook_list(id=id, name=name, type=type, id__not=id__not, name__not=name__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HookApi->hook_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **type** | **str**| Instance type. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse2005**](InlineResponse2005.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **hook_remove**
> hook_remove(id)



Remove an existing hook.

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
api_instance = polemarch_client.HookApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this hook.

try:
    api_instance.hook_remove(id)
except ApiException as e:
    print("Exception when calling HookApi->hook_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this hook. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **hook_update**
> Hook hook_update(id, data)



Update a hook.

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
api_instance = polemarch_client.HookApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this hook.
data = polemarch_client.Hook() # Hook | 

try:
    api_response = api_instance.hook_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HookApi->hook_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this hook. | 
 **data** | [**Hook**](Hook.md)|  | 

### Return type

[**Hook**](Hook.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


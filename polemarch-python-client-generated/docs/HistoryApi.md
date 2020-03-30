# polemarch_client.HistoryApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**history_cancel**](HistoryApi.md#history_cancel) | **POST** /history/{id}/cancel/ | 
[**history_clear**](HistoryApi.md#history_clear) | **DELETE** /history/{id}/clear/ | 
[**history_facts**](HistoryApi.md#history_facts) | **GET** /history/{id}/facts/ | 
[**history_get**](HistoryApi.md#history_get) | **GET** /history/{id}/ | 
[**history_list**](HistoryApi.md#history_list) | **GET** /history/ | 
[**history_remove**](HistoryApi.md#history_remove) | **DELETE** /history/{id}/ | 


# **history_cancel**
> ActionResponse history_cancel(id, data)



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
api_instance = polemarch_client.HistoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this history.
data = polemarch_client.Empty() # Empty | 

try:
    api_response = api_instance.history_cancel(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HistoryApi->history_cancel: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this history. | 
 **data** | [**Empty**](Empty.md)|  | 

### Return type

[**ActionResponse**](ActionResponse.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **history_clear**
> history_clear(id)



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
api_instance = polemarch_client.HistoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this history.

try:
    api_instance.history_clear(id)
except ApiException as e:
    print("Exception when calling HistoryApi->history_clear: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this history. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **history_facts**
> Data history_facts(id)



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
api_instance = polemarch_client.HistoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this history.

try:
    api_response = api_instance.history_facts(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HistoryApi->history_facts: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this history. | 

### Return type

[**Data**](Data.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **history_get**
> OneHistory history_get(id)



Return a execution history instance.

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
api_instance = polemarch_client.HistoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this history.

try:
    api_response = api_instance.history_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HistoryApi->history_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this history. | 

### Return type

[**OneHistory**](OneHistory.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **history_list**
> InlineResponse2004 history_list(id=id, mode=mode, kind=kind, status=status, id__not=id__not, name__not=name__not, name=name, older=older, newer=newer, ordering=ordering, limit=limit, offset=offset)



Return all history of executions.

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
api_instance = polemarch_client.HistoryApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
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
    api_response = api_instance.history_list(id=id, mode=mode, kind=kind, status=status, id__not=id__not, name__not=name__not, name=name, older=older, newer=newer, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling HistoryApi->history_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
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

[**InlineResponse2004**](InlineResponse2004.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **history_remove**
> history_remove(id)



Remove an existing history record.

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
api_instance = polemarch_client.HistoryApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this history.

try:
    api_instance.history_remove(id)
except ApiException as e:
    print("Exception when calling HistoryApi->history_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this history. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


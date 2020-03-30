# polemarch_client.CommunityTemplateApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**community_template_get**](CommunityTemplateApi.md#community_template_get) | **GET** /community_template/{id}/ | 
[**community_template_list**](CommunityTemplateApi.md#community_template_list) | **GET** /community_template/ | 
[**community_template_use_it**](CommunityTemplateApi.md#community_template_use_it) | **POST** /community_template/{id}/use_it/ | 


# **community_template_get**
> OneProjectTemplate community_template_get(id)



Return a community project template instance.

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
api_instance = polemarch_client.CommunityTemplateApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique value identifying this project template.

try:
    api_response = api_instance.community_template_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CommunityTemplateApi->community_template_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique value identifying this project template. | 

### Return type

[**OneProjectTemplate**](OneProjectTemplate.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **community_template_list**
> InlineResponse200 community_template_list(ordering=ordering, limit=limit, offset=offset)



List of community project templates.

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
api_instance = polemarch_client.CommunityTemplateApi(polemarch_client.ApiClient(configuration))
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.community_template_list(ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CommunityTemplateApi->community_template_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **community_template_use_it**
> ProjectTemplateCreate community_template_use_it(id, data)



Create project based on this template.

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
api_instance = polemarch_client.CommunityTemplateApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique value identifying this project template.
data = polemarch_client.ProjectTemplateCreate() # ProjectTemplateCreate | 

try:
    api_response = api_instance.community_template_use_it(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling CommunityTemplateApi->community_template_use_it: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique value identifying this project template. | 
 **data** | [**ProjectTemplateCreate**](ProjectTemplateCreate.md)|  | 

### Return type

[**ProjectTemplateCreate**](ProjectTemplateCreate.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


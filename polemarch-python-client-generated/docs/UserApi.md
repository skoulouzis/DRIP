# polemarch_client.UserApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**user_add**](UserApi.md#user_add) | **POST** /user/ | 
[**user_change_password**](UserApi.md#user_change_password) | **POST** /user/{id}/change_password/ | 
[**user_copy**](UserApi.md#user_copy) | **POST** /user/{id}/copy/ | 
[**user_edit**](UserApi.md#user_edit) | **PATCH** /user/{id}/ | 
[**user_get**](UserApi.md#user_get) | **GET** /user/{id}/ | 
[**user_list**](UserApi.md#user_list) | **GET** /user/ | 
[**user_remove**](UserApi.md#user_remove) | **DELETE** /user/{id}/ | 
[**user_settings_add**](UserApi.md#user_settings_add) | **POST** /user/{id}/settings/ | 
[**user_settings_get**](UserApi.md#user_settings_get) | **GET** /user/{id}/settings/ | 
[**user_settings_remove**](UserApi.md#user_settings_remove) | **DELETE** /user/{id}/settings/ | 
[**user_update**](UserApi.md#user_update) | **PUT** /user/{id}/ | 


# **user_add**
> CreateUser user_add(data)



Create a new user.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.CreateUser() # CreateUser | 

try:
    api_response = api_instance.user_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CreateUser**](CreateUser.md)|  | 

### Return type

[**CreateUser**](CreateUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_change_password**
> ChangePassword user_change_password(id, data)





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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.
data = polemarch_client.ChangePassword() # ChangePassword | 

try:
    api_response = api_instance.user_change_password(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_change_password: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 
 **data** | [**ChangePassword**](ChangePassword.md)|  | 

### Return type

[**ChangePassword**](ChangePassword.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_copy**
> User user_copy(id, data)



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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.
data = polemarch_client.User() # User | 

try:
    api_response = api_instance.user_copy(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 
 **data** | [**User**](User.md)|  | 

### Return type

[**User**](User.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_edit**
> OneUser user_edit(id, data)



Update one or more fields on an existing user.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.
data = polemarch_client.OneUser() # OneUser | 

try:
    api_response = api_instance.user_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 
 **data** | [**OneUser**](OneUser.md)|  | 

### Return type

[**OneUser**](OneUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_get**
> OneUser user_get(id)



Return a user instance.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.

try:
    api_response = api_instance.user_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 

### Return type

[**OneUser**](OneUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_list**
> InlineResponse20016 user_list(id=id, username=username, is_active=is_active, first_name=first_name, last_name=last_name, email=email, id__not=id__not, username__not=username__not, ordering=ordering, limit=limit, offset=offset)



Return all users.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
username = 'username_example' # str | A name string value (or comma separated list) of instance. (optional)
is_active = 'is_active_example' # str | Boolean value meaning status of user. (optional)
first_name = 'first_name_example' # str | Users first name. (optional)
last_name = 'last_name_example' # str | Users last name. (optional)
email = 'email_example' # str | Users e-mail value. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
username__not = 'username__not_example' # str | A name string value (or comma separated list) of instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.user_list(id=id, username=username, is_active=is_active, first_name=first_name, last_name=last_name, email=email, id__not=id__not, username__not=username__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **username** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **is_active** | **str**| Boolean value meaning status of user. | [optional] 
 **first_name** | **str**| Users first name. | [optional] 
 **last_name** | **str**| Users last name. | [optional] 
 **email** | **str**| Users e-mail value. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **username__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20016**](InlineResponse20016.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_remove**
> user_remove(id)



Remove an existing user.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.

try:
    api_instance.user_remove(id)
except ApiException as e:
    print("Exception when calling UserApi->user_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_settings_add**
> UserSettings user_settings_add(id, data)



Return user settings.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.
data = polemarch_client.UserSettings() # UserSettings | 

try:
    api_response = api_instance.user_settings_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_settings_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 
 **data** | [**UserSettings**](UserSettings.md)|  | 

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_settings_get**
> UserSettings user_settings_get(id)



Return user settings.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.

try:
    api_response = api_instance.user_settings_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_settings_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_settings_remove**
> user_settings_remove(id)



Return user settings.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.

try:
    api_instance.user_settings_remove(id)
except ApiException as e:
    print("Exception when calling UserApi->user_settings_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **user_update**
> OneUser user_update(id, data)



Update a user.

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
api_instance = polemarch_client.UserApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user.
data = polemarch_client.OneUser() # OneUser | 

try:
    api_response = api_instance.user_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling UserApi->user_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user. | 
 **data** | [**OneUser**](OneUser.md)|  | 

### Return type

[**OneUser**](OneUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


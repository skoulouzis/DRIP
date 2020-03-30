# polemarch_client.TeamApi

All URIs are relative to *http://localhost:30001/api/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**team_add**](TeamApi.md#team_add) | **POST** /team/ | 
[**team_copy**](TeamApi.md#team_copy) | **POST** /team/{id}/copy/ | 
[**team_edit**](TeamApi.md#team_edit) | **PATCH** /team/{id}/ | 
[**team_get**](TeamApi.md#team_get) | **GET** /team/{id}/ | 
[**team_list**](TeamApi.md#team_list) | **GET** /team/ | 
[**team_remove**](TeamApi.md#team_remove) | **DELETE** /team/{id}/ | 
[**team_set_owner**](TeamApi.md#team_set_owner) | **POST** /team/{id}/set_owner/ | 
[**team_update**](TeamApi.md#team_update) | **PUT** /team/{id}/ | 
[**team_user_add**](TeamApi.md#team_user_add) | **POST** /team/{id}/user/ | 
[**team_user_change_password**](TeamApi.md#team_user_change_password) | **POST** /team/{id}/user/{user_id}/change_password/ | 
[**team_user_copy**](TeamApi.md#team_user_copy) | **POST** /team/{id}/user/{user_id}/copy/ | 
[**team_user_edit**](TeamApi.md#team_user_edit) | **PATCH** /team/{id}/user/{user_id}/ | 
[**team_user_get**](TeamApi.md#team_user_get) | **GET** /team/{id}/user/{user_id}/ | 
[**team_user_list**](TeamApi.md#team_user_list) | **GET** /team/{id}/user/ | 
[**team_user_remove**](TeamApi.md#team_user_remove) | **DELETE** /team/{id}/user/{user_id}/ | 
[**team_user_settings_add**](TeamApi.md#team_user_settings_add) | **POST** /team/{id}/user/{user_id}/settings/ | A settings object, that allows API settings to be accessed as properties.     For example:
[**team_user_settings_get**](TeamApi.md#team_user_settings_get) | **GET** /team/{id}/user/{user_id}/settings/ | A settings object, that allows API settings to be accessed as properties.     For example:
[**team_user_settings_remove**](TeamApi.md#team_user_settings_remove) | **DELETE** /team/{id}/user/{user_id}/settings/ | A settings object, that allows API settings to be accessed as properties.     For example:
[**team_user_update**](TeamApi.md#team_user_update) | **PUT** /team/{id}/user/{user_id}/ | 


# **team_add**
> OneTeam team_add(data)



Create a new team.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
data = polemarch_client.OneTeam() # OneTeam | 

try:
    api_response = api_instance.team_add(data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**OneTeam**](OneTeam.md)|  | 

### Return type

[**OneTeam**](OneTeam.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_copy**
> Team team_copy(id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
data = polemarch_client.Team() # Team | 

try:
    api_response = api_instance.team_copy(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **data** | [**Team**](Team.md)|  | 

### Return type

[**Team**](Team.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_edit**
> OneTeam team_edit(id, data)



Update one or more fields on an existing team.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
data = polemarch_client.OneTeam() # OneTeam | 

try:
    api_response = api_instance.team_edit(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **data** | [**OneTeam**](OneTeam.md)|  | 

### Return type

[**OneTeam**](OneTeam.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_get**
> OneTeam team_get(id)



Return a team instance.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.

try:
    api_response = api_instance.team_get(id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 

### Return type

[**OneTeam**](OneTeam.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_list**
> InlineResponse20015 team_list(id=id, name=name, id__not=id__not, name__not=name__not, ordering=ordering, limit=limit, offset=offset)



Return all teams.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name = 'name_example' # str | A name string value (or comma separated list) of instance. (optional)
id__not = 'id__not_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
name__not = 'name__not_example' # str | A name string value (or comma separated list) of instance. (optional)
ordering = 'ordering_example' # str | Which field to use when ordering the results. (optional)
limit = 56 # int | Number of results to return per page. (optional)
offset = 56 # int | The initial index from which to return the results. (optional)

try:
    api_response = api_instance.team_list(id=id, name=name, id__not=id__not, name__not=name__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **id__not** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
 **name__not** | **str**| A name string value (or comma separated list) of instance. | [optional] 
 **ordering** | **str**| Which field to use when ordering the results. | [optional] 
 **limit** | **int**| Number of results to return per page. | [optional] 
 **offset** | **int**| The initial index from which to return the results. | [optional] 

### Return type

[**InlineResponse20015**](InlineResponse20015.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_remove**
> team_remove(id)



Remove an existing team.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.

try:
    api_instance.team_remove(id)
except ApiException as e:
    print("Exception when calling TeamApi->team_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_set_owner**
> SetOwner team_set_owner(id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
data = polemarch_client.SetOwner() # SetOwner | 

try:
    api_response = api_instance.team_set_owner(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_set_owner: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **data** | [**SetOwner**](SetOwner.md)|  | 

### Return type

[**SetOwner**](SetOwner.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_update**
> OneTeam team_update(id, data)



Update a team.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
data = polemarch_client.OneTeam() # OneTeam | 

try:
    api_response = api_instance.team_update(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **data** | [**OneTeam**](OneTeam.md)|  | 

### Return type

[**OneTeam**](OneTeam.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_add**
> CreateUser team_user_add(id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
data = polemarch_client.CreateUser() # CreateUser | 

try:
    api_response = api_instance.team_user_add(id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **data** | [**CreateUser**](CreateUser.md)|  | 

### Return type

[**CreateUser**](CreateUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_change_password**
> ChangePassword team_user_change_password(id, user_id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.
data = polemarch_client.ChangePassword() # ChangePassword | 

try:
    api_response = api_instance.team_user_change_password(id, user_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_change_password: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 
 **data** | [**ChangePassword**](ChangePassword.md)|  | 

### Return type

[**ChangePassword**](ChangePassword.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_copy**
> User team_user_copy(id, user_id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.
data = polemarch_client.User() # User | 

try:
    api_response = api_instance.team_user_copy(id, user_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_copy: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 
 **data** | [**User**](User.md)|  | 

### Return type

[**User**](User.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_edit**
> OneUser team_user_edit(id, user_id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.
data = polemarch_client.OneUser() # OneUser | 

try:
    api_response = api_instance.team_user_edit(id, user_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_edit: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 
 **data** | [**OneUser**](OneUser.md)|  | 

### Return type

[**OneUser**](OneUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_get**
> OneUser team_user_get(id, user_id)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.

try:
    api_response = api_instance.team_user_get(id, user_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 

### Return type

[**OneUser**](OneUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_list**
> InlineResponse20016 team_user_list(id, id2=id2, username=username, is_active=is_active, first_name=first_name, last_name=last_name, email=email, id__not=id__not, username__not=username__not, ordering=ordering, limit=limit, offset=offset)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
id2 = 'id_example' # str | A unique integer value (or comma separated list) identifying this instance. (optional)
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
    api_response = api_instance.team_user_list(id, id2=id2, username=username, is_active=is_active, first_name=first_name, last_name=last_name, email=email, id__not=id__not, username__not=username__not, ordering=ordering, limit=limit, offset=offset)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_list: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **id2** | **str**| A unique integer value (or comma separated list) identifying this instance. | [optional] 
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

# **team_user_remove**
> team_user_remove(id, user_id)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.

try:
    api_instance.team_user_remove(id, user_id)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_settings_add**
> UserSettings team_user_settings_add(id, user_id, data)

A settings object, that allows API settings to be accessed as properties.     For example:

from rest_framework.settings import api_settings         print(api_settings.DEFAULT_RENDERER_CLASSES)      Any setting with string import paths will be automatically resolved     and return the class, rather than the string literal.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.
data = polemarch_client.UserSettings() # UserSettings | 

try:
    # A settings object, that allows API settings to be accessed as properties.     For example:
    api_response = api_instance.team_user_settings_add(id, user_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_settings_add: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 
 **data** | [**UserSettings**](UserSettings.md)|  | 

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_settings_get**
> UserSettings team_user_settings_get(id, user_id)

A settings object, that allows API settings to be accessed as properties.     For example:

from rest_framework.settings import api_settings         print(api_settings.DEFAULT_RENDERER_CLASSES)      Any setting with string import paths will be automatically resolved     and return the class, rather than the string literal.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.

try:
    # A settings object, that allows API settings to be accessed as properties.     For example:
    api_response = api_instance.team_user_settings_get(id, user_id)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_settings_get: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 

### Return type

[**UserSettings**](UserSettings.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_settings_remove**
> team_user_settings_remove(id, user_id)

A settings object, that allows API settings to be accessed as properties.     For example:

from rest_framework.settings import api_settings         print(api_settings.DEFAULT_RENDERER_CLASSES)      Any setting with string import paths will be automatically resolved     and return the class, rather than the string literal.

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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.

try:
    # A settings object, that allows API settings to be accessed as properties.     For example:
    api_instance.team_user_settings_remove(id, user_id)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_settings_remove: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 

### Return type

void (empty response body)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **team_user_update**
> OneUser team_user_update(id, user_id, data)



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
api_instance = polemarch_client.TeamApi(polemarch_client.ApiClient(configuration))
id = 56 # int | A unique integer value identifying this user group.
user_id = 56 # int | A unique integer value identifying instance of this users sublist.
data = polemarch_client.OneUser() # OneUser | 

try:
    api_response = api_instance.team_user_update(id, user_id, data)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling TeamApi->team_user_update: %s\n" % e)
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **int**| A unique integer value identifying this user group. | 
 **user_id** | **int**| A unique integer value identifying instance of this users sublist. | 
 **data** | [**OneUser**](OneUser.md)|  | 

### Return type

[**OneUser**](OneUser.md)

### Authorization

[basic](../README.md#basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, multipart/form-data; boundary=BoUnDaRyStRiNg

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)


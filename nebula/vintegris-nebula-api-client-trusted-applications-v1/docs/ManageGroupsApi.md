# ManageGroupsApi

All URIs are relative to *https://api.nebulaservice.net/trustedapps/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**changeGroups**](ManageGroupsApi.md#changeGroups) | **PUT** /trusted/app/{id}/groups | Modify the groups assigned to a trusted app |
| [**getAllGroups**](ManageGroupsApi.md#getAllGroups) | **GET** /trusted/app/{id}/groups | Get list of all groups configured in a trusted app |



## changeGroups

> NebulaResponse changeGroups(id, accessGroupsViewModel)

Modify the groups assigned to a trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageGroupsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageGroupsApi apiInstance = new ManageGroupsApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        AccessGroupsViewModel accessGroupsViewModel = new AccessGroupsViewModel(); // AccessGroupsViewModel | 
        try {
            NebulaResponse result = apiInstance.changeGroups(id, accessGroupsViewModel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageGroupsApi#changeGroups");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| Trusted app identifier | |
| **accessGroupsViewModel** | [**AccessGroupsViewModel**](AccessGroupsViewModel.md)|  | |

### Return type

[**NebulaResponse**](NebulaResponse.md)

### Authorization

[nebulasuite](../README.md#nebulasuite)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |
| **404** | Not Found |  -  |
| **200** | OK |  -  |


## getAllGroups

> NebulaResponseListGroupViewModel getAllGroups(id)

Get list of all groups configured in a trusted app

List only the groups of a trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageGroupsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageGroupsApi apiInstance = new ManageGroupsApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        try {
            NebulaResponseListGroupViewModel result = apiInstance.getAllGroups(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageGroupsApi#getAllGroups");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**| Trusted app identifier | |

### Return type

[**NebulaResponseListGroupViewModel**](NebulaResponseListGroupViewModel.md)

### Authorization

[nebulasuite](../README.md#nebulasuite)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |
| **404** | Not Found |  -  |
| **200** | OK |  -  |


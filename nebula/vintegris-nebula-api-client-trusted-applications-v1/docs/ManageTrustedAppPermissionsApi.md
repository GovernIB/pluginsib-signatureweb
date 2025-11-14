# ManageTrustedAppPermissionsApi

All URIs are relative to *https://api.nebulaservice.net/trustedapps/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**changePermissions**](ManageTrustedAppPermissionsApi.md#changePermissions) | **PUT** /trusted/app/{id}/permissions | Modify the permissions assigned to a trusted app |
| [**regenerateAccessKey**](ManageTrustedAppPermissionsApi.md#regenerateAccessKey) | **PUT** /trusted/app/{id}/accesskey | Generate signature key for trusted app authorization |
| [**setSecurityParameters**](ManageTrustedAppPermissionsApi.md#setSecurityParameters) | **PUT** /trusted/app/{id}/security | Modify the security parameters assigned to a trusted app |



## changePermissions

> NebulaResponse changePermissions(id, permissionsBody)

Modify the permissions assigned to a trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppPermissionsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppPermissionsApi apiInstance = new ManageTrustedAppPermissionsApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        PermissionsBody permissionsBody = new PermissionsBody(); // PermissionsBody | 
        try {
            NebulaResponse result = apiInstance.changePermissions(id, permissionsBody);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppPermissionsApi#changePermissions");
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
| **permissionsBody** | [**PermissionsBody**](PermissionsBody.md)|  | [optional] |

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


## regenerateAccessKey

> NebulaResponseRegenerateTrustedAppViewModel regenerateAccessKey(id)

Generate signature key for trusted app authorization

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppPermissionsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppPermissionsApi apiInstance = new ManageTrustedAppPermissionsApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        try {
            NebulaResponseRegenerateTrustedAppViewModel result = apiInstance.regenerateAccessKey(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppPermissionsApi#regenerateAccessKey");
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

[**NebulaResponseRegenerateTrustedAppViewModel**](NebulaResponseRegenerateTrustedAppViewModel.md)

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


## setSecurityParameters

> NebulaResponse setSecurityParameters(id, trustedAppSecParamsViewModel)

Modify the security parameters assigned to a trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppPermissionsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppPermissionsApi apiInstance = new ManageTrustedAppPermissionsApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        TrustedAppSecParamsViewModel trustedAppSecParamsViewModel = new TrustedAppSecParamsViewModel(); // TrustedAppSecParamsViewModel | 
        try {
            NebulaResponse result = apiInstance.setSecurityParameters(id, trustedAppSecParamsViewModel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppPermissionsApi#setSecurityParameters");
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
| **trustedAppSecParamsViewModel** | [**TrustedAppSecParamsViewModel**](TrustedAppSecParamsViewModel.md)|  | |

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


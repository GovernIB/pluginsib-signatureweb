# ManageTrustedAppApi

All URIs are relative to *https://api.nebulaservice.net/trustedapps/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createTrustedApp**](ManageTrustedAppApi.md#createTrustedApp) | **POST** /trusted/app | Create a new trusted app |
| [**deleteTrustedApp**](ManageTrustedAppApi.md#deleteTrustedApp) | **DELETE** /trusted/app/{id} | Delete a trusted app |
| [**getTrustedApp**](ManageTrustedAppApi.md#getTrustedApp) | **GET** /trusted/app/{id} | Get a specific trusted app |
| [**getTrustedApps**](ManageTrustedAppApi.md#getTrustedApps) | **GET** /trusted/apps | Get list of trusted apps created in the system |
| [**updateTrustedApp**](ManageTrustedAppApi.md#updateTrustedApp) | **PUT** /trusted/app/{id} | Update a trusted app |



## createTrustedApp

> NebulaResponseDetailedTrustedAppViewModel createTrustedApp(createTrustedAppViewModel)

Create a new trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppApi apiInstance = new ManageTrustedAppApi(defaultClient);
        CreateTrustedAppViewModel createTrustedAppViewModel = new CreateTrustedAppViewModel(); // CreateTrustedAppViewModel | 
        try {
            NebulaResponseDetailedTrustedAppViewModel result = apiInstance.createTrustedApp(createTrustedAppViewModel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppApi#createTrustedApp");
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
| **createTrustedAppViewModel** | [**CreateTrustedAppViewModel**](CreateTrustedAppViewModel.md)|  | |

### Return type

[**NebulaResponseDetailedTrustedAppViewModel**](NebulaResponseDetailedTrustedAppViewModel.md)

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


## deleteTrustedApp

> NebulaResponse deleteTrustedApp(id)

Delete a trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppApi apiInstance = new ManageTrustedAppApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        try {
            NebulaResponse result = apiInstance.deleteTrustedApp(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppApi#deleteTrustedApp");
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

[**NebulaResponse**](NebulaResponse.md)

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


## getTrustedApp

> NebulaResponseDetailedTrustedAppViewModel getTrustedApp(id)

Get a specific trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppApi apiInstance = new ManageTrustedAppApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        try {
            NebulaResponseDetailedTrustedAppViewModel result = apiInstance.getTrustedApp(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppApi#getTrustedApp");
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

[**NebulaResponseDetailedTrustedAppViewModel**](NebulaResponseDetailedTrustedAppViewModel.md)

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


## getTrustedApps

> NebulaResponseListTrustedAppViewModel getTrustedApps(page, size)

Get list of trusted apps created in the system

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppApi apiInstance = new ManageTrustedAppApi(defaultClient);
        Integer page = 56; // Integer | 
        Integer size = 56; // Integer | 
        try {
            NebulaResponseListTrustedAppViewModel result = apiInstance.getTrustedApps(page, size);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppApi#getTrustedApps");
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
| **page** | **Integer**|  | [optional] |
| **size** | **Integer**|  | [optional] |

### Return type

[**NebulaResponseListTrustedAppViewModel**](NebulaResponseListTrustedAppViewModel.md)

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


## updateTrustedApp

> NebulaResponseDetailedTrustedAppViewModel updateTrustedApp(id, createTrustedAppViewModel)

Update a trusted app

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.ManageTrustedAppApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nebulaservice.net/trustedapps/v1");
        
        // Configure HTTP bearer authorization: nebulasuite
        HttpBearerAuth nebulasuite = (HttpBearerAuth) defaultClient.getAuthentication("nebulasuite");
        nebulasuite.setBearerToken("BEARER TOKEN");

        ManageTrustedAppApi apiInstance = new ManageTrustedAppApi(defaultClient);
        String id = "b7530d2a-d035-465b-a549-e81caaba63b6"; // String | Trusted app identifier
        CreateTrustedAppViewModel createTrustedAppViewModel = new CreateTrustedAppViewModel(); // CreateTrustedAppViewModel | 
        try {
            NebulaResponseDetailedTrustedAppViewModel result = apiInstance.updateTrustedApp(id, createTrustedAppViewModel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ManageTrustedAppApi#updateTrustedApp");
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
| **createTrustedAppViewModel** | [**CreateTrustedAppViewModel**](CreateTrustedAppViewModel.md)|  | |

### Return type

[**NebulaResponseDetailedTrustedAppViewModel**](NebulaResponseDetailedTrustedAppViewModel.md)

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


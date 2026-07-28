# FirstFactorApi

All URIs are relative to *https://api-ansmt01.nebulaservice.net*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**authFirst**](FirstFactorApi.md#authFirst) | **POST** /signin/auth/login/first | First factor authentication |



## authFirst

> NebulaResponseResponseCodeTokenWithLevelViewModel authFirst(authViewModel)

First factor authentication

First factor authentication

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.authentication.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.authentication.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.authentication.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.authentication.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.authentication.v1.api.FirstFactorApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");

        FirstFactorApi apiInstance = new FirstFactorApi(defaultClient);
        AuthViewModel authViewModel = new AuthViewModel(); // AuthViewModel | 
        try {
            NebulaResponseResponseCodeTokenWithLevelViewModel result = apiInstance.authFirst(authViewModel);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling FirstFactorApi#authFirst");
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
| **authViewModel** | [**AuthViewModel**](AuthViewModel.md)|  | |

### Return type

[**NebulaResponseResponseCodeTokenWithLevelViewModel**](NebulaResponseResponseCodeTokenWithLevelViewModel.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*, application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Not Found |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |
| **401** | Unauthorized |  -  |
| **200** | OK |  -  |


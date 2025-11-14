# XadEsApi

All URIs are relative to *https://api-ansmt01.nebulaservice.net*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**xadesSign**](XadEsApi.md#xadesSign) | **POST** /api/v2/xadessign/sign | Sign an XML file with XAdES |



## xadesSign

> XadesSignatureResponse xadesSign(_file, data)

Sign an XML file with XAdES

Sign an XML file using XAdES signature standard

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.XadEsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        XadEsApi apiInstance = new XadEsApi(defaultClient);
        File _file = new File("/path/to/file"); // File | XML file to sign
        String data = "data_example"; // String | JSON containing the parameters needed to define how the signature will be made. See schema XadesSignRequestData
        try {
            XadesSignatureResponse result = apiInstance.xadesSign(_file, data);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling XadEsApi#xadesSign");
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
| **_file** | **File**| XML file to sign | |
| **data** | **String**| JSON containing the parameters needed to define how the signature will be made. See schema XadesSignRequestData | |

### Return type

[**XadesSignatureResponse**](XadesSignatureResponse.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | XML signed successfully |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |
| **404** | Not Found |  -  |
| **401** | Unauthorized |  -  |


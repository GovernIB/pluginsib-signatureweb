# CurrentOperationsWithDocumentsApi

All URIs are relative to *https://api-ansmt01.nebulaservice.net*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**signDocumentV1**](CurrentOperationsWithDocumentsApi.md#signDocumentV1) | **POST** /signature/pades/v1/sign | Sign a PDF file |



## signDocumentV1

> ItemResponseOkEnumSignatureResponse signDocumentV1(_file, data, images)

Sign a PDF file

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.CurrentOperationsWithDocumentsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        CurrentOperationsWithDocumentsApi apiInstance = new CurrentOperationsWithDocumentsApi(defaultClient);
        File _file = new File("/path/to/file"); // File | PDF file to sign
        String data = "data_example"; // String | JSON containing the parameters needed to define how the signature will be made. See schema PadesSignatureRequest
        List<File> images = Arrays.asList(); // List<File> | List of images that will be used in the visible signature
        try {
            ItemResponseOkEnumSignatureResponse result = apiInstance.signDocumentV1(_file, data, images);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CurrentOperationsWithDocumentsApi#signDocumentV1");
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
| **_file** | **File**| PDF file to sign | |
| **data** | **String**| JSON containing the parameters needed to define how the signature will be made. See schema PadesSignatureRequest | |
| **images** | **List&lt;File&gt;**| List of images that will be used in the visible signature | [optional] |

### Return type

[**ItemResponseOkEnumSignatureResponse**](ItemResponseOkEnumSignatureResponse.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |
| **404** | Not Found |  -  |
| **401** | Unauthorized |  -  |
| **200** | Signed PDF file |  -  |


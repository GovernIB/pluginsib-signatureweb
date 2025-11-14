# CadEsSignatureApi

All URIs are relative to *https://api-ansmt01.nebulaservice.net*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**cadesSign**](CadEsSignatureApi.md#cadesSign) | **POST** /api/v1/cadessign/sign | Sign a document with CAdES |



## cadesSign

> CadesSignatureResponse cadesSign(data, _file)

Sign a document with CAdES

Signs a document with the CAdES signature standard

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.CadEsSignatureApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        CadEsSignatureApi apiInstance = new CadEsSignatureApi(defaultClient);
        String data = "data_example"; // String | JSON containing the parameters needed to define how the signature will be made. See schema CadesSignatureRequest
        File _file = new File("/path/to/file"); // File | File to sign. Multipart File or fileHash parameter must be specified.
        try {
            CadesSignatureResponse result = apiInstance.cadesSign(data, _file);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CadEsSignatureApi#cadesSign");
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
| **data** | **String**| JSON containing the parameters needed to define how the signature will be made. See schema CadesSignatureRequest | |
| **_file** | **File**| File to sign. Multipart File or fileHash parameter must be specified. | [optional] |

### Return type

[**CadesSignatureResponse**](CadesSignatureResponse.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: multipart/form-data
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Document signed successfully |  -  |
| **400** | Bad Request |  -  |
| **500** | Internal Server Error |  -  |
| **404** | Not Found |  -  |
| **401** | Unauthorized |  -  |


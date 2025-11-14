# DefaultApi

All URIs are relative to *https://api-ansmt01.nebulaservice.net*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**activateCertificate**](DefaultApi.md#activateCertificate) | **PUT** /api/v2/certificate/request/{requestId}/activate | Activate Certificate |
| [**availableForSigning**](DefaultApi.md#availableForSigning) | **GET** /api/v4/certificates/available-for-signing | Find Certificates to Sign |
| [**changeCertificatePin**](DefaultApi.md#changeCertificatePin) | **PUT** /api/v1/certificate/changePin | Change Certificate PIN |
| [**delegateCertificateUser**](DefaultApi.md#delegateCertificateUser) | **PUT** /api/v1/certificate/delegate/user | Delegate User Certificate |
| [**deleteCertificate**](DefaultApi.md#deleteCertificate) | **DELETE** /api/v1/certificate/delete | Delete Certificate |
| [**getCertificateById**](DefaultApi.md#getCertificateById) | **GET** /api/v2/certificate/{certificateId} | Get Certificate |
| [**getCertificatePolicy**](DefaultApi.md#getCertificatePolicy) | **GET** /api/v2/certificate/policy/{certificateId} | Get Policy |
| [**getMyCertificates**](DefaultApi.md#getMyCertificates) | **GET** /api/v2/certificate/mycertificates | Get User Certificates |
| [**importCertificate**](DefaultApi.md#importCertificate) | **POST** /api/v1/certificate/import | Import Certificate |
| [**issueCertificate**](DefaultApi.md#issueCertificate) | **PUT** /api/v2/certificate/request/{requestId}/issue | Issue Certificate |
| [**requestCertificateReport**](DefaultApi.md#requestCertificateReport) | **POST** /api/v1/certificate/report | Request Certificate Report |
| [**requestKeystoreCertificate**](DefaultApi.md#requestKeystoreCertificate) | **POST** /api/v1/keystore/request | Request Keystore Certificate |



## activateCertificate

> activateCertificate(requestId)

Activate Certificate

Activates an issued certificate once the issue status is ENROLL.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String requestId = "requestId_example"; // String | Unique identifier of the certificate request.
        try {
            apiInstance.activateCertificate(requestId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#activateCertificate");
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
| **requestId** | **String**| Unique identifier of the certificate request. | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Certificate activated successfully. |  -  |
| **400** | Invalid request. |  -  |
| **401** | Unauthorized. |  -  |
| **404** | Certificate not found. |  -  |


## availableForSigning

> availableForSigning(offset, limit)

Find Certificates to Sign

Gets the user’s certificate list that can be used to perform signature operations.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        Integer offset = 56; // Integer | 
        Integer limit = 56; // Integer | 
        try {
            apiInstance.availableForSigning(offset, limit);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#availableForSigning");
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
| **offset** | **Integer**|  | [optional] |
| **limit** | **Integer**|  | [optional] |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of certificates usable for signing. |  -  |
| **401** | Unauthorized. |  -  |


## changeCertificatePin

> changeCertificatePin(changeCertificatePinRequest)

Change Certificate PIN

Changes the PIN associated with a digital certificate.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        ChangeCertificatePinRequest changeCertificatePinRequest = new ChangeCertificatePinRequest(); // ChangeCertificatePinRequest | 
        try {
            apiInstance.changeCertificatePin(changeCertificatePinRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#changeCertificatePin");
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
| **changeCertificatePinRequest** | [**ChangeCertificatePinRequest**](ChangeCertificatePinRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | PIN changed successfully. |  -  |
| **400** | Invalid request body or PIN values. |  -  |
| **401** | Unauthorized. |  -  |


## delegateCertificateUser

> delegateCertificateUser(delegateCertificateUserRequest)

Delegate User Certificate

Delegates a certificate to another user in the system.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        DelegateCertificateUserRequest delegateCertificateUserRequest = new DelegateCertificateUserRequest(); // DelegateCertificateUserRequest | 
        try {
            apiInstance.delegateCertificateUser(delegateCertificateUserRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#delegateCertificateUser");
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
| **delegateCertificateUserRequest** | [**DelegateCertificateUserRequest**](DelegateCertificateUserRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Delegation successful. |  -  |
| **400** | Invalid data provided. |  -  |
| **401** | Unauthorized. |  -  |


## deleteCertificate

> deleteCertificate(deleteCertificateRequest)

Delete Certificate

Delete a certificate from the system.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        DeleteCertificateRequest deleteCertificateRequest = new DeleteCertificateRequest(); // DeleteCertificateRequest | 
        try {
            apiInstance.deleteCertificate(deleteCertificateRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#deleteCertificate");
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
| **deleteCertificateRequest** | [**DeleteCertificateRequest**](DeleteCertificateRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Certificate deleted. |  -  |
| **400** | Invalid request. |  -  |
| **401** | Unauthorized. |  -  |


## getCertificateById

> getCertificateById(certificateId)

Get Certificate

Returns the certificate data for the certificate identified by the given id.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String certificateId = "certificateId_example"; // String | Certificate identifier.
        try {
            apiInstance.getCertificateById(certificateId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#getCertificateById");
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
| **certificateId** | **String**| Certificate identifier. | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Certificate details returned. |  -  |
| **401** | Unauthorized. |  -  |
| **404** | Certificate not found. |  -  |


## getCertificatePolicy

> getCertificatePolicy(certificateId)

Get Policy

Returns the policy linked to the given user and certificate id.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String certificateId = "certificateId_example"; // String | Certificate identifier.
        try {
            apiInstance.getCertificatePolicy(certificateId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#getCertificatePolicy");
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
| **certificateId** | **String**| Certificate identifier. | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Policy returned. |  -  |
| **401** | Unauthorized. |  -  |
| **404** | Policy not found. |  -  |


## getMyCertificates

> getMyCertificates(offset, limit, enableFilter, expireFilter, certType, isOrphan, dateValidStart)

Get User Certificates

Gets the user’s certificate list and returns its search result that fulfills the given input search criteria. The caller must have role admin, owner or signer.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        Integer offset = 56; // Integer | 
        Integer limit = 56; // Integer | 
        String enableFilter = "enableFilter_example"; // String | ENABLED/DISABLED
        String expireFilter = "expireFilter_example"; // String | BOTH/EXPIRED/NOT_EXPIRED
        String certType = "certType_example"; // String | 
        Boolean isOrphan = true; // Boolean | 
        LocalDate dateValidStart = LocalDate.now(); // LocalDate | 
        try {
            apiInstance.getMyCertificates(offset, limit, enableFilter, expireFilter, certType, isOrphan, dateValidStart);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#getMyCertificates");
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
| **offset** | **Integer**|  | [optional] |
| **limit** | **Integer**|  | [optional] |
| **enableFilter** | **String**| ENABLED/DISABLED | [optional] |
| **expireFilter** | **String**| BOTH/EXPIRED/NOT_EXPIRED | [optional] |
| **certType** | **String**|  | [optional] |
| **isOrphan** | **Boolean**|  | [optional] |
| **dateValidStart** | **LocalDate**|  | [optional] |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of certificates returned. |  -  |
| **400** | Invalid parameters. |  -  |
| **401** | Unauthorized. |  -  |


## importCertificate

> importCertificate(importCertificateRequest)

Import Certificate

Import a certificate into the system (PFX / PKCS#12 in base64).

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        ImportCertificateRequest importCertificateRequest = new ImportCertificateRequest(); // ImportCertificateRequest | 
        try {
            apiInstance.importCertificate(importCertificateRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#importCertificate");
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
| **importCertificateRequest** | [**ImportCertificateRequest**](ImportCertificateRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Certificate imported. |  -  |
| **400** | Invalid request. |  -  |
| **401** | Unauthorized. |  -  |


## issueCertificate

> issueCertificate(requestId)

Issue Certificate

Issues a requested certificate. This operation is asynchronous — to execute Activate Certificate, wait until the request issue status is ENROLL.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String requestId = "requestId_example"; // String | Unique identifier of the certificate request.
        try {
            apiInstance.issueCertificate(requestId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#issueCertificate");
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
| **requestId** | **String**| Unique identifier of the certificate request. | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | Certificate issuance initiated successfully. |  -  |
| **400** | Invalid request or parameters. |  -  |
| **401** | Unauthorized |  -  |
| **404** | Request not found. |  -  |


## requestCertificateReport

> requestCertificateReport(requestCertificateReportRequest)

Request Certificate Report

Requests a report (sent by email) with certificates information.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        RequestCertificateReportRequest requestCertificateReportRequest = new RequestCertificateReportRequest(); // RequestCertificateReportRequest | 
        try {
            apiInstance.requestCertificateReport(requestCertificateReportRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#requestCertificateReport");
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
| **requestCertificateReportRequest** | [**RequestCertificateReportRequest**](RequestCertificateReportRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | Report request accepted. |  -  |
| **400** | Invalid request body. |  -  |
| **401** | Unauthorized. |  -  |


## requestKeystoreCertificate

> requestKeystoreCertificate(requestKeystoreCertificateRequest)

Request Keystore Certificate

Requests a new certificate to be issued and stored in the keystore.

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net");
        
        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken("BEARER TOKEN");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        RequestKeystoreCertificateRequest requestKeystoreCertificateRequest = new RequestKeystoreCertificateRequest(); // RequestKeystoreCertificateRequest | 
        try {
            apiInstance.requestKeystoreCertificate(requestKeystoreCertificateRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#requestKeystoreCertificate");
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
| **requestKeystoreCertificateRequest** | [**RequestKeystoreCertificateRequest**](RequestKeystoreCertificateRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[bearerAuth](../README.md#bearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Keystore certificate request created. |  -  |
| **400** | Invalid request body. |  -  |
| **401** | Unauthorized. |  -  |


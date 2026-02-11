# DigitalCertificateApi

All URIs are relative to *https://api-ansmt01.nebulaservice.net/api*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**activateCertificate**](DigitalCertificateApi.md#activateCertificate) | **PUT** /v2/certificate/request/{requestid}/activate | Activate Certificate |
| [**changeCertificatePin**](DigitalCertificateApi.md#changeCertificatePin) | **PUT** /api/v1/certificate/changepin | Change Certificate Pin |
| [**findCertificatesToSign**](DigitalCertificateApi.md#findCertificatesToSign) | **GET** /api/v4/certificates/available-for-signing | Find Certificates to Sign |
| [**getCertPolicies**](DigitalCertificateApi.md#getCertPolicies) | **GET** /api/v2/certificate/mypolicies | Get Cert Policies |
| [**getCertificate**](DigitalCertificateApi.md#getCertificate) | **GET** /api/v2/certificate/{certificateid} | Get Certificate |
| [**getMyCertificates**](DigitalCertificateApi.md#getMyCertificates) | **GET** /api/v2/certificate/mycertificates | Get User Certificates |
| [**getPolicy**](DigitalCertificateApi.md#getPolicy) | **GET** /v2/certificate/policy/{certificateid} | Get Policy |
| [**importCertificate**](DigitalCertificateApi.md#importCertificate) | **POST** /api/v1/certificate/import | Import Certificate |
| [**issueCertificate**](DigitalCertificateApi.md#issueCertificate) | **PUT** /v2/certificate/request/{requestId}/issue | Issue Certificate |
| [**signCloseV2**](DigitalCertificateApi.md#signCloseV2) | **POST** /api/v2/certificate/signclose | Sign Close |
| [**signHashV2**](DigitalCertificateApi.md#signHashV2) | **POST** /api/v2/certificate/signhash | Sign Hash |
| [**signHashV3**](DigitalCertificateApi.md#signHashV3) | **POST** /api/v3/certificate/signhash | Sign Hash V3 |
| [**signInitV2**](DigitalCertificateApi.md#signInitV2) | **POST** /api/v2/certificate/signinit | Sign Init |
| [**signInitV3**](DigitalCertificateApi.md#signInitV3) | **POST** /api/v3/certificate/signinit | Sign Init V3 |



## activateCertificate

> ActivateCertificate200Response activateCertificate(requestid, activateCertificateRequest)

Activate Certificate

Activates an accepted and issued certificate request to be used by the user

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        String requestid = "requestid_example"; // String | The certificate request id
        ActivateCertificateRequest activateCertificateRequest = new ActivateCertificateRequest(); // ActivateCertificateRequest | 
        try {
            ActivateCertificate200Response result = apiInstance.activateCertificate(requestid, activateCertificateRequest);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#activateCertificate");
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
| **requestid** | **String**| The certificate request id | |
| **activateCertificateRequest** | [**ActivateCertificateRequest**](ActivateCertificateRequest.md)|  | |

### Return type

[**ActivateCertificate200Response**](ActivateCertificate200Response.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## changeCertificatePin

> changeCertificatePin(changeCertificatePinRequest)

Change Certificate Pin

Change certificate PIN

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        ChangeCertificatePinRequest changeCertificatePinRequest = new ChangeCertificatePinRequest(); // ChangeCertificatePinRequest | 
        try {
            apiInstance.changeCertificatePin(changeCertificatePinRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#changeCertificatePin");
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

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## findCertificatesToSign

> FindCertificatesToSign200Response findCertificatesToSign(strictMatch, returnPublicKey, commonName, alias, subject, issuer, dateFrom, dateTo, order, direction, currentPage, pageSize)

Find Certificates to Sign

Gets the user&#39;s certificate list and returns its search result that fulfills the given input search criteria. All returned certificates can be used to perform a signature operation

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        Boolean strictMatch = false; // Boolean | Input parameters must exactly match the certificate parameters. If not specified, the call will search for certificates in 'LIKE' mode
        Boolean returnPublicKey = false; // Boolean | Includes the public part of the certificate in Base64 encoded x509
        String commonName = ""; // String | Search parameter: Common name of the certificate
        String alias = ""; // String | Search parameter: Alias of the certificate
        String subject = ""; // String | Search parameter: Subject DN of the certificate
        String issuer = ""; // String | Search parameter: Issuer DN of the certificate
        Integer dateFrom = 56; // Integer | Expiration date range (Start)
        Integer dateTo = 56; // Integer | Expiration date range (End)
        String order = "commonName"; // String | Order
        String direction = "ASC"; // String | Search direction
        Integer currentPage = 0; // Integer | Search offset
        Integer pageSize = 100; // Integer | Elements to return
        try {
            FindCertificatesToSign200Response result = apiInstance.findCertificatesToSign(strictMatch, returnPublicKey, commonName, alias, subject, issuer, dateFrom, dateTo, order, direction, currentPage, pageSize);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#findCertificatesToSign");
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
| **strictMatch** | **Boolean**| Input parameters must exactly match the certificate parameters. If not specified, the call will search for certificates in &#39;LIKE&#39; mode | [optional] [default to false] |
| **returnPublicKey** | **Boolean**| Includes the public part of the certificate in Base64 encoded x509 | [optional] [default to false] |
| **commonName** | **String**| Search parameter: Common name of the certificate | [optional] [default to ] |
| **alias** | **String**| Search parameter: Alias of the certificate | [optional] [default to ] |
| **subject** | **String**| Search parameter: Subject DN of the certificate | [optional] [default to ] |
| **issuer** | **String**| Search parameter: Issuer DN of the certificate | [optional] [default to ] |
| **dateFrom** | **Integer**| Expiration date range (Start) | [optional] |
| **dateTo** | **Integer**| Expiration date range (End) | [optional] |
| **order** | **String**| Order | [optional] [default to commonName] [enum: commonName, alias, subject, issuer, dateFrom, dateTo] |
| **direction** | **String**| Search direction | [optional] [default to ASC] [enum: ASC, DESC] |
| **currentPage** | **Integer**| Search offset | [optional] [default to 0] |
| **pageSize** | **Integer**| Elements to return | [optional] [default to 100] |

### Return type

[**FindCertificatesToSign200Response**](FindCertificatesToSign200Response.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **400** | Bad Request |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **500** | Internal Server Error |  -  |


## getCertPolicies

> GetCertPolicies200Response getCertPolicies(offset, limit, querymode)

Get Cert Policies

Gets the user&#39;s policy list and returns its search result given the input offset and limit parameters. The role of the user who executes this operation must be owner or signer

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        Integer offset = 56; // Integer | Search offset
        Integer limit = 56; // Integer | Search limit 0 means no limit
        String querymode = "OWNED"; // String | Filter to search between OWNED or DELEGATED policies
        try {
            GetCertPolicies200Response result = apiInstance.getCertPolicies(offset, limit, querymode);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#getCertPolicies");
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
| **offset** | **Integer**| Search offset | [optional] |
| **limit** | **Integer**| Search limit 0 means no limit | [optional] |
| **querymode** | **String**| Filter to search between OWNED or DELEGATED policies | [optional] [enum: OWNED, DELEGATED] |

### Return type

[**GetCertPolicies200Response**](GetCertPolicies200Response.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getCertificate

> Certificate getCertificate(certificateid)

Get Certificate

Returns the certificate data for the certificate identified by the given input id

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        String certificateid = "certificateid_example"; // String | The certificate id
        try {
            Certificate result = apiInstance.getCertificate(certificateid);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#getCertificate");
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
| **certificateid** | **String**| The certificate id | |

### Return type

[**Certificate**](Certificate.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## getMyCertificates

> GetMyCertificates200Response getMyCertificates(offset, limit, enableFilter, expireFilter, certType, isOrphan, dateValidStart)

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
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        Integer offset = 56; // Integer | 
        Integer limit = 56; // Integer | 
        String enableFilter = "enableFilter_example"; // String | ENABLED/DISABLED
        String expireFilter = "expireFilter_example"; // String | BOTH/EXPIRED/NOT_EXPIRED
        String certType = "certType_example"; // String | 
        Boolean isOrphan = true; // Boolean | 
        LocalDate dateValidStart = LocalDate.now(); // LocalDate | 
        try {
            GetMyCertificates200Response result = apiInstance.getMyCertificates(offset, limit, enableFilter, expireFilter, certType, isOrphan, dateValidStart);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#getMyCertificates");
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

[**GetMyCertificates200Response**](GetMyCertificates200Response.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of certificates returned. |  -  |
| **400** | Invalid parameters. |  -  |
| **401** | Unauthorized. |  -  |


## getPolicy

> GetPolicy200Response getPolicy(certificateid, userType, userId)

Get Policy

Returns the policy linked to the given user and certificate id

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        String certificateid = "certificateid_example"; // String | The certificate id
        String userType = "USER"; // String | The user type, it can be USER or GROUP
        String userId = "userId_example"; // String | The UUID (User Unique Identifier) must be specified in case the userType is USER. Alternatively, if userType is GROUP, the GUID (Group Unique Identifier) shall be specified. In case userType is USER and this parameter is not specified, the system shall obtain the UUID of the user making the request.
        try {
            GetPolicy200Response result = apiInstance.getPolicy(certificateid, userType, userId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#getPolicy");
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
| **certificateid** | **String**| The certificate id | |
| **userType** | **String**| The user type, it can be USER or GROUP | [enum: USER, GROUP] |
| **userId** | **String**| The UUID (User Unique Identifier) must be specified in case the userType is USER. Alternatively, if userType is GROUP, the GUID (Group Unique Identifier) shall be specified. In case userType is USER and this parameter is not specified, the system shall obtain the UUID of the user making the request. | [optional] |

### Return type

[**GetPolicy200Response**](GetPolicy200Response.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |


## importCertificate

> CertIdDTO importCertificate(importCertificateDTO)

Import Certificate

Import a certificate into nebulaSUITE

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        ImportCertificateDTO importCertificateDTO = new ImportCertificateDTO(); // ImportCertificateDTO | 
        try {
            CertIdDTO result = apiInstance.importCertificate(importCertificateDTO);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#importCertificate");
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
| **importCertificateDTO** | [**ImportCertificateDTO**](ImportCertificateDTO.md)|  | |

### Return type

[**CertIdDTO**](CertIdDTO.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |


## issueCertificate

> issueCertificate(requestId, issueCertificateRequest)

Issue Certificate

Issues a requested certificate. This operation is asynchronous, in order to execute the Activate_Certificate operation we must wait until the issue status of the request is ENROLL (check Get Certificate Request operation).

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        String requestId = "requestId_example"; // String | The certificate request id
        IssueCertificateRequest issueCertificateRequest = new IssueCertificateRequest(); // IssueCertificateRequest | 
        try {
            apiInstance.issueCertificate(requestId, issueCertificateRequest);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#issueCertificate");
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
| **requestId** | **String**| The certificate request id | |
| **issueCertificateRequest** | [**IssueCertificateRequest**](IssueCertificateRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **202** | Accepted |  -  |


## signCloseV2

> CSessionDTO signCloseV2(csignCloseDTO)

Sign Close

Closes a signing session for CAdES signature

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        CSignCloseDTO csignCloseDTO = new CSignCloseDTO(); // CSignCloseDTO | 
        try {
            CSessionDTO result = apiInstance.signCloseV2(csignCloseDTO);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#signCloseV2");
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
| **csignCloseDTO** | [**CSignCloseDTO**](CSignCloseDTO.md)|  | |

### Return type

[**CSessionDTO**](CSessionDTO.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |


## signHashV2

> CSignDTO signHashV2(csignReqDTOV2)

Sign Hash

Sign a hash using a CAdES signature

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        CSignReqDTOV2 csignReqDTOV2 = new CSignReqDTOV2(); // CSignReqDTOV2 | 
        try {
            CSignDTO result = apiInstance.signHashV2(csignReqDTOV2);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#signHashV2");
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
| **csignReqDTOV2** | [**CSignReqDTOV2**](CSignReqDTOV2.md)|  | |

### Return type

[**CSignDTO**](CSignDTO.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |


## signHashV3

> CSignDTO signHashV3(csignReqDTOV3)

Sign Hash V3

Sign a hash using a CAdES signature (V3)

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        CSignReqDTOV3 csignReqDTOV3 = new CSignReqDTOV3(); // CSignReqDTOV3 | 
        try {
            CSignDTO result = apiInstance.signHashV3(csignReqDTOV3);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#signHashV3");
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
| **csignReqDTOV3** | [**CSignReqDTOV3**](CSignReqDTOV3.md)|  | |

### Return type

[**CSignDTO**](CSignDTO.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |


## signInitV2

> CSessionDTOV2 signInitV2(cloginRequestDTO)

Sign Init

Initializes a signing session for CAdES signature

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        CLoginRequestDTO cloginRequestDTO = new CLoginRequestDTO(); // CLoginRequestDTO | 
        try {
            CSessionDTOV2 result = apiInstance.signInitV2(cloginRequestDTO);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#signInitV2");
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
| **cloginRequestDTO** | [**CLoginRequestDTO**](CLoginRequestDTO.md)|  | |

### Return type

[**CSessionDTOV2**](CSessionDTOV2.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |


## signInitV3

> CSessionDTOV3 signInitV3(cloginRequestDTO)

Sign Init V3

Initializes a signing session for CAdES signature (V3)

### Example

```java
// Import classes:
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.models.*;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-ansmt01.nebulaservice.net/api");
        
        // Configure HTTP bearer authorization: Authorization
        HttpBearerAuth Authorization = (HttpBearerAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setBearerToken("BEARER TOKEN");

        DigitalCertificateApi apiInstance = new DigitalCertificateApi(defaultClient);
        CLoginRequestDTO cloginRequestDTO = new CLoginRequestDTO(); // CLoginRequestDTO | 
        try {
            CSessionDTOV3 result = apiInstance.signInitV3(cloginRequestDTO);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DigitalCertificateApi#signInitV3");
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
| **cloginRequestDTO** | [**CLoginRequestDTO**](CLoginRequestDTO.md)|  | |

### Return type

[**CSessionDTOV3**](CSessionDTOV3.md)

### Authorization

[Authorization](../README.md#Authorization)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |
| **201** | Created |  -  |
| **401** | Unauthorized |  -  |
| **403** | Forbidden |  -  |
| **404** | Not Found |  -  |


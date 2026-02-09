package org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.ActivateCertificate200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.ActivateCertificateRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CertIdDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.Certificate;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.ChangeCertificatePinRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.FindCertificatesToSign200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.FindCertificatesToSign400Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetCertPolicies200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetPolicy200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetUserCertificates200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.ImportCertificateDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.IssueCertificateRequest;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class DigitalCertificateApi {
  private ApiClient apiClient;

  public DigitalCertificateApi() {
    this(Configuration.getDefaultApiClient());
  }

  public DigitalCertificateApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Activate Certificate
   * Activates an accepted and issued certificate request to be used by the user
   * @param requestid The certificate request id (required)
   * @param activateCertificateRequest  (required)
   * @return a {@code ActivateCertificate200Response}
   * @throws ApiException if fails to make API call
   */
  public ActivateCertificate200Response activateCertificate(String requestid, ActivateCertificateRequest activateCertificateRequest) throws ApiException {
    Object localVarPostBody = activateCertificateRequest;
    
    // verify the required parameter 'requestid' is set
    if (requestid == null) {
      throw new ApiException(400, "Missing the required parameter 'requestid' when calling activateCertificate");
    }
    
    // verify the required parameter 'activateCertificateRequest' is set
    if (activateCertificateRequest == null) {
      throw new ApiException(400, "Missing the required parameter 'activateCertificateRequest' when calling activateCertificate");
    }
    
    // create path and map variables
    String localVarPath = "/v2/certificate/request/{requestid}/activate".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "requestid" + "\\}", apiClient.escapeString(requestid.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<ActivateCertificate200Response> localVarReturnType = new GenericType<ActivateCertificate200Response>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Change Certificate Pin
   * Change certificate PIN
   * @param changeCertificatePinRequest  (required)
   * @throws ApiException if fails to make API call
   */
  public void changeCertificatePin(ChangeCertificatePinRequest changeCertificatePinRequest) throws ApiException {
    Object localVarPostBody = changeCertificatePinRequest;
    
    // verify the required parameter 'changeCertificatePinRequest' is set
    if (changeCertificatePinRequest == null) {
      throw new ApiException(400, "Missing the required parameter 'changeCertificatePinRequest' when calling changeCertificatePin");
    }
    
    // create path and map variables
    String localVarPath = "/v1/certificate/changepin".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };


    apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
  /**
   * Find Certificates to Sign
   * Gets the user&#39;s certificate list and returns its search result that fulfills the given input search criteria. All returned certificates can be used to perform a signature operation
   * @param strictMatch Input parameters must exactly match the certificate parameters. If not specified, the call will search for certificates in &#39;LIKE&#39; mode (optional, default to false)
   * @param returnPublicKey Includes the public part of the certificate in Base64 encoded x509 (optional, default to false)
   * @param commonName Search parameter: Common name of the certificate (optional, default to )
   * @param alias Search parameter: Alias of the certificate (optional, default to )
   * @param subject Search parameter: Subject DN of the certificate (optional, default to )
   * @param issuer Search parameter: Issuer DN of the certificate (optional, default to )
   * @param dateFrom Expiration date range (Start) (optional)
   * @param dateTo Expiration date range (End) (optional)
   * @param order Order (optional, default to commonName)
   * @param direction Search direction (optional, default to ASC)
   * @param currentPage Search offset (optional, default to 0)
   * @param pageSize Elements to return (optional, default to 100)
   * @return a {@code FindCertificatesToSign200Response}
   * @throws ApiException if fails to make API call
   */
  public FindCertificatesToSign200Response findCertificatesToSign(Boolean strictMatch, Boolean returnPublicKey, String commonName, String alias, String subject, String issuer, Integer dateFrom, Integer dateTo, String order, String direction, Integer currentPage, Integer pageSize) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/v4/certificates/available-for-signing".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "strictMatch", strictMatch));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "returnPublicKey", returnPublicKey));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "commonName", commonName));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "alias", alias));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "subject", subject));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "issuer", issuer));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "dateFrom", dateFrom));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "dateTo", dateTo));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "direction", direction));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "currentPage", currentPage));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "pageSize", pageSize));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<FindCertificatesToSign200Response> localVarReturnType = new GenericType<FindCertificatesToSign200Response>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get Cert Policies
   * Gets the user&#39;s policy list and returns its search result given the input offset and limit parameters. The role of the user who executes this operation must be owner or signer
   * @param offset Search offset (optional)
   * @param limit Search limit 0 means no limit (optional)
   * @param querymode Filter to search between OWNED or DELEGATED policies (optional)
   * @return a {@code GetCertPolicies200Response}
   * @throws ApiException if fails to make API call
   */
  public GetCertPolicies200Response getCertPolicies(Integer offset, Integer limit, String querymode) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/v2/certificate/mypolicies".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "offset", offset));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "querymode", querymode));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<GetCertPolicies200Response> localVarReturnType = new GenericType<GetCertPolicies200Response>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get Certificate
   * Returns the certificate data for the certificate identified by the given input id
   * @param certificateid The certificate id (required)
   * @return a {@code Certificate}
   * @throws ApiException if fails to make API call
   */
  public Certificate getCertificate(String certificateid) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'certificateid' is set
    if (certificateid == null) {
      throw new ApiException(400, "Missing the required parameter 'certificateid' when calling getCertificate");
    }
    
    // create path and map variables
    String localVarPath = "/v2/certificate/{certificateid}".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "certificateid" + "\\}", apiClient.escapeString(certificateid.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<Certificate> localVarReturnType = new GenericType<Certificate>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get User Certificates
   * Gets the user’s certificate list and returns its search result that fulfills the given input search criteria. The caller must have role admin, owner or signer.
   * @param offset  (optional)
   * @param limit  (optional)
   * @param enableFilter ENABLED/DISABLED (optional)
   * @param expireFilter BOTH/EXPIRED/NOT_EXPIRED (optional)
   * @param certType  (optional)
   * @param isOrphan  (optional)
   * @param dateValidStart  (optional)
   * @return a {@code GetMyCertificates200Response}
   * @throws ApiException if fails to make API call
   */
  public GetMyCertificates200Response getMyCertificates(Integer offset, Integer limit, String enableFilter, String expireFilter, String certType, Boolean isOrphan, LocalDate dateValidStart) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/api/v2/certificate/mycertificates".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "offset", offset));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "enableFilter", enableFilter));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "expireFilter", expireFilter));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "certType", certType));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "isOrphan", isOrphan));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "dateValidStart", dateValidStart));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<GetMyCertificates200Response> localVarReturnType = new GenericType<GetMyCertificates200Response>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get Policy
   * Returns the policy linked to the given user and certificate id
   * @param certificateid The certificate id (required)
   * @param userType The user type, it can be USER or GROUP (required)
   * @param userId The UUID (User Unique Identifier) must be specified in case the userType is USER. Alternatively, if userType is GROUP, the GUID (Group Unique Identifier) shall be specified. In case userType is USER and this parameter is not specified, the system shall obtain the UUID of the user making the request. (optional)
   * @return a {@code GetPolicy200Response}
   * @throws ApiException if fails to make API call
   */
  public GetPolicy200Response getPolicy(String certificateid, String userType, String userId) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'certificateid' is set
    if (certificateid == null) {
      throw new ApiException(400, "Missing the required parameter 'certificateid' when calling getPolicy");
    }
    
    // verify the required parameter 'userType' is set
    if (userType == null) {
      throw new ApiException(400, "Missing the required parameter 'userType' when calling getPolicy");
    }
    
    // create path and map variables
    String localVarPath = "/v2/certificate/policy/{certificateid}".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "certificateid" + "\\}", apiClient.escapeString(certificateid.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "userType", userType));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "userId", userId));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<GetPolicy200Response> localVarReturnType = new GenericType<GetPolicy200Response>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get User Certificates
   * Gets the user&#39;s certificate list and returns its search result that fulfills the given input search criteria. The role of the user who executes this operation must be admin, owner or signer
   * @param ownerId Unique Owner identifier (uuid). The parameter is exclusive for admin users (optional)
   * @param offset Search offset (optional)
   * @param limit Search limit 0 means no limit (optional)
   * @param enableFilter If the certificate is usable (optional)
   * @param expireFilter If the certificate is expired or expiring (optional)
   * @param certType Certificate type (optional)
   * @param isOrphan True if the certificate is orphan (This parameter is exclusive for the Admin role) (optional)
   * @param dateValidStart Start date for the certificate&#39;s valid period (optional)
   * @return a {@code GetUserCertificates200Response}
   * @throws ApiException if fails to make API call
   */
  public GetUserCertificates200Response getUserCertificates(String ownerId, Integer offset, Integer limit, String enableFilter, String expireFilter, String certType, Boolean isOrphan, String dateValidStart) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/v2/certificate/mycertificates".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "ownerId", ownerId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "offset", offset));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "enableFilter", enableFilter));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "expireFilter", expireFilter));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "certType", certType));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "isOrphan", isOrphan));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "dateValidStart", dateValidStart));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<GetUserCertificates200Response> localVarReturnType = new GenericType<GetUserCertificates200Response>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Import Certificate
   * Import a certificate into nebulaSUITE
   * @param importCertificateDTO  (required)
   * @return a {@code CertIdDTO}
   * @throws ApiException if fails to make API call
   */
  public CertIdDTO importCertificate(ImportCertificateDTO importCertificateDTO) throws ApiException {
    Object localVarPostBody = importCertificateDTO;
    
    // verify the required parameter 'importCertificateDTO' is set
    if (importCertificateDTO == null) {
      throw new ApiException(400, "Missing the required parameter 'importCertificateDTO' when calling importCertificate");
    }
    
    // create path and map variables
    String localVarPath = "/api/v1/certificate/import".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<CertIdDTO> localVarReturnType = new GenericType<CertIdDTO>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Issue Certificate
   * Issues a requested certificate. This operation is asynchronous, in order to execute the Activate_Certificate operation we must wait until the issue status of the request is ENROLL (check Get Certificate Request operation).
   * @param requestId The certificate request id (required)
   * @param issueCertificateRequest  (required)
   * @throws ApiException if fails to make API call
   */
  public void issueCertificate(String requestId, IssueCertificateRequest issueCertificateRequest) throws ApiException {
    Object localVarPostBody = issueCertificateRequest;
    
    // verify the required parameter 'requestId' is set
    if (requestId == null) {
      throw new ApiException(400, "Missing the required parameter 'requestId' when calling issueCertificate");
    }
    
    // verify the required parameter 'issueCertificateRequest' is set
    if (issueCertificateRequest == null) {
      throw new ApiException(400, "Missing the required parameter 'issueCertificateRequest' when calling issueCertificate");
    }
    
    // create path and map variables
    String localVarPath = "/v2/certificate/request/{requestId}/issue".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "requestId" + "\\}", apiClient.escapeString(requestId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };


    apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}

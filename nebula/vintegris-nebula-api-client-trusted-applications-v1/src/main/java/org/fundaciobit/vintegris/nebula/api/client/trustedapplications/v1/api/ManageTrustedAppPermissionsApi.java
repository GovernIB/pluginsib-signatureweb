package org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponse;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseRegenerateTrustedAppViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.PermissionsBody;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.TrustedAppSecParamsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class ManageTrustedAppPermissionsApi {
  private ApiClient apiClient;

  public ManageTrustedAppPermissionsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ManageTrustedAppPermissionsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Modify the permissions assigned to a trusted app
   * 
   * @param id Trusted app identifier (required)
   * @param permissionsBody  (optional)
   * @return a {@code NebulaResponse}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponse changePermissions(String id, PermissionsBody permissionsBody) throws ApiException {
    Object localVarPostBody = permissionsBody;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling changePermissions");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}/permissions".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      "*/*"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "nebulasuite" };

    GenericType<NebulaResponse> localVarReturnType = new GenericType<NebulaResponse>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Generate signature key for trusted app authorization
   * 
   * @param id Trusted app identifier (required)
   * @return a {@code NebulaResponseRegenerateTrustedAppViewModel}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponseRegenerateTrustedAppViewModel regenerateAccessKey(String id) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling regenerateAccessKey");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}/accesskey".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      "*/*"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "nebulasuite" };

    GenericType<NebulaResponseRegenerateTrustedAppViewModel> localVarReturnType = new GenericType<NebulaResponseRegenerateTrustedAppViewModel>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Modify the security parameters assigned to a trusted app
   * 
   * @param id Trusted app identifier (required)
   * @param trustedAppSecParamsViewModel  (required)
   * @return a {@code NebulaResponse}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponse setSecurityParameters(String id, TrustedAppSecParamsViewModel trustedAppSecParamsViewModel) throws ApiException {
    Object localVarPostBody = trustedAppSecParamsViewModel;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling setSecurityParameters");
    }
    
    // verify the required parameter 'trustedAppSecParamsViewModel' is set
    if (trustedAppSecParamsViewModel == null) {
      throw new ApiException(400, "Missing the required parameter 'trustedAppSecParamsViewModel' when calling setSecurityParameters");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}/security".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    
    final String[] localVarAccepts = {
      "*/*"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "nebulasuite" };

    GenericType<NebulaResponse> localVarReturnType = new GenericType<NebulaResponse>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

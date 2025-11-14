package org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.AccessGroupsViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponse;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseListGroupViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class ManageGroupsApi {
  private ApiClient apiClient;

  public ManageGroupsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ManageGroupsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Modify the groups assigned to a trusted app
   * 
   * @param id Trusted app identifier (required)
   * @param accessGroupsViewModel  (required)
   * @return a {@code NebulaResponse}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponse changeGroups(String id, AccessGroupsViewModel accessGroupsViewModel) throws ApiException {
    Object localVarPostBody = accessGroupsViewModel;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling changeGroups");
    }
    
    // verify the required parameter 'accessGroupsViewModel' is set
    if (accessGroupsViewModel == null) {
      throw new ApiException(400, "Missing the required parameter 'accessGroupsViewModel' when calling changeGroups");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}/groups".replaceAll("\\{format\\}","json")
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
   * Get list of all groups configured in a trusted app
   * List only the groups of a trusted app
   * @param id Trusted app identifier (required)
   * @return a {@code NebulaResponseListGroupViewModel}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponseListGroupViewModel getAllGroups(String id) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getAllGroups");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}/groups".replaceAll("\\{format\\}","json")
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

    GenericType<NebulaResponseListGroupViewModel> localVarReturnType = new GenericType<NebulaResponseListGroupViewModel>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

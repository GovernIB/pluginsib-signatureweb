package org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.CreateTrustedAppViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponse;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseDetailedTrustedAppViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseListTrustedAppViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class ManageTrustedAppApi {
  private ApiClient apiClient;

  public ManageTrustedAppApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ManageTrustedAppApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Create a new trusted app
   * 
   * @param createTrustedAppViewModel  (required)
   * @return a {@code NebulaResponseDetailedTrustedAppViewModel}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponseDetailedTrustedAppViewModel createTrustedApp(CreateTrustedAppViewModel createTrustedAppViewModel) throws ApiException {
    Object localVarPostBody = createTrustedAppViewModel;
    
    // verify the required parameter 'createTrustedAppViewModel' is set
    if (createTrustedAppViewModel == null) {
      throw new ApiException(400, "Missing the required parameter 'createTrustedAppViewModel' when calling createTrustedApp");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app".replaceAll("\\{format\\}","json");

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

    GenericType<NebulaResponseDetailedTrustedAppViewModel> localVarReturnType = new GenericType<NebulaResponseDetailedTrustedAppViewModel>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Delete a trusted app
   * 
   * @param id Trusted app identifier (required)
   * @return a {@code NebulaResponse}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponse deleteTrustedApp(String id) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling deleteTrustedApp");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}".replaceAll("\\{format\\}","json")
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

    GenericType<NebulaResponse> localVarReturnType = new GenericType<NebulaResponse>() {};
    return apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get a specific trusted app
   * 
   * @param id Trusted app identifier (required)
   * @return a {@code NebulaResponseDetailedTrustedAppViewModel}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponseDetailedTrustedAppViewModel getTrustedApp(String id) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getTrustedApp");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}".replaceAll("\\{format\\}","json")
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

    GenericType<NebulaResponseDetailedTrustedAppViewModel> localVarReturnType = new GenericType<NebulaResponseDetailedTrustedAppViewModel>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Get list of trusted apps created in the system
   * 
   * @param page  (optional)
   * @param size  (optional)
   * @return a {@code NebulaResponseListTrustedAppViewModel}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponseListTrustedAppViewModel getTrustedApps(Integer page, Integer size) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/trusted/apps".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "size", size));

    
    
    
    final String[] localVarAccepts = {
      "*/*"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "nebulasuite" };

    GenericType<NebulaResponseListTrustedAppViewModel> localVarReturnType = new GenericType<NebulaResponseListTrustedAppViewModel>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Update a trusted app
   * 
   * @param id Trusted app identifier (required)
   * @param createTrustedAppViewModel  (required)
   * @return a {@code NebulaResponseDetailedTrustedAppViewModel}
   * @throws ApiException if fails to make API call
   */
  public NebulaResponseDetailedTrustedAppViewModel updateTrustedApp(String id, CreateTrustedAppViewModel createTrustedAppViewModel) throws ApiException {
    Object localVarPostBody = createTrustedAppViewModel;
    
    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateTrustedApp");
    }
    
    // verify the required parameter 'createTrustedAppViewModel' is set
    if (createTrustedAppViewModel == null) {
      throw new ApiException(400, "Missing the required parameter 'createTrustedAppViewModel' when calling updateTrustedApp");
    }
    
    // create path and map variables
    String localVarPath = "/trusted/app/{id}".replaceAll("\\{format\\}","json")
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

    GenericType<NebulaResponseDetailedTrustedAppViewModel> localVarReturnType = new GenericType<NebulaResponseDetailedTrustedAppViewModel>() {};
    return apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

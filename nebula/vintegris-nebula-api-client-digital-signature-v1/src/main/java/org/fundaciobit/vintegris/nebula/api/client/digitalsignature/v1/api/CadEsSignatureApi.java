package org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseOldAuthEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature400Codes;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature404Codes;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature500Codes;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class CadEsSignatureApi {
  private ApiClient apiClient;

  public CadEsSignatureApi() {
    this(Configuration.getDefaultApiClient());
  }

  public CadEsSignatureApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Sign a document with CAdES
   * Signs a document with the CAdES signature standard
   * @param data JSON containing the parameters needed to define how the signature will be made. See schema CadesSignatureRequest (required)
   * @param _file File to sign. Multipart File or fileHash parameter must be specified. (optional)
   * @return a {@code CadesSignatureResponse}
   * @throws ApiException if fails to make API call
   */
  public CadesSignatureResponse cadesSign(String data, File _file) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'data' is set
    if (data == null) {
      throw new ApiException(400, "Missing the required parameter 'data' when calling cadesSign");
    }
    
    // create path and map variables
    String localVarPath = "/api/v1/cadessign/sign".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    if (_file != null)
      localVarFormParams.put("file", _file);
if (data != null)
      localVarFormParams.put("data", data);

    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "multipart/form-data"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "Authorization" };

    GenericType<CadesSignatureResponse> localVarReturnType = new GenericType<CadesSignatureResponse>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

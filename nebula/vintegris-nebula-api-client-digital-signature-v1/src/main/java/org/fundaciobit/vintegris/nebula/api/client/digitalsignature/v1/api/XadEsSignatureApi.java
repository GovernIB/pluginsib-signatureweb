package org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseOldAuthEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature400Codes;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature404Codes;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature500Codes;
import java.io.File;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class XadEsSignatureApi {
  private ApiClient apiClient;

  public XadEsSignatureApi() {
    this(Configuration.getDefaultApiClient());
  }

  public XadEsSignatureApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Sign an XML file with XAdES
   * Sign an XML file using XAdES signature standard
   * @param _file XML file to sign (required)
   * @param data JSON containing the parameters needed to define how the signature will be made. See schema XadesSignatureRequest (required)
   * @return a {@code XadesSignatureResponse}
   * @throws ApiException if fails to make API call
   */
  public XadesSignatureResponse xadesSign(File _file, String data) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter '_file' is set
    if (_file == null) {
      throw new ApiException(400, "Missing the required parameter '_file' when calling xadesSign");
    }
    
    // verify the required parameter 'data' is set
    if (data == null) {
      throw new ApiException(400, "Missing the required parameter 'data' when calling xadesSign");
    }
    
    // create path and map variables
    String localVarPath = "/api/v2/xadessign/sign".replaceAll("\\{format\\}","json");

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

    GenericType<XadesSignatureResponse> localVarReturnType = new GenericType<XadesSignatureResponse>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

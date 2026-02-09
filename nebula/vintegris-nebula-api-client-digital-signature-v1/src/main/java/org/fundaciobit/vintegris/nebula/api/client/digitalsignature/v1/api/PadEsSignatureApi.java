package org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Configuration;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CSignDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CSignReqDTOV3;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseOldAuthEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature400Codes;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature404Codes;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.ErrorResponseSignature500Codes;
import java.io.File;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureOperationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.10.0")
public class PadEsSignatureApi {
  private ApiClient apiClient;

  public PadEsSignatureApi() {
    this(Configuration.getDefaultApiClient());
  }

  public PadEsSignatureApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Sign a PDF file
   * 
   * @param _file PDF file to sign (required)
   * @param data JSON containing the parameters needed to define how the signature will be made. See schema PadesSignatureRequest (required)
   * @param images List of images that will be used in the visible signature (optional)
   * @return a {@code PadesSignatureOperationResponse}
   * @throws ApiException if fails to make API call
   */
  public PadesSignatureOperationResponse signDocumentV1(File _file, String data, List<File> images) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter '_file' is set
    if (_file == null) {
      throw new ApiException(400, "Missing the required parameter '_file' when calling signDocumentV1");
    }
    
    // verify the required parameter 'data' is set
    if (data == null) {
      throw new ApiException(400, "Missing the required parameter 'data' when calling signDocumentV1");
    }
    
    // create path and map variables
    String localVarPath = "/signature/pades/v1/sign".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    if (_file != null)
      localVarFormParams.put("file", _file);
if (images != null)
      localVarFormParams.put("images", images);
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

    GenericType<PadesSignatureOperationResponse> localVarReturnType = new GenericType<PadesSignatureOperationResponse>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * signHash
   * Sign a hash with a certificate
   * @param csignReqDTOV3  (required)
   * @return a {@code CSignDTO}
   * @throws ApiException if fails to make API call
   */
  public CSignDTO signHashUsingPOST2(CSignReqDTOV3 csignReqDTOV3) throws ApiException {
    Object localVarPostBody = csignReqDTOV3;
    
    // verify the required parameter 'csignReqDTOV3' is set
    if (csignReqDTOV3 == null) {
      throw new ApiException(400, "Missing the required parameter 'csignReqDTOV3' when calling signHashUsingPOST2");
    }
    
    // create path and map variables
    String localVarPath = "/api/v3/certificate/signhash".replaceAll("\\{format\\}","json");

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

    GenericType<CSignDTO> localVarReturnType = new GenericType<CSignDTO>() {};
    return apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

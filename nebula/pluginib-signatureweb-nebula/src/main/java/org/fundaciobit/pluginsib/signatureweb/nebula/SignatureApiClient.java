
package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

/**
 * PadesSignatureApiClient
 * @author anadal
 * 10 nov 2025 10:49:42
 */
@javax.annotation.Generated(
        value = "org.openapitools.codegen.languages.JavaClientCodegen",
        comments = "Generator version: 7.10.0")
public class SignatureApiClient extends ApiClient {

    /**
     * Serialize the given Java object into string entity according the given
     * Content-Type (only JSON is supported for now).
     * @param obj the object to serialize
     * @param formParams the form parameters
     * @param contentType the content type
     * @return an {@code Entity}
     * @throws ApiException on failure to serialize
     */
    public Entity<?> serialize(Object obj, Map<String, Object> formParams, String contentType) throws ApiException {
        Entity<?> entity = null;
        if (contentType.startsWith("multipart/form-data")) {
            MultipartFormDataOutput multipart = new MultipartFormDataOutput();
            //MultiPart multiPart = new MultiPart();
            for (Entry<String, Object> param : formParams.entrySet()) {

                // ================  AFEGIT -INICI  =================
                if (param.getKey().equals("file")) {
                    File file = (File) param.getValue();
                    try {
                        multipart.addFormData(param.getKey(), new FileInputStream(file),
                                MediaType.valueOf("application/pdf"), file.getName());
                    } catch (IOException e) {
                        throw new ApiException("Could not serialize multipart/form-data " + e.getMessage());
                    }
                    continue;
                }

                if (param.getKey().equals("data")) {
                    multipart.addFormData(param.getKey(), param.getValue().toString(), MediaType.APPLICATION_JSON_TYPE);
                }

                // ================  AFEGIT -FINAL  =================

                if (param.getValue() instanceof File) {
                    File file = (File) param.getValue();
                    try {
                        multipart.addFormData(param.getKey(), new FileInputStream(file),
                                MediaType.APPLICATION_OCTET_STREAM_TYPE, file.getName());
                    } catch (FileNotFoundException e) {
                        throw new ApiException("Could not serialize multipart/form-data " + e.getMessage());
                    }
                } else {
                    multipart.addFormData(param.getKey(), param.getValue().toString(),
                            MediaType.APPLICATION_JSON_TYPE/* APPLICATION_OCTET_STREAM_TYPE*/);
                }
            }
            GenericEntity<MultipartFormDataOutput> genericEntity = new GenericEntity<MultipartFormDataOutput>(
                    multipart) {
            };
            entity = Entity.entity(genericEntity, MediaType.MULTIPART_FORM_DATA_TYPE);
        } else if (contentType.startsWith("application/x-www-form-urlencoded")) {
            Form form = new Form();
            for (Entry<String, Object> param : formParams.entrySet()) {
                form.param(param.getKey(), parameterToString(param.getValue()));
            }
            entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        } else {
            // We let jersey handle the serialization
            entity = Entity.entity(obj, contentType);
        }
        return entity;
    }

}

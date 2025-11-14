package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.ext.ContextResolver;

import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.CadEsSignatureApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.PadEsSignatureApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.XadEsSignatureApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureOperationResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureRequest.SignLevelEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesVisualSignature;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.SignPackageEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.auth.HttpBearerAuth;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.AuthenticationApi;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseAuthorizeResponseViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseLoginResponseViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;
//import org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.api.XadEsApi;
//import org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.model.XadesSignRequestData;
//import org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.model.XadesSignRequestData.DigestAlgorithmEnum;
//import org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.model.XadesSignRequestData.SignPackageEnum;
//import org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.model.XadesSignatureResponseDTO;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * @author anadal
 * 12 nov 2025 11:27:27
 */
public class NebulaTester {

    // Método para codificar en Base64 URL Safe
    private static String base64UrlEncode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }

    // Método para calcular SHA256
    private static byte[] sha256(String data) throws NoSuchAlgorithmException {
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    // Método para calcular HMAC-SHA256
    private static byte[] hmacSha256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    public static void main(String[] args) {
        try {
            
            
            Properties prop = new Properties();
            prop.load(new FileReader("nebula.properties"));
            

            String url = prop.getProperty("url");
            String url_auth = prop.getProperty("url_auth");


            // PORTAFIB CONFIGURACIO
            String applicationName = prop.getProperty("applicationName");
            String tenantId = prop.getProperty("tenantId");
            String appId = prop.getProperty("appId");
            String accessKey = prop.getProperty("accessKey");
            String username = prop.getProperty("username");
            String usernameB64 = Base64.getEncoder().encodeToString(username.getBytes());
            System.out.println("base64[" + username + "] = " + usernameB64);

            String trusted_app_token = generateTrustedAppToken(applicationName, tenantId, appId, accessKey);

            // Print the trusted_app_token
            System.out.println("------ trusted_app_token (authorization en PostMan) ---- ");
            System.out.println(trusted_app_token);

            //if (true) return;

            String token = getUserToken(trusted_app_token, url_auth, applicationName, tenantId, appId, accessKey,
                    usernameB64);

            GetMyCertificates200ResponseCertificatesListInner certificat;
            {
                List<GetMyCertificates200ResponseCertificatesListInner> certificats = getUserCertificates(url, token);

                if (certificats == null) {
                    throw new Exception("L'usuari " + username + " no té certificats disponibles.");
                }

                certificat = certificats.get(0);

                System.out.println(certificat);
            }

            //if (true) return;

            testSignaturePades(url, token, certificat.getSigningId());

            //testSignatureXades(url, token, certificat.getSigningId());

            //testSignatureCades(url, token, certificat.getSigningId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<GetMyCertificates200ResponseCertificatesListInner> getUserCertificates(String url, String token)
            throws org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException, Exception {

        System.out.println(" -------  Descarregant Certificats de l'usuari -------");
        DigitalCertificateApi apiCert = getDigitalCertificateApi(url, token);

        // @param enableFilter ENABLED/DISABLED (optional)
        final String enableFilter = null; //"ENABLED";
        // @param expireFilter BOTH/EXPIRED/NOT_EXPIRED (optional)
        final String expireFilter = null; //"NOT_EXPIRED";

        GetMyCertificates200Response response = apiCert.getMyCertificates(null, null, enableFilter, expireFilter, null,
                null, null);

        //System.out.println(response);

        Integer total = response.getTotalCertificates();
        System.out.println("Total: " + total);

        if (total == null || total == 0) {
            return null;
        }

        return response.getCertificatesList();
    }

    public static DigitalCertificateApi getDigitalCertificateApi(String url, String token) {
        org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient apiClient;
        apiClient = new org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiClient();

        apiClient.setBasePath(url);

        org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.HttpBearerAuth auth;
        auth = (org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.auth.HttpBearerAuth) apiClient
                .getAuthentication("Authorization");
        final String certToken;
        if (token.startsWith("Bearer")) {
            certToken = token.substring(7);
        } else {
            certToken = token;
        }
        auth.setBearerToken(certToken);
        System.out.println(" Token Certificate Api: " + certToken);

        DigitalCertificateApi apiCert = new DigitalCertificateApi(apiClient);
        return apiCert;
    }

    protected static void testSignaturePades(String url, String token, String signingID) throws IOException {

        System.out.println("---------- Realitzant Firma PAdES ... ----------");
        /*
        String dataStr = "{\n"
                // Tipus de firma a realitzar
                + "    \"signLevel\":\"LTA\",\n" + "    \"digestAlgorithm\":\"SHA256\",\n"
                // Id de certificat
                + "    \"certId\":\"" + signingID + "\",\n"
                // Dades addicionals a la firma
                + "    \"location\": \"Barcelona\",\n"
                + "    \"visualSignature\":\n" + "    {\n"
                + "        \"xAxis\":50,\n"
                + "        \"yAxis\":90,\n"
                + "        \"width\":260,\n"
                + "        \"height\":80,\n"
                + "        \"page\":1,\n"
                + "        \"positionInPercentage\":true,\n"
                + "        \"fontName\":\"HELVETICA\",\n"
                + "        \"fontSize\":9,\n"
                + "        \"textColor\":[0,0,0,255],\n"
                + "        \"text\":\"Firmado digitalmente por\\n%CN%\\na fecha %DATE%\"\n"
                + "    },\n"
                + "    \"dateFormat\": \"EEE, d MMM yyyy HH:mm:ss\",\n"
                + "    \"timeZone\": \"Europe/Madrid\",\n"
                + "    \"reason\": \"Prueba de firma PAdES\",\n"
                + "    \"signerName\": \"Sebas\"\n" + "}";
        */

        PadesSignatureRequest psr = new PadesSignatureRequest();
        psr.setSignLevel(SignLevelEnum.LTA);
        psr.setCertId(signingID);
        psr.setLocation("Esporles");
        psr.setDateFormat("EEE, d MMM yyyy HH:mm:ss");
        psr.setTimeZone("Europe/Madrid");
        psr.setReason("Prova Firma PAdES");
        psr.setSignerName("Antoni");

        PadesVisualSignature vs = new PadesVisualSignature();
        vs.setxAxis(180f);
        vs.setyAxis(190f);
        vs.setWidth(260);
        vs.setHeight(80);
        vs.setPage(1);
        vs.setPositionInPercentage(false);
        vs.setFontName(
                org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesVisualSignature.FontNameEnum.HELVETICA);
        vs.setFontSize(11);
        vs.setTextColor(List.of(255, 0, 0, 255));
        vs.setText("Signat Digitalment per\n%CN%\nen data %DATE%");

        psr.setVisualSignature(vs);

        File pdfASignar = new File("pdf_a_signar.pdf");

        //org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient apiClient;
        //apiClient = new org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient();
        SignatureApiClient apiClient = new SignatureApiClient();

        //url = "http://localhost:80";

        apiClient.setBasePath(url);
        HttpBearerAuth auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");
        auth.setBearerToken(token);

        // {{url}}/signature/pades/v1/sign
        PadEsSignatureApi apiSign = new PadEsSignatureApi(apiClient);
        try {

            String dataStr = toJson(apiClient.getJSON(), psr);
            System.out.println(dataStr);

            PadesSignatureOperationResponse signedResponse = apiSign.signDocumentV1(pdfASignar, dataStr, null);

            System.out.println(signedResponse.getContent().getCnCertificate());

            File signed = new File("pdf-signat.pdf");
            Files.write(signed.toPath(), Base64.getDecoder().decode(signedResponse.getContent().getSignedFile()));

            System.out.println("Firma PAdES guardada a " + signed.getAbsolutePath());

        } catch (ApiException ae) {

            System.err.println("Code: " + ae.getCode());
            System.err.println("Message: " + ae.getMessage());
            System.err.println("Body: " + ae.getResponseBody());

        }

        System.out.println("FINAL");
    }

    protected static void testSignatureXades(String url, String token, String signingID) throws Exception {

        System.out.println("---------- Realitzant Firma XAdES ... ----------");

        //        String dataStr = "{\n"
        //                + "    \"signLevel\":\"B\",\n"
        //                + "    \"signPackage\":\"ENVELOPED\",\n"
        //                + "    \"digestAlgorithm\":\"SHA256\",\n"
        //                + "    \"certId\":" + signingID + "\n"
        //                + "}";

        File xmlASignar = new File("sample.xml");

        // org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.services.ApiClient apiClient;
        // apiClient = new org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.services.ApiClient();
        SignatureApiClient apiClient = new SignatureApiClient();

        //url = "http://localhost:80";

        apiClient.setBasePath(url);
        HttpBearerAuth auth;
        auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");

        auth.setBearerToken(token);

        XadEsSignatureApi apiSign = new XadEsSignatureApi(apiClient);
        try {

            XadesSignatureRequest xades = new XadesSignatureRequest();
            xades.setSignLevel(XadesSignatureRequest.SignLevelEnum.B);
            xades.setSignPackage(SignPackageEnum.ENVELOPED);
            xades.setDigestAlgorithm(
                    org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA256);
            xades.setCertId(signingID);

            String dataStr = toJson(apiClient.getJSON(), xades);
            System.out.println(dataStr);

            XadesSignatureResponse signedResponse = apiSign.xadesSign(xmlASignar, dataStr);

            System.out.println("CODE: " + signedResponse.getResponseCode());
            System.out.println("MSGE: " + signedResponse.getResponseMessage());

            File signed = new File("sample_xml_signed.xsig");
            Files.write(signed.toPath(), Base64.getDecoder().decode(signedResponse.getSignedFile()));

            System.out.println("Firma XAdES guardada a " + signed.getAbsolutePath());

        } catch (ApiException ae) {

            System.err.println("Code: " + ae.getCode());
            System.err.println("Message: " + ae.getMessage());
            System.err.println("Body: " + ae.getResponseBody());

        }

        System.out.println("FINAL");
    }

    protected static void testSignatureCades(String url, String token, String signingID) throws Exception {

        System.out.println("---------- Realitzant Firma CAdES ... ----------");

        //        String dataStr = "{\n"
        //                + "    \"signLevel\":\"B\",\n"
        //                + "    \"signPackage\":\"DETACHED\",\n"
        //                + "    \"digestAlgorithm\":\"SHA256\",\n"
        //                + "    \"certId\":" + signingID + "\n"
        //                + "}";

        File binASignar = new File("binari.bin");

        // org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.services.ApiClient apiClient;
        // apiClient = new org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.services.ApiClient();
        SignatureApiClient apiClient = new SignatureApiClient();

        //url = "http://localhost:80";

        apiClient.setBasePath(url);
        HttpBearerAuth auth;
        auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");

        auth.setBearerToken(token);

        CadEsSignatureApi apiSign = new CadEsSignatureApi(apiClient);
        try {

            CadesSignatureRequest xades = new CadesSignatureRequest();
            xades.setSignLevel(CadesSignatureRequest.SignLevelEnum.B);
            xades.setSignPackage(
                    org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.SignPackageEnum.ENVELOPING);
            xades.setDigestAlgorithm(
                    org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA256);
            xades.setCertId(Integer.parseInt(signingID));

            String dataStr = toJson(apiClient.getJSON(), xades);
            System.out.println(dataStr);

            CadesSignatureResponse signedResponse = apiSign.cadesSign(dataStr, binASignar);

            System.out.println("CODE: " + signedResponse.getResponseCode());
            System.out.println("MSGE: " + signedResponse.getResponseMessage());

            File signed = new File("binari_bin_signed.csig");
            Files.write(signed.toPath(), Base64.getDecoder().decode(signedResponse.getSignedFile()));

            System.out.println("Firma CAdES guardada a " + signed.getAbsolutePath());

        } catch (ApiException ae) {

            System.err.println("Code: " + ae.getCode());
            System.err.println("Message: " + ae.getMessage());
            System.err.println("Body: " + ae.getResponseBody());

        }

        System.out.println("FINAL");
    }

    protected static String toJson(ContextResolver<ObjectMapper> context, Object obj) throws JsonProcessingException {
        ObjectMapper mapper = context.getContext(obj.getClass());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);

        String dataStr = mapper.writer().writeValueAsString(obj);
        return dataStr;
    }

    public static String getUserToken(String trusted_app_token, String url_auth, String applicationName,
            String tenantId, String appId, String accessKey, String usernameB64)
            throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException,
            org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException {
        String token;

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(url_auth);
        AuthenticationApi api = new AuthenticationApi(apiClient);

        final String authorization = trusted_app_token;
        NebulaResponseAuthorizeResponseViewModel authResponse = api.authorize(authorization);

        String applicationToken = authResponse.getContent().getAuthorization();
        //String application = "Bearer " + token;
        final String application = applicationToken;

        System.out.println(" ---------- application -----------");
        System.out.println(application);

        // First Call

        NebulaResponseLoginResponseViewModel loginFirstResponse = api.appLoginFirst(application, usernameB64);
        String token1 = loginFirstResponse.getContent().getToken();
        System.out.println(" ---------- token1 " + loginFirstResponse.getCode() + "-----------");
        System.out.println(token1);

        // Second call
        NebulaResponseLoginResponseViewModel secondFirstResponse = api.appLoginSecond(application, token1);
        String token2 = secondFirstResponse.getContent().getToken();
        System.out.println(" ---------- token2: " + secondFirstResponse.getCode() + " -----------");
        System.out.println(token2);
        token = token2;

        return token;
    }

    public static String generateTrustedAppToken(String applicationName, String tenantId, String appId,
            String accessKey) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        // Generate random token ID
        Random random = new Random();
        String trusted_app_tokenId = "ID_Aleatori_" + random.nextInt(300000000);

        // Prepare timestamp in seconds
        long currentTimestamp = System.currentTimeMillis() / 1000;

        // Set headers for the trusted_app_token
        Map<String, String> JWT_Header = new HashMap<>();
        JWT_Header.put("typ", "JWT");
        JWT_Header.put("alg", "HS256");

        // Set the Payload for the trusted_app_token
        Map<String, Object> JWT_Payload = new HashMap<>();
        JWT_Payload.put("sub", tenantId);
        JWT_Payload.put("iat", currentTimestamp);
        JWT_Payload.put("jti", trusted_app_tokenId);
        JWT_Payload.put("iss", applicationName);
        JWT_Payload.put("azp", appId);

        // Convert objects to JSON strings
        ObjectMapper objectMapper = new ObjectMapper();
        String stringifiedJWT_Header = objectMapper.writeValueAsString(JWT_Header);
        String stringifiedJWT_Payload = objectMapper.writeValueAsString(JWT_Payload);

        // Encode JWT_Header and JWT_Payload
        String encodedJWT_Header = base64UrlEncode(stringifiedJWT_Header.getBytes(StandardCharsets.UTF_8));
        String encodedJWT_Payload = base64UrlEncode(stringifiedJWT_Payload.getBytes(StandardCharsets.UTF_8));

        // Hash the key with SHA256
        byte[] hashedAccessKey = sha256(accessKey);

        // Prepare data for signature
        String dataToSign = encodedJWT_Header + "." + encodedJWT_Payload;

        // Sign the token with HMAC-SHA256 using the hashed access key
        byte[] JWT_Signature = hmacSha256(dataToSign.getBytes(StandardCharsets.UTF_8), hashedAccessKey);

        // Encode JWT_Signature
        String encodedJWT_Signature = base64UrlEncode(JWT_Signature);

        // Construct the signed trusted_app_token
        String trusted_app_token = encodedJWT_Header + "." + encodedJWT_Payload + "." + encodedJWT_Signature;
        return trusted_app_token;
    }

    /*
    {
        DigitalCertificateApi api = getDigitalCertificateApi(url, token);
    
        String requestId = "123456789"; //"cert_request_" + username + "_" + System.currentTimeMillis();
    
        System.out.println(" -------------  Emisió de certificat amb  RequestID = " + requestId);
    
        IssueCertificateRequest issueCertificateRequest = new IssueCertificateRequest();
        issueCertificateRequest.setPin("1234");
        issueCertificateRequest.setPublish(null);
    
        api.issueCertificate(requestId, issueCertificateRequest);
    
        System.out.println(" Esperant a l'emissió del certificat ...");
    
        System.in.read();
    
    }
    */
    /*
    {
        
        org.fundaciobit.vintegris.nebula.api.client.certificaterequest.v1.services.ApiClient apiClient;
        apiClient = new org.fundaciobit.vintegris.nebula.api.client.certificaterequest.v1.services.ApiClient();
    
        apiClient.setBasePath(url);
    
        org.fundaciobit.vintegris.nebula.api.client.certificaterequest.v1.services.auth.HttpBearerAuth auth;
        auth = (org.fundaciobit.vintegris.nebula.api.client.certificaterequest.v1.services.auth.HttpBearerAuth) apiClient
                .getAuthentication("Authorization");
        final String certToken;
        if (token.startsWith("Bearer")) {
            certToken = token.substring(7);
        } else {
            certToken = token;
        }
        auth.setBearerToken(certToken);
        
        
        RequestCertificateApi api = new RequestCertificateApi(apiClient);
        
        NewUserRequestCertificateViewModel n = new NewUserRequestCertificateViewModel();
        n.setApplication(applicationName);
        n.setFirstName("Antoni");
        n.setLastName("Nadal");
        
        
        api.newUserRequestCertificate(n);
    
        
    }
    */

}

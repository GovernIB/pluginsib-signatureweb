package org.fundaciobit.pluginsib.signatureweb.nebula;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.fundaciobit.pluginsib.core.v3.utils.CertificateUtils;
import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signature.api.PolicyInfoSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignaturesSet;
import org.fundaciobit.pluginsib.signatureweb.api.AbstractSignatureWebPlugin;
import org.fundaciobit.pluginsib.signatureweb.api.SignaturesSetWeb;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.CadEsSignatureApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.PadEsSignatureApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.api.XadEsSignatureApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureOperationResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureOperationResponse.CodeEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureResponse;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureRequest.DigestAlgorithmEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.PadesSignatureRequest.SignLevelEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.SignPackageEnum;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiException;
import org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.auth.HttpBearerAuth;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.api.AuthenticationApi;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseAuthorizeResponseViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.model.NebulaResponseLoginResponseViewModel;
import org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ext.ContextResolver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * Implementació del plugin de firma web de Nebula (Vintegris)
 *
 * @author anadal
 */
public class NebulaSignatureWebPlugin extends AbstractSignatureWebPlugin {

    public static final String NEBULA_BASE_PROPERTIES = PLUGINSIB_SIGNATUREWEB_BASE_PROPERTY + "nebula.";

    public static String SELECTED_CERTIFICATE_SESSION = "Selected_Certificate_Session";

    // Constructors per defecte

    public NebulaSignatureWebPlugin() {
        super();
    }

    public NebulaSignatureWebPlugin(String propertyKeyBase) {
        super(propertyKeyBase);
    }

    public NebulaSignatureWebPlugin(String propertyKeyBase, Properties properties) {
        super(propertyKeyBase, properties);
    }

    /*
    DigitalCertificateApi apiCert = getDigitalCertificateApi(url, token);
    
    // Inicialització
    private FortressApi getApi(String username) throws Exception {
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
    
    }
    */

    // Propietats de configuració
    /*
    
    public int getConnectTimeout() {
        return Integer.parseInt(getProperty(NEBULA_BASE_PROPERTIES + "connectTimeout", "60"));
    }
    
    public int getReadTimeout() {
        return Integer.parseInt(getProperty(NEBULA_BASE_PROPERTIES + "readTimeout", "60"));
    }
    */
    /////////

    @Override
    public String getName(Locale locale) {
        return getTraduccio("pluginname", locale);
    }

    @Override
    public String filter(HttpServletRequest request, SignaturesSetWeb signaturesSet, Map<String, Object> parameters) {

        String filter = super.filter(request, signaturesSet, parameters);

        // Si ja ha trobat una incompatibilitat llavors tornam
        if (filter != null) {
            return filter;
        }

        // Comprovam si l'usuari té Certificats
        try {

            String nif = signaturesSet.getCommonInfoSignature().getAdministrationID();

            List<GetMyCertificates200ResponseCertificatesListInner> certs = getUserCertificates(nif);

            // TODO ficar dins sessió el certificat !!!!!

            if (certs == null || certs.size() == 0) {
                String msg = "L'usuari amb nif " + nif + " no té cap certificat digital vàlid a NEBULA.";
                log.error(msg);
                return msg;
            }

        } catch (Exception e) {
            String msg = "Error durant la connexio amb el servidor NEBULA: " + e.getMessage();
            log.error(msg, e);
            return msg;
        }

        return null;

    }

    @Override
    public void closeSignaturesSet(HttpServletRequest request, String id) {
        super.closeSignaturesSet(request, id);
    }

    @Override
    public String signDocuments(HttpServletRequest request, String absolutePluginRequestPath,
            String relativePluginRequestPath, SignaturesSetWeb signaturesSet, Map<String, Object> parameters) {

        return signDocumentsDirect(request, absolutePluginRequestPath, relativePluginRequestPath, signaturesSet,
                parameters);

    }

    /**
     * 
     * @param request
     * @param absolutePluginRequestPath
     * @param relativePluginRequestPath
     * @param signaturesSet
     * @param parameters
     * @return
     */
    protected String signDocumentsDirect(HttpServletRequest request, String absolutePluginRequestPath,
            String relativePluginRequestPath, SignaturesSetWeb signaturesSet, Map<String, Object> parameters) {

        try {

            signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_IN_PROGRESS);

            GetMyCertificates200ResponseCertificatesListInner selectedCertificate = null;
            // Només per entorn de test
            if (request != null) {
                selectedCertificate = (GetMyCertificates200ResponseCertificatesListInner) request.getSession()
                        .getAttribute(SELECTED_CERTIFICATE_SESSION);
            }

            Locale locale = new Locale(signaturesSet.getCommonInfoSignature().getLanguageUI());

            String nif = signaturesSet.getCommonInfoSignature().getAdministrationID();

            if (selectedCertificate == null) {

                List<GetMyCertificates200ResponseCertificatesListInner> certificates = getUserCertificates(nif);

                if (certificates == null || certificates.size() == 0) {
                    // TODO XYZ ZZZ TRA 

                    throw new Exception("No s'han trobat certificats per l'usuari " + nif);
                }

                selectedCertificate = certificates.get(0);

            }

            for (FileInfoSignature fis : signaturesSet.getFileInfoSignatureArray()) {

                try {
                    if (fis.getSignType().equals(FileInfoSignature.SIGN_TYPE_PADES)) {

                        doSignaturePades(fis, signaturesSet, selectedCertificate, nif);

                    } else if (fis.getSignType().equals(FileInfoSignature.SIGN_TYPE_CADES)) {

                        doSignatureCades(fis, signaturesSet, selectedCertificate, nif);

                    } else if (fis.getSignType().equals(FileInfoSignature.SIGN_TYPE_XADES)) {

                        doSignatureXades(fis, signaturesSet, selectedCertificate, nif);

                    } else {

                        // TODO XYZ ZZZ TRA
                        throw new Exception(
                                "Tipus de firma " + fis.getSignType() + " no suportat pel " + getName(locale));
                    }

                    fis.getStatusSignature().setStatus(StatusSignaturesSet.STATUS_FINAL_OK);

                } catch (Throwable th) {

                    String errorMsg;
                    // TODO XYZ ZZZ   S'ha de centralitzar l'API en un SOL LLOC !!!!!
                    if (th instanceof ApiException) {

                        ApiException ae = (ApiException) th;

                        log.error("Code: " + ae.getCode());
                        log.error("Message: " + ae.getMessage());
                        log.error("Body: " + ae.getResponseBody());

                        errorMsg = "Error realitzant la firma: " + ae.getMessage() + " (Code: " + ae.getCode()
                                + ", Body: " + ae.getResponseBody() + ")";
                    } else {
                        errorMsg = "Error realitzant la firma: " + th.getMessage();
                    }

                    log.error(errorMsg, th);

                    StatusSignaturesSet sss = fis.getStatusSignature();
                    sss.setErrorMsg(errorMsg);
                    sss.setErrorException(th);
                    sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);

                }

            }

            StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
            sss.setStatus(StatusSignaturesSet.STATUS_FINAL_OK);

        } catch (Throwable th) {

            String errorMsg = "Error global realitzant les firmes: " + th.getMessage();

            StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
            sss.setErrorMsg(errorMsg);
            sss.setErrorException(th);
            sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);

        }

        return signaturesSet.getUrlFinal();

        //addSignaturesSet(signaturesSet);
        //return relativePluginRequestPath + "/" + INICI_FIRMA;
    }

    protected void doSignatureXades(FileInfoSignature fis, SignaturesSetWeb signaturesSet,
            GetMyCertificates200ResponseCertificatesListInner certificat, String nif) throws Exception {

        String certId = certificat.getSigningId();

        SignatureApiClient apiClient = new SignatureApiClient();

        File xmlASignar = fis.getFileToSign();

        //url = "http://localhost:80";

        apiClient.setBasePath(getPropertyRequired(NEBULA_BASE_PROPERTIES + "url"));
        HttpBearerAuth auth;
        auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");

        auth.setBearerToken(getApiToken(nif));

        XadEsSignatureApi apiSign = new XadEsSignatureApi(apiClient);
        try {

            XadesSignatureRequest xades = new XadesSignatureRequest();
            if (fis.isUserRequiresTimeStamp()) {
                xades.setSignLevel(XadesSignatureRequest.SignLevelEnum.LT);
            } else {
                xades.setSignLevel(XadesSignatureRequest.SignLevelEnum.B);
            }

            PolicyInfoSignature policyInfo = fis.getPolicyInfoSignature();
            if (policyInfo != null) {
                xades.setPolicyId(policyInfo.getPolicyIdentifier());
                xades.setPolicyHash(policyInfo.getPolicyIdentifierHash());
                xades.setPolicyDigestAlgorithm(convertAlgorithm(policyInfo.getPolicyIdentifierHashAlgorithm()));
            }

            xades.setSignPackage(convertModeToSignPackageEnumXades(fis.getSignMode()));
            xades.setDigestAlgorithm(convertAlgorithmToDigestAlgorithmEnumXades(fis.getSignAlgorithm()));
            xades.setCertId(certId);

            String dataStr = toJson(apiClient.getJSON(), xades);
            if (isDebug()) {
                log.info("XAdES Signature Request: " + dataStr);
            }

            XadesSignatureResponse signedResponse = apiSign.xadesSign(xmlASignar, dataStr);

            String codi = signedResponse.getResponseCode();

            if (isDebug()) {
                log.info("CODE: " + codi);
                log.info("MSGE: " + signedResponse.getResponseMessage());
            }

            if (codi.equals("0000")) {

                File signedData = File.createTempFile("nebula_", "_xadessignedfile");

                Files.write(signedData.toPath(), Base64.getDecoder().decode(signedResponse.getSignedFile()));

                fis.getStatusSignature().setSignedData(signedData);

                if (isDebug()) {
                    log.info("Firma XAdES guardada a " + signedData.getAbsolutePath());
                }
            } else {
                throw new Exception("Error realitzant la firma XAdES: Code " + codi + ", Msg: "
                        + signedResponse.getResponseMessage());
            }

        } catch (ApiException ae) {

            log.error("Code: " + ae.getCode());
            log.error("Message: " + ae.getMessage());
            log.error("Body: " + ae.getResponseBody());

            throw new Exception(
                    "Excepció realitzant la firma XAdES: Code " + ae.getCode() + ", Msg: " + ae.getMessage());

        }

    }

    protected String convertAlgorithm(String algorithm) throws Exception {
        switch (algorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                return "SHA1";
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                return "SHA256";
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                return "SHA384";
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                return "SHA512";
            default:
                throw new Exception("L'algoritme de signatura " + algorithm + " no està suportat per XAdES");
        }
    }

    protected org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum convertAlgorithmToDigestAlgorithmEnumXades(
            String algorithm) throws Exception {
        switch (algorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA1;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA256;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA384;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA512;
            default:
                throw new Exception("L'algoritme de signatura " + algorithm + " no està suportat per XAdES");
        }
    }

    protected SignPackageEnum convertModeToSignPackageEnumXades(int mode) throws Exception {
        switch (mode) {
            case FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING:
                return SignPackageEnum.ENVELOPING;
            case FileInfoSignature.SIGN_MODE_DETACHED:
                return SignPackageEnum.DETACHED;
            case FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED:
                return SignPackageEnum.ENVELOPED;
            default:
                throw new Exception("El mode + " + mode + " per la firma XAdES no està suportat ");
        }

    }

    protected void doSignaturePades(FileInfoSignature fis, SignaturesSetWeb signaturesSet,
            GetMyCertificates200ResponseCertificatesListInner certificat, String nif) throws Exception, ApiException {

        if (isDebug()) {
            log.info("---------- Realitzant Firma PAdES ... ----------");
        }

        String certId = certificat.getSigningId();
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

        if (fis.isUserRequiresTimeStamp()) {
            psr.setSignLevel(SignLevelEnum.LT);
        } else {
            psr.setSignLevel(SignLevelEnum.B);
        }
        psr.setDigestAlgorithm(convertSignAlgorithmToPadesDigestAlgorithmEnum(fis.getSignAlgorithm()));

        psr.setCertId(certId);
        psr.setLocation(fis.getLocation());
        psr.setDateFormat("EEE, d MMM yyyy HH:mm:ss");
        psr.setTimeZone("Europe/Madrid");
        psr.setReason(fis.getReason());

        X509Certificate x509Cert = CertificateUtils
                .decodeCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(certificat.getCertificate())));

        psr.setSignerName(CertificateUtils.getCN(x509Cert));

        // TODO PENDENT DE FER LA POSICIÓ DE LA FIRMA VISIBLE
        /*
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
        */

        File pdfASignar = fis.getFileToSign();

        //org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient apiClient;
        //apiClient = new org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient();
        SignatureApiClient apiClient = new SignatureApiClient();

        //url = "http://localhost:80";

        apiClient.setBasePath(getPropertyRequired(NEBULA_BASE_PROPERTIES + "url"));
        HttpBearerAuth auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");
        auth.setBearerToken(getApiToken(nif));

        // {{url}}/signature/pades/v1/sign
        PadEsSignatureApi apiSign = new PadEsSignatureApi(apiClient);

        String dataStr = toJson(apiClient.getJSON(), psr);
        if (isDebug()) {
            log.info(dataStr);
        }

        PadesSignatureOperationResponse signedResponse = apiSign.signDocumentV1(pdfASignar, dataStr, null);

        CodeEnum codi = signedResponse.getCode();

        if (codi.equals(CodeEnum.OK)) {

            File signedData = File.createTempFile("nebula_", "_padessignedfile");

            Files.write(signedData.toPath(), Base64.getDecoder().decode(signedResponse.getContent().getSignedFile()));

            fis.getStatusSignature().setSignedData(signedData);

            if (isDebug()) {
                log.info(signedResponse.getContent().getCnCertificate());
                log.info("Firma PAdES guardada a " + signedData.getAbsolutePath());
                log.info("FINAL");
            }
        } else {
            throw new Exception("Error realitzant la firma PAdES: Code " + signedResponse.getCode() + ", Msg: "
                    + signedResponse.getMessage());
        }
    }

    protected void doSignatureCades(FileInfoSignature fis, SignaturesSetWeb signaturesSet,
            GetMyCertificates200ResponseCertificatesListInner certificat, String nif) throws Exception, ApiException {

        String certId = certificat.getSigningId();

        // org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.services.ApiClient apiClient;
        // apiClient = new org.fundaciobit.vintegris.nebula.api.client.xadessignatures.v1.services.ApiClient();
        SignatureApiClient apiClient = new SignatureApiClient();

        //url = "http://localhost:80";

        apiClient.setBasePath(getPropertyRequired(NEBULA_BASE_PROPERTIES + "url"));
        HttpBearerAuth auth;
        auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");

        auth.setBearerToken(getApiToken(nif));

        CadEsSignatureApi apiSign = new CadEsSignatureApi(apiClient);
        try {

            CadesSignatureRequest cades = new CadesSignatureRequest();
            if (fis.isUserRequiresTimeStamp()) {
                cades.setSignLevel(CadesSignatureRequest.SignLevelEnum.LT);
            } else {
                cades.setSignLevel(CadesSignatureRequest.SignLevelEnum.B);
            }
            cades.setSignPackage(convertModeToCadesSignPackageEnum(fis.getSignMode()));
            //   org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.SignPackageEnum.ENVELOPING);
            cades.setDigestAlgorithm(convertCadesAlgorithmToCadesDigestAlgorithmEnum(fis.getSignAlgorithm()));
            // org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA256);

            PolicyInfoSignature policyInfo = fis.getPolicyInfoSignature();
            if (policyInfo != null) {
                cades.setPolicyId(policyInfo.getPolicyIdentifier());
                cades.setPolicyHash(policyInfo.getPolicyIdentifierHash());
                cades.setPolicyDigestAlgorithm(convertPolicyHashAlgorithmToCadesDigestAlgorithmEnum(
                        policyInfo.getPolicyIdentifierHashAlgorithm()));
            }

            cades.setCertId(Integer.parseInt(certId));

            String dataStr = toJson(apiClient.getJSON(), cades);
            if (isDebug()) {
                log.info("CAdES Signature Request: " + dataStr);
            }

            CadesSignatureResponse signedResponse = apiSign.cadesSign(dataStr, fis.getFileToSign());

            if (isDebug()) {
                log.info("CODE: " + signedResponse.getResponseCode());
                log.info("MSGE: " + signedResponse.getResponseMessage());
            }

            if ("0000".equals(signedResponse.getResponseCode())) {

                File signedData = File.createTempFile("nebula_", "_cadessignedfile");

                Files.write(signedData.toPath(), Base64.getDecoder().decode(signedResponse.getSignedFile()));

                fis.getStatusSignature().setSignedData(signedData);

                if (isDebug()) {

                    log.info("Firma CAdES guardada a " + signedData.getAbsolutePath());
                    log.info("FINAL");
                }
            } else {
                throw new Exception("Error realitzant la firma CAdES: Code " + signedResponse.getResponseCode()
                        + ", Msg: " + signedResponse.getResponseMessage());
            }

        } catch (ApiException ae) {

            log.error("Code: " + ae.getCode());
            log.error("Message: " + ae.getMessage());
            log.error("Body: " + ae.getResponseBody());

            throw new Exception("Error d'API realitzant la firma CAdES: " + ae.getMessage() + " (Code: " + ae.getCode()
                    + ", Body: " + ae.getResponseBody() + ")", ae);

        }

    }

    protected org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.PolicyDigestAlgorithmEnum convertPolicyHashAlgorithmToCadesDigestAlgorithmEnum(
            String policyAlgorithm) throws Exception {

        switch (policyAlgorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.PolicyDigestAlgorithmEnum.SHA1;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.PolicyDigestAlgorithmEnum.SHA256;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.PolicyDigestAlgorithmEnum.SHA384;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.PolicyDigestAlgorithmEnum.SHA512;
            default:
                throw new Exception("El PolicyIdentifierHashAlgorithm " + policyAlgorithm
                        + "de política de signatura per CAdES no està suportat");
        }
    }

    protected org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum convertPolicyHashAlgorithmToXadesDigestAlgorithmEnum(
            String policyAlgorithm) throws Exception {

        switch (policyAlgorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA1;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA256;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA384;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.XadesSignatureRequest.DigestAlgorithmEnum.SHA512;
            default:
                throw new Exception("El PolicyIdentifierHashAlgorithm " + policyAlgorithm
                        + "de política de signatura per XAdES no està suportat");
        }
    }

    protected org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum convertCadesAlgorithmToCadesDigestAlgorithmEnum(
            String algorithm) {

        switch (algorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA1;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA256;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA384;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA512;
            default:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.DigestAlgorithmEnum.SHA256;
        }
    }

    protected org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.SignPackageEnum convertModeToCadesSignPackageEnum(
            int signMode) throws Exception {
        switch (signMode) {
            case FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.SignPackageEnum.ENVELOPING;
            case FileInfoSignature.SIGN_MODE_DETACHED:
                return org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.model.CadesSignatureRequest.SignPackageEnum.DETACHED;
            default:
                throw new Exception("Mode de firma CAdES no suportat: " + signMode);
        }
    }

    protected DigestAlgorithmEnum convertSignAlgorithmToPadesDigestAlgorithmEnum(String signAlgorithm) {
        switch (signAlgorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                return DigestAlgorithmEnum.SHA1;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                return DigestAlgorithmEnum.SHA256;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                return DigestAlgorithmEnum.SHA384;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                return DigestAlgorithmEnum.SHA512;
            default:
                return DigestAlgorithmEnum.SHA256;
        }
    }

    protected static String toJson(ContextResolver<ObjectMapper> context, Object obj) throws JsonProcessingException {
        ObjectMapper mapper = context.getContext(obj.getClass());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);

        String dataStr = mapper.writer().writeValueAsString(obj);
        return dataStr;
    }

    @Override
    public boolean administrationIdCanBeValidated() {
        // Només si ignoreNifValidation és true llavors no validam NIF
        String ignoreNifValidation = getProperty(NEBULA_BASE_PROPERTIES + "ignoreNifValidation");
        if (ignoreNifValidation != null && "true".equalsIgnoreCase(ignoreNifValidation)) {
            return false;
        }
        return true;
    }

    /*
    @Override
    public void requestGET(String absolutePluginRequestPath, String relativePluginRequestPath, String query,
            SignaturesSetWeb signaturesSet, int signatureIndex, HttpServletRequest request,
            HttpServletResponse response, Locale locale) {
    
        commonRequestGETPOST(absolutePluginRequestPath, relativePluginRequestPath, query, signaturesSet, signatureIndex,
                request, response, locale, true);
    }
    
    @Override
    public void requestPOST(String absolutePluginRequestPath, String relativePluginRequestPath, String query,
            SignaturesSetWeb signaturesSet, int signatureIndex, HttpServletRequest request,
            HttpServletResponse response, Locale locale) {
        commonRequestGETPOST(absolutePluginRequestPath, relativePluginRequestPath, query, signaturesSet, signatureIndex,
                request, response, locale, false);
    }
    
    private void commonRequestGETPOST(String absolutePluginRequestPath, String relativePluginRequestPath, String query,
            SignaturesSetWeb signaturesSet, int signatureIndex, HttpServletRequest request,
            HttpServletResponse response, Locale locale, boolean isGet) {
    
        if (query.startsWith(INICI_FIRMA)) {
            iniciFirma(absolutePluginRequestPath, relativePluginRequestPath, request, response, signaturesSet, locale);
        } else if (query.startsWith(EXECUCIO_FIRMA)) {
            execucioFirma(absolutePluginRequestPath, relativePluginRequestPath, request, response, signaturesSet,
                    locale);
        } else {
            if (isGet) {
                super.requestGET(absolutePluginRequestPath, relativePluginRequestPath, query, signaturesSet,
                        signatureIndex, request, response, locale);
            } else {
                super.requestPOST(absolutePluginRequestPath, relativePluginRequestPath, query, signaturesSet,
                        signatureIndex, request, response, locale);
            }
        }
    }
    
    private static final String INICI_FIRMA = "inicifirma";
    
    private void iniciFirma(String absolutePluginRequestPath, String relativePluginRequestPath,
            HttpServletRequest request, HttpServletResponse response, SignaturesSetWeb signaturesSet, Locale locale) {
        try {
            if (isDebug()) {
                log.info("iniciFirma");
            }
    
            String token = getToken(absolutePluginRequestPath);
    
            int pos = relativePluginRequestPath.lastIndexOf("-1");
            String baseSignaturesSet = relativePluginRequestPath.substring(0, pos - 1);
            String callbackhost = getHostAndContextPath(absolutePluginRequestPath, relativePluginRequestPath);
            String baseTimeStampURL = callbackhost + baseSignaturesSet;
    
            SignatureAdapter adapter = new SignatureAdapter(signaturesSet);
            List<SignatureConfiguration> configurationList = adapter.getSignatureConfigurationList(baseTimeStampURL);
    
            SignatureRequest signatureRequest = new SignatureRequest();
            signatureRequest.setSignatureConfigurations(configurationList);
            signatureRequest.setUserCode(signaturesSet.getCommonInfoSignature().getAdministrationID());
    
            FortressApi api = getApi();
    
            SignatureRequestResponse signatureRequestResponse = api.signatureRequest(token, signatureRequest);
            executionCodes.put(signaturesSet.getSignaturesSetID(), signatureRequestResponse.getExeCode());
            signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_IN_PROGRESS);
    
            String callbackUrl = absolutePluginRequestPath + "/" + EXECUCIO_FIRMA;
            String authCode = signatureRequestResponse.getAuthCode();
            if (isDebug()) {
                log.info("authcode: " + authCode);
                log.info("callbackUrl: " + callbackUrl);
            }
            String signatureAuthorizationUri = api.getSignatureAuthorizationUri(authCode, callbackUrl);
    
            sendRedirect(response, signatureAuthorizationUri);
    
        } catch (Exception e) {
            String msg = "Error preparant inici de firma ViaFirma-Fortress: " + e.getMessage();
            finishWithError(response, signaturesSet, msg, e);
        }
    }
    
    private static final String EXECUCIO_FIRMA = "execuciofirma";
    
    private void execucioFirma(String absolutePluginRequestPath, String relativePluginRequestPath,
            HttpServletRequest request, HttpServletResponse response, SignaturesSetWeb signaturesSet, Locale locale) {
    
        try {
            if (isDebug()) {
                log.info("execucioFirma");
            }
    
            checkErrors(request);
    
            String exeCode = executionCodes.get(signaturesSet.getSignaturesSetID());
            if (exeCode == null) {
                throw new Exception("No s'ha trobat exeCode");
            }
    
            String token = getToken(absolutePluginRequestPath);
    
            List<SignatureResponse> signatureResponses = getApi().executeSignature(token, exeCode);
    
            SignatureAdapter adapter = new SignatureAdapter(signaturesSet);
            adapter.updateSignatureStatus(signatureResponses);
    
            sendRedirect(response, signaturesSet.getUrlFinal());
    
        } catch (CancelledException e) {
            cancel(request, response, signaturesSet);
        } catch (Exception e) {
            String msg = "Error executant firma ViaFirma-Fortress: " + e.getMessage();
            finishWithError(response, signaturesSet, msg, e);
        }
    }
    
    
    
    protected void checkErrors(HttpServletRequest request) throws Exception {
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            if (error.equals("access_denied")) {
                throw new CancelledException();
            } else {
                throw new Exception(error);
            }
        }
    }
    */
    @Override
    public String getResourceBundleName() {
        return "nebula";
    }

    @Override
    protected String getSimpleName() {
        return "NebulaPlugin";
    }

    @Override
    public int getActiveTransactions() throws Exception {
        return internalGetActiveTransactions();
    }

    @Override
    public void resetAndClean(HttpServletRequest request) {
        internalResetAndClean(request);
    }

    @Override
    public List<String> getSupportedBarCodeTypes() {
        // Aquests Plugins No suporten estampació de CSV per si mateixos
        return null;
    }

    /**
     * @return true, indica que el plugin internament ofereix un generador de imatges de
     * la Firma Visible PDF.
     */
    @Override
    public boolean providesRubricGenerator() {
        // TODO pendent d'implementar
        return false;
    }

    @Override
    public boolean acceptExternalSecureVerificationCodeStamper() {
        return false;
    }

    @Override
    public boolean providesSecureVerificationCodeStamper() {
        return false;
    }

    /**
     * @return true, indica que el plugin internament ofereix un generador de segellat de temps.
     */
    @Override
    public boolean providesTimeStampGenerator(String signType) {
        return true;
    }

    @Override
    public boolean acceptExternalTimeStampGenerator(String signType) {
        return false;
    }

    @Override
    public boolean acceptExternalRubricGenerator() {
        return false;
    }

    @Override
    protected boolean isSuportXadesT() {
        return true;
    }

    @Override
    public String[] getSupportedSignatureTypes() {
        return new String[] { FileInfoSignature.SIGN_TYPE_PADES, FileInfoSignature.SIGN_TYPE_CADES,
                FileInfoSignature.SIGN_TYPE_XADES };
    }

    @Override
    public String[] getSupportedSignatureAlgorithms(String signType) {
        if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType) || FileInfoSignature.SIGN_TYPE_XADES.equals(signType)
                || FileInfoSignature.SIGN_TYPE_CADES.equals(signType)) {
            return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA1, FileInfoSignature.SIGN_ALGORITHM_SHA256,
                    FileInfoSignature.SIGN_ALGORITHM_SHA384, FileInfoSignature.SIGN_ALGORITHM_SHA512 };
        }
        return null;
    }

    @Override
    public int[] getSupportedSignatureModes(String signType) {

        if (signType == null || signType.trim().length() == 0) {
            log.error("S'ha cridat a getSupportedSignatureModes amb un tipus de firma null o buit");
            return new int[0];
        }

        switch (signType) {
            case FileInfoSignature.SIGN_TYPE_PADES:
                return new int[] { FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED };

            case FileInfoSignature.SIGN_TYPE_CADES:
                return new int[] { FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING,
                        FileInfoSignature.SIGN_MODE_DETACHED };

            case FileInfoSignature.SIGN_TYPE_XADES:
                return new int[] { FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING,
                        FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED, FileInfoSignature.SIGN_MODE_DETACHED };

            default:
                log.error(
                        "S'ha cridat a getSupportedSignatureModes amb un amb un tipus de firma desconegut o no suportat: ]"
                                + signType + "[");
                return new int[0];
        }
    }

    // --------------------------------------

    public List<GetMyCertificates200ResponseCertificatesListInner> getUserCertificates(String username)
            throws org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException, Exception {

        if (isDebug()) {
            log.info(" -------  Descarregant Certificats de l'usuari -------");
        }
        DigitalCertificateApi apiCert = getDigitalCertificateApi(username);

        // @param enableFilter ENABLED/DISABLED (optional)
        final String enableFilter = null; //"ENABLED";
        // @param expireFilter BOTH/EXPIRED/NOT_EXPIRED (optional)
        final String expireFilter = null; //"NOT_EXPIRED";

        GetMyCertificates200Response response = apiCert.getMyCertificates(null, null, enableFilter, expireFilter, null,
                null, null);

        //System.out.println(response);

        Integer total = response.getTotalCertificates();
        if (isDebug()) {
            log.info("Total: " + total);
        }

        if (total == null || total == 0) {
            return null;
        }

        return response.getCertificatesList();
    }

    protected DigitalCertificateApi getDigitalCertificateApi(String username) throws Exception {

        String url = getPropertyRequired(NEBULA_BASE_PROPERTIES + "url");
        String token = getApiToken(username);

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
        if (isDebug()) {
            log.info(" Token Certificate Api: " + certToken);
        }

        DigitalCertificateApi apiCert = new DigitalCertificateApi(apiClient);
        return apiCert;
    }

    protected String getApiToken(String username) throws Exception {

        String url_auth = getPropertyRequired(NEBULA_BASE_PROPERTIES + "url_auth");

        // PORTAFIB CONFIGURACIO
        String applicationName = getPropertyRequired(NEBULA_BASE_PROPERTIES + "applicationName");
        String tenantId = getPropertyRequired(NEBULA_BASE_PROPERTIES + "tenantId");
        String appId = getPropertyRequired(NEBULA_BASE_PROPERTIES + "appId");
        String accessKey = getPropertyRequired(NEBULA_BASE_PROPERTIES + "accessKey");

        String usernameB64 = Base64.getEncoder().encodeToString(username.getBytes());
        String trusted_app_token = generateTrustedAppToken(applicationName, tenantId, appId, accessKey);

        if (isDebug()) {
            log.info("base64[" + username + "] = " + usernameB64);
            log.info("------ trusted_app_token (authorization en PostMan) ---- ");
            log.info(trusted_app_token);
        }

        String token = getUserToken(trusted_app_token, url_auth, applicationName, tenantId, appId, accessKey,
                usernameB64);

        return token;
    }

    protected String getUserToken(String trusted_app_token, String url_auth, String applicationName, String tenantId,
            String appId, String accessKey, String usernameB64)
            throws Exception, org.fundaciobit.vintegris.nebula.api.client.trustedapplications.v1.services.ApiException {
        String token;

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(url_auth);
        AuthenticationApi api = new AuthenticationApi(apiClient);

        final String authorization = trusted_app_token;
        NebulaResponseAuthorizeResponseViewModel authResponse = api.authorize(authorization);

        String applicationToken = authResponse.getContent().getAuthorization();
        //String application = "Bearer " + token;
        final String application = applicationToken;

        if (isDebug()) {
            log.info(" ---------- applicationToken -----------");
            log.info(applicationToken);
        }

        // First Call

        NebulaResponseLoginResponseViewModel loginFirstResponse = api.appLoginFirst(application, usernameB64);
        String token1 = loginFirstResponse.getContent().getToken();
        if (isDebug()) {
            log.info(" ---------- token1 " + loginFirstResponse.getCode() + "-----------");
            log.info(token1);
        }

        // Second call
        NebulaResponseLoginResponseViewModel secondFirstResponse = api.appLoginSecond(application, token1);
        String token2 = secondFirstResponse.getContent().getToken();
        if (isDebug()) {
            log.info(" ---------- token2: " + secondFirstResponse.getCode() + " -----------");
            log.info(token2);
        }
        token = token2;

        return token;
    }

    protected String generateTrustedAppToken(String applicationName, String tenantId, String appId, String accessKey)
            throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
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

    protected boolean isDebug() {
        return Boolean.parseBoolean(getProperty(NEBULA_BASE_PROPERTIES + "debug", "false"));
    }

}

package org.fundaciobit.pluginsib.signatureweb.nebula;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.fundaciobit.pluginsib.core.v3.utils.CertificateUtils;
import org.fundaciobit.pluginsib.signature.api.CommonInfoSignature;
import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signature.api.PolicyInfoSignature;
import org.fundaciobit.pluginsib.signature.api.PropertyInfo;
import org.fundaciobit.pluginsib.signature.api.StatusSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignaturesSet;
import org.fundaciobit.pluginsib.signatureserver.miniappletutils.MiniAppletSignInfo;
import org.fundaciobit.pluginsib.signatureserver.miniappletutils.MiniAppletUtils;
import org.fundaciobit.pluginsib.signatureweb.api.AbstractSignatureWebPlugin;
import org.fundaciobit.pluginsib.signatureweb.api.SignaturesSetWeb;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetCertPolicies200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200Response;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.Policy;
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
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.ContextResolver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static String CERTIFICATES_OF_USER_SESSIONKEY = "CERTIFICATES_OF_USER_SESSIONKEY";

    private static final String FIELD_PIN = "pin";

    public static final String IGNORE_CERTIFICATE_FILTER = NEBULA_BASE_PROPERTIES + "ignore_certificate_filter";

    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

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

            NebulaCacheInfo info = getNebulaCache(nif);

            // TODO ficar dins sessió el certificat !!!!!
            Map<String, GetMyCertificates200ResponseCertificatesListInner> certs = info.getCertificatesByCertID();

            if (certs == null || certs.size() == 0) {
                // XYZ ZZZ TRA
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

        String pin;
        GetMyCertificates200ResponseCertificatesListInner selectedCertificate;
        try {

            NebulaCacheInfo info = getNebulaCache(signaturesSet.getCommonInfoSignature().getAdministrationID());

            Map<String, GetMyCertificates200ResponseCertificatesListInner> certs = info.getCertificatesByCertID();
            if (certs == null || certs.size() == 0) {

                // TODO XYZ ZZZ TRA 
                throw new Exception("La llista de certificats de l'usuari està buida."
                        + "No s'ha executat el mètode filter() o s'ha fet neteja de la informació de cache."
                        + " Tornau-ho a intentar i si el problema persisteix contacti amb suport.");

            }

            if (certs.size() != 1) {

                // TODO FALTA  Revisar si tots estan habilitats per no demanar selecció de certificat

                super.addSignaturesSet(signaturesSet);

                // Redireccionam a la pàgina de selecció de certificat i pin
                return relativePluginRequestPath + "/" + SELECT_CERTIFICATE_GET_PAGE;
            } else {

                // Hi ha un sol certificat, el seleccionam directament
                selectedCertificate = new ArrayList<GetMyCertificates200ResponseCertificatesListInner>(certs.values())
                        .get(0);
            }

            Map<String, Policy> politiques = info.getPoliciesByCertID();

            if (politiques == null || politiques.size() == 0) {
                // TODO XYZ ZZZ TRA 
                throw new Exception("No s'ha pogut recuperar les polítiques associades als certificats de l'usuari."
                        + " Tornau-ho a intentar i si el problema persisteix contacti amb suport.");
            }

            Policy policyCertificat = politiques.get(selectedCertificate.getCertificateId());
            if (policyCertificat == null) {
                // TODO XYZ ZZZ TRA 
                throw new Exception("No s'ha pogut recuperar la política associada al ID de certificat "
                        + selectedCertificate.getCertificateId() + "."
                        + " Tornau-ho a intentar i si el problema persisteix contacti amb suport.");
            }

            if (isPinRequired(policyCertificat)) {

                super.addSignaturesSet(signaturesSet);

                // Redireccionam a la pàgina de selecció de certificat i pin
                return relativePluginRequestPath + "/" + SELECT_CERTIFICATE_GET_PAGE;

            } else {
                pin = null;
            }

        } catch (Throwable th) {

            String errorMsg = "Error global preparant per signar: " + th.getMessage();

            StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
            sss.setErrorMsg(errorMsg);
            sss.setErrorException(th);
            sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);

            return signaturesSet.getUrlFinal();

        }

        return signDocumentsDirect(request, absolutePluginRequestPath, relativePluginRequestPath, signaturesSet,
                selectedCertificate, pin);

    }

    protected boolean isPinRequired(Policy policyCertificat) {
        return "1".equalsIgnoreCase(policyCertificat.getUsePin());
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
            String relativePluginRequestPath, SignaturesSetWeb signaturesSet,
            GetMyCertificates200ResponseCertificatesListInner selectedCertificate, String pin) {

        try {

            signaturesSet.getStatusSignaturesSet().setStatus(StatusSignaturesSet.STATUS_IN_PROGRESS);

            CommonInfoSignature commonInfoSignature = signaturesSet.getCommonInfoSignature();

            String nif = commonInfoSignature.getAdministrationID();

            Locale locale = new Locale(signaturesSet.getCommonInfoSignature().getLanguageUI());

            for (FileInfoSignature fis : signaturesSet.getFileInfoSignatureArray()) {

                try {
                    if (fis.getSignType().equals(FileInfoSignature.SIGN_TYPE_PADES)) {

                        if (fis.isUserRequiresTimeStamp()) {
                            doSignaturePades(fis, selectedCertificate, nif, pin);
                        } else {
                            doSignatureTriphasePades(commonInfoSignature, fis, selectedCertificate, pin);
                        }
                    } else if (fis.getSignType().equals(FileInfoSignature.SIGN_TYPE_CADES)) {

                        doSignatureCades(fis, selectedCertificate, nif);

                    } else if (fis.getSignType().equals(FileInfoSignature.SIGN_TYPE_XADES)) {

                        doSignatureXades(fis, selectedCertificate, nif);

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

                        errorMsg = "Error d'API realitzant la firma: " + ae.getMessage() + " (Code: " + ae.getCode()
                                + ", Body: " + ae.getResponseBody() + ")";
                    } else {

                        log.error(" CLass ERROR: " + th.getClass().getName());

                        String msg = th.getMessage();

                        if (msg.contains("The PIN could has caused problems in the sign init process:")) {

                            request.getSession().setAttribute("nebulaerror", getTraduccio("pin.error", locale));

                            return relativePluginRequestPath + "/" + SELECT_CERTIFICATE_GET_PAGE;
                        }

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

    protected void doSignatureXades(FileInfoSignature fis, GetMyCertificates200ResponseCertificatesListInner certificat,
            String nif) throws Exception {

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
                xades.setSignLevel(XadesSignatureRequest.SignLevelEnum.LTA);
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

    protected void doSignaturePades(FileInfoSignature fis, GetMyCertificates200ResponseCertificatesListInner certificat,
            String nif, String pin) throws Exception, ApiException {

        if (isDebug()) {
            log.info("---------- Realitzant Firma PAdES ... ----------");
        }

        String certId = certificat.getSigningId();
        /**
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

        psr.setCertPin(pin);

        if (fis.isUserRequiresTimeStamp()) {
            if (isDebug()) {
                log.info(
                        "L'usuari ha indicat que vol que la firma PAdES contingui TimeStamp, per tant es realitzarà una firma de nivell LTA");
            }
            psr.setSignLevel(SignLevelEnum.LTA);
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

        PadEsSignatureApi apiSign = getPadesSignatureApi(nif);

        String dataStr = toJson(apiSign.getApiClient().getJSON(), psr);
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

    protected void doSignatureTriphasePades(CommonInfoSignature commonInfo, FileInfoSignature fileInfo,
            GetMyCertificates200ResponseCertificatesListInner cert, String pin)
            throws ApiException, Exception, IOException, FileNotFoundException {

        DigitalCertificateApi api = getDigitalCertificateApi(commonInfo.getAdministrationID());

        X509Certificate x509Cert;
        x509Cert = CertificateUtils
                .decodeCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(cert.getCertificate())));

        final String fileInfoSignAlgorithm = fileInfo.getSignAlgorithm();
        
        String algorithmMiniapplet = MiniAppletUtils.convertAlgorithm(fileInfo);

        byte[] dataToSign = Files.readAllBytes(fileInfo.getFileToSign().toPath());

        Properties params;
        {

            String timeStampUrl = null;

            MiniAppletSignInfo info;
            info = MiniAppletUtils.convertLocalSignature(commonInfo, fileInfo, timeStampUrl, x509Cert);

            params = info.getProperties();

        }

        NebulaTriphaseSigner nTriPhase = new NebulaTriphaseSigner(api, params, cert, pin, fileInfoSignAlgorithm, isDebug());

        byte[] hashDocumento = nTriPhase.step1_PreSign(dataToSign, algorithmMiniapplet,
                new java.security.cert.Certificate[] { x509Cert }, params);

        byte[] signedHash = nTriPhase.step2_signHash(algorithmMiniapplet, hashDocumento);

        byte[] signedData = nTriPhase.step3_PostSign(algorithmMiniapplet, new java.security.cert.Certificate[] { x509Cert }, params,
                signedHash);

        File signedFileData = File.createTempFile("nebula_", "_padestriphasesignedfile");

        FileOutputStream fos = new FileOutputStream(signedFileData);
        fos.write(signedData);
        fos.flush();
        fos.close();

        StatusSignature statusSignature = fileInfo.getStatusSignature();
        statusSignature.setSignedData(signedFileData);
        statusSignature.setStatus(StatusSignature.STATUS_FINAL_OK);
        statusSignature.setErrorMsg(null);

    }

    public PadEsSignatureApi getPadesSignatureApi(String nif) throws Exception {
        PadEsSignatureApi apiSign;
        //org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient apiClient;
        //apiClient = new org.fundaciobit.vintegris.nebula.api.client.digitalsignature.v1.services.ApiClient();
        SignatureApiClient apiClient = new SignatureApiClient();

        //url = "http://localhost:80";

        apiClient.setBasePath(getPropertyRequired(NEBULA_BASE_PROPERTIES + "url"));
        HttpBearerAuth auth = (HttpBearerAuth) apiClient.getAuthentication("Authorization");
        auth.setBearerToken(getApiToken(nif));

        apiSign = new PadEsSignatureApi(apiClient);
        return apiSign;
    }

    protected void doSignatureCades(FileInfoSignature fis, GetMyCertificates200ResponseCertificatesListInner certificat,
            String nif) throws Exception, ApiException {

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

        final SignIDAndIndex sai = new SignIDAndIndex(signaturesSet, signatureIndex);
        final String lang = locale.getLanguage();

        if (query.startsWith(SELECT_CERTIFICATE_GET_PAGE)) {
            PrintWriter out = generateHeader(request, response, absolutePluginRequestPath, relativePluginRequestPath,
                    lang, sai, signaturesSet);
            selectCertificateGET(request, response, relativePluginRequestPath, query, signaturesSet, out, locale);

            generateFooter(out, sai, signaturesSet);
        } else if (query.startsWith(SELECT_CERTIFICATE_POST_PAGE)) {

            selectCertificatePOST(absolutePluginRequestPath, relativePluginRequestPath, request, response,
                    signaturesSet, locale);

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

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ------------------ FIRMAR -------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------

    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------
    // ------------------ S E L E C T     C E R T I F I C A T E -------------------
    // ----------------------------------------------------------------------------
    // ----------------------------------------------------------------------------

    private static final String SELECT_CERTIFICATE_GET_PAGE = "selectCertificateGet";

    private void selectCertificateGET(HttpServletRequest request, HttpServletResponse response,
            String relativePluginRequestPath, String relativePath, SignaturesSetWeb signaturesSet, PrintWriter out,
            Locale locale) {

        out.println("<h3>" + getTraduccio("selectcertificat.titol", locale) + "</h3><br/>");

        NebulaCacheInfo info;

        try {

            info = getNebulaCache(signaturesSet.getCommonInfoSignature().getAdministrationID());

        } catch (Throwable th) {

            String errorMsg = th.getMessage();

            StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
            sss.setErrorMsg(errorMsg);
            sss.setErrorException(th);
            sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);

            log.error(errorMsg, th);

            sendRedirect(response, signaturesSet.getUrlFinal());

            return;

        }

        String error = (String) request.getSession().getAttribute("nebulaerror");

        if (error != null) {
            out.println("<div class=\"alert alert-danger\" role=\"alert\">" + error + "</div>");
            request.getSession().removeAttribute("nebulaerror");
        }

        out.println("<form action=\"" + relativePluginRequestPath + "/" + SELECT_CERTIFICATE_POST_PAGE
                + "\" method=\"post\" >");

        // Afegir camp hidden amb nom "cert" 
        out.println("<input type=\"hidden\" name=\"cert\" id=\"cert\" value=\"\" />");

        int certificatsDisponibles = 0;

        String filter = signaturesSet.getCommonInfoSignature().getFiltreCertificats();

        Map<String, GetMyCertificates200ResponseCertificatesListInner> certs = info.getCertificatesByCertID();

        Map<String, Policy> politiques = info.getPoliciesByCertID();

        for (Map.Entry<String, GetMyCertificates200ResponseCertificatesListInner> entry : certs.entrySet()) {
            String certID = entry.getKey();
            GetMyCertificates200ResponseCertificatesListInner cert = entry.getValue();

            Policy politica = politiques.get(certID);

            boolean passFilter;

            X509Certificate certX509 = null;
            try {

                certX509 = CertificateUtils
                        .decodeCertificate(new ByteArrayInputStream(Base64.getDecoder().decode(cert.getCertificate())));
            } catch (Exception e) {
                log.error("Error obteninr certX509 des de Certificat CER: " + e.getMessage(), e);

            }

            if ("true".equals(getProperty(IGNORE_CERTIFICATE_FILTER))) {
                passFilter = true;
            } else {

                try {

                    passFilter = MiniAppletUtils.matchFilter(certX509, filter);
                } catch (Exception e) {
                    log.error(" Error comprovant filtre Certificat: " + e.getMessage(), e);
                    passFilter = false;
                }
            }

            if (passFilter) {
                certificatsDisponibles++;
            } else {
                continue;
            }

            /*
            out.println("<table border=\"0\">");
            
            out.println("<td style=\"border: 1px solid gray; padding-top:1px;\">");
            
            out.println("<input type=\"radio\" name=\"cert\" id=\"optionsRadios_" + certID + "\" value=\""
                    + cert.getCertificateId() + "\" " + ((count == 0) ? "checked" : "") + " >");
            
            out.println("<label class=\"radio\">");
            */

            String nom = null;
            if (cert.getAlias() != null && !cert.getAlias().trim().isEmpty()) {
                nom = cert.getAlias();
            } else {
                if (certX509 != null) {

                    String subjectCN = CertificateUtils.getCN(certX509);
                    if (subjectCN != null && !subjectCN.trim().isEmpty()) {
                        nom = subjectCN;
                    } else {
                        nom = cert.getSubject();
                    }

                    String organitzacio = getOrganization(certX509);

                    if (organitzacio != null && !organitzacio.trim().isEmpty()) {
                        nom += " - " + organitzacio;
                    } else {

                        String[] empresa;
                        try {
                            empresa = CertificateUtils.getEmpresaNIFNom(certX509);
                            if (empresa != null) {
                                nom += " (" + empresa[1] + ")";
                            }
                        } catch (Exception e) {

                        }
                    }

                }

            }

            if (nom == null) {
                nom = cert.getSubject();
            }

            Long dataFinal = cert.getDateValidEnd();

            if (dataFinal != null) {

                final String to = DATE_FORMATTER.format(new Timestamp(dataFinal));

                nom = nom + " (" + MessageFormat.format(getTraduccio("valid", locale), to) + ")";
            }

            // Dibuixar div amb els cantons arrodonits i una mica de padding

            out.println(
                    "<div style=\"border: 2px solid gray; border-radius: 8px; padding: 10px; margin-bottom: 10px;\">");

            out.println("<p style=\"margin-bottom: 15px;\"><b>" + nom + "</b></p>");

            /*
            out.println("<small>");
            out.println("<ul>");
            out.println("<li>Subject: " + cert.getSubject() + "</li>");
            out.println("<li>Issuer: " + cert.getIssuer() + " </li>");
            
            // Afegir dates
            
            //log.info("\n\nData inici: " + cert.getDateValidStart()+"Data fi: " + cert.getDateValidEnd() + "\n\n");
            
            final String from = DATE_FORMATTER.format(new Timestamp(cert.getDateValidStart()));
            
            final String to = DATE_FORMATTER.format(new Timestamp(cert.getDateValidEnd()));
            
            out.println("<li>" + MessageFormat.format(getTraduccio("valid", locale), to) + "</li>");
            
            out.println("</ul>");
            
            out.println("</small>");
            
            
            out.println("</label>");
            
            out.println("</td>");
            
            out.println("</td>");
            
            out.println("<td style=\"border: 1px solid gray; padding-top:1px;\">");
            */

            boolean pinRequired = isPinRequired(politica);
            if (pinRequired) {

                out.println(getTraduccio("pin", locale) + ":");
                out.println("<input type=\"password\" style=\"display: none;\" />" + "<input type=\"password\" id=\""
                        + FIELD_PIN + "_" + cert.getCertificateId() + "\" name=\"" + FIELD_PIN + "_"
                        + cert.getCertificateId() + "\" value=\"\" />");
                // Boto bootstrap per posar al camp hidden cert el valor de "cert.getCertificateId()" i fer submit al formulari

            }

            out.println("<button type=\"button\" class=\"btn btn-primary\" onclick=\"signWithCertificate('"
                    + cert.getCertificateId() + "', " + pinRequired + ");\">" + getTraduccio("firmar", locale)
                    + "</button>");

            out.println("</div>");

        }

        if (certificatsDisponibles == 0) {
            String warn = getTraduccio("warn.notecertificats", locale);
            out.println("<table>");
            out.println("<tr>");
            out.println("<br/><div class=\"alert alert-error\">");
            out.println("<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>");
            out.println(" <strong>" + warn + "</strong>");
            out.println("</div>");
            out.println("</td></tr>");
            out.println("</table>");
        }

        out.println("<script type=\"text/javascript\">");

        // Mètode per realitzar accions al pitjar el boto de signar amb aquest certificat:
        //  (1) Assignar el valor del paràmetre certID al camp hidden "cert" del formulari
        //  (2) El segon parametre es un boolea que indica si el pin és requerit
        //  (3) Si el segon parametre és true llavors comprovar si el valor del pin està buit (input amb id FIELD_PIN + "_" + certID) i si està buit mostrar una alerta dient que el pin és obligatori i no submitar el formulari
        //  (4) Submitar el formulari
        out.println("function signWithCertificate(certID, pinRequired) {");
        out.println("  document.getElementById('cert').value = certID;");
        out.println("  if (pinRequired) {");
        out.println("    var pinValue = document.getElementById('" + FIELD_PIN + "_' + certID).value;");
        out.println("    if (!pinValue || pinValue.trim() === '') {");
        out.println("      alert('" + getTraduccio("pin.requiredalert", locale) + "');");
        out.println("      return;");
        out.println("    }");
        out.println("  }");
        out.println("  document.forms[0].submit();");
        out.println("}");

        out.println("</script>");

        out.println("<br/><br/>");

        out.println("<button class=\"btn btn-warn\" type=\"button\" id=\"btnCancel\"  onclick=\"location.href='"
                + relativePluginRequestPath + "/" + CANCEL_PAGE + "'\" >" + getTraduccio("cancel", locale)
                + "</button>");
        out.println("&nbsp;&nbsp;");
        /*
        if (certificatsDisponibles != 0) {
            int numFitxers = signaturesSet.getFileInfoSignatureArray().length;
            out.println(
                    "<button class=\"btn btn-primary\" type=\"submit\" onclick=\"document.body.style.cursor='wait'; document.getElementById('btnCancel').disabled=true; this.disabled=true; this.form.submit();\">"
                            + getTraduccio("firmardocument" + (numFitxers == 0 ? "" : ".plural"), locale)
                            + "</button>");
        
        }
        */
        out.println("</form>");
    }

    public String getOrganization(X509Certificate cert) {

        if (cert == null) {
            return null;
        }

        X500Principal principal = cert.getSubjectX500Principal();

        try {
            // Prova d'obtenir l'organització a través de la OID
            for (Rdn rdn : new LdapName(principal.getName(X500Principal.RFC2253)).getRdns()) {
                if ("O".equalsIgnoreCase(rdn.getType())) {
                    return rdn.getValue().toString();
                }
            }
        } catch (Exception e) {
            // Si hi ha qualsevol error, es retorna null
            log.warn("Error obtenint organització del certificat [" + cert.getSubjectDN() + "]: " + e.getMessage(), e);
        }
        return null;
    }

    private static final String SELECT_CERTIFICATE_POST_PAGE = "selectCertificatePost";

    private void selectCertificatePOST(String absolutePluginRequestPath, String relativePluginRequestPath,
            HttpServletRequest request, HttpServletResponse response, SignaturesSetWeb signaturesSet, Locale locale) {

        try {
            //final String signaturesSetID = signaturesSet.getSignaturesSetID();
            final CommonInfoSignature commonInfoSignature = signaturesSet.getCommonInfoSignature();

            String certID = request.getParameter("cert");

            if (certID == null || certID.trim().isEmpty()) {
                // TODO XYZ ZZZ TRA 
                throw new Exception("No s'ha elegit el Certificat amb el que vol signar."
                        + " Tornau-ho a intentar i si el problema persisteix contacti amb suport.");
            }

            String pin = request.getParameter(FIELD_PIN + "_" + certID);

            String nif = commonInfoSignature.getAdministrationID();
            NebulaCacheInfo info = getNebulaCache(nif);

            Map<String, GetMyCertificates200ResponseCertificatesListInner> certs = info.getCertificatesByCertID();
            GetMyCertificates200ResponseCertificatesListInner selectedCertificate = certs.get(certID);

            if (selectedCertificate == null) {
                throw new Exception("No s'ha trobat el certificat amb ID " + certID
                        + " dins de la llista de certificats de l'usuari " + nif);
            }

            String url = signDocumentsDirect(request, absolutePluginRequestPath, relativePluginRequestPath,
                    signaturesSet, selectedCertificate, pin);

            sendRedirect(response, url);

        } catch (Throwable th) {

            String errorMsg = th.getMessage();

            StatusSignaturesSet sss = signaturesSet.getStatusSignaturesSet();
            sss.setErrorMsg(errorMsg);
            sss.setErrorException(th);
            sss.setStatus(StatusSignaturesSet.STATUS_FINAL_ERROR);

            sendRedirect(response, signaturesSet.getUrlFinal());

        }

    }

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
        resetNebulaCacheInfo();
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

        if (isOnlyHashSignatures()) {
            // Si el plugin només suporta signatures de hash, llavors no s'ofereix generador de segellat de temps
            return false;
        } else {
            return true;
        }
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

        if (isOnlyHashSignatures()) {
            // Si el plugin només suporta signatures de hash, llavors només s'ofereixen signatures de tipus PAdES
            return new String[] { FileInfoSignature.SIGN_TYPE_PADES };
        } else {

            return new String[] { FileInfoSignature.SIGN_TYPE_PADES, FileInfoSignature.SIGN_TYPE_CADES,
                    FileInfoSignature.SIGN_TYPE_XADES };
        }
    }

    @Override
    public String[] getSupportedSignatureAlgorithms(String signType) {

        if (signType == null || signType.trim().length() == 0) {
            log.error("S'ha cridat a getSupportedSignatureAlgorithms amb un tipus de firma null o buit");
            return null;
        }

        if (isOnlyHashSignatures()) {
            // Si el plugin només suporta signatures de hash, llavors només s'ofereixen algoritmes de hash per signatures PAdES
            if (signType.equals(FileInfoSignature.SIGN_TYPE_PADES)) {
                return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA256, FileInfoSignature.SIGN_ALGORITHM_SHA384,
                        FileInfoSignature.SIGN_ALGORITHM_SHA512 };
            } else {
                // Per a altres tipus de firma (CAdES, XAdES) no s'ofereixen algoritmes si només es suporten signatures de hash
                return null;
            }
        } else if (FileInfoSignature.SIGN_TYPE_PADES.equals(signType)
                || FileInfoSignature.SIGN_TYPE_XADES.equals(signType)
                || FileInfoSignature.SIGN_TYPE_CADES.equals(signType)) {
            return new String[] { FileInfoSignature.SIGN_ALGORITHM_SHA256, FileInfoSignature.SIGN_ALGORITHM_SHA384,
                    FileInfoSignature.SIGN_ALGORITHM_SHA512 };
        } else {
            return null;
        }
    }

    @Override
    public int[] getSupportedSignatureModes(String signType) {

        if (signType == null || signType.trim().length() == 0) {
            log.error("S'ha cridat a getSupportedSignatureModes amb un tipus de firma null o buit");
            return new int[0];
        }

        if (isOnlyHashSignatures()) {
            // Si el plugin només suporta signatures de hash, llavors no s'ofereixen modes de signatura
            if (signType == FileInfoSignature.SIGN_TYPE_PADES) {
                return new int[] { FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED };
            } else {
                // Per a altres tipus de firma (CAdES, XAdES) no s'ofereixen modes de signatura si només es suporten signatures de hash
                return new int[0];
            }
        } else {

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
    }

    public DigitalCertificateApi getDigitalCertificateApi(String username) throws Exception {

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

    // Mètode para codificar en Base64 URL Safe
    private static String base64UrlEncode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }

    // Mètode per calcular SHA256
    private static byte[] sha256(String data) throws NoSuchAlgorithmException {
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes(StandardCharsets.UTF_8));
    }

    // Mètode per calcular HMAC-SHA256
    private static byte[] hmacSha256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    protected boolean isDebug() {
        return Boolean.parseBoolean(getProperty(NEBULA_BASE_PROPERTIES + "debug", "false"));
    }

    protected boolean isOnlyHashSignatures() {
        return Boolean.parseBoolean(getProperty(NEBULA_BASE_PROPERTIES + "onlyhashsignatures", "false"));
    }

    // ----------------------------------------------------
    // ----------------------------------------------------
    // ------------------ CACHE -------------------
    // ----------------------------------------------------

    public static Map<String, NebulaCacheInfo> cache = new HashMap<String, NebulaCacheInfo>();

    public static boolean testing = false;

    // Mètode per afegir nova cache
    private NebulaCacheInfo getNebulaCache(String nif) throws Exception {

        NebulaCacheInfo nci = cache.get(nif);

        if (nci == null || nci.isExpired()) {
            nci = initCertificatesAndPolicies(new NebulaCacheInfo(nif));
            cache.put(nif, nci);
        } else {
            nci.sessionReset();
        }

        return nci;

    }

    private NebulaCacheInfo initCertificatesAndPolicies(NebulaCacheInfo cacheInfo) throws Exception {

        String nif = cacheInfo.getNif();

        {
            List<GetMyCertificates200ResponseCertificatesListInner> certs = getCertificatesOfUser(nif);

            Map<String, GetMyCertificates200ResponseCertificatesListInner> certificatesByCertID = new HashMap<>();

            for (GetMyCertificates200ResponseCertificatesListInner cert : certs) {
                certificatesByCertID.put(cert.getCertificateId(), cert);
            }

            cacheInfo.setCertificatesByCertID(certificatesByCertID);

        }

        {

            DigitalCertificateApi apiCert = getDigitalCertificateApi(nif);

            Map<String, Policy> policiesByCertID = new HashMap<String, Policy>();

            GetCertPolicies200Response allPolicies = apiCert.getCertPolicies(null, null, null);

            for (Policy politica : allPolicies.getPolicyList()) {

                policiesByCertID.put(politica.getIdCert(), politica);

            }

            cacheInfo.setPoliciesByCertID(policiesByCertID);

        }

        return cacheInfo;

    }

    public List<GetMyCertificates200ResponseCertificatesListInner> getCertificatesOfUser(String nif)
            throws Exception, org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException {
        DigitalCertificateApi apiCert = getDigitalCertificateApi(nif);

        // @param enableFilter ENABLED/DISABLED (optional)
        final String enableFilter = "ENABLED";
        // @param expireFilter EXPIRED, EXPIRING, BOTH (optional)
        final String expireFilter = null;

        GetMyCertificates200Response response = apiCert.getMyCertificates(null, null, enableFilter, expireFilter, null,
                null, null);

        List<GetMyCertificates200ResponseCertificatesListInner> certs = response.getCertificatesList();
        return certs;
    }

    public static void resetNebulaCacheInfo() {
        cache.clear();
    }

    // ----------------------------------------------------

    @Override
    public List<PropertyInfo> getAvailableProperties(String propertyKeyBase) {

        List<PropertyInfo> props = new ArrayList<>();

        {
            // es.caib.sample.pluginsib.signatureweb.nebula.url=https://api-ansmt01.nebulaservice.net
            PropertyInfo propUrl = new PropertyInfo(NEBULA_BASE_PROPERTIES + "url", "URL de la API de Nebula", false,
                    "https://api-ansmt01.nebulaservice.net");
            props.add(propUrl);
        }

        {
            // es.caib.sample.pluginsib.signatureweb.nebula.url_auth=https://api.nebulaservice.net/trustedapps/v1
            PropertyInfo propUrlAuth = new PropertyInfo(NEBULA_BASE_PROPERTIES + "url_auth",
                    "URL de autenticació de Nebula", false, "https://api.nebulaservice.net/trustedapps/v1");
            props.add(propUrlAuth);
        }

        {
            //             es.caib.sample.pluginsib.signatureweb.nebula.applicationName=PORTAFIB
            PropertyInfo propApplicationName = new PropertyInfo(NEBULA_BASE_PROPERTIES + "applicationName",
                    "Nom de l'aplicació registrada a Nebula", false, null);
            props.add(propApplicationName);
        }

        {
            //  es.caib.sample.pluginsib.signatureweb.nebula.tenantId=2d-cf05-40ad-9493-XXXXXXXXXX
            PropertyInfo propTenantId = new PropertyInfo(NEBULA_BASE_PROPERTIES + "tenantId", "ID del tenant a Nebula",
                    false, null);
            props.add(propTenantId);
        }

        {
            //  es.caib.sample.pluginsib.signatureweb.nebula.appId=18-2235-47db-a2fd-XXXXXXXXXXXX
            PropertyInfo propAppId = new PropertyInfo(NEBULA_BASE_PROPERTIES + "appId",
                    "ID de l'aplicació registrada a Nebula", false, null);
            props.add(propAppId);
        }

        {
            // es.caib.sample.pluginsib.signatureweb.nebula.accessKey=60dF623BA94B0a5acD80B39XXXXXXXXXXXXXXXXXX
            PropertyInfo propAccessKey = new PropertyInfo(NEBULA_BASE_PROPERTIES + "accessKey",
                    "Clau d'accés de l'aplicació registrada a Nebula", false, null);
            props.add(propAccessKey);
        }

        {
            // En entorn de PRE de NEBULA els certificats no inclouen el NIF, per tant cal ignorar la seva validació
            // es.caib.sample.pluginsib.signatureweb.nebula.ignoreNifValidation=true
            PropertyInfo propIgnoreNifValidation = new PropertyInfo(NEBULA_BASE_PROPERTIES + "ignoreNifValidation",
                    "Indica si s'ha d'ignorar la validació del NIF" + " (En entorn de PRE de NEBULA els certificats"
                            + " no inclouen el NIF, per tant cal ignorar la seva validació)",
                    true, "false", new String[] { "true", "false" }, null);
            props.add(propIgnoreNifValidation);
        }

        {
            // es.caib.sample.pluginsib.signatureweb.nebula.ignore_certificate_filter
            PropertyInfo propIgnoreCertificateFilter = new PropertyInfo(
                    NEBULA_BASE_PROPERTIES + "ignore_certificate_filter",
                    "Indica si s'ha d'ignorar el filtre de certificats definit a l'Administració (filtreCertificats)",
                    true, "false", new String[] { "true", "false" }, null);
            props.add(propIgnoreCertificateFilter);
        }

        {
            // es.caib.sample.pluginsib.signatureweb.nebula.debug=false
            PropertyInfo propDebug = new PropertyInfo(NEBULA_BASE_PROPERTIES + "debug",
                    "Indica si s'activa el mode debug, que mostra informació addicional als logs", true, "false",
                    new String[] { "true", "false" }, null);
            props.add(propDebug);
        }

        {
            // es.caib.sample.pluginsib.signatureweb.nebula.debug=false
            PropertyInfo onlyhashDebug = new PropertyInfo(NEBULA_BASE_PROPERTIES + "onlyhashsignatures",
                    "Si val true indica que totes les signatures realitzades siguin emprant Hash", true, "false",
                    new String[] { "true", "false" }, null);
            props.add(onlyhashDebug);
        }

        return props;
    }

}

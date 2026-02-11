package org.fundaciobit.pluginsib.signatureweb.nebula;

import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.fundaciobit.pluginsib.core.v3.utils.PluginsManager;
import org.fundaciobit.pluginsib.signature.api.CommonInfoSignature;
import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignaturesSet;
import org.fundaciobit.pluginsib.signatureweb.api.ISignatureWebPlugin;
import org.fundaciobit.pluginsib.signatureweb.api.SignaturesSetWeb;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author anadal
 * 16 dic 2025 8:03:13
 */
public class NebulaTester {

    public static final String TEST_RESULTS_FOLDER = "./results/";
    public static final String NEBULA_PROPERTIES_FILE = "nebula.properties";
    public static final String TEST_PROPERTIES_FILE = "test.properties";

    static {
        new File(TEST_RESULTS_FOLDER).mkdirs();
    }

    private ISignatureWebPlugin pluginInstance = null;

    @BeforeClass
    public static void checkPropertiesFile() {
        File nebulaPropertiesFile = new File(NEBULA_PROPERTIES_FILE);
        File testPropertiesFile = new File(TEST_PROPERTIES_FILE);
        
        boolean nebulaExists = nebulaPropertiesFile.exists();
        boolean testExists = testPropertiesFile.exists();
        
        if (!nebulaExists) {
            System.out.println("ATENCIÓ: El fitxer '" + NEBULA_PROPERTIES_FILE + "' no existeix. Els tests NO s'executaran.");
        }
        if (!testExists) {
            System.out.println("ATENCIÓ: El fitxer '" + TEST_PROPERTIES_FILE + "' no existeix. Els tests NO s'executaran.");
        }
        
        // Aquesta línia farà que JUnit salti tots els tests si els fitxers no existeixen
        assumeTrue("Els fitxers de configuració " + NEBULA_PROPERTIES_FILE + " i " + TEST_PROPERTIES_FILE + " han d'existir per executar els tests",
                   nebulaExists && testExists);
    }

    public static void main(String[] args) {
        try {

            NebulaTester nebulaTest = new NebulaTester();
            
            nebulaTest.getCertificatesOfUser();

            nebulaTest.padesBasicSignature();
            
            nebulaTest.padesTimestampSignature();

            nebulaTest.cadesAttachedEnvelopingBasicSignature();
            nebulaTest.cadesAttachedEnvelopingTimestampSignature();
            nebulaTest.cadesDetachedBasicSignature();
            nebulaTest.cadesDetachedTimestampSignature();

            nebulaTest.xadesAttachedEnvelopedBasicSignature();
            nebulaTest.xadesAttachedEnvelopedTimestampSignature();
            nebulaTest.xadesAttachedEnvelopingBasicSignature();
            nebulaTest.xadesAttachedEnvelopingTimestampSignature();
            nebulaTest.xadesDetachedBasicSignature();
            nebulaTest.xadesDetachedTimestampSignature();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public ISignatureWebPlugin getPlugin() throws Exception {

        if (pluginInstance == null) {

            Properties properties = new Properties();
            properties.load(new FileReader(NEBULA_PROPERTIES_FILE));

            Locale locale = Locale.getDefault();

            String className = NebulaSignatureWebPlugin.class.getName();
            String basePropertiesKey = "es.caib.sample.";

            pluginInstance = (ISignatureWebPlugin) PluginsManager.instancePluginByClassName(className,
                    basePropertiesKey, properties);

            System.out.println("Plugin instantiated: " + pluginInstance.getName(locale));
        }

        return pluginInstance;
    }
    
    
    
    @Test
    public List<GetMyCertificates200ResponseCertificatesListInner> getCertificatesOfUser(
            ) throws ApiException, Exception, IOException {
        
        return getCertificatesOfUser(true);
    }
    
    
    
    
    public List<GetMyCertificates200ResponseCertificatesListInner> getCertificatesOfUser(boolean printInfo
            ) throws ApiException, Exception, IOException {
        
        
        NebulaSignatureWebPlugin nebula = (NebulaSignatureWebPlugin) getPlugin();
        
        Properties test = new Properties();
        test.load(new FileReader("test.properties"));
        String nif = test.getProperty("nif");
        
        
        List<GetMyCertificates200ResponseCertificatesListInner> certificates = nebula.getUserCertificatesFromCache(null, nif);
        if (printInfo) {
            
            if (certificates == null || certificates.isEmpty()) {
                System.out.println("No s'han trobat certificats per l'usuari amb NIF: " + nif);             
            } else {
            
                for (GetMyCertificates200ResponseCertificatesListInner cert : certificates) {
                    System.out.println("\n\n ---------- Cert + " + cert.getCertificateId() + " \n" + cert + "\n\n");
        
                    String certificateCer = cert.getCertificate();
                    byte[] certBytes = Base64.getDecoder().decode(certificateCer);
        
                    File f = new File("nebula_certificate_" + cert.getCertificateId()+ ".cer");
                    Files.write(f.toPath(), certBytes);
                    
                    System.out.println(" Certificat guardat a " + f.getAbsolutePath()); 
        
                }
            }
        }

        return certificates;
    }
    
    
    
    

    @Test
    public void padesBasicSignature() throws IOException, FileNotFoundException, Exception {

        padesCommonSignature("PAdES Basic Signature", false, "pades_basic_signat.pdf");

    }

    @Test
    public void padesTimestampSignature() throws IOException, FileNotFoundException, Exception {

        padesCommonSignature("PAdES Timestamp Signature", true, "pades_timestamp_signat.pdf");

    }

    protected void padesCommonSignature(String testName, boolean userRequiresTimeStamp, String resultFilename)
            throws IOException, FileNotFoundException, Exception {

        long startTime = System.currentTimeMillis();

        String fileToSignPath = "./samplefiles/pdf_a_signar.pdf";
        String mimeType = "application/pdf";
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;
        String signType = FileInfoSignature.SIGN_TYPE_PADES;
        String pathSignedFile = TEST_RESULTS_FOLDER + resultFilename;

        String result = doSignature(testName, fileToSignPath, mimeType, signType, signMode, userRequiresTimeStamp,
                pathSignedFile);

        checkTest(testName, startTime, result);

    }

    @Test
    public void cadesAttachedEnvelopingBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES attached enveloping Basic Signature";
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;
        String fileName = "cades_atached_enveloping_basic_signat.csig";
        boolean userRequiresTimeStamp = false;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    @Test
    public void cadesAttachedEnvelopingTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES attached enveloping Timestamp Signature";
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;
        String fileName = "cades_atached_enveloping_timestamp_signat.csig";
        boolean userRequiresTimeStamp = true;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    @Test
    public void cadesDetachedBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES Detached Basic Signature";
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;
        String fileName = "cades_Detached_basic_signat.csig";
        boolean userRequiresTimeStamp = false;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    @Test
    public void cadesDetachedTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES Detached Timestamp Signature";
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;
        String fileName = "cades_Detached_timestamp_signat.csig";
        boolean userRequiresTimeStamp = true;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    protected void cadesCommonSignature(String testName, int signMode, String fileName, boolean userRequiresTimeStamp)
            throws IOException, FileNotFoundException, Exception {
        String fileToSignPath = "./samplefiles/binari.bin";
        String mimeType = "application/octet-stream";
        String signType = FileInfoSignature.SIGN_TYPE_CADES;

        long startTime = System.currentTimeMillis();
        String pathSignedFile = TEST_RESULTS_FOLDER + fileName;

        String result = doSignature(testName, fileToSignPath, mimeType, signType, signMode, userRequiresTimeStamp,
                pathSignedFile);

        checkTest(testName, startTime, result);
    }

    @Test
    public void xadesAttachedEnvelopingBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloping Basic Signature";
        String fileName = "xades_attached_enveloping_basic_signature.xsig";
        boolean userRequiresTimeStamp = false;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void xadesAttachedEnvelopingTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloping Timestamp Signature";
        String fileName = "xades_attached_enveloping_timestamp_signature.xsig";
        boolean userRequiresTimeStamp = true;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void xadesAttachedEnvelopedBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloped Basic Signature";
        String fileName = "xades_attached_enveloped_basic_signature.xsig";
        boolean userRequiresTimeStamp = false;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void xadesAttachedEnvelopedTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloped Timestamp Signature";
        String fileName = "xades_attached_enveloped_timestamp_signature.xsig";
        boolean userRequiresTimeStamp = true;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void xadesDetachedBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Detached Basic Signature";
        String fileName = "xades_detached_basic_signature.xsig";
        boolean userRequiresTimeStamp = false;
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void xadesDetachedTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Detached Timestamp Signature";
        String fileName = "xades_detached_timestamp_signature.xsig";
        boolean userRequiresTimeStamp = true;
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    protected void xadesCommonSignature(String testName, String fileName, int signMode, boolean userRequiresTimeStamp)
            throws IOException, FileNotFoundException, Exception {
        String fileToSignPath = "./samplefiles/sample.xml";
        String mimeType = "application/xml";
        String signType = FileInfoSignature.SIGN_TYPE_XADES;

        long startTime = System.currentTimeMillis();
        String pathSignedFile = TEST_RESULTS_FOLDER + fileName;

        String result = doSignature(testName, fileToSignPath, mimeType, signType, signMode, userRequiresTimeStamp,
                pathSignedFile);

        checkTest(testName, startTime, result);
    }

    protected String doSignature(String testName, String fileToSignPath, String mimeType, String signType, int signMode,
            boolean userRequiresTimeStamp, String pathSignedFile) throws IOException, FileNotFoundException, Exception {

        Properties test = new Properties();
        test.load(new FileReader("test.properties"));

        String nif = test.getProperty("nif");
        String username = test.getProperty("username");
        String languageUI = test.getProperty("languageUI");

        return doSignature(testName, nif, username, languageUI, fileToSignPath, mimeType, signType, signMode,
                userRequiresTimeStamp, pathSignedFile);
    }

    protected String doSignature(String testName, String nif, String username, String languageUI, String fileToSignPath,
            String mimeType, String signType, int signMode, boolean userRequiresTimeStamp, String pathSignedFile)
            throws IOException, FileNotFoundException, Exception {

        System.out.println("\n\n================ Iniciant test: " + testName + " ================\n");

        SignaturesSetWeb ssw = createSignaturesSetWeb(nif, username, languageUI, fileToSignPath, mimeType, signType,
                signMode, userRequiresTimeStamp);

        final HttpServletRequest request = null; // Proves va ok
        final Map<String, Object> parameters = new HashMap<String, Object>();

        ISignatureWebPlugin plugin = getPlugin();

        String filter = plugin.filter(request, ssw, parameters);

        if (filter != null) {
            return "NO PASSA FILTRE: " + filter;
        }

        // Podem fer això ja que el plugin realitza la signatura directament i no redirecciona a cap pàgina

        String absolutePluginRequestPath = "/absolute/path/to/nebula/plugin";
        String relativePluginRequestPath = "/relative/path/to/nebula/plugin";

        String returnURL = plugin.signDocuments(request, absolutePluginRequestPath, relativePluginRequestPath, ssw,
                parameters);

        System.out.println("Return URL: " + returnURL);

        // Verificar Estat de la Signatura
        StatusSignaturesSet statusSignatureGlobal = ssw.getStatusSignaturesSet();

        switch (statusSignatureGlobal.getStatus()) {

            case StatusSignature.STATUS_FINAL_OK: {
                System.out.println("Signatura Global realitzada correctament.");
                FileInfoSignature[] fisList = ssw.getFileInfoSignatureArray();
                System.out.println("Verificant cada firma ...");
                for (FileInfoSignature fis : fisList) {

                    System.out.println("   ------ FIRMA [" + fis.getSignID() + " ] ");
                    System.out.println("\t - Estat: " + fis.getStatusSignature().getStatus());
                    switch (fis.getStatusSignature().getStatus()) {
                        case StatusSignature.STATUS_FINAL_OK:
                            System.out.println("\t - Signatura realitzada correctament.");
                            File desti = new File(pathSignedFile);

                            if (desti.exists()) {
                                desti.delete();
                            }

                            if (fis.getStatusSignature().getSignedData().renameTo(desti)) {
                                ;
                                System.out.println("\t - Fitxer signat guardat a: " + desti);
                            } else {
                                System.err.println(
                                        "\t - No s'ha pogut guardar el fitxer signat a: " + desti.getAbsolutePath());
                                return "No s'ha pogut guardar el fitxer signat a: " + desti
                                        + "(segurament estigui bloquejat)";
                            }
                        break;

                        case StatusSignature.STATUS_FINAL_ERROR:
                            System.err.println("\t - Error en la signatura:\n");
                            System.err.println("\t\t - Message: " + fis.getStatusSignature().getErrorMsg());
                            Throwable cause = fis.getStatusSignature().getErrorException();
                            if (cause != null) {
                                System.err.println("\t\t - Exception: " + fis.getStatusSignature().getErrorException());
                            }
                            return "Error en la firma [" + fis.getSignID() + " ]: "
                                    + fis.getStatusSignature().getErrorMsg();

                        default:
                            System.err.println("\t - Esta final incorrecte:\n");
                            return "Estat final incorrecte en la firma [" + fis.getSignID() + " ]: "
                                    + fis.getStatusSignature().getStatus();
                    }

                }
                // OK
                return null;
            }

            case StatusSignature.STATUS_FINAL_ERROR: {
                System.err.println("Error GLOBAL en la signatura:\n");
                System.err.println("\t - Message: " + statusSignatureGlobal.getErrorMsg());
                Throwable cause = statusSignatureGlobal.getErrorException();
                if (cause != null) {
                    System.err.println("\t - Exception: " + statusSignatureGlobal.getErrorException());
                }
                return "Error GLOBAL en la signatura: " + statusSignatureGlobal.getErrorMsg();
            }

            default: {
                String msg = "Estat final GLOBAL incorrecte: Status: " + statusSignatureGlobal.getStatus();
                System.err.println(msg);
                return msg;
            }
        }
    }

    protected SignaturesSetWeb createSignaturesSetWeb(String nif, String username, String languageUI,
            String fileToSignPath, String mimeType, String signType, int signMode, boolean userRequiresTimeStamp) {
        SignaturesSetWeb ssw = new SignaturesSetWeb();
        {

            CommonInfoSignature commonInfo = new CommonInfoSignature();
            commonInfo.setAdministrationID(nif);
            commonInfo.setUsername(username);
            commonInfo.setLanguageUI(languageUI);

            StatusSignature statusSignatureSign = new StatusSignature();
            statusSignatureSign.setStatus(StatusSignature.STATUS_INITIALIZING);

            FileInfoSignature fis = new FileInfoSignature();
            fis.setFileToSign(new File(fileToSignPath));
            fis.setLanguageSign("es");
            fis.setLocation("Esporles");
            fis.setMimeType(mimeType);
            fis.setName(fis.getFileToSign().getName());
            fis.setReason("Prova de firmar amb Nebula");
            fis.setSignAlgorithm(FileInfoSignature.SIGN_ALGORITHM_SHA256);
            fis.setSignID("nebula_sign_" + System.nanoTime());
            fis.setSignMode(signMode);
            fis.setSignNumber(1);
            fis.setSignOperation(FileInfoSignature.SIGN_OPERATION_SIGN);
            fis.setSignType(signType);
            fis.setStatusSignature(statusSignatureSign);
            fis.setUserRequiresTimeStamp(userRequiresTimeStamp);

            StatusSignaturesSet statusSignatureGlobal = new StatusSignaturesSet();
            statusSignatureGlobal.setStatus(StatusSignature.STATUS_INITIALIZING);

            ssw.setCommonInfoSignature(commonInfo);
            ssw.setExpiryDate(new java.util.Date(System.currentTimeMillis() + 3600 * 1000));
            ssw.setFileInfoSignatureArray(new FileInfoSignature[] { fis });
            ssw.setSignaturesSetID("nebula_ss_" + System.nanoTime());
            ssw.setStatusSignaturesSet(statusSignatureGlobal);
            ssw.setUrlFinal("/url/de/return/de/prova");

        }
        return ssw;
    }

    protected void checkTest(String testName, long startTime, String result) {
        long endTime = System.currentTimeMillis();
        System.out.println("Temps test " + testName + ":  " + (endTime - startTime) + " ms");

        assertNull("Error realitzant test " + testName + ": " + result, result);
    }

}

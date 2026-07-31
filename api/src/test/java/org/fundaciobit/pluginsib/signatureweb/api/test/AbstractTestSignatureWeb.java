package org.fundaciobit.pluginsib.signatureweb.api.test;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.fundaciobit.pluginsib.core.v3.utils.PluginsManager;
import org.fundaciobit.pluginsib.signature.api.CommonInfoSignature;
import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignaturesSet;
import org.fundaciobit.pluginsib.signatureweb.api.ISignatureWebPlugin;
import org.fundaciobit.pluginsib.signatureweb.api.SignaturesSetWeb;
import org.jboss.logging.Logger;

import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;

/**
 * 
 * @author anadal (u80067)
 * 24 jul 2026 10:25:36
 */
public abstract class AbstractTestSignatureWeb {

    protected Logger log = Logger.getLogger(this.getClass());

    private ISignatureWebPlugin pluginInstance = null;

    public AbstractTestSignatureWeb() throws Exception {
        super();

        getResultsDir().mkdirs();

        checkPropertiesFile();
    }

    /**
     * Conté un fitxer amb les propietats del plugin.
     * @throws IOException
     */
    protected abstract File getPluginPropertiesFile();

    /**
     * Conté un fitxer amb les propietats del test, com ara el NIF de l'usuari, username de l'usuari, ...
     * @return
     * @throws IOException
     */
    protected abstract File getTestFile();

    protected abstract Class<? extends ISignatureWebPlugin> getPluginClass();

    /**
     * Conté un directori on es guardaran els resultats de les proves.
     * @return
     */
    protected File getResultsDir() {
        File resultsDir = new File("./results/");
        return resultsDir;
    }

    protected String getPropertyBase() {
        return "es.caib.sample.";
    }

    /** Per si volem utilitzar un altre fitxer en algun test concret, per exemple un fitxer amb una signatura ja feta. */
    public File getFileToUseInTest(String testName) {
        return null;
    }

    /*
    protected File getResourceFile(String resourceName) throws URISyntaxException {
        // Cerca el recurs al classpath
        URL url = getClass().getClassLoader().getResource(resourceName);
        if (url == null) {
            throw new IllegalStateException("No s'ha trobat el recurs al classpath: " + resourceName);
        }
        // Converteix la URL en un File
        return new File(url.toURI());
    }*/

    protected File getResourceFile(String resourceName) {

        // Cerca el recurs al classpath
        URL url;
        {
            url = getClass().getClassLoader().getResource(resourceName);
            if (url == null) {
                throw new RuntimeException("No s'ha trobat el recurs al classpath: " + resourceName);
            }

            System.out.println("Recurs trobat al classpath: " + resourceName + " -> " + url);

            // Converteix la URL en un File
            //return new File(url.toURI());
        }

        System.out.println("Recurs trobat al classpath 2222: " + resourceName + " -> " + url);

        //URL url = getClass().getResource(resourceName);

        // URL url = getClass().getClassLoader().getResource(resourceName);

        try {
            return new File(url.toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            // El recurs està dins d'un JAR (URI no jeràrquic tipus jar:file:...!/...)
            try (InputStream is = url.openStream()) {
                if (is == null) {
                    throw new RuntimeException("Recurs no trobat: " + resourceName);
                }
                String fileName = new File(resourceName).getName();
                File tempFile = File.createTempFile("res-", "-" + fileName);
                tempFile.deleteOnExit();
                try (OutputStream os = new FileOutputStream(tempFile)) {
                    is.transferTo(os);
                }
                tempFile.deleteOnExit();
                return tempFile;
            } catch (IOException ex) {
                throw new RuntimeException("Error al crear el fitxer temporal per al recurs: " + resourceName, ex);
            }
        }
    }

    protected File getPathSignedFile(String testName, String fileName) {
        return new File(getResultsDir(), "TEST[" + testName.replace(' ', '_') + "]_" + fileName);
    }

    protected void checkTest(String testName, long startTime, String result) {
        long endTime = System.currentTimeMillis();
        System.out.println("Temps test " + testName + ":  " + (endTime - startTime) + " ms");

        assertNull("Error realitzant test " + testName + ": " + result, result);
    }

    
    public void checkPropertiesFile() {
        File propFile = getPluginPropertiesFile();

        assumeTrue("El fitxer de propietats del plugin no pot ser null", propFile != null);

        assumeTrue("ATENCIÓ: El fitxer '" + propFile.getAbsolutePath() + "' no existeix. Els tests NO s'executaran.",
                propFile.exists());

        File testPropertiesFile = getTestFile();

        assumeTrue("El fitxer de propietats del test no pot ser null", testPropertiesFile != null);

        assumeTrue("ATENCIÓ: El fitxer '" + testPropertiesFile.getAbsolutePath()
                + "' no existeix. Els tests NO s'executaran.", testPropertiesFile.exists());

    }

    public ISignatureWebPlugin getPlugin() throws Exception {

        if (pluginInstance == null) {

            Properties properties = new Properties();
            properties.load(new FileReader(getPluginPropertiesFile()));

            //Locale locale = Locale.getDefault();

            Class<? extends ISignatureWebPlugin> classe = getPluginClass();
            String basePropertiesKey = getPropertyBase();

            pluginInstance = (ISignatureWebPlugin) PluginsManager.instancePluginByClass(classe, basePropertiesKey,
                    properties);

            //log.info("Plugin instantiated: " + pluginInstance.getName(locale));
        }

        return pluginInstance;
    }

    @Test
    public void testPadesBasicSignature() throws IOException, FileNotFoundException, Exception {

        padesCommonSignature("PAdES Basic Signature", false, "pades_basic_signat.pdf");

    }

    @Test
    public void testPadesTimestampSignature() throws IOException, FileNotFoundException, Exception {

        padesCommonSignature("PAdES Timestamp Signature", true, "pades_timestamp_signat.pdf");

    }

    protected void padesCommonSignature(String testName, boolean userRequiresTimeStamp, String fileName)
            throws IOException, FileNotFoundException, Exception {

        long startTime = System.currentTimeMillis();

        File fileToSignPath = getFileToUseInTest(testName);

        if (fileToSignPath == null) {
            fileToSignPath = getResourceFile("signaturewebsamplefiles/pdf_a_signar.pdf");
        }

        String mimeType = "application/pdf";
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;
        String signType = FileInfoSignature.SIGN_TYPE_PADES;
        File pathSignedFile = getPathSignedFile(testName, fileName); // new File(getResultsDir(), resultFilename);

        String result = doSignature(testName, fileToSignPath, mimeType, signType, signMode, userRequiresTimeStamp,
                pathSignedFile);

        checkTest(testName, startTime, result);

    }

    @Test
    public void testXadesAttachedEnvelopingBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloping Basic Signature";
        String fileName = "xades_attached_enveloping_basic_signature.xsig";
        boolean userRequiresTimeStamp = false;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void testXadesAttachedEnvelopingTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloping Timestamp Signature";
        String fileName = "xades_attached_enveloping_timestamp_signature.xsig";
        boolean userRequiresTimeStamp = true;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void testXadesAttachedEnvelopedBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloped Basic Signature";
        String fileName = "xades_attached_enveloped_basic_signature.xsig";
        boolean userRequiresTimeStamp = false;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void testXadesAttachedEnvelopedTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Attached Enveloped Timestamp Signature";
        String fileName = "xades_attached_enveloped_timestamp_signature.xsig";
        boolean userRequiresTimeStamp = true;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void testXadesDetachedBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Detached Basic Signature";
        String fileName = "xades_detached_basic_signature.xsig";
        boolean userRequiresTimeStamp = false;
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    @Test
    public void testXadesDetachedTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "XAdES Detached Timestamp Signature";
        String fileName = "xades_detached_timestamp_signature.xsig";
        boolean userRequiresTimeStamp = true;
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;

        xadesCommonSignature(testName, fileName, signMode, userRequiresTimeStamp);

    }

    protected void xadesCommonSignature(String testName, String fileName, int signMode, boolean userRequiresTimeStamp)
            throws IOException, FileNotFoundException, Exception {

        File fileToSignPath = getFileToUseInTest(testName);

        if (fileToSignPath == null) {
            fileToSignPath = getResourceFile("signaturewebsamplefiles/sample.xml");
        }

        String mimeType = "application/xml";
        String signType = FileInfoSignature.SIGN_TYPE_XADES;

        long startTime = System.currentTimeMillis();
        File pathSignedFile = getPathSignedFile(testName, fileName);

        String result = doSignature(testName, fileToSignPath, mimeType, signType, signMode, userRequiresTimeStamp,
                pathSignedFile);

        checkTest(testName, startTime, result);
    }

    @Test
    public void testCadesAttachedEnvelopingBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES attached enveloping Basic Signature";
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;
        String fileName = "cades_atached_enveloping_basic_signat.csig";
        boolean userRequiresTimeStamp = false;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    @Test
    public void testCadesAttachedEnvelopingTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES attached enveloping Timestamp Signature";
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPING;
        String fileName = "cades_atached_enveloping_timestamp_signat.csig";
        boolean userRequiresTimeStamp = true;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    @Test
    public void testCadesDetachedBasicSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES Detached Basic Signature";
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;
        String fileName = "cades_Detached_basic_signat.csig";
        boolean userRequiresTimeStamp = false;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    @Test
    public void testCadesDetachedTimestampSignature() throws IOException, FileNotFoundException, Exception {

        String testName = "CAdES Detached Timestamp Signature";
        int signMode = FileInfoSignature.SIGN_MODE_DETACHED;
        String fileName = "cades_Detached_timestamp_signat.csig";
        boolean userRequiresTimeStamp = true;

        cadesCommonSignature(testName, signMode, fileName, userRequiresTimeStamp);

    }

    protected void cadesCommonSignature(String testName, int signMode, String fileName, boolean userRequiresTimeStamp)
            throws IOException, FileNotFoundException, Exception {

        File fileToSignPath = getFileToUseInTest(testName);

        if (fileToSignPath == null) {
            fileToSignPath = getResourceFile("signaturewebsamplefiles/binari.bin");
            // getResourceFile("samplefiles/binari.bin").
        }

        String mimeType = "application/octet-stream";
        String signType = FileInfoSignature.SIGN_TYPE_CADES;

        long startTime = System.currentTimeMillis();
        File pathSignedFile = getPathSignedFile(testName, fileName); //new File(getResultsDir(), fileName);

        String result = doSignature(testName, fileToSignPath, mimeType, signType, signMode, userRequiresTimeStamp,
                pathSignedFile);

        checkTest(testName, startTime, result);
    }

    protected String doSignature(String testName, File fileToSignPath, String mimeType, String signType, int signMode,
            boolean userRequiresTimeStamp, File pathSignedFile) throws IOException, FileNotFoundException, Exception {

        Properties test = new Properties();
        test.load(new FileReader(getTestFile()));

        String nif = test.getProperty("nif");
        if (nif == null || nif.trim().isEmpty()) {
            throw new IllegalStateException(
                    "No s'ha trobat la propietat 'nif' al fitxer de test: " + getTestFile().getAbsolutePath());
        }
        String username = test.getProperty("username");
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalStateException(
                    "No s'ha trobat la propietat 'username' al fitxer de test: " + getTestFile().getAbsolutePath());
        }
        String languageUI = test.getProperty("languageUI");
        if (languageUI == null || languageUI.trim().isEmpty()) {
            throw new IllegalStateException(
                    "No s'ha trobat la propietat 'languageUI' al fitxer de test: " + getTestFile().getAbsolutePath());
        }

        String error;
        error = doSignature(testName, nif, username, languageUI, fileToSignPath, mimeType, signType, signMode,
                userRequiresTimeStamp, pathSignedFile);

        if (error != null) {
            System.err.println("============ Error en el test [" + testName + "] ===================================");
            System.err.println(error);
            System.err.println("===============================================================================");
        } else {
            System.out.println("Test " + testName + " realitzat correctament.");
        }

        return error;
    }

    protected String doSignature(String testName, String nif, String username, String languageUI, File fileToSignPath,
            String mimeType, String signType, int signMode, boolean userRequiresTimeStamp, File pathSignedFile)
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
        String host = "localhost";
        int port = 8888;

        String relativePluginRequestPath = "/" + getPluginClass().getSimpleName() + "/plugin";
        String absolutePluginRequestPath = "http://" + host + ":" + port + relativePluginRequestPath;

        String returnURL = plugin.signDocuments(request, absolutePluginRequestPath, relativePluginRequestPath, ssw,
                parameters);

        if (returnURL.equals(ssw.getUrlFinal())) {
            // Finalitzat OK
        } else {

            Server server = startWebServer(ssw, plugin, port, relativePluginRequestPath, absolutePluginRequestPath,
                    false);

            // Obrir Navegador amb la URL de signatura
            String fullURL = "http://" + host + ":" + port + returnURL;

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(fullURL));
            } else {
                System.err.println("No es pot obrir el navegador automàticament. Obriu manualment la URL: " + fullURL);
            }

            // Esperem que el servidor s'aturi quan es rebi la callback
            server.join();

        }

        // Verificar Estat de la Signatura
        StatusSignaturesSet statusSignatureGlobal = ssw.getStatusSignaturesSet();

        try {
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
                                final File desti = pathSignedFile;

                                if (desti.exists()) {
                                    desti.delete();
                                }

                                if (fis.getStatusSignature().getSignedData().renameTo(desti)) {

                                    System.out.println("\t - Fitxer signat guardat a: " + desti);
                                } else {
                                    System.err.println("\t - No s'ha pogut guardar el fitxer signat a: "
                                            + desti.getAbsolutePath());
                                    return "No s'ha pogut guardar el fitxer signat a: " + desti
                                            + "(segurament estigui bloquejat)";
                                }
                            break;

                            case StatusSignature.STATUS_FINAL_ERROR:
                                System.err.println("\t - Error en la signatura:\n");
                                System.err.println("\t\t - Message: " + fis.getStatusSignature().getErrorMsg());
                                Throwable cause = fis.getStatusSignature().getErrorException();
                                if (cause != null) {
                                    System.err.println(
                                            "\t\t - Exception: " + fis.getStatusSignature().getErrorException());
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
        } finally {

            try {
                plugin.closeSignaturesSet(request, returnURL);
            } catch (Throwable e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
    }

    /**
     * Posa en marxa un servidor WEB per donar respostes a les peticions del plugin. Aquest servidor s'atura automàticament quan es rep la petició de callback.
     * @param ssw
     * @param plugin
     * @param port
     * @param relativePluginRequestPath
     * @param absolutePluginRequestPath
     * @param debug
     * @return
     * @throws Exception
     */
    protected Server startWebServer(SignaturesSetWeb ssw, ISignatureWebPlugin plugin, int port,
            String relativePluginRequestPath, String absolutePluginRequestPath, boolean debug) throws Exception {
        Server server = new Server(port); // (2) Definir port

        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setHandler(new AbstractHandler() {
            @Override

            public void handle(String target, Request baseRequest, HttpServletRequest request,
                    HttpServletResponse response) {
                // Obtenir si la petició és GET o POST
                final boolean isGet = "GET".equalsIgnoreCase(request.getMethod());
                final int signatureIndex = 0;
                final String signaturesSetID = ssw.getSignaturesSetID();

                if (debug) {
                    System.out.println("=======================================================");
                    System.out.println("=====> Target: " + target);
                    System.out.println("=====> request.getRequestURI(): " + request.getRequestURI());
                    System.out.println("=====> request.getQueryString(): " + request.getQueryString());
                    System.out.println("=====> FINAL: " + ssw.getUrlFinal());
                }

                if (target.equals(ssw.getUrlFinal())) {
                    if (debug) {
                        System.out.println("=====> Callback: Aturam servidor");
                    }

                    // Aturar en un fil separat per evitar deadlock
                    stopServer(server);

                    response.setContentType("text/html; charset=utf-8");
                    try {
                        response.getWriter()
                                .println("<html><body><h1>Signatura completada. <br/>"
                                        + "Revisi la consola per saber si tot ha anat bé.<br/>"
                                        + "Podeu tancar aquesta finestra.</h1></body></html>");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    response.setStatus(200);

                    /*} else if (target.startsWith("/favicon.ico")) {
                    
                    response.setStatus(200);
                       
                    
                    */
                } else {

                    // query s'obte a partir del Target llevant-li la part relativa

                    if (!target.startsWith(relativePluginRequestPath)) {
                        System.err.println("Error: El Target [" + target
                                + "] no comença amb la ruta relativa del plugin: " + relativePluginRequestPath);
                        response.setStatus(400);
                        return;
                    }

                    String query = target.substring(relativePluginRequestPath.length() + 1); // +1 per eliminar el '/' inicial

                    if (debug) {
                        System.out.println("=====> query: " + query);
                    }

                    try {
                        if (isGet) {
                            plugin.requestGET(absolutePluginRequestPath, relativePluginRequestPath, query,
                                    signaturesSetID, signatureIndex, request, response);
                        } else {
                            plugin.requestPOST(absolutePluginRequestPath, relativePluginRequestPath, query,
                                    signaturesSetID, signatureIndex, request, response);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                        stopServer(server);
                        response.setStatus(500);
                    }
                }

                baseRequest.setHandled(true);
            }

            protected void stopServer(Server server) {
                new Thread(() -> {
                    try {
                        Thread.sleep(2000); // Esperar 2 segons abans d'aturar el servidor
                        server.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

        server.setHandler(sessionHandler);

        server.start();
        return server;
    }

    protected SignaturesSetWeb createSignaturesSetWeb(String nif, String username, String languageUI,
            File fileToSignPath, String mimeType, String signType, int signMode, boolean userRequiresTimeStamp) {
        SignaturesSetWeb ssw = new SignaturesSetWeb();
        {

            CommonInfoSignature commonInfo = new CommonInfoSignature();
            commonInfo.setAdministrationID(nif);
            commonInfo.setUsername(username);
            commonInfo.setLanguageUI(languageUI);

            StatusSignature statusSignatureSign = new StatusSignature();
            statusSignatureSign.setStatus(StatusSignature.STATUS_INITIALIZING);

            FileInfoSignature fis = new FileInfoSignature();
            fis.setFileToSign(fileToSignPath);
            fis.setLanguageSign("es");
            fis.setLocation("Esporles");
            fis.setMimeType(mimeType);
            fis.setName(fis.getFileToSign().getName());
            fis.setReason("Prova de firmar amb " + getPluginClass().getSimpleName());
            fis.setSignAlgorithm(FileInfoSignature.SIGN_ALGORITHM_SHA256);
            fis.setSignID(getPluginClass().getSimpleName() + "_sign_" + System.nanoTime());
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
            ssw.setSignaturesSetID(this.getPluginClass().getSimpleName() + "_SignatureSet_" + System.nanoTime());
            ssw.setStatusSignaturesSet(statusSignatureGlobal);
            ssw.setUrlFinal("/url/de/return/de/prova");

        }
        return ssw;
    }

}

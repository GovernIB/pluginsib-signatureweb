package org.fundaciobit.signatureweb.miniappletinserversia;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.fundaciobit.pluginsib.signature.api.CommonInfoSignature;
import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signature.api.ITimeStampGenerator;
import org.fundaciobit.pluginsib.signature.api.PdfVisibleSignature;
import org.fundaciobit.pluginsib.signature.api.PolicyInfoSignature;
import org.fundaciobit.pluginsib.signature.api.SecureVerificationCodeStampInfo;
import org.fundaciobit.pluginsib.signature.api.SignaturesTableHeader;
import org.fundaciobit.pluginsib.signature.api.StatusSignature;
import org.fundaciobit.pluginsib.signature.api.StatusSignaturesSet;
import org.fundaciobit.pluginsib.signatureweb.api.SignaturesSetWeb;
import org.fundaciobit.pluginsib.signatureweb.miniappletinserversia.MiniAppletInServerSIASignatureWebPlugin;

import com.openlandsw.rss.gateway.CertificateInfo;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author anadal
 *
 */
public class SignatureInServerSIATest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *          name of the test case
     */
    public SignatureInServerSIATest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(SignatureInServerSIATest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
        //main(null);
    }

    public static void main(String[] args) {

        try {

            Properties properties = new Properties();

            properties.load(new FileInputStream("test.properties"));

            String username = properties.getProperty("username");
            String administrationID = properties.getProperty("administrationID");
            String languageUI = "ca";
            File fileToSign = new File(properties.getProperty("pdf.source"));

            MiniAppletInServerSIASignatureWebPlugin plugin;
            plugin = new MiniAppletInServerSIASignatureWebPlugin("es.ibsalut.example.", properties);

            // listCertificatesOfUsername(username, administrationID, plugin);
            SignaturesSetWeb signaturesSet = createSignaturesSet(username, administrationID, languageUI, fileToSign);

            // 1.- SignDocument
            Map<String, CertificateInfo> certificateInfoMap = plugin.signDocumentsWithoutRequest(signaturesSet);
            String cert = new ArrayList<String>(certificateInfoMap.keySet()).get(0);

            // Tot valors null
            String[] timeStampUrlByIndex = new String[signaturesSet.getFileInfoSignatureArray().length];

            // 2.- Presign
            int port = 1989 + (int) (Math.random() * 100.0);
            final String callBackURL = "http://localhost:" + port + "/returnurl/" + signaturesSet.getSignaturesSetID();
            final boolean debug = true;
            Locale locale = new Locale(languageUI);
            String redireccionURL = plugin.fimarPreWithoutRequest(signaturesSet, locale, debug, callBackURL, cert,
                    certificateInfoMap, timeStampUrlByIndex);

            System.out.println("RedirectUrl = " + redireccionURL);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(redireccionURL));
            } else {
                System.out.println("Per favor obri un Navegador i copia-li la URL anterior ...");
            }

            // 3.- Esperam a la firma des de SIA
            readFromSocket(port);

            // 4.- Recuperam fitxers signats i les guardam dins SignaturesSet
            plugin.firmarPostWithoutRequest(signaturesSet, locale);

            // 5.- Imprimim resultats de la firma es troben dins de signaturesSet

            switch (signaturesSet.getStatusSignaturesSet().getStatus()) {

                case StatusSignaturesSet.STATUS_INITIALIZING: // = 0;
                    System.err.println("S'ha rebut un estat inconsistent del proces de firma"
                            + " (inicialitzant). Pot ser el PLugin de Firma no està ben desenvolupat."
                            + " Consulti amb el seu administrador.");

                case StatusSignaturesSet.STATUS_IN_PROGRESS: // = 1;
                    System.err.println("S'ha rebut un estat inconsistent del proces de firma"
                            + " (En Progrés). Pot ser el PLugin de Firma no està ben desenvolupat."
                            + " Consulti amb el seu administrador.");

                case StatusSignaturesSet.STATUS_FINAL_ERROR: // = -1;
                {
                    System.err.println("Error durant la realització de les firmes: "
                            + signaturesSet.getStatusSignaturesSet().getErrorMsg());
                    Throwable desc = signaturesSet.getStatusSignaturesSet().getErrorException();
                    if (desc != null) {
                        desc.printStackTrace(System.err);
                    }
                    
                }

                case StatusSignaturesSet.STATUS_CANCELLED: // = -2;
                {
                    System.err.println("Durant el proces de firmes, l'usuari ha cancelat la transacció.");
                    
                }

                case StatusSignaturesSet.STATUS_FINAL_OK: // = 2;
                {
                    File[] resultsFile = processStatusFileOfSign(signaturesSet);
                    
                    // TODO falta processar N fitxers

                    File pdfDestBase = new File(properties.getProperty("pdf.dest"));

                    File pdfDest = new File(pdfDestBase.getParentFile(), "MINIAPPLET_" + pdfDestBase.getName());
                    
                    if (pdfDest.exists()) {
                        pdfDest.delete();
                    }

                    resultsFile[0].renameTo(pdfDest);

                    System.out.println(" Document Signat Guardat a " + pdfDest.getAbsolutePath());

                } // Final Case Firma OK
            } // Final Switch Firma

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected static File[] processStatusFileOfSign(SignaturesSetWeb signaturesSet)
            throws Exception, FileNotFoundException, IOException {

        FileInfoSignature[] results = signaturesSet.getFileInfoSignatureArray();

        File[] resultFile = new File[results.length];

        System.out.println(" ===== RESULTATS [" + results.length + "] =========");

        for (int i = 0; i < results.length; i++) {

            FileInfoSignature file = results[i];

            final String signID = file.getSignID();

            System.out.println(" ---- Signature [ " + signID + " ]");

            StatusSignature fss = file.getStatusSignature();

            int statusSign = fss.getStatus();

            switch (statusSign) {

                case StatusSignature.STATUS_INITIALIZING: // = 0;
                    System.err.println("  STATUS = " + statusSign + " (STATUS_INITIALIZING)");
                    System.err.println("  ESULT: Incoherent Status");
                break;

                case StatusSignature.STATUS_IN_PROGRESS: // = 1;
                    System.err.println("  STATUS = " + statusSign + " (STATUS_IN_PROGRESS)");
                    System.err.println("  RESULT: Incoherent Status");
                break;

                case StatusSignature.STATUS_FINAL_ERROR: // = -1;
                    System.err.println("  STATUS = " + statusSign + " (STATUS_ERROR)");
                    System.err.println("  RESULT: Error en la firma: " + fss.getErrorMsg());
                break;

                case StatusSignature.STATUS_CANCELLED: // = -2;
                    System.err.println("  STATUS = " + statusSign + " (STATUS_CANCELLED)");
                    System.err.println("  RESULT: L'usuari ha cancel.lat la firma.");
                break;

                case StatusSignature.STATUS_FINAL_OK: // = 2;

                    File signedData = file.getStatusSignature().getSignedData();
                    /*
                    String postFix;
                    String signType = fssr.getSignedFileInfo().getSignType();
                    if (FirmaSimpleSignedFileInfo.SIGN_TYPE_PADES.equals(signType)) {
                        postFix = "_signed.pdf";
                    } else if (FirmaSimpleSignedFileInfo.SIGN_TYPE_CADES.equals(signType)) {
                        postFix = "_signed.csig";
                    } else if (FirmaSimpleSignedFileInfo.SIGN_TYPE_XADES.equals(signType)) {
                        postFix = "_signed.xsig";
                    } else {
                        postFix = "_signed.unknown_extension_for_sign_type_" + signType;
                    }
                    */
                    resultFile[i] = signedData;

                break;
            }

        } // Final for de fitxers firmats

        return resultFile;
    }

    private static SignaturesSetWeb createSignaturesSet(String username, String administrationID, String languageUI,
            File fileToSign) {
        SignaturesSetWeb signaturesSet;
        String filtreCertificats = null;

        CommonInfoSignature commonInfoSignature;
        commonInfoSignature = new CommonInfoSignature(languageUI, filtreCertificats, username, administrationID);

        String signID = "SignID_1_" + System.currentTimeMillis();

        File previusSignatureDetachedFile = null;
        String mimeType = "application/pdf";
        String name = fileToSign.getName();
        String reason = "Prova SIA";
        String location = "Developer Place";
        String signerEmail = "any@any.com";
        int signNumber = 1;
        final String languageSign = languageUI;
        int signOperation = FileInfoSignature.SIGN_OPERATION_SIGN;
        String signType = FileInfoSignature.SIGN_TYPE_PADES;
        String signAlgorithm = FileInfoSignature.SIGN_ALGORITHM_SHA256;
        int signMode = FileInfoSignature.SIGN_MODE_ATTACHED_ENVELOPED;
        int signaturesTableLocation = FileInfoSignature.SIGNATURESTABLELOCATION_WITHOUT;
        SignaturesTableHeader signaturesTableHeader = null;
        PdfVisibleSignature pdfVisibleSignature = null;
        SecureVerificationCodeStampInfo secureVerificationCodeStampInfo = null;
        boolean userRequiresTimeStamp = false;
        ITimeStampGenerator timeStampGenerator = null;
        PolicyInfoSignature policyInfoSignature = null;
        String expedientCode = null;
        String expedientName = null;
        String expedientUrl = null;
        String procedureCode = null;
        String procedureName = null;

        FileInfoSignature fileInfoSignature = new FileInfoSignature(signID, fileToSign, previusSignatureDetachedFile,
                mimeType, name, reason, location, signerEmail, signNumber, languageSign, signOperation, signType,
                signAlgorithm, signMode, signaturesTableLocation, signaturesTableHeader, pdfVisibleSignature,
                secureVerificationCodeStampInfo, userRequiresTimeStamp, timeStampGenerator, policyInfoSignature,
                expedientCode, expedientName, expedientUrl, procedureCode, procedureName);

        FileInfoSignature[] fileInfoSignatureArray = { fileInfoSignature };

        String signaturesSetID = "Test_" + System.currentTimeMillis();
        Date expiryDate = new Date(System.currentTimeMillis() + 259200000); // + 3 dies,

        String urlFinal = "https://www.microsoft.com";

        signaturesSet = new SignaturesSetWeb(signaturesSetID, expiryDate, commonInfoSignature, fileInfoSignatureArray,
                urlFinal);
        return signaturesSet;
    }

    public static void readFromSocket(int port) throws Exception {

        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Servidor escoltant al PORT: " + port);
        {
            Socket clientSocket = serverSocket.accept();
            System.err.println("Nou Client Connectat desde " + clientSocket.getRemoteSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

            String s;
            System.err.println(" =========================== ");
            while ((s = in.readLine()) != null) {
                System.out.println(s);
                break;
            }
            System.err.println(" =========================== ");

            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            out.println(
                    "<html><body>OK (Revisi consola per saber l'estat final del proc&eacute;s de Firma)</body></html>");

            System.err.println("Connexio amb el client finalitzada.");
            out.flush();
            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();
    }

    public static void listCertificatesOfUsername(String username, String administrationID,
            MiniAppletInServerSIASignatureWebPlugin plugin) throws Exception, FileNotFoundException, IOException {
        final String filter = "";
        boolean filtered = (plugin.filter(username, administrationID, filter) != 0);

        if (!filtered) {
            System.out.println("No ha passat el filtre del Plugin");
        } else {
            System.out.println("FILTRE OK");
        }

        Map<String, CertificateInfo> certs = plugin.listCertificates(username, administrationID);

        System.out.println("Certificats Size" + certs.size());

        for (CertificateInfo cinfo : certs.values()) {
            System.out.println(" ========================= ");
            System.out.println(cinfo.getDn_certificate());
            byte[] data = cinfo.getCertificate();

            String dn = cinfo.getDn_certificate();
            FileOutputStream file = new FileOutputStream(dn.substring(0, Math.max(dn.length(), 200)) + ".cer");

            file.write(data);

            file.flush();

            file.close();
        }
    }

}

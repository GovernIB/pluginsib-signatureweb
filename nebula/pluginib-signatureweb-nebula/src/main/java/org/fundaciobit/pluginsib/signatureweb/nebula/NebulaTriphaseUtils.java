package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.security.cert.X509Certificate;
import java.util.Base64;

import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signatureserver.miniappletutils.MiniAppletUtils;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CLoginRequestDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSessionDTOV3;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSignCloseDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSignDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSignReqDTOV3;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.services.ApiException;


/**
 * 
 * @author anadal (u80067)
 * 20 abr 2026 10:48:22
 */
public class NebulaTriphaseUtils {

   
    protected static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(NebulaTriphaseUtils.class);
    
    
    public static byte[] signHashUsingNebulaApi(final byte[] hashToSign, DigitalCertificateApi api,
            GetMyCertificates200ResponseCertificatesListInner nebulaCertificate,

            String pin, FileInfoSignature fileInfo,
            X509Certificate certificate) throws ApiException, Exception {
        CLoginRequestDTO clogin = new CLoginRequestDTO();
        clogin.setPin(pin);
        clogin.setSigningId(nebulaCertificate.getSigningId());

        CSessionDTOV3 cSession = api.signInitV3(clogin);

        CSignReqDTOV3 request = new CSignReqDTOV3();

        // XYZ ZZZ
        //log.info("\n\n  XYZ ZZZ CSignReqDTOV3.request => " + request + "\n\n");

        /*  The mechanism used to generate the signature. If this parameter is not provided, the default mechanism is CKM_RSA_PKCS
         * 
         * 
         * 1    CKM_RSA_PKCS
        3   CKM_RSA_X_509
        4   CKM_MD2_RSA_PKCS
        5   CKM_MD5_RSA_PKCS
        6   CKM_SHA1_RSA_PKCS No suportat
        64  CKM_SHA256_RSA_PKCS
        65  CKM_SHA384_RSA_PKCS
        66  CKM_SHA512_RSA_PKCS
         * 
         * 
         */
        final String fileInfoSignAlgorithm = fileInfo.getSignAlgorithm();

        String algorithmNebulaID = null;
        switch (fileInfoSignAlgorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                algorithmNebulaID = "6";
            break;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                algorithmNebulaID = "64";
            break;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                algorithmNebulaID = "65";
            break;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                algorithmNebulaID = "66";
            break;
            default:
                throw new Exception("L'algorisme de signatura " + fileInfoSignAlgorithm + " no està suportat");
        }

        request.setMechanism(algorithmNebulaID);
        request.setObjId(cSession.getObjId());
        request.setSession(cSession.getSession());
        request.setSigndata(Base64.getEncoder().encodeToString(hashToSign));

        {
            log.info("CSignReqDTOV3.request => " + request);
            String algorithmMiniApplet = MiniAppletUtils.convertAlgorithm(fileInfo);
            log.info("XYZ ZZZ   ALGORITHM MINIAPPLET =   " + algorithmMiniApplet);
            log.info("XYZ ZZZ   ALGORITHM FILEINFO =   " + fileInfo.getSignAlgorithm());
            log.info("XYZ ZZZ   ALGORITHM NEBULA ID =   " + algorithmNebulaID);
        }

        CSignDTO signed = api.signHashV3(request);

        if (signed.getMessage() == null || !signed.getMessage().startsWith("Operation Success")) {
            final String msg = "El procés de generació d'una firma trifàsica (amb HASH) ha fallat: "
                    + signed.getMessage();
            log.error("Signed STATUS: " + signed.getMessage(), new Exception(msg));
            throw new Exception(msg);
        }

        CSignCloseDTO signedClose = new CSignCloseDTO();
        signedClose.setSession(cSession.getSession());

        api.signCloseV2(signedClose);

        byte[] signedHash = Base64.getDecoder().decode(signed.getSignedData());

        return signedHash;
    }
    
    
}

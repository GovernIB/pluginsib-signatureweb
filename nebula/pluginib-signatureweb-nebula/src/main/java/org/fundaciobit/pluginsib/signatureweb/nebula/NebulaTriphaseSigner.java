package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.util.Base64;
import java.util.Properties;

import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signatureserver.miniappletutils.AbstractTriFaseSigner;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CLoginRequestDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSessionDTOV3;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSignCloseDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSignDTO;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.CSignReqDTOV3;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.jboss.logging.Logger;

/**
 * Implementació de signatura trifàsica per a Nebula.
 * @author anadal
 * 10 febrer 2026 13:26:58
 */
public class NebulaTriphaseSigner extends AbstractTriFaseSigner {
    
    Logger log = Logger.getLogger(NebulaTriphaseSigner.class);

    protected final DigitalCertificateApi api;

    protected final Properties params;

    protected final GetMyCertificates200ResponseCertificatesListInner cert;
    
    protected final String pin;

    public NebulaTriphaseSigner(final DigitalCertificateApi api, final Properties params,
            final GetMyCertificates200ResponseCertificatesListInner cert, String pin) {
        this.api = api;
        this.params = params;
        this.cert = cert;
        this.pin = pin;
    }

    @Override
    public byte[] step2_signHash(final String algorithm, final byte[] hashDocumentoParam) throws Exception {

        CLoginRequestDTO clogin = new CLoginRequestDTO();
        clogin.setPin(pin);
        clogin.setSigningId(cert.getSigningId());
        

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
        String algorithmID = null;
        switch (algorithm) {
            case FileInfoSignature.SIGN_ALGORITHM_SHA1:
                algorithmID = "6";
            break;
            case FileInfoSignature.SIGN_ALGORITHM_SHA256:
                algorithmID = "64";
            break;
            case FileInfoSignature.SIGN_ALGORITHM_SHA384:
                algorithmID = "65";
            break;
            case FileInfoSignature.SIGN_ALGORITHM_SHA512:
                algorithmID = "66";
            break;
            default:
                throw new Exception("L'algorisme de signatura " + algorithm + " no està suportat");
        }
        
        log.info("XYZ ZZZ   ALGORITHM =   " + algorithm + "\n");
        log.info("XYZ ZZZ   ALGORITHM ID =   " + algorithmID + "\n");

        request.setMechanism(algorithmID);
        request.setObjId(cSession.getObjId());
        request.setSession(cSession.getSession());
        request.setSigndata(Base64.getEncoder().encodeToString(hashDocumentoParam));

        CSignDTO signed = api.signHashV3(request);

        CSignCloseDTO signedClose = new CSignCloseDTO();
        signedClose.setSession(cSession.getSession());

        api.signCloseV2(signedClose);

        //log.info("Signed: " + signed);

        byte[] signedHash = Base64.getDecoder().decode(signed.getSignedData());
        
        return signedHash;

    }

    public Properties getParams() {
        return params;
    }

    /**
     * Evita que la càrrega es faci amb classloaders pensats per funcionar en applets.
     * Veure #541.
     */
    @Override
    public Class<?> loadClass(String name) throws Exception {
        return Class.forName(name);
    }
}

package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.security.cert.X509Certificate;

import org.fundaciobit.pluginsib.signature.api.CommonInfoSignature;
import org.fundaciobit.pluginsib.signature.api.FileInfoSignature;
import org.fundaciobit.pluginsib.signatureserver.miniappletutils.AbstractPadesTriPhaseSigner;

import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.api.DigitalCertificateApi;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;

import org.jboss.logging.Logger;

/**
 * 
 * @author anadal (u80067)
 * 20 abr 2026 10:44:07
 */
public class NebulaPadesTriphaseSigner extends AbstractPadesTriPhaseSigner {

    protected final Logger log = Logger.getLogger(NebulaPadesTriphaseSigner.class);

    protected final DigitalCertificateApi api;

    protected final GetMyCertificates200ResponseCertificatesListInner nebulaCertificate;

    protected final String pin;

    public NebulaPadesTriphaseSigner(DigitalCertificateApi api,
            GetMyCertificates200ResponseCertificatesListInner nebulaCertificate,

            String pin, CommonInfoSignature commonInfoSignature, FileInfoSignature fileInfo,
            X509Certificate certificate, String timeStampURL) {
        super(commonInfoSignature, fileInfo, certificate, timeStampURL);
        this.api = api;
        this.nebulaCertificate = nebulaCertificate;
        this.pin = pin;
    }

    @Override
    public byte[] step2_SignHash(final byte[] hashToSign) throws Exception {

        return NebulaTriphaseUtils.signHashUsingNebulaApi(hashToSign, api, nebulaCertificate, pin, fileInfo,
                getCertificate());

    }

}

package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.util.List;
import java.util.Map;

import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.Policy;

/**
 * 
 * @author anadal
 * 12 febrer 2026 10:48:55
 */
public class NebulaCacheInfo {

    protected final String nif;

    protected final List<NebulaAuthenticatorType> authenticators;

    private long expirationTime;

    // TODO Ajuntar certificats i Policy en una classe !!!!!

    protected Map<String, GetMyCertificates200ResponseCertificatesListInner> certificatesByCertID = null;

    protected Map<String, Policy> policiesByCertID = null;

    public NebulaCacheInfo(String nif, List<NebulaAuthenticatorType> authenticators, String signaturesSetID) {
        super();
        this.nif = nif;
        this.authenticators = authenticators;

        NebulaCache.putNebulaCacheSessionInfo(signaturesSetID, authenticators);

        // 10 hores
        this.expirationTime = System.currentTimeMillis() + 10 * 60 * 1000;
    }

    public Map<String, GetMyCertificates200ResponseCertificatesListInner> getCertificatesByCertID() {
        return certificatesByCertID;
    }

    public void setCertificatesByCertID(
            Map<String, GetMyCertificates200ResponseCertificatesListInner> certificatesByCertID) {
        this.certificatesByCertID = certificatesByCertID;
    }

    public Map<String, Policy> getPoliciesByCertID() {
        return policiesByCertID;
    }

    public void setPoliciesByCertID(Map<String, Policy> policiesByCertID) {
        this.policiesByCertID = policiesByCertID;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public String getNif() {
        return nif;
    }

    public boolean isExpired() {

        return System.currentTimeMillis() > this.expirationTime;
    }

    public List<NebulaAuthenticatorType> getAuthenticators() {
        return authenticators;
    }

}

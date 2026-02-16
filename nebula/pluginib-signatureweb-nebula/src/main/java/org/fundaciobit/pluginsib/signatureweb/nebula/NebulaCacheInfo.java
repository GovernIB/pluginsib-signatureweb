package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.util.Map;

import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.Policy;

/**
 * 
 * @author anadal
 * 12 febrer 2026 10:48:55
 */
public class NebulaCacheInfo {

    protected long expirationTime;

    protected final String nif;

    protected Map<String, GetMyCertificates200ResponseCertificatesListInner> certificatesByCertID = null;

    protected Map<String, Policy> policiesByCertID = null;

    protected String selectedCertificateID = null;

    protected String pin;

    public NebulaCacheInfo(String nif) {
        super();
        this.nif = nif;
        // 8 hores
        this.expirationTime = System.currentTimeMillis() + 8 * 60 * 1000;
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

    public String getSelectedCertificateID() {
        return selectedCertificateID;
    }

    public void setSelectedCertificateID(String selectedCertificateID) {
        this.selectedCertificateID = selectedCertificateID;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public void sessionReset() {
        this.expirationTime = System.currentTimeMillis();

        this.pin = null;
        this.selectedCertificateID = null;
    }

}

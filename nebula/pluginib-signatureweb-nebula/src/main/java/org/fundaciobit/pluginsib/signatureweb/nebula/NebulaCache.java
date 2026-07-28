package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author anadal (u80067)
 * 10 jul 2026 13:49:37
 */
public class NebulaCache {

    // ----------------------------------------------------
    // ----------------------------------------------------
    // ------------------ CACHE -------------------
    // ----------------------------------------------------

    public static Map<String, NebulaCacheInfo> cacheByNif = new HashMap<String, NebulaCacheInfo>();

    protected static Map<String, NebulaCacheSessionInfo> sessionInfoBySignaturesSetID = new java.util.HashMap<>();

    public static boolean testing = false;

    public static NebulaCacheInfo getCacheInfo(String nif) {
        return cacheByNif.get(nif);
    }

    public static void putCacheInfo(String nif, NebulaCacheInfo cacheInfo) {
        cacheByNif.put(nif, cacheInfo);
    }

    public static void clear() {
        cacheByNif.clear();
        sessionInfoBySignaturesSetID.clear();
    }

    public static void putNebulaCacheSessionInfo(String signaturesSetID, NebulaCacheSessionInfo sessionInfo) {
        sessionInfoBySignaturesSetID.put(signaturesSetID, sessionInfo);
    }

    public static void putNebulaCacheSessionInfo(String signaturesSetID, List<NebulaAuthenticatorType> authenticators) {
        // Si només hi ha un autenticador, el seleccionem automàticament
        NebulaCacheSessionInfo ncsi;
        if (authenticators != null && authenticators.size() == 1) {
            ncsi = new NebulaCacheSessionInfo(authenticators.get(0));
        } else {
            ncsi = new NebulaCacheSessionInfo();
        }
        sessionInfoBySignaturesSetID.put(signaturesSetID, ncsi);
    }

    public static NebulaCacheSessionInfo getNebulaCacheSession(String signaturesSetID) {
        NebulaCacheSessionInfo ncsi = sessionInfoBySignaturesSetID.get(signaturesSetID);
        return ncsi;
    }

    public static void setSelectedAuthenticator(String signaturesSetID, NebulaAuthenticatorType selectedAuthenticator) {
        NebulaCacheSessionInfo ncsi = sessionInfoBySignaturesSetID.get(signaturesSetID);
        if (ncsi == null) {
            ncsi = new NebulaCacheSessionInfo();
            sessionInfoBySignaturesSetID.put(signaturesSetID, ncsi);
        }

        ncsi.setSelectedAuthenticator(selectedAuthenticator);
    }
    /*
    public static String getSelectedCertificateID(String signaturesSetID) {
        NebulaCacheSessionInfo ncsi = sessionInfoBySignaturesSetID.get(signaturesSetID);
        if (ncsi == null) {
            return null;
        }
        
        return ncsi.getSelectedCertificateID();
    }
    
    public static void setSelectedCertificateID(String signaturesSetID, String selectedCertificateID) {
        NebulaCacheSessionInfo ncsi = sessionInfoBySignaturesSetID.get(signaturesSetID);
        if (ncsi == null) {
            ncsi = new NebulaCacheSessionInfo();
            sessionInfoBySignaturesSetID.put(signaturesSetID, ncsi);
        }
        
        ncsi.setSelectedCertificateID(selectedCertificateID);
    }
    
    public static String getPin(String signaturesSetID) {
    
        NebulaCacheSessionInfo ncsi = sessionInfoBySignaturesSetID.get(signaturesSetID);
        if (ncsi == null) {
            return null;
        }
        
        return ncsi.getPin();
    }
    
    public static void setPin(String signaturesSetID, String pin) {
        
        NebulaCacheSessionInfo ncsi = sessionInfoBySignaturesSetID.get(signaturesSetID);
        if (ncsi == null) {
            ncsi = new NebulaCacheSessionInfo();
            sessionInfoBySignaturesSetID.put(signaturesSetID, ncsi);
        }
        
        ncsi.setPin(pin);
    }
    */

    public static void cleanSessionInfo(String signaturesSetID) {
        sessionInfoBySignaturesSetID.remove(signaturesSetID);
    }

}

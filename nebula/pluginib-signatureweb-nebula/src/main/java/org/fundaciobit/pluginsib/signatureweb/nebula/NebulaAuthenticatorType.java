package org.fundaciobit.pluginsib.signatureweb.nebula;

/**
 * 
 * @author anadal (u80067)
 * 10 jul 2026 12:46:45
 */
public enum NebulaAuthenticatorType {

    ONE_TIME_PASSWORD_OTP("ATST"), MOBILE_SMS("SMS"), DIRECT_ACCESS("UPLDAP");

    String nebulaName;

    NebulaAuthenticatorType(String name) {
        this.nebulaName = name;
    }

    public String getNebulaName() {
        return nebulaName;
    }

    public static NebulaAuthenticatorType fromName(String name) {
        for (NebulaAuthenticatorType type : NebulaAuthenticatorType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
    
    
    public static NebulaAuthenticatorType fromNebulaName(String name) {
        for (NebulaAuthenticatorType type : NebulaAuthenticatorType.values()) {
            if (type.getNebulaName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}

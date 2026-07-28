package org.fundaciobit.pluginsib.signatureweb.nebula;

import org.fundaciobit.vintegris.nebula.api.client.authentication.v1.model.TokenWithChallengeVierModel;

/**
 * Els camps següents són de sessió, i s'han de netejar entre diferents sessions de firma
 * 
 * @author anadal (u80067)
 * 10 jul 2026 13:19:18
 */
public class NebulaCacheSessionInfo {

    protected NebulaAuthenticatorType selectedAuthenticator = null;

    protected TokenWithChallengeVierModel tokenWithChallengeVierModel = null;

    protected String token2;

    public NebulaCacheSessionInfo() {
        super();
    }

    public NebulaCacheSessionInfo(NebulaAuthenticatorType selectedAuthenticator) {
        super();
        this.selectedAuthenticator = selectedAuthenticator;
    }

    public NebulaAuthenticatorType getSelectedAuthenticator() {
        return selectedAuthenticator;
    }

    public void setSelectedAuthenticator(NebulaAuthenticatorType selectedAuthenticator) {
        this.selectedAuthenticator = selectedAuthenticator;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public TokenWithChallengeVierModel getTokenWithChallengeVierModel() {
        return tokenWithChallengeVierModel;
    }

    public void setTokenWithChallengeVierModel(TokenWithChallengeVierModel tokenWithChallengeVierModel) {
        this.tokenWithChallengeVierModel = tokenWithChallengeVierModel;
    }

}

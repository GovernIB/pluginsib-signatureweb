package org.fundaciobit.pluginsib.signatureweb.nebula;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.fundaciobit.pluginsib.signatureweb.api.ISignatureWebPlugin;
import org.fundaciobit.pluginsib.signatureweb.api.test.AbstractTestSignatureWeb;
import org.fundaciobit.vintegris.nebula.api.client.digitalcertificate.v1.model.GetMyCertificates200ResponseCertificatesListInner;
import org.junit.Test;

/**
 * 
 * @author anadal
 * 16 dic 2025 8:03:13
 */
public class NebulaTester extends AbstractTestSignatureWeb {

    public NebulaTester() throws Exception {
        super();
    }

    @Override
    protected File getPluginPropertiesFile() {
        return new File("nebula.properties");
    }

    @Override
    protected File getTestFile() {
        return new File("test.properties");
    }

    @Override
    protected Class<? extends ISignatureWebPlugin> getPluginClass() {
        return NebulaSignatureWebPlugin.class;
    }

    public static void main(String[] args) {
        try {

            NebulaTester nebulaTest = new NebulaTester();

            nebulaTest.testCertificatesOfUser();
            nebulaTest.testListAutenticatorMethods();

            nebulaTest.testPadesBasicSignature();
            nebulaTest.testPadesTimestampSignature();

            nebulaTest.testXadesAttachedEnvelopedBasicSignature();
            nebulaTest.testXadesAttachedEnvelopedTimestampSignature();
            nebulaTest.testXadesAttachedEnvelopingBasicSignature();
            nebulaTest.testXadesAttachedEnvelopingTimestampSignature();
            nebulaTest.testXadesDetachedBasicSignature();
            nebulaTest.testXadesDetachedTimestampSignature();

            nebulaTest.testCadesAttachedEnvelopingBasicSignature();
            nebulaTest.testCadesAttachedEnvelopingTimestampSignature();
            nebulaTest.testCadesDetachedBasicSignature();
            nebulaTest.testCadesDetachedTimestampSignature();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @Test
    protected void testCertificatesOfUser() throws Exception {

        String nif = getNifFromProperties();

        ISignatureWebPlugin plugin = this.getPlugin();

        NebulaSignatureWebPlugin nebulaPlugin = (NebulaSignatureWebPlugin) plugin;

        List<GetMyCertificates200ResponseCertificatesListInner> certs = nebulaPlugin.getCertificatesOfUser(nif);

        System.out.println("\n\n#Certs: " + certs.size() + "\n\n");

        for (GetMyCertificates200ResponseCertificatesListInner cert : certs) {
            System.out.println("Cert: " + cert);
        }
    }

    @Test
    protected void testListAutenticatorMethods() throws Exception {

        String nif = getNifFromProperties();

        ISignatureWebPlugin plugin = this.getPlugin();

        NebulaSignatureWebPlugin nebulaPlugin = (NebulaSignatureWebPlugin) plugin;

        List<NebulaAuthenticatorType> types = nebulaPlugin.getAllAuthenticationMethods(nif);

        for (NebulaAuthenticatorType type : types) {
            System.out.println("Authenticator: " + type + " (" + type.getNebulaName() + ")");
        }

    }

    protected String getNifFromProperties() throws IOException {
        Properties test = new Properties();
        test.load(new FileReader("test.properties"));
        String nif = test.getProperty("nif");
        return nif;
    }

}

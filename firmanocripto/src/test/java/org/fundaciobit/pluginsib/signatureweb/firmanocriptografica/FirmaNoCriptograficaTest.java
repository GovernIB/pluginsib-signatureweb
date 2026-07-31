package org.fundaciobit.pluginsib.signatureweb.firmanocriptografica;

import java.io.File;
import org.fundaciobit.pluginsib.signatureweb.api.ISignatureWebPlugin;
import org.fundaciobit.pluginsib.signatureweb.api.test.AbstractTestSignatureWeb;

/**
 * 
 * @author anadal (u80067)
 * 31 jul 2026 11:14:09
 */
public class FirmaNoCriptograficaTest extends AbstractTestSignatureWeb {

    public static void main(String[] args) {

        try {
            FirmaNoCriptograficaTest firmaNoCriptograficaTest = new FirmaNoCriptograficaTest();
            firmaNoCriptograficaTest.testPadesBasicSignature();
            System.out.println("FINAL PROCESS DE FIRMA OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the test case
     */
    public FirmaNoCriptograficaTest() throws Exception {
        super();
    }

    @Override
    protected File getPluginPropertiesFile() {
        return new File("plugin.properties");
    }

    @Override
    protected File getTestFile() {
        return new File("test.properties");
    }

    @Override
    protected Class<? extends ISignatureWebPlugin> getPluginClass() {
        return FirmaNoCriptograficaSignatureWebPlugin.class;

    }

    @Override
    protected String getPropertyBase() {
        return "es.caib.portafib.";
    }

}

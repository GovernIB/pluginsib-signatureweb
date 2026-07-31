package org.fundaciobit.pluginsib.signatureweb.firmanocriptografica;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

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
     *
     */
    public FirmaNoCriptograficaTest() throws Exception {
        super();
    }

    @Override
    protected File getPluginPropertiesFile() {
        // TODO Auto-generated method stub
        return new File("plugin.properties");
    }

    @Override
    protected File getTestFile() {
        return new File("test.properties");
    }

    @Override
    protected Class<? extends ISignatureWebPlugin> getPluginClass() {
        // TODO Auto-generated method stub
        return FirmaNoCriptograficaSignatureWebPlugin.class;

    }

    // Sobreescriure getPropertyBase()
    @Override
    protected String getPropertyBase() {
        return "es.caib.portafib.";
    }
   
    @Override
    
    protected File getResourceFile(String resourceName)  {
        
        
       
        // Cerca el recurs al classpath
        URL url;
        {
             url = getClass().getClassLoader().getResource(resourceName);
            if (url == null) {
                throw new RuntimeException("No s'ha trobat el recurs al classpath: " + resourceName);
            }
            
            System.out.println("Recurs trobat al classpath: " + resourceName + " -> " + url);
            
            // Converteix la URL en un File
            //return new File(url.toURI());
        }
        
        
        System.out.println("Recurs trobat al classpath 2222: " + resourceName + " -> " + url);
        
         //URL url = getClass().getResource(resourceName);
        
       // URL url = getClass().getClassLoader().getResource(resourceName);

        try {
            return new File(url.toURI());
        } catch (URISyntaxException | IllegalArgumentException e) {
            // El recurs està dins d'un JAR (URI no jeràrquic tipus jar:file:...!/...)
            try (InputStream is = url.openStream()) {
                if (is == null) {
                    throw new RuntimeException("Recurs no trobat: " + resourceName);
                }
                String fileName = new File(resourceName).getName();
                File tempFile = File.createTempFile("res-", "-" + fileName);
                tempFile.deleteOnExit();
                try (OutputStream os = new FileOutputStream(tempFile)) {
                    is.transferTo(os);
                }
                tempFile.deleteOnExit();
                return tempFile;
            } catch (IOException ex) {
                throw new RuntimeException("Error al crear el fitxer temporal per al recurs: " + resourceName, ex);
            }
        }
    }
    

}

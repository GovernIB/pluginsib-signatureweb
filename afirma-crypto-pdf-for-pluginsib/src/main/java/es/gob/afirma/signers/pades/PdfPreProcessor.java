/* Copyright (C) 2011 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation;
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * You may contact the copyright holder at: soporte.afirma@seap.minhap.es
 */

package es.gob.afirma.signers.pades;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.aowagie.text.Annotation;
import com.aowagie.text.DocumentException;
import com.aowagie.text.Image;
import com.aowagie.text.Jpeg;
import com.aowagie.text.Rectangle;
import com.aowagie.text.pdf.PdfContentByte;
import com.aowagie.text.pdf.PdfReader;
import com.aowagie.text.pdf.PdfStamper;

import es.gob.afirma.core.misc.Base64;

//----------------------------------
//---- PORTAFIB: NOUS IMPORTS ------
//----------------------------------

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLContext;

import es.gob.afirma.core.misc.AOUtil;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/** Utilidades para el manejo y modificaci&oacute;n de PDF antes de firmarlo.
 * @author Tom&aacute;s Garc&iacute;a-Mer&aacute;s */
public final class PdfPreProcessor {

    private static final Logger LOGGER = Logger.getLogger("es.gob.afirma");  //$NON-NLS-1$

    private static final int LAST_PAGE = -1;
    private static final int ALL_PAGES = 0;
    private static final int FIRST_PAGE = 1;

	private PdfPreProcessor() {
		// No permitimos la instancacion
	}

	/** A&ntilde;ade campos adicionales al diccionario PDF.
	 * @param moreInfo Campos a a&ntilde;adir al diccionario PDF
	 * @param stp Estampador de PDF, debe abrirse y cerrarse fuera de este m&eacute;todo */
	public static void addMoreInfo(final Map<String, String> moreInfo, final PdfStamper stp) {
		if (moreInfo == null || moreInfo.isEmpty()) {
			return;
		}
		stp.setMoreInfo(moreInfo);
	}

	static void attachFile(final Properties extraParams, final PdfStamper stp) throws IOException {
		if (extraParams == null) {
			return;
		}
		if (stp == null) {
			throw new IllegalArgumentException("No se puede adjuntar un fichero a un PdfStamper nulo"); //$NON-NLS-1$
		}
		// Contenido a adjuntar (en Base64)
		final String b64Attachment = extraParams.getProperty(PdfExtraParams.ATTACH);

		// Nombre que se pondra al fichero adjunto en el PDF
		final String attachmentFileName = extraParams.getProperty(PdfExtraParams.ATTACH_FILENAME);

		// Descripcion del adjunto
		final String attachmentDescription = extraParams.getProperty(PdfExtraParams.ATTACH_DESCRIPTION);

		if (b64Attachment != null && attachmentFileName != null) {
			final byte[] attachment;
			try {
				attachment = Base64.decode(b64Attachment);
			}
			catch(final IOException e) {
				LOGGER.warning("Se ha indicado un adjunto, pero no estaba en formato Base64, se ignorara : " + e); //$NON-NLS-1$
				return;
			}
			stp.getWriter().addFileAttachment(attachmentDescription, attachment, null, attachmentFileName);
		}

	}

	/** Sobreimpone una imagen JPEG en un documento PDF.
	 * @param jpegImage Imagen JPEG
	 * @param width Ancho de la imagen
	 * @param height Alto de la imagen
	 * @param left Distancia de la imagen al borde izquiero de la p&aacute;gina del PDF
	 * @param bottom Distancia de la imagen al borde inferior de la p&aacute;gina del PDF
	 * @param pageNum N&uacute;mero de p&aacute;gina del PDF donde insertar la imagen
	 *                (la numeraci&oacute;n comienza en 1)
	 * @param url URL a la que enlazar&aacute; la imagen si queremos que esta sea un hiperv&iacute;nculo
	 *            (puede ser <code>null</code>)
	 * @param stp Estampador PDF de iText
	 * @throws IOException En caso de errores de entrada / salida */
	public static void addImage(final byte[] jpegImage,
			                     final int width,
			                     final int height,
			                     final int left,
			                     final int bottom,
			                     final int pageNum,
			                     final String url,
			                     final PdfStamper stp) throws IOException {
		final PdfContentByte content = stp.getOverContent(pageNum);
		try {
			final Image image = new Jpeg(jpegImage);
			if (url != null) {
				image.setAnnotation(new Annotation(0, 0, 0, 0, url));
			}
			content.addImage(
				image,  // Image
				width,  // Image width
				0,
				0,
				height, // Image height
				left,   // Lower left X position of the image
				bottom, // Lower left Y position of the image
				false   // Inline
			);
		}
		catch (final DocumentException e) {
			throw new IOException("Error durante la insercion de la imagen en el PDF: " + e, e); //$NON-NLS-1$
		}
	}

	/** Sobreimpone una imagen en un documento PDF.
	 * @param extraParams Datos de la imagen a a&ntilde;adir como <a href="doc-files/extraparams.html">par&aacute;metros
	 *                    adicionales</a>.
	 * @param stp Estampador de PDF, debe abrirse y cerrarse fuera de este m&eacute;todo.
	 * @param pdfReader Lector PDF, para obtener el n&uacute;mero de p&aacute;ginas del documento.
	 * @throws IOException Cuando ocurren errores de entrada / salida. */
	static void addImage(final Properties extraParams, final PdfStamper stp, final PdfReader pdfReader) throws IOException {

		if (extraParams == null || stp == null) {
			return;
		}

		final String imageDataBase64 = extraParams.getProperty(PdfExtraParams.IMAGE);
		if (imageDataBase64 == null || imageDataBase64.length() < 1) {
			return;
		}
		final byte[] image = Base64.decode(imageDataBase64);

		final Rectangle rect = PdfUtil.getPositionOnPage(extraParams, PdfExtraParams.IMAGE);

		if (rect == null) {
			return;
		}

		final String imagePage = extraParams.getProperty(PdfExtraParams.IMAGE_PAGE);
		if (imagePage == null) {
			return;
		}

		int pageNum;
		try {
			pageNum = Integer.parseInt(imagePage.trim());
		}
		catch(final NumberFormatException e) {
			throw new IOException(
				"Se ha indicado un numero de pagina con formato invalido para insertar la imagen (" + imagePage + "): " + e, e //$NON-NLS-1$ //$NON-NLS-2$
			);
		}

		if (pageNum == LAST_PAGE) {
			pageNum = pdfReader.getNumberOfPages();
		}
		final int pageLimit;
		if (pageNum == ALL_PAGES) {
			pageNum = FIRST_PAGE;
			pageLimit = pdfReader.getNumberOfPages();
		}
		else {
			pageLimit = pageNum;
		}

		for (int i= pageNum; i<=pageLimit; i++) {
			addImage(
				image,
				(int) rect.getWidth(),
				(int) rect.getHeight(),
				(int) rect.getLeft(),
				(int) rect.getBottom(),
				i,
				null,
				stp
			);
		}

		LOGGER.info("Anadida imagen al PDF antes de la firma"); //$NON-NLS-1$
	}
	
	//----------------------------------
//--- PORTAFIB: CODI ELIMINAT ------
//----------------------------------
/*
    static com.aowagie.text.Image getImage(final String imagebase64Encoded) {
    	if (imagebase64Encoded == null || imagebase64Encoded.isEmpty()) {
    		return null;
    	}
    	final byte[] image;
    	try {
			image = Base64.decode(imagebase64Encoded);
		}
    	catch (final Exception e) {
    		LOGGER.severe("Se ha proporcionado una imagen de rubrica que no esta codificada en Base64: " + e); //$NON-NLS-1$
			return null;
		}
    	try {
			return new Jpeg(image);
		}
    	catch (final Exception e) {
    		LOGGER.info("Se ha proporcionado una imagen de rubrica que no esta codificada en JPEG: " + e); //$NON-NLS-1$
		}
    	return null;
    }
    */
    
    
//----------------------------------
//------ PORTAFIB: NOU CODI  -------
//----------------------------------
// 2018-09-28 Llançar excepcions si alguna cosa no va bé 

public static Image getImage(final String imagebase64Encoded, X509Certificate certificate)
    throws IOException {

  if (imagebase64Encoded == null || "".equals(imagebase64Encoded))  { //$NON-NLS-1$
    return null;
  }
  final byte[] image;
  
  if (imagebase64Encoded.startsWith("http:") || imagebase64Encoded.startsWith("https:")) {
    final String url = imagebase64Encoded;
    try {
      image = downloadImage(url, certificate.getEncoded());
    } catch (final Exception e) {
      String msg = "Se ha producido un error durante la descarga de la imagen de rubrica"
          + " desde la URL " + url + ": " + e.getMessage(); 
      LOGGER.severe(msg); //$NON-NLS-1$
      // TODO Aqui hay que lanzar una excepci�n !!!!
      // No podemos firmar con una rubrica incorrecta !!!!!
      throw new IOException(msg);
    }
    
  } else {
    try {
      image = Base64.decode(imagebase64Encoded);
    } catch (final Exception e) {
      String msg = "Se ha proporcionado una imagen de rubrica que no esta codificada en Base64: " + e; 
      LOGGER.severe(msg); //$NON-NLS-1$
      throw new IOException(msg);
    }
  }
  
  
  try {
    return new Jpeg(image);
  } catch (final Exception e) {
    String msg = "Se ha proporcionado una imagen de rubrica que no esta codificada en JPEG: " + e; 
    LOGGER.severe(msg); //$NON-NLS-1$
    throw new IOException(msg);
  }
  
}


static byte[] downloadImage(String url, byte[] certificate) throws Exception  {
  String charset = "UTF-8";

  
  String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
  String CRLF = "\r\n"; // Line separator required by multipart/form-data.

  URLConnection connection = new URL(url).openConnection();
  connection.setDoOutput(true);
  connection.setDoInput(true);
  connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

  
  if (url.toLowerCase().startsWith("https")) {
    // IGNORAR CERTIFICATS DE SERVIDOR
    
    // Create a trust manager that does not validate certificate chains 
    final TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() { 
          @Override public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
          } 

          @Override public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
          } 
          
          @Override public X509Certificate[] getAcceptedIssuers() {
            return null; 
          } 
        } 
      };
    
    
      //Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance( "SSL" );
      sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
      //Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    
    
      //All set up, we can get a resource through https now:
      //Tell the url connection object to use our socket factory which bypasses security checks
      ( (HttpsURLConnection) connection ).setSSLSocketFactory( sslSocketFactory );
      
      
      ( (HttpsURLConnection) connection ).setHostnameVerifier(
          new javax.net.ssl.HostnameVerifier(){

                  @Override
              public boolean verify(String hostname,
                      javax.net.ssl.SSLSession sslSession) {
                  return true;
              }
          });
      
  }
  
  OutputStream output = connection.getOutputStream();
  PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);


  // Send binary file.
  writer.append("--" + boundary).append(CRLF);
  writer.append("Content-Disposition: form-data; name=\"certificateFile\"; filename=\"certificate.cer\"").append(CRLF);
  writer.append("Content-Type: application/x-509-user-cert").append(CRLF);
  writer.append("Content-Transfer-Encoding: binary").append(CRLF);
  writer.append(CRLF).flush();
  output.write(certificate);
  output.flush(); // Important before continuing with writer!
  writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

  // End of multipart/form-data.
  writer.append("--" + boundary + "--").append(CRLF).flush();


  // Request is lazily fired whenever you need to obtain information about response.
    
  int responseCode = ((HttpURLConnection) connection).getResponseCode();
  
  if (responseCode != 200) {
    throw new Exception("Retornado codigo de error " + responseCode 
        + " durante la descarga de la imagen.");
  }
  
  InputStream is = connection.getInputStream();
  
  return AOUtil.getDataFromInputStream(is);
  
  
}


}

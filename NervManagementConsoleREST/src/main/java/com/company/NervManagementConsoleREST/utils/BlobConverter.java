package com.company.NervManagementConsoleREST.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;

public class BlobConverter {
	
	// Converte un Blob in una stringa Base64
	public String blobToBase64(Blob blob) {
	    if (blob != null) {
	        try (InputStream inputStream = blob.getBinaryStream();
	             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
	            
	            byte[] buffer = new byte[8192];  // Usa un buffer di dimensione più piccola
	            int bytesRead;
	            
	            // Leggi il flusso di byte e scrivili nel ByteArrayOutputStream
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	            	byteArrayOutputStream.write(buffer, 0, bytesRead);
	            }

	            // Converte in Base64
	            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
    // Converte una stringa Base64 in un Blob
    public Blob base64ToBlob(String base64String) {
        if (base64String != null && !base64String.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(base64String);  // Decodifica la stringa Base64
                return new SerialBlob(decodedBytes);  // Converte i byte in Blob
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
}


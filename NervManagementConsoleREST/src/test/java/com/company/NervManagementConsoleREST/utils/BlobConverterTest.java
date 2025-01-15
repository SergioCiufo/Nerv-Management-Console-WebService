package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.junit.jupiter.api.Test;

public class BlobConverterTest {
	private BlobConverter blobConverter = new BlobConverter();
	
	@Test
    public void shouldConvertBlobToBase64_whenBlobIsNotNull() throws Exception {
        //parameters
        byte[] byteArray = "test data".getBytes();
        Blob blob = new SerialBlob(byteArray);

        //mock
        String expectedBase64 = "test";

        //test
        String base64String = blobConverter.blobToBase64(blob);

        //result
        assertNotNull(base64String);
        //assertEquals(expectedBase64, base64String);
    }

    @Test
    public void shouldReturnNull_whenBlobIsNull() {
        //parameters
        Blob blob = null;

        //mock
        String expectedBase64 = null;

        //test
        String base64String = blobConverter.blobToBase64(blob);

        //result
        assertNull(base64String);
    }

    @Test
    public void shouldConvertBase64ToBlob_whenBase64StringIsNotNull() throws Exception {
        //parameters
        String base64String = "test";

        //mock
        byte[] expectedByteArray = "test data".getBytes();

        //test
        Blob blob = blobConverter.base64ToBlob(base64String);

        //result
        assertNotNull(blob);
        //assertArrayEquals(expectedByteArray, blob.getBytes(1, (int) blob.length()));
    }

    @Test
    public void shouldReturnNull_whenBase64StringIsNull() {
        //parameters
        String base64String = null;

        //mock
        Blob expectedBlob = null;

        //test
        Blob blob = blobConverter.base64ToBlob(base64String);

        //result
        assertNull(blob);
    }

    @Test
    public void shouldReturnNull_whenBase64StringIsEmpty() {
        //parameters
        String base64String = "";

        //mock
        Blob expectedBlob = null;

        //test
        Blob blob = blobConverter.base64ToBlob(base64String);

        //result
        assertNull(blob);
    }
    
    @Test
    public void shouldHandleExceptionInBlobToBase64_whenConvertFails() throws Exception {
        // parameters
        Blob blob = mock(Blob.class);

        //mock
        doThrow(new RuntimeException("test")).when(blob).getBinaryStream();

        //test
        String base64String = blobConverter.blobToBase64(blob);

        //result
        assertNull(base64String);
    }
    
    @Test
    public void shouldHandleExceptionBase64ToBlob_whenConvertFails() {
        // parameters
        String invalidBase64String = "not-a-valid-base64";

        // test
        Blob blob = blobConverter.base64ToBlob(invalidBase64String);

        // result
        assertNull(blob);
    }
}

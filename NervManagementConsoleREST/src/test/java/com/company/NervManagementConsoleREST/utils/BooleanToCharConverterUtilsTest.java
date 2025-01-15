package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BooleanToCharConverterUtilsTest {
	private BooleanToCharConverterUtils booleanToCharConverterUtils = new BooleanToCharConverterUtils();
	
	@Test
    public void shouldReturnY_whenBooleanIsTrue() {
        //test
        String result = booleanToCharConverterUtils.convertToDatabaseColumn(true);
        
        //result
        assertEquals("Y", result);
    }
    
    @Test
    public void shouldReturnN_whenBooleanIsFalse() {
        //test
        String result = booleanToCharConverterUtils.convertToDatabaseColumn(false);
        
        //result
        assertEquals("N", result);
    }
    
    @Test
    public void shouldReturnN_whenBooleanIsNull() {
        //test
        String result = booleanToCharConverterUtils.convertToDatabaseColumn(null);
        
        //result
        assertEquals("N", result);
    }
    
    @Test
    public void shouldReturnTrue_whenDbDataIsY() {
        //test
        Boolean result = booleanToCharConverterUtils.convertToEntityAttribute("Y");
        
        //result
        assertTrue(result);
    }
    
    @Test
    public void shouldReturnFalse_whenDbDataIsN() {
        //test
        Boolean result = booleanToCharConverterUtils.convertToEntityAttribute("N");
        
        //result
        assertFalse(result);
    }
    
    @Test
    public void shouldReturnFalse_whenDbDataIsNull() {
        //test
        Boolean result = booleanToCharConverterUtils.convertToEntityAttribute(null);
        
        //result
        assertFalse(result);
    }
}

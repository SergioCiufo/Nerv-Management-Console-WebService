package com.company.NervManagementConsoleREST.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class LocalDateAdapterUtilsTest {

    private final LocalDateAdapterUtils adapter = new LocalDateAdapterUtils();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Test
    public void shouldUnmarshal_whenValidDateString() throws Exception {
        //parameter
        String dateString = "2025-01-15";

        //test
        LocalDate result = adapter.unmarshal(dateString);

        //result
        assertNotNull(result);
        assertEquals(LocalDate.parse(dateString, formatter), result);
    }

    @Test
    public void shouldMarshal_whenValidLocalDate() throws Exception {
        //parameter
        LocalDate localDate = LocalDate.of(2025, 1, 15);


        //test
        String result = adapter.marshal(localDate);

        //result
        assertNotNull(result);
        assertEquals(localDate.format(formatter), result);
    }

    @Test
    public void shouldThrowException_whenInvalidDateString() {
        //parameter
        String invalidDateString = "invalid-date";

        //test & result
        assertThrows(Exception.class, () -> adapter.unmarshal(invalidDateString));
    }

    @Test
    public void shouldThrowException_whenNullLocalDate() {
        //parameter
        LocalDate nullDate = null;

        //test & result
        assertThrows(NullPointerException.class, () -> adapter.marshal(nullDate));
    }
}

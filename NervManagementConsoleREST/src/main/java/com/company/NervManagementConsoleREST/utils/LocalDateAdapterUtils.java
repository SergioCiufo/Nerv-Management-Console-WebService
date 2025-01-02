package com.company.NervManagementConsoleREST.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//JAXB non supporta nativamente LocalDate, 
//quindi è necessario un XmlAdapter per serializzarlo correttamente come stringa (yyyy-MM-dd) in XML.
public class LocalDateAdapterUtils extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, formatter);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.format(formatter);
    }
}
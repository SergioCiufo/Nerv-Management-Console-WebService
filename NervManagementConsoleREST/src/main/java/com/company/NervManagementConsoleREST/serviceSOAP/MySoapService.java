package com.company.NervManagementConsoleREST.serviceSOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
//http://localhost:8080/NervManagementConsoleREST/soap/MySoapService?wsdl
//webService: indica che questa classe è un servizio web SOAP
@WebService
public class MySoapService {
	
	//espone il meotodo come operazione SOAP
    @WebMethod(operationName = "TestHello")
    public String sayHello(@WebParam(name="guestname") String guestName) {
    	if(guestName == null || guestName.trim().isEmpty()) {
			return "Hello Stranger!";
		}
    	return "Hello, " + guestName + "!";
    }
}
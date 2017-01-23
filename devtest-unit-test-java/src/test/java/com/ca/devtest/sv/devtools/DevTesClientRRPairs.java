package com.ca.devtest.sv.devtools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.ca.devtest.sv.devtools.application.DoItClient;
import com.ca.devtest.sv.devtools.application.SoapClient;
import com.ca.devtest.sv.devtools.protocol.builder.DataProtocolBuilder;
import com.ca.devtest.sv.devtools.services.VirtualService;
import com.ca.devtest.sv.devtools.type.DataProtocolType;

public class DevTesClientRRPairs {

	DevTestClient devTestClient = new DevTestClient("localhost", "VSE", "svpower", "svpower", "DevTesClientRRPairs");
	DoItClient clientDoIt = new DoItClient("localhost", "9001");

	@Test
	public void createService() {

		try {
			URL url = getClass().getClassLoader().getResource("rrpairs/doit");
			File rrPairsFolder = new File(url.toURI());
			VirtualService service = devTestClient.fromRRPairs("DoItSample", rrPairsFolder).overHttp(9001, "/cgi-bin/GatewayJavaDoIt.cgi")
					.addRequestDataProtocol(new DataProtocolBuilder(DataProtocolType.DOIT.getType()).build())
					.addRespondDataProtocol(new DataProtocolBuilder(DataProtocolType.DOIT.getType()).build()).build();
			service.deploy();

			String result = clientDoIt.callDoItService();
			System.out.println(result);
			service.unDeploy();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void createSoapService() throws IOException, URISyntaxException {
		int port=9001;
		String path= "/lisa";
		
		/* Create Virtual Service */
		URL url = getClass().getClassLoader().getResource("rrpairs/soap");
		File rrPairsFolder = new File(url.toURI());
		VirtualService service = devTestClient.fromRRPairs("lisaBankTU", rrPairsFolder)
				.overHttp(port, path)
				.addRequestDataProtocol(new DataProtocolBuilder(DataProtocolType.SOAP.getType()).build()).build();
		/* Deploy Virtual Service */
		service.deploy();
		
		/* Test */
			SoapClient soapclient= new SoapClient("localhost", String.valueOf(port));
			url =getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml");
			File requestFile= new File(getClass().getClassLoader().getResource("rrpairs/soap/getUser-req.xml").toURI());
			String request=FileUtils.readFileToString(requestFile, "UTF-8");
			String response=soapclient.callService(path, request);
		
		/* Deploy Virtual Service */
		service.unDeploy();

	}
	

}

/**
 * 
 */
package com.ca.devtest.sv.devtools;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ca.devtest.sv.devtools.protocol.TransportProtocolDefinition;
import com.ca.devtest.sv.devtools.protocol.builder.DataProtocolBuilder;
import com.ca.devtest.sv.devtools.protocol.builder.TransportProtocolBuilder;
import com.ca.devtest.sv.devtools.type.DataProtocolType;
import com.ca.devtest.sv.devtools.type.TransportProtocolType;

/**
 * @author gaspa03
 *
 */
public class TransportProtocolBuilderTest {

	@Test
	public void buildHttpDoit() {
	TransportProtocolDefinition tphDefinition=	new TransportProtocolBuilder(TransportProtocolType.HTTP.getType()).addParameter("listenPort", "8080").addParameter("basePath", "/cgi-bin/GatewayJavaDoIt.cgi").addParameter("targetHost", "localhost").addRequestDataProtocol(new DataProtocolBuilder(DataProtocolType.DOIT.getType())).addResponseDataProtocol(new DataProtocolBuilder(DataProtocolType.DOIT.getType())).build();
		System.out.println(tphDefinition.toVrsContent());
	}

}

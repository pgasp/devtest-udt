package com.ca.lisa.custom.dph.xml;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.itko.lisa.test.TestExec;
import com.itko.lisa.vse.stateful.model.Request;
import com.itko.lisa.vse.stateful.protocol.DataProtocol;
import com.itko.lisa.vse.stateful.protocol.xml.XMLRequestParser;

public class XMLwithAttributesToArguments extends DataProtocol {

	private static final Log LOGGER = LogFactory.getLog(XMLwithAttributesToArguments.class);

	public XMLwithAttributesToArguments() {
		super();
	}

	/*
	 * transform request when recording
	 */
	@Override
	public void updateRequest(Request request) {

		String requestBody = request.getBodyAsString();
		if (   (requestBody == null)
		    || (requestBody.trim().isEmpty()) ) {
			return;
		}

		boolean toArguments = true;
		XMLRequestParser parser = new XMLwithAttributesParser(request,toArguments);

		try {
			parser.parseXml().saveResultsToRequest();
		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new RuntimeException(ex.getMessage(), ex);
		} catch (SAXException ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}

	/*
	 * transform request when replay
	 */
	@Override
	public void updateRequest(TestExec testExec, Request request) {
		this.updateRequest(request);
	}
}

package com.ca.lisa.custom.dph.xml;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.itko.lisa.vse.stateful.model.Request;
import com.itko.lisa.vse.stateful.protocol.xml.XMLParserUtils;
import com.itko.lisa.vse.stateful.protocol.xml.XMLRequestParser;
import com.itko.util.ParameterList;
import com.itko.util.XMLUtils;

public class XMLwithAttributesParser extends XMLRequestParser {

	private XMLParserUtils parser;
	private boolean toArguments;

	public XMLwithAttributesParser(Request request, boolean toArguments) {
		super(request);
		this.toArguments = toArguments;
	}

	@Override
	protected void doParseXml() throws IOException, SAXException {
		Element rootNode = XMLUtils.openXMLDocFromStrBuffer(getXmlForParsing(), false).getDocumentElement();
		this.parser = new XMLParserUtils(rootNode);
		this.parser.parseNodesWithAttributesIntoArguments();
	}

	@Override
	public String getOperationNameFromXML() {
		return this.parser.getOperationNameFromParsedElement();
	}

	@Override
	public void saveResultsToRequest() {

		ParameterList parseArguments = this.parser.getArgumentWithAttrsParameterList();
		if (toArguments) {
			addNewArguments(getRequest(), parseArguments);
		} else {
			addNewAttributes(getRequest(), parseArguments);
		}

		ParameterList parseAttributes = this.parser.getAttributeParameterList();
		addNewAttributes(getRequest(), parseAttributes);
	}

	private static void addNewArguments(Request request, ParameterList arguments) {

		ParameterList requestArguments = ((request == null) ? null : request.getArguments());
		String[] argumentKeys = ((arguments == null) ? null : arguments.getKeyArray());

		if ((requestArguments != null) && (argumentKeys != null)) {
			ParameterList argumentsToAdd = new ParameterList();
			for (String argumentKey : argumentKeys) {
				if (StringUtils.contains(argumentKey, "@")) {
					if (! requestArguments.containsKey(argumentKey)) {
						String argumentValue = arguments.getParameterValue(argumentKey);
						argumentsToAdd.put(argumentKey, argumentValue);
					}
				}
			}
			requestArguments.addAll(argumentsToAdd);
		}
	}

	private static void addNewAttributes(Request request, ParameterList attributes) {

		ParameterList requestAttributes = ((request == null) ? null : request.getAttributes());
		String[] attributeKeys = ((attributes == null) ? null : attributes.getKeyArray());

		if ((requestAttributes != null) && (attributeKeys != null)) {
			ParameterList attributesToAdd = new ParameterList();
			for (String attributeKey : attributeKeys) {
				if (StringUtils.contains(attributeKey, "@")) {
					if (! requestAttributes.containsKey(attributeKey)) {
						String attributeValue = attributes.getParameterValue(attributeKey);
						attributesToAdd.put(attributeKey, attributeValue);
					}
				}
			}
			requestAttributes.addAll(attributesToAdd);
		}
	}
}

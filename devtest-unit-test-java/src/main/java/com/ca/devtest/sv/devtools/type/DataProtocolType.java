/**
 * 
 */
package com.ca.devtest.sv.devtools.type;

/**
 * @author gaspa03
 *
 */
public enum DataProtocolType {

	DOIT("com.personalfinance.devtest.dph.DoItDataHandler"),
	SOAP("com.itko.lisa.vse.stateful.protocol.ws.WSSOAPProtocolHandler");
	
	private String type="";
	
	DataProtocolType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
}

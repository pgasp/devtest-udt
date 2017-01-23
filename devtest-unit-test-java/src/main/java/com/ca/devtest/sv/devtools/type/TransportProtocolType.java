/**
 * 
 */
package com.ca.devtest.sv.devtools.type;

/**
 * @author gaspa03
 *
 */
public enum TransportProtocolType {

	HTTP("com.itko.lisa.vse.stateful.protocol.http.HttpProtocolHandler");
	
	private String type;
	
	TransportProtocolType(String type){
		this.type=type;
	}
	
	public String getType(){
		return type;
	}
}

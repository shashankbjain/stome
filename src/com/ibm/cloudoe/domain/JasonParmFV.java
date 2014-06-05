/**
 * 
 */
package com.ibm.cloudoe.domain;

/**
 * @author Asuresh
 *
 */
public enum JasonParmFV 
{
	ORDERNUMBER("orderNumber"),
	STORENAME("storeName"),
	ADDRESS1("address1"),
	CITY("city"),
	STATE("state"),
	POSTALCODE("postalCode"),
	WEIGHT("weight"),
	PICKER("picker"),
	STOMEAMOUNT("stomeAmount"),
	CONTACT("contact");
	
	private String _value;
	
	private JasonParmFV(String value)
	{
		_value = value;
	}
	
	public String value()
	{
	    return this._value;
	}
}

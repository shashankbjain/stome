/**
 * 
 */
package com.ibm.cloudoe.domain;

/**
 * @author Asuresh and Kalyan
 *
 */
public enum OrderStatusFV 
{
	AVAILABLE("Available"),
	READY("Ready"),
	PICKED("Picked"),
	DELIVERED("Delivered");
	
	private String _value;
	
	private OrderStatusFV(String value)
	{
		_value = value;
	}
	
	public String value()
	{
	    return this._value;
	}

}

/**
 * 
 */
package com.ibm.cloudoe.domain;

/**
 * @author Asuresh
 *
 */
public class Store 
{
	protected String storeName;
	protected Address address;
	
	public String getStoreName()
	{
		return this.storeName;
	}
	
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	
	public Address getAddress()
	{
		return this.address;
	}
	
	public void setAddress(Address address)
	{
		this.address = address;
	}
}

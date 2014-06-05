/**
 * 
 */
package com.ibm.cloudoe.domain;

import org.apache.commons.lang.StringUtils;;

/**
 * @author Asuresh
 *
 */
public class Address 
{
	protected String address1;
	protected String city;
	protected String state;
	protected String postalCode;
	protected String contact;
		
	public String getAddress1()
	{
		return this.address1;
	}
	
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
		
	public String getCity()
	{
		return this.city;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getState()
	{
		return this.state;
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	
	public String getPostalCode()
	{
		return this.postalCode;
	}
	
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}
	
	public String getContact()
	{
		return this.contact;
	}
	
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	
	public String toString()
	{
		return this.address1 
		+ (!StringUtils.isEmpty(this.city)?  (", " + this.city):"")
		+ (!StringUtils.isEmpty(this.state)?  (", " + this.state):"")
		+ (!StringUtils.isEmpty(this.postalCode)?  (" " + this.postalCode):"");
	}
}

/**
 * 
 */
package com.ibm.cloudoe.domain;

import com.ibm.cloudoe.domain.OrderStatusFV;
/**
 * @author Asuresh
 *
 */
public class Order 
{
	protected String orderNumber;
	protected Store store;
	protected String storeName;
	protected Address address;
	protected double weight;
	protected double stomeAmount;
	protected OrderStatusFV status;
	protected String picker;
	
	public void setOrderNumber(String orderNumber)
	{
		this.orderNumber = orderNumber;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setStore(Store store)
	{
		this.store = store;
	}
	
	public Store getStore()
	{
		return store;
	}
	
	public String getStoreName()
	{
		return this.storeName;
	}
	
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}
	
	public Address getAddress() {
		return this.address;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setStomeAmount(double amt)
	{
		this.stomeAmount = amt;
	}
	
	public double getStomeAmount() {
		return stomeAmount;
	}

	public void setStatus(OrderStatusFV status)
	{
		this.status = status;
	}
	
	public OrderStatusFV getStatus() {
		return status;
	}

	public String getPicker()
	{
		return picker;
	}
	
	public void setPicker(String picker)
	{
		this.picker = picker;
	}
	
	public String getDeliveryPostalCode()
	{
		return this.address.getPostalCode();
	}
	
	public String getDeliveryAddress()
	{
		return this.address.toString();
	}
	
	public String getStoreAddress()
	{
		return this.store.address.toString();
	}
	
	public String getPickUpConfirmationCode()
	{
		String confirmCodeString = picker + orderNumber;
		return Integer.toString(
				Math.abs(confirmCodeString.hashCode()));
	}
	
	public String getDeliveryConfirmationCOde()
	{
		return Integer.toString(
				Math.abs(this.orderNumber.hashCode()));
	}
}

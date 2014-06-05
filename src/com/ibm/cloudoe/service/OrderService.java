/**
 * 
 */
package com.ibm.cloudoe.service;


import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.ibm.cloudoe.domain.Order;
import com.ibm.cloudoe.domain.OrderStatusFV;
import java.util.ArrayList;


/**
 * @author Asuresh
 *
 */
public class OrderService 
{
	protected static ConcurrentHashMap<String, Order> orderMap 
		= StoreAndOrderLoader.orderMap;
	
	synchronized public List<Order> getAllOrders()
	{
		List<Order> orderList = new ArrayList<Order>();
		Iterator<Order> itr = orderMap.values().iterator();
		while(itr.hasNext())
		{
			Order o = itr.next();
			orderList.add(o);
		}
		
		return orderList;
	}
	
	
	synchronized public List<Order> getAllOrdersByPostalCode(
			String postalCode)
	{
		List<Order> orderList = new ArrayList<Order>();
		
		Iterator<Order> itr = orderMap.values().iterator();
		while(itr.hasNext())
		{
			Order o = itr.next();
			if(o.getDeliveryPostalCode().equals(postalCode))
				orderList.add(o);
		}
		return orderList;
	}
	
	synchronized public List<Order> getAllOrdersByPostalCodeAndStatus(
			String postalCode, OrderStatusFV orderStatus)
	{
		List<Order> orderList = new ArrayList<Order>();
		
		Iterator<Order> itr = orderMap.values().iterator();
		while(itr.hasNext())
		{
			Order o = itr.next();
			if(o.getDeliveryPostalCode().equals(postalCode)
					&& o.getStatus().equals(orderStatus))
				orderList.add(o);
		}
		return orderList;
	}
	
	synchronized public List<Order> processOrderPickupRequest(
			List<String> orderNumberList, String pickerName)
	{
		List<Order> pickupOrderList = new ArrayList<Order>();
		
		for(String tcOrderId : orderNumberList)
		{
			Order ord = orderMap.get(tcOrderId);
			if(ord.getStatus().equals(OrderStatusFV.AVAILABLE))
			{
				pickupOrderList.add(ord);
				ord.setStatus(OrderStatusFV.READY);
				ord.setPicker("pickerName");
			}
		}
		
		return pickupOrderList;
	}
	
	synchronized public boolean confirmOrderPickupRequest(
			String orderNumber, String pickerName, String pickupCode)
	{
		boolean pickUpSucessful = false;
		
		Order ord = orderMap.get(orderNumber);
		
		if( ord != null && pickupCode.equalsIgnoreCase(
				ord.getPickUpConfirmationCode()))
		{
			pickUpSucessful = true;
			ord.setStatus(OrderStatusFV.PICKED);
			PickupConfirmationService service = new PickupConfirmationService();
			service.notifyEndCustomer(
				ord.getAddress().getContact(), orderNumber, pickerName);
		}
		
		return pickUpSucessful;
	}
	
	synchronized public boolean deliveryConfirmationRequest(
			String orderNumber, String deliveryCode)
	{
		boolean deliverySucessful = false;
		Order ord = orderMap.get(orderNumber);
		if( ord != null && deliveryCode.equalsIgnoreCase(
				ord.getDeliveryConfirmationCOde()))
		{
			deliverySucessful = true;
			ord.setStatus(OrderStatusFV.DELIVERED);
			PickupConfirmationService service = new PickupConfirmationService();
			service.thankEndCustomer(
				ord.getAddress().getContact());
		}
		
		return deliverySucessful;
	}
}

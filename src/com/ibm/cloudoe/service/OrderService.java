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
		System.out.println(" OrderService:: getAllOrders  START");
		List<Order> orderList = new ArrayList<Order>();
		Iterator<Order> itr = orderMap.values().iterator();
		System.out.println(" OrderService:: getAllOrders  OrderMap Size " + orderMap.size());
		while(itr.hasNext())
		{
			Order o = itr.next();
			orderList.add(o);
			System.out.println(" OrderService:: getAllOrders  " + o.toString());		
		}
		
		return orderList;
	}
	
	
	synchronized public List<Order> getAllOrdersByPostalCode(
			String postalCode)
	{
		System.out.println(" OrderService:: getAllOrdersByPostalCode  START");		
		List<Order> orderList = new ArrayList<Order>();
		
		Iterator<Order> itr = orderMap.values().iterator();
		while(itr.hasNext())
		{
			Order o = itr.next();
			if(o.getDeliveryPostalCode().equals(postalCode))
				orderList.add(o);
			
			System.out.println(" OrderService:: getAllOrdersByPostalCode  Order Detail " + o.toString());
		}
		return orderList;
	}
	
	synchronized public List<Order> getAllOrdersByPostalCodeAndStatus(
			String postalCode, OrderStatusFV orderStatus)
	{
		System.out.println(" OrderService:: getAllOrdersByPostalCodeAndStatus  START");		
		List<Order> orderList = new ArrayList<Order>();
		
		Iterator<Order> itr = orderMap.values().iterator();
		while(itr.hasNext())
		{
			Order o = itr.next();
			if(o.getDeliveryPostalCode().equals(postalCode)
					&& o.getStatus().equals(orderStatus))
				orderList.add(o);
			
			System.out.println(" OrderService:: getAllOrdersByPostalCodeAndStatus  orders " + o.toString());
		}
		return orderList;
	}
	
	synchronized public List<Order> processOrderPickupRequest(
			List<String> orderNumberList, String pickerName)
	{
		System.out.println(" OrderService:: processOrderPickupRequest 	pickerName = " + pickerName);	
		List<Order> pickupOrderList = new ArrayList<Order>();
		
		for(String tcOrderId : orderNumberList)
		{
			Order ord = orderMap.get(tcOrderId);
			if(ord.getStatus().equals(OrderStatusFV.AVAILABLE))
			{
				System.out.println(" OrderService:: processOrderPickupRequest :: Order Status is available");				
				pickupOrderList.add(ord);
				ord.setStatus(OrderStatusFV.READY);
				ord.setPicker("pickerName");
			}
			System.out.println(" OrderService:: processOrderPickupRequest 	Order Detail = " + ord.toString());			
		}
		
		return pickupOrderList;
	}
	
	synchronized public List<Order> confirmOrderPickupRequest(
			List<String> orderNumberList, String pickerName, String pickupCode)
	{
		System.out.println(" OrderService:: confirmOrderPickupRequest");
		System.out.println("pickerName = " + pickerName);
		System.out.println("pickupCode = " + pickupCode);
		
		List<Order> orderList = new ArrayList<Order>();
		
		for(String orderNumber : orderNumberList)
		{
			Order ord = orderMap.get(orderNumber);
			System.out.println("Order = " + orderNumber);
			
			if( ord != null && pickupCode.equalsIgnoreCase(
				ord.getPickUpConfirmationCode()))
			{
				System.out.println("Order = " + orderNumber + " Picked.");
				orderList.add(ord);
				ord.setStatus(OrderStatusFV.PICKED);
				PickupConfirmationService service = new PickupConfirmationService();
				service.notifyEndCustomer(
				ord.getAddress().getContact(), orderNumber, pickerName);
			}
		}
		
		return orderList;
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

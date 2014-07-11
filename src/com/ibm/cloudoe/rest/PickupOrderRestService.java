/**
 * 
 */
package com.ibm.cloudoe.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import com.ibm.cloudoe.domain.Order;
import com.ibm.cloudoe.service.OrderService;
import com.stome.twitter.IStomeTwitterServices;
import com.stome.twitter.StomeTwitterServicesImpl;

/**
 * @author Asuresh
 *
 */
@Path("/pickupOrder")
public class PickupOrderRestService 
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String pickupOrder(@QueryParam("tcorderid") String tcorderid,
			@QueryParam("pickername") String pickername, 
			@QueryParam("pickerid") Long pickerid) 
	{
		OrderService os = new OrderService();
		
		System.out.println("PickupOrderRestService:: pickupOrder ");
		
		String [] orderListArray = tcorderid.split(","); 
		
		List<String> orderArrayList = Arrays.asList(orderListArray); 
		
        List<Order>  orderList = os.processOrderPickupRequest(orderArrayList,pickername);
        sendPickupConfirmationCode(pickerid, orderList);
        
        String jsonStr = "";
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			jsonStr = mapper.writeValueAsString(orderList);	
		}
		catch(Exception ex)
		{
			System.out.println("Exception while coverting object to json " +  ex);   		
		}
        
		System.out.println("PickupOrderRestService:: pickupOrder return = " + jsonStr);        
        
        return  jsonStr;
	}
	
	
	protected void sendPickupConfirmationCode(Long pickerid, List<Order> orderList)
	{
		System.out.println("PickupOrderRestService");
		System.out.println("Picker ID = " + pickerid);
		
		Map<String, List<String>> pickConf = new HashMap<String, List<String>>();
        
        if( orderList != null && orderList.size() > 0)
        {
        	System.out.println("Confirmed order count = " + orderList.size());
        	
        	for(Order ord : orderList)
        	{
        		System.out.println("Pickup Conf# " + ord.getPickUpConfirmationCode());
        		
        		if( pickConf.get(ord.getPickUpConfirmationCode()) != null)
        		{
        			System.out.println("Order# " + ord.getOrderNumber() + 
        					"added for " + "Pickup Conf# " + ord.getPickUpConfirmationCode());
        			(pickConf.get(ord.getPickUpConfirmationCode())).add(ord.getOrderNumber());
        		}
        		else
        		{
        			System.out.println("Pickup Conf# " + ord.getPickUpConfirmationCode() + " added");
        			System.out.println("Order# " + ord.getOrderNumber() + 
        					"added for " + "Pickup Conf# " + ord.getPickUpConfirmationCode());
        			List<String> orderListString = new ArrayList<String>();
        			orderListString.add(ord.getOrderNumber());
        			pickConf.put(ord.getPickUpConfirmationCode(), orderListString);
        		}
        	}
        }
       
        
        if( pickConf != null && pickConf.isEmpty() == false)
        {
        	IStomeTwitterServices twitterService = new StomeTwitterServicesImpl();
        	
        	for(String pickConfCode : pickConf.keySet())
        	{	
        		System.out.println("Pickup Conf# " + pickConfCode);
        		if( pickConf.get(pickConfCode) != null)
        		{
        			String orderString = StringUtils.join(pickConf.get(pickConfCode), ",");
        			String pickupConfMsg = "Pickup Conf#"+pickConfCode+" Orders#"+orderString;
        			System.out.println("Order String# " + orderString);
        			System.out.println("Pickup Confirmation Message# " + pickupConfMsg);
        			twitterService.sendOrderPickupNumbers(pickerid, pickupConfMsg);
        		}
        	}
        }
	}
}

/**
 * 
 */
package com.ibm.cloudoe.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import com.ibm.cloudoe.domain.Order;
import com.ibm.cloudoe.service.OrderService;

/**
 * @author Asuresh
 *
 */
@Path("/confirmOrderPickup")
public class ConfirmOrderPickupRestService 
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String confirmOrderPickup(@HeaderParam("tcorderid") String tcorderid,
			@HeaderParam("pickername") String pickername, 
			@HeaderParam("pickupcode") String pickupCode) 
	{
		OrderService os = new OrderService();
		
		System.out.println("ConfirmOrderPickupRestService:: confirmOrderPickup ");
		
		String [] orderListArray = tcorderid.split(","); 
		
		List<String> orderArrayList = Arrays.asList(orderListArray); 
		
        List<Order>  orderList = os.confirmOrderPickupRequest(orderArrayList,pickername, pickupCode);
        
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
        
		System.out.println("ConfirmOrderPickupRestService:: ConfirmPickupOrder return = " + jsonStr);        
        
        return  jsonStr;
	}
}

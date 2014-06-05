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
import org.json.simple.JSONArray;
import com.ibm.cloudoe.domain.Order;
import com.ibm.cloudoe.service.OrderService;

/**
 * @author Asuresh
 *
 */
@Path("/pickupOrder")
public class PickupOrderRestService 
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String pickupOrder(@HeaderParam("tcorderid") String tcorderid,
			@HeaderParam("pickername") String pickername) 
	{
		OrderService os = new OrderService();
		
		System.out.println("OrderListRestService:: pickupOrder ");
		
		String [] orderListArray = tcorderid.split(","); 
		
		List<String> orderArrayList = Arrays.asList(orderListArray); 
		
        List<Order>  orderList = os.processOrderPickupRequest(orderArrayList,pickername);
        
        String jsonStr = new JSONArray().toJSONString(orderList);
        
		System.out.println("OrderListRestService:: pickupOrder return = " + jsonStr);        
        
        return  jsonStr;
	}
}
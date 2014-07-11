/**
 * 
 */
package com.ibm.cloudoe.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.codehaus.jackson.map.ObjectMapper;
import com.ibm.cloudoe.domain.Order;
import com.ibm.cloudoe.service.OrderService;

/**
 * @author Asuresh
 *
 */

@Path("/getOrderListByZip")
public class OrderListByZipRestService
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrderListByZipCode(@QueryParam("zipcode") String zipcode) 
	{
		System.out.println("OrderListRestService:: getOrderListByZipCode");
		OrderService os = new OrderService();
        List<Order>  orderList = os.getAllOrdersByPostalCode(zipcode);
        
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
		System.out.println("OrderListRestService:: getOrderListByZipCode return =" + jsonStr);        
        return jsonStr;
	}
}

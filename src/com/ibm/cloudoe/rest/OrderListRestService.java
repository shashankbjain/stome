package com.ibm.cloudoe.rest;

import javax.ws.rs.GET;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import java.util.List;
import com.ibm.cloudoe.service.OrderService;
import org.apache.wink.json4j.JSONObject;
import com.ibm.cloudoe.domain.Order;
import java.util.Arrays;

//This class define the /getOrderList RESTful API to fetch all orders information
@Path("/getOrderList")
public class OrderListRestService 
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOrderList() 
	{
		System.out.println("OrderListRestService:: getOrderList");
		OrderService os = new OrderService();
        List<Order>  orderList = os.getAllOrders();
        
        
        JSONArray array = new JSONArray() ;
        array.addAll(orderList) ;
        
        String jsonStr = array.toJSONString() ;
		System.out.println("OrderListRestService:: getOrderList jsonString " +  jsonStr);        
        return jsonStr;
	}
}

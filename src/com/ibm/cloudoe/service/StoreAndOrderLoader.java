/**
 * 
 */
package com.ibm.cloudoe.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibm.cloudoe.domain.Address;
import com.ibm.cloudoe.domain.JasonParmFV;
import com.ibm.cloudoe.domain.Order;
import com.ibm.cloudoe.domain.OrderStatusFV;
import com.ibm.cloudoe.domain.Store;

/**
 * @author Asuresh
 *
 */
public class StoreAndOrderLoader 
{
	public static ConcurrentHashMap<String, Order> orderMap = 
		new ConcurrentHashMap<String, Order>();
	
	public static ConcurrentHashMap<String, Store> storeMap = 
		new ConcurrentHashMap<String, Store>();
	
	public static void initialize()
	{	
		loadStores("stores.json");
		loadOrders("orders.json");
	}
	
	public static void loadStores(String jsonFile)
	{
		// read JSON text file and populate storeOrderList
		JSONParser parser = new JSONParser();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(jsonFile));
			String line;
			while ((line = br.readLine()) != null)
			{
				JSONObject jsonObject = (JSONObject) parser.parse(line);
				String storeName = jsonObject.get(
						JasonParmFV.STORENAME.value()).toString();
				String address1 = jsonObject.get(
						JasonParmFV.ADDRESS1.value()).toString();
				String city = jsonObject.get(
						JasonParmFV.CITY.value()).toString();
				String state = jsonObject.get(
						JasonParmFV.STATE.value()).toString();
				String postalCode = jsonObject.get(
						JasonParmFV.POSTALCODE.value()).toString();
				String contact = jsonObject.get(
						JasonParmFV.CONTACT.value()).toString();
				
				Store store = new Store();
				store.setStoreName(storeName);
				Address stAddress = new Address();
				stAddress.setAddress1(address1);
				stAddress.setCity(city);
				stAddress.setState(state);
				stAddress.setPostalCode(postalCode);
				stAddress.setContact(contact);
				store.setAddress(stAddress); 
				
				storeMap.put(storeName, store);
				
				System.out.println("Store Name = " + storeName
						+ ", Address1 = " + address1 + ", City = " + city 
						+ ", State = " + state + ", Postal Code = " + postalCode
						+ ", Contact = " + contact);
			}
			br.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("FileNotFoundException");
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			System.out.println("IOException");
			ex.printStackTrace();
		}
		catch (ParseException ex)
		{
			System.out.println("ParseException");
			ex.printStackTrace();
		}
		catch (NullPointerException ex)
		{
			System.out.println("NullPointerException");
			ex.printStackTrace();
		}
	}
	
	public static void loadOrders(String jsonFile)
	{
		// read JSON text file and populate storeOrderList
		JSONParser parser = new JSONParser();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(jsonFile));
			String line;
			while ((line = br.readLine()) != null)
			{
				JSONObject jsonObject = (JSONObject) parser.parse(line);
							
				String orderNumber = jsonObject.get(
						JasonParmFV.ORDERNUMBER.value()).toString();
				String storeName = jsonObject.get(
						JasonParmFV.STORENAME.value()).toString();
				String address1 = jsonObject.get(
						JasonParmFV.ADDRESS1.value()).toString();
				String city = jsonObject.get(
						JasonParmFV.CITY.value()).toString();
				String state = jsonObject.get(
						JasonParmFV.STATE.value()).toString();
				String postalCode = jsonObject.get(
						JasonParmFV.POSTALCODE.value()).toString();
				String contact = jsonObject.get(
						JasonParmFV.CONTACT.value()).toString();
				Double weight = (Double)jsonObject.get(
						JasonParmFV.WEIGHT.value());
				Double stomeAmount = (Double) jsonObject.get(
						JasonParmFV.STOMEAMOUNT.value());
				
				Order ord = new Order();
				ord.setOrderNumber(orderNumber);
				ord.setStoreName(storeName);
				ord.setStore(storeMap.get(storeName));
				
				Address dAddress = new Address();
				dAddress.setAddress1(address1);
				dAddress.setCity(city);
				dAddress.setState(state);
				dAddress.setPostalCode(postalCode);
				dAddress.setContact(contact);
				ord.setAddress(dAddress); 
				
				ord.setWeight(weight);
				ord.setStomeAmount(stomeAmount);
				
				ord.setStatus(OrderStatusFV.AVAILABLE);
				orderMap.put(orderNumber, ord);
				
				System.out.println("Order Number = " + orderNumber
						+ ", Store Name = " + storeName + ", Address1 = " + address1 
						+ ", City = " + city + ", State = " + state 
						+ ", Postal Code = " + postalCode + ", Contact = " + contact
						+ ", Weight = " + weight + ", Stome Amount = " + stomeAmount);
			}
			br.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("FileNotFoundException");
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			System.out.println("IOException");
			ex.printStackTrace();
		}
		catch (ParseException ex)
		{
			System.out.println("ParseException");
			ex.printStackTrace();
		}
		catch (NullPointerException ex)
		{
			System.out.println("NullPointerException");
			ex.printStackTrace();
		}
	}
}

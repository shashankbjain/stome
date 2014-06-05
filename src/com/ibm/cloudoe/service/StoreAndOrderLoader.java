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
	
	public static void main(String[] args)
	{
		initialize();
	}
	
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
				Store store = new Store();
				store.setStoreName(jsonObject.get(
					JasonParmFV.STORENAME.value()).toString());
				Address stAddress = new Address();
				stAddress.setAddress1(jsonObject.get(
						JasonParmFV.ADDRESS1.value()).toString());
				stAddress.setCity(jsonObject.get(
						JasonParmFV.CITY.value()).toString());
				stAddress.setState(jsonObject.get(
						JasonParmFV.STATE.value()).toString());
				stAddress.setPostalCode(jsonObject.get(
						JasonParmFV.POSTALCODE.value()).toString());
				stAddress.setContact(jsonObject.get(
						JasonParmFV.CONTACT.value()).toString());
				store.setAddress(stAddress); 
				storeMap.put(jsonObject.get(
						JasonParmFV.STORENAME.value()).toString(), store);
			}
			br.close();
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		catch (NullPointerException ex)
		{
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
				Order ord = new Order();
				ord.setOrderNumber(jsonObject.get(
					JasonParmFV.ORDERNUMBER.value()).toString());
				
				ord.setStoreName(jsonObject.get(
						JasonParmFV.STORENAME.value()).toString());
				ord.setStore(storeMap.get(ord.getStoreName()));
				
				Address dAddress = new Address();
				dAddress.setAddress1(jsonObject.get(
						JasonParmFV.ADDRESS1.value()).toString());
				dAddress.setCity(jsonObject.get(
						JasonParmFV.CITY.value()).toString());
				dAddress.setState(jsonObject.get(
						JasonParmFV.STATE.value()).toString());
				dAddress.setPostalCode(jsonObject.get(
						JasonParmFV.POSTALCODE.value()).toString());
				dAddress.setContact(jsonObject.get(
						JasonParmFV.CONTACT.value()).toString());
				ord.setAddress(dAddress); 
				
				ord.setWeight((Double)jsonObject.get(
						JasonParmFV.WEIGHT.value()));
						
				ord.setStomeAmount((Double) jsonObject.get(
						JasonParmFV.STOMEAMOUNT.value()));
				ord.setStatus(OrderStatusFV.AVAILABLE);
				orderMap.put(jsonObject.get(
						JasonParmFV.STORENAME.value()).toString(), ord);
			}
			br.close();
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		catch (ParseException ex)
		{
			ex.printStackTrace();
		}
		catch (NullPointerException ex)
		{
			ex.printStackTrace();
		}
	}
}

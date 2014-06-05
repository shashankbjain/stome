/**
 * 
 */
package com.ibm.cloudoe.service;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.List;

/**
 * @author Asuresh
 *
 */
public class PickupConfirmationService
{	
	public static final String ACCOUNT_SID = "AC98b49400b6ef58d9bd3d040089c11b68";
	public static final String AUTH_TOKEN = "bb24eda550bd3df0d4190b8946c80421";
	public static final String STOME_PHONE = "+16789169534";

	public void notifyEndCustomer(String customerPhoneNumber, 
			String orderNumber, String pickerName) 
	{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		try 
		{
			params.add(new BasicNameValuePair("To", customerPhoneNumber));
			params.add(new BasicNameValuePair("From", STOME_PHONE));
			params.add(new BasicNameValuePair("Body", "Stome Message"
					+ "\nOrder : " + orderNumber 
					+ "\nDelivery By : "+ pickerName 
					+ "\nDelivery confirmation code : "  
					+ Math.abs(orderNumber.hashCode())));
			MessageFactory messageFactory = 
				client.getAccount().getMessageFactory();
			messageFactory.create(params);
		} 
		catch (Exception e) 
		{
			System.out.println("*** Failed to send delivery" +
					" confirmation code ***");
			System.out.println("Order = " + orderNumber 
					+ ", Customer contact = " + customerPhoneNumber
					+ ", Delivery User = " + pickerName);
		}
	}
	
	public void thankEndCustomer(String customerPhoneNumber)
	{
		try 
		{
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("To", customerPhoneNumber));
			params.add(new BasicNameValuePair("From", STOME_PHONE));
			params.add(new BasicNameValuePair("Body", "Thank you for using Stome!"));
			MessageFactory messageFactory = 
				client.getAccount().getMessageFactory();
			messageFactory.create(params);
		}
		catch (Exception e) 
		{
			System.out.println("*** Failed to thank end customer");
		}
	}
}

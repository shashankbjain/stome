package com.stome.twitter;

import twitter4j.User;
import twitter4j.UserStreamListener;

public interface IStomeTwitterServices extends UserStreamListener{
	
	public void sendFollowRequest(User aUser) ;
	
	public void sendOrderPickupNumbers(String userId , String pickupConf) ;
	
	public void sendDeliveryConfirmMsg(String userId , String code) ;
	
	
	
}

package com.stome.twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;

public class StomeTwitterServicesImpl implements IStomeTwitterServices {

	public void onBlock(User arg0, User arg1) {
	}

	public void onDeletionNotice(long arg0, long arg1) {
	}

	public void onDirectMessage(DirectMessage dmsg) {
		String directMsg = dmsg.getText();

		if (directMsg.startsWith("#ORDS")) {
			/*
			 * // Need to send back list of available orders as JSON String
			 * StoreOperation ops = new StoreOperation() ; List<IOrder> orders =
			 * ops.getOrdersToPickup("30339") ; orders.get(0).toString() ;
			 */

			String zipCode = directMsg.substring("#ORDS".length()).trim();
			System.out.println("User Zip Code  " + zipCode);
			String location = dmsg.getSender().getLocation();

			System.out.println("User Location = " + location);

			Twitter twt = getTwitterInstance();

			String stomeOrderListUrl = "http://mastome.mybluemix.net/tiny.jsp?zipcode="+zipCode+"pickername"+
			dmsg.getSenderScreenName();

			String tinyUrl = createTinyUrl(stomeOrderListUrl);

			try {
				//twt.sendDirectMessage(dmsg.getSenderId(), stomeOrderListUrl);
				String msg = "@" + dmsg.getSenderId() + " " + tinyUrl;
				System.out.println("sending back tweet msg:" + msg);
				
				Status status = twt.updateStatus(msg);
				System.out.println("status:" + status.getText());
				
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onFavorite(User arg0, User arg1, Status arg2) {
	}

	public void onFollow(User arg0, User arg1) {

		// User has signed up for @stomeapp...
		// first follow him from the app side..

		sendFollowRequest(arg0);

	}

	public void onFriendList(long[] arg0) {
	}

	public void onUnblock(User arg0, User arg1) {
		// TODO Auto-generated method stub

	}

	public void onUnfavorite(User arg0, User arg1, Status arg2) {
		// TODO Auto-generated method stub

	}

	public void onUnfollow(User arg0, User arg1) {
		// TODO Auto-generated method stub

	}

	public void onUserListCreation(User arg0, UserList arg1) {
		// TODO Auto-generated method stub

	}

	public void onUserListDeletion(User arg0, UserList arg1) {
		// TODO Auto-generated method stub

	}

	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
		// TODO Auto-generated method stub

	}

	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
		// TODO Auto-generated method stub

	}

	public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
		// TODO Auto-generated method stub

	}

	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
		// TODO Auto-generated method stub

	}

	public void onUserListUpdate(User arg0, UserList arg1) {
		// TODO Auto-generated method stub

	}

	public void onUserProfileUpdate(User arg0) {
		// TODO Auto-generated method stub

	}

	public void onDeletionNotice(StatusDeletionNotice arg0) {
		// TODO Auto-generated method stub

	}

	public void onScrubGeo(long arg0, long arg1) {
		// TODO Auto-generated method stub

	}

	public void onStallWarning(StallWarning arg0) {
		// TODO Auto-generated method stub

	}

	public void onStatus(Status status) {
		// TODO Auto-generated method stub

		System.out.println("===" + status.getGeoLocation());

	}

	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub

	}

	public void onException(Exception arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendFollowRequest(User aUser) {

		Twitter twt = getTwitterInstance();
		try {
			twt.createFriendship(aUser.getId(), true);
			twt.sendDirectMessage(aUser.getId(),
					"Welcome to Stome.. tweet , deliver and earn");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Twitter getTwitterInstance() {
		return new TwitterFactory().getInstance();
	}

	private String createTinyUrl(String stomeUrl) {

		String tinyUrl = "";

		try {
			URL url = new URL("http://tinyurl.com/api-create.php?url="
					+ stomeUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200) {
				tinyUrl = "ERROR.. Could not generate tiny URL.. Please visit: "
						+ stomeUrl;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			int numTries = 0;
			while ((tinyUrl = br.readLine()) != null) {

				if(!StringUtils.isEmpty(tinyUrl)) {
					break ;
				}
				numTries++;
				if (numTries > 15) {
					tinyUrl = " Timed out generating a tiny url";
					break;
				}
			}
		} catch (Exception e) {
			tinyUrl = " Timed out generating a tiny url";
			e.printStackTrace();
		}

		System.out.println("Converted Tiny Url = " + tinyUrl) ;
		return tinyUrl;
	}

	@Override
	public void sendOrderPickupNumbers(String userId, String pickupConf) {

		Twitter twitter = getTwitterInstance();

		try {
			twitter.sendDirectMessage(userId, pickupConf);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendDeliveryConfirmMsg(String userId, String code) {

		Twitter twitter = getTwitterInstance();

		try {
			twitter.sendDirectMessage(userId, code);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

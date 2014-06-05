/**
 * 
 */
package com.ibm.cloudoe.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.ibm.cloudoe.domain.Store;

/**
 * @author Asuresh
 *
 */
public class StoreService 
{	
	protected static ConcurrentHashMap<String, Store> storeMap
		= StoreAndOrderLoader.storeMap;
	
	synchronized public List<Store> getAllStores()
	{
		List<Store> storeList = new ArrayList<Store>();
		Iterator<Store> itr = storeMap.values().iterator();
		while(itr.hasNext())
		{
			Store o = itr.next();
			storeList.add(o);
		}
		return storeList;
	}
}

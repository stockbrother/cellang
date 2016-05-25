/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 11, 2012
 */
package org.cellang.webcore.client;

import java.util.ArrayList;
import java.util.List;

import org.cellang.webcore.client.data.ErrorInfosData;
import org.cellang.webcore.client.lang.Callback;

/**
 * @author wu
 * 
 */
public class Console {

	private static Console ME = new Console();

	private List<Callback<Object, Boolean>> messageCallbackList = new ArrayList<Callback<Object, Boolean>>();

	private List<Object> cache;// 
	
	private int cacheLimit = 10000;

	public static Console getInstance() {
		return ME;
	}

	private Console() {
		this.cache = new ArrayList<Object>();
	}
	
	public void replay(Callback<Object, Boolean> handler) {
		this.replay(this.cacheLimit,handler);
	}
	
	public void replay(int lastLines, Callback<Object, Boolean> handler) {
		int to = this.cache.size();
		int from = to - lastLines;
		from = from < 0 ? 0 : from;

		List<Object> l2 = new ArrayList<Object>(this.cache.subList(from, to));

		for (int i = 0; i < l2.size(); i++) {
			Object line = l2.get(i);
			handler.execute(line);
		}
	}

	public void addMessageCallback(Callback<Object, Boolean> cb) {
		this.messageCallbackList.add(cb);
	}

	public void removeMessageCallback(Callback<Object, Boolean> cb) {
		this.messageCallbackList.remove(cb);
	}

	public void println(Object obj) {
		this.cache.add(obj);
		//
		if(this.cache.size()>this.cacheLimit+100){
			
			for(int i=0;i<100;i++){
				this.cache.remove(0);				
			}				
			
		}
		System.out.println(obj);
		for (Callback<Object, Boolean> cb : this.messageCallbackList) {
			try {
				cb.execute(obj);
			} catch (Throwable t) {
				System.out.println(Console.class.getName()
						+ ",error when dispatch println in console to callback:" + cb + ",throwable:" + t);
				t.printStackTrace();
			}
		}

	}

	/**
	 * @param eis
	 */
	public void error(ErrorInfosData eis) {
		this.println(eis);
	}

}

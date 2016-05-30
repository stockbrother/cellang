/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 1, 2012
 */
package org.cellang.clwt.commons.client.html5;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.WebException;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 *         <p>
 *         http://www.w3schools.com/html/html5_webstorage.asp
 */
public final class LocalStorageJSO extends JavaScriptObject {

	protected LocalStorageJSO() {

	}

	public static LocalStorageJSO getInstance(boolean force) {
		if (!isSupport()) {
			if (force) {
				String agent = Window.Navigator.getUserAgent();
				throw new WebException("browser not support Storage,agent: "
						+ agent);
			}
			return null;

		}

		LocalStorageJSO rt = getInstanceInternal();

		return rt;
	}

	public static native boolean isSupport()
	/*-{
	
		if(!("localStorage" in window)){
			return false;
		}
		return true;
	
	}-*/;

	private native static LocalStorageJSO getInstanceInternal()
	/*-{
		return localStorage;															
	}-*/;

	public native String getValue(String key)
	/*-{
			return localStorage[key];																		
	}-*/;

	/**
	 * Dec 1, 2012
	 */
	public native void setValue(String key, String value)/*-{
															localStorage[key]=value;
															}-*/;

	public native void removeValue(String key)/*-{
												localStorage.removeItem(key);
												}-*/;

	public native int length()/*-{
												return localStorage.length;
												}-*/;

	public native String key(int idx)/*-{
										return localStorage.key(idx);  
										
										}-*/;

	public List<String> keyList() {
		List<String> rt = new ArrayList<String>();
		for (int i = 0; i < this.length(); i++) {
			String key = this.key(i);
			rt.add(key);
		}
		return rt;
	}
}

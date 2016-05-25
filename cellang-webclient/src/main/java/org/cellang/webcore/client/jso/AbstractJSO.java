/**
 *  Jan 15, 2013
 */
package org.cellang.webcore.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

/**
 * @author wuzhen
 * 
 */
public class AbstractJSO extends JavaScriptObject {

	protected AbstractJSO() {

	}

	public final String toLongString() {
		String rt = "{";
		for (String key : this.keyArray()) {
			Object v = this.getProperty(key);
			rt += key + "=" + v + ",";
		}
		rt += "}";
		return rt;
	}

	public final native Object getProperty(String key)
	/*-{
		var rt = this[key];
		var type = typeof rt;
		if(type == 'boolean'){
			rt = @java.lang.Boolean::valueOf(Z)(rt);
		} else if(type == 'number'){
			rt = @java.lang.Double::valueOf(D)(rt);
		}
		return rt; 
		
	}-*/;

	public final native void setProperty(String key, boolean v)
	/*-{
		this[key] = v;
	}-*/;

	public final native void setProperty(String key, double v)
	/*-{
		this[key] = v;
	}-*/;

	public final String[] keyArray() {
		JsArrayString jsa = this.nativeKeyArray();
		String[] rt = new String[jsa.length()];
		for (int i = 0; i < rt.length; i++) {
			rt[i] = jsa.get(i);//
		}
		return rt;

	}

	private final native JsArrayString nativeKeyArray()
	/*-{
		var rt = [];
	  	for(var key in this){
	     	rt.push(key);
	  	}
	  	return rt;
		
	}-*/;
}

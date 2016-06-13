/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package org.cellang.clwt.commons.client.html5;

import org.cellang.clwt.core.client.core.ErrorReportProxyHandler;
import org.cellang.clwt.core.client.lang.Handler;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author wuzhen
 *         <p>
 *         http://www.mosync.com/files/imports/doxygen/latest/html5/filereader.
 *         md.html
 */
public final class FileReaderJSO extends JavaScriptObject {

	protected FileReaderJSO() {

	}

	public native static boolean isSupport()
	/*-{
		if($wnd.FileReader){
			return true;
		}
		return false;
	}-*/;

	public native static FileReaderJSO newInstance()
	/*-{
		if($wnd.FileReader){
			return new $wnd.FileReader();														
		}
		return null;
	}-*/;

	public FileReaderJSO onLoadEnd(Handler<JavaScriptObject> handler) {
		this.addEventHandler("onloadend", new ErrorReportProxyHandler<JavaScriptObject>(handler));
		return this;
	}

	public native void addEventHandler(String eventType, Handler<JavaScriptObject> handler)
	/*-{
														
															this[eventType]=function(evt){
																//alert('eventType:'+eventType+',event:'+evt+',handler:'+handler);
																handler.@com.fs.uicore.api.gwt.client.HandlerI::handle(Ljava/lang/Object;)(evt);
																
															};
														
														}-*/;

	public native FileReaderJSO readAsDataURL(FileRefJSO fileRef)
	/*-{
																		return this.readAsDataURL(fileRef);
																		
																		}-*/;

	public native String getResult()/*-{
										return this.result;
										}-*/;
}

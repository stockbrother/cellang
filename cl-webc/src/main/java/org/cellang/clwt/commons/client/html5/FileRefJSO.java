/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package org.cellang.clwt.commons.client.html5;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public final class FileRefJSO extends JavaScriptObject {

	protected FileRefJSO() {

	}
	
	public native String getName()/*-{
									// TODO Auto-generated method stub
									return null;
									}-*/
	;

	public native int getSize()/*-{
									return this.size;
									}-*/;

	public native JsDate getLastModifiedDate() /*-{
												return this.lastModifiedDate;
												}-*/;

	public native String getType() /*-{
												return this.type;
												}-*/;

	public static native JsArray<FileRefJSO> getFileList(Element fileInput)/*-{
																			return fileInput.files;
																			
																			}-*/;

}

/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 4, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.util;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;

/**
 * @author wu
 * 
 */
public class NativeEventUtil {

	public static void click(Element ele) {
		// this.archorClick(ae);//TODO
		// see com.google.gwt.user.client.ui.CustomButton
		NativeEvent evt = Document.get().createClickEvent(1, 0, 0, 0, 0, false,
				false, false, false);
		ele.dispatchEvent(evt);
	}
}

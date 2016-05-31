/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package org.cellang.clwt.commons.client.localdata;

import org.cellang.clwt.commons.client.html5.LocalStorageJSO;
import org.cellang.clwt.core.client.UiException;

/**
 * @author wu
 * 
 */
public class Html5LocalStore extends LocalStore {

	private static Html5LocalStore ME;

	private LocalStorageJSO lsj;

	public static Html5LocalStore getInstance(boolean force) {
		if (ME != null) {
			return ME;
		}

		LocalStorageJSO jso = LocalStorageJSO.getInstance(false);//
		if (jso == null) {
			if (force) {
				throw new UiException(
						"browser not support html5 local storage ");
			}
			return null;
		}
		ME = new Html5LocalStore(jso);
		return ME;
	}

	public Html5LocalStore(LocalStorageJSO jso) {
		this.lsj = jso;
	}

	@Override
	protected String getValue(String key) {
		return this.lsj.getValue(key);
	}

	/**
	 * Dec 9, 2012
	 */

	@Override
	protected void setValue(String key, String value) {
		this.lsj.setValue(key, value);
	}
}

/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package org.cellang.elastictable.util;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.lang.HasProperties;
import org.cellang.elastictable.RowObject;

/**
 * @author wu
 * 
 */
public class NodeWrapperUtil {
	public static <W extends RowObject> List<HasProperties<Object>> convert(
			List<W> wl, String[] from, boolean[] force, String[] to) {
		List<HasProperties<Object>> rt = new ArrayList<HasProperties<Object>>();
		for (W w : wl) {
			HasProperties<Object> pts = w.convert(from, force, to);
			rt.add(pts);
		}
		return rt;
	}

	public static <W extends RowObject> List<HasProperties<Object>> convert(
			List<W> wl) {
		List<HasProperties<Object>> rt = new ArrayList<HasProperties<Object>>();
		for (W w : wl) {
			HasProperties<Object> pts = w.convert();
			rt.add(pts);
		}
		return rt;
	}

	public static List<String> getIdList(List<? extends RowObject> nwL) {
		List<String> rt = new ArrayList<String>();
		for (RowObject nw : nwL) {
			rt.add(nw.getId());//
		}
		return rt;
	}

	/**
	 * Dec 6, 2012
	 */
	public static <T> List<T> getFieldList(List<? extends RowObject> nwL,
			String key) {
		//
		List<T> rt = new ArrayList<T>();
		for (RowObject nw : nwL) {
			T vi = (T) nw.getProperty(key, true);
			rt.add(vi);
		}
		return rt;
	}

}

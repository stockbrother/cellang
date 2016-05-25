/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package org.cellang.webframework.client.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wu
 * 
 */
public class OID {

	private static Map<String, Long> nextMap = new HashMap<String, Long>();

	public static String next(String prefix) {
		Long next = nextMap.get(prefix);
		next = next == null ? 0L : next;
		String rt = prefix + next;
		nextMap.put(prefix, next + 1);

		return rt;
	}
}

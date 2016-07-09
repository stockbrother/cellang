/**
 *  
 */
package org.cellang.core.util;

import java.io.IOException;
import java.io.Reader;

/**
 * @author wu
 * 
 */
public class StringUtil {

	public static String readAsString(Reader reader) {
		StringBuffer sb = new StringBuffer();
		char[] cbuf = new char[1024];
		while (true) {
			try {
				int is = reader.read(cbuf);
				if (is <= 0) {
					break;
				}
				sb.append(cbuf, 0, is);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}
		return sb.toString();
	}

}

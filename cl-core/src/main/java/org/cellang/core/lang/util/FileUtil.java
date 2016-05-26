/**
 *  
 */
package org.cellang.core.lang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * @author wu
 * 
 */
public class FileUtil {

	public static String readAsString(InputStream is, String encode) {
		try {
			Reader reader = new InputStreamReader(is, encode);
			return readAsString(reader);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * @param reader
	 * @deprecated should use writer
	 */
	public static String readAsString(Reader reader) {
		StringBuffer sb = new StringBuffer();
		char[] cbuf = new char[1024];
		while (true) {
			try {
				int l = reader.read(cbuf);
				if (l == -1) {
					break;
				}
				sb.append(cbuf, 0, l);
			} catch (IOException e) {
				throw new RuntimeException(e);

			}
		}
		return sb.toString();
	}

	public static void copy(InputStream from, OutputStream to) throws IOException {
		byte[] buf = new byte[1024];
		while (true) {
			int l = from.read(buf);
			if (l == -1) {
				break;
			}
			to.write(buf, 0, l);

		}
	}
}

/**
 *  
 */
package org.cellang.core.util;

import java.io.File;
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

	public static File createTempDir(String prefix) {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = prefix + System.currentTimeMillis() + "-";

		int trys = 100;
		for (int counter = 0; counter < trys; counter++) {
			File tempDir = new File(baseDir, baseName + counter);
			if (tempDir.mkdir()) {
				return tempDir;
			}
		}
		throw new IllegalStateException("Failed to create directory within " + trys + " attempts (tried " + baseName
				+ "0 to " + baseName + (trys - 1) + ')');
	}

	public static void deleteTempDir(File home) {

		// check the
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		if (!baseDir.equals(home.getParentFile())) {
			throw new RuntimeException("dir:" + home + " not in tempdir!");
		}

		doDeleteTempDir(0, home);
	}

	private static void doDeleteTempDir(int depth, File home) {
		File[] cfileA = home.listFiles();
		for (File f : cfileA) {
			if (f.isFile()) {
				f.delete();
			} else {
				doDeleteTempDir(depth + 1, f);
			}
		}
		home.delete();
	}

}

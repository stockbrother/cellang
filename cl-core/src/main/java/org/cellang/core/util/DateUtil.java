/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package org.cellang.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuzhen
 * 
 */
public class DateUtil {
	private static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static String format(Date dd) {
		return FORMAT.format(dd);
	}

	public static Date parse(String date) {
		Date dt = null;
		try {
			dt = FORMAT.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);//
		}//
		return dt;
	}
}

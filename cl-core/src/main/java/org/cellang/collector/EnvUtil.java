package org.cellang.collector;

import java.io.File;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

import org.cellang.core.util.DateUtil;

public class EnvUtil {
	static TimeZone timeZone = TimeZone.getTimeZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC));
	static TimeZone dataTimeZone = TimeZone.getTimeZone(ZoneOffset.ofHours(8));//
	public static TimeZone getTimeZone() {
		return dataTimeZone;
	}

	public static Date newDateOfYearLastDay(int year) {
		return DateUtil.newDateOfYearLastDay(year, dataTimeZone);
	}

	public static File getDataDir() {
		String dataDirS = System.getProperty("cellang.data.dir");
		if (dataDirS == null) {
			dataDirS = System.getenv("cellang.data.dir");
			throw new RuntimeException("system property or environment variable cellang.data.dir not found.");
		}

		File d = new File(dataDirS);
		if (!d.exists()) {
			throw new RuntimeException("data dir:" + d.getAbsolutePath() + " not exist.");
		}
		return d;
	}

	/**
	 * Return the first dir, which contains the corp list data.
	 * 
	 * @return
	 */
	public static File getDir1() {

		return new File(getDataDir(), "1");

	}
	
	public static boolean isProxyEnabled(){
		return true;
	}

	public static String getProxyHome() {
		return "proxy.houston.hpecorp.net";
	}

	public static int getProxyPort() {
		return 8080;
	}

	public static String getHttpHost163() {
		//
		return "quotes.money.163.com";
	}

}

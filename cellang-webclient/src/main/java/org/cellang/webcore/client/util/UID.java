package org.cellang.webcore.client.util;

public class UID {

	// TODO uuid generate.
	private static int index;

	private static String PREFIX = System.currentTimeMillis() + "-";

	public static String create() {
		return create("");
	}

	public static String create(String pre) {
		return pre + PREFIX + (index++);
	}
}

/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.util;

/**
 * @author wu
 * 
 */
public class ObjectUtil {
	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == null) {
			if (o2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			return o1.equals(o2);
		}
	}

	public static boolean equals(Object[] os1, Object[] os2) {
		if (os1 == null) {
			if (os2 == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (os2 == null) {
				return false;
			} else {
				if (os1.length != os2.length) {
					return false;
				} else {
					for (int i = 0; i < os1.length; i++) {
						if (!nullSafeEquals(os1[i], os2[i])) {
							return false;
						}
					}
					return true;
				}
			}
		}
	}

	public static String getClassShortName(Object obj) {
		return getClassShortName(obj.getClass());
	}

	public static String getClassShortName(Class cls) {
		String cname = cls.getName();
		int idx = cname.lastIndexOf('.');
		String sname = cname;
		if (idx >= 0) {
			sname = cname.substring(idx + 1);
		}
		return sname;
	}
}

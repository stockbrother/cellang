package org.cellang.corpsviewer.utils;

import java.util.HashMap;

public class ValueMap<K, V> extends HashMap<K, V> {

	public V get(K k, V def) {
		V rt = super.get(k);
		if (rt == null) {
			rt = def;
		}
		return rt;
	}
}

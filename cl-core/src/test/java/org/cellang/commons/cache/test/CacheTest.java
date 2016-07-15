package org.cellang.commons.cache.test;

import org.cellang.commons.cache.Cache;

import junit.framework.TestCase;

public class CacheTest extends TestCase {

	long modified;
	Object obj;

	public void test() {
		Cache.Provider<Object> objProvider = new Cache.Provider<Object>() {

			@Override
			public Object get() {
				return obj;
			}

			@Override
			public long getModified() {
				return modified;
			}
		};

		Cache<Object> cache = new Cache<Object>(objProvider);
		this.obj = "A";
		Object obj1 = cache.get();
		assertEquals(this.obj, obj1);//

	}
}

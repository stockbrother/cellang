package org.cellang.test;

import org.cellang.commons.lang.NameSpace;
import org.junit.Assert;

import junit.framework.TestCase;

public class NameSpaceTest extends TestCase {

	public void test() {
		NameSpace ns = NameSpace.valueOf("abc.def");

		Assert.assertArrayEquals(new String[] { "abc", "def" }, ns.getNameArray());

	}
}

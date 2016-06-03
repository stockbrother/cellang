/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 20, 2013
 */
package org.cellang.elastictable.test.cases;

import java.util.Date;

import org.cellang.elastictable.elasticsearch.ElasticTimeFormat;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public class ElasticTimeFormatTest extends TestCase {

	public void test() {
		String s = ElasticTimeFormat.format(new Date());
		System.out.println(s);
		Date d = ElasticTimeFormat.parse("2013-01-20T15:23:40.713Z");
	}
}

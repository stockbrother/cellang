package org.cellang.test;

import org.json.JSONObject;
import org.json.JSONTokener;

import junit.framework.TestCase;

public class JSONTokenerTest extends TestCase {

	public void testObjectKey1() {
		String str = "{\"a\":\"av\"}";
		JSONTokener jt = new JSONTokener(str);
		Object o = jt.nextValue();
		assertTrue(JSONObject.class.isInstance(o));
		JSONObject jso = (JSONObject) o;
		assertEquals("av", jso.get("a"));//
	}

	public void testObjectKey2() {
		String str = "{'a':\"av\"}";
		JSONTokener jt = new JSONTokener(str);
		Object o = jt.nextValue();
		assertTrue(JSONObject.class.isInstance(o));
		JSONObject jso = (JSONObject) o;
		assertEquals("av", jso.get("a"));//
	}

	public void testObjectKey3() {
		String str = "{a:\"av\"}";
		JSONTokener jt = new JSONTokener(str);
		Object o = jt.nextValue();
		assertTrue(JSONObject.class.isInstance(o));
		JSONObject jso = (JSONObject) o;
		assertEquals("av", jso.get("a"));//
	}
}

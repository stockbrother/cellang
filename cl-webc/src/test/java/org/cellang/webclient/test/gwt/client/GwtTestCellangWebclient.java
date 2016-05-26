package org.cellang.webclient.test.gwt.client;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtTestCellangWebclient extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "org.cellang.webc.main.test.gwt.CellangWebclientJunit";
	}

	@Override
	protected void gwtSetUp() throws Exception {
	}

	@Override
	protected void gwtTearDown() throws Exception {
	}

	public void test() {
		System.out.println("start test()");
		System.out.println("end test()");
	}
}

package org.cellang.clwt.commons.test.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.junit.client.GWTTestCase;

public abstract class AbstractGwtTestBase extends GWTTestCase {

	protected Set<String> finishing;

	
	@Override
	protected void gwtSetUp() throws Exception {
		this.finishing = new HashSet<String>();
	}

	public void tryFinish(String finish) {
		this.finishing.remove(finish);
		System.out.println("finish:" + finish + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}

	@Override
	protected void gwtTearDown() throws Exception {

	}

}

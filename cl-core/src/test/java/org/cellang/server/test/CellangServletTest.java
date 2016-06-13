package org.cellang.server.test;

import org.cellang.commons.transfer.test.mock.ClientWithBrowserMock;
import org.cellang.core.servlet.CellangServlet;

import junit.framework.TestCase;

public class CellangServletTest extends TestCase {
	ClientWithBrowserMock browser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		browser = new ClientWithBrowserMock(CellangServlet.class);
	}

	public void testNoThisHandler() throws Exception {
		System.out.println("start testNoThisHandler");
		String sessionId = browser.syncConnect();
		try {
			
		} finally {
			browser.syncClose(sessionId);
		}
		System.out.println("end testNoThisHandler");
	}

	@Override
	protected void tearDown() throws Exception {
		browser.destroy();
		super.tearDown();

	}
}

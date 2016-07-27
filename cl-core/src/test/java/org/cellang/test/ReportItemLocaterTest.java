package org.cellang.test;

import java.io.IOException;
import java.io.StringWriter;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.format.ReportItemLocators;

import junit.framework.TestCase;

public class ReportItemLocaterTest extends TestCase {

	public void test() throws IOException {
		ReportItemLocators rils = ReportItemLocators.load();
		ReportItemLocator root = rils.getRoot();
		StringWriter sw = new StringWriter();

		root.write(sw);
		System.out.println(sw.getBuffer());
	}
}

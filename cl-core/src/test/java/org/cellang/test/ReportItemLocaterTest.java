package org.cellang.test;

import java.io.IOException;
import java.io.StringWriter;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.core.entity.BalanceSheetReportEntity;

import junit.framework.TestCase;

public class ReportItemLocaterTest extends TestCase {

	public void test() throws IOException {
		ReportItemLocators.Group rils = ReportItemLocators.getInstance().get(BalanceSheetReportEntity.class);

		ReportItemLocator root = rils.getRoot();
		StringWriter sw = new StringWriter();

		root.write(sw);
		System.out.println(sw.getBuffer());
	}
}

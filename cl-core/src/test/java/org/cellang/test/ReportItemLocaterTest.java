package org.cellang.test;

import java.io.IOException;
import java.io.StringWriter;

import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.corpsviewer.corpdata.ItemDefine;
import org.cellang.corpsviewer.corpdata.ItemDefines;

import junit.framework.TestCase;

public class ReportItemLocaterTest extends TestCase {

	public void test() throws IOException {
		ItemDefines.Group rils = ItemDefines.getInstance().get(BalanceSheetReportEntity.class);

		ItemDefine root = rils.getRoot();
		StringWriter sw = new StringWriter();

		root.write(sw);
		System.out.println(sw.getBuffer());
	}
}

package org.cellang.server.test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.EntitySessionFactoryImpl;
import org.cellang.core.entity.GetSingleEntityOp;
import org.cellang.core.loader.BalanceSheetFileProcessor;
import org.cellang.core.util.FileUtil;

import junit.framework.TestCase;

public class BalanceSheetLoaderTest extends TestCase {

	public void test() {
		GetSingleEntityOp<BalanceSheetReportEntity> getOp = new GetSingleEntityOp<>();
		File dbHome = FileUtil.createTempDir("cl-test-home");
		dbHome = new File(dbHome, "db");
		String dbName = "h2db";
		EntitySessionFactory es = EntitySessionFactoryImpl.newInstance(dbHome, dbName, new EntityConfigFactory());
		BalanceSheetFileProcessor sl = new BalanceSheetFileProcessor(es);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("org/cellang/balancesheet/Book1.csv");
		InputStreamReader rd = new InputStreamReader(is);
		sl.process(rd);
		String corpId = "601857";

		BalanceSheetReportEntity se = getOp.set(BalanceSheetReportEntity.class, "corpId", corpId).execute(es);
		assertNotNull(se);
		assertEquals(corpId, se.getCorpId());

	}
}

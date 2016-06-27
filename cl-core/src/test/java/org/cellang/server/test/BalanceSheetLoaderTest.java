package org.cellang.server.test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.cellang.core.BalanceSheetFileProcessor;
import org.cellang.core.entity.BalanceSheetEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityService;
import org.cellang.core.util.FileUtil;

import junit.framework.TestCase;

public class BalanceSheetLoaderTest extends TestCase {

	public void test() {
		File dbHome = FileUtil.createTempDir("cl-test-home");
		dbHome = new File(dbHome, "db");
		String dbName = "h2db";
		EntityService es = EntityService.newInstance(dbHome, dbName,new EntityConfigFactory());
		BalanceSheetFileProcessor sl = new BalanceSheetFileProcessor(es);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("org/cellang/balancesheet/Book1.csv");
		InputStreamReader rd = new InputStreamReader(is);
		sl.load(rd);
		String corpId = "601857";
		BalanceSheetEntity se = es.getSingle(BalanceSheetEntity.class, "corpId", corpId);
		assertNotNull(se);
		assertEquals(corpId, se.getCorpId());

	}
}

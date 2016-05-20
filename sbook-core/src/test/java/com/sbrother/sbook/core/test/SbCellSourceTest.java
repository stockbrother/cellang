package com.sbrother.sbook.core.test;

import java.math.BigDecimal;

import com.sbrother.sbook.core.SbCellValue;
import com.sbrother.sbook.core.SbCellSource;
import com.sbrother.sbook.core.SbValue;
import com.sbrother.sbook.core.jdbc.SbJdbcAttributeSourceConfiguration;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SbCellSourceTest extends TestCase {

	public void test() {

		SbJdbcAttributeSourceConfiguration cfg = new SbJdbcAttributeSourceConfiguration();
		cfg.setUrl("jdbc:h2:./target/test/h2db");

		SbCellSource as = cfg.create();
		as.open();
		try {

			SbCellValue ao = new SbCellValue();
			ao.setBookId("book1");
			ao.setTypeId("attr-name1");			
			ao.setValue(SbValue.getInstance(new BigDecimal("123.456")));

			as.save(ao);
			SbCellValue ao2 = as.getCell(ao.getBookId(), ao.getTypeId());
			Assert.assertEquals(ao, ao2);
			as.clear();
			SbCellValue ao3 = as.getCell(ao.getBookId(), ao.getTypeId());
			Assert.assertNull(ao3);//
		} finally {
			as.close();
		}
	}
}

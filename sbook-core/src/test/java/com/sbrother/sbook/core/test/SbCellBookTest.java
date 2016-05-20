package com.sbrother.sbook.core.test;

import java.math.BigDecimal;

import com.sbrother.sbook.core.SbCell;
import com.sbrother.sbook.core.SbStockBook;
import com.sbrother.sbook.core.SbValue;
import com.sbrother.sbook.core.jdbc.SbJdbcAttributeSourceConfiguration;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SbCellBookTest extends TestCase {

	public void test() {

		SbJdbcAttributeSourceConfiguration cfg = new SbJdbcAttributeSourceConfiguration();
		cfg.setUrl("jdbc:h2:./target/test/h2db");

		SbStockBook as = cfg.createJdbcAttributeSource();
		as.open();
		try {

			SbCell ao = new SbCell();
			ao.setBookIdentifier("book1");
			ao.setName("attr-name1");			
			ao.setValue(SbValue.getInstance(new BigDecimal("123.456")));

			as.saveAttributeObject(ao);
			SbCell ao2 = as.getCell(ao.getBookIdentifier(), ao.getName());
			Assert.assertEquals(ao, ao2);
			as.clear();
			SbCell ao3 = as.getCell(ao.getBookIdentifier(), ao.getName());
			Assert.assertNull(ao3);//
		} finally {
			as.close();
		}
	}
}

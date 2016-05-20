package com.sbrother.sbook.core.test;

import java.math.BigDecimal;

import com.sbrother.sbook.core.SbDate;
import com.sbrother.sbook.core.SbName;
import com.sbrother.sbook.core.SbCell;
import com.sbrother.sbook.core.SbOwner;
import com.sbrother.sbook.core.SbCellDataSource;
import com.sbrother.sbook.core.SbValue;
import com.sbrother.sbook.core.jdbc.SbJdbcAttributeSourceConfiguration;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AttributeSourceTest extends TestCase {

	public void test() {

		SbJdbcAttributeSourceConfiguration cfg = new SbJdbcAttributeSourceConfiguration();
		cfg.setUrl("jdbc:h2:./target/test/h2db");

		SbCellDataSource as = cfg.createJdbcAttributeSource();
		as.open();
		try {

			SbCell ao = new SbCell();
			ao.setDate(SbDate.getInstance("2015/12/31 00:00:00.000"));
			ao.setName(SbName.getInstance("attr-name1"));
			ao.setOwner(SbOwner.getInstance("attr-owner1"));
			ao.setValue(SbValue.getInstance(new BigDecimal("123.456")));

			as.saveAttributeObject(ao);
			SbCell ao2 = as.getAttributeObject(ao.getOwner(), ao.getDate(), ao.getName());
			Assert.assertEquals(ao, ao2);
			as.clear();
			SbCell ao3 = as.getAttributeObject(ao.getOwner(), ao.getDate(), ao.getName());
			Assert.assertNull(ao3);//
		} finally {
			as.close();
		}
	}
}

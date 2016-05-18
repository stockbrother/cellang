package com.sb.anyattribute.core.test;

import java.math.BigDecimal;

import com.sb.anyattribute.core.AttributeDate;
import com.sb.anyattribute.core.AttributeName;
import com.sb.anyattribute.core.AttributeObject;
import com.sb.anyattribute.core.AttributeOwner;
import com.sb.anyattribute.core.AttributeSource;
import com.sb.anyattribute.core.AttributeValue;
import com.sb.anyattribute.core.jdbc.JdbcAttributeSourceConfiguration;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AttributeSourceTest extends TestCase {

	public void test() {

		JdbcAttributeSourceConfiguration cfg = new JdbcAttributeSourceConfiguration();
		cfg.setUrl("jdbc:h2:./target/test/h2db");

		AttributeSource as = cfg.createJdbcAttributeSource();
		as.open();
		try {

			AttributeObject ao = new AttributeObject();
			ao.setDate(AttributeDate.getInstance("2015/12/31 00:00:00.000"));
			ao.setName(AttributeName.getInstance("attr-name1"));
			ao.setOwner(AttributeOwner.getInstance("attr-owner1"));
			ao.setValue(AttributeValue.getInstance(new BigDecimal("123.456")));

			as.saveAttributeObject(ao);
			AttributeObject ao2 = as.getAttributeObject(ao.getOwner(), ao.getDate(), ao.getName());
			Assert.assertEquals(ao, ao2);
		} finally {
			as.close();
		}
	}
}

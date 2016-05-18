package com.sb.anyattribute.core.jdbc;

import com.sb.anyattribute.common.Configuration;

public class JdbcAttributeSourceConfiguration extends Configuration {

	public JdbcAttributeSource createJdbcAttributeSource() {

		JdbcAttributeSource rt = new JdbcAttributeSource();
		rt.configure(this);//

		return rt;
	}

	public String getUrl() {
		return super.getString("url", true);
	}

	public void setUrl(String string) {
		super.set("url", string);
	}

}

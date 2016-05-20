package com.sbrother.sbook.core.jdbc;

import com.sbrother.sbook.common.SbConfiguration;

public class SbJdbcAttributeSourceConfiguration extends SbConfiguration {

	public SbJdbcAttributeSource createJdbcAttributeSource() {

		SbJdbcAttributeSource rt = new SbJdbcAttributeSource();
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

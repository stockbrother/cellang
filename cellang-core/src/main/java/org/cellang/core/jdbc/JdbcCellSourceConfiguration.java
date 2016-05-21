package org.cellang.core.jdbc;

import org.cellang.common.Configuration;

public class JdbcCellSourceConfiguration extends Configuration {

	public JdbcCellSource create() {

		JdbcCellSource rt = new JdbcCellSource();
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

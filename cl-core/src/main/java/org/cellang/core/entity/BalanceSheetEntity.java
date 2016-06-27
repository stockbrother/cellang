package org.cellang.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;

public class BalanceSheetEntity extends EntityObject {
	public static final String tableName = "balancesheet";
	private String corpId;
	private Date reportDate;
	private int quanter;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public int getQuanter() {
		return quanter;
	}

	public void setQuanter(int quanter) {
		this.quanter = quanter;
	}

	public static void fillCreate(CreateTableOperation cto) {
		cto.addColumn("id", String.class);
		cto.addColumn("corpId", String.class);
		cto.addColumn("reportDate", Date.class);
		cto.addColumn("quanter", Integer.class);
		cto.addPrimaryKey("id");//

	}
}

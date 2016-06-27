package org.cellang.core.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FuzhaiQuanyiBiEntity extends EntityObject {

	public static final String tableName = "fuzhaiquanyibi";
	private String corpId;
	private Date reportDate;
	private BigDecimal fuzhai;
	private BigDecimal quanyi;
	private BigDecimal fzqyb;

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

	public BigDecimal getFuzhai() {
		return fuzhai;
	}

	public void setFuzhai(BigDecimal fuzhai) {
		this.fuzhai = fuzhai;
	}

	public BigDecimal getQuanyi() {
		return quanyi;
	}

	public void setQuanyi(BigDecimal quanyi) {
		this.quanyi = quanyi;
	}

	public BigDecimal getFzqyb() {
		return fzqyb;
	}

	public void setFzqyb(BigDecimal fzqyb) {
		this.fzqyb = fzqyb;
	}
}

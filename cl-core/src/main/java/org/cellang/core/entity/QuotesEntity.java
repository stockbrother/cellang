package org.cellang.core.entity;

import java.math.BigDecimal;

public class QuotesEntity extends EntityObject {

	public static String tableName = "quotes";
	private String symbol;
	private String code;
	private String name;
	private BigDecimal trade;
	private BigDecimal pricechange;
	private BigDecimal changepercent;
	private BigDecimal buy;
	private BigDecimal sell;
	private BigDecimal settlement;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private Double volume;
	private Double amount;
	private String ticktime;
	private Double per;
	private Double pb;
	private Double mktcap;
	private Double nmc;
	private Double turnoverratio;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getTrade() {
		return trade;
	}
	public void setTrade(BigDecimal trade) {
		this.trade = trade;
	}
	public BigDecimal getPricechange() {
		return pricechange;
	}
	public void setPricechange(BigDecimal pricechange) {
		this.pricechange = pricechange;
	}
	public BigDecimal getChangepercent() {
		return changepercent;
	}
	public void setChangepercent(BigDecimal changepercent) {
		this.changepercent = changepercent;
	}
	public BigDecimal getBuy() {
		return buy;
	}
	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}
	public BigDecimal getSell() {
		return sell;
	}
	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}
	public BigDecimal getSettlement() {
		return settlement;
	}
	public void setSettlement(BigDecimal settlement) {
		this.settlement = settlement;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTicktime() {
		return ticktime;
	}
	public void setTicktime(String ticktime) {
		this.ticktime = ticktime;
	}
	public Double getPer() {
		return per;
	}
	public void setPer(Double per) {
		this.per = per;
	}
	public Double getPb() {
		return pb;
	}
	public void setPb(Double pb) {
		this.pb = pb;
	}
	public Double getMktcap() {
		return mktcap;
	}
	public void setMktcap(Double mktcap) {
		this.mktcap = mktcap;
	}
	public Double getNmc() {
		return nmc;
	}
	public void setNmc(Double nmc) {
		this.nmc = nmc;
	}
	public Double getTurnoverratio() {
		return turnoverratio;
	}
	public void setTurnoverratio(Double turnoverratio) {
		this.turnoverratio = turnoverratio;
	}

}

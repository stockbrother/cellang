package org.cellang.corpsviewer.corpdata;

import java.math.BigDecimal;

public class ItemData {

	private ItemDefine define;

	private BigDecimal value;

	public ItemData(ItemDefine def, BigDecimal value) {
		this.define = def;
		this.value = value;
	}

	public ItemDefine getItemDefine() {
		return this.define;
	}

	public BigDecimal getValue() {
		return this.value;
	}

}

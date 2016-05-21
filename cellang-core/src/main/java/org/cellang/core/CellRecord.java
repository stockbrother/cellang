package org.cellang.core;

import org.cellang.common.ObjectUtil;

public class CellRecord {

	private String id;

	private String typeId;

	private CellValue value;

	private long version;

	public String getTypeId() {
		return typeId;
	}

	public CellValue getValue() {
		return value;
	}

	public void setTypeId(String name) {
		this.typeId = name;
	}

	public void setValue(CellValue value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CellRecord)) {
			return false;
		}
		CellRecord ao = (CellRecord) obj;

		return ObjectUtil.isNullSafeEquals(this.typeId, ao.typeId) //
				&& ObjectUtil.isNullSafeEquals(this.value, ao.value)//
				;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

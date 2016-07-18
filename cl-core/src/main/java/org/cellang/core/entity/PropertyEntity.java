package org.cellang.core.entity;

public class PropertyEntity extends EntityObject {

	public static final String tableName = "property";

	private String category;

	private String key;

	private String value;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

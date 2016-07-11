package org.cellang.core.entity;

public class IndexConfig {
	private String indexName;
	private String[] fieldArray;
	private Class<? extends EntityObject> entityCls;

	public IndexConfig(Class<? extends EntityObject> entityCls, String indexName, String[] fieldArray) {
		this.entityCls = entityCls;
		this.indexName = indexName;
		this.fieldArray = fieldArray;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String[] getFieldArray() {
		return fieldArray;
	}

	public void setFieldArray(String[] fieldArray) {
		this.fieldArray = fieldArray;
	}

	public Class<? extends EntityObject> getEntityCls() {
		return entityCls;
	}

	public void setEntityCls(Class<? extends EntityObject> entityCls) {
		this.entityCls = entityCls;
	}
}

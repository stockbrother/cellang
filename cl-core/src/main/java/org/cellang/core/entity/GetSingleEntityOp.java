package org.cellang.core.entity;

public class GetSingleEntityOp<T extends EntityObject> extends EntityOp<T> {
	Class<T> cls;
	String[] fields;
	Object[] args;

	public GetSingleEntityOp() {

	}

	public GetSingleEntityOp<T> set(Class<T> cls, String field, Object arg) {
		return set(cls, new String[] { field }, new Object[] { arg });
	}

	public GetSingleEntityOp<T> set(Class<T> cls, String[] fields, Object[] args) {
		this.cls = cls;
		this.fields = fields;
		this.args = args;
		return this;
	}

	@Override
	public T execute(EntitySession es) {
		return es.getSingle(cls, fields, args);
	}

}

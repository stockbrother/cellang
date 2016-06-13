/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.commons.client.mvc.widget.EditorI;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.Callback;
import org.cellang.clwt.core.client.lang.Handler;

/**
 * @author wu
 * 
 */
public class FieldModel {

	private String name;

	private EditorI editor;

	private Class<?> type;

	private Class<? extends EditorI> editorClass;

	private Object value;

	private Callback< EditorI, Object> editorCallback;
	
	/**
	 * @param name
	 */
	public FieldModel(String name, Class<?> type, Class<? extends EditorI> eclass,Callback< EditorI, Object> editorCallback) {
		this.name = name;
		this.type = type;
		this.editorClass = eclass;
		this.editorCallback = editorCallback;
	}
	public FieldModel(String name, Class<?> type, Class<? extends EditorI> eclass) {
		this(name,type,eclass,null);
	}
	
	public void setEditor(EditorI editor) {
		this.editor = editor;
		if(this.editorCallback!=null){
			this.editorCallback.execute(this.editor);
		}
	}

	public EditorI getEditor(boolean force) {
		if (editor == null && force) {
			throw new UiException("eidtor not init");
		}
		return this.editor;
	}

	public Class<?> getFieldType() {
		return this.type;
	}

	public void setFieldValue(Object fv) {
		this.value = fv;
	}

	public Object getFieldValue() {
		return this.value;
	}

	public Class<? extends EditorI> getEditorClass() {
		return editorClass;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 *Feb 23, 2013
	 */
	public void addValueHandler(Handler<Object> handlerI) {
		//
		throw new UiException("TODO");
		
	}


}

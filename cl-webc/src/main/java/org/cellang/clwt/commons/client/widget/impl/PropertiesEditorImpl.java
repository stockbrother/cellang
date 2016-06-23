/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.widget.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.widget.ChangeEvent;
import org.cellang.clwt.commons.client.widget.EditorI;
import org.cellang.clwt.commons.client.widget.LabelI;
import org.cellang.clwt.commons.client.widget.PropertiesEditorI;
import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.TableI.CellI;
import org.cellang.clwt.commons.client.widget.TableI.RowI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.MapProperties;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.util.ObjectUtil;

import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class PropertiesEditorImpl extends EditorSupport<ObjectPropertiesData> implements PropertiesEditorI {

	protected TableI table;

	protected Map<String, PropertyModel> propertyMap;

	protected List<PropertyModel> propertyList;

	/** */
	public PropertiesEditorImpl(Container c, String name) {
		super(c, name, DOM.createDiv());
		this.propertyList = new ArrayList<PropertyModel>();
		this.propertyMap = new HashMap<String, PropertyModel>();
		this.table = this.factory.create(TableI.class);

		this.appendElement(this.table);
	}

	@Override
	protected ObjectPropertiesData newData() {
		return new ObjectPropertiesData();
	}

	/*
	 * This is called by setData(),it will set the default property in the model
	 * of editor;
	 */
	@Override
	public void setData(ObjectPropertiesData dt) {
		super.setData(dt);

		for (PropertyModel pm : this.propertyList) {
			String key = pm.getKey();
			EditorI e = pm.getEditor(true);
			Object value = dt == null ? null : dt.getProperty(key);//

			Object old = e.getData();

			// to avoid dead loop , reversely,property model's change will
			// cause the data update also.

			if (!ObjectUtil.nullSafeEquals(value, old)) {
				e.setData(value);//
			}
		}

	}

	@Override
	public void addProperty(final PropertyModel pm) {
		final String key = pm.getKey();
		PropertyModel old = this.propertyMap.get(key);
		if (old != null) {
			throw new UiException("property exist:" + key + ",old:" + old);
		}

		this.propertyMap.put(key, pm);

		this.propertyList.add(pm);

		TableI.BodyI body = this.table.getBody();

		Class<? extends EditorI> ecls = pm.getEditorClass();

		RowI r = body.createRow();
		r.getElement().addClassName("properties-editor-row");
		
		LabelI l = this.factory.create(LabelI.class);// key
														// i18n
		String text = this.localized(pm.getI18nKey());
		l.setText(text);//

		CellI cell1 = r.createCell();
		cell1.getElement().addClassName("properties-editor-key");
		cell1.child(l);

		// TODO by type to create editor
		TableI.CellI cel2 = r.createCell();
		cel2.getElement().addClassName("properties-editor-value");
		Map<String,Object> epts = pm.getEditorPts();
		MapProperties<Object> pts = new MapProperties<Object>();
		
		pts.setProperties(epts);
		EditorI<?> editor = (EditorI<?>) this.factory.create(ecls,null, pts);// editor
		editor.setName(key);//
		// class
		cel2.child(editor);

		pm.setEditor(editor);// NOTE TODO remove,this should be replaced by a
								// event
								// notification,

		editor.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent t) {
				//
				PropertiesEditorImpl.this.onChange(key, t);
			}
		});
		//
	}

	/**
	 * Feb 5, 2013
	 */
	protected void onChange(String key, ChangeEvent t) {
		ObjectPropertiesData data = this.getData();
		Object old = data.getProperty(key);
		Object value = t.getData();
		if (ObjectUtil.nullSafeEquals(old, value)) {
			return;
		}
		data.setProperty(key, value);
		this.setData(data, true);
	}

	/**
	 * Nov 18, 2012
	 */
	public void setProperty(String key, Object value) {
		PropertyModel pm = this.getFieldModel(key);

		ObjectPropertiesData data = this.getData();
		if (data == null) {
			data = new ObjectPropertiesData();
		}
		Object old = data.getProperty(key);
		if (!ObjectUtil.nullSafeEquals(old, value)) {
			data.setProperty(key, value);
			this.setData(data, true);// see: processModelDefaultValue();
		}

	}

	@Override
	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype) {
		return this.addFieldModel(key, etype, key);
	}

	@Override
	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype, String i18nKey) {
		return this.addFieldModel(key, etype, null, i18nKey);
	}

	@Override
	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype,
			Map<String, Object> editorPts, String i18nKey) {

		PropertyModel rt = new PropertyModel(key, i18nKey, etype,editorPts);

		this.addProperty(rt);

		return rt;
	}

	@Override
	public List<PropertyModel> getFieldModelList() {
		return this.propertyList;
	}

	/* */
	@Override
	public PropertyModel getFieldModel(String pname) {
		return this.propertyMap.get(pname);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI#
	 * getPropertyEditor(java.lang.String)
	 */
	@Override
	public EditorI getPropertyEditor(String name) {
		PropertyModel pm = this.getFieldModel(name);
		return pm.getEditor(true);

	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public void setFieldValue(String fname, Object v) {
		EditorI edt = this.getPropertyEditor(fname);
		edt.setData(v);
	}

}

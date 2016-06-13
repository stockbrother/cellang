/**
 * Jul 1, 2012
 */
package org.cellang.clwt.commons.client.mvc.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.core.client.data.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public interface PropertiesEditorI extends EditorI<ObjectPropertiesData> {

	public class PropertyModel {
		private String name;
		private EditorI editor;
		private Class<? extends EditorI> editorClass;
		private String i18nKey;
		private Map<String, Object> editorPts;

		public String getI18nKey() {
			return i18nKey;
		}

		/**
		 * @param name
		 */
		public PropertyModel(String name, String i18nKey, Class<? extends EditorI> editorClass,
				Map<String, Object> editorPts) {
			this.name = name;
			this.i18nKey = i18nKey;
			this.editorClass = editorClass;
			this.editorPts = editorPts == null ? new HashMap<String, Object>() : editorPts;
		}

		public String getKey() {
			return this.name;
		}

		public void setEditor(EditorI editor) {
			this.editor = editor;
		}

		public EditorI getEditor(boolean force) {
			return this.editor;
		}

		public Class<? extends EditorI> getEditorClass() {
			return this.editorClass;
		}

		/**
		 * @return the editorPts
		 */
		public Map<String, Object> getEditorPts() {
			return editorPts;
		}

	}

	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype);

	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype, String i18nKey);

	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype,
			Map<String, Object> edtPts, String i18nKey);

	public PropertyModel getFieldModel(String key);

	public List<PropertyModel> getFieldModelList();

	public EditorI getPropertyEditor(String name);

	public void setFieldValue(String fname, Object v);

	public void addProperty(final PropertyModel pm);

}

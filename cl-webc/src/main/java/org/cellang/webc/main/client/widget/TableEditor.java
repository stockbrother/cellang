package org.cellang.webc.main.client.widget;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.widget.EditorI;
import org.cellang.webc.main.client.data.TableData;

import com.google.gwt.editor.client.Editor;

public interface TableEditor extends Editor<TableData> {
	public class ColumnModel {
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
		public ColumnModel(String name, String i18nKey, Class<? extends EditorI> editorClass,
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

	public ColumnModel addColumnModel(String key, Class<? extends EditorI> etype);

	public List<ColumnModel> getColumnModelList();

	public void addRowData(List<Object> row);

}

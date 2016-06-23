package org.cellang.webc.main.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.widget.EditorI;
import org.cellang.clwt.commons.client.widget.LabelI;
import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.TableI.RowI;
import org.cellang.clwt.commons.client.widget.impl.BooleanEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.DateEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.EditorSupport;
import org.cellang.clwt.commons.client.widget.impl.IntegerEditorImpl;
import org.cellang.clwt.commons.client.widget.impl.StringEditorImpl;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.DateData;
import org.cellang.webc.main.client.data.TableData;

import com.google.gwt.user.client.DOM;

public class TableEditorImpl extends EditorSupport<TableData> implements TableEditor {

	protected TableI table;
	protected List<ColumnModel> columnList;

	private static Map<String, Class> typeMap = new HashMap<String, Class>();

	private static Map<Class, Class<? extends EditorI>> editorMap = new HashMap<Class, Class<? extends EditorI>>();

	static {
		typeMap.put("string", String.class);
		typeMap.put("boolean", Boolean.class);
		typeMap.put("integer", Integer.class);
		typeMap.put("datetime", DateData.class);
	}
	static {
		editorMap.put(String.class, StringEditorImpl.class);
		editorMap.put(Boolean.class, BooleanEditorImpl.class);
		editorMap.put(Integer.class, IntegerEditorImpl.class);
		editorMap.put(DateData.class, DateEditorImpl.class);

	}

	public TableEditorImpl(Container c, String name) {

		super(c, name, DOM.createDiv());
		this.columnList = new ArrayList<ColumnModel>();
		this.table = this.factory.create(TableI.class);

		this.appendElement(this.table);
	}

	@Override
	public void setData(TableData d) {
		super.setData(d);

		// Header
		int cols = d.getCols();
		for (int i = 0; i < cols; i++) {
			String key = d.getColumnName(i);
			String colType = d.getColumnType(i);
			Class typeClass = typeMap.get(colType);
			if (typeClass == null) {
				throw new RuntimeException("not supported:" + colType);
			}
			Class<? extends EditorI> etype = editorMap.get(typeClass);
			if (etype == null) {
				throw new RuntimeException("not supported:" + colType);
			}
			this.addColumnModel(key, etype);

		}
		// Body
		int rows = d.getRows();
		for (int i = 0; i < rows; i++) {
			List<Object> row = d.getRow(i);
			this.addRowData(row);
		}
	}

	@Override
	public ColumnModel addColumnModel(String key, Class<? extends EditorI> etype) {
		ColumnModel rt = new ColumnModel(key, null, etype, null);
		this.columnList.add(rt);//
		TableI.HeaderI h = this.table.getHeaders().createHeader(key);
		LabelI l = this.factory.create(LabelI.class);
		l.setText(key);//
		h.child(l);
		return rt;
	}

	@Override
	public List<ColumnModel> getColumnModelList() {
		return this.columnList;
	}

	@Override
	public void addRowData(List<Object> row) {
		RowI r = this.table.getBody().createRow();
		for (int i = 0; i < row.size(); i++) {
			Object dataI = row.get(i);
			TableI.CellI c = r.createCell();
			ColumnModel cm = this.columnList.get(i);
			EditorI editor = (EditorI) this.factory.create(cm.getEditorClass());// editor
			editor.setData(dataI);
			c.child(editor);
		}

	}

}

package org.cellang.webc.main.client.widget;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.commons.client.mvc.simple.SimpleView;
import org.cellang.clwt.commons.client.widget.StringEditorI;
import org.cellang.clwt.commons.client.widget.TableI;
import org.cellang.clwt.commons.client.widget.TableI.CellI;
import org.cellang.clwt.commons.client.widget.TableI.RowI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.webc.main.client.Actions;

public class CreateTableView extends SimpleView {
	TableI table;
	private List<StringEditorI> nameList = new ArrayList<StringEditorI>();
	private List<StringEditorI> typeList = new ArrayList<StringEditorI>();

	public CreateTableView(Container ctn) {
		super(ctn);
		this.addAction(Actions.A_TABLE_ADDCOLUMN);
		this.addAction(Actions.A_TABLE_SAVE);
		table = this.factory.create(TableI.class);
		this.appendElement(table);//
		this.addHandler(ActionEvent.TYPE, new EventHandlerI<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				if (t.getActionPath().equals(Actions.A_TABLE_ADDCOLUMN)) {
					CreateTableView.this.addColumn();
				} else if (t.getActionPath().equals(Actions.A_TABLE_SAVE)) {
					CreateTableView.this.save();
				}
			}
		});
	}

	protected void save() {
		MsgWrapper req = new MsgWrapper(Path.valueOf("table.create.request"));
		List<ObjectPropertiesData> nameL = new ArrayList<ObjectPropertiesData>();
		for (int i = 0; i < nameList.size(); i++) {
			String name = nameList.get(i).getData();
			String type = typeList.get(i).getData();
			ObjectPropertiesData col = new ObjectPropertiesData();
			col.setProperty("name", name);
			col.setProperty("type", type);
			nameL.add(col);

		}
		req.setPayload("columnList", nameL);

		this.getClient(true).getLogicalChannel(true).sendMessage(req);//

	}

	protected void addColumn() {
		RowI r = this.table.getBody().createRow();
		CellI c1 = r.createCell();
		StringEditorI se1 = this.factory.create(StringEditorI.class);
		c1.child(se1);
		CellI c2 = r.createCell();
		StringEditorI se2 = this.factory.create(StringEditorI.class);
		c2.child(se2);

		this.nameList.add(se1);
		this.typeList.add(se2);
	}

}

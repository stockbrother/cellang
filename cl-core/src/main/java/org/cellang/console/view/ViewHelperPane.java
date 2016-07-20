package org.cellang.console.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.control.ActionHandler;
import org.cellang.console.control.ColumnAppendable;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.Descriable;
import org.cellang.console.control.FilterPane;
import org.cellang.console.control.Filterable;
import org.cellang.console.control.ValueChangeListener;
import org.cellang.console.model.TableDataProvider;

public class ViewHelperPane extends HelperPane<View> {

	public ViewHelperPane() {
	}
	
	public void setContextObject(View view){
		// if the view support query
		super.setContextObject(view);
		if(view == null){
			return;
		}
		TableDataProvider<?> dp = view.getDelegate(TableDataProvider.class);
		if (dp == null) {
			return;
		}

		DataPageQuerable dpq = dp.getDelegate(DataPageQuerable.class);
		if (dpq != null) {
			this.addAction("<<", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.prePage();
				}
			});

			this.addAction(">>", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.nextPage();
				}
			});
			
			this.addAction("Refresh", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.refresh();
				}
			});

		}

		// if the view contains description
		Descriable des = dp.getDelegate(Descriable.class);
		if (des != null) {
			Map<String, Object> desMap = new HashMap<>();
			des.getDescription(desMap);
			this.addText(desMap);// TODO

		}
		// if the view support fitler
		Filterable fil = dp.getDelegate(Filterable.class);
		if (fil != null) {

			this.addFilter(new FilterPane(fil));
		}
		// add new column to table.
		ColumnAppendable ce = dp.getDelegate(ColumnAppendable.class);
		if (ce != null) {
			List<String> nameL = ce.getExtenableColumnList();
			if (!nameL.isEmpty()) {

				addDropDownList(nameL, new ValueChangeListener<String>() {

					@Override
					public void valueChanged(String value) {
						ce.appendColumn(value);//
					}
				});
			}
		}

	}

}

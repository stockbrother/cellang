package org.cellang.console.view.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.console.HasDelagates;
import org.cellang.console.control.Action;
import org.cellang.console.control.ActionHandler;
import org.cellang.console.control.ColumnAppendable;
import org.cellang.console.control.ColumnOrderable;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.Descriable;
import org.cellang.console.control.Favoriteable;
import org.cellang.console.control.Filterable;
import org.cellang.console.control.Refreshable;
import org.cellang.console.control.ValueChangeListener;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.console.view.table.TableDataProvider;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.FavoriteActionEntity;

public class ViewHelperPane extends HelperPane<View> {
	EntitySessionFactory esf;

	public ViewHelperPane() {
	}

	public void setContextObject(View view) {
		// if the view support query
		super.setContextObject(view);
		if (view == null) {
			return;
		}
		TableDataProvider dpa = view.getDelegate(TableDataProvider.class);
		if (dpa == null) {
			return;
		}
		if (dpa instanceof HasDelagates) {
			HasDelagates dp = (HasDelagates) dpa;
			DataPageQuerable dpq = dp.getDelegate(DataPageQuerable.class);
			if (dpq != null) {
				
				this.addAction(new Action() {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "<<";
					}

					@Override
					public void perform() {
						dpq.prePage();

					}
				});
				
				this.addAction(new Action() {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return ">>";
					}

					@Override
					public void perform() {
						dpq.nextPage();

					}
				});

			}
			Refreshable rfs = dp.getDelegate(Refreshable.class);
			if (rfs != null) {
				
				this.addAction(new Action() {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Refresh";
					}

					@Override
					public void perform() {
						rfs.refresh();

					}
				});

			}

			Favoriteable fv = dp.getDelegate(Favoriteable.class);
			if (fv != null) {

				this.addAction(new Action() {

					@Override
					public String getName() {
						// TODO Auto-generated method stub
						return "Add to Favorite";
					}

					@Override
					public void perform() {
						ViewHelperPane.this.addToFavorite(fv);

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

					addDropDownList("Add Column", nameL, new ValueChangeListener<String>() {

						@Override
						public void valueChanged(String value) {
							ce.appendColumn(value);//
						}
					});
				}
			}
			ColumnOrderable co = dp.getDelegate(ColumnOrderable.class);
			if (co != null) {
				List<String> nameL = co.getOrderableColumnList();
				if (!nameL.isEmpty()) {

					addDropDownList("Order By", nameL, new ValueChangeListener<String>() {

						@Override
						public void valueChanged(String value) {
							co.setOrderBy(value);//
						}
					});
				}
			}
		}
	}

	protected void addToFavorite(Favoriteable fv) {
		String type = fv.getFavoriteType();
		String content = fv.getFavoriteContent();
		FavoriteActionEntity ae = new FavoriteActionEntity();
		ae.setId(UUIDUtil.randomStringUUID());
		ae.setContent(content);
		ae.setName("Favorite View");
		ae.setDescription("Favorite View Description");
		ae.setType(type);
		this.esf.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				es.save(ae);
				return null;
			}
		});
	}

	public void install(OperationContext oc) {
		this.esf = oc.getEntityService();
	}

}

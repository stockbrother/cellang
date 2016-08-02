package org.cellang.console.control.entity;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.control.SelectionListener;
import org.cellang.console.ext.CorpP_EBITDAExtendingProperty;
import org.cellang.console.ext.CorpP_EExtendingProperty;
import org.cellang.console.ext.CorpROEExtendingPropertyDefine;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.console.view.report.ReportTableView;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.InterestedCorpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see EntityConfigManager
 * @author wu
 *
 */
public class CorpInfoEntityConfigControl extends EntityConfigControl<CorpInfoEntity> implements HasActions {

	private static final Logger LOG = LoggerFactory.getLogger(CorpInfoEntityConfigControl.class);
	EntitySessionFactory entitySessions;
	OperationContext oc;

	public CorpInfoEntityConfigControl(OperationContext oc, EntitySessionFactory entitySessions) {
		this.oc = oc;
		this.entitySessions = entitySessions;

		this.addExtendingProperty(new CorpP_EExtendingProperty(1), true);
		this.addExtendingProperty(new CorpP_EExtendingProperty(5), true);
		this.addExtendingProperty(new CorpP_EBITDAExtendingProperty(1), true);
		this.addExtendingProperty(new CorpP_EBITDAExtendingProperty(5), true);
		this.addExtendingProperty(new CorpROEExtendingPropertyDefine(1), true);
		this.addExtendingProperty(new CorpROEExtendingPropertyDefine(5), true);

	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (HasActions.class.equals(cls)) {
			return (T) this;
		} else if (SelectionListener.class.equals(cls)) {
			return (T) this;
		} else if (EntitySessionFactory.class.equals(cls)) {
			return (T) this.entitySessions;
		}
		return null;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		if (!(context instanceof CorpInfoEntity)) {
			return al;
		}
		al.add(new Action() {

			@Override
			public String getName() {
				return "Add to Interested";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.addToInterested((CorpInfoEntity) context);
			}
		});
		al.add(new Action() {

			@Override
			public String getName() {
				return "Open B/S Report";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.openBSReport((CorpInfoEntity) context);
			}
		});
		al.add(new Action() {

			@Override
			public String getName() {
				return "Open Customized Report";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.openCustomizedReport((CorpInfoEntity) context);
			}
		});

		return al;
	}

	protected void openCustomizedReport(CorpInfoEntity context) {
		ReportItemLocators.Group template = ReportItemLocators.getInstance().get(CustomizedReportEntity.class);
		View v = new ReportTableView(oc, template, oc.getReportConfigFactory().customizedReportConfig,
				this.entitySessions, 10, context.getId());
		oc.getViewManager().addView(v, true);
	}

	protected void openBSReport(CorpInfoEntity context) {
		ReportItemLocators.Group template = ReportItemLocators.getInstance().get(BalanceSheetReportEntity.class);
		View v = new ReportTableView(oc, template, oc.getReportConfigFactory().balanceSheetReportConfig,
				this.entitySessions, 10, context.getId());
		oc.getViewManager().addView(v, true);
	}

	protected void addToInterested(CorpInfoEntity ce) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("add corp:" + ce.getCode() + " as interested.");
		}
		this.entitySessions.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				InterestedCorpEntity ic = new InterestedCorpEntity();
				ic.setId(ce.getId());//
				ic.setCorpId(ce.getId());//
				es.save(ic);
				return null;
			}
		});
	}

}

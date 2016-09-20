package org.cellang.console.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.cellang.console.control.Action;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.console.view.report.ReportTemplateRow;
import org.cellang.console.view.report.ReportTemplateRowChartDataProvider;
import org.cellang.console.view.report.ReportTemplateRowChartView;
import org.cellang.console.view.report.ReportTemplateTableView;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.metrics.ReportConfig;

public class MenuBar extends JMenuBar {

	public static class ReportTemplateAction extends Action {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void perform() {

		}

	}

	public static class Menu extends JMenu {
		public Menu(String title) {
			super(title);
		}

		public void addItem(Action action) {
			JMenuItem mi = new JMenuItem(action.getName());
			mi.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					action.perform();
				}
			});
			this.add(mi);
		}

	}

	public MenuBar(OperationContext oc) {
		Menu file = this.addMenu("File");
		file.addItem(new Action() {

			@Override
			public String getName() {

				return "Report Template(Balance)";

			}

			@Override
			public void perform() {
				
				ReportConfig rc = oc.getReportConfigFactory().balanceSheetReportConfig;
				View v = new ReportTemplateTableView(oc, rc);
				oc.getViewManager().addView(1, v, true);
			}
		});

		file.addItem(new Action() {

			@Override
			public String getName() {

				return "Report Template(Other)";

			}

			@Override
			public void perform() {				
				ReportConfig rc = oc.getReportConfigFactory().customizedReportConfig;
				View v = new ReportTemplateTableView(oc, rc);
				oc.getViewManager().addView(1, v, true);
			}
		});

		file.addItem(new Action() {

			@Override
			public String getName() {
				return "Template Chart";
			}

			@Override
			public void perform() {
				ReportTemplateRow rtr = oc.getReportTemplateRow();
				ReportTemplateRowChartDataProvider chartDp = new ReportTemplateRowChartDataProvider(oc);
				chartDp.addReportRow(rtr);
				ReportTemplateRowChartView cv = new ReportTemplateRowChartView(chartDp);
				oc.getViewManager().addView(2, cv, true);

			}
		});

	}

	public Menu addMenu(String title) {
		Menu menu = new Menu(title);
		this.add(menu);
		return menu;
	}

}

package org.cellang.corpsviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.cellang.corpsviewer.actions.OpenAddingCorpGroupViewAction;
import org.cellang.corpsviewer.actions.OpenBalanceSheetTemplateAction;
import org.cellang.corpsviewer.actions.OpenCorpGroupAction;
import org.cellang.corpsviewer.actions.OpenCorpListAction;
import org.cellang.corpsviewer.actions.OpenCunHuoTurnOverAction;
import org.cellang.corpsviewer.actions.OpenTemplateChart;
import org.cellang.corpsviewer.actions.OpenTemplateTableAction;
import org.cellang.corpsviewer.actions.RefreshAllViewAction;
import org.cellang.corpsviewer.myfavorites.OpenBalanceSheetAction;
import org.cellang.corpsviewer.myfavorites.OpenCashFlowStatementAction;
import org.cellang.corpsviewer.myfavorites.OpenIncomeStatementAction;
import org.cellang.corpsviewer.myfavorites.OpenMyFavoritesListViewAction;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

public class MenuBar extends JMenuBar {

	public static class Menu extends JMenu {
		Map<String, Action> actionMap = new HashMap<>();

		public Menu(String title) {
			super(title);
		}

		public void addItem(Action action) {
			String key = action.getName();
			if (this.actionMap.containsKey(key)) {
				throw new RuntimeException("duplicated.");
			}
			this.actionMap.put(key, action);
			JMenuItem mi = new JMenuItem(key);
			mi.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					action.perform();
				}
			});
			this.add(mi);
		}

		public Action getAction(String key) {
			return this.actionMap.get(key);
		}

	}

	private Map<String, Menu> menuMap = new HashMap<>();

	public MenuBar(OperationContext oc) {

		Menu//
		menu = this.addMenu("Analysis");
		{
			menu.addItem(new OpenBalanceSheetTemplateAction(oc));
			menu.addItem(new OpenTemplateTableAction(oc));
			menu.addItem(new OpenTemplateChart(oc));
		}

		menu = this.addMenu("Operation");
		{
			menu.addItem(new RefreshAllViewAction(oc));
		}

		menu = this.addMenu("Groups");
		{
			menu.addItem(new OpenCorpGroupAction(oc));
			menu.addItem(new OpenAddingCorpGroupViewAction(oc));
			menu.addItem(new DeleteCorpGroupAction(oc));
		}

		menu = this.addMenu("Corps");
		{
			menu.addItem(new OpenMyFavoritesListViewAction(oc));
			menu.addItem(new AddToMyFavoritesAction(oc));
			menu.addItem(new OpenCorpListAction(oc));
		}

		menu = this.addMenu("Reports");
		{
			menu.addItem(new OpenBalanceSheetAction(oc));
			menu.addItem(new OpenIncomeStatementAction(oc));
			menu.addItem(new OpenCashFlowStatementAction(oc));
		}
		menu = this.addMenu("List");
		{
			menu.addItem(new OpenCunHuoTurnOverAction(oc));
		}

	}

	public <T extends Action> void executeAction(Class<T> cls) {
		T a = this.getMenuItemAction(cls);
		if (a == null) {
			throw new RuntimeException("no this action type:" + cls);
		}
		a.perform();
	}

	public <T extends Action> T getMenuItemAction(Class<T> cls) {

		for (Menu m : this.menuMap.values()) {
			for (Action a : m.actionMap.values()) {
				if (cls.isInstance(a)) {
					return (T) a;
				}
			}
		}

		return null;
	}

	public Action getMenuItemAction(String[] path) {
		Menu m = this.menuMap.get(path[0]);

		if (m == null) {
			return null;
		}
		return m.getAction(path[1]);//
	}

	public Menu addMenu(String title) {
		Menu menu = new Menu(title);
		if (menuMap.containsKey(title)) {
			throw new RuntimeException("duplicated");
		}
		this.menuMap.put(title, menu);
		super.add(menu);
		return menu;
	}

}

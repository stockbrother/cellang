package org.cellang.console.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.cellang.console.control.Action;
import org.cellang.console.corpgrouping.AddToMyFavoritesAction;
import org.cellang.console.corpgrouping.DeleteCorpGroupAction;
import org.cellang.console.ops.OperationContext;

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
		menu = this.addMenu("File");
		{
			menu.addItem(new OpenCorpListAction(oc));
			menu.addItem(new OpenCorpGroupAction(oc));
			menu.addItem(new OpenAddingCorpGroupViewAction(oc));
			menu.addItem(new OpenBalanceSheetTemplateAction(oc));
			menu.addItem(new OpenTemplateTableAction(oc));
			menu.addItem(new OpenTemplateChart(oc));
		}
		menu = this.addMenu("Edit");
		{
			menu.addItem(new DeleteCorpGroupAction(oc));
			menu.addItem(new RefreshAllViewAction(oc));

		}
		menu = this.addMenu("My Favorites");
		{
			menu.addItem(new OpenMyFavoritesListViewAction(oc));
			menu.addItem(new AddToMyFavoritesAction(oc));
		}
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

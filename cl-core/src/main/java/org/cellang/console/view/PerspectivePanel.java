package org.cellang.console.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

public class PerspectivePanel extends JSplitPane {

	List<ViewGroupPanel> viewGroupList = new ArrayList<ViewGroupPanel>();
	List<JSplitPane> splitPaneList = new ArrayList<>();

	List<ViewAddListener> llist = new ArrayList<ViewAddListener>();
	List<ViewRemoveListener> removeLList = new ArrayList<>();
	public PerspectivePanel() {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.setContinuousLayout(true);//

	}

	public void addView(View v, boolean b) {
		addView(0, v, b);
	}

	public void addView(int group, View v, boolean b) {

		while (viewGroupList.size() <= group) {

			JSplitPane parent = this;
			if (splitPaneList.size() > 0) {
				parent = splitPaneList.get(splitPaneList.size() - 1);
			}

			ViewGroupPanel vp = new ViewGroupPanel(this);
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			//split.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			//split.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			split.setBorder(null);
			split.setLeftComponent(vp);
			parent.setRightComponent(split);

			this.splitPaneList.add(split);//
			this.viewGroupList.add(vp);
		}

		ViewGroupPanel vp = this.viewGroupList.get(group);
		vp.addView(v, b);
		for (ViewAddListener l : llist) {
			l.viewAdded(v);
		}
	}

	public void addViewAddListener(ViewAddListener vl) {
		this.llist.add(vl);
	}

	public void viewRemoved(View v) {
		for (ViewRemoveListener l : removeLList) {
			l.viewRemoved(v);
		}
	}

}

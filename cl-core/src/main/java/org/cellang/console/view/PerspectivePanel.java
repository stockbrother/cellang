package org.cellang.console.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;

public class PerspectivePanel extends JSplitPane {

	List<ViewGroupPanel> viewGroupList = new ArrayList<ViewGroupPanel>();
	List<JSplitPane> splitPaneList = new ArrayList<>();
	Object context;

	public PerspectivePanel(Object context) {
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.context = context;
		this.setContinuousLayout(true);//
	}

	public void addView(View v) {
		addView(0, v, true);
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

			ViewGroupPanel vp = new ViewGroupPanel(this.context, this);
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			// split.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			// split.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			split.setBorder(null);
			split.setLeftComponent(vp);
			parent.setRightComponent(split);

			this.splitPaneList.add(split);//
			this.viewGroupList.add(vp);
		}

		ViewGroupPanel vp = this.viewGroupList.get(group);
		vp.addView(v, b);

	}

	public void viewRemoved(View v) {

	}

}

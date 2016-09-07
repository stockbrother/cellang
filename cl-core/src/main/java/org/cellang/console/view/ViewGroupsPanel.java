package org.cellang.console.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;

public class ViewGroupsPanel extends JSplitPane {

	List<ViewGroupPanel> viewGroupList = new ArrayList<ViewGroupPanel>();
	
	List<ViewAddListener> llist = new ArrayList<ViewAddListener>();
	List<ViewRemoveListener> removeLList = new ArrayList<>();
	
	public ViewGroupsPanel(){		
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.setContinuousLayout(true);//		
	}
	
	public void addView(View v, boolean b) {
		if(viewGroupList.isEmpty()){
			ViewGroupPanel vp = new ViewGroupPanel(this);			
			this.add(vp);
			this.viewGroupList.add(vp);			
		}
		
		ViewGroupPanel vp = this.viewGroupList.get(0);
		vp.addView(v, b);
		for (ViewAddListener l : llist) {
			l.viewAdded(v);
		}
	}

	public void clear() {
		
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

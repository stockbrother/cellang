package org.cellang.console.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.console.control.HasActions;

public class AbstractView extends JScrollPane implements View {

	protected String title;
	protected String id;
	protected boolean selected;
	List<ViewSelectionListener> llist = new ArrayList<>();

	public AbstractView(String title) {
		this.title = title;
		this.id = UUIDUtil.randomStringUUID();
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {

		if (cls.equals(HasActions.class)) {
			if (this instanceof HasActions) {
				return (T) this;
			}
		}

		return null;
	}

	@Override
	public String getId() {
		return this.id;
	}
	@Override
	public void addViewSelectionListener(ViewSelectionListener vsl){
		this.llist.add(vsl);
	}
	
	@Override
	public void select(boolean sel) {
		
		if(this.selected == sel){
			return;
		}
		this.selected = sel;
		if(this.selected){
			for (ViewSelectionListener vL : this.llist) {
				vL.viewSelected(this);
			}			
		}

	}

}

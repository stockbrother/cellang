/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.stack;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.commons.client.widget.LayoutSupport;
import org.cellang.clwt.commons.client.widget.StackItemI;
import org.cellang.clwt.commons.client.widget.StackWI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.WebElement;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class StackWImpl extends LayoutSupport implements StackWI {

	private List<StackItemI> itemList;

	/**
	 * @param ele
	 */
	public StackWImpl(Container c, String name, HasProperties<Object> pts) {
		super(c, name, DOM.createDiv(), pts);
		this.itemList = new ArrayList<StackItemI>();

	}

	@Override
	public StackItemI getSelected(boolean force) {

		List<StackItemI> iml = this.itemList;
		StackItemI rt = null;
		for (StackItemI im : iml) {
			if (im.isSelect()) {
				if (rt != null) {
					throw new UiException("bug,more than one item is selected");
				}
				rt = im;

			}
		}

		if (force && rt == null) {
			throw new UiException("no selected item in stack");
		}
		return rt;

	}

	@Override
	public StackItemI getDefaultItem(boolean force) {

		List<StackItemI> iml = this.itemList;
		StackItemI rt = null;
		for (StackItemI im : iml) {
			if (((StackItemRef) im).isDefaultItem()) {
				if (rt != null) {
					throw new UiException("bug,more than one item is default");
				}
				rt = im;

			}
		}

		if (force && rt == null) {
			throw new UiException("no default item in stack");
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.widget.stack.StackWI#insert(com.fs.uicore
	 * .api.gwt.client.core.WidgetI)
	 */
	@Override
	public StackItemRef insert(Path path, WebWidget child, boolean select) {
		if (null != this.getByPath(path, false)) {
			throw new UiException("already exist:" + path);
		}
		StackItemRef rt = new StackItemRef(path, this, child);

		this.itemList.add(rt);// NOTE,rt is the child of the widget's model
		rt.select(select);
		child.setProperty("_item_path", path);
		this.appendElement(child);

		return rt;
	}

	@Override
	public StackItemI getByPath(Path path, boolean force) {
		for (StackItemI s : this.itemList) {
			if (s.getPath().equals(path)) {
				return s;
			}
		}
		if (force) {
			throw new UiException("no item found for path:" + path);
		}
		return null;

	}

	@Override
	public void remove(Path path) {
		StackItemI si = this.getByPath(path, false);
		this.removeElement(si.getManagedWidget());//
		this.itemList.remove(si);

	}

	@Override
	public void appendElement(WebElement cw) {
		Element ele = DOM.createDiv();
		DOM.appendChild(ele, cw.getElement());//
		DOM.appendChild(this.element, ele);
	}

	@Override
	public void removeElement(WebElement cw) {
		Element ce = cw.getElement();
		//DIV as the shell,parent is DIV.
		ce.getParentElement().removeFromParent();
	}

	@Override
	public void updateSelect(StackItemI im) {

		// reset selected

		boolean sel = im.isSelect();//

		if (sel) {// push

			List<StackItemI> iml = this.itemList;
			for (StackItemI imm : iml) {
				if (imm != im) {// unselect all the other
								// item if its selected.
					((StackItemRef) imm).trySelect(false);//
				}
			}
		} else {// unselect,do nothing.
				// TODO may be to find the item to be selected.
				// this.getSelected(false);

		}

		ElementWrapper ew = im.getManagedWidget().getElementWrapper();

		Style style = ew.getStyle();
		Display dis = sel ? Display.BLOCK : Display.NONE;
		style.setDisplay(dis);//

		/**
		 * 
		 * String cname = "position-selected"; String cname2 =
		 * "position-unselected";
		 * 
		 * ew.addClassName(sel ? cname : cname2);// selected
		 * ew.removeClassName(sel ? cname2 : cname);// unselected
		 */

	}

	@Override
	public int getSize() {
		return this.itemList.size();
	}

}

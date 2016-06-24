/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package org.cellang.clwt.commons.client.mvc.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.mvc.support.ViewSupport;
import org.cellang.clwt.commons.client.widget.ButtonI;
import org.cellang.clwt.commons.client.widget.ErrorInfosWidgetI;
import org.cellang.clwt.commons.client.widget.ListI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.event.ClickEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Path;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;

/**
 * @author wu display model as text
 */
public class SimpleView extends ViewSupport {

	protected ListI actionListInFooter;//
	protected ListI actionListInHeader;//
	protected Map<Path, ButtonI> actionMap;

	private ErrorInfosWidgetI errorInfoDisplay;

	protected Element header;

	protected Element body;

	protected Element footer;

	/**
	 * @param ctn
	 */
	public SimpleView(Container ctn) {
		this(ctn, null);
	}

	public SimpleView(Container ctn, String name) {
		this(ctn, name, DOM.createDiv());
	}

	public SimpleView(Container c, String name, Element ele) {

		super(c, name, ele);

		this.actionMap = new HashMap<Path, ButtonI>();

		// BODY:
		this.header = DOM.createDiv();
		DOM.appendChild(this.element, header);
		this.header.addClassName("position-header");
		// BODY:
		this.body = DOM.createDiv();
		DOM.appendChild(this.element, body);
		this.body.addClassName("position-body");
		// BODY:
		this.footer = DOM.createDiv();
		DOM.appendChild(this.element, footer);
		this.footer.addClassName("position-footer");

		// TODO header
		this.errorInfoDisplay = this.factory.create(ErrorInfosWidgetI.class);
		this.errorInfoDisplay.setProperty("_parentElement", this.header);
		this.appendElement(this.errorInfoDisplay);
		// footer:

		this.actionListInFooter = this.factory.create(ListI.class);
		this.actionListInFooter.setProperty("_parentElement", this.footer);
		this.appendElement(this.actionListInFooter);
		

		this.actionListInHeader = this.factory.create(ListI.class);
		this.actionListInHeader.setProperty("_parentElement", this.header);
		this.appendElement(this.actionListInHeader);

	}

	public ButtonI addAction(final Path aname) {
		return this.addAction(aname, false);
	}

	public ButtonI addAction(final Path aname, ListI parent) {
		return this.addAction(aname, false, parent);
	}

	public ButtonI addAction(final Path aname, boolean hide) {
		return this.addAction(aname, hide, this.actionListInFooter);
	}
	
	//TODO addAction with handler.
	
	public ButtonI addAction(final Path aname, boolean hide, ListI parent) {
		// listen to the button clicked event,which is button state is changed.
		ButtonI b = this.actionMap.get(aname);
		if (b != null) {
			throw new UiException("already exist action:" + name + " in view:" + this);
		}

		b = this.factory.create(ButtonI.class);// TODO,
		b.setText(true, aname + "");

		parent.appendElement(b);
		// click event is raised in button,not button's model
		b.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				SimpleView.this.dispatchActionEvent(aname);
			}
		});
		this.actionMap.put(aname, b);
		this.hideAction(aname, hide);

		return b;
	}

	protected void hideAction(Path aname, boolean hide) {
		ButtonI b = this.actionMap.get(aname);
		if (b == null) {
			throw new UiException("no action:" + aname + " in view:" + this);
		}
		b.setVisible(!hide);
	}

	/**
	 * @Override protected void onAddChild(Element pe, ElementObjectI cw) {
	 *           Element parent = (Element) cw.getProperty("_parentElement"); if
	 *           (parent == null) { parent = this.body;// }
	 * 
	 *           super.onAddChild(parent, cw); }
	 **/

	protected List<Path> getActionList() {
		return new ArrayList<Path>(this.actionMap.keySet());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ViewI#clickAction(java.lang.String)
	 */
	@Override
	public void clickAction(final Path apath) {
		// click action is used for testing code,that should be the simulation
		// of human click.
		// for some test case,when child model is adding to the parent child,the
		// event will raise in a none-consistant situation.
		// We should consider to do deferred command for any event dispatching.
		DeferredCommand.add(new Command() {

			@Override
			public void execute() {
				SimpleView.this.doClickAction(apath);
			}
		});
	}

	private void doClickAction(Path ap) {
		ButtonI ab = this.actionMap.get(ap);
		if (ab == null) {
			throw new UiException("widget not found for action:" + ap + " in view:" + this);
		}
		ab.getElementWrapper().click();
	}

	@Override
	public void addErrorInfo(ErrorInfosData eis) {
		this.errorInfoDisplay.addErrorInfos(eis);
	}

	@Override
	public void clearErrorInfo() {
		this.errorInfoDisplay.clear();
	}

}

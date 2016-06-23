/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.basic;

import org.cellang.clwt.commons.client.widget.ButtonI;
import org.cellang.clwt.commons.client.widget.WidgetSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.ClickEvent;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.State;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ButtonImpl extends WidgetSupport implements ButtonI {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(ButtonImpl.class);

	private State state;

	private boolean disable;

	public ButtonImpl(Container c, String name) {
		super(c, name, (Element) Document.get().createPushButtonElement().cast());
		this.addGwtHandler(com.google.gwt.event.dom.client.ClickEvent.getType(), new ClickHandler() {

			@Override
			public void onClick(com.google.gwt.event.dom.client.ClickEvent event) {
				ButtonImpl.this.onGwtClick(event);
			}//
		});
	}

	@Override
	public void setText(boolean toloc, String txt) {
		String title = null;
		String txt2 = txt;
		if (toloc) {
			String tkey = Path.valueOf("tip").concat(Path.valueOf(txt)).toString();
			txt2 = this.localized(txt);//
			title = this.localized(tkey);
		}
		Element ele = this.getElement();

		ele.setInnerText(txt2);//
		if (title != null) {
			ele.setTitle(title);// TODO replace this
		}

	}

	public void onGwtClick(com.google.gwt.event.dom.client.ClickEvent event) {
		LOG.info("onGwtClick,event:" + event);
		if (this.disable) {
			return;
		}
		this.switchState();
		new ClickEvent(this).dispatch();
	}

	public void switchState() {
		State s = this.getState();

		if (ButtonI.UP.equals(s)) {
			this.setState(ButtonI.DOWN);
		} else {
			this.setState(ButtonI.UP);
		}
	}

	public void setState(State s) {
		this.state = s;
	}

	@Override
	public State getState() {

		return this.state;

	}

	/*
	 * Mar 14, 2013
	 */
	@Override
	public void disable(boolean dis) {
		if (dis) {
			this.elementWrapper.addClassName("disable");
		} else {
			this.elementWrapper.removeClassName("disable");
		}
		this.disable = dis;
		this.element.setPropertyBoolean("disabled", dis);
	}

}

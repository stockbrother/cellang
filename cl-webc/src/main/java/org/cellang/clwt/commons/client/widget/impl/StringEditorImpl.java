/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget.impl;

import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.widget.StringEditorI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.KeyDownEvent;
import org.cellang.clwt.core.client.event.KeyUpEvent;
import org.cellang.clwt.core.client.gwtbridge.GwtChangeHandler;
import org.cellang.clwt.core.client.gwtbridge.GwtKeyDownHandler;
import org.cellang.clwt.core.client.gwtbridge.GwtKeyUpHandler;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.ObjectElementHelper;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class StringEditorImpl extends EditorSupport<String> implements StringEditorI {

	private boolean trim = true;
	private boolean emptyAsNull = true;
	private boolean isTextArea;

	private int lengthLimit = -1;

	private Element stringElement;

	/** */
	public StringEditorImpl(Container c, String name, HasProperties pts) {
		super(c, name, DOM.createDiv(), pts);
		this.isTextArea = (Boolean) this.getProperty(StringEditorI.PK_TEXAREA, Boolean.FALSE);
		if (this.isTextArea) {
			stringElement = DOM.createTextArea();
		} else {
			boolean isPassword = (Boolean) this.getProperty(StringEditorI.PK_ISPASSWORD, Boolean.FALSE);
			if (isPassword) {
				stringElement = DOM.createInputPassword();
			} else {
				stringElement = DOM.createInputText();
			}
		}

		int ll = (Integer) this.getProperty(StringEditorI.PK_LENGTH_LIMIT, -1);
		// not set,see the default config in client parameters
		if (ll == -1) {
			ll = this.getClient(true).getParameterAsInt(UiCommonsConstants.CPK_TEXT_INPUT_LENGTH_LIMIT, -1);
		}

		if (ll < -1) {
			throw new UiException("" + StringEditorI.PK_LENGTH_LIMIT + ",must >= -1");
		}

		this.lengthLimit = ll;

		this.element.appendChild(this.stringElement);
		ObjectElementHelper oeh = new ObjectElementHelper(stringElement,this);//TODO
		oeh.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new GwtChangeHandler() {

			@Override
			public void handleInternal(com.google.gwt.event.dom.client.ChangeEvent event) {
				StringEditorImpl.this.onChange();
			}
		});
		oeh.addGwtHandler(com.google.gwt.event.dom.client.KeyDownEvent.getType(), new GwtKeyDownHandler() {

			@Override
			protected void handleInternal(com.google.gwt.event.dom.client.KeyDownEvent evt) {
				StringEditorImpl.this.onKeyDown(evt);
			}
		});
		oeh.addGwtHandler(com.google.gwt.event.dom.client.KeyUpEvent.getType(), new GwtKeyUpHandler() {

			@Override
			protected void handleInternal(com.google.gwt.event.dom.client.KeyUpEvent evt) {
				StringEditorImpl.this.onKeyUp(evt);
			}
		});
	}

	protected void onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent evt) {
		this.onChange();
		new KeyUpEvent(this, evt.getNativeKeyCode(), evt.isControlKeyDown()).dispatch();
	}

	/**
	 * Apr 2, 2013
	 */
	protected void onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent evt) {
		new KeyDownEvent(this, evt.getNativeKeyCode(), evt.isControlKeyDown()).dispatch();
	}

	protected void onChange() {

		String v = this.getText();

		if (this.lengthLimit != -1 && v != null && v.length() > this.lengthLimit) {
			// not show a message for this?
			v = v.substring(0, this.lengthLimit);
		}

		this.setData((v), true);//

	}

	/* */
	@Override
	public boolean setData(String dt, boolean dis) {
		boolean changed = super.setData(dt, dis);
		if (!changed) {
			return false;
		}
		String txt = dt == null ? "" : dt;
		this.setText(txt);
		return changed;

	}

	public String getText() {
		String rt = DOM.getElementProperty(this.stringElement, "value");

		if (this.trim) {
			if (rt != null) {
				rt = rt.trim();
			}
		}

		if (this.emptyAsNull && "".equals(rt)) {
			rt = null;
		}

		return rt;

	}

	protected void setText(String txt) {
		DOM.setElementProperty(this.stringElement, "value", txt);
	}

}

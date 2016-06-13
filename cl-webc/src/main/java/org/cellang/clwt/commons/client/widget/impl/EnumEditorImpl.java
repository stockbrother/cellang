/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget.impl;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.commons.client.mvc.widget.EnumEditorI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.util.ObjectUtil;

import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 *         <p>
 *         see ListBox in GWT.
 */
public class EnumEditorImpl extends EditorSupport<String> implements EnumEditorI {

	/** */
	public EnumEditorImpl(Container c, String name) {
		super(c, name, DOM.createSelect());

		this.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new ChangeHandler() {

			@Override
			public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {
				EnumEditorImpl.this.onChange();
			}
		});
	}

	// change by user.
	protected void onChange() {
		String value = this.concreteElement().getValue();
		this.setData((value), true);//

	}

	@Override
	public boolean setData(String data, boolean dis) {
		boolean rt = super.setData(data, dis);
		if (rt) {
			this.updateElement();
		}
		return rt;
	}

	protected void updateElement() {
		String value = this.getData();
		OptionElement oe = this.getOptionElementByValue(value, false);
		if (oe == null) {
			return;
		}
		oe.setSelected(true);// is need to set other unselected?
	}

	protected OptionElement getOptionElementByValue(String value, boolean force) {
		NodeList<OptionElement> oeL = this.concreteElement().getOptions();

		for (int i = 0; i < oeL.getLength(); i++) {
			OptionElement oe = oeL.getItem(i);
			if (ObjectUtil.nullSafeEquals(value, oe.getValue())) {
				return oe;
			}

		}
		if (force) {
			throw new UiException("no option found by value:" + value + ",all values:" + this.getValueList());
		}
		return null;
	}

	protected List<String> getValueList() {
		NodeList<OptionElement> oeL = this.concreteElement().getOptions();

		List<String> rt = new ArrayList<String>();

		for (int i = 0; i < oeL.getLength(); i++) {
			OptionElement oe = oeL.getItem(i);
			rt.add(oe.getValue());//

		}
		return rt;
	}

	@Override
	public void addOption(String option) {
		SelectElement se = this.concreteElement();
		OptionElement oe = DOM.createOption().cast();
		oe.setValue(option);
		oe.setText(option);// TODO i18n
		se.add(oe, null);//
		this.updateElement();//
	}

	private SelectElement concreteElement() {
		return getElement().cast();
	}

}

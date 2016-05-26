/**
 * Jun 30, 2012
 */
package org.cellang.clwt.core.client.impl;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ContainerAware;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.lang.WebElement;
import org.cellang.clwt.core.client.widget.AbstractWebWidget;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;

/**
 * @author wu Root widget
 */
public class DefaultRootWidget extends AbstractWebWidget implements WebWidget, ContainerAware {

	/** */
	public DefaultRootWidget(Container c, String name) {
		super(c, name, getRootElement());
	}

	/* */
	@Override
	public WebClient getClient(boolean force) {
		WebClient rt = (WebClient) this.container.get(WebClient.class, force);
		return rt;
	}

	@Override
	protected void processAddChildElementObject(WebElement ceo) {
		this.elementWrapper.append(ceo.getElement());//

	}

	/**
	 * Convenience method for getting the document's body element.
	 * 
	 * @return the document's body element
	 */
	public static com.google.gwt.user.client.Element getRootElement() {
		Element ele = Document.get().getElementById("_cellang_root").cast();
		if (ele == null) {
			ele = getBodyElement();
			ele.setInnerText("not found id:_cellang_root");
		} else {
			ele.setInnerText("Hello world!");
		}

		return ele;

	}

	private static native com.google.gwt.user.client.Element getBodyElement()/*-{
																				return $doc.body;
																				}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.Container)
	 */
	@Override
	public void setContainer(Container c) {
		this.container = c;

	}

	@Override
	public Container getContainer() {
		return this.container;//
	}

}

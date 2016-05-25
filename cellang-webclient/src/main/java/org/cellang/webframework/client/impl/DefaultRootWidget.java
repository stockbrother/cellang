/**
 * Jun 30, 2012
 */
package org.cellang.webframework.client.impl;

import org.cellang.webframework.client.Container;
import org.cellang.webframework.client.ContainerAware;
import org.cellang.webframework.client.RootI;
import org.cellang.webframework.client.WebClient;
import org.cellang.webframework.client.lang.WebElement;
import org.cellang.webframework.client.widget.AbstractWebWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;

/**
 * @author wu Root widget
 */
public class DefaultRootWidget extends AbstractWebWidget implements RootI, ContainerAware {

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
		}else{
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
	 * @see
	 * com.fs.uicore.api.gwt.client.ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.Container)
	 */
	@Override
	public void setContainer(Container c) {
		this.container = c;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#getContainer()
	 */
	@Override
	public Container getContainer() {
		return this.container;//
	}

}

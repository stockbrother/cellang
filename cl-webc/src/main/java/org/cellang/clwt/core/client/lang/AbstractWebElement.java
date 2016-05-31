/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.lang;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.ScrollEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.Event.Type;
import org.cellang.clwt.core.client.gwtbridge.GwtHandlerI;
import org.cellang.clwt.core.client.gwtbridge.GwtScrollHandler;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractWebElement extends AbstractWebObject implements WebElement {

	protected ObjectElementHelpers helpers;

	protected Element element;

	protected ElementWrapper elementWrapper;

	protected ElementWrapper opacities;// is the container div,which is set to
										// opancity=1.0;

	protected boolean visible = true;
	
	protected GwtScrollHandler scrollHandler;

	protected static final String HK_MASTER = "_master";// helper for the top
														// element
	
	

	public AbstractWebElement(Container c, Element element) {
		this(c, null, element);
	}

	public AbstractWebElement(Container c, String name, Element element) {
		this(c, name, element, null);
	}

	public AbstractWebElement(Container c, String name, Element element, HasProperties<Object> pts) {
		super(c, name, pts);
		this.element = element;
		if(name != null){
			this.element.addClassName(name);			
		}

		this.elementWrapper = new ElementWrapper(this.element);
		// STYLE class name:
		// wgt-JavaClassShortName
		//TODO remove this class name
		this.elementWrapper.addClassName(this.getStyleClassName(""));
		// name-uiObjectName
		this.elementWrapper.addClassName("name-" + this.getName());
		this.helpers = new ObjectElementHelpers(this);
		this.helpers.addHelper(HK_MASTER, this.element);//
	}

	protected String getChildName(String cname) {
		if (this.name != null) {
			cname = this.name + "-" + cname;
		}
		return cname;
	}

	protected String getClassNamePrefix() {
		return "elo-";
	}

	protected ObjectElementHelper getMasterHelper() {
		return this.helpers.getHelper(HK_MASTER, true);
	}

	protected String getStyleClassName(String prefix) {
		String cname = this.getClass().getName();
		int idx = cname.lastIndexOf('.');
		String sname = cname;
		if (idx >= 0) {
			sname = cname.substring(idx + 1);
		}
		return this.getClassNamePrefix() + sname;
	}

	@Override
	public ElementWrapper getElementWrapper() {
		return this.elementWrapper;
	}

	/*
	
	 */
	@Override
	@Deprecated
	public Element getElement() {

		return this.element;
	}

	@Override
	public <E extends GwtEvent<H>, H extends EventHandler> HandlerRegistration addGwtEventHandler(
			DomEvent.Type<H> type, GwtHandlerI<E, H> eh) {
		return this.helpers.getHelper(HK_MASTER, true).addGwtHandler(type, eh);

	}

	@Override
	@Deprecated
	// use another Method for error catch
	public final <H extends EventHandler> HandlerRegistration addGwtHandler(DomEvent.Type<H> type,
			final H handler) {
		return this.helpers.getHelper(HK_MASTER, true).addHandler(type, handler);
	}

	protected Element removeChild() {
		NodeList nl = this.element.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.getItem(i);
			n.removeFromParent();
		}
		return this.element;
	}

	@Override
	public void attach() {
		super.attach();

	}

	@Override
	public void doAttach() {
		super.doAttach();
		this.helpers.attach();
	}

	@Override
	public void doDetach() {
		this.helpers.detach();
		super.doDetach();
	}

	@Override
	public void addChild(WebObject c) {
		if (c == null || !(c instanceof WebElement)) {
			throw new UiException("child is null or not a widget:" + c);
		}
		super.addChild(c);
		if ((c instanceof WebElement)) {
			this.processAddChildElementObject((WebElement) c);
		}
	}

	protected void processAddChildElementObject(WebElement ce) {
		Element ele = ce.getElement();
		if(ele.hasParentElement()){
			return;
		}
		this.elementWrapper.append(ce.getElement());//

	}

	/* */
	@Override
	public void removeChild(WebObject c) {
		super.removeChild(c);
		WebWidget cw = (WebWidget) c;
		this.onRemoveChild(this.element, cw);
	}

	protected void onRemoveChild(Element ele, WebWidget cw) {
		cw.getElement().removeFromParent();// TODO
	}

	@Override
	public void _click() {
		NativeEvent evt = Document.get().createClickEvent(1, 0, 0, 0, 0, false, false, false, false);
		this.element.dispatchEvent(evt);
	}

	protected void addOpacity(ElementWrapper ew) {
		if (this.opacities == null) {
			this.opacities = new ElementWrapper(DOM.createDiv());
			this.elementWrapper.append(this.opacities);//
			this.opacities.addClassName("opacities");
		}
		this.opacities.append(ew);//
	}
	
	

	@Override
	public void setVisible(boolean v) {
		this.visible = v;
		this.elementWrapper.addAndRemoveClassName(v, "visible", "invisible");
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	// TODO move to another place.
	public static native boolean _isVisible(Element elem) /*-{
															return (elem.style.display != 'none');
															}-*/;

	// TOTO move to another place.
	public static native void _setVisible(Element elem, boolean visible) /*-{
																			elem.style.display = visible ? '' : 'none';
																			}-*/;

	/*
	 *May 14, 2013
	 */
	@Override
	public <E extends Event> void addHandler(Type<E> ec, final EventHandlerI<E> l) {
		super.addHandler(ec, l);
		//TODO toward a more generic method. sink event?
		if(this.scrollHandler == null && ScrollEvent.TYPE.equals(ec)){//
			
			this.scrollHandler = new GwtScrollHandler(){

				@Override
				protected void handleInternal(com.google.gwt.event.dom.client.ScrollEvent evt) {
					Point topLeft = Point.valueOf(0, 0);
					ScrollEvent se = new ScrollEvent(topLeft,AbstractWebElement.this);
					se.dispatch();
				}
			};
			
			this.addGwtEventHandler(com.google.gwt.event.dom.client.ScrollEvent.getType(), this.scrollHandler);
		}
	}

}

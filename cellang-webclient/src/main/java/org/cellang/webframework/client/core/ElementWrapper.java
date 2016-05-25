/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.webframework.client.core;

import java.util.ArrayList;
import java.util.List;

import org.cellang.webframework.client.WebException;
import org.cellang.webframework.client.jso.RectJSO;
import org.cellang.webframework.client.lang.Point;
import org.cellang.webframework.client.lang.Rectangle;
import org.cellang.webframework.client.lang.Size;
import org.cellang.webframework.client.lang.Callback;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ElementWrapper {

	protected Element element;

	public ElementWrapper(Element ele) {
		this(ele, true);
	}

	public ElementWrapper(Element ele, boolean addStyle) {
		this.element = ele;
		// this.addClassName("ewp-" + ObjectUtil.getClassShortName(this));
	}

	public static ElementWrapper valueOf(com.google.gwt.dom.client.Element ele) {
		Element e = ele.cast();
		return new ElementWrapper(e);

	}

	/**
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}

	public void setAttribute(String key, String value) {
		this.element.setAttribute(key, value);
	}

	public String getAttribute(String key) {
		return this.element.getAttribute(key);
	}

	public void append(Element ele) {
		DOM.appendChild(this.element, ele);
	}

	public void append(ElementWrapper ew) {
		DOM.appendChild(this.element, ew.element);//
	}

	@Deprecated
	// note use this,
	public Rectangle getAbsoluteRectangle() {
		Point tl = Point.valueOf(this.element.getAbsoluteLeft(), this.element.getAbsoluteTop());
		Point br = Point.valueOf(this.element.getAbsoluteRight(), this.element.getAbsoluteBottom());

		Rectangle rt = new Rectangle(tl, br);
		return rt;
	}

	public Rectangle getOffsetRectangle() {
		Point tl = Point.valueOf(this.element.getOffsetLeft(), this.element.getOffsetTop());
		Size size = Size.valueOf(this.element.getOffsetWidth(), this.element.getOffsetHeight());

		Rectangle rt = new Rectangle(tl, size);
		return rt;
	}

	public ElementWrapper getParent() {

		if (null == this.element.getParentElement()) {
			return null;
		}

		Element rtE = this.element.getParentElement().cast();

		return new ElementWrapper(rtE);
	}

	public void moveToCenterOf(ElementWrapper ew) {
		this.moveToCenterOf(ew.getAbsoluteRectangle());
	}

	public void moveToCenterOf(Rectangle ew) {
		Point targetCenter = ew.getCenter();
		Point currentCenter = this.getAbsoluteRectangle().getCenter();
		Point currentTopLeft = this.getAbsoluteRectangle().getTopLeft();
		Point p3 = targetCenter.minus(currentCenter.minus(currentTopLeft));
		this.moveTo(p3);//
	}

	/**
	 * Note: move to offset,not absolute.
	 * <p>
	 * Apr 21, 2013
	 */
	public void moveTo(Point p) {
		DOM.setStyleAttribute(this.element, "left", p.getX() + "px");
		DOM.setStyleAttribute(this.element, "top", p.getY() + "px");
	}

	public Style getStyle() {
		return this.element.getStyle();
	}

	public void click() {
		// this.archorClick(ae);//TODO
		// see com.google.gwt.user.client.ui.CustomButton
		NativeEvent evt = Document.get().createClickEvent(1, 0, 0, 0, 0, false, false, false, false);
		this.element.dispatchEvent(evt);
	}

	/**
	 * Nov 10, 2012
	 */
	public ElementWrapper addClassName(String cname) {
		this.element.addClassName(cname);

		return this;
	}

	public ElementWrapper removeClassName(String cname) {
		this.element.removeClassName(cname);
		return this;
	}

	public boolean addOrRemoveClassName(String cname) {
		boolean rt = this.hasClassName(cname);

		if (rt) {
			this.removeClassName(cname);
		} else {
			this.addClassName(cname);
		}

		return rt;
	}

	public boolean hasClassName(String cname) {
		String oldStyle = this.element.getClassName();
		int idx = oldStyle.indexOf(cname);

		while (idx != -1) {
			if (idx == 0 || oldStyle.charAt(idx - 1) == ' ') {
				int last = idx + cname.length();
				int lastPos = oldStyle.length();
				if ((last == lastPos) || ((last < lastPos) && (oldStyle.charAt(last) == ' '))) {
					break;
				}
			}
			idx = oldStyle.indexOf(cname, idx + 1);
		}
		return idx != -1;
	}

	public ElementWrapper addAndRemoveClassName(boolean addFirst, String c1, String c2) {
		if (addFirst) {
			this.addClassName(c1);
			this.removeClassName(c2);
		} else {
			this.removeClassName(c1);
			this.addClassName(c2);
		}
		return this;
	}

	public List<ElementWrapper> getChildListByTag(String type) {
		NodeList<Node> nl = this.element.getChildNodes();
		List<ElementWrapper> rt = new ArrayList<ElementWrapper>();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.getItem(i);
			if (!(node instanceof Element)) {
				continue;
			}
			Element ele = (Element) node;
			if (ele.getTagName().equals(type)) {
				rt.add(new ElementWrapper(ele));
			}
		}
		return rt;
	}

	public void forEachChildElement(Callback<ElementWrapper, Boolean> vis) {
		NodeList<Node> nl = this.element.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.getItem(i);
			if (!(node instanceof Element)) {
				continue;
			}
			Element ele = (Element) node;
			Boolean stop = vis.execute(new ElementWrapper(ele));
			if (Boolean.TRUE.equals(stop)) {
				break;
			}
		}
	}

	public List<ElementWrapper> getChildElementList() {
		NodeList<Node> nl = this.element.getChildNodes();
		List<ElementWrapper> rt = new ArrayList<ElementWrapper>();

		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.getItem(i);
			if (!(node instanceof Element)) {
				continue;
			}
			Element ele = (Element) node;

			rt.add(new ElementWrapper(ele));

		}
		return rt;
	}

	public void tryMoveInside(Point pt, ElementWrapper outer) {// target mouse
																// point
		Rectangle rec1 = outer.getAbsoluteRectangle();
		Size size1 = rec1.getSize();
		Size size2 = this.getAbsoluteRectangle().getSize();

		int w = Math.max(0, size1.getWidth() - size2.getWidth());
		int h = Math.max(0, size1.getHeight() - size2.getHeight());
		// rec3 is the scope of the topLeft point .
		Rectangle rec3 = new Rectangle(rec1.getTopLeft(), Size.valueOf(w, h));

		Point p2 = rec3.getShortestPointTo(pt);//
		this.moveTo(p2);

	}

	public void setVisible(boolean visible) {
		this.addAndRemoveClassName(visible, "visible", "invisible");
	}

	public void moveAndResize(Rectangle rect) {
		this.moveTo(rect.getTopLeft());
		this.resize(rect.getSize());
	}

	public void setSize(Size size) {
		this.setWidth(size.getWidth());
		this.setHeight(size.getHeight());
	}

	public void resize(Size size) {
		this.setWidth(size.getWidth());
		this.setHeight(size.getHeight());
	}

	public void resizeAndKeepCenter(Size size) {

		Rectangle rect1 = this.getOffsetRectangle();
		Size size1 = rect1.getSize();
		Size sizeOffset = size1.minus(size);
		sizeOffset = sizeOffset.divide(2.0d);//
		Point topleft1 = rect1.getTopLeft();

		Point topleft2 = topleft1.add(sizeOffset.getWidth(), sizeOffset.getHeight());
		Rectangle rect2 = new Rectangle(topleft2, size);
		this.moveAndResize(rect2);

	}

	public void setWidth(int w) {
		DOM.setStyleAttribute(this.element, "width", w + "px");
	}

	public void setHeight(int h) {
		DOM.setStyleAttribute(this.element, "height", h + "px");
	}

	public ElementWrapper parent(ElementWrapper pe) {
		pe.append(this);
		return this;
	}

	public ElementWrapper getBody() {
		return this.findParentByTag("body", true);//
	}

	public ElementWrapper findParentByTag(String tag, boolean force) {
		ElementWrapper p = this.getParent();
		while (p != null && !p.getElement().getTagName().equalsIgnoreCase(tag)) {
			p = p.getParent();
		}
		if (p == null && force) {
			throw new WebException("no parent with tag:" + tag + " for element:" + this);
		}
		return p;
	}

	public Rectangle getBoundingClientRect() {
		RectJSO rjs = getBoundingClientRect(this.element);
		Point topLeft = Point.valueOf(rjs.getLeft(), rjs.getTop());
		Size size = Size.valueOf(rjs.getWidth(), rjs.getHeight());

		Rectangle rt = new Rectangle(topLeft, size);

		return rt;
	}

	private static native RectJSO getBoundingClientRect(Element ele)
	/*-{
		return ele.getBoundingClientRect();
	}-*/;

}

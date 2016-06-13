/**
 * Jun 13, 2012
 */
package org.cellang.clwt.commons.client.widget.impl.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.dom.TDWrapper;
import org.cellang.clwt.commons.client.dom.TRWrapper;
import org.cellang.clwt.commons.client.dom.TableWrapper;
import org.cellang.clwt.commons.client.mvc.widget.LayoutSupport;
import org.cellang.clwt.commons.client.mvc.widget.ListI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.core.ElementWrapper;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.lang.WebElement;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ListImpl extends LayoutSupport implements ListI {

	protected TableWrapper table;

	protected boolean vertical;

	protected TRWrapper firstTRForHorizental;

	protected Comparator comparator;

	protected List<WebWidget> childList;
	
	public ListImpl(Container c, String name, HasProperties<Object> pts) {
		super(c, name, DOM.createDiv(), pts);
		
		this.childList = new ArrayList<WebWidget>();
		table = new TableWrapper();
		this.elementWrapper.append(table);
		this.vertical = (Boolean) this.getProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);
		this.comparator = (Comparator) this.getProperty(ListI.PK_COMPARATOR, null);

		String cname = "vlist";
		if (!this.vertical) {
			this.firstTRForHorizental = this.table.addTr();
			cname = "hlist";
		}
		this.element.addClassName(cname);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.widget.list.ListI#getSize()
	 */
	@Override
	public int getSize() {
		return this.getChildWidgetList().size();
	}

	@Override
	protected void processAddChildElementObject(WebElement ceo) {
		
		WebWidget before = null;
		int idx = this.childList.size();//last as default
		if(this.comparator != null){
			
			for( int i=0; i<this.childList.size(); i++){
				WebWidget wi = this.childList.get(0);
				
				if( this.comparator.compare( (WebWidget)ceo,wi) < 0){
					idx = i;
					before = wi;
					break;
				}
			}
		}
		this.childList.add(idx, (WebWidget)ceo);
		String cname = (String)ceo.getProperty(ListI.PK_LIST_ITEM_CLASSNAME);
		if (this.vertical) {
			
			TRWrapper beforeTr = before==null?null:(TRWrapper) before.getProperty("externalParentElement");
			
			TRWrapper tr = this.table.insertTrBefore(beforeTr);
			
			TDWrapper td = tr.addTd();
			td.append(ceo.getElement());
			td.addClassName(UiCommonsConstants.CN_VLIST_ITEM);
			
			if(cname!= null){
				td.addClassName(cname);
			}
			
			ceo.setProperty("externalParentElement", tr);
		} else {//
			TDWrapper td = this.firstTRForHorizental.addTd();
			td.append(ceo.getElement());
			if(cname!= null){
				td.addClassName(cname);
			}
			td.addClassName(UiCommonsConstants.CN_HLIST_ITEM);
			ceo.setProperty("externalParentElement", td);
		}
	}
	
	

	@Override
	protected void onRemoveChild(Element ele, WebWidget cw) {
		ElementWrapper epe = (ElementWrapper) cw.getProperty("externalParentElement");
		//TRWrapper,or TDWrapper
		epe.getElement().removeFromParent();
		this.childList.remove(cw);//NOTE,equals
		super.onRemoveChild(ele, cw);
	}

}

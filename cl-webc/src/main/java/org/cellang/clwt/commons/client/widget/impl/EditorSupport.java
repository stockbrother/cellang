/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget.impl;

import org.cellang.clwt.commons.client.mvc.widget.EditorI;
import org.cellang.clwt.commons.client.mvc.widget.LayoutSupport;
import org.cellang.clwt.commons.client.widget.ChangeEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.HasProperties;
import org.cellang.clwt.core.client.util.ObjectUtil;

import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class EditorSupport<T> extends LayoutSupport implements EditorI<T> {

	protected T data;

	public EditorSupport(Container c, String name, Element ele) {
		this(c,name,ele,null);
	}
	/** */
	public EditorSupport(Container c, String name, Element ele, HasProperties pts) {
		super(c, name, ele,pts);
		this.data = this.newData();
	}

	/* */
	@Override
	public T getData() {
		return this.data;
	}

	protected T newData() {
		return null;
	}

	/* */
	@Override
	public void input(T d) {
		this.setData(d, true);//
	}

	@Override
	public void setData(T d) {
		this.setData(d, true);//
	}
	protected boolean setData(T d, boolean dispatch) {
		
		boolean same = ObjectUtil.nullSafeEquals(d, this.data);
		
		if(same){
			return false;
		}
		
		this.data = d;
		
		if (dispatch) {// TODO
			new ChangeEvent(this).dispatch();
		}
		return true;
	}

}

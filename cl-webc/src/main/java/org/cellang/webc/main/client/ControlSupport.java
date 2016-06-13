/**
 * Jun 25, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.CreaterI;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.mvc.support.AbstractControl;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public class ControlSupport extends AbstractControl {

	public ControlSupport(Container c, String name) {
		super(c, name);
	}
//
//	public <T extends ModelI> T getOrCreateModel(ModelI parent, Class<T> cls, String name, CreaterI<T> crt) {
//		T rt = parent.getChild(cls, name, false);
//		if (rt != null) {
//			return rt;
//		}
//		rt = crt.create(this.container);
//		rt.parent(parent);
//		return rt;
//	}
//
//	public <T extends ModelI> T getOrCreateModel(ModelI parent, Class<T> cls, CreaterI<T> crt) {
//		T rt = parent.getChild(cls, false);
//		if (rt != null) {
//			return rt;
//		}
//		rt = crt.create(this.container);
//		rt.parent(parent);
//		return rt;
//	}

	public <T extends WebWidget> T getOrCreateView(WebWidget parent, Class<T> cls, CreaterI<T> crt) {
		T rt = parent.getChild(cls, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.container);
		rt.parent(parent);
		return rt;
	}
	public <T extends WebWidget> T getOrCreateViewInBody(Path path, CreaterI<T> crt) {
		return this.getOrCreateViewInBody(path, crt, false);
	}
	public <T extends WebWidget> T getOrCreateViewInBody(Path path, CreaterI<T> crt,boolean select) {
		return this.getBodyView().getOrCreateItem(path, crt,select);

	}

	public WebWidget getRootView() {
		return this.getClient(true).getRoot();
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getLogicalChannel(true).sendMessage(req);//
	}

	protected FrwkViewI getFrwkView() {
		return this.getRootView().getChild(FrwkViewI.class, true);
	}

	protected BodyViewI getBodyView() {
		return this.getFrwkView().getBodyView();
	}
}

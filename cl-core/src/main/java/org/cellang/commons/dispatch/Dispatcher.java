package org.cellang.commons.dispatch;

import org.cellang.commons.lang.Handler;
import org.cellang.commons.lang.NameSpace;

public interface Dispatcher<T> {

	public void dispatch(NameSpace path, T ctx);

	public void addHandler(NameSpace p, Handler<T> h);

	public void addHandler(NameSpace p, boolean strict, Handler<T> h);

	public void addDefaultHandler(Handler<T> h);

}

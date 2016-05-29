package org.cellang.commons.dispatch;

import org.cellang.commons.lang.Handler;
import org.cellang.commons.lang.Path;

public interface Dispatcher<T> {

	public void dispatch(Path path, T ctx);

	public void addHandler(Path p, Handler<T> h);

	public void addHandler(Path p, boolean strict, Handler<T> h);

	public void addDefaultHandler(Handler<T> h);

}

/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.support;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.Interceptor;
import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.NodeService;

/**
 * @author wu
 * 
 */
public abstract class OperationSupport<O extends NodeOperation<O, T>, T extends ExecuteResult<T, ?>>
		implements NodeOperation<O, T> {

	public static final int S_INIT = 0;

	public static final int S_EXECUTING = 1;

	public static final int S_EXECUTED = 2;

	public static final int S_CLOSED = 3;

	protected NodeService dataService;

	@Deprecated
	protected HasProperties<Object> parameters;

	protected T result;

	protected int state;

	protected List<Interceptor<O>> beforeInterceptorList = new ArrayList<Interceptor<O>>();

	public OperationSupport(T rst) {
		this.result = rst;
		this.dataService = rst.getDataService();
		this.parameters = new MapProperties<Object>();
	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public O beforeInterceptor(Interceptor<O> itr) {
		//
		this.addBeforeInterceptor(itr);
		return this.cast();
	}

	@Override
	public void addBeforeInterceptor(Interceptor<O> itr) {
		this.beforeInterceptorList.add(itr);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O parameter(String key, Object value) {
		//
		this.parameters.setProperty(key, value);
		return this.cast();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O parameters(HasProperties<Object> pts) {
		this.parameters.setProperties(pts);//
		return this.cast();
	}

	@Override
	public Object getParameter(String key) {
		return getParameter(key, false);
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public <X> X getParameter(Class<X> cls, String key, X def) {
		Object rt = this.getParameter(key);
		if (rt == null) {
			return def;
		}
		return (X) rt;
	}

	@Override
	public Object getParameter(String key, boolean force) {

		Object rt = this.parameters.getProperty(key);
		if (rt == null && force) {
			throw new RuntimeException("no value found for key:" + key);
		}
		return rt;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O execute() {
		this.state = S_EXECUTING;
		try {

			this.executeInternal(this.result);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			this.state = S_EXECUTED;
		}
		return this.cast();
	}

	protected NodeService getDataService() {
		return this.dataService;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public O prepare() {
		//
		return this.cast();
	}

	@Override
	public <T> T cast() {
		return (T) this;
	}

	@Override
	public T getResult() {
		return (T) this.result;
	}

	/**
	 * Oct 27, 2012
	 */
	public void setResult(Object value) {
		this.result.setProperty(ExecuteResult.PK_DEFAULT, value);
	}

	protected abstract void executeInternal(T rst) throws Exception;

}

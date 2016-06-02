/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.support;

import org.cellang.core.lang.ErrorInfos;
import org.cellang.core.lang.MapProperties;
import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.NodeService;

/**
 * @author wu
 * 
 */
public abstract class ResultSupport<R extends ExecuteResult<R,T>,T> extends MapProperties<Object> implements
		ExecuteResult<R,T> {

	protected NodeService dataService;

	public ResultSupport(NodeService ds) {
		this.dataService = ds;
		this.setProperty(PK_ERRORINFOS, new ErrorInfos());
	}

	@Override
	public T get(String key, boolean force) {
		//
		return (T)this.getProperty(key, force);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public T get(boolean force) {
		//
		return (T)this.getProperty(PK_DEFAULT);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public void set(T value) {
		this.setProperty(PK_DEFAULT, value);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public ErrorInfos getErrorInfo() {
		//
		return (ErrorInfos) this.getProperty(PK_ERRORINFOS);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public boolean hasError() {
		//
		return this.getErrorInfo().hasError();
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public R assertNoError() {
		//
		if (this.hasError()) {
			throw new RuntimeException(this.getErrorInfo().toString());
		}
		return (R)this;
	}

	/*
	 * Oct 29, 2012
	 */
	@Override
	public <X> X cast() {
		//
		return (X) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cellang.elasticnode.ExecuteResult#getDataService()
	 */
	@Override
	public NodeService getDataService() {
		return this.dataService;
	}

}

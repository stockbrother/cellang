/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.operations;

import java.util.Date;

import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.NodeWrapper;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
public interface NodeQueryOperationI<O extends NodeQueryOperationI<O, W, R>, W extends NodeWrapper, R extends ExecuteResult<R, ?>>
		extends NodeOperationI<O,W, R> {

	public O propertyNotEq(String key, Object value);

	public O propertyEq(String key, Object value);

	public O propertyGt(String key, Object value, boolean include);

	public O propertyMatch(String key, String pharse);

	public O propertyMatch(String key, String pharse, int slop);
	
	public O multiMatch(String[] fields, String phrase, int slop);

	public O propertyLt(String key, Object value, boolean include);

	public O propertyRange(String key, Object from, boolean includeFrom, Object to, boolean includeTo);

	public O uniqueId(String uid);

	public O id(String id);

	public O timestampRange(Date from, boolean includeFrom, Date to, boolean includeTo);

	public O explain(boolean expl);

	public String getUniqueId();


}

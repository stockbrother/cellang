/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.operations;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.result.NodeSearchResultI;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
// public interface NodeQueryOperationI<W extends RowObject> extends
// TableOperation<NodeQueryOperationI<W>, NodeQueryResultI<W>> {
public interface NodeSearchOperationI<W extends RowObject> extends
		NodeQueryOperationI<NodeSearchOperationI<W>, W, NodeSearchResultI<W>> {

	public NodeSearchOperationI<W> first(int from);

	public NodeSearchOperationI<W> maxSize(int maxs);

	public NodeSearchOperationI<W> sort(String key);

	public NodeSearchOperationI<W> sort(String key, boolean desc);

	public NodeSearchOperationI<W> sortTimestamp(boolean desc);

	public NodeSearchOperationI<W> singleNewest(boolean nf);

	public int getFrom();

	public int getMaxSize();

}

/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.operations;

import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.result.NodeSearchResultI;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
// public interface NodeQueryOperationI<W extends NodeWrapper> extends
// NodeOperation<NodeQueryOperationI<W>, NodeQueryResultI<W>> {
public interface NodeSearchOperationI<W extends NodeWrapper> extends
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

/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.elasticsearch.operations;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.lang.HasProperties;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.result.NodeSearchResultI;
import org.cellang.elastictable.support.ResultSupport;

/**
 * @author wu
 * 
 */
public class NodeSearchResult<W extends RowObject> extends
		ResultSupport<NodeSearchResultI<W>, List<W>> implements
		NodeSearchResultI<W> {

	public NodeSearchResult(TableService ds) {
		super(ds);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public List<W> list() {
		//
		return (List<W>) this.get(true);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public int size() {
		//
		return this.list().size();
	}

	@Override
	public W first(boolean force) {
		//
		if (this.size() == 0) {
			if (force) {
				throw new RuntimeException("result is empty ");
			} else {
				return null;
			}
		} else {
			return this.list().get(0);// get first,can be used for sort by
										// timestamp
										// for instance.
		}
	}

	@Override
	public W single(boolean force) {
		//
		if (this.size() == 0) {
			if (force) {
				throw new RuntimeException("result is empty ");
			} else {
				return null;
			}
		} else if (this.size() == 1) {
			return this.list().get(0);
		} else {
			throw new RuntimeException("too many:" + this.list());
		}
	}

	/*
	 * Nov 29, 2012
	 */
	@Override
	public List<HasProperties<Object>> propertiesList() {
		List<W> l = this.list();

		List<HasProperties<Object>> rt = new ArrayList<HasProperties<Object>>(
				l.size());
		for (W w : l) {
			rt.add(w.getTarget());
		}
		return rt;
	}

}

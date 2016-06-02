/**
 *  Nov 28, 2012
 */
package org.cellang.elasticnode.result;

import java.util.List;

import org.cellang.core.lang.HasProperties;
import org.cellang.elasticnode.NodeWrapper;

public interface NodeSearchResultI<W extends NodeWrapper> extends
		ListResultI<NodeSearchResultI<W>, W> {
	
	public List<HasProperties<Object>> propertiesList();
	
}
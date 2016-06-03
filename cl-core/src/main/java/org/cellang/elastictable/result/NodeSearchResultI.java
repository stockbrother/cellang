/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable.result;

import java.util.List;

import org.cellang.core.lang.HasProperties;
import org.cellang.elastictable.RowObject;

public interface NodeSearchResultI<W extends RowObject> extends
		ListResultI<NodeSearchResultI<W>, W> {
	
	public List<HasProperties<Object>> propertiesList();
	
}
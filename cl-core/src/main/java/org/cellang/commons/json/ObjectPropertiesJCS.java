/**
 * Jun 23, 2012
 */
package org.cellang.commons.json;

import org.cellang.core.lang.HasProperties;

/**
 * @author wu
 * 
 */
public class ObjectPropertiesJCS extends PropertiesJCSSupport<HasProperties> {

	/** */
	public ObjectPropertiesJCS(Codecs f) {
		super("O", HasProperties.class, f);
	}

	/* */
	@Override
	public HasProperties convert(HasProperties pts) {
		return pts;
	}

}

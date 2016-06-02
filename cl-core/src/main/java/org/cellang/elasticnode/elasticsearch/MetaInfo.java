/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package org.cellang.elasticnode.elasticsearch;

import org.cellang.core.lang.MapProperties;

/**
 * @author wu
 * 
 */
public class MetaInfo extends MapProperties<String> {

	public static final String PK_VERSION = "version";

	public static final String PK_OWNER = "owner";

	public static final String PK_PASSWORD = "password";

	public static final String TYPE = "meta-info";

	public MetaInfo() {
		
	}

	public String getOwner() {
		return this.getProperty(PK_OWNER, true);
	}

	public String getPassword() {
		return this.getProperty(PK_PASSWORD, true);
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.getProperty(PK_VERSION, true);
	}

}

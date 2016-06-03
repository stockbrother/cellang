/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package org.cellang.elastictable;

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

	public MetaInfo owner(String owner) {
		this.setProperty(PK_OWNER, owner);
		return this;
	}

	public MetaInfo version(String owner) {
		this.setProperty(PK_VERSION, owner);
		return this;
	}

	public MetaInfo password(String owner) {
		this.setProperty(PK_PASSWORD, owner);
		return this;
	}

	public static MetaInfo newInstance() {
		return new MetaInfo();
	}

}

/**
 *  
 */
package org.cellang.core.lang.util;

import org.cellang.core.commons.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ImageUrl {

	private String protocol;

	private String format;

	private String encode;

	private String data;

	public static ImageUrl NONE = new ImageUrl("none", null, null, null);

	/**
	 * @param format
	 * @param encode
	 * @param data
	 */
	public ImageUrl(String protocol, String format, String encode, String data) {
		this.format = format;
		this.protocol = protocol;
		this.encode = encode;
		this.data = data;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getFormat() {
		return format;
	}

	public String getEncode() {
		return encode;
	}

	public String getData() {
		return data;
	}

	public boolean isNone() {
		return NONE.equals(this);
	}

	@Override
	public String toString() {
		if (isNone()) {
			return this.protocol;
		}

		return this.protocol + ":" + this.format + ";" + this.encode + "," + this.data;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || (!(obj instanceof ImageUrl))) {
			return false;
		}
		ImageUrl o2 = (ImageUrl) obj;
		return ObjectUtil.isNullSafeEquals(this.format, o2.format)
				&& ObjectUtil.isNullSafeEquals(this.encode, o2.encode)
				&& ObjectUtil.isNullSafeEquals(this.data, o2.data)
				&& ObjectUtil.isNullSafeEquals(this.protocol, o2.protocol);

	}

	// data:image/x-icon;base64,
	public static ImageUrl parse(String str, boolean force) {
		if (NONE.getProtocol().equals(str)) {
			return ImageUrl.NONE;
		}
		
		int idxCom = str.indexOf(":");

		String protocol = str.substring(0, idxCom);

		int idxSemiC = str.indexOf(";");
		String format = str.substring(protocol.length() + 1, idxSemiC);
		int idxCo = str.indexOf(",");
		String encode = str.substring(protocol.length() + format.length() + 2, idxCo);

		String data = str.substring(protocol.length() + format.length() + encode.length() + 3);
		return new ImageUrl(protocol, format, encode, data);
	}
}

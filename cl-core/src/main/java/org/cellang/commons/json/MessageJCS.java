/**
 * Jul 17, 2012
 */
package org.cellang.commons.json;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;

/**
 * @author wu
 * 
 */
public class MessageJCS extends PropertiesJCSSupport<MessageI> implements Codec {
	public static final String HEADERS = "_headers";
	public static final String PAYLOADS = "_payloads";

	/** */
	public MessageJCS(Codecs f) {
		super("M", MessageI.class, f);
	}

	/* */
	@Override
	protected MessageI convert(HasProperties<Object> pts) {
		HasProperties<String> hds = (HasProperties) pts.getProperty(HEADERS);
		HasProperties<Object> payloads = (HasProperties) pts.getProperty(PAYLOADS);
		MessageI rt = MessageSupport.newMessage(hds, payloads);

		return rt;

	}

	/* */
	@Override
	protected HasProperties<Object> convert(MessageI t) {
		HasProperties<Object> rt = new MapProperties<Object>();
		rt.setProperty(HEADERS, t.getHeaders());
		rt.setProperty(PAYLOADS, t.getPayloads());

		return rt;

	}

}

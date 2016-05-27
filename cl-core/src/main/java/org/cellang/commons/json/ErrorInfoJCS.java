/**
 * Jul 17, 2012
 */
package org.cellang.commons.json;

import java.util.List;

import org.cellang.core.lang.ErrorInfo;
import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;

/**
 * @author wu
 * 
 */
public class ErrorInfoJCS extends PropertiesJCSSupport<ErrorInfo> implements Codec {
	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String DETAIL = "detail";

	/** */
	public ErrorInfoJCS(Codecs f) {
		super("E", ErrorInfo.class, f);

	}

	/* */
	@Override
	protected ErrorInfo convert(HasProperties<Object> pts) {
		String id = (String) pts.getProperty(ID);
		String source = (String) pts.getProperty(CODE);
		String msg = (String) pts.getProperty(MESSAGE);
		List<String> detail = (List<String>) pts.getProperty(DETAIL);
		ErrorInfo rt = new ErrorInfo(source, msg, null, id);
		rt.getDetail().addAll(detail);

		return rt;

	}

	/* */
	@Override
	protected HasProperties<Object> convert(ErrorInfo t) {
		HasProperties<Object> rt = new MapProperties<Object>();
		rt.setProperty(ID, t.getId());
		rt.setProperty(CODE, t.getCode());
		rt.setProperty(MESSAGE, t.getMessage());
		rt.setProperty(DETAIL, t.getDetail());
		return rt;

	}

}

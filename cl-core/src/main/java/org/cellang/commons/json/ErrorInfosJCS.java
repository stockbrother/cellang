/**
 * Jul 17, 2012
 */
package org.cellang.commons.json;

import java.util.List;

import org.cellang.core.lang.ErrorInfos;

/**
 * @author wu
 * 
 */
public class ErrorInfosJCS extends ListJCSSupport<ErrorInfos> implements
		Codec {
	/** */
	public ErrorInfosJCS(Codecs f) {
		super("ES", ErrorInfos.class, f);

	}

	/* */
	@Override
	protected ErrorInfos convert(List pts) {
		ErrorInfos rt = new ErrorInfos();
		rt.getErrorInfoList().addAll(pts);
		return rt;

	}

	/* */
	@Override
	protected List convert(ErrorInfos t) {
		List rt = t.getErrorInfoList();
		return rt;

	}

}

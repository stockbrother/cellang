/**
 * Jul 17, 2012
 */
package org.cellang.webcore.client.codec;

import java.util.ArrayList;
import java.util.List;

import org.cellang.webcore.client.data.ErrorInfoData;
import org.cellang.webcore.client.data.ErrorInfosData;

/**
 * @author wu
 * 
 */
public class ErrorInfosJCC extends ListJCCSupport<ErrorInfosData> {

	/** */
	public ErrorInfosJCC(CodecFactory f) {
		super("ES", ErrorInfosData.class, f);

	}

	/* */
	@Override
	protected ErrorInfosData convert(List l) {
		ErrorInfosData rt = new ErrorInfosData();
		for (int i = 0; i < l.size(); i++) {
			rt.add((ErrorInfoData) l.get(i));
		}
		return rt;

	}

	/* */
	@Override
	protected List convert(ErrorInfosData t) {
		List rt = new ArrayList();
		for (ErrorInfoData e : t.getErrorInfoList()) {
			rt.add(e);
		}
		return rt;

	}

}

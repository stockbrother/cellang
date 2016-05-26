/**
 * Jul 17, 2012
 */
package org.cellang.clwt.core.client.codec;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.data.ErrorInfoData;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;

/**
 * @author wu
 *         <p>
 *         <code>
 * ["_O",{"_ERROR_INFO_S":["_ES",[["_E",{"message":["_S","validate failed"],"detail":["_L",[]],"source":["_S","payloads.property['nick']!=null"]}]]]}]
 * </code>
 */
public class ErrorInfoJCC extends PropertiesJCCSupport<ErrorInfoData> {

	public static final String ID = "id";
	
	public static final String CODE = "code";

	public static final String MESSAGE = "message";

	public static final String DETAIL = "detail";

	/** */
	public ErrorInfoJCC(CodecFactory f) {
		super("E", ErrorInfoData.class, f);

	}

	/* */
	@Override
	protected ErrorInfoData convert(ObjectPropertiesData l) {
		String id = (String) l.getProperty(ID);
		String code = (String) l.getProperty(CODE);
		String message = (String) l.getProperty(MESSAGE);
		List dl = (List) l.getProperty(DETAIL);

		ErrorInfoData rt = new ErrorInfoData(code, message,id);// TODO
															// null
		for (int i = 0; i < dl.size(); i++) {
			String sd = (String) dl.get(i);
			rt.getDetail().add(sd);// TODO null?
		}

		return rt;

	}

	/* */
	@Override
	protected ObjectPropertiesData convert(ErrorInfoData t) {
		ObjectPropertiesData rt = new ObjectPropertiesData();
		rt.setProperty(ID, t.getId());
		rt.setProperty(CODE, (t.getCode()));
		rt.setProperty(MESSAGE, (t.getMessage()));
		List ld = new ArrayList();
		for (String d : t.getDetail()) {
			ld.add((d));
		}

		rt.setProperty(DETAIL, ld);

		return rt;

	}

}

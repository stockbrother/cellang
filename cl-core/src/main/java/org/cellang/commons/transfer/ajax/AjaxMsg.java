/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.lang.NameSpace;
import org.cellang.core.lang.MapProperties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author wu
 * 
 */
public class AjaxMsg extends MapProperties<String> {

	public static final NameSpace CONNECT = NameSpace.valueOf("ajax.connect");
	
	public static final NameSpace CONNECT_SUCCESS = CONNECT.getSubPath("success");

	public static final NameSpace CLOSE = NameSpace.valueOf("ajax.close");
	
	public static final NameSpace CLOSE_SUCCESS = CLOSE.getSubPath("success");

	public static final NameSpace MESSAGE = NameSpace.valueOf("ajax.message");

	public static final NameSpace ERROR = NameSpace.valueOf("ajax.error");

	public static final NameSpace HEART_BEAT = NameSpace.valueOf("ajax.heart-beat");
	
	public static final NameSpace INTERRUPT = NameSpace.valueOf("ajax.interrupt");//FOR interrupt,see ajax msg context 

	public static final String PK_PATH = "_path";

	public static final String PK_TEXTMESSAGE = "_text_message";

	public static final String PK_CONNECT_SESSION_ID = "_session_id";
	
	public static final String PK_ERROR_CODE = "_error_code";
	
	public static final String PK_ERROR_MSG = "_error_message";
	
	public static final String ERROR_CODE_SESSION_NOTFOUND = "session-not-found";

	public AjaxMsg(JSONObject jo) {
		this.setProperties(jo);
	}

	public AjaxMsg(NameSpace path) {
		this.setProperty(PK_PATH, path.toString());
	}
	
	public static AjaxMsg interruptMsg(){
		return new AjaxMsg(AjaxMsg.INTERRUPT);
	}
	
	public boolean isInterruptMsg(){
		return this.isPath(INTERRUPT);
	}

	public boolean isPath(NameSpace path) {
		return path.equals(this.getPath());
	}

	public NameSpace getPath() {
		String ps = this.getProperty(PK_PATH, true);
		return NameSpace.valueOf(ps);
	}

	/**
	 * May 8, 2013
	 */
	public static List<AjaxMsg> tryParseMsgArray(Reader reader) {
		//
		List<AjaxMsg> rt = new ArrayList<AjaxMsg>();
		JSONArray jsa = (JSONArray) JSONValue.parse(reader);
		for (int i = 0; i < jsa.size(); i++) {

			JSONObject jo = (JSONObject) jsa.get(i);

			String pathS = (String) jo.get(PK_PATH);

			NameSpace path = NameSpace.valueOf(pathS);
			AjaxMsg am = new AjaxMsg(path);
			am.setProperties(jo);
			rt.add(am);
		}
		return rt;
	}

}

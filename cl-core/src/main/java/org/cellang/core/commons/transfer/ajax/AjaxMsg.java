/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.core.commons.transfer.ajax;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.cellang.core.commons.lang.Path;
import org.cellang.core.lang.MapProperties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author wu
 * 
 */
public class AjaxMsg extends MapProperties<String> {

	public static final Path CONNECT = Path.valueOf("ajax/connect");

	public static final Path CLOSE = Path.valueOf("ajax/close");

	public static final Path MESSAGE = Path.valueOf("ajax/message");

	public static final Path ERROR = Path.valueOf("ajax/error");

	public static final Path HEART_BEAT = Path.valueOf("ajax/heart-beat");
	
	public static final Path INTERRUPT = Path.valueOf("ajax/interrupt");//FOR interrupt,see ajax msg context 

	public static final String PK_PATH = "_path";

	public static final String PK_TEXTMESSAGE = "_text_message";

	public static final String PK_CONNECT_SESSION_ID = "_session_id";
	
	public static final String PK_ERROR_CODE = "_error_code";
	
	public static final String PK_ERROR_MSG = "_error_message";
	
	public static final String ERROR_CODE_SESSION_NOTFOUND = "session-not-found";

	public AjaxMsg(JSONObject jo) {
		this.setProperties(jo);
	}

	public AjaxMsg(Path path) {
		this.setProperty(PK_PATH, path.toString());
	}
	
	public static AjaxMsg interruptMsg(){
		return new AjaxMsg(AjaxMsg.INTERRUPT);
	}
	
	public boolean isInterruptMsg(){
		return this.isPath(INTERRUPT);
	}

	public boolean isPath(Path path) {
		return path.equals(this.getPath());
	}

	public Path getPath() {
		String ps = this.getProperty(PK_PATH, true);
		return Path.valueOf(ps);
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

			Path path = Path.valueOf(pathS);
			AjaxMsg am = new AjaxMsg(path);
			am.setProperties(jo);
			rt.add(am);
		}
		return rt;
	}

}

/**
 * Jun 12, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.codec.CodecFactory;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.transfer.LogicalChannel;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface ClientObject extends WebObject {
	public static final Path RES_CLIENT_INIT_SUCCESS = Path.valueOf("client.init.success");
	public String getClientId();

	public LogicalChannel getLogicalChannel(boolean force);

	public WebWidget getRoot();

	public void setParameter(String key, String value);

	public String getParameter(String key, String def);

	public String getParameter(String key, boolean force);
	
	public int getParameterAsInt(String key, int def);
	
	public boolean getParameterAsBoolean(String key, boolean def);

	public CodecFactory getCodecFactory();

	public String getPreferedLocale();

	public String localized(String key);

	public void start();//

}

/**
 * Jun 12, 2012
 */
package org.cellang.webframework.client;

import org.cellang.webframework.client.lang.WebObject;
import org.cellang.webframework.client.transferpoint.TransferPoint;
import org.cellang.webframework.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public interface WebClient extends WebObject {

	public String getClientId();

	public TransferPoint getEndpoint(boolean force);

	public WebWidget getRoot();

	public void setParameter(String key, String value);

	public String getParameter(String key, String def);

	public String getParameter(String key, boolean force);
	
	public int getParameterAsInt(String key, int def);
	
	public boolean getParameterAsBoolean(String key, boolean def);

	public CodecI.FactoryI getCodecFactory();

	public String getPreferedLocale();

	public String localized(String key);

	public void start();//

}

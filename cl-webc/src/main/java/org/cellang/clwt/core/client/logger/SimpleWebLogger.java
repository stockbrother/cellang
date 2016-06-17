/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package org.cellang.clwt.core.client.logger;

import org.cellang.clwt.core.client.Console;
import org.cellang.clwt.core.client.data.DateData;
import org.cellang.clwt.core.client.util.DateUtil;
import org.cellang.clwt.core.client.util.ExceptionUtil;

/**
 * @author wu
 * 
 */
public class SimpleWebLogger extends AbstractWebLogger {

	public SimpleWebLogger(String name) {
		super(name);
	}

	@Override
	public void doLog(int level, Object msg, Throwable t) {
		String levelS = WebLoggerFactory.levelName.get(level);
		String log = "[" + DateUtil.format(DateData.valueOfNow()) + "] [" + levelS + "] [" + this.name
				+ "] (Unknow client source line number.) - " + msg;
		Console.getInstance().println(log);

		if (t != null) {
			String more = ExceptionUtil.getStacktraceAsString(t, "\n");
			Console.getInstance().println("throwable:" + more);//

		}

	}

}

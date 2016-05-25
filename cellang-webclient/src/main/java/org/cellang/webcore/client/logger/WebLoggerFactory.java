/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package org.cellang.webcore.client.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.webcore.client.WebException;
import org.cellang.webcore.client.gwtbridge.GwtBridge;
import org.cellang.webcore.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class WebLoggerFactory {

	public static class Configuration {

		private String loggerName;

		private int loggerLevel;

		public Configuration(String loggerName, int loggerLevel) {
			this.loggerName = loggerName;
			this.loggerLevel = loggerLevel;
		}

		public boolean isRoot() {
			return this.loggerName == null;
		}

		// is the parent of the loggerName
		public boolean isParentOf(String loggerName) {

			return this.isRoot() || (loggerName != null && loggerName.startsWith(this.loggerName));

		}

		/**
		 * @return the loggerName
		 */
		public String getLoggerName() {
			return loggerName;
		}

		/**
		 * @return the loggerLevel
		 */
		public int getLoggerLevel() {
			return loggerLevel;
		}
	}

	protected static class Configurations {

		private List<Configuration> configurationList = new ArrayList<Configuration>();

		private Map<String, Configuration> cache = new HashMap<String, Configuration>();

		public Configurations(int rootLevel) {

			this.configurationList.add(new Configuration(null, rootLevel));
		}

		public Configuration getConfiguration4Logger(String name) {

			Configuration rt = this.cache.get(name);
			if (rt != null) {
				return rt;
			}
			for (Configuration cfg : configurationList) {

				if (cfg.isParentOf(name)) {// the current cfg is the parent of
											// the logger name

					// the parent found is the parent of the current cfg,so the
					// current cfg is close to the logger name
					if (rt == null || rt.isParentOf(cfg.getLoggerName())) {
						rt = cfg;
					}
				}

			}
			if (rt == null) {
				throw new WebException("bug,at least has a root logger with null name");
			}

			this.cache.put(name, rt);
			return rt;
		}

		public Configuration getConfiguration(String name) {

			for (Configuration cfg : configurationList) {
				if (ObjectUtil.nullSafeEquals(name, cfg.getLoggerName())) {
					return cfg;
				}
			}
			return null;
		}

		public void addConfiguration(Configuration cfg) {
			Configuration old = this.getConfiguration(cfg.getLoggerName());
			if (old != null) {
				old.loggerLevel = cfg.loggerLevel;// TODO other properties.
			} else {
				this.configurationList.add(cfg);

			}
			this.cache.clear();
		}

	}

	private static Map<Integer, String> levelName = new HashMap<Integer, String>();
	static {

		levelName.put(WebLogger.LEVEL_DISABLE, "DISABLE");
		levelName.put(WebLogger.LEVEL_TRACE, "TRACE");
		levelName.put(WebLogger.LEVEL_DEBUG, "DEBUG");
		levelName.put(WebLogger.LEVEL_INFO, "INFO");
		levelName.put(WebLogger.LEVEL_WARN, "WARN");
		levelName.put(WebLogger.LEVEL_ERROR, "ERROR");

	}
	private static WebLogger NULL = new NullWebLogger("null");

	private static Configurations CONFIGURATIONS;
	static {
		// add the root logger config.
		String ll = GwtBridge.getWindowLocationParameter("fs.logLevel", "INFO");
		ll = ll.toUpperCase();
		int level = WebLoggerFactory.getLevel(ll, true);
		CONFIGURATIONS = new Configurations(level);
	}

	private static WebLoggerFactory ME = new WebLoggerFactory();

	public static void configure(String name, int level) {
		configure(new Configuration(name, level));
	}

	public static void configure(Class cls, int level) {
		configure(new Configuration(cls.getName(), level));
	}

	public static void configure(Configuration cfg) {
		CONFIGURATIONS.addConfiguration(cfg);//
	}

	public static WebLogger getNullLogger() {
		return NULL;
	}

	public static WebLogger getLogger(Class cls) {
		return getLogger(cls.getName());
	}

	public static WebLogger getLogger(String name) {

		return new SimpleWebLogger(name);// TODO level

	}

	public static int getLevel4Logger(String name) {

		Configuration cfg = getConfiguration4Logger(name);
		return cfg.loggerLevel;
	}

	public static Configuration getConfiguration4Logger(String name) {
		return CONFIGURATIONS.getConfiguration4Logger(name);
	}

	public static int getLevel(String name, int def) {
		Integer rt = getLevel(name, false);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	public static Integer getLevel(String name, boolean force) {
		for (Integer level : levelName.keySet()) {
			String value = levelName.get(level);
			if (name.equals(value)) {
				return level;
			}
		}
		if (force) {
			throw new WebException("no level for name:" + name + ",all level:" + levelName);
		}
		return null;
	}

	/**
	 * Nov 18, 2012
	 */
	public static String getLevelName(int level, boolean force) {

		String name = levelName.get(level);
		if (force && name == null) {
			throw new WebException("no name for level:" + level + ",all level:" + levelName);
		}

		return name;

	}

}

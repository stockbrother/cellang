package org.cellang.elastictable.test;

import java.util.Collection;

import org.elasticsearch.Version;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.internal.InternalSettingsPreparer;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.script.groovy.GroovyPlugin;

import com.google.common.collect.ImmutableList;

public class EmbeddedESServer {
	private static class PluginEnabledNode extends Node {
		public PluginEnabledNode(Settings settings, Collection<Class<? extends Plugin>> plugins) {
			super(InternalSettingsPreparer.prepareEnvironment(settings, null), Version.CURRENT, plugins);
		}
	}

	private final Node node;
	private final String home;

	public EmbeddedESServer(String home) {
		this.home = home;
		Settings.Builder elasticsearchSettings = Settings.settingsBuilder()//
				.put("http.enabled", "false")//
				.put("path.home", home)//
				.put("script.inline", true)//
				.put("script.indexed", true)//
				.put("node.local", true);
		;

		node = new PluginEnabledNode(elasticsearchSettings.build(),
				ImmutableList.<Class<? extends Plugin>> of(GroovyPlugin.class)).start();

	}

	public Client getClient() {
		return node.client();
	}

	public void shutdown() {
		node.close();
	}

}

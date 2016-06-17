package org.cellang.elastictable.test.other;

import java.io.File;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class IndexExistTest {

	public void test() {
		File homeDir = new File("taget" + File.separator + "indexExistTest");
		if (!homeDir.exists()) {
			homeDir.mkdir();
		}
		System.out.println(homeDir.toString());
		String home = homeDir.getAbsolutePath();
		Settings settings = null;//
		settings = Settings.settingsBuilder()//
				//.put("cluster.name", "cluster")//
				.put("path.home", home)//
				// .put("http.enabled", "false")//
				.put("script.inline", true)//
				.put("script.indexed", true)//
				.put("node.local", true)//
				.build();

		Node node = NodeBuilder.nodeBuilder().settings(settings).node();
		Client client = node.client();

		IndicesAdminClient indices = client.admin().indices();

		IndicesExistsResponse res = indices.prepareExists("testindex").execute().actionGet();

		if (res.isExists()) { // Everytime getting value as false
			System.out.println("index exists.");
		} else {
			System.out.println("Creating index");
			CreateIndexRequestBuilder createIndexRequestBuilder = indices.prepareCreate("testindex");
			createIndexRequestBuilder.execute().actionGet(); // Erring out with
																// IndexAlreadyExistsException
		}

	}

}

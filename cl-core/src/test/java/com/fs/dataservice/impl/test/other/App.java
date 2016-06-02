/**
 *  
 */
package com.fs.dataservice.impl.test.other;

import java.util.Date;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;

/**
 * @author wu
 * 
 */
public class App {

	public static void main(String[] args) throws Exception {

		// on startup
		System.out.println("Elastic Search begins to start...");
		Node node = null;//TODO NodeBuilder.nodeBuilder().node();
		Client client = node.client();

		System.out.println("Begin to build one JSON");
		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("user", "kimchy")
				.field("postDate", new Date()).field("message", "trying out Elastic Search").endObject();

		System.out.println("JSON = " + builder.string());
		// on shutdown
		System.out.println("before close");
		// node.close();
	}
}

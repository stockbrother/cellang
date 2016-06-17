/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.test.cases;

import java.io.File;

import org.cellang.core.util.FileUtil;
import org.cellang.elastictable.ElasticTableBuilder;
import org.cellang.elastictable.MetaInfo;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.elasticsearch.EmbeddedESServer;
import org.cellang.elastictable.meta.DataSchema;
import org.cellang.elastictable.test.MockObject;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public class EmbeddedServerTest extends TestCase {

	private static class ServerAndClient {

		EmbeddedESServer server;
		TableService datas;

		public ServerAndClient(EmbeddedESServer server, TableService datas) {
			this.server = server;
			this.datas = datas;
		}

		public static ServerAndClient buildServerAndClient(File home) {
			EmbeddedESServer server = new EmbeddedESServer(home.getAbsolutePath());
			
			DataSchema sa = DataSchema.newInstance();
			MockObject.config(sa);

			TableService datas = ElasticTableBuilder.newInstance()
					.metaInfo(MetaInfo.newInstance()//
							.owner("test")//
							.version("0.0.1-test")//
							.password("none"))
					.schema(sa).client(server.getClient())//
					.build();//

			server.shutdown();

			return new ServerAndClient(server, datas);

		}

		public void close() {
			this.server.shutdown();
			this.server = null;
			this.datas = null;
		}
	}

	public void test() throws Exception {
		super.setUp();
		File home = FileUtil.createTempDir("test");

		System.out.println("create temp directory for es server:" + home);
		ServerAndClient sc1 = ServerAndClient.buildServerAndClient(home);
		sc1.close();

		ServerAndClient sc2 = ServerAndClient.buildServerAndClient(home);
		sc2.close();

		FileUtil.deleteTempDir(home);
	}

}

/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.test.cases.support;

import java.io.File;

import org.cellang.core.util.FileUtil;
import org.cellang.elastictable.ElasticTableBuilder;
import org.cellang.elastictable.MetaInfo;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.meta.DataSchema;
import org.cellang.elastictable.operations.DumpOperationI;
import org.cellang.elastictable.test.EmbeddedESServer;
import org.cellang.elastictable.test.MockObject;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public abstract class TestBase extends TestCase {

	protected TableService datas;

	protected EmbeddedESServer server;

	protected File home;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		home = FileUtil.createTempDir("test");

		System.out.println("create temp directory for es server:" + this.home);

		this.server = new EmbeddedESServer(this.home.getAbsolutePath());

		DataSchema sa = DataSchema.newInstance();
		MockObject.config(sa);

		this.datas = ElasticTableBuilder.newInstance()
				.metaInfo(MetaInfo.newInstance()//
						.owner("test")//
						.version("0.0.1-test")//
						.password("none"))
				.schema(sa).client(this.server.getClient())//
				.build();//

	}

	@Override
	public void tearDown() throws Exception {
		this.server.shutdown();
		FileUtil.deleteTempDir(this.home);
	}

	protected void dump() {
		System.out.println("\n\ndump:\n");
		this.datas.prepareOperation(DumpOperationI.class).execute().getResult().assertNoError();
	}

}

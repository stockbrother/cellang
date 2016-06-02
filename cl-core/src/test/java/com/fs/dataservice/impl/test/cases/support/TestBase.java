/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.impl.test.cases.support;

import org.cellang.elasticnode.NodeServiceFactory;
import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.operations.DumpOperationI;

import junit.framework.TestCase;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected NodeService datas;

	@Override
	public void setUp() {
		
		NodeServiceFactory dsf = null;//TODO

		this.datas = dsf.getDataService();//
		
	}

	protected void dump() {
		System.out.println("\n\ndump:\n");
		this.datas.prepareOperation(DumpOperationI.class).execute().getResult().assertNoError();
	}

	public void tearDown() throws Exception {

	}

}

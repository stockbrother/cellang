/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 19, 2013
 */
package com.fs.dataservice.impl.test.cases;

import java.util.List;

import org.cellang.elasticnode.operations.NodeSearchOperationI;

import com.fs.dataservice.impl.test.MockNode;
import com.fs.dataservice.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class MatchQueryTest extends TestBase {
	/**
	 * <code>
curl -XGET 'http://localhost:9200/nodes/mockNode/_search?pretty=true' -d '{
    "query": {
        "match_all":{}
    }
}'

curl -XGET 'localhost:9200/nodes/_analyze?analyzer=text&pretty=true' -d '{value13 value132}'

curl -XGET 'localhost:9200/nodes/_analyze?analyzer=text&pretty=true' -d '{value13 and value131 value132 value133}'

curl -XGET 'http://localhost:9200/nodes/mockNode/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : {
        "term" : {
          "type_" : "mockNode"
        }
      },
      "must" : {
        "match" : {
          "field3" : {
            "query" : "value13 value132",
            "type" : "phrase",
            "slop" : 0,
            "operator" : "AND"
          }
        }
      }
    }
  },
  "explain" : false
}'
curl -XGET 'http://localhost:9200/nodes/mockNode/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : [ {
        "term" : {
          "type_" : "mockNode"
        }
      }, {
        "match" : {
          "field3" : {
            "query" : "value13 value132",
            "type" : "phrase",
            "operator" : "AND",
            "slop" : 0
          }
        }
      } ]
    }
  },
  "explain" : true
}'
curl -XGET 'http://localhost:9200/nodes/mockNode/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : [ {
        "term" : {
          "type_" : "mockNode"
        }
      }]
    }
  },
  "explain" : true
}'

curl -XGET 'http://localhost:9200/nodes/mockNode/_search?pretty=true' -d '
{
  "from" : 0,
  "size" : 100,
  "query" : {
    "bool" : {
      "must" : [ {
        "term" : {
          "type_" : "mockNode"
        }
      }, {
        "match" : {
          "field3" : {
            "query" : "value13 value132",
            "type" : "phrase",
            "operator" : "AND",
            "slop" : 3
          }
        }
      } ]
    }
  },
  "explain" : true
}'
</code> Jan 19, 2013
	 */
	public void testQueryByMatch() {
		MockNode mn1 = new MockNode().forCreate(this.datas);
		mn1.setProperty(MockNode.FIELD1, "value11");
		mn1.setProperty(MockNode.FIELD2, "value12");
		mn1.setProperty(MockNode.FIELD3, "value13 and value131 value132 value133");
		mn1.save(true);

		MockNode mn2 = new MockNode().forCreate(this.datas);
		mn2.setProperty(MockNode.FIELD1, "value21");
		mn2.setProperty(MockNode.FIELD2, "value22");
		mn2.setProperty(MockNode.FIELD3, "value23 and value231 value232 value233");
		mn2.save(true);

		NodeSearchOperationI<MockNode> qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.propertyMatch(MockNode.FIELD3, "value13 value132");
		List<MockNode> mnl = qo.execute().getResult().assertNoError().list();

		assertEquals("slop=0,should not match", 0, mnl.size());

		qo = this.datas.prepareNodeSearch(MockNode.class);
		qo.propertyMatch(MockNode.FIELD3, "value13 value132", 3);
		mnl = qo.execute().getResult().assertNoError().list();
		assertEquals("slop=3,should match one", 1, mnl.size());

		MockNode mn = mnl.get(0);
		assertEquals(mn1, mn);
	}

}

/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.elasticsearch.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.cellang.core.lang.ErrorInfo;
import org.cellang.core.lang.ErrorInfos;
import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.elasticsearch.ElasticClient;
import org.cellang.elasticnode.meta.NodeMeta;
import org.cellang.elasticnode.operations.NodeQueryOperationI;
import org.cellang.elasticnode.support.NodeOperationSupport;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.search.MatchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public abstract class NodeQueryOperationSupport<O extends NodeQueryOperationI<O, W, R>, W extends NodeWrapper, R extends ExecuteResult<R, ?>>
		extends NodeOperationSupport<O, W, R> implements NodeQueryOperationI<O, W, R> {

	private static Logger LOG = LoggerFactory.getLogger(NodeQueryOperationSupport.class);

	public static class Term {
		String field;
		Object value;
		Boolean mustOrNot;
	}

	public static class Range {
		String field;
		Object from;
		boolean includeFrom;
		Object to;
		boolean includeTo;
	}

	public static class Match {
		String field;
		String pharse;
		int slop;
	}

	public static class MultiMatch {
		List<String> fieldList;
		String pharse;
		int slop;
	}

	private static final String PK_TERMS = "terms";

	private static final String PK_RANGES = "ranges";

	private static final String PK_MATCHES = "matches";//

	private static final String PK_MULTI_MATCHS = "multi-matchs";

	private ElasticClient elastic;

	protected boolean explain;

	/**
	 * @param ds
	 */
	public NodeQueryOperationSupport(NodeService ds, R rst) {
		super(rst);
		this.elastic = (ElasticClient) ds;
		this.parameters.setPropertiesByArray(PK_TERMS, new ArrayList<Term>());
		this.parameters.setPropertiesByArray(PK_RANGES, new ArrayList<Range>());
		this.parameters.setPropertiesByArray(PK_MATCHES, new MapProperties<Match>());

	}

	@Override
	public O explain(boolean expl) {
		this.explain = expl;
		return (O) this;
	}

	/*
	 * Oct 27, 2012
	 */

	protected BoolQueryBuilder buildQuery(R rst) {

		Client client = elastic.getClient();
		BoolQueryBuilder qb = new BoolQueryBuilder();
		NodeType ntype = this.getNodeType(true);// type is must.
		String uid = this.getUniqueId();
		// types
		String typeS = ntype.toString();
		if (uid != null) {// id must under type.
			IdsQueryBuilder qb1 = new IdsQueryBuilder(typeS);//

			// ids
			qb1.addIds(uid);//
			qb.must(qb1);
		}
		// type field
		TermQueryBuilder typeQ = new TermQueryBuilder(Node.PK_TYPE, typeS);// NOTE,NodeType
																			// is
																			// not
																			// string,cannot
																			// query
																			// out.
		qb.must(typeQ);
		// fields equals
		List<Term> teL = (List<Term>) this.parameters.getProperty(PK_TERMS);

		for (Term tm : teL) {

			this.validateKeyIsConfigedInType(tm.field, rst.getErrorInfo());
			TermQueryBuilder qbi = new TermQueryBuilder(tm.field, tm.value);
			if (tm.mustOrNot == null) {
				qb.should(qbi);
			} else if (tm.mustOrNot) {
				qb.must(qbi);
			} else {
				qb.mustNot(qbi);
			}
		}
		// end of equals
		// ranges

		for (Range rg : this.rangeList()) {
			RangeQueryBuilder qbi = new RangeQueryBuilder(rg.field);
			qbi.from(rg.from);
			qbi.to(rg.to);
			qb.must(qbi);//
		}
		if (rst.hasError()) {
			return qb;
		}

		// end of ranges
		// matches
		HasProperties<Match> mts = (HasProperties<Match>) this.parameters.getProperty(PK_MATCHES);

		this.validateKeyIsConfigedInType(mts.keyList(), rst.getErrorInfo());
		if (rst.hasError()) {
			return qb;
		}
		for (String key : mts.keyList()) {
			Match rg = mts.getProperty(key);
			if (rg.pharse == null) {
				//
				continue;
			}
			MatchQueryBuilder qbi = new MatchQueryBuilder(key, rg.pharse);
			qbi.type(MatchQuery.Type.PHRASE);
			qbi.slop(rg.slop);
			qbi.operator(Operator.AND);//
			qb.must(qbi);//
		}
		// multi matches
		List<MultiMatch> mmL = (List<MultiMatch>) this.parameters.getProperty(PK_MULTI_MATCHS);

		if (mmL != null) {
			for (MultiMatch mm : mmL) {
				this.validateKeyIsConfigedInType(mm.fieldList, rst.getErrorInfo());//check fields.
				MultiMatchQueryBuilder qbi = new MultiMatchQueryBuilder(mm.pharse,
						mm.fieldList.toArray(new String[] {}));
				qbi.slop(mm.slop);
				qbi.type(MultiMatchQueryBuilder.Type.PHRASE);
				qb.must(qbi);
			}
		}

		return qb;

	}

	private void validateKeyIsConfigedInType(String field, ErrorInfos errorInfo) {

		if (null == this.nodeConfig.getField(field, false)) {
			errorInfo.add(new ErrorInfo("no field:" + field + ",in type:" + this.nodeConfig.getNodeType()));
		}
	}

	/**
	 * Nov 3, 2012
	 */

	private void validateKeyIsConfigedInType(List<String> keyList, ErrorInfos errorInfo) {

		for (String k : keyList) {

			this.validateKeyIsConfigedInType(k, errorInfo);
		}
	}

	@Override
	public O propertyNotEq(String key, Object value) {
		//
		Term tm = this.addTerm(key, value);
		tm.mustOrNot = Boolean.FALSE;
		return (O) this;
	}

	@Override
	public O propertyEq(String key, Object value) {
		//
		Term tm = this.addTerm(key, value);
		tm.mustOrNot = Boolean.TRUE;
		return (O) this;
	}

	private List<Term> termList() {
		List<Term> rt = (List<Term>) this.parameters.getProperty(PK_TERMS);
		return rt;
	}

	private Term addTerm(String key, Object value) {

		Term rt = new Term();
		rt.field = key;
		rt.value = value;
		this.termList().add(rt);

		return rt;

	}

	@Override
	public O propertyGt(String key, Object value, boolean include) {
		//
		Range rg = this.addRange(key);
		rg.from = value;
		rg.includeFrom = include;
		return (O) this;
	}

	private List<Range> rangeList() {
		List<Range> tes = (List<Range>) this.parameters.getProperty(PK_RANGES);
		return tes;
	}

	private Range addRange(String key) {

		Range rt = new Range();
		rt.field = key;
		this.rangeList().add(rt);
		return rt;

	}

	@Override
	public O propertyLt(String key, Object value, boolean include) {
		Range rg = this.addRange(key);
		rg.to = value;
		rg.includeTo = include;
		return (O) this;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public O uniqueId(String uid) {
		this.parameter(Node.PK_UNIQUE_ID, uid);
		return (O) this;
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public String getUniqueId() {
		//
		return (String) this.parameters.getProperty(Node.PK_UNIQUE_ID);
	}

	/*
	 * Oct 28, 2012
	 */
	@Override
	public O id(String id) {
		//
		return this.propertyEq(Node.PK_ID, id);
	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public O nodeType(Class<W> cls) {
		//
		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(cls, true);
		this.nodeType(nc);
		return (O) this;
	}

	/*
	 * Dec 4, 2012
	 */
	@Override
	public O timestampRange(Date from, boolean includeFrom, Date to, boolean includeTo) {
		//
		this.propertyRange(Node.PK_TIMESTAMP, from, includeFrom, to, includeTo);
		return (O) this;
	}

	/*
	 * Dec 4, 2012
	 */
	@Override
	public O propertyRange(String key, Object from, boolean includeFrom, Object to, boolean includeTo) {
		//
		this.propertyGt(key, from, includeFrom);
		this.propertyLt(key, to, includeTo);//
		return (O) this;
	}

	/*
	 * Jan 19, 2013
	 */
	@Override
	public O propertyMatch(String key, String pharse) {
		return this.propertyMatch(key, pharse, 0);

	}

	@Override
	public O propertyMatch(String key, String pharse, int slop) {
		// TODO allow multiple phrases for one filed.
		HasProperties<Match> tes = (HasProperties<Match>) this.parameters.getProperty(PK_MATCHES);
		Match m = tes.getProperty(key);
		if (m == null) {
			m = new Match();
			m.field = key;
			m.slop = slop;
			tes.setProperty(key, m);
		}
		m.pharse = pharse;
		return (O) this;
	}

	@Override
	public O multiMatch(String[] keys, String pharse, int slop) {
		List<MultiMatch> mmL = (List<MultiMatch>) this.parameters.getProperty(PK_MULTI_MATCHS);
		if (mmL == null) {
			mmL = new ArrayList<MultiMatch>();
			this.parameters.setProperty(PK_MULTI_MATCHS, mmL);
		}
		MultiMatch mm = new MultiMatch();
		mm.fieldList = new ArrayList<String>();
		mm.fieldList.addAll(Arrays.asList(keys));
		mm.pharse = pharse;
		mm.slop = slop;
		mmL.add(mm);

		return (O) this;
	}

}

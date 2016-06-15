/**
 * Jun 22, 2012
 */
package org.cellang.core.server.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;
import org.cellang.core.rowobject.CellRowObject;
import org.cellang.core.rowobject.ColumnRowObject;
import org.cellang.core.rowobject.RowRowObject;
import org.cellang.core.rowobject.SheetRowObject;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;
import org.cellang.elastictable.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class SheetGetHandler extends AbstracHandler {

	private static Logger LOG = LoggerFactory.getLogger(SheetGetHandler.class);

	// protected ConfirmCodeNotifierI confirmCodeNotifier;

	// protected boolean isNeedConfirm = false;

	public SheetGetHandler(TableService ts) {
		super(ts);
		/*
		 * ValidatorI<MessageI> vl = this.createValidator("submit");
		 * vl.addExpression(Constants.P_ERROR_SIGNUP_NICK, prefix +
		 * "['nick']!=null"); vl.addExpression(Constants.P_ERROR_SIGNUP_EMAIL,
		 * prefix + "['email']!=null");
		 * vl.addExpression(Constants.P_ERROR_SIGNUP_PASSWORD, prefix +
		 * "['password']!=null");
		 */
	}

	// create anonymous account.
	@Override
	public void handle(MessageContext hc) {
		String sheetId = (String) hc.getRequestMessage().getPayload("sheetId", true);
		int maxSize = 10000;
		SheetRowObject rtR = this.tableService.getNewestById(SheetRowObject.class, sheetId, false);
		List<HasProperties<Object>> rt = new ArrayList<HasProperties<Object>>();

		if (rtR != null) {

			HasProperties<Object> rt0 = new MapProperties<Object>();
			rt0.setProperties(rtR.getTarget());

			List<RowRowObject> rroL = this.tableService.getListNewestFirst(RowRowObject.class, RowRowObject.SHEETID,
					sheetId, 0, maxSize);

			List<ColumnRowObject> croL = this.tableService.getListNewestFirst(ColumnRowObject.class,
					ColumnRowObject.SHEETID, sheetId, 0, maxSize);

			rroL = this.sortRow(rtR.getFirstRowId(), rroL);
			croL = this.sortCol(rtR.getFirstColId(), croL);

			List<CellRowObject> cll = this.tableService.getListNewestFirst(CellRowObject.class, CellRowObject.SHEETID,
					sheetId, 0, maxSize);

			List<List<String>> cellTable = new ArrayList<List<String>>();
			for (int i = 0; i < rroL.size(); i++) {
				List<String> row = new ArrayList<String>();
				for (int j = 0; j < croL.size(); j++) {
					row.add("" + i + "" + j);
				}
				cellTable.add(row);
			}
			rt0.setProperty("cellTable", cellTable);//
			rt.add(rt0);//
		}
		hc.getResponseMessage().setPayload(rt);//

	}

	// Sort by nextRowId
	private List<RowRowObject> sortRow(String firstRowId, List<RowRowObject> rroL) {
		Map<String, RowRowObject> map = new HashMap<String, RowRowObject>();
		for (int i = 0; i < rroL.size(); i++) {
			RowRowObject rro = rroL.get(i);
			map.put(rro.getId(), rro);
		}
		List<RowRowObject> rt = new ArrayList<RowRowObject>();
		String rid = firstRowId;
		while (true) {
			RowRowObject next = map.get(rid);
			if (next == null) {
				throw new RuntimeException("not found row,rid:" + rid + ",all:" + map);
			}
			rt.add(next);
			rid = next.getNextRowId();
			if (firstRowId.equals(rid)) {//
				break;
			}
		}
		return rt;
	}

	// Sort by nextColId
	private List<ColumnRowObject> sortCol(String firstId, List<ColumnRowObject> rroL) {
		Map<String, ColumnRowObject> map = new HashMap<String, ColumnRowObject>();
		for (int i = 0; i < rroL.size(); i++) {
			ColumnRowObject rro = rroL.get(i);
			map.put(rro.getId(), rro);
		}
		List<ColumnRowObject> rt = new ArrayList<ColumnRowObject>();
		String rid = firstId;
		while (true) {// TODO deadlock
			ColumnRowObject next = map.get(rid);
			rt.add(next);
			rid = next.getNextColId();
			if (firstId.equals(rid)) {
				break;
			}
		}
		return rt;
	}
	/**
	 * <code> 
	&#64;Override
	public void handle(MessageContext sc) {
	
		super.handle(sc);
	}
	
	&#64;Handle("init")
	public void handleInit(MessageI req, ResponseI res, MessageContext hc) {
	
	}
	
	&#64;Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW mw, ResponseI res, MessageContext hc,
			ValidateResult<MessageI> vr) {
		MessageI req = mw.getMessage();
		if (res.getErrorInfos().hasError()) {
			// if has error such as validate error,then not continue.
			return;
		}
		// here the data is valid for save processing.
		String email = (String) req.getPayload("email");// this is account
		email = email.toLowerCase();//
	
		Account old = this.dataService.getNewestById(Account.class, email, false);
		if (old != null) {
			res.getErrorInfos().addError(Constants.P_ERROR_SIGNUP);
			return;
		}
		String nick = (String) req.getPayload("nick");// just for display.
		String pass = (String) req.getPayload("password");
		// TODO query by email.
		// TODO query to assert there is no pending signup or account.
	
		String passcode = nick;// TODO generate a passcode.
		//
		String confirmCode = UUID.randomUUID().toString();
	
		SignupRequest pts = new SignupRequest().forCreate(this.dataService);// NOTE
		pts.setEmail(email);//
		pts.setPassword(pass);
		pts.setNick(nick);
		pts.setConfirmCode(confirmCode);//
		pts.save(true);
	
		// this.confirmCodeNotifier.notify(mw, hc, email, confirmCode);
		SignupHandler.confirm(this.dataService, email, confirmCode, res.getErrorInfos());
	}
	
	public static void confirm(TableService ds, String email, String confirmCode, ErrorInfos eis) {
		email = email.toLowerCase();
		NodeSearchOperationI<SignupRequest> qo = ds.prepareNodeSearch(NodeTypes.SIGNUP_REQUEST);
	
		qo.propertyEq(SignupRequest.PK_CONFIRM_CODE, confirmCode);
		qo.propertyEq(SignupRequest.PK_EMAIL, email);// query by email and the
		// confirm code.
	
		NodeSearchResultI<SignupRequest> rst = qo.execute().getResult().assertNoError().cast();
		List<SignupRequest> srl = rst.list();
		if (srl.isEmpty()) {
			eis.add(new ErrorInfo(null, "confirmCode or username error,or already confirmed."));// TODO
			return;
		}
		SignupRequest sp = srl.get(0);//
	
		String password = (String) sp.getProperty("password");
	
		// do really create account.
		Account an = new Account().forCreate(ds);
		an.setId(email);// email as the id?
		an.setPassword(password);
		an.setNick(sp.getNick());
		an.setType(Account.TYPE_REGISTERED);
		an.save(true);
		//
		AccountInfo xai = new AccountInfo().forCreate(ds);
		xai.setId(email);
		xai.setEmail(email);//
		xai.setAccountId(an.getId());
		xai.setPassword(password);//
		xai.save(true);//
	}
	</code>
	 */

}
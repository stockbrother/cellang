/**
 * Jun 22, 2012
 */
package org.cellang.core.server.handler;

import java.util.UUID;

import org.cellang.core.rowobject.AccountRowObject;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;
import org.cellang.elastictable.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class SignupSubmitHandler extends AbstracHandler {

	private static Logger LOG = LoggerFactory.getLogger(SignupSubmitHandler.class);

	// protected ConfirmCodeNotifierI confirmCodeNotifier;

	// protected boolean isNeedConfirm = false;
	
	public SignupSubmitHandler(TableService ts) {
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
		String email = hc.getRequestMessage().getString("email");
		String nick = hc.getRequestMessage().getString("nick");
		String password = hc.getRequestMessage().getString("password");
		AccountRowObject an = new AccountRowObject().forCreate(this.tableService);
		an.setId(email);// email as the id?
		an.setEmail(email);//
		an.setPassword(password);
		an.setNick(nick);
		an.setType(AccountRowObject.TYPE_REGISTERED);
		an.save(true);
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

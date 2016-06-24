/**
 * Jun 22, 2012
 */
package org.cellang.core.server.handler;

import org.cellang.core.entity.AccountEntity;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class SignupSubmitHandler extends AbstracHandler {

	private static Logger LOG = LoggerFactory.getLogger(SignupSubmitHandler.class);
	
	// create anonymous account.
	@Override
	public void handle(MessageContext hc) {
		String email = hc.getRequestMessage().getString("email");
		String nick = hc.getRequestMessage().getString("nick");
		String password = hc.getRequestMessage().getString("password");
		AccountEntity an = new AccountEntity();
		an.setId(email);//
		an.setEmail(email);//
		an.setPassword(password);
		an.setNick(nick);
		an.setType("unkown");
		hc.getServerContext().getEntityService().save(an);
	}
	
}

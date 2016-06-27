package org.cellang.core.server.handler;

import org.cellang.commons.ObjectUtil;
import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.core.entity.AccountEntity;
import org.cellang.core.entity.EntityService;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.ServerContext;

public class LoginHandler extends AbstracHandler {

	@Override
	public void handle(MessageContext mc) {

		String email = mc.getRequestMessage().getString("email", true);
		String passWd = mc.getRequestMessage().getString("password", false);
		
		AccountEntity acc = mc.getServerContext().getEntityService().getSingle(AccountEntity.class, "email",email);

		if (acc == null) {
			mc.getResponseMessage().getErrorInfos().addError("user-or-password.not-found");
			return;
		}
		if (!ObjectUtil.isNullSafeEquals(acc.getPassword(), passWd)) {
			mc.getResponseMessage().getErrorInfos().addError("user-or-password.not-found");
		}
	}

}

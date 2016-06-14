package org.cellang.core.server.handler;

import org.cellang.commons.ObjectUtil;
import org.cellang.core.rowobject.AccountRowObject;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;
import org.cellang.elastictable.TableService;

public class LoginHandler extends AbstracHandler {

	public LoginHandler(TableService ts) {
		super(ts);
	}

	@Override
	public void handle(MessageContext mc) {

		String email = mc.getRequestMessage().getString("email", true);
		String passWd = mc.getRequestMessage().getString("password", true);
		AccountRowObject acc = this.tableService.getNewestById(AccountRowObject.class, email, false);

		if (acc == null) {
			mc.getResponseMessage().getErrorInfos().addError("user-or-password/not-found");
			return;
		}
		if (!ObjectUtil.isNullSafeEquals(acc.getPassword(), passWd)) {
			mc.getResponseMessage().getErrorInfos().addError("user-or-password/not-found");
		}
	}

}

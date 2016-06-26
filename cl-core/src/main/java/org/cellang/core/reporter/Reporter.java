package org.cellang.core.reporter;

import java.io.Writer;

import org.cellang.core.entity.EntityService;

public abstract class Reporter {
	protected EntityService es;

	public Reporter(EntityService es) {
		this.es = es;
	}

	public abstract void generate(Writer w);

}

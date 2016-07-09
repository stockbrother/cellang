package org.cellang.core.beanshell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import org.cellang.commons.util.StringUtil;

public class ConsoleUnescapeInputStream extends InputStream {

	protected BufferedReader reader;

	private Charset charset;

	private byte[] bbuf = new byte[] {};

	private int offset;

	private String newline = System.getProperty("line.separator");

	public ConsoleUnescapeInputStream(Reader reader) {
		this(reader, Charset.forName("utf8"));
	}

	public ConsoleUnescapeInputStream(Reader reader, Charset cs) {
		this.reader = new BufferedReader(reader);
		this.charset = cs;
	}

	@Override
	public int read() throws IOException {
		if (this.offset >= bbuf.length) {
			String line = reader.readLine();
			line = line + newline;
			String rtS = StringUtil.unescapeJavaString(line);
			this.bbuf = rtS.getBytes(charset);
			this.offset = 0;
		}

		return this.bbuf[offset++];
	}

}

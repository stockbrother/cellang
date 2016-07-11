package org.cellang.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import org.cellang.commons.util.StringUtil;

public class Utf8CodedInputStream extends InputStream {

	protected BufferedReader reader;

	private Charset charset;

	private byte[] bbuf = new byte[] {};

	private int offset;

	private String newline = System.getProperty("line.separator");

	public Utf8CodedInputStream(Reader reader) {
		this(reader, Charset.forName("utf8"));
	}

	public Utf8CodedInputStream(Reader reader, Charset cs) {
		this.reader = new BufferedReader(reader);
		this.charset = cs;
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {
		int av = this.available();
		int len2 = av == 0 ? 1 : av;
		return super.read(b, off, len2);

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

	@Override
	public int available() throws IOException {
		return this.bbuf.length - offset;
	}

}
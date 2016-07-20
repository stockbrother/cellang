package org.cellang.core;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.SortedMap;

public class Tmp {

	public static void main(String[] args) throws IOException {
		Charset cs = Charset.forName("GBK");
		print(cs);
	}

	private static void all() {
		SortedMap<String, Charset> map = Charset.availableCharsets();
		for (Charset cs : map.values()) {
			print(cs);
		}

	}

	private static void print(Charset cs) {
		System.out.println("=======================");
		System.out.println(cs.name());
		try {

			PrintWriter stdout = new PrintWriter(new OutputStreamWriter(System.out, //
					cs//
			), true);

			PrintStream ps = System.out;

			stdout.println("资产负债表");
			stdout.println("\u8D44\u4EA7\u8D1F\u503A\u8868");

		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		}
		System.out.println("=======================");
	}

	public String run() throws IOException {

		Reader r = new InputStreamReader(System.in);

		char[] cbuf = new char[1024];

		r.read(cbuf, 0, 2);
		return null;

	}
}

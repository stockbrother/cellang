package org.cellang.commons.httpclient;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientFactory {

	private static final Logger LOG = LoggerFactory.getLogger(HttpClientFactory.class);

	private String httpProxyHost = "proxy.houston.hpecorp.net";

	private int httpProxyPort = 8080;

	private long pause = 10 * 1000;

	public long getPause() {
		return pause;
	}

	public void setPause(long pause) {
		this.pause = pause;
	}

	public void get(String host, Iterator<String> uriIt, HttpResponseCallback hep) {
		try {
			this.doGet(host, uriIt, hep);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void doGet(String host, Iterator<String> uriIt, HttpResponseCallback hep) throws IOException {
		CloseableHttpClient httpclient = HttpClients.custom().build();
		try {

			HttpHost target = new HttpHost(host, 80, "http");
			HttpHost proxy = new HttpHost(httpProxyHost, httpProxyPort);
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			long lastResponseTs = 0;
			while (uriIt.hasNext()) {

				String uri = uriIt.next();

				while (true) {
					long pass = System.currentTimeMillis() - lastResponseTs;
					long remain = pause - pass;
					if (remain < 0) {
						break;
					}
					try {
						LOG.info("wait " + remain + "ms before next request");//
						Thread.sleep(remain);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}

				HttpGet httpget = new HttpGet(uri);
				httpget.setConfig(config);

				LOG.info("executing request url: " + target + httpget.getRequestLine() + " via " + proxy);

				CloseableHttpResponse response = httpclient.execute(target, httpget);
				try {
					hep.onResponse(response);//
				} finally {
					response.close();
				}
				lastResponseTs = System.currentTimeMillis();

			}
		} finally {
			httpclient.close();
		}
	}

}

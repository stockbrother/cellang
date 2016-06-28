package org.cellang.collector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.cellang.core.entity.CorpInfoEntity;

import au.com.bytecode.opencsv.CSVReader;

/**
 * <code>
		HTTP/1.1 200 OK
		Server=nginx
		Date=Tue, 28 Jun 2016 06:47:07 GMT
		Content-Type=text/csv ; charset=gbk
		Transfer-Encoding=chunked
		Content-Disposition=attachment; filename=zcfzb000001.csv
		P-Via=X0.1
		Expires=Tue, 28 Jun 2016 06:50:07 GMT
		Cache-Control=max-age=180
		P-Via=X20.204
		P-Cache=EXPIRED
		P-Via=X51.176
		via=product-midproxy13.sa.cnc.bj
		via=yz_proxy1
		Proxy-Connection=Keep-Alive
		Connection=Keep-Alive
		Age=0
</code>
 * 
 * @author wuzhen
 *
 */
public class NeteaseCollector {

	private File neteaseFolder;

	private CloseableHttpClient httpclient;

	private HttpHost target;

	private HttpHost proxy;

	private RequestConfig config;

	private List<CorpInfoEntity> orgInfoList;

	private long lastAccessTimestamp;

	public NeteaseCollector(File dataFolder) {
		this.neteaseFolder = dataFolder;
	}

	public static void main(String[] args) throws Exception {
		new NeteaseCollector(new File("src" + File.separator + "main" + File.separator + "163")).start();
	}

	public List<CorpInfoEntity> loadCorpInfoEntity() {
		File corpListFile = new File("src" + File.separator + "main" + File.separator + "doc" + File.separator + "1"
				+ File.separator + "index1.corplist.csv");

		List<CorpInfoEntity> rt = new ArrayList<CorpInfoEntity>();
		try {
			CSVReader reader = new CSVReader(new FileReader(corpListFile));

			String[] next = reader.readNext();

			// skip header
			while (true) {
				next = reader.readNext();
				if (next == null) {
					break;
				}
				String code = next[0].trim();
				String name = next[1].trim();
				CorpInfoEntity ce = new CorpInfoEntity();
				ce.setId(code);
				ce.setCode(code);
				ce.setName(name);
				rt.add(ce);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return rt;
	}

	public void start() throws Exception {
		this.orgInfoList = this.loadCorpInfoEntity();
		httpclient = HttpClients.custom().build();
		try {
			// http://quotes.money.163.com/service/zcfzb_600305.html
			target = new HttpHost("quotes.money.163.com", 80, "http");
			proxy = new HttpHost("proxy.houston.hpecorp.net", 8080);
			config = RequestConfig.custom().setProxy(proxy).build();

			for (CorpInfoEntity oi : this.orgInfoList) {
				this.collectFor(oi);
			}

		} finally {
			httpclient.close();
		}
	}

	public void collectFor(CorpInfoEntity oi) throws Exception {
		String type = "zcfzb";
		File file = new File(this.neteaseFolder, type + oi.getCode() + ".csv");
		if (file.exists()) {
			System.out.println("file exist:" + file);
		} else {
			doCollectFor(oi, type, file);
		}

	}

	public void doCollectFor(CorpInfoEntity oi, String type, File file) throws Exception {

		while (true) {
			long pass = System.currentTimeMillis() - this.lastAccessTimestamp;
			if (pass < 30 * 1000) {
				System.out.print(".");
				Thread.sleep(1000);
				continue;
			}
			System.out.println("time done.");

			break;
		}
		HttpGet httpget = new HttpGet("/service/" + type + "_" + oi.getCode() + ".html?type=year");
		httpget.setConfig(config);

		System.out.println("Executing request " + httpget.getRequestLine() + " to " + target + " via " + proxy);

		CloseableHttpResponse response = httpclient.execute(target, httpget);
		this.lastAccessTimestamp = System.currentTimeMillis();
		try {

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			for (Header header : response.getAllHeaders()) {
				System.out.println(header.getName() + "=" + header.getValue());
			}

			if (response.getStatusLine().getStatusCode() == 200) {
				FileOutputStream os = new FileOutputStream(file);
				response.getEntity().writeTo(os);
				os.close();
			}

		} finally {
			response.close();
		}
	}
}

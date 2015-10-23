package com.leonoss.wechat.apppay;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leonoss.wechat.apppay.cfg.HttpConf;
import com.leonoss.wechat.apppay.dto.UnifiedOrderResponse;
import com.leonoss.wechat.apppay.dto.WechatAppPayProtocolHandler;
import com.leonoss.wechat.apppay.exception.WechatAppPayServiceException;

public class WechatHttpCapableClient implements Closeable {
	protected PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
	protected CloseableHttpClient httpClient;
	private static Logger logger = LoggerFactory
			.getLogger(WechatHttpCapableClient.class);

	@Override
	public void close() throws IOException {
		connMgr.close();
		httpClient.close();
	}

	protected void initHttp(HttpConf httpConf) {
		connMgr.setMaxTotal(httpConf.numberOfConnections);
		if (httpConf.timeOutInSeconds > 0) {
			RequestConfig requestConfig = RequestConfig
					.custom()
					.setSocketTimeout(httpConf.timeOutInSeconds * 1000)
					.setConnectTimeout(httpConf.timeOutInSeconds * 1000)
					.setConnectionRequestTimeout(
							httpConf.timeOutInSeconds * 1000).build();
			httpClient = HttpClients.custom().setConnectionManager(connMgr)
					.setDefaultRequestConfig(requestConfig).build();
		} else {
			httpClient = HttpClients.custom().setConnectionManager(connMgr)
					.build();
		}
	}

	protected String sendHttpPostAndReturnString(String url, String body)
			throws IOException {
		logger.trace("Sending Get to URL {}", url);

		String responseText = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost postRequest = new HttpPost(url);
			postRequest.setHeader("Content-Type", "text/xml;charset=utf-8");
			StringEntity postEntity = new StringEntity(body,
					Charset.forName("UTF-8"));
			postRequest.setEntity(postEntity);
			response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new IOException("Received not 200 OK status code:"
						+ response.getStatusLine().toString());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, "UTF-8");
				logger.debug("Received Unified Order resposne from wechat: "
						+ responseText);
				return responseText;
			} else {
				return null;
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
}

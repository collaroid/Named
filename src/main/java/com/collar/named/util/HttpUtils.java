package com.collar.named.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtils {

	/**
	 * 返回http get的结果
	 *
	 * @param url
	 * @param timeout
	 * @return 如果发生异常返回空字符串
	 */
	public static String httpGet(String url, int timeout) {

		CloseableHttpClient httpClient = PoolConnectionManager.getInstance().getHttpClient();
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout((int) timeout)
                .setConnectTimeout((int) timeout)
                .setConnectionRequestTimeout((int) timeout)
                .build();
		httpGet.setConfig(requestConfig);

		CloseableHttpResponse response = null;
        try {
			response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String rspStr = EntityUtils.toString(entity, "GBK");
				return rspStr;
			}
		} catch (ClientProtocolException e) {
			httpGet.abort();
			System.out.println(e.getMessage());
		} catch (IOException e) {
		    httpGet.abort();
			System.out.println(e.getMessage());
		} finally {
		    if (response != null) {
		        try {
		            response.close();
		        } catch (IOException e) {
					System.out.println(e.getMessage());
		        }
		    }

		    httpGet.releaseConnection();
		}

		return "";
	}

}
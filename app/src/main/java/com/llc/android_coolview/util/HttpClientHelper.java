package com.llc.android_coolview.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpClientHelper {
	public final long versionNumber = 1;
	public final String versionName = "0.1";
	private String encoding = HTTP.UTF_8;
	private int connectionTimeout = 5000;
	private int soTimeout = 5000;
	private String requestMethod = "post";
	private List<NameValuePair> paramList = new ArrayList<NameValuePair>();// 请求参数列表

	public HttpClientHelper() {
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * 设置连接请求超时
	 *
	 * @param connectionTimeout
	 * @return
	 */
	public HttpClientHelper setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	/**
	 * 设置页面读取超时
	 *
	 * @param soTimeout
	 * @return
	 */
	public HttpClientHelper setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
		return this;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * 设置请求方式 get或post
	 *
	 * @param requestMethod
	 *            get|post
	 * @return
	 */
	public HttpClientHelper setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
		return this;
	}

	public HttpClientHelper addParam(String key, String value) {
		paramList.add(new BasicNameValuePair(key, value));
		return this;
	}

	public HttpClientHelper addParam(String key, int value) {
		paramList.add(new BasicNameValuePair(key, String.valueOf(value)));
		return this;
	}

	public String getEncoding() {
		return encoding;
	}

	public HttpClientHelper setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public String readHtml(String url) {
		Log.i("ReadHtml", "url:" + url);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest httpRequest;
		if (requestMethod.equals("post")) {
			httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);
				((HttpPost) httpRequest).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			httpRequest = new HttpGet(url);
		}
		httpRequest.setParams(params);
		try {
			HttpResponse response = httpClient.execute(httpRequest);
			Log.i("ReadHtml", "responseCode:"+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, getEncoding());
					Log.i("ReadHtml", "result:" + result);
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("ReadHtml", "result:null");
		return null;
	}

	public InputStream readXml(String url) {
		Log.i("ReadHtml", "url:" + url);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, soTimeout);
		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest httpRequest;
		if (requestMethod.equals("post")) {
			httpRequest = new HttpPost(url);
			httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);
				((HttpPost) httpRequest).setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			httpRequest = new HttpGet(url);
		}
		httpRequest.setParams(params);
		try {
			HttpResponse response = httpClient.execute(httpRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream result = entity.getContent();
					Log.i("ReadHtml", "result:" + result);
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("ReadHtml", "result:null");
		return null;
	}

	public Bitmap getBitmap(String url) {
		final HttpClient httpClient = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse httpResponse = httpClient.execute(getRequest);
			final int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			final HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				InputStream inputStream = null;
				try {
					inputStream = httpEntity.getContent();
					return BitmapFactory.decodeStream(inputStream);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					httpEntity.consumeContent();
				}
			}
		} catch (IOException e) {
			getRequest.abort();
		} catch (IllegalStateException e) {
			getRequest.abort();
		} catch (Exception e) {
			getRequest.abort();
		}
		return null;
	}

}

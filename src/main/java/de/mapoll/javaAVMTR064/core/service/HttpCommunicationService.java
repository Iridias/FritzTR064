/*
 * *********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2018 Iridias (https://github.com/Iridias)
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 **********************************************************************************************************************
 */
package de.mapoll.javaAVMTR064.core.service;

import de.mapoll.javaAVMTR064.core.CommunicationService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class HttpCommunicationService implements CommunicationService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public static final int DEFAULT_PORT = 49000;
	
	private HttpClientContext context = HttpClientContext.create();
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private HttpHost targetHost;
	
	public HttpCommunicationService(final String host) {
		this(host, DEFAULT_PORT);
	}
	
	public HttpCommunicationService(final String host, final int port) {
		targetHost = new HttpHost(host, port);
	}
	
	@Override
	public InputStream retrieveData(final String path) throws IOException {
		HttpGet request = new HttpGet(path);
		return executeRequest(request);
	}
	
	private InputStream executeRequest(final HttpRequest request) throws IOException {
		CloseableHttpResponse response = null;
		byte[] content;
		
		try {
			response = httpClient.execute(targetHost, request, context);
			content = fetchContent(response);
		} catch (IOException e) {
			log.error("Unable to retrieve data", e);
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}
		}
		
		return new ByteArrayInputStream(content);
	}
	
	private byte[] fetchContent(final CloseableHttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if(entity.getContentLength() == 0) {
			throw new IOException("No Content in response!");
		}
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode != HttpStatus.SC_OK) {
			throw new IOException("Server responded with unexpected status-code: " + statusCode);
		}
		
		return EntityUtils.toByteArray(response.getEntity()); // TR064 Responses are small enough to easily fit in memory!
	}
	
	@Override
	public InputStream sendData(final String path, final String action, final String messageBody) throws IOException {
		HttpPost request = new HttpPost(path);
		request.addHeader("soapaction", action);
		request.addHeader("charset", "utf-8");
		request.addHeader("content-type", "text/xml");
		request.setEntity(new StringEntity(messageBody));
		
		return executeRequest(request);
	}
	
	@Override
	public void applyCredentials(final String user, final String password) {
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(user, password));
		context.setCredentialsProvider(credentialsProvider);
		
		prepareAuthCache();
	}
	
	private void prepareAuthCache() {
		AuthCache authCache = new BasicAuthCache();
		DigestScheme digestScheme = new DigestScheme();
		digestScheme.overrideParamter("realm", "F!Box SOAP-Auth");
		digestScheme.overrideParamter("nonce", Long.toString(new Random().nextLong(), 36));
		digestScheme.overrideParamter("qop", "auth");
		digestScheme.overrideParamter("nc", "0");
		digestScheme.overrideParamter("cnonce", DigestScheme.createCnonce());
		
		authCache.put(targetHost, digestScheme);
		context.setAuthCache(authCache);
	}
	
	public void setHttpClient(final CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	HttpClientContext getContext() {
		return context;
	}
}

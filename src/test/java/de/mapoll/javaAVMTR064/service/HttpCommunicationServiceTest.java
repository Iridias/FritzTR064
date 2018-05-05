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
package de.mapoll.javaAVMTR064.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HttpCommunicationServiceTest {
	
	private HttpCommunicationService sut;
	private String dummyHost = "localhost";
	
	@Mock
	private CloseableHttpClient httpClient;
	@Mock
	private CloseableHttpResponse response;
	@Mock
	private HttpEntity httpEntity;
	@Mock
	private StatusLine statusLine;
	
	HttpHost expectedHost = new HttpHost(dummyHost, HttpCommunicationService.DEFAULT_PORT);
	
	@Before
	public void init() throws IOException {
		sut = new HttpCommunicationService(dummyHost);
		sut.setHttpClient(httpClient);
		
		given(httpClient.execute(any(HttpHost.class), any(HttpRequest.class), any(HttpContext.class))).willReturn(response);
		given(response.getEntity()).willReturn(httpEntity);
		given(response.getStatusLine()).willReturn(statusLine);
	}
	
	@Test(expected = IOException.class)
	public void testRetrieveDataForCommunicationError() throws IOException {
		doThrow(new IOException("TEST CommunicationError")).when(httpClient).execute(any(HttpHost.class), any(HttpRequest.class), any(HttpContext.class));
		sut.retrieveData("");
	}
	
	@Test(expected = IOException.class)
	public void testRetrieveDataForHttpError() throws IOException {
		sut.retrieveData("");
	}
	
	@Test(expected = IOException.class)
	public void testRetrieveDataForNoContent() throws IOException {
		given(httpEntity.getContentLength()).willReturn(0L);
		sut.retrieveData("");
	}
	
	@Test
	public void testRetrieveData() throws IOException {
		String path = "test";
		String dummyContent = "Just say NARF!";
		
		given(statusLine.getStatusCode()).willReturn(HttpStatus.SC_OK);
		given(httpEntity.getContentLength()).willReturn((long)dummyContent.length());
		given(httpEntity.getContent()).willReturn(new ByteArrayInputStream(dummyContent.getBytes(StandardCharsets.UTF_8)));
		
		InputStream result = sut.retrieveData(path);
		assertNotNull(result);
		
		String resultContent = IOUtils.toString(result, StandardCharsets.UTF_8);
		assertEquals(dummyContent, resultContent);
		
		verify(httpClient, times(1)).execute(eq(expectedHost), any(HttpGet.class), eq(sut.getContext()));
	}
	
	
	@Test
	public void testSendData() throws IOException {
		String path = "test";
		String action = "do#it";
		String messageBody = "The Pinky and the Brain";
		String dummyResponse = "ZORT";
		
		given(statusLine.getStatusCode()).willReturn(HttpStatus.SC_OK);
		given(httpEntity.getContentLength()).willReturn((long)dummyResponse.length());
		given(httpEntity.getContent()).willReturn(new ByteArrayInputStream(dummyResponse.getBytes(StandardCharsets.UTF_8)));
		
		InputStream response = sut.sendData(path, action, messageBody);
		assertNotNull(response);
		
		String resultContent = IOUtils.toString(response, StandardCharsets.UTF_8);
		assertEquals(dummyResponse, resultContent);
		
		ArgumentCaptor<HttpRequest> httpRequestAC = ArgumentCaptor.forClass(HttpRequest.class);
		verify(httpClient, times(1)).execute(eq(expectedHost), httpRequestAC.capture(), eq(sut.getContext()));
		
		verifyPostRequest(httpRequestAC.getValue(), action, messageBody);
	}
	
	private void verifyPostRequest(final HttpRequest request, final String action, final String messageBody) throws IOException {
		assertNotNull(request);
		assertTrue(request instanceof HttpPost);
		
		HttpPost postRequest = (HttpPost) request;
		assertTrue(postRequest.containsHeader("soapaction"));
		assertTrue(postRequest.containsHeader("charset"));
		assertTrue(postRequest.containsHeader("content-type"));
		assertEquals(action, postRequest.getFirstHeader("soapaction").getValue());
		assertEquals("utf-8", postRequest.getFirstHeader("charset").getValue());
		assertEquals("text/xml", postRequest.getFirstHeader("content-type").getValue());
		
		assertNotNull(postRequest.getEntity());
		String entityContent = IOUtils.toString(postRequest.getEntity().getContent(), StandardCharsets.UTF_8);
		assertEquals(messageBody, entityContent);
	}
	
	@Test
	public void testApplyCredentials() {
		String user = "Pinky";
		String password = "NARF";
		
		sut.applyCredentials(user, password);
		
		HttpClientContext context = sut.getContext();
		assertNotNull(context);
		
		verifyContextCredentials(context, user, password);
		verifyContextAuthCache(context);
	}
	
	private void verifyContextCredentials(final HttpClientContext context, final String user, final String password) {
		assertNotNull(context.getCredentialsProvider());
		Credentials credentials = context.getCredentialsProvider().getCredentials(AuthScope.ANY);
		assertNotNull(credentials);
		assertEquals(user, credentials.getUserPrincipal().getName());
		assertEquals(password, credentials.getPassword());
	}
	
	private void verifyContextAuthCache(final HttpClientContext context) {
		assertNotNull(context.getAuthCache());
		
		AuthScheme scheme = context.getAuthCache().get(expectedHost);
		assertNotNull(scheme);
		assertTrue(scheme instanceof DigestScheme);
		assertEquals("F!Box SOAP-Auth", ((DigestScheme)scheme).getParameter("realm"));
		assertEquals("auth", ((DigestScheme)scheme).getParameter("qop"));
		assertEquals("0", ((DigestScheme)scheme).getParameter("nc"));
		assertNotNull(((DigestScheme)scheme).getParameter("cnonce"));
		assertNotNull(((DigestScheme)scheme).getParameter("nonce"));
	}
	
}

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
package de.mapoll.javaAVMTR064;

import de.mapoll.javaAVMTR064.core.CommunicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FritzConnectionTest {
	
	private FritzConnection sut;
	
	private String dummyHost = "localhost";
	
	@Mock
	private CommunicationService communicationService;
	
	@Before
	public void init() {
		sut = new FritzConnection(dummyHost) {
			protected CommunicationService createCommunicationService(final String address, final int port) {
				return communicationService;
			}
		};
	}
	
	@Test
	public void testGetXMLIS() throws IOException {
		String dummyPath = "test";
		InputStream expectedResult = mock(InputStream.class);
		given(communicationService.retrieveData(dummyPath)).willReturn(expectedResult);
		InputStream result = sut.getXMLIS(dummyPath);
		
		assertNotNull(result);
		assertEquals(expectedResult, result);
		
		verify(communicationService, times(1)).retrieveData(dummyPath);
	}
	
	@Test
	public void testGetSOAPXMLIS() throws IOException {
		String dummyPath = "test";
		String dummyAction = "action";
		String dummyMessage = "NARF";
		InputStream expectedResult = mock(InputStream.class);
		
		given(communicationService.sendData(dummyPath, dummyAction, dummyMessage)).willReturn(expectedResult);
		InputStream result = sut.getSOAPXMLIS(dummyPath, dummyAction, dummyMessage);
		
		assertNotNull(result);
		assertEquals(expectedResult, result);
		
		verify(communicationService, times(1)).sendData(dummyPath, dummyAction, dummyMessage);
	}
	
}

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
package de.mapoll.javaAVMTR064.service.impl;

import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import de.mapoll.javaAVMTR064.exception.FritzServiceException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

public abstract class AbstractServiceTest {
	
	@Mock
	protected FritzConnection connection;
	@Mock
	protected Service dummyService;
	@Mock
	protected Action dummyAction;
	@Mock
	protected Response dummyResponse;
	
	protected void init(final String serviceName) {
		given(connection.getService(serviceName)).willReturn(dummyService);
		given(dummyService.getAction(anyString())).willReturn(dummyAction);
	}
	
	protected void testExecuteCallForExceptionOnAction(final Runnable runner) {
		try {
			doThrow(new IOException("TEST")).when(dummyAction).execute();
			doThrow(new IOException("TEST")).when(dummyAction).execute(anyMap());
			
			runner.run();
			fail("Expected FritzServiceException");
		} catch (IOException e) {
			fail("Unexpected behaviour");
		} catch (FritzServiceException e) {
			assertNotNull(e);
		}
	}
	
	protected void testExecuteCallForInvalidResponse(final Runnable runner) {
		try {
			given(dummyAction.execute()).willReturn(dummyResponse);
			given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
			doThrow(new NoSuchFieldException("TEST")).when(dummyResponse).getValueAsInteger(anyString());
			doThrow(new NoSuchFieldException("TEST")).when(dummyResponse).getValueAsString(anyString());
			doThrow(new NoSuchFieldException("TEST")).when(dummyResponse).getValueAsBoolean(anyString());
			
			runner.run();
			fail("Expected FritzServiceException");
		} catch (IOException|NoSuchFieldException e) {
			fail("Unexpected behaviour");
		} catch (FritzServiceException e) {
			assertNotNull(e);
		}
	}
	
	protected void verifyParameterArgument(final ArgumentCaptor<Map<String, Object>> executionParametersAC, final String key, final Object value) {
		Map<String,Object> params = executionParametersAC.getValue();
		assertNotNull(params);
		assertFalse(params.isEmpty());
		assertEquals(value, params.get(key));
	}
}

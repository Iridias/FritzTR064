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

import de.mapoll.javaAVMTR064.exception.FritzServiceException;
import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;
import de.mapoll.javaAVMTR064.model.dect.SpecificDectEntry;
import de.mapoll.javaAVMTR064.model.dect.UpdateStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDectServiceTest extends AbstractServiceTest {
	
	private static final String SERVICE_NAME = "X_AVM-DE_Dect:1";
	
	private DefaultDectService sut;
	
	@Before
	public void init() {
		super.init(SERVICE_NAME);
		
		sut = new DefaultDectService(connection);
		
		verify(connection, times(1)).getService(SERVICE_NAME);
	}
	
	@Test(expected = FritzServiceException.class)
	public void testFindNumberOfDectEntriesForExceptionOnAction() throws IOException {
		doThrow(new IOException("TEST")).when(dummyAction).execute();
		
		sut.findNumberOfDectEntries();
	}
	
	@Test(expected = FritzServiceException.class)
	public void testFindNumberOfDectEntriesForInvalidResponse() throws IOException, NoSuchFieldException {
		given(dummyAction.execute()).willReturn(dummyResponse);
		doThrow(new NoSuchFieldException("TEST")).when(dummyResponse).getValueAsInteger(anyString());
		
		sut.findNumberOfDectEntries();
	}
	
	@Test
	public void testFindNumberOfDectEntries() throws IOException, NoSuchFieldException {
		int expectedAmount = 42;
		
		given(dummyAction.execute()).willReturn(dummyResponse);
		given(dummyResponse.getValueAsInteger("NewNumberOfEntries")).willReturn(expectedAmount);
		
		int result = sut.findNumberOfDectEntries();
		assertEquals(expectedAmount, result);
		
		verify(dummyService, atLeastOnce()).getAction("GetNumberOfDectEntries");
		verify(dummyAction, times(1)).execute();
		verify(dummyResponse, times(1)).getValueAsInteger("NewNumberOfEntries");
	}
	
	@Test(expected = FritzServiceException.class)
	public void testFindGenericDectEntryForExceptionOnAction() throws IOException {
		doThrow(new IOException("TEST")).when(dummyAction).execute(anyMap());
		
		sut.findGenericDectEntry(21);
	}
	
	@Test(expected = FritzServiceException.class)
	public void testFindGenericDectEntryForInvalidResponse() throws IOException, NoSuchFieldException {
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		doThrow(new NoSuchFieldException("TEST")).when(dummyResponse).getValueAsString(anyString());
		
		sut.findGenericDectEntry(21);
	}
	
	@Test
	public void testFindGenericDectEntry() throws IOException, NoSuchFieldException {
		int index = 42;
		GenericDectEntry expected = buildExpectedGenericDectEntry(index);
		
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		givenDectResponseWillReturnExpectedData(expected);
		
		GenericDectEntry result = sut.findGenericDectEntry(index);
		assertNotNull(result);
		assertEquals(expected, result);
		
		ArgumentCaptor<Map<String,Object>> executionParametersAC = ArgumentCaptor.forClass(Map.class);
		
		verify(dummyService, atLeastOnce()).getAction("GetGenericDectEntry");
		verify(dummyAction, times(1)).execute(executionParametersAC.capture());
		verifyParameterArgument(executionParametersAC, "NewIndex", index);
	}
	
	private void givenDectResponseWillReturnExpectedData(final SpecificDectEntry expected) throws NoSuchFieldException {
		given(dummyResponse.getValueAsString("NewID")).willReturn(expected.getId());
		given(dummyResponse.getValueAsBoolean("NewActive")).willReturn(expected.isActive());
		given(dummyResponse.getValueAsString("NewName")).willReturn(expected.getName());
		given(dummyResponse.getValueAsString("NewModel")).willReturn(expected.getModel());
		given(dummyResponse.getValueAsBoolean("NewUpdateAvailable")).willReturn(expected.isUpdateAvailable());
		given(dummyResponse.getValueAsString("NewUpdateInfo")).willReturn(expected.getUpdateInfo());
		given(dummyResponse.getValueAsString("NewUpdateSuccessful")).willReturn(expected.getUpdateStatus().name());
	}
	
	private GenericDectEntry buildExpectedGenericDectEntry(final int index) {
		GenericDectEntry.Builder b = new GenericDectEntry.Builder();
		b.setIndex(index);
		enrichDectEntryBuilder(b);
		
		return b.build();
	}
	
	private SpecificDectEntry buildExpectedSpecificDectEntry() {
		SpecificDectEntry.Builder b = new SpecificDectEntry.Builder();
		enrichDectEntryBuilder(b);
		
		return b.build();
	}
	
	private void enrichDectEntryBuilder(final SpecificDectEntry.Builder b) {
		b.setActive(true);
		b.setId("42");
		b.setModel("Dummy Model");
		b.setName("Dummy DECT Entry");
		b.setUpdateAvailable(false);
		b.setUpdateInfo("No Update");
		b.setUpdateStatus(UpdateStatus.unknown);
	}
	
	@Test(expected = FritzServiceException.class)
	public void testFindSpecificDectEntryForExceptionOnAction() throws IOException {
		doThrow(new IOException("TEST")).when(dummyAction).execute(anyMap());
		
		sut.findSpecificDectEntry("42");
	}
	
	@Test(expected = FritzServiceException.class)
	public void testFindSpecificDectEntryForInvalidResponse() throws IOException, NoSuchFieldException {
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		doThrow(new NoSuchFieldException("TEST")).when(dummyResponse).getValueAsString(anyString());
		
		sut.findSpecificDectEntry("42");
	}
	
	@Test
	public void testFindSpecificDectEntry() throws IOException, NoSuchFieldException {
		String id = "42";
		SpecificDectEntry expected = buildExpectedSpecificDectEntry();
		
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		givenDectResponseWillReturnExpectedData(expected);
		
		SpecificDectEntry result = sut.findSpecificDectEntry(id);
		assertNotNull(result);
		assertEquals(expected, result);
		
		ArgumentCaptor<Map<String,Object>> executionParametersAC = ArgumentCaptor.forClass(Map.class);
		
		verify(dummyService, atLeastOnce()).getAction("GetSpecificDectEntry");
		verify(dummyAction, times(1)).execute(executionParametersAC.capture());
		verifyParameterArgument(executionParametersAC, "NewID", id);
	}
	
	@Test(expected = FritzServiceException.class)
	public void testTriggerUpdateDectDeviceForExceptionOnAction() throws IOException {
		doThrow(new IOException("TEST")).when(dummyAction).execute(anyMap());
		
		sut.triggerUpdateDectDevice("42");
	}
	
	@Test
	public void testTriggerUpdateDectDevice() throws IOException {
		String id = "42";
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		
		sut.triggerUpdateDectDevice(id);
		
		ArgumentCaptor<Map<String,Object>> executionParametersAC = ArgumentCaptor.forClass(Map.class);
		verify(dummyService, atLeastOnce()).getAction("DectDoUpdate");
		verify(dummyAction, times(1)).execute(executionParametersAC.capture());
		verifyParameterArgument(executionParametersAC, "NewID", id);
	}
	
	
	private void verifyParameterArgument(final ArgumentCaptor<Map<String,Object>> executionParametersAC, final String key, final Object value) {
		Map<String,Object> params = executionParametersAC.getValue();
		assertNotNull(params);
		assertFalse(params.isEmpty());
		assertEquals(value, params.get(key));
	}
	
}

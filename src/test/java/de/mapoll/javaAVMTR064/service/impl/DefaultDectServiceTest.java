package de.mapoll.javaAVMTR064.service.impl;

import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import de.mapoll.javaAVMTR064.exception.FritzServiceException;
import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;
import de.mapoll.javaAVMTR064.model.dect.UpdateStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDectServiceTest {
	
	private static final String SERVICE_NAME = "X_AVM-DE_Dect:1";
	
	private DefaultDectService sut;
	
	@Mock
	private FritzConnection connection;
	@Mock
	private Service dummyService;
	@Mock
	private Action dummyAction;
	@Mock
	private Response dummyResponse;
	
	@Before
	public void init() {
		given(connection.getService(SERVICE_NAME)).willReturn(dummyService);
		given(dummyService.getAction(anyString())).willReturn(dummyAction);
		
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
		
		Map<String,Object> params = executionParametersAC.getValue();
		assertNotNull(params);
		assertFalse(params.isEmpty());
		assertEquals(index, params.get("NewIndex"));
	}
	
	private void givenDectResponseWillReturnExpectedData(final GenericDectEntry expected) throws NoSuchFieldException {
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
		b.setActive(true);
		b.setId("42");
		b.setModel("Dummy Model");
		b.setName("Dummy DECT Entry");
		b.setUpdateAvailable(false);
		b.setUpdateInfo("No Update");
		b.setUpdateStatus(UpdateStatus.unknown);
		
		return b.build();
	}
}

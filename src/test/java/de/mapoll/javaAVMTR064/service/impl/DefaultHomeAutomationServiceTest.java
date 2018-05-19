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

import de.mapoll.javaAVMTR064.model.homeauto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DefaultHomeAutomationServiceTest extends AbstractServiceTest {
	
	private static final String SERVICE_NAME = "X_AVM-DE_Homeauto:1";
	
	private DefaultHomeAutomationService sut;
	
	@Captor
	private ArgumentCaptor<Map<String,Object>> executionParametersAC;
	
	@Before
	public void init() {
		super.init(SERVICE_NAME);
		sut = new DefaultHomeAutomationService(connection);
		
		verify(connection, times(1)).getService(SERVICE_NAME);
	}
	
	@Test
	public void testConfigureSocketStateForExceptionOnAction() {
		super.testExecuteCallForExceptionOnAction(() -> sut.configureSocketState("TestDevice", SwitchState.UNDEFINED));
	}
	
	@Test
	public void testConfigureSocketState() throws IOException {
		String deviceIdentifier = "TestDevice";
		SwitchState switchState = SwitchState.TOGGLE;
		
		sut.configureSocketState(deviceIdentifier, switchState);
		
		verify(dummyService, atLeastOnce()).getAction("SetSwitch");
		verify(dummyAction, times(1)).execute(executionParametersAC.capture());
		verifyParameterArgument(executionParametersAC, "NewAIN", deviceIdentifier);
		verifyParameterArgument(executionParametersAC, "NewSwitchState", switchState.name());
	}
	
	
	@Test
	public void testFindGenericDeviceInfosForExceptionOnAction() {
		super.testExecuteCallForExceptionOnAction(() -> sut.findGenericDeviceInfos(42));
	}
	
	@Test
	public void testFindGenericDeviceInfosForInvalidResponse() {
		super.testExecuteCallForInvalidResponse(() -> sut.findGenericDeviceInfos(42));
	}
	
	@Test
	public void testFindGenericDeviceInfos() throws IOException, NoSuchFieldException {
		int index = 42;
		GenericDeviceInfos expected = buildExpectedGenericDeviceInfos(index);
		
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		givenResponseWillReturnExpectedData(expected);
		
		GenericDeviceInfos result = sut.findGenericDeviceInfos(index);
		assertNotNull(result);
		assertEquals(expected, result);
		
		verify(dummyService, atLeastOnce()).getAction("GetGenericDeviceInfos");
		verify(dummyAction, times(1)).execute(executionParametersAC.capture());
		verifyParameterArgument(executionParametersAC, "NewIndex", index);
	}
	
	private GenericDeviceInfos buildExpectedGenericDeviceInfos(final int index) {
		GenericDeviceInfos.Builder b = new GenericDeviceInfos.Builder();
		b.setIndex(index);
		enrichDeviceInfosBuilder(b);
		
		return b.build();
	}
	
	@Test
	public void testFindSpecificDeviceInfosForExceptionOnAction() {
		super.testExecuteCallForExceptionOnAction(() -> sut.findSpecificDeviceInfos("TestDevice"));
	}
	
	@Test
	public void testFindSpecificDeviceInfosForInvalidResponse() {
		super.testExecuteCallForInvalidResponse(() -> sut.findSpecificDeviceInfos("TestDevice"));
	}
	
	
	@Test
	public void testFindSpecificDeviceInfos() throws IOException, NoSuchFieldException {
		String deviceIdentifier = "TestDevice";
		SpecificDeviceInfos expected = buildExpectedSpecificDeviceInfos();
		
		given(dummyAction.execute(anyMap())).willReturn(dummyResponse);
		givenResponseWillReturnExpectedData(expected);
		
		SpecificDeviceInfos result = sut.findSpecificDeviceInfos(deviceIdentifier);
		assertNotNull(result);
		assertEquals(expected, result);
		
		verify(dummyService, atLeastOnce()).getAction("GetSpecificDeviceInfos");
		verify(dummyAction, times(1)).execute(executionParametersAC.capture());
		verifyParameterArgument(executionParametersAC, "NewAIN", deviceIdentifier);
	}
	
	private SpecificDeviceInfos buildExpectedSpecificDeviceInfos() {
		SpecificDeviceInfos.Builder b = new SpecificDeviceInfos.Builder();
		enrichDeviceInfosBuilder(b);
		
		return b.build();
	}
	
	private void enrichDeviceInfosBuilder(final SpecificDeviceInfos.Builder b) {
		b.setConnectionStatus(ConnectionStatus.CONNECTED);
		b.setDeviceId(36);
		b.setDeviceIdentifier("TestDevice");
		b.setDeviceName("Test Device");
		b.setFirmwareVersion("42b");
		b.setFunctionBitMask(21);
		b.setManufacturer("Beeblebrox Inc.");
		b.setMultimeterEnergy(22);
		b.setMultimeterPower(23);
		b.setMultimeterStatus(EnabledStatus.ENABLED);
		b.setMultimeterValidity(Validity.VALID);
		b.setProductName("Improbability Device");
		b.setSwitchKeyLock(false);
		b.setSwitchState(SwitchState.TOGGLE);
		b.setSwitchTimeMode(SwitchTimeMode.AUTO);
		b.setSwitchStatus(EnabledStatus.UNDEFINED);
		b.setSwitchValidity(Validity.INVALID);
		b.setTemperatureCelcius(18);
		b.setTemperatureOffset(10);
		b.setTemperatureStatus(EnabledStatus.ENABLED);
		b.setTemperatureValidity(Validity.UNDEFINED);
		b.setThermostatComfortTemperature(24);
		b.setThermostatComfortValveStatus(ValveStatus.OPEN);
		b.setThermostatCurrentTemperature(21);
		b.setThermostatReduceTemperature(0);
		b.setThermostatReduceValveStatus(ValveStatus.CLOSED);
		b.setThermostatStatus(EnabledStatus.ENABLED);
		b.setThermostatTargetTemperature(25);
		b.setThermostatTargetValveStatus(ValveStatus.TEMP);
		b.setThermostatValidity(Validity.VALID);
	}
	
	private void givenResponseWillReturnExpectedData(final SpecificDeviceInfos expected) throws NoSuchFieldException {
		given(dummyResponse.getValueAsString("NewAIN")).willReturn(expected.getDeviceIdentifier());
		given(dummyResponse.getValueAsInteger("NewDeviceId")).willReturn(expected.getDeviceId());
		given(dummyResponse.getValueAsInteger("NewFunctionBitMask")).willReturn(expected.getFunctionBitMask());
		given(dummyResponse.getValueAsString("NewFirmwareVersion")).willReturn(expected.getFirmwareVersion());
		given(dummyResponse.getValueAsString("NewManufacturer")).willReturn(expected.getManufacturer());
		given(dummyResponse.getValueAsString("NewNewProductName")).willReturn(expected.getProductName());
		given(dummyResponse.getValueAsString("NewNewDeviceName")).willReturn(expected.getDeviceName());
		given(dummyResponse.getValueAsString("NewPresent")).willReturn(expected.getConnectionStatus().name());
		given(dummyResponse.getValueAsString("NewMultimeterIsEnabled")).willReturn(expected.getMultimeterStatus().name());
		given(dummyResponse.getValueAsString("NewMultimeterIsValid")).willReturn(expected.getMultimeterValidity().name());
		given(dummyResponse.getValueAsInteger("NewMultimeterPower")).willReturn(expected.getMultimeterPower());
		given(dummyResponse.getValueAsInteger("NewMultimeterEnergy")).willReturn(expected.getMultimeterEnergy());
		given(dummyResponse.getValueAsString("NewTemperatureIsEnabled")).willReturn(expected.getTemperatureStatus().name());
		given(dummyResponse.getValueAsString("NewTemperatureIsValid")).willReturn(expected.getTemperatureValidity().name());
		given(dummyResponse.getValueAsInteger("NewTemperatureCelsius")).willReturn(expected.getTemperatureCelcius());
		given(dummyResponse.getValueAsInteger("NewTemperatureOffset")).willReturn(expected.getTemperatureOffset());
		given(dummyResponse.getValueAsString("NewSwitchIsEnabled")).willReturn(expected.getSwitchStatus().name());
		given(dummyResponse.getValueAsString("NewSwitchIsValid")).willReturn(expected.getSwitchValidity().name());
		given(dummyResponse.getValueAsString("NewSwitchState")).willReturn(expected.getSwitchState().name());
		given(dummyResponse.getValueAsString("NewSwitchMode")).willReturn(expected.getSwitchTimeMode().name());
		given(dummyResponse.getValueAsBoolean("NewSwitchLock")).willReturn(expected.isSwitchKeyLock());
		given(dummyResponse.getValueAsString("NewHkrIsEnabled")).willReturn(expected.getThermostatStatus().name());
		given(dummyResponse.getValueAsString("NewHkrIsValid")).willReturn(expected.getThermostatValidity().name());
		given(dummyResponse.getValueAsInteger("NewHkrIsTemperature")).willReturn(expected.getThermostatCurrentTemperature());
		given(dummyResponse.getValueAsString("NewHkrSetVentilStatus")).willReturn(expected.getThermostatTargetValveStatus().name());
		given(dummyResponse.getValueAsInteger("NewHkrSetTemperature")).willReturn(expected.getThermostatTargetTemperature());
		given(dummyResponse.getValueAsString("NewHkrReduceVentilStatus")).willReturn(expected.getThermostatReduceValveStatus().name());
		given(dummyResponse.getValueAsInteger("NewHkrReduceTemperature")).willReturn(expected.getThermostatReduceTemperature());
		given(dummyResponse.getValueAsString("NewHkrComfortVentilStatus")).willReturn(expected.getThermostatComfortValveStatus().name());
		given(dummyResponse.getValueAsInteger("NewHkrComfortTemperature")).willReturn(expected.getThermostatComfortTemperature());
	}
	
}

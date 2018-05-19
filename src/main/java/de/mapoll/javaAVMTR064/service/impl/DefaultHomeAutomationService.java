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

import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.model.homeauto.*;
import de.mapoll.javaAVMTR064.service.HomeAutomationService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultHomeAutomationService extends AbstractDefaultService implements HomeAutomationService {
	
	public DefaultHomeAutomationService(final FritzConnection connection) {
		service = connection.getService("X_AVM-DE_Homeauto:1");
	}
	
	@Override
	public void configureSocketState(final String deviceIdentifier, final SwitchState switchState) {
		Map<String,Object> params = new HashMap<>();
		params.put("NewAIN", deviceIdentifier);
		params.put("NewSwitchState", switchState.name());
		
		executeAction("SetSwitch", params);
	}
	
	@Override
	public GenericDeviceInfos findGenericDeviceInfos(final int index) {
		return executeAction("GetGenericDeviceInfos", Collections.singletonMap("NewIndex", index), this::buildGenericDeviceInfosFromResponse);
	}
	
	private GenericDeviceInfos buildGenericDeviceInfosFromResponse(final Response response, final Map<String,Object> params) throws NoSuchFieldException {
		GenericDeviceInfos.Builder b = new GenericDeviceInfos.Builder();
		b.setIndex(firstValueFromMap(params, Integer.class));
		b.setDeviceIdentifier(response.getValueAsString("NewAIN"));
		enrichDeviceInfosBuilderFromResponse(b, response);
		
		return b.build();
	}
	
	@Override
	public SpecificDeviceInfos findSpecificDeviceInfos(final String deviceIdentifier) {
		return executeAction("GetSpecificDeviceInfos", Collections.singletonMap("NewAIN", deviceIdentifier), this::buildSpecificDeviceInfosFromResponse);
	}
	
	private SpecificDeviceInfos buildSpecificDeviceInfosFromResponse(final Response response, final Map<String,Object> params) throws NoSuchFieldException {
		SpecificDeviceInfos.Builder b = new SpecificDeviceInfos.Builder();
		b.setDeviceIdentifier(firstValueFromMap(params, String.class));
		enrichDeviceInfosBuilderFromResponse(b, response);
		
		return b.build();
	}
	
	private void enrichDeviceInfosBuilderFromResponse(final SpecificDeviceInfos.Builder b, final Response response) throws NoSuchFieldException {
		b.setConnectionStatus(ConnectionStatus.fromString(response.getValueAsString("NewPresent")));
		b.setDeviceId(response.getValueAsInteger("NewDeviceId"));
		b.setDeviceName(response.getValueAsString("NewNewDeviceName"));
		b.setFirmwareVersion(response.getValueAsString("NewFirmwareVersion"));
		b.setFunctionBitMask(response.getValueAsInteger("NewFunctionBitMask"));
		b.setManufacturer(response.getValueAsString("NewManufacturer"));
		b.setMultimeterEnergy(response.getValueAsInteger("NewMultimeterEnergy"));
		b.setMultimeterPower(response.getValueAsInteger("NewMultimeterPower"));
		b.setMultimeterStatus(EnabledStatus.fromString(response.getValueAsString("NewMultimeterIsEnabled")));
		b.setMultimeterValidity(Validity.fromString(response.getValueAsString("NewMultimeterIsValid")));
		b.setProductName(response.getValueAsString("NewNewProductName"));
		b.setSwitchKeyLock(response.getValueAsBoolean("NewSwitchLock"));
		b.setSwitchState(SwitchState.fromString(response.getValueAsString("NewSwitchState")));
		b.setSwitchTimeMode(SwitchTimeMode.fromString(response.getValueAsString("NewSwitchMode")));
		b.setSwitchStatus(EnabledStatus.fromString(response.getValueAsString("NewSwitchIsEnabled")));
		b.setSwitchValidity(Validity.fromString(response.getValueAsString("NewSwitchIsValid")));
		b.setTemperatureCelcius(response.getValueAsInteger("NewTemperatureCelsius"));
		b.setTemperatureOffset(response.getValueAsInteger("NewTemperatureOffset"));
		b.setTemperatureStatus(EnabledStatus.fromString(response.getValueAsString("NewTemperatureIsEnabled")));
		b.setTemperatureValidity(Validity.fromString(response.getValueAsString("NewTemperatureIsValid")));
		b.setThermostatComfortTemperature(response.getValueAsInteger("NewHkrComfortTemperature"));
		b.setThermostatComfortValveStatus(ValveStatus.fromString(response.getValueAsString("NewHkrComfortVentilStatus")));
		b.setThermostatCurrentTemperature(response.getValueAsInteger("NewHkrIsTemperature"));
		b.setThermostatReduceTemperature(response.getValueAsInteger("NewHkrReduceTemperature"));
		b.setThermostatReduceValveStatus(ValveStatus.fromString(response.getValueAsString("NewHkrReduceVentilStatus")));
		b.setThermostatStatus(EnabledStatus.fromString(response.getValueAsString("NewHkrIsEnabled")));
		b.setThermostatTargetTemperature(response.getValueAsInteger("NewHkrSetTemperature"));
		b.setThermostatTargetValveStatus(ValveStatus.fromString(response.getValueAsString("NewHkrSetVentilStatus")));
		b.setThermostatValidity(Validity.fromString(response.getValueAsString("NewHkrIsValid")));
	}
}

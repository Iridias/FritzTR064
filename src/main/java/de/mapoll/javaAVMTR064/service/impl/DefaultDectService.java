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
import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;
import de.mapoll.javaAVMTR064.model.dect.SpecificDectEntry;
import de.mapoll.javaAVMTR064.model.dect.UpdateStatus;
import de.mapoll.javaAVMTR064.service.DectService;

import java.util.Collections;
import java.util.Map;

public class DefaultDectService extends AbstractDefaultService implements DectService {
	
	public DefaultDectService(final FritzConnection connection) {
		this.service = connection.getService("X_AVM-DE_Dect:1");
	}
	
	@Override
	public int findNumberOfDectEntries() {
		return executeAction("GetNumberOfDectEntries", null, this::determineEntryAmount);
	}
	
	private int determineEntryAmount(final Response response, final Map<String,Object> params) throws NoSuchFieldException {
		return response.getValueAsInteger("NewNumberOfEntries");
	}

	@Override
	public SpecificDectEntry findSpecificDectEntry(final String id) {
		return executeAction("GetSpecificDectEntry", Collections.singletonMap("NewID", id), this::buildSpecificDectEntryFromResponse);
	}
	
	private SpecificDectEntry buildSpecificDectEntryFromResponse(final Response response, final Map<String,Object> params) throws NoSuchFieldException {
		SpecificDectEntry.Builder b = new SpecificDectEntry.Builder();
		b.setId(firstValueFromMap(params, String.class));
		
		enrichDectEntryBuilderFromResponse(b, response);
		
		return b.build();
	}
	
	@Override
	public GenericDectEntry findGenericDectEntry(final int index) {
		return executeAction("GetGenericDectEntry", Collections.singletonMap("NewIndex", index), this::buildGenericDectEntryFromResponse);
	}
	
	private GenericDectEntry buildGenericDectEntryFromResponse(final Response response, final Map<String,Object> params) throws NoSuchFieldException {
		GenericDectEntry.Builder b = new GenericDectEntry.Builder();
		b.setIndex(firstValueFromMap(params, Integer.class));
		b.setId(response.getValueAsString("NewID"));
		
		enrichDectEntryBuilderFromResponse(b, response);
		
		return b.build();
	}
	
	private void enrichDectEntryBuilderFromResponse(final SpecificDectEntry.Builder b, final Response response) throws NoSuchFieldException {
		b.setActive(response.getValueAsBoolean("NewActive"));
		b.setName(response.getValueAsString("NewName"));
		b.setModel(response.getValueAsString("NewModel"));
		b.setUpdateAvailable(response.getValueAsBoolean("NewUpdateAvailable"));
		b.setUpdateInfo(response.getValueAsString("NewUpdateInfo"));
		b.setUpdateStatus(UpdateStatus.fromString(response.getValueAsString("NewUpdateSuccessful")));
	}
	
	@Override
	public void triggerUpdateDectDevice(final String id) {
		executeAction("DectDoUpdate", Collections.singletonMap("NewID", id));
	}
}

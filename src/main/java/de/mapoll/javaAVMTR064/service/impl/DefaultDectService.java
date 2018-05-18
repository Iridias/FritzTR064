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
import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;
import de.mapoll.javaAVMTR064.model.dect.SpecificDectEntry;
import de.mapoll.javaAVMTR064.model.dect.UpdateStatus;
import de.mapoll.javaAVMTR064.service.DectService;

import java.io.IOException;
import java.util.Collections;

public class DefaultDectService implements DectService {
	
	private FritzConnection connection;
	private Service service;
	
	public DefaultDectService(final FritzConnection connection) {
		this.connection = connection;
		this.service = connection.getService("X_AVM-DE_Dect:1");
	}
	
	@Override
	public int findNumberOfDectEntries() {
		Action action = service.getAction("GetNumberOfDectEntries");
		int amount;
		try {
			Response response = action.execute();
			amount = response.getValueAsInteger("NewNumberOfEntries");
		} catch (IOException|NoSuchFieldException e) {
			throw new FritzServiceException("Unable to retrieve number of DECT-Entries", e);
		}
		
		return amount;
	}

	@Override
	public SpecificDectEntry findSpecificDectEntry(final String id) {
		Action action = service.getAction("GetSpecificDectEntry");
		try {
			Response response = action.execute(Collections.singletonMap("NewID", id));
			return buildSpecificDectEntryFromResponse(response, id);
		} catch (IOException|NoSuchFieldException e) {
			throw new FritzServiceException("Unable to retrieve Specific DECT-Entry for ID " + id, e);
		}
	}
	
	private SpecificDectEntry buildSpecificDectEntryFromResponse(final Response response, final String id) throws NoSuchFieldException {
		SpecificDectEntry.Builder b = new SpecificDectEntry.Builder();
		b.setId(id);
		
		enrichDectEntryBuilderFromResponse(b, response);
		
		return b.build();
	}
	
	@Override
	public GenericDectEntry findGenericDectEntry(final int index) {
		Action action = service.getAction("GetGenericDectEntry");
		try {
			Response response = action.execute(Collections.singletonMap("NewIndex", index));
			return buildGenericDectEntryFromResponse(response, index);
		} catch (IOException|NoSuchFieldException e) {
			throw new FritzServiceException("Unable to retrieve Generic DECT-Entry for index " + index, e);
		}
	}
	
	private GenericDectEntry buildGenericDectEntryFromResponse(final Response response, final int index) throws NoSuchFieldException {
		GenericDectEntry.Builder b = new GenericDectEntry.Builder();
		b.setIndex(index);
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
		b.setUpdateStatus(determineUpdateStatus(response.getValueAsString("NewUpdateSuccessful")));
	}
	
	private UpdateStatus determineUpdateStatus(final String status) {
		try {
			return UpdateStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
			return UpdateStatus.unknown;
		}
	}
}

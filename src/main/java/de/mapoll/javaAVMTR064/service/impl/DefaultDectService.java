package de.mapoll.javaAVMTR064.service.impl;

import de.mapoll.javaAVMTR064.Action;
import de.mapoll.javaAVMTR064.FritzConnection;
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import de.mapoll.javaAVMTR064.exception.FritzServiceException;
import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;
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
		b.setActive(response.getValueAsBoolean("NewActive"));
		b.setName(response.getValueAsString("NewName"));
		b.setModel(response.getValueAsString("NewModel"));
		b.setUpdateAvailable(response.getValueAsBoolean("NewUpdateAvailable"));
		b.setUpdateInfo(response.getValueAsString("NewUpdateInfo"));
		b.setUpdateStatus(determineUpdateStatus(response.getValueAsString("NewUpdateSuccessful")));
		
		return b.build();
	}
	
	private UpdateStatus determineUpdateStatus(final String status) {
		try {
			return UpdateStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
			return UpdateStatus.unknown;
		}
	}
}

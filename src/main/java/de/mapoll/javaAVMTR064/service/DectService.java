package de.mapoll.javaAVMTR064.service;

import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;

/**
 * Wrapper to the AVM TR-064 DECT-Service: urn:X_AVM-DE_Dect-com:serviceId:X_AVM-DE_Dect1
 *
 * @since 2.1
 */
public interface DectService {
	
	/**
	 * @return the amount of DECT-devices known to the FritzBox
	 * @throws de.mapoll.javaAVMTR064.exception.FritzServiceException if something goes wrong.
	 */
	int findNumberOfDectEntries();
	
	/**
	 * @param index must be >= 0 and < {@link #findNumberOfDectEntries()}
	 * @return an immutable {@link GenericDectEntry} with data of the device at the specified index. Never null!
	 * @throws de.mapoll.javaAVMTR064.exception.FritzServiceException if something goes wrong (e.g. the index is invalid)
	 */
	GenericDectEntry findGenericDectEntry(int index);
}

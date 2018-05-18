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
package de.mapoll.javaAVMTR064.service;

import de.mapoll.javaAVMTR064.model.dect.GenericDectEntry;
import de.mapoll.javaAVMTR064.model.dect.SpecificDectEntry;

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
	 * Similar to {@link #findGenericDectEntry(int)} but finds the device/entry by its ID instead of index.
	 *
	 * @param id of the DectEntry
	 * @return an immutable {@link SpecificDectEntry} with data of the device with the specified ID. Never null!
	 * @throws de.mapoll.javaAVMTR064.exception.FritzServiceException if something goes wrong (e.g. the ID is invalid)
	 */
	SpecificDectEntry findSpecificDectEntry(String id);
	
	/**
	 * @param index must be >= 0 and < {@link #findNumberOfDectEntries()}
	 * @return an immutable {@link GenericDectEntry} with data of the device at the specified index. Never null!
	 * @throws de.mapoll.javaAVMTR064.exception.FritzServiceException if something goes wrong (e.g. the index is invalid)
	 */
	GenericDectEntry findGenericDectEntry(int index);
}

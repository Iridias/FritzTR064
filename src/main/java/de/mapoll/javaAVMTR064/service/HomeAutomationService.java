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

import de.mapoll.javaAVMTR064.model.homeauto.GenericDeviceInfos;
import de.mapoll.javaAVMTR064.model.homeauto.SpecificDeviceInfos;
import de.mapoll.javaAVMTR064.model.homeauto.SwitchState;

/**
 * Wrapper to the AVM TR-064 Homeauto-Service: urn:X_AVM-DE_Homeauto-com:serviceId:X_AVM-DE_Homeauto1
 *
 * @since 2.1
 */
public interface HomeAutomationService {
	
	/**
	 * @param index must be >= 0
	 * @return an immutable {@link GenericDeviceInfos} with data of the device at the specified index. Never null!
	 * @throws de.mapoll.javaAVMTR064.exception.FritzServiceException if something goes wrong (e.g. the index is invalid)
	 */
	GenericDeviceInfos findGenericDeviceInfos(int index);
	
	/**
	 * @param deviceIdentifier of the DeviceInfo
	 * @return an immutable {@link SpecificDeviceInfos} with data of the device with the specified deviceIdentifier. Never null!
	 * @throws de.mapoll.javaAVMTR064.exception.FritzServiceException if something goes wrong (e.g. the ID is invalid)
	 */
	SpecificDeviceInfos findSpecificDeviceInfos(String deviceIdentifier);
	
	void configureSocketState(String deviceIdentifier, SwitchState switchState);
}

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
import de.mapoll.javaAVMTR064.Response;
import de.mapoll.javaAVMTR064.Service;
import de.mapoll.javaAVMTR064.exception.FritzServiceException;
import de.mapoll.javaAVMTR064.service.ResponseToModelConverter;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractDefaultService {
	
	protected Service service;
	
	protected <T> T executeAction(final String actionName, final Map<String,Object> parameters) {
		return executeAction(actionName, parameters, null);
	}
	
	protected <T> T executeAction(final String actionName, final Map<String,Object> parameters, final ResponseToModelConverter<T> converter) {
		Action action = service.getAction(actionName);
		try {
			Response response = parameters != null ? action.execute(parameters) : action.execute();
			return converter != null ? converter.buildModelFromResponse(response, parameters) : null;
		} catch (IOException |NoSuchFieldException e) {
			throw new FritzServiceException("Unable to execute action " + actionName, e);
		}
	}
	
	protected <T> T firstValueFromMap(final Map<String,Object> map, final Class<T> type) {
		return (T) map.entrySet().iterator().next().getValue();
	}
	
}

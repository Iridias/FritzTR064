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
package de.mapoll.javaAVMTR064.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.UUID;

public class HelperUtils {
	
	private static final Logger log = LoggerFactory.getLogger(HelperUtils.class);
	
	private HelperUtils() {}
	
	
	public static Type findJavaTypeForDataType(final String dataTypeName) {
		if("boolean".equals(dataTypeName)) {
			return Boolean.class;
		}  else if("string".equals(dataTypeName)) {
			return String.class;
		} else if("dateTime".equals(dataTypeName)) {
			return Date.class;
		} else if("uuid".equals(dataTypeName)) {
			return UUID.class;
		} else if(dataTypeName.matches("u?i[0-9]+")) {
			return Integer.class;
		}
		log.error("Unknown DataType: {}", dataTypeName);
		return null;
	}
}

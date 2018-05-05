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

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HelperUtilsTest {
	
	@Test
	public void testFindJavaTypeForDataType() {
		assertEquals(String.class, HelperUtils.findJavaTypeForDataType("string"));
		assertEquals(Boolean.class, HelperUtils.findJavaTypeForDataType("boolean"));
		assertEquals(Date.class, HelperUtils.findJavaTypeForDataType("dateTime"));
		assertEquals(UUID.class, HelperUtils.findJavaTypeForDataType("uuid"));
		assertEquals(Integer.class, HelperUtils.findJavaTypeForDataType("ui4"));
		assertEquals(Integer.class, HelperUtils.findJavaTypeForDataType("ui2"));
		assertEquals(Integer.class, HelperUtils.findJavaTypeForDataType("i8"));
		assertEquals(Integer.class, HelperUtils.findJavaTypeForDataType("i12"));
	}
	
	@Test
	public void testFindJavaTypeForDataTypeUnknown() {
		assertNull(HelperUtils.findJavaTypeForDataType("invalid"));
	}
	
}

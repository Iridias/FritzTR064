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

import org.junit.Before;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DefaultHomeAutomationServiceTest extends AbstractServiceTest {
	
	private static final String SERVICE_NAME = "X_AVM-DE_Homeauto:1";
	
	private DefaultHomeAutomationService sut;
	
	@Before
	public void init() {
		super.init(SERVICE_NAME);
		sut = new DefaultHomeAutomationService();
		
		verify(connection, times(1)).getService(SERVICE_NAME);
	}
	
	
}

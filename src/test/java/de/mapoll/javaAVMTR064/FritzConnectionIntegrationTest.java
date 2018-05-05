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
package de.mapoll.javaAVMTR064;

import de.mapoll.javaAVMTR064.exception.ProtocolException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class FritzConnectionIntegrationTest extends AbstractIntegrationTest {
	
	private FritzConnection sut;
	
	@Before
	public void init() throws IOException {
		super.init();
		sut = new FritzConnection(host);
	}

	@Test
	public void test() throws IOException, ProtocolException, NoSuchFieldException {
		sut.setCredentials(user, password);
		sut.init();
		
		for(Map.Entry<String,Service> entry : sut.getServices().entrySet()) {
			checkEntryKey(entry);
			checkServiceActions(entry.getValue());
		}
	}
	
	private void checkEntryKey(final Map.Entry<String,Service> entry) {
		assertNotNull(entry);
		assertNotNull(entry.getKey());
		assertNotNull(entry.getValue());
		log.info("Service: {}", entry.getKey());
	}
	
	private void checkServiceActions(final Service service) {
		assertNotNull(service.getActions());
		for(Map.Entry<String,Action> entry : service.getActions().entrySet()) {
			assertNotNull(entry.getKey());
			assertNotNull(entry.getValue());
			log.info("Service - Action: {}", entry.getKey());
		}
	}
}

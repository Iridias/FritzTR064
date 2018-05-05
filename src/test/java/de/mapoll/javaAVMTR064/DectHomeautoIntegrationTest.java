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
import java.util.Collections;
import java.util.Map;

public class DectHomeautoIntegrationTest extends AbstractIntegrationTest {
	
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
		
		int devices = checkDectDevices();
		checkServiceHomeauto(devices);
	}
	
	
	private int checkDectDevices() throws IOException, NoSuchFieldException {
		Service sd = sut.getService("X_AVM-DE_Dect:1");
		Action ad = sd.getAction("GetNumberOfDectEntries");
		Response rd = ad.execute();
		
		int activeAmount = 0;
		int amount = rd.getValueAsInteger("NewNumberOfEntries");
		for(int i=0; i<amount; i++) {
			Response r = checkExecuteAction(sd.getAction("GetGenericDectEntry"), Collections.singletonMap("NewIndex", i));
			if(r.getValueAsBoolean("NewActive")) {
				++activeAmount;
			}
		}
		
		return activeAmount;
	}
	
	private void checkServiceHomeauto(final int deviceAmount) throws IOException {
		Service s = sut.getService("X_AVM-DE_Homeauto:1");
		checkExecuteAction(s.getAction("GetInfo"));
		for(int i=0; i<deviceAmount; i++) {
			checkExecuteAction(s.getAction("GetGenericDeviceInfos"), Collections.singletonMap("NewIndex", i));
		}
	}
	
	private Response checkExecuteAction(final Action a) throws IOException {
		return checkExecuteAction(a, null);
	}
	
	private Response checkExecuteAction(final Action a, final Map<String, Object> arguments) throws IOException {
		log.info("Args: {}", a.getArguments());
		Response  r = a.execute(arguments);
		for(Map.Entry<String,String> e : r.getData().entrySet()) {
			log.info("Action Response: {} - {}", e.getKey(), e.getValue());
		}
		
		return r;
	}
}

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

import de.mapoll.javaAVMTR064.beans.RootType;
import de.mapoll.javaAVMTR064.beans.ScpdType;
import de.mapoll.javaAVMTR064.exception.ProtocolException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JAXBParserServiceTest {
	
	private JAXBParserService sut;
	
	@Before
	public void init() {
		sut = new JAXBParserService();
	}
	
	@Test
	public void testParseInternetGatewayDeviceDescription() throws FileNotFoundException, ProtocolException {
		InputStream xml = createInputStreamFromTestFile("igddesc.xml");
		
		RootType result = sut.parseInternetGatewayDeviceDescription(xml);
		assertNotNull(result);
		checkSpecVersion(result);
		
		assertEquals("urn:schemas-upnp-org:device:InternetGatewayDevice:1", result.getDevice().getDeviceType());
		assertNotNull(result.getDevice().getServiceList());
		assertNotNull(result.getDevice().getServiceList().getService());
		assertEquals(1, result.getDevice().getServiceList().getService().size());
		assertNotNull(result.getDevice().getDeviceList());
		assertNotNull(result.getDevice().getDeviceList().getDevice());
		assertEquals(1, result.getDevice().getDeviceList().getDevice().size());
	}
	
	@Test
	public void testParseTR64Description() throws FileNotFoundException, ProtocolException {
		InputStream xml = createInputStreamFromTestFile("tr64desc.xml");
		
		RootType result = sut.parseTR64Description(xml);
		assertNotNull(result);
		checkSpecVersion(result);
		
		assertEquals("urn:dslforum-org:device:InternetGatewayDevice:1", result.getDevice().getDeviceType());
		assertEquals("FRITZ!Box 7490", result.getDevice().getFriendlyName());
		assertNotNull(result.getDevice().getServiceList());
		assertNotNull(result.getDevice().getServiceList().getService());
		assertEquals(22, result.getDevice().getServiceList().getService().size());
		assertNotNull(result.getDevice().getDeviceList());
		assertNotNull(result.getDevice().getDeviceList().getDevice());
		assertEquals(2, result.getDevice().getDeviceList().getDevice().size());
	}
	
	private void checkSpecVersion(final RootType result) {
		assertNotNull(result.getSpecVersion());
		assertNotNull(result.getDevice());
		assertEquals(1, result.getSpecVersion().getMajor());
		assertEquals(0, result.getSpecVersion().getMinor());
	}
	
	
	@Test
	public void testParseSCPDData() throws FileNotFoundException, ProtocolException {
		InputStream xml = createInputStreamFromTestFile("deviceinfoSCPD.xml");
		
		ScpdType result = sut.parseSCPDData(xml);
		assertNotNull(result);
		assertNotNull(result.getSpecVersion());
		assertEquals(1, result.getSpecVersion().getMajor());
		assertEquals(0, result.getSpecVersion().getMinor());
		
		assertNotNull(result.getActionList());
		assertNotNull(result.getActionList().getAction());
		assertEquals(4, result.getActionList().getAction().size());
		
		assertNotNull(result.getActionList().getAction().get(0));
		assertNotNull(result.getActionList().getAction().get(0).getArgumentList());
		assertNotNull(result.getActionList().getAction().get(0).getArgumentList().getArgument());
	}
	
	private InputStream createInputStreamFromTestFile(final String fileName) throws FileNotFoundException {
		File file = new File(getClass().getResource("/"+fileName).getFile());
		assertTrue(file.isFile());
		
		return new FileInputStream(file);
	}
}

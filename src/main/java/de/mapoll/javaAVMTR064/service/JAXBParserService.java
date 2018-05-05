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

import de.mapoll.javaAVMTR064.ParserService;
import de.mapoll.javaAVMTR064.beans.RootType;
import de.mapoll.javaAVMTR064.beans.ScpdType;
import de.mapoll.javaAVMTR064.exception.ProtocolException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;

public class JAXBParserService implements ParserService {
	
	private XMLReader reader;
	
	@Override
	public RootType parseTR64Description(final InputStream xml) throws ProtocolException {
		return parseDescriptionInternal(xml, RootType.class);
	}
	
	@Override
	public RootType parseInternetGatewayDeviceDescription(final InputStream xml) throws ProtocolException {
		return parseDescriptionInternal(xml, RootType.class);
	}
	
	@Override
	public ScpdType parseSCPDData(final InputStream xml) throws ProtocolException {
		return parseDescriptionInternal(xml, ScpdType.class);
	}
	
	/**
	 * We use the indirection over SAXParser in order to ignore the namespace.
	 * Thats because the TR64-descriptor and the Internet-Gateway-Device-descriptor
	 * are using the exact same XML-layout, but different namespaces. As long as there is no difference,
	 * there is no need to duplicate the structure-types.
	 * But remember: as soon as there is a difference, you have to duplicate all of them to fulfil SRP!
	 *
	 * @param xml
	 * @param type
	 * @return
	 * @throws ProtocolException
	 */
	private <T> T parseDescriptionInternal(final InputStream xml, final Class<T> type) throws ProtocolException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			final Source er = new SAXSource(getNamespaceUnawareXmlReader(), new InputSource(xml));
			
			return (T) jaxbUnmarshaller.unmarshal(er);
		} catch (JAXBException e) {
			throw new ProtocolException(e.getMessage(), e.getErrorCode(), e);
		} catch (SAXException | ParserConfigurationException e) {
			throw new ProtocolException(e.getMessage(), null, e);
		}
	}
	
	private XMLReader getNamespaceUnawareXmlReader() throws ParserConfigurationException, SAXException {
		if(reader == null) {
			final SAXParserFactory sax = SAXParserFactory.newInstance();
			sax.setNamespaceAware(false);
			reader = sax.newSAXParser().getXMLReader();
		}
		
		return reader;
	}
	
}

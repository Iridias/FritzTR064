/*
 * *********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2015 Marin Pollmann <pollmann.m@gmail.com>
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

import de.mapoll.javaAVMTR064.beans.ActionType;
import de.mapoll.javaAVMTR064.beans.ScpdType;
import de.mapoll.javaAVMTR064.beans.ServiceType;
import de.mapoll.javaAVMTR064.core.CommunicationService;
import de.mapoll.javaAVMTR064.core.ParserService;
import de.mapoll.javaAVMTR064.exception.ProtocolException;
import de.mapoll.javaAVMTR064.core.service.ServiceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Service {

    private ServiceType serviceXML;
    private Map<String, Action> actions;
    private ParserService parserService = ServiceFactory.getParserService();

    public Service(final ServiceType serviceXML, final CommunicationService communicationService) throws IOException, ProtocolException {
        this.serviceXML = serviceXML;
        actions = new HashMap<>();
    
        InputStream data = communicationService.retrieveData(serviceXML.getSCPDURL());
        ScpdType scpd = parserService.parseSCPDData(data);
        for (ActionType a : scpd.getActionList().getAction()) {
            actions.put(a.getName(), new Action(a, scpd.getServiceStateTable().getStateVariable(), communicationService, this.serviceXML));
        }
    }

    public Map<String, Action> getActions() {
        return actions;
    }

    public Action getAction(String name) {
        return getActions().get(name);
    }

    public String toString() {
        return serviceXML.getServiceType();
    }
}

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the 
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.cluster.hasingleton.service.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class SingletonAccessBean implements SingletonAccess {
    private static final Logger LOGGER = LoggerFactory.getLogger(SingletonAccessBean.class);

    public String getInstanceId() {
        LOGGER.info("getInstanceId() is called()");
        ServiceController<?> service = CurrentServiceContainer.getServiceContainer().getService(ClusterwideSingletonService.SINGLETON_SERVICE_NAME);
        LOGGER.debug("SERVICE {}", service);

        if (service != null) {
            ClusterWideSingleton singl = (ClusterWideSingleton) service.getValue();
            return singl.getInstanceId();
        } else {
            throw new IllegalStateException("Service '" + ClusterwideSingletonService.SINGLETON_SERVICE_NAME + "' not found!");
        }
    }
}

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
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.Clustered;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Singleton
//@Remote
@Stateful
@Clustered
public class ClusterWideSingletonBean implements ClusterWideSingleton {

    private static Logger LOGGER = LoggerFactory.getLogger(ClusterWideSingletonBean.class);

    @PostConstruct
    public void postConstr() {
        LOGGER.info("YOU SHOULD SEE THIS ONLY ONCE");
    }

    public void init() {
        LOGGER.info("YOU SHOULD SEE THIS ONLY ONCE -- init() called");
    }

    private int serialNumber = 0;

    @Override
    public String getInstanceId() {
        // the @yadayada of this object + serial
        return this.toString() + " serial number: " + serialNumber++;
    }
}

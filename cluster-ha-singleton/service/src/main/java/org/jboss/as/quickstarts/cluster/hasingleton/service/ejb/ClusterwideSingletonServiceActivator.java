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

import org.jboss.as.clustering.singleton.SingletonService;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.server.ServerEnvironment;
import org.jboss.as.server.ServerEnvironmentService;
import org.jboss.logging.Logger;
import org.jboss.msc.service.DelegatingServiceContainer;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceListener;

/**
 * Service activator that installs the ClusterwideSingletonService as a clustered singleton service
 * during deployment.
 *
 * @author Paul Ferraro
 */
public class ClusterwideSingletonServiceActivator implements ServiceActivator {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void activate(ServiceActivatorContext context) {
        log.info("ClusterwideSingletonService will be installed!");

        final ClusterwideSingletonService service = new ClusterwideSingletonService();
        final SingletonService<ClusterWideSingleton> singleton = new SingletonService<ClusterWideSingleton>(service, ClusterwideSingletonService.SINGLETON_SERVICE_NAME);

        /*
         * We can pass a chain of election policies to the singleton, for example to tell JGroups to prefer running the singleton on a node with a
         * particular name
         */
        // singleton.setElectionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference("node2/cluster")));

        // Workaround for JBoss AS 7.1.2
        // In later releases, SingleService.build(...) accepts a service target
        singleton.build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()).addListener(new ServiceListener<Object>() {
            @Override
            public void listenerAdded(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void transition(ServiceController<?> controller, ServiceController.Transition transition) {
                log.info("ZAUJIMAVA TRANSITION: " + transition);
            }

            @Override
            public void serviceRemoveRequested(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void serviceRemoveRequestCleared(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void dependencyFailed(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void dependencyFailureCleared(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void immediateDependencyUnavailable(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void immediateDependencyAvailable(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void transitiveDependencyUnavailable(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }

            @Override
            public void transitiveDependencyAvailable(ServiceController<?> controller) {
                // TODO: Customise this generated block
            }
        }))
                // singleton.build(context.getServiceTarget())
                .addDependency(ServerEnvironmentService.SERVICE_NAME, ServerEnvironment.class, service.env)
                .setInitialMode(ServiceController.Mode.ACTIVE)
                .addListener(new ServiceListener<ClusterWideSingleton>() {
                    @Override
                    public void listenerAdded(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void transition(ServiceController<? extends ClusterWideSingleton> controller, ServiceController.Transition transition) {
                        // TODO: Customise this generated block
                        log.info("TRANSITION: " + transition);

                        if(transition.getAfter().equals(ServiceController.Substate.UP)) {
                            if (!singleton.isMaster()) {
                                ServiceController<?> servicex = CurrentServiceContainer.getServiceContainer().getService(ClusterwideSingletonService.SINGLETON_SERVICE_NAME);
                                ClusterWideSingleton singl = (ClusterWideSingleton) servicex.getValue();

                                service.setLookedupSingletonInstance(singl);

                                log.info("This is a slave node. Getting reference to the bean from the master. " + singl);

                            }
                        }
                    }

                    @Override
                    public void serviceRemoveRequested(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void serviceRemoveRequestCleared(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void dependencyFailed(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void dependencyFailureCleared(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void immediateDependencyUnavailable(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void immediateDependencyAvailable(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void transitiveDependencyUnavailable(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }

                    @Override
                    public void transitiveDependencyAvailable(ServiceController<? extends ClusterWideSingleton> controller) {
                        // TODO: Customise this generated block
                    }
                })
                .install()
        ;



        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();  // TODO: Customise this generated block
        }

        if (!singleton.isMaster()) {
            ServiceController<?> servicex = CurrentServiceContainer.getServiceContainer().getService(ClusterwideSingletonService.SINGLETON_SERVICE_NAME);
            ClusterWideSingleton singl = (ClusterWideSingleton) servicex.getValue();

            service.setLookedupSingletonInstance(singl);

            log.info("This is a slave node. Getting reference to the bean from the master. " + singl);

        }*/


    }
}

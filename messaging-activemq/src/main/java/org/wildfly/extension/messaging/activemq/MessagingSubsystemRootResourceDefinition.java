/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.extension.messaging.activemq;

import static org.jboss.as.controller.SimpleAttributeDefinitionBuilder.create;
import static org.jboss.dmr.ModelType.INT;

import java.util.Arrays;
import java.util.Collection;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.capability.RuntimeCapability;

/**
 * {@link org.jboss.as.controller.ResourceDefinition} for the messaging subsystem root resource.
 *
 * @author Brian Stansberry (c) 2011 Red Hat Inc.
 */
public class MessagingSubsystemRootResourceDefinition extends PersistentResourceDefinition {
    public static final RuntimeCapability<Void> CONFIGURATION_CAPABILITY = RuntimeCapability.Builder.of("org.wildfly.messaging.activemq.external.configuration", false)
            .setServiceType(ExternalBrokerConfigurationService.class)
            .build();

    public static final SimpleAttributeDefinition GLOBAL_CLIENT_THREAD_POOL_MAX_SIZE = create("global-client-thread-pool-max-size", INT)
            .setAttributeGroup("global-client")
            .setXmlName("thread-pool-max-size")
            .setRequired(false)
            .setAllowExpression(true)
            .setRestartAllServices()
            .build();

    public static final SimpleAttributeDefinition GLOBAL_CLIENT_SCHEDULED_THREAD_POOL_MAX_SIZE = create("global-client-scheduled-thread-pool-max-size", INT)
            .setAttributeGroup("global-client")
            .setXmlName("scheduled-thread-pool-max-size")
            .setRequired(false)
            .setAllowExpression(true)
            .setRestartAllServices()
            .build();

    public static final AttributeDefinition[] ATTRIBUTES = {
            GLOBAL_CLIENT_THREAD_POOL_MAX_SIZE,
            GLOBAL_CLIENT_SCHEDULED_THREAD_POOL_MAX_SIZE
    };

    public static final MessagingSubsystemRootResourceDefinition INSTANCE = new MessagingSubsystemRootResourceDefinition();

    private MessagingSubsystemRootResourceDefinition() {
        super(new SimpleResourceDefinition.Parameters(MessagingExtension.SUBSYSTEM_PATH,
                MessagingExtension.getResourceDescriptionResolver(MessagingExtension.SUBSYSTEM_NAME))
                .setAddHandler(MessagingSubsystemAdd.INSTANCE)
                .setRemoveHandler(new ReloadRequiredRemoveStepHandler())
                .setCapabilities(CONFIGURATION_CAPABILITY));
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Arrays.asList(ATTRIBUTES);
    }
}

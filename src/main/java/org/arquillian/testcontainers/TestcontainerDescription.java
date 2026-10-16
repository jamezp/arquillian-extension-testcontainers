/*
 * Copyright The Arquillian Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.arquillian.testcontainers;

import java.util.concurrent.atomic.AtomicBoolean;

import org.arquillian.testcontainers.api.Testcontainer;
import org.testcontainers.containers.GenericContainer;

/**
 * A holder for information about the Testcontainer being injected into a field.
 *
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
class TestcontainerDescription {

    private final Testcontainer testcontainer;

    private final String name;

    private final GenericContainer<?> instance;
    private final AtomicBoolean started = new AtomicBoolean(false);

    TestcontainerDescription(final Testcontainer testcontainer, final GenericContainer<?> instance) {
        this.testcontainer = testcontainer;
        this.name = testcontainer.name();
        this.instance = instance;
    }

    /**
     * Starts the container if it has not already been started
     */
    void start() {
        if (started.compareAndSet(false, true)) {
            instance.start();
        }
    }

    /**
     * Stops the container if it has been started
     */
    void stop() {
        if (started.get()) {
            instance.stop();
        }
    }

    /**
     * The annotation that was on the field
     */
    Testcontainer testcontainer() {
        return testcontainer;
    }

    /**
     * The name of the container, or an empty string for unnamed containers
     */
    String name() {
        return name;
    }

    /**
     * The instance of the container created
     */
    GenericContainer<?> instance() {
        return instance;
    }
}

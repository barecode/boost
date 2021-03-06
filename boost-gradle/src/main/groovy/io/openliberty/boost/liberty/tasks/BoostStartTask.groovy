/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.boost.liberty.tasks

import org.gradle.api.logging.LogLevel

public class BoostStartTask extends AbstractBoostTask {

    BoostStartTask() {
        configure({
            description 'Starts the Boost application'
            logging.level = LogLevel.INFO
            group 'Boost'

            dependsOn 'libertyStart'

            doFirst {
                logger.info('Starting the application.')
            }
        })
    }
}
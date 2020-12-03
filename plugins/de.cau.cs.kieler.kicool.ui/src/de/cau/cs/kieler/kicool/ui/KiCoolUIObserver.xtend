/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.kicool.ui

import de.cau.cs.kieler.kicool.compilation.observer.AbstractContextNotification
import java.util.Observable
import java.util.Observer
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.ui.progress.UIJob
import java.util.concurrent.LinkedBlockingQueue

/**
 * UI observer that is able to run inside the UI thread (even if the context is not).
 * 
 * @author ssm
 * @kieler.design 2017-02-24 proposed
 * @kieler.rating 2017-02-24 proposed yellow 
 */
abstract class KiCoolUIObserver implements Observer {

    abstract def boolean runInUIThread()
    
    abstract def void update(AbstractContextNotification notification)
    
    val queue = new LinkedBlockingQueue<AbstractContextNotification>;
    var UIJob job = null
    
    override update(Observable o, Object arg) {
        if (arg instanceof AbstractContextNotification) {
            if (runInUIThread) {
                synchronized (queue) {
                    queue.add(arg)
                    if (job === null) {
                        job = new UIJob("Updating...") {
                            override IStatus runInUIThread(IProgressMonitor monitor) {
                                while (true) {
                                    queue.poll.update
                                    synchronized (queue) {
                                        if (queue.empty) {
                                            job = null
                                            return Status.OK_STATUS;
                                        }
                                    }
                                }
                            }
                        }
                        job.schedule
                    }
                }
            } else {
                while (!queue.empty) {
                    job.wait
                }
                arg.update
            }
        }
    }

}

/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.kico.klighd.model;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Model of KiCoModelView to represent errors and exceptions
 * 
 * @author als
 * @kieler.design 2014-07-30 proposed
 * @kieler.rating 2014-07-30 proposed yellow
 * 
 */
public class KiCoErrorModel {

    private final String message;
    private final String reason;
    private final String stacktrace;

    /**
     * Constructs a error model given message
     * 
     * @param message
     *            error message
     */
    public KiCoErrorModel(String message) {
        this.message = message;
        this.stacktrace = null;
        this.reason = "Unkown";
    }

    /**
     * Constructs a error model given message and details
     * 
     * @param message
     *            error message
     * @param reason
     * @param stacktrace
     */
    public KiCoErrorModel(String message, String reason, String stacktrace) {
        this.message = message;
        this.stacktrace = stacktrace;
        if (reason == null) {
            this.reason = "Unkown";
        } else {
            this.reason = reason;
        }
    }

    /**
     * Constructs a error model given message and exception
     * 
     * @param message
     *            error message
     * @param exception
     */
    public KiCoErrorModel(String message, Exception exception) {
        this.message = message;
        // Print stack trace into string
        StringWriter traceReader = new StringWriter();
        exception.printStackTrace(new PrintWriter(traceReader));
        String exceptionTrace = traceReader.toString();
        this.stacktrace = exceptionTrace;
        if(exception.getMessage() == null || exception.getMessage().isEmpty()){
            this.reason = exception.toString();
        }else{
            this.reason = exception.getMessage();
        }
    }

    /**
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the exception
     */
    public String getStackTrace() {
        return stacktrace;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }
}

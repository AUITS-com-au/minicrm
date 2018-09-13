package com.sh.crm.general.utils;

import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggingUtils {
    public static void logStackTrace(Logger logger, Throwable cause, String level) {
        if (logger != null && level != null && cause != null) {
            StringWriter stringWriter = new StringWriter();
            cause.printStackTrace(new PrintWriter(stringWriter, true));
            level = level.toLowerCase();
            switch (level) {
                case "debug":
                    if (logger.isDebugEnabled()) {
                        logger.debug("Exception Thrown, Cause : {}", stringWriter.toString());
                    }
                    break;
                case "info":
                    if (logger.isInfoEnabled()) {
                        logger.info("Exception Thrown, Cause : {}", stringWriter.toString());
                    }
                    break;
                case "trace":
                    if (logger.isTraceEnabled()) {
                        logger.trace("Exception Thrown, Cause : {}", stringWriter.toString());
                    }
                    break;
                case "error":

                    logger.error("Exception Thrown, Cause : {}", stringWriter.toString());
                    break;

                default:
                    return;
            }
            stringWriter = null;
            level = null;
            cause = null;
        }
    }
}

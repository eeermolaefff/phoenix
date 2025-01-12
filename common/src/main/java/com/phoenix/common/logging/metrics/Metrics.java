package com.phoenix.common.logging.metrics;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@UtilityClass
public class Metrics {

    private static final Logger metricsLogger = LoggerFactory.getLogger("MetricsLogger");

    public static void mark(String key, String subkey, Object value, String message) {
        try {
            Thread thread = Thread.currentThread();
            MDC.put("thread", thread.getName());
            MDC.put("logger", getLogger(thread));
            MDC.put("key", key);
            MDC.put("subkey", subkey);
            MDC.put("value", value == null ? "null" : value.toString());
            metricsLogger.info(message);
        } finally {
            MDC.remove("thread");
            MDC.remove("logger");
            MDC.remove("key");
            MDC.remove("subkey");
            MDC.remove("value");
        }
    }

    private static String getLogger(Thread currentThread) {
        StackTraceElement[] traces = currentThread.getStackTrace();
        StackTraceElement trace = traces[3];
        return getIdentifier(trace);
    }

    private static String getIdentifier(StackTraceElement trace) {
        if (trace == null)
            return "null";
        return trace.getClassName().split("\\$")[0] + "." + trace.getMethodName() + "(...)";
    }
}

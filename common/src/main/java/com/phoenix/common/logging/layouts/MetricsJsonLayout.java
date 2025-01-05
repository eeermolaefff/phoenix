package com.phoenix.common.logging.layouts;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;

import java.util.Map;

public class MetricsJsonLayout extends JsonLayout {

    public MetricsJsonLayout() {
        setTimestampFormat("yyyy-MM-dd HH:mm:ss.SSS");
        setTimestampFormatTimezoneId("Etc/UTC");
        setAppendLineSeparator(true);
        setIncludeContextName(false);
        setIncludeLevel(false);
        setIncludeThreadName(false);
        setIncludeLoggerName(false);
        setIncludeMessage(false);
    }

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        map.put("type", "metrics");
    }
}

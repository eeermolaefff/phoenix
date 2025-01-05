package com.phoenix.common.logging.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    LogType[] values() default { LogType.EXECUTION_TIME, LogType.ARGUMENTS, LogType.RETURNS };
}
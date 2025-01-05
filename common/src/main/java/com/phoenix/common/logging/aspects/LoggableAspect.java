package com.phoenix.common.logging.aspects;

import com.phoenix.common.logging.metrics.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggableAspect {

    @Pointcut("@annotation(Loggable)")
    public void callLoggableMethod() {}

    @Around("callLoggableMethod()")
    public Object aroundCallMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String identifier = signature.getDeclaringTypeName() + "." + method.getName() + "(...)";

        Loggable annotation = method.getAnnotation(Loggable.class);
        LogType[] logTypes = annotation.values();

        long timestampFrom = System.currentTimeMillis();
        try {
            if (containsIn(logTypes, LogType.ARGUMENTS))
                markArguments(identifier, joinPoint);

            Object result = joinPoint.proceed();
            if (containsIn(logTypes, LogType.RETURNS))
                markResult(identifier, result);

            return result;
        } finally {
            long timestampTo = System.currentTimeMillis();
            if (containsIn(logTypes, LogType.EXECUTION_TIME))
               markExecutionTime(identifier, timestampTo - timestampFrom);
        }
    }

    private boolean containsIn(LogType[] existedTypes, LogType searchedType) {
        for (LogType existedType : existedTypes)
            if (existedType.equals(searchedType))
                return true;
        return false;
    }

    private void markArguments(String identifier, ProceedingJoinPoint joinPoint) {
        String argumentsAsString = Arrays.stream(joinPoint.getArgs())
                .map(a -> a == null ? "null" : a.toString())
                .collect(Collectors.joining(", "));

        Metrics.mark(
                "arguments",
                identifier,
                "[" + argumentsAsString + "]",
                "Arguments received by the function"
        );
    }

    private void markResult(String identifier, Object result) {
        Metrics.mark(
                "return",
                identifier,
                result,
                "The value returned by the function"
        );
    }

    private void markExecutionTime(String identifier, Long timeMills) {
        Metrics.mark(
                "execution",
                identifier,
                timeMills,
                "Function execution time in milliseconds"
        );
    }
}

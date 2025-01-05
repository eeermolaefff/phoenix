package com.phoenix.common.grpc;

import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    public Status handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
    }

    @GrpcExceptionHandler
    public Status handleUnexpectedException(Exception e) {
        log.error("An unexpected exception has occurred", e);
        return Status.UNKNOWN.withDescription("An unexpected exception has occurred").withCause(e);
    }
}

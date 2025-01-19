package com.phoenix.common.grpc.hermes.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: hermes/hermes.proto")
public final class HermesGrpc {

  private HermesGrpc() {}

  public static final String SERVICE_NAME = "Hermes";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1,
      com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> getArbitrateV1Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "arbitrateV1",
      requestType = com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1.class,
      responseType = com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1,
      com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> getArbitrateV1Method() {
    io.grpc.MethodDescriptor<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1, com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> getArbitrateV1Method;
    if ((getArbitrateV1Method = HermesGrpc.getArbitrateV1Method) == null) {
      synchronized (HermesGrpc.class) {
        if ((getArbitrateV1Method = HermesGrpc.getArbitrateV1Method) == null) {
          HermesGrpc.getArbitrateV1Method = getArbitrateV1Method =
              io.grpc.MethodDescriptor.<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1, com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "arbitrateV1"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HermesMethodDescriptorSupplier("arbitrateV1"))
              .build();
        }
      }
    }
    return getArbitrateV1Method;
  }

  private static volatile io.grpc.MethodDescriptor<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2,
      com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> getArbitrateV2Method;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "arbitrateV2",
      requestType = com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2.class,
      responseType = com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2,
      com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> getArbitrateV2Method() {
    io.grpc.MethodDescriptor<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2, com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> getArbitrateV2Method;
    if ((getArbitrateV2Method = HermesGrpc.getArbitrateV2Method) == null) {
      synchronized (HermesGrpc.class) {
        if ((getArbitrateV2Method = HermesGrpc.getArbitrateV2Method) == null) {
          HermesGrpc.getArbitrateV2Method = getArbitrateV2Method =
              io.grpc.MethodDescriptor.<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2, com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "arbitrateV2"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new HermesMethodDescriptorSupplier("arbitrateV2"))
              .build();
        }
      }
    }
    return getArbitrateV2Method;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HermesStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HermesStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HermesStub>() {
        @java.lang.Override
        public HermesStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HermesStub(channel, callOptions);
        }
      };
    return HermesStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HermesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HermesBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HermesBlockingStub>() {
        @java.lang.Override
        public HermesBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HermesBlockingStub(channel, callOptions);
        }
      };
    return HermesBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HermesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HermesFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HermesFutureStub>() {
        @java.lang.Override
        public HermesFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HermesFutureStub(channel, callOptions);
        }
      };
    return HermesFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class HermesImplBase implements io.grpc.BindableService {

    /**
     */
    public void arbitrateV1(com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1 request,
        io.grpc.stub.StreamObserver<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getArbitrateV1Method(), responseObserver);
    }

    /**
     */
    public void arbitrateV2(com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2 request,
        io.grpc.stub.StreamObserver<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getArbitrateV2Method(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getArbitrateV1Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1,
                com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse>(
                  this, METHODID_ARBITRATE_V1)))
          .addMethod(
            getArbitrateV2Method(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2,
                com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse>(
                  this, METHODID_ARBITRATE_V2)))
          .build();
    }
  }

  /**
   */
  public static final class HermesStub extends io.grpc.stub.AbstractAsyncStub<HermesStub> {
    private HermesStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HermesStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HermesStub(channel, callOptions);
    }

    /**
     */
    public void arbitrateV1(com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1 request,
        io.grpc.stub.StreamObserver<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getArbitrateV1Method(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void arbitrateV2(com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2 request,
        io.grpc.stub.StreamObserver<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getArbitrateV2Method(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class HermesBlockingStub extends io.grpc.stub.AbstractBlockingStub<HermesBlockingStub> {
    private HermesBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HermesBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HermesBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse arbitrateV1(com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getArbitrateV1Method(), getCallOptions(), request);
    }

    /**
     */
    public com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse arbitrateV2(com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2 request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getArbitrateV2Method(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class HermesFutureStub extends io.grpc.stub.AbstractFutureStub<HermesFutureStub> {
    private HermesFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HermesFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HermesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> arbitrateV1(
        com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getArbitrateV1Method(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse> arbitrateV2(
        com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2 request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getArbitrateV2Method(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ARBITRATE_V1 = 0;
  private static final int METHODID_ARBITRATE_V2 = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final HermesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(HermesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ARBITRATE_V1:
          serviceImpl.arbitrateV1((com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV1) request,
              (io.grpc.stub.StreamObserver<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse>) responseObserver);
          break;
        case METHODID_ARBITRATE_V2:
          serviceImpl.arbitrateV2((com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageRequestV2) request,
              (io.grpc.stub.StreamObserver<com.phoenix.common.grpc.hermes.generated.HermesOuterClass.HermesArbitrageResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class HermesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HermesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.phoenix.common.grpc.hermes.generated.HermesOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Hermes");
    }
  }

  private static final class HermesFileDescriptorSupplier
      extends HermesBaseDescriptorSupplier {
    HermesFileDescriptorSupplier() {}
  }

  private static final class HermesMethodDescriptorSupplier
      extends HermesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    HermesMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (HermesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HermesFileDescriptorSupplier())
              .addMethod(getArbitrateV1Method())
              .addMethod(getArbitrateV2Method())
              .build();
        }
      }
    }
    return result;
  }
}

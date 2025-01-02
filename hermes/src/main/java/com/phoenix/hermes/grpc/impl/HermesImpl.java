package com.phoenix.hermes.grpc.impl;

import com.phoenix.common.hermes.grpc.generated.HermesGrpc;
import com.phoenix.common.hermes.grpc.generated.HermesOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HermesImpl extends HermesGrpc.HermesImplBase {

    @Override
    public void arbitrate(
            HermesOuterClass.Graph graph,
            StreamObserver<HermesOuterClass.Cycle> responseObserver
    ) {
        log.info("Graph: {}", graph);

        HermesOuterClass.Cycle.Builder cycleBuilder = HermesOuterClass.Cycle.newBuilder();

        for (HermesOuterClass.Edge edge : graph.getEdgeList()) {
            cycleBuilder.addVertex(edge.getVertexFrom());
            cycleBuilder.addVertex(edge.getVertexTo());
        }

        responseObserver.onNext(cycleBuilder.build());
        responseObserver.onCompleted();
    }
}

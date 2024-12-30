package com.pantheon.hermes.grpc.impl;

import com.pantheon.hermes.grpc.HermesOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import static com.pantheon.hermes.grpc.HermesGrpc.*;

@Slf4j
public class HermesImpl extends HermesImplBase {

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

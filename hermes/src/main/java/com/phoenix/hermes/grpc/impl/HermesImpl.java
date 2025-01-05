package com.phoenix.hermes.grpc.impl;

import com.phoenix.common.grpc.hermes.generated.HermesGrpc;
import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import com.phoenix.hermes.graph.Cycle;
import com.phoenix.hermes.graph.Graph;
import com.phoenix.hermes.graph.GraphMapper;
import com.phoenix.hermes.service.HermesService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class HermesImpl extends HermesGrpc.HermesImplBase {

    private final HermesService hermesService;

    @Override
    public void arbitrate(
            HermesOuterClass.Graph request,
            StreamObserver<HermesOuterClass.Cycles> responseObserver
    ) {
        validate(request);

        Graph graph = GraphMapper.map(request);
        Iterable<Cycle> cycles = hermesService.findAllCycles(graph);
        HermesOuterClass.Cycles foundCycles = GraphMapper.map(cycles);

        responseObserver.onNext(foundCycles);
        responseObserver.onCompleted();
    }

    private void validate(HermesOuterClass.Graph graph) {
        if (graph == null || graph.getEdgeCount() == 0)
            throw new IllegalArgumentException("Graph should not be null and empty");
    }
}

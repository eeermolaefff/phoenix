package com.phoenix.hermes.grpc.impl;

import com.phoenix.common.grpc.hermes.generated.HermesGrpc;
import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.cycles.mappers.CycleMapper;
import com.phoenix.hermes.arbitrage.graph.mappers.GraphMapper;
import com.phoenix.hermes.service.HermesService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.Set;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class HermesImpl extends HermesGrpc.HermesImplBase {

    private final HermesService hermesService;

    @Override
    public void arbitrate(
            HermesOuterClass.HermesArbitrageRequest request,
            StreamObserver<HermesOuterClass.HermesArbitrageResponse> responseObserver
    ) {
        validate(request);

        HermesService.RequestFields requestFields = extractRequestFields(request);
        Set<Cycle> cycles = hermesService.findAllCycles(requestFields);

        List<HermesOuterClass.Cycle> foundCycles = CycleMapper.mapCycles(cycles);
        responseObserver.onNext(
                HermesOuterClass.HermesArbitrageResponse.newBuilder()
                        .addAllCycles(foundCycles)
                        .build()
        );
        responseObserver.onCompleted();
    }

    private void validate(HermesOuterClass.HermesArbitrageRequest request) {
        if (!request.hasGraph())
            throw new IllegalArgumentException("Graph should be specified");

        HermesOuterClass.Graph graph = request.getGraph();
        if (graph.getEdgesCount() == 0)
            throw new IllegalArgumentException("Graph should not be empty");

        if (request.hasStartVertices() && request.getStartVertices().getStartVerticesList().isEmpty())
            throw new IllegalArgumentException("Start vertices list should not be empty");

        if (request.hasSearchParams()) {
            HermesOuterClass.SearchParams searchParams = request.getSearchParams();

            if (searchParams.getMinProfitAsPercent() < 0)
                throw new IllegalArgumentException("Min profit as percent should not be negative value");
            if (searchParams.getMaxProfitAsPercent() <= 0)
                throw new IllegalArgumentException("Max profit as percent should be positive value");
            if (searchParams.getComponentMinSize() < 0)
                throw new IllegalArgumentException("Component min size should not be negative value");
            if (searchParams.getNumberOfRandomLaunches() < 0)
                throw new IllegalArgumentException("Number of random launches should not be negative value");
        }
    }

    private HermesService.RequestFields extractRequestFields(HermesOuterClass.HermesArbitrageRequest request) {
        var builder = HermesService.RequestFields.builder().digraph(GraphMapper.mapGraph(request.getGraph()));

        if (request.hasStartVertices())
            builder.startVertices(request.getStartVertices().getStartVerticesList());
        if (request.hasSearchParams()) {
            HermesOuterClass.SearchParams searchParams = request.getSearchParams();
            builder.minProfitAsPercent((double) searchParams.getMinProfitAsPercent());
            builder.maxProfitAsPercent((double) searchParams.getMaxProfitAsPercent());
            builder.componentMinSize(searchParams.getComponentMinSize());
            builder.numberOfRandomLaunches(searchParams.getNumberOfRandomLaunches());
        }

        return builder.build();
    }
}

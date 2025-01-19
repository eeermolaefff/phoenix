package com.phoenix.hermes.common.grpc.impl;

import com.phoenix.common.grpc.hermes.generated.HermesGrpc;
import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import com.phoenix.hermes.common.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.common.cycles.mappers.CycleMapper;
import com.phoenix.hermes.common.grpc.mapper.RequestMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Set;

@Slf4j
@GrpcService
public class HermesImpl extends HermesGrpc.HermesImplBase {

    private final com.phoenix.hermes.v1.service.HermesService hermesServiceV1;
    private final com.phoenix.hermes.v2.service.HermesService hermesServiceV2;

    public HermesImpl(
            @Qualifier("hermesServiceV1") com.phoenix.hermes.v1.service.HermesService hermesServiceV1,
            @Qualifier("hermesServiceV2") com.phoenix.hermes.v2.service.HermesService hermesServiceV2
    ) {
        this.hermesServiceV1 = hermesServiceV1;
        this.hermesServiceV2 = hermesServiceV2;
    }

    @Override
    public void arbitrateV1(
            HermesOuterClass.HermesArbitrageRequestV1 request,
            StreamObserver<HermesOuterClass.HermesArbitrageResponse> responseObserver
    ) {
        validate(request);

        com.phoenix.hermes.v1.service.HermesService.RequestFields requestFields = RequestMapper.map(request);
        Set<Cycle> cycles = hermesServiceV1.findAllCycles(requestFields);

        List<HermesOuterClass.Cycle> foundCycles = CycleMapper.mapCycles(cycles);
        responseObserver.onNext(
                HermesOuterClass.HermesArbitrageResponse.newBuilder()
                        .addAllCycles(foundCycles)
                        .build()
        );
        responseObserver.onCompleted();
    }


    @Override
    public void arbitrateV2(
            HermesOuterClass.HermesArbitrageRequestV2 request,
            StreamObserver<HermesOuterClass.HermesArbitrageResponse> responseObserver
    ) {
        validate(request);

        com.phoenix.hermes.v2.service.HermesService.RequestFields requestFields = RequestMapper.map(request);
        Set<Cycle> cycles = hermesServiceV2.findAllCycles(requestFields);

        List<HermesOuterClass.Cycle> foundCycles = CycleMapper.mapCycles(cycles);
        responseObserver.onNext(
                HermesOuterClass.HermesArbitrageResponse.newBuilder()
                        .addAllCycles(foundCycles)
                        .build()
        );
        responseObserver.onCompleted();
    }

    private void validate(HermesOuterClass.HermesArbitrageRequestV1 request) {
        if (!request.hasGraph())
            throw new IllegalArgumentException("Graph should be specified");

        HermesOuterClass.Graph graph = request.getGraph();
        if (graph.getEdgesCount() == 0)
            throw new IllegalArgumentException("Graph should not be empty");

        if (request.hasFilterParams()) {
            HermesOuterClass.FilterParams filterParams = request.getFilterParams();
            validate(filterParams);
        }
    }


    private void validate(HermesOuterClass.HermesArbitrageRequestV2 request) {
        if (!request.hasGraph())
            throw new IllegalArgumentException("Graph should be specified");

        HermesOuterClass.Graph graph = request.getGraph();
        if (graph.getEdgesCount() == 0)
            throw new IllegalArgumentException("Graph should not be empty");

        if (request.hasFilterParams()) {
            HermesOuterClass.FilterParams filterParams = request.getFilterParams();
            validate(filterParams);
        }
    }

    private void validate(HermesOuterClass.FilterParams filterParams) {
        if (filterParams.getMinProfitAsPercent() < 0)
            throw new IllegalArgumentException("Min profit as percent should not be negative value");
        if (filterParams.getMaxProfitAsPercent() <= 0)
            throw new IllegalArgumentException("Max profit as percent should be positive value");
        if (filterParams.getComponentMinSize() < 0)
            throw new IllegalArgumentException("Component min size should not be negative value");
    }
}

package com.phoenix.hermes.common.grpc.mapper;

import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import com.phoenix.hermes.common.graph.mappers.GraphMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestMapper {

    public static com.phoenix.hermes.v1.service.HermesService.RequestFields map(
            HermesOuterClass.HermesArbitrageRequestV1 request) {
        var builder = com.phoenix.hermes.v1.service.HermesService.RequestFields.builder()
                .digraph(GraphMapper.mapGraph(request.getGraph()));

        if (request.hasRequestParams()) {
            HermesOuterClass.RequestParamsV1 requestParamsV1 = request.getRequestParams();
            builder.startVertices(requestParamsV1.getStartVerticesList());
            builder.numberOfRandomLaunches(requestParamsV1.getNumberOfRandomLaunches());
        }
        if (request.hasFilterParams()) {
            HermesOuterClass.FilterParams filterParams = request.getFilterParams();
            builder.minProfitAsPercent((double) filterParams.getMinProfitAsPercent());
            builder.maxProfitAsPercent((double) filterParams.getMaxProfitAsPercent());
            builder.componentMinSize(filterParams.getComponentMinSize());
        }

        return builder.build();
    }

    public static com.phoenix.hermes.v2.service.HermesService.RequestFields map(
            HermesOuterClass.HermesArbitrageRequestV2 request) {
        var builder = com.phoenix.hermes.v2.service.HermesService.RequestFields.builder()
                .digraph(GraphMapper.mapGraph(request.getGraph()));

        if (request.hasRequestParams()) {
            HermesOuterClass.RequestParamsV2 requestParamsV2 = request.getRequestParams();
            builder.verticesToConnect(requestParamsV2.getVerticesToConnectList());
            builder.startVertex(requestParamsV2.getStartVertex());
        }
        if (request.hasFilterParams()) {
            HermesOuterClass.FilterParams filterParams = request.getFilterParams();
            builder.minProfitAsPercent((double) filterParams.getMinProfitAsPercent());
            builder.maxProfitAsPercent((double) filterParams.getMaxProfitAsPercent());
            builder.componentMinSize(filterParams.getComponentMinSize());
        }

        return builder.build();
    }
}

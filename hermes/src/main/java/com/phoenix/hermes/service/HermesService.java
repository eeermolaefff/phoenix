package com.phoenix.hermes.service;

import com.phoenix.common.logging.aspects.Loggable;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

public interface HermesService {

    @Loggable
    Set<Cycle> findAllCycles(RequestFields requestFields);

    @Getter
    @Builder
    @ToString
    class RequestFields {
        private WeightedDigraph digraph;
        @Builder.Default
        private List<Integer> startVertices = List.of();
        @Builder.Default
        private Double minProfitAsPercent = 0.0;
        @Builder.Default
        private Double maxProfitAsPercent = Double.POSITIVE_INFINITY;
        @Builder.Default
        private Integer componentMinSize = 0;
        @Builder.Default
        private Integer numberOfRandomLaunches = 1;
    }
}

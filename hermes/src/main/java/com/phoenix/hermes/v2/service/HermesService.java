package com.phoenix.hermes.v2.service;

import com.phoenix.hermes.common.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.common.graph.impl.interfaces.WeightedDigraph;
import com.phoenix.hermes.common.graph.processing.Connectivity;

import com.phoenix.hermes.v1.cycles.processing.CyclesConnector;
import com.phoenix.hermes.v2.cycles.processing.Arbitrageur;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("hermesServiceV2")
public class HermesService {

    public Set<Cycle> findAllCycles(RequestFields requestFields) {
        Connectivity connectivity = new Connectivity(requestFields.getDigraph());
        log.info("Graph connectivity:\n {}", connectivity);

        validate(requestFields, connectivity);

        Arbitrageur arbitrage = new Arbitrageur(requestFields.getDigraph(), requestFields.getStartVertex());

        int componentId = connectivity.getComponentIdByVertex(requestFields.getStartVertex());
        CyclesConnector connector = new CyclesConnector();
        connector.addCyclesToComponent(componentId, arbitrage.getFoundCycles());
        connector.addArbitrageToComponent(componentId, arbitrage);
        return connector.getAllCyclesConnected();   // TODO process vertices to connect
    }

    private void validate(RequestFields requestFields, Connectivity connectivity) {
       int componentSize = connectivity.getComponentSizeById(requestFields.startVertex);
       if (componentSize < requestFields.getComponentMinSize())
           throw new IllegalArgumentException("The graph has no components of connectivity with required min size");
    }

    @Getter
    @Builder
    @ToString
    public static class RequestFields {
        private WeightedDigraph digraph;
        private Integer startVertex;
        @Builder.Default
        private List<Integer> verticesToConnect = List.of();
        @Builder.Default
        private Double minProfitAsPercent = 0.0;
        @Builder.Default
        private Double maxProfitAsPercent = Double.POSITIVE_INFINITY;
        @Builder.Default
        private Integer componentMinSize = 0;
    }
}

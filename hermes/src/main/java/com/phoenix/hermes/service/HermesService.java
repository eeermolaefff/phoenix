package com.phoenix.hermes.service;

import com.phoenix.common.logging.aspects.Loggable;
import com.phoenix.common.utils.Pair;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.ConnectedCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.cycles.processing.Arbitrage;
import com.phoenix.hermes.arbitrage.cycles.processing.ArbitrageFinder;
import com.phoenix.hermes.arbitrage.cycles.processing.CyclesConnector;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;
import com.phoenix.hermes.arbitrage.graph.processing.Connectivity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HermesService {

    private final Random random = new Random();

    @Loggable
    public Set<Cycle> findAllCycles(RequestFields requestFields) {
        Connectivity connectivity = new Connectivity(requestFields.getDigraph());
        log.info("Graph connectivity:\n {}", connectivity);
        validate(requestFields, connectivity);

        List<Pair<Integer, Boolean>> launchVertices =
                generateLaunchVerticesWithArbitrageSavingParam(connectivity, requestFields);

        CyclesConnector connector = new CyclesConnector();
        launchArbitragesFromStartVertices(requestFields.getDigraph(), connectivity, launchVertices, connector);
        return requestFields.getStartVertices().isEmpty() ?
                connector.getAllCycles() : connector.getAllCyclesConnected();

    }

    private void validate(RequestFields requestFields, Connectivity connectivity) {
       int maxComponentSize = Arrays.stream(connectivity.getAllComponents())
               .map(List::size)
               .max(Integer::compareTo)
               .orElse(0);

       if (maxComponentSize < requestFields.getComponentMinSize())
           throw new IllegalArgumentException("The graph has no components of connectivity with required min size");
    }

    private void launchArbitragesFromStartVertices(
            WeightedDigraph graph,
            Connectivity connectivity,
            List<Pair<Integer, Boolean>> startVertices,
            CyclesConnector connector
    ) {
        for (Pair<Integer, Boolean> pair : startVertices) {
            int startVertex = pair.getFirst();
            boolean saveStartVertexArbitrage = pair.getSecond();

            Pair<Arbitrage, Set<Cycle>> result = ArbitrageFinder.findCycles(graph, startVertex);

            int componentId = connectivity.getComponentIdByVertex(startVertex);
            Arbitrage arbitrage = result.getFirst();
            Set<Cycle> foundCycles = result.getSecond();

            connector.addCyclesToComponent(componentId, foundCycles);
            if (saveStartVertexArbitrage)
                connector.addArbitrageToComponent(componentId, arbitrage);
        }
    }

    private List<Pair<Integer, Boolean>> generateLaunchVerticesWithArbitrageSavingParam(
            Connectivity connectivity,
            RequestFields requestFields
    ) {
        List<Pair<Integer, Boolean>> launchVertices = new ArrayList<>();

        launchVertices.addAll(generateStartVertices(requestFields));
        launchVertices.addAll(generateRandomVertices(connectivity, requestFields));

        return launchVertices;
    }

    private List<Pair<Integer, Boolean>> generateStartVertices(RequestFields requestFields) {
        return requestFields.getStartVertices().stream()
                .map(vertex -> new Pair<>(vertex, true))
                .toList();
    }

    // TODO Per connectivity component
    private List<Pair<Integer, Boolean>> generateRandomVertices(
            Connectivity connectivity,
            RequestFields requestFields
    ) {
        List<Pair<Integer, Boolean>> startVertices = new ArrayList<>();

        int minVertexIdx = 0;
        int maxVertexIdx = connectivity.getSize();
        int numberOfRandomLaunches = requestFields.getNumberOfRandomLaunches();
        int componentMinSize = requestFields.getComponentMinSize();

        while (startVertices.size() < numberOfRandomLaunches) {
            int startVertex = random.nextInt(minVertexIdx, maxVertexIdx);
            int component = connectivity.getComponentIdByVertex(startVertex);

            if (connectivity.getComponentSizeById(component) >= componentMinSize)
                startVertices.add(new Pair<>(startVertex, false));
        }

        return startVertices;
    }

    @Getter
    @Builder
    @ToString
    public static class RequestFields {
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

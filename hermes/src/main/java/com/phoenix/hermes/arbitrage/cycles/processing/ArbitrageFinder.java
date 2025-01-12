package com.phoenix.hermes.arbitrage.cycles.processing;

import com.phoenix.common.utils.Pair;
import com.phoenix.hermes.arbitrage.cycles.impl.ProfitableCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class ArbitrageFinder {

    public static Pair<Arbitrage, Set<Cycle>> findCycles(
            WeightedDigraph graph,
            int startVertex
    ) {
        Set<Cycle> foundCycles = new HashSet<>();

        double additionalCommission = 1;
        Arbitrage arbitrage = new Arbitrage(graph, startVertex, additionalCommission);

        while (arbitrage.hasCycle()) {
            List<DirectedEdge> cycleEdges = arbitrage.getCycle();
            Cycle foundCycle = new ProfitableCycle(cycleEdges);

            CycleSplitter splitter = new CycleSplitter(foundCycle);
            for (Cycle splittedCycle : splitter.split())
                foundCycles.add(splittedCycle);

            additionalCommission = nextAdditionalCommission(foundCycle, additionalCommission);
            arbitrage = new Arbitrage(graph, startVertex, additionalCommission);
        }

        return new Pair<>(arbitrage, foundCycles);
    }

    private static double nextAdditionalCommission(Cycle cycle, double currentAdditionalCommission) {
        double power = 1. / cycle.getSize();
        double radical = 1 / (1 + cycle.getProfitAsPercent() / 100);
        return currentAdditionalCommission * Math.pow(radical, power);
    }
}

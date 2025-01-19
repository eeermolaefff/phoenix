package com.phoenix.hermes.v1.cycles.processing;

import com.phoenix.common.utils.Pair;
import com.phoenix.hermes.common.cycles.impl.ProfitableCycle;
import com.phoenix.hermes.common.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.common.graph.impl.interfaces.WeightedDigraph;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class ArbitrageFinder {

    public static Pair<Arbitrageur, Set<Cycle>> findCycles(
            WeightedDigraph graph,
            int startVertex
    ) {
        Set<Cycle> foundCycles = new HashSet<>();

        double additionalCommission = 1;
        Arbitrageur arbitrage = new Arbitrageur(graph, startVertex, additionalCommission);

        while (arbitrage.hasCycle()) {
            List<DirectedEdge> cycleEdges = arbitrage.getCycle();
            Cycle foundCycle = new ProfitableCycle(cycleEdges);

            CycleSplitter splitter = new CycleSplitter(foundCycle);
            for (Cycle splittedCycle : splitter.split())
                foundCycles.add(splittedCycle);

            additionalCommission = nextAdditionalCommission(foundCycle, additionalCommission);
            arbitrage = new Arbitrageur(graph, startVertex, additionalCommission);
        }

        return new Pair<>(arbitrage, foundCycles);
    }

    private static double nextAdditionalCommission(Cycle cycle, double currentAdditionalCommission) {
        double power = 1. / cycle.getSize();
        double radical = 1 / (1 + cycle.getProfitAsPercent() / 100);
        return currentAdditionalCommission * Math.pow(radical, power);
    }
}

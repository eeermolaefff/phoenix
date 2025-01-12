package com.phoenix.hermes.arbitrage.cycles.processing.v2;

import com.phoenix.common.utils.Pair;
import com.phoenix.hermes.arbitrage.cycles.impl.ProfitableCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.cycles.processing.Arbitrage;
import com.phoenix.hermes.arbitrage.cycles.processing.CycleSplitter;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class ArbitrageFinderNew {

    public static Set<Cycle> findCycles(
            WeightedDigraph graph,
            int startVertex
    ) {
        ArbitrageNew arbitrage = new ArbitrageNew(graph, startVertex);
        return arbitrage.getFoundCycles();
    }
}

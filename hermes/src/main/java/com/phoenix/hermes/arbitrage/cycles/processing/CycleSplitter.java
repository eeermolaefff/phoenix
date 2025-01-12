package com.phoenix.hermes.arbitrage.cycles.processing;

import com.phoenix.hermes.arbitrage.cycles.impl.ProfitableCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.arbitrage.graph.impl.ArrayBasedDigraph;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CycleSplitter {

    private final Map<Integer, Integer> oldToNew = new TreeMap<>();
    private final int[] newToOld;
    private final List<DirectedEdge>[] backwardEdges;
    private final boolean[] wasVisited;
    private final WeightedDigraph graph;
    private final DirectedEdge[] forwardEdges;

    public CycleSplitter(Cycle cycle) {
        List<DirectedEdge> edges = cycle.getCycleEdges();
        if (edges.isEmpty())
            throw new IllegalArgumentException("Cycle should not be empty");

        for (DirectedEdge edge : edges) {
            int vertexOld = edge.getVertexFrom();
            int vertexNew = oldToNew.size();
            oldToNew.putIfAbsent(vertexOld, vertexNew);
        }

        forwardEdges = new DirectedEdge[oldToNew.size()];
        wasVisited = new boolean[oldToNew.size()];
        backwardEdges = new List[oldToNew.size()];

        newToOld = new int[oldToNew.size()];
        for (var entry : oldToNew.entrySet())
            newToOld[entry.getValue()] = entry.getKey();

        graph = buildGraph(edges);
    }

    public Iterable<Cycle> split() {
        dfs(0);

        List<Cycle> cycles = new ArrayList<>();
        for (List<DirectedEdge> edges : backwardEdges) {
            if (edges == null)
                continue;
            for (DirectedEdge backwardEdge : edges)
                cycles.add(buildCycle(backwardEdge));
        }
        return cycles;
    }

    private Cycle buildCycle(DirectedEdge backwardEdge) {
        List<DirectedEdge> edges = new ArrayList<>();

        int backwardFrom = backwardEdge.getVertexFrom();
        int backwardTo = backwardEdge.getVertexTo();
        edges.add(backwardEdge.copy(newToOld[backwardFrom], newToOld[backwardTo]));

        for (int from = backwardTo, to; from != backwardFrom; from = to) {
            DirectedEdge next = forwardEdges[from];
            to = next.getVertexTo();
            edges.add(next.copy(newToOld[from], newToOld[to]));
        }

        return new ProfitableCycle(edges);
    }

    private WeightedDigraph buildGraph(Iterable<DirectedEdge> cycle) {
        WeightedDigraph digraph = new ArrayBasedDigraph(oldToNew.size());
        for (DirectedEdge oldEdge : cycle) {
            int from = oldToNew.get(oldEdge.getVertexFrom());
            int to = oldToNew.get(oldEdge.getVertexTo());
            digraph.add(oldEdge.copy(from, to));
        }
        return digraph;
    }

    private void dfs(int vertexFrom) {
        wasVisited[vertexFrom] = true;

        for (DirectedEdge edge : graph.getAdjacentEdges(vertexFrom)) {
            int vertexTo = edge.getVertexTo();

            if (wasVisited[vertexTo]) {
                if (backwardEdges[vertexFrom] == null)
                    backwardEdges[vertexFrom] = new ArrayList<>();
                backwardEdges[vertexFrom].add(edge);
            } else {
                forwardEdges[vertexFrom] = edge;
                dfs(vertexTo);
            }
        }
    }
}

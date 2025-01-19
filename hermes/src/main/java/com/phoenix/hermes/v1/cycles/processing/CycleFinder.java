package com.phoenix.hermes.v1.cycles.processing;

import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.common.graph.impl.interfaces.WeightedDigraph;

import java.util.LinkedList;
import java.util.List;

public class CycleFinder {

    private final boolean[] marked;
    private final boolean[] onStack;
    private final DirectedEdge[] edgeTo;

    private LinkedList<DirectedEdge> cyclePath;

    public CycleFinder(WeightedDigraph digraph, int vertexFrom) {
        marked = new boolean[digraph.getNumberOfVertices()];
        onStack = new boolean[digraph.getNumberOfVertices()];
        edgeTo = new DirectedEdge[digraph.getNumberOfVertices()];

        dfs(digraph, vertexFrom);
        for (int vertex = 0; vertex < digraph.getNumberOfVertices(); vertex++)
            if (!marked[vertex])
                dfs(digraph, vertex);
    }

    private void dfs(WeightedDigraph digraph, int from) {
        marked[from] = true;
        onStack[from] = true;
        for (DirectedEdge edge : digraph.getAdjacentEdges(from)) {
            int to = edge.getVertexTo();
            if (hasCycle())
                return;
            else if (!marked[to]) {
                edgeTo[to] = edge;
                dfs(digraph, to);
            }
            else if (onStack[to]) {
                cyclePath = new LinkedList<>();
                edgeTo[to] = null;
                cyclePath.push(edge);
                for (DirectedEdge x = edgeTo[from]; x != null; x = edgeTo[x.getVertexFrom()])
                    cyclePath.push(x);
                return;
            }
        }
        onStack[from] = false;
    }

    public boolean hasCycle() {
        return cyclePath != null;
    }

    public List<DirectedEdge> getCycle() {
        return cyclePath;
    }
}

package com.phoenix.hermes.arbitrage.graph.processing;

import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.arbitrage.graph.impl.interfaces.WeightedDigraph;

import java.util.ArrayList;
import java.util.List;

public class Connectivity {

    private final boolean[] marked;
    private final int[] componentIds;
    private int numberOfComponents;

    public Connectivity(WeightedDigraph digraph) {
        marked = new boolean[digraph.getNumberOfVertices()];
        componentIds = new int[digraph.getNumberOfVertices()];
        DeepFirstOrder dfs = new DeepFirstOrder(digraph.reversed());
        for (int from :  dfs.reversePost())
            if (!marked[from]) {
                dfs(digraph, from);
                numberOfComponents++;
            }
    }

    public int getSize() {
        return marked.length;
    }

    public boolean isStronglyConnected(int vertex1, int vertex2) {
        return componentIds[vertex1] == componentIds[vertex2];
    }

    public int getComponentIdByVertex(int vertex) {
        return componentIds[vertex];
    }

    public int getNumberOfComponents() {
        return numberOfComponents;
    }

    public Iterable<Integer> getComponentByVertex(int vertex) {
        int componentId = componentIds[vertex];
        return getComponentById(componentId);
    }

    public int getComponentSizeById(int componentId) {
        int size = 0;
        for (int id : this.componentIds)
            if (id == componentId)
                size++;
        return size;
    }

    public Iterable<Integer> getComponentById(int componentId) {
        List<Integer> component = new ArrayList<>();
        for (int v = 0; v < componentIds.length; v++)
            if (componentIds[v] == componentId)
                component.add(v);
        return component;
    }

    public List<Integer>[] getAllComponents() {
        List<Integer>[] components = new ArrayList[numberOfComponents];
        for (int i = 0; i < numberOfComponents; i++)
            components[i] = new ArrayList<>();

        for (int v = 0; v < componentIds.length; v++)
            components[componentIds[v]].add(v);

        return components;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<Integer> component : getAllComponents()) {
            builder.append("[");
            for (int vertex : component)
                builder.append(vertex).append(" ");
            builder.deleteCharAt(builder.length() - 1).append("]\n");
        }
        return builder.toString();
    }

    private void dfs(WeightedDigraph digraph, int vertexFrom) {
        componentIds[vertexFrom] = numberOfComponents;
        marked[vertexFrom] = true;
        for (DirectedEdge edge : digraph.getAdjacentEdges(vertexFrom)) {
            int vertexTo = edge.getVertexTo();
            if (!marked[vertexTo])
                dfs(digraph, vertexTo);
        }
    }
}

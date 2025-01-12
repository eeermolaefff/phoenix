package com.phoenix.hermes.arbitrage.cycles.impl;

import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.ConnectedCycle;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;

import java.util.*;

public class MultiConnectedCycle extends ProfitableCycle implements ConnectedCycle {

    private final Map<Integer, List<DirectedEdge>> connections = new TreeMap<>();

    public MultiConnectedCycle(List<DirectedEdge> cycle) {
        super(cycle);
    }

    public void connect(int vertexFrom, List<DirectedEdge> path) {
        if (path == null || path.isEmpty())
            throw new IllegalArgumentException("Path should not be null and empty");
        connections.put(vertexFrom, path);
    }

    public int getNumberOfConnections() {
        return connections.size();
    }

    public Set<Integer> getConnectedVertices() {
        return connections.keySet();
    }

    public List<DirectedEdge> getConnection(int vertexFrom) {
        return connections.get(vertexFrom);
    }

    public List<List<DirectedEdge>> getAllConnections() {
        return new ArrayList<>(connections.values());
    }

    @Override
    protected Map<String, Object> buildCycleAsJson() {
        Map<String, Object> cycleAsJson = super.buildCycleAsJson();
        cycleAsJson.put("connections", buildConnectionsStringList());
        return cycleAsJson;
    }

    private List<Object> buildConnectionsStringList() {
        List<Object> connectionsList = new ArrayList<>();
        for (List<DirectedEdge> connection : connections.values()) {
            List<String> edgesStringList = buildEdgesStringList(connection);
            connectionsList.add(edgesStringList);
        }
        return connectionsList;
    }
}

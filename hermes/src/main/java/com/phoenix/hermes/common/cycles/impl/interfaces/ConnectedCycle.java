package com.phoenix.hermes.common.cycles.impl.interfaces;

import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;

import java.util.*;

public interface ConnectedCycle extends Cycle {

    void connect(int vertexFrom, List<DirectedEdge> path);
    int getNumberOfConnections();
    Set<Integer> getConnectedVertices();
    List<DirectedEdge> getConnection(int vertexFrom);
    List<List<DirectedEdge>> getAllConnections();
}

package com.phoenix.hermes.common.cycles.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.hermes.common.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;

import java.util.*;

public abstract class BaseCycle implements Cycle {

    protected final List<DirectedEdge> edges;
    protected String stringRepresentation;

    protected BaseCycle(List<DirectedEdge> edges) {
        if (edges == null || edges.isEmpty())
            throw new IllegalArgumentException("Edges list should not be null and empty");
        this.edges = edges;
    }

    public int getSize() {
        return edges.size();
    }

    public List<DirectedEdge> getCycleEdges() {
        return getCycleEdges(0);
    }

    public List<DirectedEdge> getCycleEdges(int firsElementPosition) {
        if (firsElementPosition == 0)
            return edges;

        List<DirectedEdge> scrolled = new ArrayList<>();
        scrolled.addAll(edges.subList(firsElementPosition, edges.size()));
        scrolled.addAll(edges.subList(0, firsElementPosition));
        return scrolled;
    }

    public int getPositionOf(int vertex) {
        int pos = 0;
        for (DirectedEdge edge : edges) {
            if (edge.getVertexFrom() == vertex)
                return pos;
            pos++;
        }
        return -1;
    }

    @Override
    public String toString() {
        if (stringRepresentation == null)
            stringRepresentation = toJSONString(buildCycleAsJson());
        return stringRepresentation;
    }

    protected String toJSONString(Map<String, Object> json) {
        try {
            return new ObjectMapper().writeValueAsString(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not cast map to JSON string: [%s]".formatted(json), e);
        }
    }

    protected Map<String, Object> buildCycleAsJson() {
        Map<String, Object> cycleAsJson = new TreeMap<>();

        cycleAsJson.put("className", this.getClass().getSimpleName());
        cycleAsJson.put("size", edges.size());
        cycleAsJson.put("cycle", buildEdgesStringList(edges));

        return cycleAsJson;
    }

    protected List<String> buildEdgesStringList(Iterable<DirectedEdge> edges) {
        List<String> jsonEdgesList = new ArrayList<>();
        for (DirectedEdge edge : edges)
            jsonEdgesList.add(edge.toString());
        return jsonEdgesList;
    }
}

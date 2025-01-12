package com.phoenix.hermes.arbitrage.cycles.impl;

import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class HashableCycle extends BaseCycle {

    protected final List<DirectedEdge> sortedEdges = new ArrayList<>();
    protected Integer cycleHash;

    protected HashableCycle(List<DirectedEdge> cycle) {
        super(cycle);
        sortedEdges.addAll(cycle);
        Collections.sort(sortedEdges);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HashableCycle another))  //TODO Проверить что все наследники тут не ломают
            return false;
        if (this.hashCode() != another.hashCode())
            return false;
        return deepEquals(another);
    }

    @Override
    public int hashCode() {
        if (cycleHash == null)
            cycleHash = calculateHash();
        return cycleHash;
    }

    protected int calculateHash() {
        AtomicInteger hash = new AtomicInteger();
        edges.forEach(edge -> hash.addAndGet(edge.hashCode()));
        return hash.get();
    }

    @Override
    protected Map<String, Object> buildCycleAsJson() {
        Map<String, Object> cycleAsJson = super.buildCycleAsJson();
        cycleAsJson.put("sortedEdges", buildEdgesStringList(sortedEdges));
        return cycleAsJson;
    }

    protected boolean deepEquals(HashableCycle another) {
        if (this.sortedEdges.size() != another.sortedEdges.size())
            return false;

        for (int i = 0; i < this.sortedEdges.size(); i++) {
            DirectedEdge thisEdge = this.sortedEdges.get(i);
            DirectedEdge anotherEdge = another.sortedEdges.get(i);
            if (!thisEdge.equals(anotherEdge))
                return false;
        }

        return true;
    }
}

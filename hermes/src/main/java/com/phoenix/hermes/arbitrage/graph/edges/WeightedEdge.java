package com.phoenix.hermes.arbitrage.graph.edges;

import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import lombok.EqualsAndHashCode;

import java.util.Comparator;

@EqualsAndHashCode
public class WeightedEdge implements DirectedEdge {

    private final int vertexFrom;
    private final int vertexTo;
    private final double weight;

    public WeightedEdge(
            int vertexFrom,
            int vertexTo,
            double weight
    ) {
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
        this.weight = weight;
    }

    @Override
    public int compareTo(DirectedEdge edge) {
        return Comparator.comparing(DirectedEdge::getVertexFrom)
                .thenComparing(DirectedEdge::getVertexTo)
                .thenComparing(DirectedEdge::getWeight)
                .compare(this, edge);
    }

    @Override
    public double goThrough(double startCurrencyAmount) {
        return startCurrencyAmount * weight;
    }

    @Override
    public String toString() {
        return "%d -> %d (%f)".formatted(vertexFrom, vertexTo, weight);
    }

    @Override
    public String toString(double startCurrencyAmount) {
        return "%f %d -> %f %d (%f)".formatted(
                startCurrencyAmount, vertexFrom, goThrough(startCurrencyAmount), vertexTo, weight);
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public int getVertexTo() {
        return vertexTo;
    }

    @Override
    public int getVertexFrom() {
        return vertexFrom;
    }

    @Override
    public DirectedEdge reversed() {
        return new WeightedEdge(vertexTo, vertexFrom, 1 / weight);
    }

    @Override
    public DirectedEdge copy(int vertexFrom, int vertexTo) {
        return new WeightedEdge(vertexFrom, vertexTo, weight);
    }
}

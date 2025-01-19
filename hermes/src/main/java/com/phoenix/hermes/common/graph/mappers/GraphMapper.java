package com.phoenix.hermes.common.graph.mappers;

import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import com.phoenix.hermes.common.graph.edges.WeightedEdge;
import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.common.graph.impl.ArrayBasedDigraph;
import com.phoenix.hermes.common.graph.impl.interfaces.WeightedDigraph;
import lombok.experimental.UtilityClass;

import java.util.stream.StreamSupport;

@UtilityClass
public class GraphMapper {

    public static WeightedDigraph mapGraph(HermesOuterClass.Graph graph) {
        int numberOfVertices = graph.getNumberOfVertices();
        WeightedDigraph digraph = new ArrayBasedDigraph(numberOfVertices);

        for (HermesOuterClass.Edge edge : graph.getEdgesList()) {
            DirectedEdge directedEdge = mapEdge(edge);
            digraph.add(directedEdge);
        }

        return digraph;
    }

    public static Iterable<HermesOuterClass.Edge> mapEdges(Iterable<DirectedEdge> edges) {
        return  StreamSupport.stream(edges.spliterator(), false)
                .map(GraphMapper::mapEdge)
                .toList();
    }

    public static DirectedEdge mapEdge(HermesOuterClass.Edge edge) {
        return new WeightedEdge(
                edge.getVertexFrom(),
                edge.getVertexTo(),
                edge.getMultiplier()
        );
    }

    public static HermesOuterClass.Edge mapEdge(DirectedEdge edge) {
        return HermesOuterClass.Edge.newBuilder()
                .setVertexFrom(edge.getVertexFrom())
                .setVertexTo(edge.getVertexTo())
                .setMultiplier((float) edge.getWeight())
                .build();
    }
}

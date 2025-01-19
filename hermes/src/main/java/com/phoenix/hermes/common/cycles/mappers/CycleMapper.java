package com.phoenix.hermes.common.cycles.mappers;

import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import com.phoenix.hermes.common.cycles.impl.interfaces.ConnectedCycle;
import com.phoenix.hermes.common.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.common.graph.mappers.GraphMapper;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.StreamSupport;

@UtilityClass
public class CycleMapper {

    public static List<HermesOuterClass.Cycle> mapCycles(Iterable<Cycle> cycles) {
        return StreamSupport.stream(cycles.spliterator(), false)
                .map(CycleMapper::mapCycle)
                .toList();
    }

    public static HermesOuterClass.Cycle mapCycle(Cycle cycle) {
        var builder = HermesOuterClass.Cycle.newBuilder()
                .setCycleSize(cycle.getSize())
                .setProfitAsPercent((float) cycle.getProfitAsPercent())
                .addAllEdges(GraphMapper.mapEdges(cycle.getCycleEdges()));

        if (cycle instanceof ConnectedCycle connectedCycle)
            builder.addAllConnections(mapConnections(connectedCycle.getAllConnections()));

        return builder.build();
    }

    private static List<HermesOuterClass.Connection> mapConnections(Iterable<List<DirectedEdge>> connections) {
        return StreamSupport.stream(connections.spliterator(), false)
                .map(CycleMapper::mapConnection)
                .toList();
    }

    private static HermesOuterClass.Connection mapConnection(List<DirectedEdge> connection) {
        return HermesOuterClass.Connection.newBuilder()
                .addAllEdges(GraphMapper.mapEdges(connection))
                .setStartVertex(connection.get(0).getVertexFrom())
                .build();
    }
}

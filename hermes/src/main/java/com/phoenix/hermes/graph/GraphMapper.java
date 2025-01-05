package com.phoenix.hermes.graph;

import com.phoenix.common.grpc.hermes.generated.HermesOuterClass;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GraphMapper {

    public static Graph map(HermesOuterClass.Graph graph) {
//        throw new UnsupportedOperationException();
        return null;
    }

    public static HermesOuterClass.Cycles map(Iterable<Cycle> cycles) {
        throw new UnsupportedOperationException();
    }
}

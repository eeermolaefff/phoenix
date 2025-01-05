package com.phoenix.hermes.service;

import com.phoenix.common.logging.aspects.Loggable;
import com.phoenix.hermes.graph.Cycle;
import com.phoenix.hermes.graph.Graph;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HermesService {

    @Loggable
    public List<Cycle> findAllCycles(Graph graph) {
        throw new UnsupportedOperationException();
    }
}

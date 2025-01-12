package com.phoenix.hermes.service;

import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.cycles.processing.v2.ArbitrageFinderNew;
import com.phoenix.hermes.arbitrage.graph.processing.Connectivity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HermesServiceNew implements HermesService {

    @Override
    public Set<Cycle> findAllCycles(RequestFields requestFields) {
        Connectivity connectivity = new Connectivity(requestFields.getDigraph());
        log.info("Graph connectivity:\n {}", connectivity);
        validate(requestFields, connectivity);
        return ArbitrageFinderNew.findCycles(requestFields.getDigraph(), requestFields.getStartVertices().get(0));
    }

    private void validate(RequestFields requestFields, Connectivity connectivity) {
       int maxComponentSize = Arrays.stream(connectivity.getAllComponents())
               .map(List::size)
               .max(Integer::compareTo)
               .orElse(0);

       if (maxComponentSize < requestFields.getComponentMinSize())
           throw new IllegalArgumentException("The graph has no components of connectivity with required min size");
    }
}

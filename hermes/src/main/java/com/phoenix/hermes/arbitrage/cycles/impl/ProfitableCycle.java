package com.phoenix.hermes.arbitrage.cycles.impl;

import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import lombok.Getter;

import java.util.*;

@Getter
public class ProfitableCycle extends HashableCycle {

    protected static final double START_CURRENCY_AMOUNT = 1;

    protected final double profitAsPercent;

    public ProfitableCycle(List<DirectedEdge> cycle) {
        super(cycle);
        profitAsPercent = calculateProfitAsPercent(cycle);
    }

    public static double calculateProfitAsPercent(List<DirectedEdge> cycle) {
        double currentCurrencyAmount = START_CURRENCY_AMOUNT;
        for (DirectedEdge edge : cycle)
            currentCurrencyAmount = edge.goThrough(currentCurrencyAmount);
        return (currentCurrencyAmount - START_CURRENCY_AMOUNT) * 100 / START_CURRENCY_AMOUNT;
    }

    @Override
    protected Map<String, Object> buildCycleAsJson() {
        Map<String, Object> cycleAsJson = super.buildCycleAsJson();
        cycleAsJson.put("profitAsPercent", profitAsPercent);
        return cycleAsJson;
    }

    @Override
    protected List<String> buildEdgesStringList(Iterable<DirectedEdge> edges) {
        double currencyAmount = START_CURRENCY_AMOUNT;
        List<String> jsonEdgesList = new ArrayList<>();
        for (DirectedEdge edge : edges) {
            jsonEdgesList.add(edge.toString(currencyAmount));
            currencyAmount = edge.goThrough(currencyAmount);
        }
        return jsonEdgesList;
    }

    @Override
    public int compareTo(Cycle o) {
        return 0;
    }
}

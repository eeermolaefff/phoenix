package com.phoenix.hermes.common.graph.processing;

import com.phoenix.hermes.common.graph.edges.interfaces.DirectedEdge;
import com.phoenix.hermes.common.graph.impl.interfaces.WeightedDigraph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class DeepFirstOrder {

    private final boolean[] marked;
    private final Deque<Integer> reversePost;
    private final Queue<Integer> post;
    private final Queue<Integer> pre;

    public DeepFirstOrder(WeightedDigraph digraph) {
        pre = new LinkedList<>();
        post = new LinkedList<>();
        reversePost = new LinkedList<>();
        marked = new boolean[digraph.getNumberOfVertices()];
        for (int from = 0; from < digraph.getNumberOfVertices(); from++)
            if (!marked[from])
                dfs(digraph, from);
    }

    private void dfs(WeightedDigraph digraph, int from) {
        marked[from] = true;
        pre.add(from);
        for (DirectedEdge e : digraph.getAdjacentEdges(from)) {
            int to = e.getVertexTo();
            if (!marked[to])
                dfs(digraph, to);
        }
        post.add(from);
        reversePost.push(from);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}

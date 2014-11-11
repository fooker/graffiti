package sh.lab.graffiti.dsl.contexts;

import com.google.common.collect.Maps;
import groovy.lang.Closure;
import sh.lab.graffiti.model.Graph;

import java.util.Map;

public class RootContext {

    private final Map<String, Graph> graphs = Maps.newHashMap();

    public RootContext() {
    }

    public Graph graph(final Map<String, Object> args,
                       final Closure<Void> body) {
        final GraphContext context = new GraphContext();
        context.apply(args, body);

        final Graph graph = context.build();
        this.graphs.put(graph.getId(), graph);

        return graph;
    }

    public Graph graph(final Closure<Void> body) {
        return this.graph(null, body);
    }

    public Map<String, Graph> getGraphs() {
        return this.graphs;
    }
}

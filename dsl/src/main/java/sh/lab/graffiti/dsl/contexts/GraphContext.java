package sh.lab.graffiti.dsl.contexts;

import groovy.lang.Closure;
import sh.lab.graffiti.model.Graph;
import sh.lab.graffiti.model.Group;
import sh.lab.graffiti.model.Source;

import java.util.Map;

public class GraphContext extends NodeContext<Graph, Graph.Builder> {

    public GraphContext() {
        super(Graph.builder());
    }

    public Source source(final Map<String, Object> args) {
        final SourceContext context = new SourceContext();
        context.apply(args);

        final Source source = context.build();
        this.getBuilder().source(source);

        return source;
    }

    public Group group(final Map<String, Object> args,
                              final Closure<Void> body) {
        final GroupContext context = new GroupContext();
        context.apply(args, body);

        final Group group = context.build();
        this.getBuilder().group(group);

        return group;
    }

    public Group group(final Closure<Void> body) {
        return this.group(null, body);
    }
}

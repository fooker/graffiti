package sh.lab.graffiti.dsl.contexts;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import org.codehaus.groovy.runtime.InvokerHelper;
import sh.lab.graffiti.model.Node;

import java.util.Map;

public abstract class NodeContext<N extends Node, B extends Node.Builder<N>> extends GroovyObjectSupport {

    private final B builder;
    
    protected NodeContext(final B builder) {
        this.builder = builder;
    }

    protected B getBuilder() {
        return this.builder;
    }

    protected N build() {
        return this.builder.build();
    }
    
    @Override
    public void setProperty(final String name,
                            final Object value) {
        InvokerHelper.invokeMethod(this.builder, name, value);
    }

    protected void apply(final Closure<Void> body) {
        if (body == null) return;

        body.setResolveStrategy(Closure.DELEGATE_FIRST);
        body.setDelegate(this);
        body.call();
    }

    public void apply(final Map<String, Object> args) {
        if (args == null) return;

        for (final Map.Entry<String, Object> arg : args.entrySet()) {
            this.setProperty(arg.getKey(),
                             arg.getValue());
        }
    }

    public void apply(final Map<String, Object> args,
                      final Closure<Void> body) {
        this.apply(args);
        this.apply(body);
    }
}

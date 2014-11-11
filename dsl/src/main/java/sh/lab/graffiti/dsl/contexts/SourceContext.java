package sh.lab.graffiti.dsl.contexts;

import sh.lab.graffiti.model.Source;

public class SourceContext extends NodeContext<Source, Source.Builder> {

    public SourceContext() {
        super(Source.builder());
    }
}

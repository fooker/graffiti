package sh.lab.graffiti.generators.rrd;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import sh.lab.graffiti.generators.Generator;
import sh.lab.graffiti.model.Graph;

import java.util.stream.Collectors;

public class RrdGraphCommandGenerator extends Generator<String> {

    private final Escaper SHELL_ESCAPER = Escapers.builder()
                                                  .addEscape('\"',
                                                             "\\\"")
                                                  .build();

    public RrdGraphCommandGenerator() {
    }

    @Override
    public String generate(final Graph graph) throws Exception {
        final RrdGraphVisitor visitor = new RrdGraphVisitor(this.getConfig());
        graph.accept(visitor);

        return visitor.getTokens()
                      .stream()
                      .map(SHELL_ESCAPER::escape)
                      .map((s) -> String.format("\"%s\"", s))
                      .collect(Collectors.joining(" "));
    }
}

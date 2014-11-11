package sh.lab.graffiti.stylewars;

import com.google.common.collect.Maps;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.MapOptionHandler;
import sh.lab.graffiti.dsl.Loader;
import sh.lab.graffiti.generators.Config;
import sh.lab.graffiti.generators.rrd.RrdGraphCommandGenerator;
import sh.lab.graffiti.model.Graph;
import sh.lab.graffiti.model.Source;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static enum Type {
        RRD_GRAPH_COMMAND
    }

    @Option(name = "-c",
            handler = MapOptionHandler.class)
    private Map<String, String> config = Maps.newHashMap();

    @Argument(index = 0,
            metaVar = "file",
            required = true)
    private Path file;

    public static void main(final String... args) throws Exception {
        final Main main = new Main();

        final CmdLineParser parser = new CmdLineParser(main);
        parser.parseArgument(args);

        main.run();
    }

    private void applyToConfig(final Config config) throws Exception {
        InvokerHelper.setProperties(config, this.config);
    }

    private Collection<Graph> load() throws Exception {
        return Loader.load(this.file.toUri().toURL()).values();
    }

    private void run() throws Exception {
        final RrdGraphCommandGenerator generator = new RrdGraphCommandGenerator();
        this.applyToConfig(generator.getConfig());

        final Collection<Graph> graphs = this.load();

        System.out.format("reports=%s\n",
                          graphs.stream()
                                .map(Graph::getId)
                                .collect(Collectors.joining(", \\\n")));

        for (final Graph graph : this.load()) {
            System.out.format("report.%s.name=%s\n",
                              graph.getId(),
                              graph.getTitle());

            System.out.format("report.%s.columns=%s\n",
                              graph.getId(),
                              graph.getSources()
                                   .stream()
                                   .map(Source::getTrack)
                                   .collect(Collectors.joining(",")));

            System.out.format("report.%s.command=%s\n",
                              graph.getId(),
                              generator.generate(graph));
        }
    }
}

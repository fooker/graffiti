package sh.lab.graffiti.generators.rrd;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import sh.lab.graffiti.generators.Config;
import sh.lab.graffiti.model.*;
import sh.lab.graffiti.model.expression.Expression;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RrdGraphVisitor implements sh.lab.graffiti.model.GraphVisitor {
    private final static Map<Consolidation, String> CF = ImmutableMap.<Consolidation, String>builder()
                                                                     .put(Consolidation.AVERAGE, "AVERAGE")
                                                                     .put(Consolidation.MAXIMUM, "MAXIMUM")
                                                                     .put(Consolidation.MINIMUM, "MINIMUM")
                                                                     .build();

    private static enum Color {
        BUTTER(0xedd400, 0xc4a000, 0xfce94f),
        CHAMELEON(0x73d216, 0x4e9a06, 0x8ae234),
        ORANGE(0xf57900, 0xce5c00, 0xfcaf3e),
        CHOCOLATE(0xc17d11, 0x8f5902, 0xe9b96e),
        SKY(0x3465a4, 0x204a87, 0x729fcf),
        PLUM(0x75507b, 0x5c3566, 0xad7fa8),
        SCARLET(0xcc0000, 0xa40000, 0xef2929);

        public final int base;
        public final int shadow;
        public final int highlight;

        private Color(final int base, final int shadow, final int highlight) {
            this.base = base;
            this.shadow = shadow;
            this.highlight = highlight;
        }
    }

    private final Iterator<Color> color = Iterators.cycle(Color.values());

    private final Config config;

    private final List<String> tokens = Lists.newArrayList();

    private AtomicLong nameSeq = new AtomicLong(1);

    private final Map<Source, String> sourceNames = Maps.newHashMap();

    private class TokenBuilder {
        private final StringBuilder s = new StringBuilder();


        public TokenBuilder append(final String s) {
            this.s.append(s);
            return this;
        }

        public TokenBuilder append(final char c) {
            this.s.append(c);
            return this;
        }

        public void push() {
            RrdGraphVisitor.this.tokens.add(this.s.toString());
        }
    }

    public RrdGraphVisitor(final Config config) {
        this.config = config;
    }

    private String escape(final String s) {
        return s.replace(":", "\\:");
    }

    @Override
    public void visit(final Graph graph) {
        this.option("start", this.config.getStart());
        this.option("end", this.config.getUntil());
        this.option("step", this.config.getStep());

        this.option("title", graph.getTitle());

        this.flag("no-legend", !this.config.isRenderLegend());

        for (final Source source : graph.getSources()) {
            source.accept(this);
        }

        for (final Group group : graph.getGroups()) {
            group.accept(this);
        }
    }

    @Override
    public void visit(final Source source) {
        // Generate a name for the DEF
        final String name = this.name();

        // Remember the source name
        this.sourceNames.put(source, name);

        // Append a DEF-line
        this.token()
            .append("DEF")
            .append(':')
            .append(name)
            .append('=')
            .append(escape(source.getFile().toString()))
            .append(':')
            .append(source.getTrack())
            .append(':')
            .append(CF.get(source.getConsolidation()))
            .push();
    }

    @Override
    public void visit(final Group group) {
        for (final Track track : group.getTracks()) {
            track.accept(this);
        }
    }

    @Override
    public void visit(final Track track) {
        final String name = this.expression(track.getExpression(), track.isNegate());

        final Color color = this.color.next();

        this.token()
            .append("GRAD")
            .append(':')
            .append(name)
            .append('#')
            .append(String.format("%06x",
                                  color.base))
            .append('#')
            .append(String.format("%06x",
                                  color.highlight))
            .append(':')
            .append("0")
            .push();

        this.token()
            .append("LINE")
            .append("1")
            .append(':')
            .append(name)
            .append('#')
            .append(String.format("%06x",
                                  color.shadow))
            .append(':')
            .append(escape(track.getLabel()))
            .push();
    }

    private TokenBuilder token() {
        return new TokenBuilder();
    }

    private void option(final String name,
                        final Object value) {
        if (value != null) {
            this.token()
                .append(String.format("--%s=%s", name, value))
                .push();
        }
    }

    private void flag(final String name,
                      final boolean set) {
        if (set) {
            this.token()
                .append(String.format("--%s", name))
                .push();
        }
    }

    private String name() {
        return String.format("x%X", this.nameSeq.getAndIncrement());
    }

    private String expression(final Expression expression,
                              final boolean negate) {
        final String name = this.name();

        final RrdExpressionVisitor visitor = new RrdExpressionVisitor(this);
        expression.accept(visitor);

        final TokenBuilder t = this.token()
                                   .append("CDEF")
                                   .append(':')
                                   .append(name)
                                   .append('=')
                                   .append(visitor.toString());

        if (negate) {
            t.append(',')
             .append("-1")
             .append(',')
             .append('*');
        }

        t.push();

        return name;
    }

    public String resolve(final Source source) {
        return this.sourceNames.get(source);
    }

    public List<String> getTokens() {
        return this.tokens;
    }
}

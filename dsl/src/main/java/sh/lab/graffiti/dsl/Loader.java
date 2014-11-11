package sh.lab.graffiti.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import sh.lab.graffiti.dsl.contexts.RootContext;
import sh.lab.graffiti.model.Consolidation;
import sh.lab.graffiti.model.Graph;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;

public class Loader {

    private static final CompilerConfiguration CONFIG = new CompilerConfiguration() {{
        this.addCompilationCustomizers(new ImportCustomizer() {{
            this.addStaticStars(Consolidation.class.getName());
        }});
        this.setScriptBaseClass(DelegatingScript.class.getName());
    }};

    public static Map<String, Graph> load(final URL source) {

        try (final Reader reader = new InputStreamReader(source.openStream())) {
            final GroovyShell shell = new GroovyShell(new Binding(),
                                                      CONFIG);

            final DelegatingScript script = (DelegatingScript) shell.parse(reader,
                                                                           source.toString());

            final RootContext delegate = new RootContext();
            script.setDelegate(delegate);
            script.run();

            return delegate.getGraphs();

        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Loader() {
    }
}

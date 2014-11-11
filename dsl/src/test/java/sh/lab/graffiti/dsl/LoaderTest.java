package sh.lab.graffiti.dsl;

import org.junit.Test;
import sh.lab.graffiti.model.Graph;

import java.net.URL;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class LoaderTest {

    private final static String BASE_PATH = "sh/lab/graffiti/dsl/examples/";

    private Map<String, Graph> load(final String file) {
        final URL url = LoaderTest.class.getClassLoader().getResource(BASE_PATH + '/' + file + ".groovy");

        return Loader.load(url);
    }

    @Test
    public void test() {
        final Map<String, Graph> graphs = load("full");

        assertThat(graphs, is(notNullValue()));
        assertThat(graphs.values(), is(not(empty())));
    }
}

package sh.lab.graffiti.generators;

import sh.lab.graffiti.model.Graph;

public abstract class Generator<R> {

    private Config config = new Config();

    public abstract R generate(final Graph graph) throws Exception;

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(final Config config) {
        this.config = config;
    }
}

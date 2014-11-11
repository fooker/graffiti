package sh.lab.graffiti.model;

public interface Node {

    public static interface Builder<T extends Node> {
        public abstract T build();
    }

    void accept(final GraphVisitor visitor);
}

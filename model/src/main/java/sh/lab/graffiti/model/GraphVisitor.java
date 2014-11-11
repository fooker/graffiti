package sh.lab.graffiti.model;

public interface GraphVisitor {
    void visit(final Graph graph);
    void visit(final Source source);
    void visit(final Group group);
    void visit(final Track track);
}

package sh.lab.graffiti.model.expression;

public interface Expression {

    void accept(final ExpressionVisitor visitor);
}

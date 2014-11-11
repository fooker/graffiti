package sh.lab.graffiti.model.expression;

public interface ExpressionVisitor {
    void visit(final ConstantExpression constant);
    void visit(final SourceExpression source);
    void visit(final OperationExpression operation);
}

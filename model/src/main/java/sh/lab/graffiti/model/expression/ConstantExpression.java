package sh.lab.graffiti.model.expression;

public class ConstantExpression implements Expression {
    private final double value;

    public ConstantExpression(final double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return Double.toString(this.value);
    }
}

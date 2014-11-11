package sh.lab.graffiti.model.expression;

import com.google.common.base.Preconditions;

public class OperationExpression implements Expression {

    private final Operator operator;

    private final Expression[] arguments;

    public OperationExpression(final Operator operator,
                               final Expression... arguments) {
        Preconditions.checkArgument(arguments.length == operator.getParameterCount());

        this.operator = operator;
        this.arguments = arguments;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public Expression[] getArguments() {
        return this.arguments;
    }

    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append('(')
         .append(' ')
         .append(this.operator.name())
         .append(' ');

        for (final Expression argument : this.arguments) {
            s.append(argument.toString())
             .append(' ');
        }

        s.append(')');

        return s.toString();
    }
}

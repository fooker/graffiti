package sh.lab.graffiti.dsl.categories;

import sh.lab.graffiti.model.Source;
import sh.lab.graffiti.model.expression.*;

public class ExpressionCategory {
    public static Expression getExpression(final Object o) {
        if (o instanceof Expression) {
            return (Expression) o;
        } else if (o instanceof Number) {
            return new ConstantExpression(((Number) o).doubleValue());
        } else if (o instanceof Source) {
            return new SourceExpression((Source) o);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Expression plus(final Object o1, final Object o2) {
        return new OperationExpression(Operator.ADD,
                                       getExpression(o1),
                                       getExpression(o2));
    }

    public static Expression minus(final Object o1, final Object o2) {
        return new OperationExpression(Operator.SUBSTRACT,
                                       getExpression(o1),
                                       getExpression(o2));
    }

    public static Expression multiply(final Object o1, final Object o2) {
        return new OperationExpression(Operator.MULTIPLY,
                                       getExpression(o1),
                                       getExpression(o2));
    }

    public static Expression div(final Object o1, final Object o2) {
        return new OperationExpression(Operator.DIVIDE,
                                       getExpression(o1),
                                       getExpression(o2));
    }

    public static Expression power(final Object o1, final Object o2) {
        return new OperationExpression(Operator.POWER,
                                       getExpression(o1),
                                       getExpression(o2));
    }

    public static Expression negative(final Object o1) {
        return new OperationExpression(Operator.NEGATE,
                                       getExpression(o1));
    }
}

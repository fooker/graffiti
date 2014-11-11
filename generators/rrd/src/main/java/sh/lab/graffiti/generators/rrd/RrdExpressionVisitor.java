package sh.lab.graffiti.generators.rrd;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import sh.lab.graffiti.model.expression.*;

import java.util.List;

public class RrdExpressionVisitor implements ExpressionVisitor {

    private final static Joiner JOINER = Joiner.on(',')
                                               .useForNull("UNKN");

    private final RrdGraphVisitor graph;

    private List<String> elements = Lists.newArrayList();

    public RrdExpressionVisitor(final RrdGraphVisitor graph) {
        this.graph = graph;
    }

    @Override
    public void visit(final ConstantExpression constant) {
        this.push(Double.toString(constant.getValue()));
    }

    @Override
    public void visit(final SourceExpression source) {
        this.push(graph.resolve(source.getSource()));
    }

    @Override
    public void visit(final OperationExpression operation) {
        for (final Expression expression : operation.getArguments()) {
            expression.accept(this);
        }

        switch (operation.getOperator()) {
            case ADD:
                this.push("+");
                break;
            case SUBSTRACT:
                this.push("-");
                break;
            case MULTIPLY:
                this.push("*");
                break;
            case DIVIDE:
                this.push("/");
                break;
            case MODULO:
                this.push("%");
                break;
            case POWER:
                this.push("EXP");
                break;
            case NEGATE:
                this.push("-1", "*");
                break;
        }
    }

    private void push(final String... elements) {
        for (final String element : elements) {
            this.elements.add(element);
        }
    }

    @Override
    public String toString() {
        return JOINER.join(this.elements);
    }
}

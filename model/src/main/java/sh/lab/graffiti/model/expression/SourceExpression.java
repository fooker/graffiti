package sh.lab.graffiti.model.expression;

import sh.lab.graffiti.model.Source;

public class SourceExpression implements Expression {
    private final Source source;

    public SourceExpression(final Source source) {
        this.source = source;
    }

    public Source getSource() {
        return this.source;
    }

    public void accept(final ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return this.source.getFile() + ":" + this.source.getTrack();
    }
}

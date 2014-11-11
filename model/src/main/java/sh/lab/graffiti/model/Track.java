package sh.lab.graffiti.model;

import com.google.common.base.Preconditions;
import sh.lab.graffiti.model.expression.Expression;

public class Track implements Node {

    private final String label;

    private final Expression expression;

    private final boolean negate;

    public Track(final String label,
                 final Expression expression,
                 final boolean negate) {
        this.label = label;
        this.expression = expression;
        this.negate = negate;
    }

    public String getLabel() {
        return this.label;
    }

    public Expression getExpression() {
        return this.expression;
    }

    public boolean isNegate() {
        return this.negate;
    }

    @Override
    public void accept(final GraphVisitor visitor) {
        visitor.visit(this);
    }

    public static class Builder implements Node.Builder<Track> {

        private String label;

        private Expression expression;

        private boolean negate;

        private Builder() {
        }

        public Builder label(final String label) {
            this.label = label;
            return this;
        }

        public Builder value(final Expression expression) {
            this.expression = expression;
            return this;
        }

        public Builder negate(final boolean negate) {
            this.negate = negate;
            return this;
        }

        @Override
        public Track build() {
            Preconditions.checkNotNull(this.label, "label");
            Preconditions.checkNotNull(this.expression, "expression");

            return new Track(this.label,
                             this.expression,
                             this.negate);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

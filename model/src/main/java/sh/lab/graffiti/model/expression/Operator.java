package sh.lab.graffiti.model.expression;

public enum Operator {
    ADD(2),
    SUBSTRACT(2),
    MULTIPLY(2),
    DIVIDE(2),
    MODULO(2),
    POWER(2),
    NEGATE(1);

    private final int parameterCount;

    private Operator(final int parameterCount) {
        this.parameterCount = parameterCount;
    }

    public int getParameterCount() {
        return parameterCount;
    }
}

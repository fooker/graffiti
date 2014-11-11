package sh.lab.graffiti.generators;

public class Config {
    private String start = "NOW-1h";
    private String until = "NOW";

    private String step = "5";

    private boolean renderLegend = true;

    public String getStart() {
        return this.start;
    }

    public void setStart(final String start) {
        this.start = start;
    }

    public String getUntil() {
        return this.until;
    }

    public void setUntil(final String until) {
        this.until = until;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(final String step) {
        this.step = step;
    }

    public boolean isRenderLegend() {
        return this.renderLegend;
    }

    public void setRenderLegend(final boolean renderLegend) {
        this.renderLegend = renderLegend;
    }
}

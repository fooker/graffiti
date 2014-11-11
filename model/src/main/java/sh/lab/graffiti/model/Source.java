package sh.lab.graffiti.model;

import com.google.common.base.Preconditions;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Source implements Node {

    private final Path file;

    private final String track;

    private final Consolidation consolidation;

    public Source(final Path file,
                  final String track,
                  final Consolidation consolidation) {
        this.file = file;
        this.track = track;
        this.consolidation = consolidation;
    }

    public Path getFile() {
        return this.file;
    }

    public String getTrack() {
        return this.track;
    }

    public Consolidation getConsolidation() {
        return this.consolidation;
    }

    public static class Builder implements Node.Builder<Source> {

        private Path file;

        private String track;

        private Consolidation consolidation = Consolidation.AVERAGE;

        private Builder() {
        }

        public Builder file(final Path file) {
            this.file = file;
            return this;
        }

        public Builder file(final File file) {
            return this.file(file.toPath());
        }

        public Builder file(final String file) {
            return this.file(Paths.get(file));
        }

        public Builder track(final String track) {
            this.track = track;
            return this;
        }

        public Builder consolidation(final Consolidation consolidation) {
            this.consolidation = consolidation;
            return this;
        }

        @Override
        public Source build() {
            Preconditions.checkNotNull(this.file, "file");
            Preconditions.checkNotNull(this.track, "track");
            Preconditions.checkNotNull(this.consolidation, "consolidation");

            return new Source(this.file,
                              this.track,
                              this.consolidation);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void accept(final GraphVisitor visitor) {
        visitor.visit(this);
    }
}

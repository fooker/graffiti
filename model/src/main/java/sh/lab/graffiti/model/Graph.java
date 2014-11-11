package sh.lab.graffiti.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class Graph implements Node {

    private final String id;

    private final String title;

    private final List<Source> sources;

    private final List<Group> groups;

    public Graph(final String id,
                 final String title,
                 final List<Source> sources,
                 final List<Group> groups) {
        this.id = id;
        this.title = title;
        this.sources = sources;
        this.groups = groups;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public List<Source> getSources() {
        return this.sources;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public static class Builder implements Node.Builder<Graph> {

        private String id;

        private String title;

        private ImmutableList.Builder<Source> sources = ImmutableList.builder();

        private ImmutableList.Builder<Group> groups = ImmutableList.builder();

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder source(final Source source) {
            this.sources.add(source);
            return this;
        }

        public Builder source(final Source.Builder source) {
            return this.source(source.build());
        }

        public Builder group(final Group group) {
            this.groups.add(group);
            return this;
        }

        public Builder group(final Group.Builder group) {
            return this.group(group.build());
        }

        @Override
        public Graph build() {
            Preconditions.checkNotNull(this.id, "id");

            return new Graph(this.id,
                             this.title,
                             this.sources.build(),
                             this.groups.build());
        }
    }

    public static Builder builder() {
        return new Graph.Builder();
    }

    public void accept(final GraphVisitor visitor) {
        visitor.visit(this);
    }
}

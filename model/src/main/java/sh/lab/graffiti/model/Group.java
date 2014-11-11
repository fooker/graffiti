package sh.lab.graffiti.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class Group implements Node {

    private final boolean stacked;

    private final List<Track> tracks;

    public Group(final boolean stacked,
                 final List<Track> tracks) {
        this.stacked = stacked;
        this.tracks = tracks;
    }

    public boolean isStacked() {
        return this.stacked;
    }

    public List<Track> getTracks() {
        return this.tracks;
    }

    public static class Builder implements Node.Builder<Group> {

        private boolean stacked = false;

        private final ImmutableList.Builder<Track> tracks = ImmutableList.builder();

        private Builder() {
        }

        public Builder stacked(final boolean stacked) {
            this.stacked = stacked;
            return this;
        }

        public Builder track(final Track track) {
            this.tracks.add(track);
            return this;
        }


        public Builder track(final Track.Builder track) {
            return this.track(track.build());
        }

        @Override
        public Group build() {
            return new Group(this.stacked,
                             this.tracks.build());
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void accept(final GraphVisitor visitor) {
        visitor.visit(this);
    }
}

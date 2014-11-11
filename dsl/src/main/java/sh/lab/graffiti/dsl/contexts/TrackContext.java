package sh.lab.graffiti.dsl.contexts;

import sh.lab.graffiti.model.Track;

public class TrackContext extends NodeContext<Track, Track.Builder> {

    public TrackContext() {
        super(Track.builder());
    }
}

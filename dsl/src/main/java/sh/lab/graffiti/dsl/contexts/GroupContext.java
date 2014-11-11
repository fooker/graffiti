package sh.lab.graffiti.dsl.contexts;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.GroovyCategorySupport;
import sh.lab.graffiti.dsl.categories.ExpressionCategory;
import sh.lab.graffiti.model.Group;
import sh.lab.graffiti.model.Source;
import sh.lab.graffiti.model.Track;
import sh.lab.graffiti.model.expression.*;

import java.util.Map;

public class GroupContext extends NodeContext<Group, Group.Builder> {

    public GroupContext() {
        super(Group.builder());
    }

    public Track track(final Map<String, Object> args,
                       final Expression expression) {
        final TrackContext context = new TrackContext();
        context.apply(args, null);

        context.getBuilder().value(expression);

        final Track track = context.build();
        this.getBuilder().track(track);

        return track;
    }

    public Track track(final Map<String, Object> args,
                       final Source source) {
        return this.track(args, new SourceExpression(source));
    }

    public Track track(final Map<String, Object> args,
                       final Closure<Expression> expressionBody) {
        final Expression expression = GroovyCategorySupport.use(ExpressionCategory.class, expressionBody);

        return this.track(args, expression);
    }
}

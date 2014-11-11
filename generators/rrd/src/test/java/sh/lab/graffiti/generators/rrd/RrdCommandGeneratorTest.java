package sh.lab.graffiti.generators.rrd;

import org.junit.Test;
import sh.lab.graffiti.model.*;
import sh.lab.graffiti.model.expression.ConstantExpression;
import sh.lab.graffiti.model.expression.OperationExpression;
import sh.lab.graffiti.model.expression.Operator;
import sh.lab.graffiti.model.expression.SourceExpression;


public class RrdCommandGeneratorTest {

    @Test
    public void test() throws Exception {
        final RrdGraphCommandGenerator generator = new RrdGraphCommandGenerator();

        generator.getConfig().setRenderLegend(true);
        generator.getConfig().setStart("NOW-1h");
        generator.getConfig().setUntil("NOW");
        generator.getConfig().setStep("5min");

        final Source i = Source.builder()
                               .file("host.rrd")
                               .track("input")
                               .consolidation(Consolidation.AVERAGE)
                               .build();

        final Source o = Source.builder()
                               .file("host.rrd")
                               .track("output")
                               .consolidation(Consolidation.AVERAGE)
                               .build();

        final Graph graph = Graph
                .builder()
                .title("Test")
                .source(i)
                .source(o)
                .group(Group.builder()
                            .stacked(false)
                            .track(Track.builder()
                                        .label("Input")
                                        .value(new OperationExpression(Operator.MULTIPLY,
                                                                       new SourceExpression(i),
                                                                       new ConstantExpression(8)))
                                        .negate(false))
                            .track(Track.builder()
                                        .label("Output")
                                        .value(new OperationExpression(Operator.MULTIPLY,
                                                                       new SourceExpression(o),
                                                                       new ConstantExpression(8)))
                                        .negate(true)))
                .group(Group.builder()
                            .stacked(true)
                            .track(Track.builder()
                                        .label("Input")
                                        .value(new OperationExpression(Operator.MULTIPLY,
                                                                       new SourceExpression(i),
                                                                       new OperationExpression(
                                                                               Operator.DIVIDE,
                                                                               new ConstantExpression(1),
                                                                               new SourceExpression(o)))))
                            .track(Track.builder()
                                        .label("output")
                                        .value(new OperationExpression(Operator.MULTIPLY,
                                                                       new SourceExpression(o),
                                                                       new OperationExpression(
                                                                               Operator.DIVIDE,
                                                                               new ConstantExpression(1),
                                                                               new SourceExpression(i))))))
                .build();

        final String out = generator.generate(graph);

        System.out.println(out);

    }
}

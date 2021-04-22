import config.Config;
import config.ConfigDataBase;
import graph.GraphGenerator;
import graph.GraphSort;
import graph.SimpleGraphView;
import vertexes.Configurate;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import vertexes.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class main {
    public static void main(String[] args) {

        Config configurator = new ConfigDataBase();

        A vertexA = new A();
        B vertexB = new B();
        C vertexC = new C();
        D vertexD = new D();
        E vertexE = new E();
        F vertexF = new F();

        Map<Integer, Configurate> confClasses = new HashMap();
        confClasses.put(2, vertexA);
        confClasses.put(4, vertexB);
        confClasses.put(3, vertexC);
        confClasses.put(5, vertexD);
        confClasses.put(1, vertexE);
        confClasses.put(6, vertexF);

        GraphGenerator graphGeneratorA = new GraphGenerator(3, confClasses, "class vertexes.A");
        GraphGenerator graphGeneratorB = new GraphGenerator(0, confClasses, "class vertexes.B");
        GraphGenerator graphGeneratorC = new GraphGenerator(0, confClasses, "class vertexes.C");
        GraphGenerator graphGeneratorD = new GraphGenerator(1, confClasses, "class vertexes.D");
        GraphGenerator graphGeneratorE = new GraphGenerator(0, confClasses, "class vertexes.E");
        GraphGenerator graphGeneratorF = new GraphGenerator(1, confClasses, "class vertexes.F");

        vertexA.setDependency(graphGeneratorA.getDependecy());
        vertexB.setDependency(graphGeneratorB.getDependecy());
        vertexC.setDependency(graphGeneratorC.getDependecy());
        vertexD.setDependency(graphGeneratorD.getDependecy());
        vertexE.setDependency(graphGeneratorE.getDependecy());
        vertexF.setDependency(graphGeneratorF.getDependecy());


        for (Map.Entry<Integer, Configurate> conf : confClasses.entrySet()) {
            conf.getValue().updateConfiguration(configurator);
          //  System.out.println("class configuration = " + conf.getValue().getClass().toString());
        }

        GraphSort graphSort = new GraphSort(confClasses);
        Graph<String,String>g3 = graphSort.topologicalSort();

        SimpleGraphView sgv = new SimpleGraphView(confClasses);

        Layout<Integer, String> layout = new CircleLayout(g3);
        layout.setSize(new Dimension(800, 600));
        BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(800, 600));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(g3));
        JFrame frame = new JFrame("Simple Graph View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}





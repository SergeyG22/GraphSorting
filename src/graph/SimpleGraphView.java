package graph;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import vertexes.Configurate;

import java.util.Map;


public class SimpleGraphView {
    public Graph<String, String> g2 = new SparseMultigraph<String, String>();

    public SimpleGraphView(Map conf) {
        Integer indexEdgeName = 0;
        Map<Integer, Configurate> confClasses = conf;
        for (Map.Entry<Integer, Configurate> entry : confClasses.entrySet()) {
            String currentClassName = entry.getValue().getClass().toString();
            for (String dependencyClassName : entry.getValue().getDependency()) {
                g2.addEdge("Edge" + indexEdgeName, currentClassName, dependencyClassName, EdgeType.DIRECTED);
                indexEdgeName++;
            }
            g2.addVertex(entry.getValue().getClass().toString());
        }
    }



}



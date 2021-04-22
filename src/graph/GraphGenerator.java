package graph;

import vertexes.Configurate;

import java.util.ArrayList;
import java.util.Map;

public class GraphGenerator {
    private int maxDependency = 0;
    private ArrayList<String> nameClasses = new ArrayList<>();
    private String[] dependecy;

    public GraphGenerator(int maxDependency, Map conf, String className) {

        dependecy = new String[maxDependency];

        try {
            if (maxDependency >= conf.size()) {
                System.out.println("maxDependency is range");
                throw new Error();
            } else if (maxDependency < 0) {
                System.out.println("maxDependency is range");
                throw new Error();
            }

        } catch (Exception c) {

        }

        this.maxDependency = maxDependency;
        Map<Integer, Configurate> confClasses = conf;
        for (Map.Entry<Integer, Configurate> entry : confClasses.entrySet()) {
            String currentClassName = entry.getValue().getClass().toString();
            if (!className.equals(currentClassName)) {
                nameClasses.add(currentClassName);
            }
        }

        for (int i = 0; i < maxDependency; ++i) {
            dependecy[i] = nameClasses.get(i);
        }

    }

    public String[] getDependecy() {
        return dependecy;
    }
}

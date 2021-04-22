package graph;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import vertexes.Configurate;

import java.util.*;

public class GraphSort {

    private int numberVertexes;                                 // общее число вершин
    private Map<String, List<String>> listVertex;               // хеш всех доступных вершин и их зависимостей
    private LinkedHashMap<String, Integer> dictionary;          // хеш переводит именнованую вершину в числовой индекс
    private LinkedList<Integer> adjancelist[];                  // список смежности
    private Map<Integer, Configurate> confClasses;              // список конфигурационных классов интерфейса vertexes.Configurate
    public Graph<String, String> g3;                            // список вершин для графического отображения уже остортированого графа
    private boolean visited[];                                  // хранит состояние посещенна ли вершина? (да,нет)
    private boolean graphAcyclic = false;

    public GraphSort(Map conf) {
        g3 = new SparseMultigraph<>();
        confClasses = conf;
        numberVertexes = conf.size();
        listVertex = new HashMap<>();
        dictionary = new LinkedHashMap<>();
        adjancelist = new LinkedList[numberVertexes];
        visited = new boolean[numberVertexes];

        for (int i = 0; i < numberVertexes; ++i) {
            adjancelist[i] = new LinkedList();
        }

        List<String> listDependency;
        for (Map.Entry<Integer, Configurate> entry : confClasses.entrySet()) {     //извлекает имена всех вершин и их зависимостей
            String currentClassName = entry.getValue().getClass().toString();
            listDependency = new LinkedList<>();
            listVertex.put(currentClassName, listDependency);
            for (String dependencyClassName : entry.getValue().getDependency()) {
                listDependency.add(dependencyClassName);
            }
        }

        Integer index = 0;
        for (Map.Entry<Integer, Configurate> entry : confClasses.entrySet()) {
            String currentClassName = entry.getValue().getClass().toString();
            dictionary.put(currentClassName, index);                           //сохраняет все известные имена классов под индексами вершин
            index++;
        }

        for (Map.Entry<String, List<String>> entry : listVertex.entrySet()) {
            int v = dictionary.get(entry.getKey());                            //переводит и сохраняет именнованую вершину в числовой индекс
            for (String l : entry.getValue()) {
                int w = dictionary.get(l);
                adjancelist[v].add(w);                                         //помещает числовой индекс вершины в список смежности
            }
        }

    }

    public boolean depthFirstSeach(int vertex) {
        visited[vertex] = true;
        Iterator it = adjancelist[vertex].listIterator();
        while (it.hasNext()) {
            int adj = (Integer) it.next();
            if (!visited[adj]) {
                depthFirstSeach(adj);
            } else {
                graphAcyclic = true;
            }
        }
        return false;
    }


    public Graph<String, String> topologicalSort() {

        depthFirstSeach(numberVertexes - 1);   // проверка графа на ацикличность присваивает истину graphAcyclic если граф ацикличен в противном случае ложь

        if (!graphAcyclic) {
            ArrayList<String> vertexesOfSortGraph = new ArrayList<>();

            Stack stack = new Stack();
            boolean visited[] = new boolean[numberVertexes];
            for (int i = 0; i < numberVertexes; ++i) {
                visited[i] = false;
            }

            for (int i = 0; i < numberVertexes; ++i) {
                if (visited[i] == false) {
                    topologicalSortUtil(i, visited, stack);
                }
            }

            while (stack.empty() == false) {
                int value = (Integer) stack.pop();
                for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                    if (entry.getValue().equals(value)) {
                        vertexesOfSortGraph.add(entry.getKey());
                        break;
                    }
                }
            }


            ArrayList<String> orderedVertexes = new ArrayList<>();

            for (int i = 0; i < vertexesOfSortGraph.size(); ++i) {
                if (i == 0) {
                    orderedVertexes.add(vertexesOfSortGraph.get(i));
                    continue;
                }
                orderedVertexes.add(vertexesOfSortGraph.get(i));

                if (i == vertexesOfSortGraph.size() - 1) {
                    break;
                }
                for (int j = 0; j < 1; ++j) {
                    orderedVertexes.add(vertexesOfSortGraph.get(i));
                }
            }


            String firstVertex = new String();
            String secondVertex;
            Integer indexEdgeNumber = 0;
            Boolean scip = true;
            for (String currentVertex : orderedVertexes) {
                if (scip) {
                    firstVertex = currentVertex;
                    scip = false;
                } else {
                    secondVertex = currentVertex;
                    scip = true;
                    g3.addEdge("Edge" + indexEdgeNumber, firstVertex, secondVertex, EdgeType.DIRECTED);    //каждый первый и второй элемент последовательности образует ребро с вершинами first и second
                    indexEdgeNumber++;
                }
            }
        }
        else{
            System.out.println("Graph is not acyclic");
            return null;
        }
        return g3;
    }

    private void topologicalSortUtil(int vertex, boolean visited[], Stack stack) {
        visited[vertex] = true;
        Integer i;
        Iterator<Integer> it = adjancelist[vertex].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }
        stack.push(vertex);
    }

}



















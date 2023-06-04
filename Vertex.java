import java.util.HashMap;
import java.util.Map;

public class Vertex implements Comparable<Vertex>{
    private String name;
    private Map<Vertex,Integer> adjList;
    private int distance;

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Map<Vertex, Integer> getAdjList() {
        return this.adjList;
    }

    public void setAdjList(Map<Vertex, Integer> adjList) {
        this.adjList = adjList;
    }

    public String getName() {
        return this.name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public Vertex(String name){
        this.name=name;
        this.adjList=new HashMap<>();
        this.distance = Integer.MAX_VALUE;
    }

    public void add(Vertex adjVertex , Integer weight){
        adjList.put(adjVertex, weight);
        adjVertex.getAdjList().put(this, weight);
    }

    @Override
    public int compareTo(Vertex v) {
        return this.distance-v.getDistance();
    }
}

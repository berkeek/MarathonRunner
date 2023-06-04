import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {
    private String start;
    private String end;
    private Map<String, Vertex> vertexMap;
    private PriorityQueue<Vertex> prioq;
    private PriorityQueue<Vertex> flagprioq;

    public PriorityQueue<Vertex> getFlagprioq() {
        return this.flagprioq;
    }

    public void setFlagprioq(PriorityQueue<Vertex> flagprioq) {
        this.flagprioq = flagprioq;
    }

    public PriorityQueue<Vertex> getPrioq() {
        return this.prioq;
    }

    public void setPrioq(PriorityQueue<Vertex> prioq) {
        this.prioq = prioq;
    }

    public Map<String, Vertex> getVertexMap() {
        return this.vertexMap;
    }

    public void setVertexMap(Map<String, Vertex> vertexMap) {
        this.vertexMap = vertexMap;
    }

    public Graph(int capacity){
        this.vertexMap= new HashMap<>(capacity);
        this.prioq= new PriorityQueue<>();
        this.flagprioq= new PriorityQueue<>();
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void findDistances(Vertex start){
        start.setDistance(0);
        prioq.add(start);
        HashSet<Vertex> visited = new HashSet<>();
        
        while (visited.size()!=vertexMap.size()){
            if (prioq.isEmpty()){
                return;
            }
            Vertex minDistance = prioq.remove();

            if(visited.contains(minDistance)){
                continue;
            }
            visited.add(minDistance);

            minDistance.getAdjList().forEach(
                (Vertex vertex , Integer weight) -> {
                    if (!visited.contains(vertex)){
                        prioq.add(vertex); // sonraki ifin icine alinabilir
                        if (minDistance.getDistance() + weight < vertex.getDistance()){
                            vertex.setDistance(minDistance.getDistance()+weight);
                        }
                    }
                });
            
        }
        prioq.clear();
    }

    public int findFlagDistances(Vertex startingFlag,HashSet<Vertex> flagmap){
        int totalDistance = 0; 
        HashSet<Vertex> visitedFlags = new HashSet<>();
        HashSet<Vertex> visitedVertexes = new HashSet<>();
        startingFlag.setDistance(0);
        flagprioq.add(startingFlag);
        while(visitedFlags.size()!=flagmap.size()){
            while (visitedVertexes.size()!=this.getVertexMap().size()){
                if (flagprioq.isEmpty()){
                    return totalDistance;
                }
                Vertex minDistance = flagprioq.remove();
    
            //if vertex is visited continue to next vertes
            if(visitedVertexes.contains(minDistance)){
                continue;
            }
            
            visitedVertexes.add(minDistance);

            minDistance.getAdjList().forEach(
            (Vertex vertex , Integer weight) -> {
                if (!visitedVertexes.contains(vertex)){
                    if (minDistance.getDistance() + weight < vertex.getDistance()){
                        vertex.setDistance(minDistance.getDistance()+weight);
                    }
                    flagprioq.add(vertex); 
                }
            });

            if (!visitedFlags.contains(minDistance)){
                if (flagmap.contains(minDistance)){
                    visitedFlags.add(minDistance);
                    totalDistance+=minDistance.getDistance();
                    minDistance.setDistance(0);
                    visitedVertexes.clear();
                    break;
                }
            }
            }
            visitedFlags.forEach(
                (Vertex vertex) -> {
                    flagprioq.add(vertex);
                });
        
        }

        return totalDistance;
        
    }

    public void resetDistances(){
        this.vertexMap.forEach(
            (String s, Vertex v) -> {
                v.setDistance(Integer.MAX_VALUE);
            });
    }
}

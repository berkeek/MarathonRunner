import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class project4{
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        Scanner sc = new Scanner(file);
              
        FileWriter writer = new FileWriter(args[1]);
        BufferedWriter bf = new BufferedWriter(writer);
        //number of vertexes
        int graphCapacity = sc.nextInt();
        sc.nextLine();

        //number of flags
        int flagNum = sc.nextInt();
        sc.nextLine();

        //graph
        Graph graph = new Graph(graphCapacity);

        //hashmap of flags
        HashSet<Vertex> flagMap = new HashSet<>(flagNum);

        //start and end vertexes
        String[] list = sc.nextLine().split(" ");
        String start = list[0];
        String end = list[1];

        //flag vertexes
        String[] flags = sc.nextLine().split(" ");
        String flag1 = flags[0];
        for (String name : flags){
            Vertex tmp = new Vertex(name);
            flagMap.add(tmp);
            graph.getVertexMap().put(name, tmp);
        }
        
        
        while (sc.hasNextLine()){
            String[] line = sc.nextLine().split(" ");
            String vrtx = line[0];
            Vertex v;
            if (graph.getVertexMap().containsKey(vrtx)){
                 v = graph.getVertexMap().get(vrtx);
            }else{
                v = new Vertex(vrtx);
                graph.getVertexMap().put(vrtx, v);
            }
            for (int i = 1;i<line.length;i=i+2){
                String vrtx1 = line[i];
                Vertex v1;
                if (graph.getVertexMap().containsKey(vrtx1)){
                     v1 = graph.getVertexMap().get(vrtx1);
                }else{
                    v1 = new Vertex(vrtx1);
                    graph.getVertexMap().put(vrtx1, v1);
                }
                v.add(v1,Integer.parseInt(line[i+1]));
            }
            
        }
        sc.close();



        //graph.getVertexMap().forEach((String k, Vertex v) -> 
        //{v.getAdjList().forEach((Vertex k1,Integer v1) -> 
        //    {System.out.println(k+": "+k1.getName()+"-->"+v1);});});
        graph.findDistances(graph.getVertexMap().get(start));
        int firstResult = graph.getVertexMap().get(end).getDistance();
        if (firstResult!=Integer.MAX_VALUE){
            bf.write(firstResult+"");
        }else{
            bf.write(-1+"");
        }
        bf.write("\n");
        graph.resetDistances();
        if(flagNum==0 ){
            bf.write(-1+"");
        }else{
            int result = graph.findFlagDistances(graph.getVertexMap().get(flag1), flagMap);
            if (result!=0){
                bf.write(result+"");
            }else{
                bf.write(-1+"");
            }
            
        }
        bf.close();      
    }

    
}
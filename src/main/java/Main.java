import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int numberOfIslands;
    static int numberOfFerries;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String lineOne = reader.readLine();
        String[] partsOne = lineOne.split(" ");
        numberOfIslands = Integer.parseInt(partsOne[0]);
        int maxBridges = Integer.parseInt(partsOne[1]);
        numberOfFerries = Integer.parseInt(partsOne[2]);
        /*
        numberOfIslands = scanner.nextInt();
        int maxBridges = scanner.nextInt();
        numberOfFerries = scanner.nextInt();

         */
        ArrayList<Edge>[] islands = new ArrayList[numberOfIslands + 1];

        for(int i = 0; i <= numberOfIslands;i++){
            islands[i] = new ArrayList<>();
        }
        ArrayList<Edge> edges = new ArrayList<> ();

        for (int i = 0; i < maxBridges; i++){
            String line = reader.readLine();
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            Edge e = new Edge(a,b,c);
            edges.add(e);
            islands[a].add(e);
            islands[b].add(e);
        }
        Collections.sort(edges);
        int[] prizes = new int[numberOfIslands - 1];


        BitSet visitedIslands = new BitSet(numberOfIslands + 1);
        int e = 0;
        visitedIslands.set(1);
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.addAll(islands[1]);

        while (e < numberOfIslands - 1){
            Edge cheapestEdge = queue.poll();
            if (!visitedIslands.get(cheapestEdge.src)){ //If cheapest route endpoint hasnt been visited //TODO: Add thingy in case it has been visited
                queue.addAll(islands[cheapestEdge.src]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.src); //Sets cheapest route as visited
                prizes[e] = cheapestEdge.weight;
                e++;
            }
            if (!visitedIslands.get(cheapestEdge.dest)){ //If cheapest route endpoint hasnt been visited //TODO: Add thingy in case it has been visited
                queue.addAll(islands[cheapestEdge.dest]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.dest); //Sets cheapest route as visited
                prizes[e] = cheapestEdge.weight;
                e++;
            }
        }
        Arrays.parallelSort(prizes);
        int totalPris = 0;
        for(int i = 0; i < numberOfIslands-numberOfFerries-1;i++){
            totalPris += prizes[i];
        }
        System.out.println(totalPris);
    }
}

class Edge implements Comparable<Edge> {
    int src;
    int dest;
    int weight;
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    @Override
    public int compareTo(Edge o) {
        if (o == null) return Integer.MIN_VALUE;
        return this.weight - o.weight;
    }
}
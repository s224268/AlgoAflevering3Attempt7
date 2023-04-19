import java.util.*;

public class Main {
    static int numberOfIslands;
    static boolean[] visited;
    static int numberOfFerries;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfIslands = scanner.nextInt();
        int maxBridges = scanner.nextInt();
        numberOfFerries = scanner.nextInt();
        //Edge[] edges = new Edge[maxBridges + numberOfFerries - 1];
        ArrayList<Edge>[] islands = new ArrayList[numberOfIslands + 1];

        for(int i = 0; i <= numberOfIslands;i++){
            islands[i] = new ArrayList<>();
        }
        ArrayList<Edge> edges = new ArrayList<> ();

        for (int i = 0; i < maxBridges; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            Edge e = new Edge(a,b,c);
            edges.add(e); //Makes the edge and adds it to the array
            islands[a].add(e);
            islands[b].add(e);//adds the newly made edge to its' owner island
        }
        Collections.sort(edges);
        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<Edge> MSTedges = new ArrayList<>();


        BitSet visitedIslands = new BitSet(numberOfIslands + 1);
        int e = 0;
        int j = 0;
        //ArrayList<Edge> possibleEdges = new ArrayList<>();
        visitedIslands.set(1);
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.addAll(islands[1]);
        //possibleEdges.addAll(islands[1]);

        while (e < numberOfIslands - 1){
            Edge cheapestEdge = null;
            cheapestEdge = queue.poll();
            MSTedges.add(cheapestEdge);
            if (!visitedIslands.get(cheapestEdge.src)){ //If cheapest route endpoint hasnt been visited //TODO: Add thingy in case it has been visited
                queue.addAll(islands[cheapestEdge.src]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.src); //Sets cheapest route as visited
                prices.add(cheapestEdge.weight); //Adds the price, for keeping tally
                e++;
            }
            if (!visitedIslands.get(cheapestEdge.dest)){ //If cheapest route endpoint hasnt been visited //TODO: Add thingy in case it has been visited
                queue.addAll(islands[cheapestEdge.dest]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.dest); //Sets cheapest route as visited
                prices.add(cheapestEdge.weight); //Adds the price, for keeping tally
                e++;
            }
            if (j < numberOfIslands){
                j++;
            }
        }
        Collections.sort(prices);
        int totalPris = 0;
        for(int i = 0; i < numberOfIslands-numberOfFerries-1;i++){
            totalPris += prices.get(i);
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
        if (this == null) return Integer.MAX_VALUE;
        if (o == null) return Integer.MIN_VALUE;
        return this.weight - o.weight;
    }
}
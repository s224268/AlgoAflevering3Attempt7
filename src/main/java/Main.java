import java.util.*;

public class Main {
    static int numberOfIslands;
    static boolean[] visited;
    static int[] priser;
    static int numberOfFerries;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfIslands = scanner.nextInt();
        int maxBridges = scanner.nextInt();
        numberOfFerries = scanner.nextInt();
        Edge[] edges = new Edge[maxBridges + numberOfFerries - 1];
        ArrayList<Edge>[] islands = new ArrayList[numberOfIslands + 1];

        for(int i = 0; i <= numberOfIslands;i++){
            islands[i] = new ArrayList<Edge>();
        }

        for (int i = 0; i < maxBridges; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            edges[i] = new Edge(a,b,c); //Makes the edge and adds it to the array
            islands[a].add(edges[i]); //adds the newly made edge to its' owner island
        }
        Arrays.parallelSort(edges);
        ArrayList<Integer> prices = new ArrayList<>();
        ArrayList<Edge> MSTedges = new ArrayList<>();


        BitSet visitedIslands = new BitSet(numberOfIslands);
        int e = 0;
        int i = 0;
        ArrayList<Edge> possibleedges = new ArrayList<>();
        possibleedges.addAll(islands[1]);//Adds all edges from the 0´th island, making it our starting point
        while (e < numberOfIslands - 1){
            Edge cheapestEdge = Collections.min(possibleedges); //Find the cheapest route from known islands
            possibleedges.remove(cheapestEdge); //Removes it so it won't be checked in the future
            if (!visitedIslands.get(cheapestEdge.dest)){ //If cheapest route endpoint hasnt been visited //TODO: Add thingy in case it has been visited
                MSTedges.add(cheapestEdge); //Not sure if this is needed
                possibleedges.addAll(islands[cheapestEdge.dest]); //Adds all routes from the destination of the cheapest edge
                visitedIslands.set(cheapestEdge.dest); //Sets cheapest route as visited
                prices.add(cheapestEdge.weight); //Adds the price, for keeping tally
                e++;
            }
        }
        int totalPrice = 0;
        for(int b : prices){
            totalPrice += b;
        }
        System.out.println(totalPrice);


    }

    public static boolean isGood(int i, int j){
        if (!visited[i] && !visited[j]) return false;
        return !visited[i] || !visited[j]; //intellij wants to do magic
    }


    public static void prim(int[][] matrix){
        visited = new boolean[numberOfIslands + 1];
        visited[1] = true;
        int goodEdges = 0;
        while (goodEdges < numberOfIslands - 1){
            int val1 = -1; int val2 = -1; int minPrice = Integer.MAX_VALUE; //Maybe make this higher
            for (int i = 1; i <= numberOfIslands; i++){
                for (int j = 1; j <= numberOfIslands; j++){
                    int potPrice = matrix[j][i];
                    if (potPrice < minPrice && potPrice != 0){
                        if (isGood(j,i)){
                            minPrice = potPrice;
                            val1 = j;
                            val2 = i;
                            if (minPrice == 1) break;
                        }
                    }
                }
            }
            if (val1 > 0 && val2 > 0){
                goodEdges++;
                priser[goodEdges-1] = minPrice;
                visited[val1] = true;
                visited[val2] = true;
            }
        }
    }

    public static void prim2(int[][] matrix){

    }

}

class Edge implements Comparable<Edge> {
    int src, dest, weight;
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    @Override
    public int compareTo(Edge o) {
        return this.weight - o.weight;
    }
}
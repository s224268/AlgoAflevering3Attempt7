import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    static int numberOfIslands;
    static boolean[] visited;
    static boolean[][] goodPaths;
    static ArrayList<Integer> prices;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        numberOfIslands = scanner.nextInt();
        int maxBridges = scanner.nextInt();
        int numberOfFerries = scanner.nextInt();
        int[][] matrix = new int[numberOfIslands+1][numberOfIslands+1];

        for (int i = 0; i < maxBridges; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            matrix[a][b] = c;
            matrix[b][a] = c; //Technically unnessecary but makes it cleaner
        }
        //printMatrix(matrix);
        prices = new ArrayList<>(); //This could be an array
        prim(matrix);
        //System.out.println("Unsorted prices: " + prices);
        Collections.sort(prices);
        //System.out.println("Sorted prices: " + prices);
        int totalPrice = 0;
        for(int i = 0; i < numberOfIslands-numberOfFerries-1;i++){
            totalPrice += prices.get(i);
        }
        System.out.println(totalPrice);
    }

    public static boolean isGood(int i, int j){
        if (i == j) return false;
        if (!visited[i] && !visited[j]) return false;
        if (visited[i] && visited[j]) return false; //intellij wants to do magic
        return true;
    }

    public static void prim(int[][] matrix){
        visited = new boolean[numberOfIslands + 1];


        visited[1] = true;
        int goodEdges = 0;
        while (goodEdges < numberOfIslands - 1){
            int val1 = -1; int val2 = -1; int minPrice = 200001; //Maybe make this higher
            for (int i = 1; i <= numberOfIslands; i++){
                for (int j = 1; j <= numberOfIslands; j++){
                    if (matrix[i][j] < minPrice && matrix[i][j] != 0){
                        if (isGood(i,j)){
                            minPrice = matrix[i][j]; //Should decrease step by step
                            val1 = i;
                            val2 = j;
                            //if (minPrice == 1) break;
                        }
                    }
                }
            }
            if (val1 > 0 && val2 > 0){
                goodEdges++;
                prices.add(minPrice);
                visited[val1] = true;
                visited[val2] = true;
            }
        }
        //System.out.println("Total pris uden færger: " + totalCost);
    }



    public static void printMatrix(int[][] matrix){
        for (int k = 0; k <= numberOfIslands; k++){
            System.out.print(k + "   ");
        }
        System.out.println("");
        for (int i = 1; i <= numberOfIslands;i++){
            System.out.print(i + " ");
            for (int j = 1; j <= numberOfIslands;j++){
                System.out.print("[" + matrix[i][j] + "] ");
            }
            System.out.println("");
        }
    }
}

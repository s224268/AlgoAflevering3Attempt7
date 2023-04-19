import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int numberOfIslands;
    static boolean[] visited;
    static int[] priser;
    static int numberOfFerries;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String lineOne = reader.readLine();
        String[] partsOne = lineOne.split(" ");
        numberOfIslands = Integer.parseInt(partsOne[0]);
        int maxBridges = Integer.parseInt(partsOne[1]);
        numberOfFerries = Integer.parseInt(partsOne[2]);

        int[][] matrix = new int[numberOfIslands+1][numberOfIslands+1];


        for (int i = 0; i < maxBridges; i++){
            String line = reader.readLine();
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            int c = Integer.parseInt(parts[2]);
            matrix[a][b] = c;
        }
        priser = new int[numberOfIslands - 1];
        prim(matrix);

        Arrays.sort(priser);

        int totalPris = 0;
        for(int i = 0; i < numberOfIslands-numberOfFerries-1;i++){
            totalPris += priser[i];
        }
        System.out.println(totalPris);
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
                    int potPrice = matrix[i][j];
                    if (potPrice == 0){
                        continue;
                    }
                    if (potPrice < minPrice){
                        if (isGood(i,j)){
                            minPrice = potPrice;
                            val1 = i;
                            val2 = j;
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
}

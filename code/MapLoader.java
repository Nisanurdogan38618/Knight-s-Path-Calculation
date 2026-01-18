/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
import java.io.*;
import java.util.*;

public class MapLoader {
    public Tile[][] tiles;
    public int width, height;
    public List<Position> objectives = new ArrayList<>();
    public Map<String, Double> travelCosts = new HashMap<>();

    public void loadMap(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String[] dims = br.readLine().split(" ");
        width = Integer.parseInt(dims[0]);
        height = Integer.parseInt(dims[1]);
        tiles = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String[] parts = br.readLine().split(" ");
                int col = Integer.parseInt(parts[0]);
                int row = Integer.parseInt(parts[1]);
                int type = Integer.parseInt(parts[2]);
                tiles[col][row] = new Tile(col, row, type);
            }
        }

        br.close();
    }

    public void loadCosts(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split(" ");
            String key1 = parts[0] + "," + parts[1] + "-" + parts[2] + "," + parts[3];
            String key2 = parts[2] + "," + parts[3] + "-" + parts[0] + "," + parts[1];
            double cost = Double.parseDouble(parts[4].replace(",", "."));
            travelCosts.put(key1, cost);
            travelCosts.put(key2, cost);
        }

        br.close();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile t = tiles[x][y];
                if (t == null || t.type == 2) continue;

                for (int[] d : new int[][]{{0,1},{1,0},{0,-1},{-1,0}}) {
                    int nx = x + d[0];
                    int ny = y + d[1];
                    if (nx < 0 || ny < 0 || nx >= width || ny >= height) continue;
                    Tile neighbor = tiles[nx][ny];
                    if (neighbor == null || neighbor.type == 2) continue;

                    String key = x + "," + y + "-" + nx + "," + ny;
                    if (travelCosts.containsKey(key)) {
                        t.adjacentTiles.add(neighbor);
                    }
                }
            }
        }
    }

    public void loadObjectives(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        objectives.clear();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isBlank()) continue;
            String[] parts = line.trim().split(" ");
            objectives.add(new Position(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }
        br.close();
    }

    public double getCost(Tile from, Tile to) {
        String key = from.column + "," + from.row + "-" + to.column + "," + to.row;
        return travelCosts.getOrDefault(key, Double.POSITIVE_INFINITY);
    }
}



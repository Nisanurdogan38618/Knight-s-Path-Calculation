/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
import java.util.*;

public class PathFinder {
    private MapLoader loader;

    public PathFinder(MapLoader loader) {
        this.loader = loader;
    }

    public List<Tile> findShortestPath(Tile start, Tile goal) {
        Map<Tile, Double> dist = new HashMap<>();
        Map<Tile, Tile> prev = new HashMap<>();
        PriorityQueue<Tile> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (int x = 0; x < loader.width; x++) {
            for (int y = 0; y < loader.height; y++) {
                Tile t = loader.tiles[x][y];
                if (t != null && t.type != 2) {
                    dist.put(t, Double.POSITIVE_INFINITY);
                    prev.put(t, null);
                }
            }
        }

        dist.put(start, 0.0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Tile current = pq.poll();
            if (current.equals(goal)) break;

            for (Tile neighbor : current.adjacentTiles) {
                double alt = dist.get(current) + loader.getCost(current, neighbor);
                if (alt < dist.get(neighbor)) {
                    dist.put(neighbor, alt);
                    prev.put(neighbor, current);
                    pq.remove(neighbor);
                    pq.add(neighbor);
                }
            }
        }

        if (prev.get(goal) == null) return null;

        List<Tile> path = new ArrayList<>();
        Tile step = goal;
        while (step != null) {
            path.add(0, step);
            step = prev.get(step);
        }

        return path;
    }
}


/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
import java.util.*;

public class ShortestRoute {
    public static List<Position> getOptimalOrder(MapLoader loader, PathFinder pathFinder) {
        List<Position> objectives = new ArrayList<>(loader.objectives);
        Position start = objectives.remove(0);

        final List<Position>[] bestOrder = new List[]{null};
        final double[] minCost = {Double.POSITIVE_INFINITY};

        Map<String, Double> costCache = new HashMap<>();

        permute(objectives, 0, objectives.size(), perm -> {
            double cost = 0;
            Tile current = loader.tiles[start.col][start.row];
            boolean valid = true;

            for (Position pos : perm) {
                Tile next = loader.tiles[pos.col][pos.row];
                double stepCost = getPathCost(current, next, pathFinder, loader, costCache);
                if (stepCost == Double.POSITIVE_INFINITY) {
                    valid = false;
                    break;
                }
                cost += stepCost;
                current = next;
            }

            if (valid && cost < minCost[0]) {
                minCost[0] = cost;
                bestOrder[0] = new ArrayList<>();
                bestOrder[0].add(start);
                bestOrder[0].addAll(perm);
            }
        });

        if (bestOrder[0] == null) return List.of(start); // fallback: only start
        return bestOrder[0];
    }

    private static double getPathCost(Tile from, Tile to, PathFinder pf, MapLoader loader, Map<String, Double> cache) {
        String key = from.column + "," + from.row + ":" + to.column + "," + to.row;
        if (cache.containsKey(key)) return cache.get(key);

        List<Tile> path = pf.findShortestPath(from, to);
        if (path == null) {
            cache.put(key, Double.POSITIVE_INFINITY);
            return Double.POSITIVE_INFINITY;
        }

        double cost = 0;
        for (int i = 1; i < path.size(); i++) {
            cost += loader.getCost(path.get(i - 1), path.get(i));
        }

        cache.put(key, cost);
        return cost;
    }

    private static void permute(List<Position> arr, int k, int n, java.util.function.Consumer<List<Position>> callback) {
        if (k == n) {
            callback.accept(new ArrayList<>(arr));
        } else {
            for (int i = k; i < n; i++) {
                Collections.swap(arr, i, k);
                permute(arr, k + 1, n, callback);
                Collections.swap(arr, i, k);
            }
        }
    }
}

/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean draw = args.length > 0 && args[0].equals("-draw");
        int offset = draw ? 1 : 0;

        if (args.length < offset + 3) {
            System.out.println("Usage: java Main [-draw] mapData.txt travelCosts.txt objectives.txt");
            return;
        }

        String mapFile = args[offset];
        String costFile = args[offset + 1];
        String objFile = args[offset + 2];

        MapLoader loader = new MapLoader();
        loader.loadMap(mapFile);
        loader.loadCosts(costFile);
        loader.loadObjectives(objFile);

        PathFinder pathFinder = new PathFinder(loader);
        Set<Position> collectedCoins = new HashSet<>();
        List<Tile> redDots = new ArrayList<>();

        new File("out").mkdirs();
        FileWriter writer = new FileWriter("out/output.txt");

        Position start = loader.objectives.get(0);
        Tile current = loader.tiles[start.col][start.row];

        double totalCost = 0;
        int totalSteps = 0;

        if (draw) {
            GameRenderer.drawMap(loader.tiles, loader.height);
            for (int i = 1; i < loader.objectives.size(); i++) {
                Position pos = loader.objectives.get(i);
                if (!collectedCoins.contains(pos)) {
                    Tile t = loader.tiles[pos.col][pos.row];
                    GameRenderer.drawTile(t, loader.height, "misc/coin.png");
                }
            }
            StdDraw.show();
        }

        for (int i = 1; i < loader.objectives.size(); i++) {
            Position targetPos = loader.objectives.get(i);
            Tile target = loader.tiles[targetPos.col][targetPos.row];

            List<Tile> path = pathFinder.findShortestPath(current, target);

            if (path == null || path.isEmpty()) {
                writer.write("Objective " + i + " cannot be reached!\n");
                continue;
            }

            writer.write("Starting position: (" + current.column + ", " + current.row + ")\n");

            double pathCost = 0;
            for (int step = 1; step < path.size(); step++) {
                Tile from = path.get(step - 1);
                Tile to = path.get(step);
                double cost = loader.getCost(from, to);
                pathCost += cost;
                totalSteps++;

                writer.write("Step Count: " + step + ", move to " + to + ". Total Cost: " + String.format(Locale.US, "%.2f", pathCost) + ".\n");

                if (draw) {
                    String bg = switch (from.type) {
                        case 0 -> "misc/grassTile.jpeg";
                        case 1 -> "misc/sandTile.png";
                        default -> "misc/impassableTile.jpeg";
                    };
                    StdDraw.picture(from.column + 0.5, from.row + 0.5, bg, 1, 1);

                    for (int j = 1; j < loader.objectives.size(); j++) {
                        Position pos = loader.objectives.get(j);
                        if (collectedCoins.contains(pos)) continue;
                        Tile coin = loader.tiles[pos.col][pos.row];
                        if (!coin.equals(from) && !coin.equals(to)) {
                            GameRenderer.drawTile(coin, loader.height, "misc/coin.png");
                        }
                    }

                    StdDraw.setPenColor(StdDraw.BOOK_RED);
                    StdDraw.filledCircle(from.column + 0.5, from.row + 0.5, 0.1);
                    redDots.add(from);

                    GameRenderer.drawTile(to, loader.height, "misc/knight.png");
                    StdDraw.show();
                    StdDraw.pause(200);
                }

                for (int j = 1; j < loader.objectives.size(); j++) {
                    Position pos = loader.objectives.get(j);
                    if (pos.col == to.column && pos.row == to.row) {
                        collectedCoins.add(pos);
                        break;
                    }
                }
            }

            writer.write("Objective " + i + " reached!\n");

            current = target;
            totalCost += pathCost;

            if (draw) {
                for (Tile t : redDots) {
                    String bg = switch (t.type) {
                        case 0 -> "misc/grassTile.jpeg";
                        case 1 -> "misc/sandTile.png";
                        default -> "misc/impassableTile.jpeg";
                    };
                    StdDraw.picture(t.column + 0.5, t.row + 0.5, bg, 1, 1);
                }
                redDots.clear();

                for (int j = 1; j < loader.objectives.size(); j++) {
                    Position pos = loader.objectives.get(j);
                    if (!collectedCoins.contains(pos)) {
                        Tile coin = loader.tiles[pos.col][pos.row];
                        GameRenderer.drawTile(coin, loader.height, "misc/coin.png");
                    }
                }

                GameRenderer.drawTile(current, loader.height, "misc/knight.png");
                StdDraw.show();
            }
        }

        writer.write("Total Step:" + totalSteps + ", Total Cost: " + String.format(Locale.US, "%.2f", totalCost) + "\n");
        writer.close();
    }
}

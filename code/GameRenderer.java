/*
 * Name: Nisanur Dogan
 * ID: 2023400186
 */
import java.util.List;

public class GameRenderer {
    public static void drawMap(Tile[][] tiles, int height) {
        int cols = tiles.length;
        int rows = tiles[0].length;
        int canvasWidth = 600;
        int canvasHeight = 600;
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.setXscale(0, cols);
        StdDraw.setYscale(0, rows);
        StdDraw.clear();

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Tile tile = tiles[x][y];
                if (tile == null) continue;

                String img = switch (tile.type) {
                    case 0 -> "misc/grassTile.jpeg";
                    case 1 -> "misc/sandTile.png";
                    case 2 -> "misc/impassableTile.jpeg";
                    default -> null;
                };

                if (img != null) {
                    StdDraw.picture(x + 0.5, y + 0.5, img, 1, 1);
                }
            }
        }

        StdDraw.show();
    }

    public static void drawTile(Tile tile, int height, String imgPath) {
        StdDraw.picture(tile.column + 0.5, tile.row + 0.5, imgPath, 1, 1);
    }
}



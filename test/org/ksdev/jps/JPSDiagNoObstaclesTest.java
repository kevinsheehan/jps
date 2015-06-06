package org.ksdev.jps;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Kevin
 */
public class JPSDiagNoObstaclesTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Tile[][] tiles = new Tile[][]{
                {new Tile(0, 0), new Tile(1, 0), new Tile(2, 0), new Tile(3, 0), new Tile(4, 0), new Tile(5, 0), new Tile(6, 0), new Tile(7, 0), new Tile(8, 0)},
                {new Tile(0, 1), new Tile(1, 1), new Tile(2, 1), new Tile(3, 1), new Tile(4, 1), new Tile(5, 1), new Tile(6, 1), new Tile(7, 1), new Tile(8, 1)},
                {new Tile(0, 2), new Tile(1, 2), new Tile(2, 2), new Tile(3, 2), new Tile(4, 2), new Tile(5, 2), new Tile(6, 2), new Tile(7, 2), new Tile(8, 2)},
                {new Tile(0, 3), new Tile(1, 3), new Tile(2, 3), new Tile(3, 3), new Tile(4, 3), new Tile(5, 3), new Tile(6, 3), new Tile(7, 3), new Tile(8, 3)},
                {new Tile(0, 4), new Tile(1, 4), new Tile(2, 4), new Tile(3, 4), new Tile(4, 4), new Tile(5, 4), new Tile(6, 4), new Tile(7, 4), new Tile(8, 4)}
        };

        List<List<Tile>> tileList = JPSTestUtil.arraysToLists(tiles);

        JPS<Tile> jps = JPS.JPSFactory.getJPS(new Grid<>(tileList), Graph.Diagonal.NO_OBSTACLES);

        if (jps == null) {
            System.out.println("JPS is null");
            return;
        }

        Tile start = tileList.get(4).get(0);
        Tile end = tileList.get(0).get(8);
        System.out.println("Start: " + start.x + ", " + start.y);
        System.out.println("End: " + end.x + ", " + end.y);

        /*for (int i = 0; i < 4; i++) {
            tileList.get(2).get(i).walkable = false;
        }*/
        for (int i = 1; i < 5; i++) {
            for (int j = 5; j < 9; j++) {
                tileList.get(i).get(j).walkable = false;
            }
        }


        Future<Queue<Tile>> futurePath = jps.findPath(start, end);

        Queue<Tile> path = futurePath.get();

        if (path == null || path.isEmpty()) {
            System.out.printf("No path found");
            return;
        }

        for (Tile tile : path) {
            System.out.println(String.format("X: %s, Y: %s", tile.x, tile.y));
        }
    }
}

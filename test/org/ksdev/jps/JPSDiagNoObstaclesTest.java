package org.ksdev.jps;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Kevin
 */
public class JPSDiagNoObstaclesTest extends JPSDiagBaseTest {
    @Before
    public void setup() { setup(Graph.Diagonal.NO_OBSTACLES); }

    @Test
    public void goAroundWall() throws ExecutionException, InterruptedException {
        Tile start = tileList.get(0).get(0);
        Tile end = tileList.get(4).get(4);
        for (int i = 0; i < 4; i++) {
            tileList.get(2).get(i).walkable = false;
        }
        tileList.get(1).get(4).walkable = false;

        Future<Queue<Tile>> futurePath = jps.findPath(start, end);
        Queue<Tile> path = futurePath.get();

        assert path.remove().equals(tileList.get(0).get(0));
        assert path.remove().equals(tileList.get(0).get(1));
        assert path.remove().equals(tileList.get(0).get(2));
        assert path.remove().equals(tileList.get(0).get(3));
        assert path.remove().equals(tileList.get(0).get(4));
        assert path.remove().equals(tileList.get(0).get(5));
        assert path.remove().equals(tileList.get(1).get(5));
        assert path.remove().equals(tileList.get(2).get(5));
        assert path.remove().equals(tileList.get(3).get(4));
        assert path.remove().equals(tileList.get(4).get(4));
    }

    @Test
    public void diagonalTwoObstacles() throws ExecutionException, InterruptedException {
        // This test will fail (which is good)
        Tile start = tileList.get(4).get(0);
        Tile end = tileList.get(0).get(8);
        for (int i = 1; i < 5; i++) {
            for (int j = 5; j < 9; j++) {
                tileList.get(i).get(j).walkable = false;
            }
        }
        for (int i = 0; i < 4; i++) {
            tileList.get(2).get(i).walkable = false;
        }
        tileList.get(0).get(3).walkable = false;
        tileList.get(1).get(4).walkable = false;

        Future<Queue<Tile>> futurePath = jps.findPath(start, end);
        Queue<Tile> path = futurePath.get();

        assert path == null;
    }

    @Test
    public void noObstacles() throws ExecutionException, InterruptedException {
        Tile start = tileList.get(4).get(0);
        Tile end = tileList.get(0).get(4);

        Future<Queue<Tile>> futurePath = jps.findPath(start, end);
        Queue<Tile> path = futurePath.get();

        assert path.remove().equals(tileList.get(4).get(0));
        assert path.remove().equals(tileList.get(3).get(1));
        assert path.remove().equals(tileList.get(2).get(2));
        assert path.remove().equals(tileList.get(1).get(3));
        assert path.remove().equals(tileList.get(0).get(4));
    }

    @Test
    public void noPath() throws ExecutionException, InterruptedException {
        Tile start = tileList.get(0).get(0);
        Tile end = tileList.get(4).get(8);

        for (Tile tile : tileList.get(2)) {
            tile.walkable = false;
        }

        Future<Queue<Tile>> futurePath = jps.findPath(start, end);
        Queue<Tile> path = futurePath.get();

        assert path == null;
    }

    @Test
    public void map2() throws ExecutionException, InterruptedException {
        Tile start = tileList2.get(38).get(34);
        System.out.println(start.isWalkable());
        Tile end = tileList2.get(36).get(30);
        System.out.println(end.isWalkable());

        Future<Queue<Tile>> futurePath = jps2.findPath(start, end);
        Queue<Tile> path = futurePath.get();

        for (Tile tile : path) {
            System.out.println("X: " + tile.x + ", Y: " + tile.y);
        }

        assert path != null;
    }
}

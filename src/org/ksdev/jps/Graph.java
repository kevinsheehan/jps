package org.ksdev.jps;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kevin
 */
public abstract class Graph<T extends Node> {
    public enum Diagonal {
        ALWAYS,
        NO_OBSTACLES,
        ONE_OBSTACLE,
        NEVER
    }

    private List<T> nodes;
    private int width;

    public Graph(List<List<T>> map) {
        if (map == null || map.get(0) == null) return;
        width = map.get(0).size();
        nodes = new ArrayList<>(map.size() * map.get(0).size());

        map.forEach(nodes::addAll);
        System.out.println("nodes length " + nodes.size());
    }

    /**
     * @return List of all nodes in the graph.
     */
    public Collection<T> getNodes() { return nodes; }

    public T getNode(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= nodes.size() / width) {
            return null;
        }
        return nodes.get(x + y*width);
    }

    /**
     * Given two adjacent nodes, returns the distance between them.
     * @return The distance between the two nodes given.
     */
    public abstract double getDistance(Node a, Node b);

    /**
     * Given two nodes, returns the estimated distance between them. Optimizing this is the best way to improve
     * performance of your search time.
     * @return Estimated distance between the two given nodes.
     */
    public abstract double getHeuristicDistance(Node a, Node b);

    /**
     * By default, we return all reachable diagonal neighbors that have no obstacles blocking us.
     * i.e.
     * O O G
     * O C X
     * O O O
     *
     * In this above example, we could not go diagonally from our (C)urrent position to our (G)oal do to the obstacle (X).
     *
     * Please use {@link #getNeighborsOf(Node, Diagonal)} method if you would like to specify different diagonal functionality.
     *
     * @return All reachable neighboring nodes of the given node.
     */
    public Collection<T> getNeighborsOf(T node) {
        return getNeighborsOf(node, Diagonal.NO_OBSTACLES);
    }

    /**
     * @return All reachable neighboring nodes of the given node.
     */
    public Collection<T> getNeighborsOf(T node, Diagonal diagonal) {
        int x = node.x;
        int y = node.y;
        List<T> neighbors = new ArrayList<>();

        boolean n = false, s = false, e = false, w = false, ne = false, nw = false, se = false, sw = false;

        System.out.println("Graph checking neighbors");

        // ?
        if (isWalkable(x, y - 1)) {
            neighbors.add(getNode(x, y - 1));
            n = true;
        }
        // ?
        if (isWalkable(x + 1, y)) {
            neighbors.add(getNode(x + 1, y));
            e = true;
        }
        // ?
        if (isWalkable(x, y + 1)) {
            neighbors.add(getNode(x, y+1));
            s = true;
        }
        // ?
        if (isWalkable(x - 1, y)) {
            neighbors.add(getNode(x-1, y));
            w = true;
        }

        switch (diagonal) {
            case NEVER:
                return neighbors;
            case NO_OBSTACLES:
                ne = n && e;
                nw = n && w;
                se = s && e;
                sw = s && w;
                break;
            case ONE_OBSTACLE:
                ne = n || e;
                nw = n || w;
                se = s || e;
                sw = s || w;
                break;
            case ALWAYS:
                ne = nw = se = sw = true;
        }

        // ?
        if (nw && isWalkable(x - 1, y - 1)) {
            neighbors.add(getNode(x - 1, y - 1));
        }
        // ?
        if (ne && isWalkable(x + 1, y - 1)) {
            neighbors.add(getNode(x + 1, y - 1));
        }
        // ?
        if (se && isWalkable(x + 1, y + 1)) {
            neighbors.add(getNode(x + 1, y + 1));
        }
        // ?
        if (sw && isWalkable(x - 1, y + 1)) {
            neighbors.add(getNode(x - 1, y + 1));
        }

        return neighbors;
    }

    public boolean isWalkable(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < nodes.size() / width && getNode(x, y).walkable;
    }
}

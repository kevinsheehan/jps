package org.ksdev.jps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kevin
 */
public class JPSDiagNever<T extends Node> extends JPS<T> {
    public JPSDiagNever(Graph<T> graph) { super(graph); }

    @Override
    protected Collection<T> findNeighbors(T node) {
        List<T> neighbors = new ArrayList<>();
        Node parent = node.parent;

        // directed pruning: can ignore most neighbors, unless forced.
        if (parent != null) {
            final int x = node.x;
            final int y = node.y;
            // get normalized direction of travel
            final int dx = (x - parent.x) / Math.max(Math.abs(x - parent.x), 1);
            final int dy = (y - parent.y) / Math.max(Math.abs(y - parent.y), 1);

            // search horizontally/vertically
            if (dx != 0) {
                if (graph.isWalkable(x + dx, y))
                    neighbors.add(graph.getNode(x + dx, y));
                if (graph.isWalkable(x, y + 1))
                    neighbors.add(graph.getNode(x, y + 1));
                if (graph.isWalkable(x, y - 1))
                    neighbors.add(graph.getNode(x, y - 1));
            } else if (dy != 0) {
                if (graph.isWalkable(x, y + dy))
                    neighbors.add(graph.getNode(x, y + dy));
                if (graph.isWalkable(x + 1, y))
                    neighbors.add(graph.getNode(x + 1, y));
                if (graph.isWalkable(x - 1, y))
                    neighbors.add(graph.getNode(x - 1, y));
            }
        } else {
            // no parent, return all neighbors
            neighbors.addAll(graph.getNeighborsOf(node, Graph.Diagonal.NEVER));
        }

        return neighbors;
    }

    @Override
    protected T jump(T neighbor, T current, T goal) {
        if (neighbor == null || !neighbor.walkable) return null;
        if (neighbor.equals(goal)) return neighbor;

        int dx = neighbor.x - current.x;
        int dy = neighbor.y - current.y;

        // check for forced neighbors
        // check horizontally/vertically
        if (dx != 0) {
            if ((graph.isWalkable(neighbor.x, neighbor.y + 1) && !graph.isWalkable(neighbor.x - dx, neighbor.y + 1)) ||
                    (graph.isWalkable(neighbor.x, neighbor.y - 1) && !graph.isWalkable(neighbor.x - dx, neighbor.y - 1))) {
                return neighbor;
            }
        } else if (dy != 0) {
            if ((graph.isWalkable(neighbor.x + 1, neighbor.y) && !graph.isWalkable(neighbor.x + 1, neighbor.y - dy)) ||
                    (graph.isWalkable(neighbor.x - 1, neighbor.y) && !graph.isWalkable(neighbor.x - 1, neighbor.y - dy))) {
                return neighbor;
            }
            // when moving vertically check for horizontal jump points
            if (jump(graph.getNode(neighbor.x + 1, neighbor.y), neighbor, goal) != null ||
                    jump(graph.getNode(neighbor.x - 1, neighbor.y), neighbor, goal) != null) {
                return neighbor;
            }
        } else {
            return null;
        }

        return jump(graph.getNode(neighbor.x + dx, neighbor.y + dy), neighbor, goal);
    }
}

package org.ksdev.jps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kevin
 */
public class JPSDiagAlways<T extends Node> extends JPS<T> {
    public JPSDiagAlways(Graph<T> graph) {
        super(graph);
    }

    // todo: rewrite this method
    @Override
    protected Collection<T> findNeighbors(T node) {
        int x = node.x, y = node.y;
        Node parent = node.parent;
        int px, py, dx, dy;

        List<T> neighbors = new ArrayList<>();

        // directed pruning: can ignore most neighbors, unless forced.
        if (parent != null) {
            px = parent.x;
            py = parent.y;
            // get the normalized direction of travel
            dx = (x - px) / Math.max(Math.abs(x - px), 1);
            dy = (y - py) / Math.max(Math.abs(y - py), 1);

            // search diagonally
            if (dx != 0 && dy != 0) {
                if (graph.isWalkable(x, y + dy)) {
                    neighbors.add(graph.getNode(x, y + dy));
                }
                if (graph.isWalkable(x + dx, y)) {
                    neighbors.add(graph.getNode(x + dx, y));
                }
                if (graph.isWalkable(x + dx, y + dy)) {
                    neighbors.add(graph.getNode(x + dx, y + dy));
                }
                if (!graph.isWalkable(x - dx, y)) {
                    neighbors.add(graph.getNode(x - dx, y + dy));
                }
                if (!graph.isWalkable(x, y - dy)) {
                    neighbors.add(graph.getNode(x + dx, y - dy));
                }
            }
            // search horizontally/vertically
            else {
                if(dx == 0) {
                    if (graph.isWalkable(x, y + dy)) {
                        neighbors.add(graph.getNode(x, y + dy));
                    }
                    if (!graph.isWalkable(x + 1, y)) {
                        neighbors.add(graph.getNode(x + 1, y + dy));
                    }
                    if (!graph.isWalkable(x - 1, y)) {
                        neighbors.add(graph.getNode(x - 1, y + dy));
                    }
                }
                else {
                    if (graph.isWalkable(x + dx, y)) {
                        neighbors.add(graph.getNode(x + dx, y));
                    }
                    if (!graph.isWalkable(x, y + 1)) {
                        neighbors.add(graph.getNode(x + dx, y + 1));
                    }
                    if (!graph.isWalkable(x, y - 1)) {
                        neighbors.add(graph.getNode(x + dx, y - 1));
                    }
                }
            }
        } else {
            Collection<T> neighborNodes = graph.getNeighborsOf(node, Graph.Diagonal.ALWAYS);
            neighbors.addAll(neighborNodes);
        }

        return neighbors;
    }

    @Override
    protected T jump(T neighbor, T current, T goal) { return null; }
}

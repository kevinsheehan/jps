package org.ksdev.jps;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author Kevin
 */
public abstract class JPS<T extends Node> {
    protected final Graph<T> graph;

    public JPS(Graph<T> graph) {
        this.graph = graph;
    }

    public Future<Queue<T>> findPath(T start, T goal) {
        System.out.println("find path");
        FutureTask<Queue<T>> future = new FutureTask<>(() -> findPathSync(start, goal));
        future.run();
        return future;
    }

    public Queue<T> findPathSync(T start, T goal) {
        Queue<T> open = new PriorityQueue<>((a, b) -> {
            // we want the nodes with the lowest projected F value to be checked first
            return Double.compare(a.f, b.f);
        });

        // set the `g` and `f` value of the start node to be 0
        start.g = 0;
        start.f = 0;

        // push the start node into the open list
        open.add(start);
        start.opened = true;

        // while the open list is not empty
        while (!open.isEmpty()) {
            System.out.println("loop");
            // pop the position of node which has the minimum `f` value.
            T node = open.poll();
            // mark the current node as checked
            node.closed = true;

            if (node.equals(goal)) {
                return expandPath(backtrace(goal));
            }
            // add all possible next steps from the current node
            open.addAll(identifySuccessors(node, goal));
        }

        // failed to find a path
        return null;
    }

    /**
     * Identify successors for the given node. Runs a JPS in direction of each available neighbor, adding any open
     * nodes found to the open list.
     * @return All the nodes we have found jumpable from the current node
     */
    public List<T> identifySuccessors(T node, T goal) {
        List<T> opened = new ArrayList<>();
        // get all neighbors to the current node
        Collection<T> neighbors = findNeighbors(node);
        System.out.println("Neighbor count: " + neighbors.size());

        double d;
        double ng;
        for (T neighbor : neighbors) {
            // jump in the direction of our neighbor
            T jumpNode = jump(neighbor, node, goal);

            // don't add a node we have already gotten to quicker
            if (jumpNode == null || jumpNode.closed) continue;

            // determine the jumpNode's distance from the start along the current path
            d = graph.getDistance(jumpNode, node);
            ng = node.g + d;

            // if the node has already been opened and this is a shorter path, update it
            // if it hasn't been opened, mark as open and update it
            if (!jumpNode.opened || ng < jumpNode.g) {
                jumpNode.g = ng;
                jumpNode.h = graph.getHeuristicDistance(jumpNode, goal);
                jumpNode.f = jumpNode.g + jumpNode.h;
                jumpNode.parent = node;

                if (!jumpNode.opened) {
                    jumpNode.opened = true;
                    opened.add(jumpNode);
                }
            }
        }
        return opened;
    }

    /**
     * Find all neighbors for a given node. If node has a parent then prune neighbors based on JPS algorithm,
     * otherwise return all neighbors.
     */
    protected abstract Collection<T> findNeighbors(T node);

    /**
     * Search towards the child from the parent, returning when a jump point is found.
     */
    protected abstract T jump(T neighbor, T current, T goal);

    /**
     * Returns a path of the parent nodes from a given node.
     */
    private Queue<T> backtrace(T node) {
        LinkedList<T> path = new LinkedList<>();
        path.add(node);
        System.out.println("My node is: " + node.x + ", " + node.y);
        while(node.parent != null) {
            node = (T) node.parent;
            path.addFirst(node);
            System.out.println("My node is: " + node.x + ", " + node.y);
        }
        return path;
    }

    /**
     * Takes an abbreviated path and expands to a full path of connected nodes that link all the nodes
     * in the given path.
     */
    private Queue<T> expandPath(Queue<T> shortenedPath) {
        if (shortenedPath.size() < 2) return shortenedPath;
        LinkedList<T> path = new LinkedList<>();

        T cur, next;

        cur = shortenedPath.poll();
        int size = shortenedPath.size();
        for (int i = 0; i < size; i++) {
            next = shortenedPath.poll();

            Queue<T> interpolated = interpolate(cur, next);
            path.addAll(interpolated);

            cur = next;
        }
        path.add(cur);

        return path;
    }

    /**
     * Pushes all the nodes from start to end
     */
    private Queue<T> interpolate(T start, T end) {
        System.out.println("Interpolating from " + start.x + ", " + start.y + " to " + end.x + ", " + end.y);
        LinkedList<T> path = new LinkedList<>();

        int x = start.x;
        int y = start.y;
        int dx = Math.abs(end.x - start.x);
        int dy = Math.abs(end.y - start.y);
        int sx = start.x < end.x ? 1 : -1;
        int sy = start.y < end.y ? 1 : -1;

        int err = dx - dy;
        int e2;
        while(true) {
            if (x == end.x && y == end.y) break;

            path.add(graph.getNode(x, y));

            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x += sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y += sy;
            }
        }
        System.out.println("Done interpolating");
        return path;
    }

    public static class JPSFactory {
        public static <T extends Node> JPS<T> getJPS(Graph<T> graph, Graph.Diagonal diagonal) {
            switch (diagonal) {
                case ALWAYS:
                    return new JPSDiagAlways<>(graph);
                case NO_OBSTACLES:
                    return new JPSDiagNoObstacles<>(graph);
            }
            return null;
        }
    }
}

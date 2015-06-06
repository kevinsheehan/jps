package org.ksdev.jps;

import java.util.List;

/**
 * @author Kevin
 */
public class Grid<T extends Node> extends Graph<T> {
    public Grid(List<List<T>> map) {
        super(map);
    }

    @Override
    public double getDistance(Node a, Node b) {
        return Math.sqrt((a.x-b.x)^2 + (a.y - b.y)^2);
    }

    @Override
    public double getHeuristicDistance(Node a, Node b) {
        return Math.sqrt((a.x-b.x)^2 + (a.y-b.y)^2);
    }
}

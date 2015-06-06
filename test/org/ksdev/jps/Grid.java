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
        return Math.sqrt(Math.abs(a.x-b.x) + Math.abs(a.y - b.y));
    }

    @Override
    public double getHeuristicDistance(Node a, Node b) {
        return Math.sqrt(Math.abs(a.x-b.x) + Math.abs(a.y-b.y));
    }
}

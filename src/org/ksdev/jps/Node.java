package org.ksdev.jps;

/**
 * @author Kevin
 */
public abstract class Node {
    int x;
    int y;

    double f = 0; // distance to start + estimate to end
    double g = 0; // distance to start (parent's g + distance from parent)
    double h = 0; // estimate to end
    Node parent = null;

    boolean opened = false;
    boolean closed = false;

    public boolean walkable = true;
}

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

    boolean walkable = true;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setWalkable(boolean walkable) { this.walkable = walkable; }
    public boolean isWalkable() { return walkable; }
}

# Jump Point Search
Jump Point Search algorithm implemented in Java. This project is a port of the JPS algorithm implemented in the [PathFinding.js](https://github.com/qiao/PathFinding.js) project.

## Requirements
This library has been built using Java 8 up to this point. Making it compatible with other versions of Java is possible and likely.

## Usage
There are four different rules that pathing Diagonally can follow.
- Always
- One Obstacle
- No Obstacles
- Never

You can check out the code within the Test directory for examples on how to use this within your own projects. But in the end it more or less comes down to these three lines:
```java
        JPS<Tile> jps = JPS.JPSFactory.getJPS(new Grid<>(tileList), Graph.Diagonal.NO_OBSTACLES);
        Future<Queue<Tile>> futurePath = jps.findPath(start, end);
        Queue<Tile> path = futurePath.get();
```

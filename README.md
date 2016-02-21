# Jump Point Search
Jump Point Search algorithm implemented in Java. This project started as a port of the JPS algorithm implemented in the [PathFinding.js](https://github.com/qiao/PathFinding.js) project.

## Requirements
This library has been built using Java 8 up to this point. Making it compatible with other versions of Java is possible but at this point unlikely.

## Usage
There are four different rules that pathing Diagonally can follow.
- Always
- One Obstacle
- No Obstacles
- Never

You can check out the code within the Test directory for examples on how to use this within your own projects. But in the end it more or less comes down to these three lines:
```java
JPS<Tile> jps = JPS.JPSFactory.getJPS(new Graph<>(tileList), Graph.Diagonal.NO_OBSTACLES);
Future<Queue<Tile>> futurePath = jps.findPath(start, end);
Queue<Tile> path = futurePath.get();
```

### Distance and Heuristic
The code comes with four different distance algorithms already included but you are free to set your own as well.
The four you can choose from are: Manhattan, Euclidean, Octile, and Chebyshev.
```java
// Tile Map, Distance Calculation, Heuristic Calculation
new Graph<>(tiles, Graph.DistanceAlgo.MANHATTAN, Graph.DistanceAlgo.EUCLIDEAN);          
```

If you would like to set your own:
```java
BiFunction<Node, Node, Double> minDistance = (a, b) -> (double) Math.min(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
graph.setDistanceAlgo(minDistance);
graph.setHeuristicAlgo(minDistance);
```

### Adjacent Stop
When finding a path you can have the goal include adjacent nodes. If you want all of the goal node's neighbors to be considered good endpoints:
```java
Queue<Tile> futurePath = jps.findPathSync(start, end, true);
```

If you want to have neighbors be goal nodes but to exclude the diagonal ones:
```java
Queue<Tile> futurePath = jps.findPathSync(start, end, true, false);
```

## License
The MIT License (MIT)

Copyright (c) 2015 Kevin Sheehan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

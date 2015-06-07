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

## License
The MIT License (MIT)

Copyright (c) 2015 Kevin Sheehan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


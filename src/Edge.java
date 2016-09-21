import java.awt.Color;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//Class to represent an edge in the maze.
public class Edge {

    Vertex from;
    Vertex to;
    int weight;

    //Constructor for an edge, takes a from Vertex a to Vertex and a weight.
    Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    //EFFECT:Modifies this.to and this.from neighbors.
    //Sets this.to and this.from as neighbors.
    public void setNeighbors() {
        from.setNeighbor(to);
        to.setNeighbor(from);
    }

    //Returns an image of this edge.
    WorldImage drawEdge() {
        int xpos = (to.x - from.x);

        if (xpos == 0) {
            return new RectangleImage(Maze.CELL_SIZE - (Maze.CELL_SIZE
                    / 4), (Maze.CELL_SIZE * 2) - (Maze.CELL_SIZE / 4),
                    OutlineMode.SOLID, Color.gray);
        }
        else {
            return new RectangleImage((Maze.CELL_SIZE * 2)
                    - (Maze.CELL_SIZE / 4), Maze.CELL_SIZE
                            - (Maze.CELL_SIZE / 4), OutlineMode.SOLID,
                    Color.gray);
        }

    }

    //Returns an image of the path drawn in blue.
    WorldImage drawPathEdge() {
        int xpos = (to.x - from.x);

        if (xpos == 0) {
            return new RectangleImage(Maze.CELL_SIZE - (Maze.CELL_SIZE * 2
                    / 3), (Maze.CELL_SIZE * 2) - (Maze.CELL_SIZE * 2 / 3),
                    OutlineMode.SOLID, Color.blue);
        }
        else {
            return new RectangleImage((Maze.CELL_SIZE * 2)
                    - (Maze.CELL_SIZE * 2 / 3), Maze.CELL_SIZE
                            - (Maze.CELL_SIZE * 2 / 3), OutlineMode.SOLID,
                    Color.blue);
        }

    }

}

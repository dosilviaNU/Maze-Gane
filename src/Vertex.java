import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

//Class to represent a vertex on the maze.
public class Vertex {

    Vertex left;
    Vertex top;
    Vertex right;
    Vertex bottom;

    Boolean visited;

    Boolean breadCrumb;

    int x;
    int y;

    ArrayList<Edge> edges;

    //Constructor for a vertex. Takes anx and y grid coordinates.
    Vertex(int x, int y) {

        this.x = x;
        this.y = y;
        this.edges = new ArrayList<Edge>(4);

        this.visited = false;
        this.breadCrumb = false;

    }

    //Random number generator.
    Random rand = new Random();

    //EFFECT: Changes this vertex left, right, top, bottom neighbors.
    //Set this Vertexes neighbors.
    public void setNeighbors(Vertex left, Vertex top, Vertex right,
            Vertex bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    //EFFECT: Fills this vertexes list of edges.
    //Sets this Vertexes list of edges to its right/bottom neighbors.
    public void setEdges() {

        if (!this.right.equals(this)) {
            this.edges.add(new Edge(this, this.right, this.rand.nextInt(
                    500)));
        }
        if (!this.bottom.equals(this)) {
            this.edges.add(new Edge(this, this.bottom, this.rand.nextInt(
                    500)));
        }

    }

    //EFFECT:Modifies this.visited field.
    //Sets the Vertices visited field to true.
    void toggleVisited() {
        this.visited = true;
    }

    void notVisited() {
        this.visited = false;
    }

    void toggleBreadcrumb() {
        this.breadCrumb = true;
    }

    //Returns true if given object is equal to this vertex.
    public boolean equals(Object given) {
        if (!(given instanceof Vertex)) {
            return false;
        }
        else {
            Vertex that = (Vertex) given;
            return this.x == that.x && this.y == that.y;
        }

    }

    //EFFECT:Modifies neighbor fields.
    //Sets give to this.left, right, top or bottom
    //depending on givens x/y values relative to this.
    void setNeighbor(Vertex given) {

        if (this.x == given.x) {
            if (this.y > given.y) {
                this.top = given;

            }
            else {
                this.bottom = given;

            }
        }
        if (this.y == given.y) {
            if (this.x > given.x) {
                this.left = given;

            }
            else {
                this.right = given;

            }

        }

    }

    //EFFECT:Sets all neighbors to this.
    //Removes all neighbors from this.
    public void removeNeighbors() {
        this.left = this;
        this.top = this;
        this.right = this;
        this.bottom = this;
    }

    //Returns this objects hashCode.
    public int hashCode() {
        return this.x * 3253 + this.y * 10733;
    }

    //Returns an image of this vertex.
    public WorldImage drawVertex() {
        if (this.visited) {

            return new RectangleImage(Maze.CELL_SIZE - (Maze.CELL_SIZE * 2
                    / 3), Maze.CELL_SIZE - (Maze.CELL_SIZE * 2 / 3),
                    OutlineMode.SOLID, Color.cyan);

        }
        return new RectangleImage(Maze.CELL_SIZE - (Maze.CELL_SIZE * 2
                / 3), Maze.CELL_SIZE - (Maze.CELL_SIZE * 2 / 3),
                OutlineMode.SOLID, Color.gray);
    }

    public WorldImage drawBreadcrumb() {
        if (this.breadCrumb) {

            return new RectangleImage(Maze.CELL_SIZE - (Maze.CELL_SIZE * 2
                    / 3), Maze.CELL_SIZE - (Maze.CELL_SIZE * 2 / 3),
                    OutlineMode.SOLID, Color.yellow);

        }

        return new RectangleImage(Maze.CELL_SIZE - (Maze.CELL_SIZE * 2
                / 3), Maze.CELL_SIZE - (Maze.CELL_SIZE * 2 / 3),
                OutlineMode.SOLID, Color.gray);
    }

}

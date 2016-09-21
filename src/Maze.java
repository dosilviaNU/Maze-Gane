import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;
import javalib.worldimages.WorldImage;

//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

//I tried to get this all into one file, but WebCAT Went Feral on me and took all my points,
//so I switched it back to seperate files. Sorry.

//Class to represent the Maze world.
public class Maze extends World {

    //Constant representing this mazes width.
    static final int MAZE_WIDTH = 64;

    //Constant representing this mazes height.
    static final int MAZE_HEIGHT = Maze.MAZE_WIDTH * 3 / 4;

    //Constant representing total cell count.
    static final int HEIGHT_WIDTH = Maze.MAZE_HEIGHT * Maze.MAZE_WIDTH;

    //Constant representing this mazes cell size. 
    static final int CELL_SIZE = 10;

    //Constant representing the Big Bang window width.
    static final int BB_WIDTH = Maze.MAZE_WIDTH * Maze.CELL_SIZE;

    //Constant representing the big bang window height.
    static final int BB_HEIGHT = Maze.MAZE_HEIGHT * Maze.CELL_SIZE + 120;

    //Constant representing the column arrays size.
    static final int COL_ARRAY = Maze.MAZE_HEIGHT;

    //Constant representing the row arrays size.
    static final int ROW_ARRAY = Maze.MAZE_WIDTH;

    //Array list to hold the vertex.
    ArrayList<ArrayList<Vertex>> columns;

    //ArrayList to hold all the edges in the maze.
    ArrayList<Edge> edges;

    ArrayList<Vertex> vertices;

    //ArrayList to hold all the edges in the maze.
    ArrayList<Edge> workList;

    //Hashmap of Representatives.
    HashMap<Vertex, Vertex> reps;

    //Hash map to map visited nodes, to edges from them to unvisited nodes.
    HashMap<Vertex, Edge> cameFromEdge;

    //The starting point vertex, located at top left corner.
    Vertex start;

    //The finishing vertex, located at bottom right corner.
    Vertex finish;

    //A Deque containing the vertices to take to reach the finish from the start.
    ArrayList<Edge> path;

    //A stack used by the DFS algorithm.
    Stack<Vertex> dfWorklist;

    //A queue used by the BFS algorithm.
    Queue<Vertex> bfWorklist;

    //Represents the player in the maze.
    Player player;

    //Boolean flag to show solved path or not.
    Boolean viewPaths;

    //Players step count.
    int stepsTaken;

    //Constructor for the maze.
    Maze() {

        this.columns = new ArrayList<ArrayList<Vertex>>(Maze.ROW_ARRAY);
        setVertices();

        this.edges = new ArrayList<Edge>();

        this.workList = new ArrayList<Edge>();

        this.reps = new HashMap<Vertex, Vertex>(Maze.HEIGHT_WIDTH);

        this.path = new ArrayList<Edge>();

        this.vertices = new ArrayList<Vertex>();

        this.dfWorklist = new Stack<Vertex>();

        this.bfWorklist = new Queue<Vertex>();

        this.cameFromEdge = new HashMap<Vertex, Edge>();

        this.viewPaths = false;

        this.stepsTaken = 0;

        setVertices();
        gatherVertices();
        setWorkList();
        setHashMap();
        sortWorkList();
        //Set this to false to run algorithm without path compression.
        kruskalAlgo(true);
        setNeighbors();
        setPlayer();

    }

    //Dummy constructor for testing.
    Maze(int something) {

        this.columns = new ArrayList<ArrayList<Vertex>>(Maze.ROW_ARRAY);

        this.edges = new ArrayList<Edge>();

        this.workList = new ArrayList<Edge>();

        this.reps = new HashMap<Vertex, Vertex>(Maze.HEIGHT_WIDTH);

    }

    //EFFECT: Sets all collections to empty.
    //Reinitializes all data to the defaul values.
    void initData() {
        this.columns = new ArrayList<ArrayList<Vertex>>(Maze.ROW_ARRAY);
        setVertices();

        this.edges = new ArrayList<Edge>();

        this.workList = new ArrayList<Edge>();

        this.reps = new HashMap<Vertex, Vertex>(Maze.HEIGHT_WIDTH);

        this.path = new ArrayList<Edge>();

    }

    //EFFECT: Fills this.vertices, with default vertices.   
    //Creates all the vertices in the maze.
    void setVertices() {

        for (int i = 0; i < Maze.ROW_ARRAY; i++) {
            ArrayList<Vertex> temparray = new ArrayList<Vertex>(
                    Maze.COL_ARRAY);
            for (int j = 0; j < Maze.COL_ARRAY; j++) {

                temparray.add(j, new Vertex(calcGridCoord(i),
                        calcGridCoord(j)));
            }
            if (i == 0) {

                this.start = temparray.get(0);
            }
            if (i == Maze.ROW_ARRAY - 1) {
                this.finish = temparray.get(Maze.COL_ARRAY - 1);
            }
            this.columns.add(i, temparray);
        }
    }

    //EFFECT:Places the player at this.start.
    //Creates and places a player at this.start.
    void setPlayer() {
        this.player = new Player(this.start);
    }

    //Calculates grid position based off of indices.
    int calcGridCoord(int index) {
        return (index * Maze.CELL_SIZE) + Maze.CELL_SIZE / 2;
    }

    //EFFECT: Sets all the neighbors for all vertices in the maze.
    //EFFECT: Sets the edges for all the vertices in the maze.
    //EFFECT: Fills this.worklist with edges. 
    //Creates the worklist to be used by Kruskal's Algorithm.
    void setWorkList() {
        for (int i = 0; i < Maze.ROW_ARRAY; i++) {

            ArrayList<Vertex> prev = new ArrayList<Vertex>(Maze.COL_ARRAY);
            ArrayList<Vertex> curr = new ArrayList<Vertex>(Maze.COL_ARRAY);
            ArrayList<Vertex> next = new ArrayList<Vertex>(Maze.COL_ARRAY);

            if (i == 0) {
                curr = this.columns.get(i);
                next = this.columns.get(i + 1);
                for (int j = 0; j < Maze.COL_ARRAY; j++) {
                    if (j == 0) {
                        curr.get(j).setNeighbors(curr.get(j), curr.get(j),
                                next.get(j), curr.get(j + 1));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                    else if (j == curr.size() - 1) {
                        curr.get(j).setNeighbors(curr.get(j), curr.get(j
                                - 1), next.get(j), curr.get(j));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                    else {
                        curr.get(j).setNeighbors(curr.get(j), curr.get(j
                                - 1), next.get(j), curr.get(j + 1));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                }
            }

            else if (i == Maze.ROW_ARRAY - 1) {

                prev = this.columns.get(i - 1);
                curr = this.columns.get(i);

                for (int j = 0; j < Maze.COL_ARRAY; j++) {
                    if (j == 0) {
                        curr.get(j).setNeighbors(prev.get(j), curr.get(j),
                                curr.get(j), curr.get(j + 1));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                    else if (j == curr.size() - 1) {
                        curr.get(j).setNeighbors(prev.get(j), curr.get(j
                                - 1), curr.get(j), curr.get(j));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                    else {
                        curr.get(j).setNeighbors(prev.get(j), curr.get(j
                                - 1), curr.get(j), curr.get(j + 1));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                }
            }
            else {
                prev = this.columns.get(i - 1);
                curr = this.columns.get(i);
                next = this.columns.get(i + 1);

                for (int j = 0; j < Maze.COL_ARRAY; j++) {
                    if (j == 0) {
                        curr.get(j).setNeighbors(prev.get(j), curr.get(j),
                                next.get(j), curr.get(j + 1));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                    else if (j == Maze.COL_ARRAY - 1) {
                        curr.get(j).setNeighbors(prev.get(j), curr.get(j
                                - 1), next.get(j), curr.get(j));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                    else {
                        curr.get(j).setNeighbors(prev.get(j), curr.get(j
                                - 1), next.get(j), curr.get(j + 1));
                        curr.get(j).setEdges();
                        curr.get(j).removeNeighbors();
                        this.workList.addAll(curr.get(j).edges);
                    }
                }

            }
        }

    }

    //EFFECT: Filled this.vertices.
    //Consolidates this.colums into one arraylist.
    void gatherVertices() {
        for (int i = 0; i < Maze.ROW_ARRAY; i++) {
            this.vertices.addAll(this.columns.get(i));
        }

    }

    //EFFECT: Fills the representative<Edge, Edge> hashmap.
    //Fill the hash map to be used by the Kruskal algorithm.
    void setHashMap() {

        for (int i = 0; i < Maze.ROW_ARRAY; i++) {
            ArrayList<Vertex> temparray = this.columns.get(i);
            for (Vertex vert : temparray) {
                this.reps.put(vert, vert);
            }
        }

    }

    //EFFECT: Sorts this.worklist.
    //Sorts the worklist from smallest weighted edge, to the highest weighted edge.
    void sortWorkList() {

        Collections.sort(this.workList, new CompareEdge());
        //this.workList.sort(new CompareEdge());
    }

    //EFFECT: Calls the pathCompression method.
    //Recursive function that follows the links from given vertex to 
    //that vertices representative. If Vertex links to itself, returns 
    //self.
    Vertex findRep(Vertex mod, Vertex orig, boolean pathCompression) {

        if (this.reps.get(mod).equals(mod)) {
            if (pathCompression) {
                pathCompression(orig, mod);
            }
            return mod;

        }
        else {
            return findRep(this.reps.get(mod), orig, pathCompression);
        }
    }

    //EFFECT: Performs path compression on this.reps.
    //Recursively follows the path from original Vertex passed to
    //findRep, and sets all the links along the way to the Vertex returned
    //by findRep.
    void pathCompression(Vertex original, Vertex found) {

        if (this.reps.get(original).equals(original)) {
            //Do nothing.
        }
        else {
            this.reps.put(this.reps.get(original), found);
            pathCompression(this.reps.get(original), found);
        }
    }

    //!this.workList.isEmpty()
    //this.edges.size() < Maze.HEIGHT_WIDTH
    //EFFECT:Generates the maze.
    //Performs Kruskal's algorithm on the list of edges in the maze.
    //Links all the edges, discards edges that would create cycles.
    void kruskalAlgo(boolean pathCompression) {

        while (!this.workList.isEmpty()) {

            Edge edge = this.workList.get(0);
            Vertex from = edge.from;
            Vertex to = edge.to;

            Vertex fromRep = findRep(from, from, pathCompression);
            Vertex toRep = findRep(to, to, pathCompression);

            if (fromRep.equals(toRep)) {
                this.workList.remove(0);

            }
            else {

                this.edges.add(this.workList.remove(0));

                this.reps.put(toRep, from);

            }

        }

    }

    //EFFECT:Modifies the neighbor values for all vertices in this.edges.
    //Sets all the neighbors in this.edges.
    void setNeighbors() {
        for (Edge e : this.edges) {
            e.setNeighbors();
        }
    }

    //EFFECT:Fills this.path with edges.
    //Reconstructs the path the DFS/BFS searches took to this.finish.
    ArrayList<Edge> reconstruct(Vertex current) {

        while (!current.equals(this.player.loc)) {

            Edge edge = this.cameFromEdge.get(current);

            this.path.add(edge);
            current = edge.from;
            if (current.equals(this.player.loc)) {
                return this.path;
            }
        }
        return this.path;

    }

    void resetVisited() {
        for (Vertex v : this.vertices) {
            v.notVisited();
        }
    }

    //EFECT:Fills this.cameFromEdge.
    //Solves the maze using depth first search.
    ArrayList<Edge> depthFirst() {

        if (!this.dfWorklist.isEmpty()) {

            Vertex next = this.dfWorklist.pop();
            if (next.equals(this.finish)) {
                this.dfWorklist = new Stack<Vertex>();
                resetVisited();
                return reconstruct(next);
            }

            else if (next.visited) {
                //Do nothing.
            }
            else {

                if (!next.equals(next.left)) {
                    this.dfWorklist.push(next.left);
                    if (!this.cameFromEdge.containsKey(next.left)) {
                        this.cameFromEdge.put(next.left, new Edge(next,
                                next.left, 0));
                    }
                }
                if (!next.equals(next.top)) {
                    this.dfWorklist.push(next.top);
                    if (!this.cameFromEdge.containsKey(next.top)) {
                        this.cameFromEdge.put(next.top, new Edge(next,
                                next.top, 0));
                    }
                }
                if (!next.equals(next.right)) {
                    this.dfWorklist.push(next.right);
                    if (!this.cameFromEdge.containsKey(next.right)) {
                        this.cameFromEdge.put(next.right, new Edge(next,
                                next.right, 0));
                    }
                }
                if (!next.equals(next.bottom)) {
                    this.dfWorklist.push(next.bottom);
                    if (!this.cameFromEdge.containsKey(next.bottom)) {
                        this.cameFromEdge.put(next.bottom, new Edge(next,
                                next.bottom, 0));
                    }
                }

            }
            next.toggleVisited();
        }
        return new ArrayList<Edge>();
    }

    //EFECT:Fills this.cameFromEdge.
    //Solves the maze using breadth first search.
    ArrayList<Edge> breadthFirst() {

        if (!this.bfWorklist.isEmpty()) {

            Vertex next = this.bfWorklist.dequeue();
            if (next.visited) {
                //Do nothing.
            }

            else if (next.equals(this.finish)) {

                this.bfWorklist = new Queue<Vertex>();
                resetVisited();
                return reconstruct(next);
            }
            else {

                if (!next.equals(next.left)) {
                    this.bfWorklist.enqueue(next.left);
                    if (!this.cameFromEdge.containsKey(next.left)) {
                        this.cameFromEdge.put(next.left, new Edge(next,
                                next.left, 0));
                    }
                }
                if (!next.equals(next.top)) {
                    this.bfWorklist.enqueue(next.top);
                    if (!this.cameFromEdge.containsKey(next.top)) {
                        this.cameFromEdge.put(next.top, new Edge(next,
                                next.top, 0));
                    }
                }
                if (!next.equals(next.right)) {
                    this.bfWorklist.enqueue(next.right);
                    if (!this.cameFromEdge.containsKey(next.right)) {
                        this.cameFromEdge.put(next.right, new Edge(next,
                                next.right, 0));
                    }
                }
                if (!next.equals(next.bottom)) {
                    this.bfWorklist.enqueue(next.bottom);
                    if (!this.cameFromEdge.containsKey(next.bottom)) {
                        this.cameFromEdge.put(next.bottom, new Edge(next,
                                next.bottom, 0));
                    }
                }

                next.toggleVisited();

            }

        }
        return new ArrayList<Edge>();
    }

    //WorldEnd function ends the game when theGameIsOver function returns true.
    public WorldEnd worldEnds() {
        if (this.player.loc.equals(this.finish)) {
            return new WorldEnd(true, this.winScene());
        }
        else {
            return new WorldEnd(false, this.makeScene());
        }
    }

    public WorldScene winScene() {
        WorldScene winScene = this.getEmptyScene();
        winScene.placeImageXY(this.winMessage(), Maze.BB_WIDTH / 2,
                Maze.BB_HEIGHT / 2);
        return winScene;

    }

    //The image of Game Over to be displayed in the final scene.
    public WorldImage winMessage() {
        return new TextImage("You Win", 50, new Color(255, 0, 0));
    }

    //Returns the image of the Maze world.
    public WorldScene makeScene() {
        WorldScene scene = this.getEmptyScene();

        scene.placeImageXY(new RectangleImage(Maze.BB_WIDTH, Maze.BB_HEIGHT
                + 100, OutlineMode.SOLID, Color.black), Maze.BB_WIDTH / 2,
                Maze.BB_HEIGHT / 2);

        for (Edge edge : this.edges) {
            int xpos = (edge.to.x + edge.from.x) / 2;
            int ypos = (edge.to.y + edge.from.y) / 2;
            scene.placeImageXY(edge.drawEdge(), xpos, ypos);

        }
        for (Vertex v : this.vertices) {
            scene.placeImageXY(v.drawBreadcrumb(), v.x, v.y);
        }

        if (this.viewPaths) {
            for (Vertex v : this.vertices) {
                scene.placeImageXY(v.drawVertex(), v.x, v.y);
            }
        }

        if (this.viewPaths) {
            for (Edge edge : this.path) {
                int xpos = (edge.to.x + edge.from.x) / 2;
                int ypos = (edge.to.y + edge.from.y) / 2;
                scene.placeImageXY(edge.drawPathEdge(), xpos, ypos);
            }

        }

        scene.placeImageXY(new RectangleImage(Maze.CELL_SIZE
                - (Maze.CELL_SIZE / 4), Maze.CELL_SIZE - (Maze.CELL_SIZE
                        / 4), OutlineMode.SOLID, Color.green),
                this.start.x, this.start.y);
        scene.placeImageXY(new RectangleImage(Maze.CELL_SIZE
                - (Maze.CELL_SIZE / 4), Maze.CELL_SIZE - (Maze.CELL_SIZE
                        / 4), OutlineMode.SOLID, Color.red), this.finish.x,
                this.finish.y);

        scene.placeImageXY(this.player.drawPlayer(), this.player.playerX(),
                this.player.playerY());

        scene.placeImageXY(new TextImage("Maze Game", 25, new Color(255, 0,
                0)), Maze.BB_WIDTH / 2, Maze.BB_HEIGHT - 95);
        scene.placeImageXY(new TextImage("Arrow Keys: Control Player", 15,
                new Color(255, 0, 0)), Maze.BB_WIDTH / 4, Maze.BB_HEIGHT
                        - 75);
        scene.placeImageXY(new TextImage("N: New Maze", 15, new Color(255,
                0, 0)), Maze.BB_WIDTH / 4, Maze.BB_HEIGHT - 60);
        scene.placeImageXY(new TextImage("R: Reset this Maze", 15,
                new Color(255, 0, 0)), Maze.BB_WIDTH / 4, Maze.BB_HEIGHT
                        - 45);
        scene.placeImageXY(new TextImage("B: BFS Solution D: DFS Solution",
                15, new Color(255, 0, 0)), Maze.BB_WIDTH / 4,
                Maze.BB_HEIGHT - 30);
        scene.placeImageXY(new TextImage("P: Toggle Solved Path", 15,
                new Color(255, 0, 0)), Maze.BB_WIDTH / 4, Maze.BB_HEIGHT
                        - 15);
        scene.placeImageXY(new TextImage("Player Step Count: "
                + this.stepsTaken, 15, new Color(255, 0, 0)), Maze.BB_WIDTH
                        * 3 / 4, Maze.BB_HEIGHT - 60);
        return scene;

    }

    //EFFECT: Updates world state.
    //Big Bang onTick handler.
    public void onTick() {
        if (!this.dfWorklist.isEmpty()) {
            this.path = depthFirst();
        }
        if (!this.bfWorklist.isEmpty()) {
            this.path = breadthFirst();
        }
    }

    //EFFECT:Moves the player or performs DFS/BFS dependign on key inputs.
    //BigBang on key handler.
    public void onKeyEvent(String ke) {
        if (ke.equals("d")) {
            startOver(this);
            this.viewPaths = true;
            this.dfWorklist.push(this.player.loc);
        }
        if (ke.equals("b")) {
            startOver(this);
            this.viewPaths = true;
            this.bfWorklist.enqueue(this.player.loc);
        }

        if (ke.equals("r")) {
            startOver(this);
        }

        if (ke.equals("n")) {
            newMaze(this);
        }

        if (ke.equals("up")) {

            this.player.moveUp();
            this.stepsTaken = this.stepsTaken + 1;
        }
        if (ke.equals("down")) {

            this.player.moveDown();
            this.stepsTaken = this.stepsTaken + 1;
        }
        if (ke.equals("left")) {
            this.player.moveLeft();
            this.stepsTaken = this.stepsTaken + 1;
        }
        if (ke.equals("right")) {

            this.player.moveRight();
            this.stepsTaken = this.stepsTaken + 1;
        }

        if (ke.equals("p")) {
            if (this.viewPaths) {
                this.viewPaths = false;
                newScene(this);
            }
            else {
                this.viewPaths = true;
                newScene(this);
            }
        }

    }

    //EFFECT: Returns a newly drawn maze.
    //Creates a new scene.
    void newScene(Maze w) {
        w.makeScene();
    }

    //EFFECT:Resets and creates a new maze.
    //Creates a new maze.
    void newMaze(Maze w) {
        w.initData();
        w.columns = new ArrayList<ArrayList<Vertex>>(Maze.ROW_ARRAY);
        setVertices();
        w.edges = new ArrayList<Edge>();
        w.workList = new ArrayList<Edge>();
        w.reps = new HashMap<Vertex, Vertex>(Maze.HEIGHT_WIDTH);
        w.path = new ArrayList<Edge>();
        w.vertices = new ArrayList<Vertex>();
        w.stepsTaken = 0;
        this.dfWorklist = new Stack<Vertex>();
        this.bfWorklist = new Queue<Vertex>();
        this.cameFromEdge = new HashMap<Vertex, Edge>();
        w.setVertices();
        w.gatherVertices();
        w.setWorkList();
        w.setHashMap();
        w.sortWorkList();
        //Set this to false to run algorithm without path compression.
        w.kruskalAlgo(true);
        w.setNeighbors();
        w.setPlayer();

    }

    //EFFECT: Resets this maze.
    //Resets current maze.
    void startOver(Maze w) {
        w.resetVisited();
        w.path = new ArrayList<Edge>();
        w.bfWorklist = new Queue<Vertex>();
        w.dfWorklist = new Stack<Vertex>();
    }

    //EFFECT: Starts bigBang for a Maze world.
    //Start the maze.
    public static void main(String[] argv) {

        //run the game
        Maze w = new Maze();

        w.bigBang(Maze.BB_WIDTH, Maze.BB_HEIGHT, .001);

    }

}

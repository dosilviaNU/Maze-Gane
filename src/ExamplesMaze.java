import tester.Tester;

//Assignment 10
//Silvia, David
//dosilvia
//Donovan, Joe
//jdonovan

public class ExamplesMaze {

    //Example Vertices.
    Vertex first = new Vertex(50, 50);
    Vertex firstclone = new Vertex(50, 50);
    Vertex second = new Vertex(55, 50);
    Vertex third = new Vertex(50, 45);
    Vertex fourth = new Vertex(50, 55);
    Vertex fifth = new Vertex(45, 50);

    //EFFECT:Initializes vertices to default.
    void initVertices() {
        this.first = new Vertex(50, 50);
        this.firstclone = new Vertex(50, 50);
        this.second = new Vertex(55, 50);
        this.third = new Vertex(50, 45);
        this.fourth = new Vertex(50, 55);
        this.fifth = new Vertex(45, 50);
    }

    //Tests for Vertices.
    void testSetNeighbors(Tester t) {
        first.setNeighbors(second, third, fourth, fifth);
        t.checkExpect(first.left.equals(second), true);
        t.checkExpect(first.top.equals(third), true);
        t.checkExpect(first.right.equals(fourth), true);
        t.checkExpect(first.bottom.equals(fifth), true);
        t.checkExpect(first.bottom.equals(fourth), false);
    }

    //Tests for set edges.
    void testSetEdges(Tester t) {
        initVertices();
        first.setNeighbors(second, third, fourth, fifth);

        //Check that first.edges is empty.
        t.checkExpect(first.edges.size(), 0);
        first.setEdges();

        //Check that both edges right and bottom were set.
        t.checkExpect(first.edges.size(), 2);
        initVertices();

        first.setNeighbors(second, third, first, fifth);
        first.setEdges();
        //Check that right edge was ignored because it linked to itself.
        t.checkExpect(first.edges.size(), 1);

    }

    //Tests for .equals
    void testVertexEquals(Tester t) {
        t.checkExpect(this.first.equals(fifth), false);
        t.checkExpect(this.first.equals(first), true);
    }

    //Tests for Maze.
    Maze testmaze = new Maze(2);

    void initTestMaze() {
        this.testmaze = new Maze(2);
    }

    //Tests for setVertices.
    void testSetVertices(Tester t) {
        initTestMaze();
        t.checkExpect(testmaze.columns.isEmpty(), true);
        testmaze.setVertices();
        t.checkExpect(testmaze.columns.size(), Maze.MAZE_WIDTH);

    }

    //Tests for calc grid coords.
    void testCalcGrid(Tester t) {
        t.checkExpect(testmaze.calcGridCoord(6), 6 * Maze.CELL_SIZE
                + (Maze.CELL_SIZE / 2));
        t.checkExpect(testmaze.calcGridCoord(8), 8 * Maze.CELL_SIZE
                + (Maze.CELL_SIZE / 2));

    }

    //Test for setWorkList
    void testSetWorkList(Tester t) {
        initTestMaze();
        testmaze.setVertices();
        t.checkExpect(testmaze.workList.size(), 0);
        testmaze.setWorkList();
        t.checkExpect(testmaze.workList.size(), (Maze.HEIGHT_WIDTH * 2)
                - (Maze.MAZE_HEIGHT + Maze.MAZE_WIDTH));

    }

    //Test set hashMap.
    void testSetHashMap(Tester t) {
        initTestMaze();
        testmaze.setVertices();
        testmaze.setWorkList();
        t.checkExpect(testmaze.reps.size(), 0);

        testmaze.setHashMap();
        t.checkExpect(testmaze.reps.size(), Maze.HEIGHT_WIDTH);

    }

    //Tests for Algorithm and Path compression.
    void testKruskalPathCompression(Tester t) {
        initTestMaze();
        testmaze.setVertices();
        testmaze.setWorkList();
        testmaze.setHashMap();

        t.checkExpect(testmaze.edges.size(), 0);

        testmaze.kruskalAlgo(true);

        //t.checkExpect(testmaze.hasPathbetween(), true);

        t.checkExpect(testmaze.edges.size(), Maze.HEIGHT_WIDTH - 1);

        initTestMaze();
        testmaze.setVertices();
        testmaze.setWorkList();
        testmaze.setHashMap();

        //Timing without path compression.
        long startWithoutPC = System.currentTimeMillis();
        testmaze.kruskalAlgo(false);
        long endWithoutPC = System.currentTimeMillis();
        long withoutPC = endWithoutPC - startWithoutPC;

        initTestMaze();
        testmaze.setVertices();
        testmaze.setWorkList();
        testmaze.setHashMap();

        //Timing with path compression.
        long startWithPC = System.currentTimeMillis();
        testmaze.kruskalAlgo(true);
        long endWithPC = System.currentTimeMillis();
        long withPC = endWithPC - startWithPC;

        //True if withPC was faster than withoutPC.
        t.checkExpect(withPC < withoutPC, true);

    }

}

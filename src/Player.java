import java.awt.Color;

import javalib.worldimages.CircleImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.WorldImage;

//Class to represent the player.
public class Player {
    Vertex loc;

    //Constructor for Player.
    Player(Vertex loc) {
        this.loc = loc;

    }

    //Returns an image of this player.
    WorldImage drawPlayer() {
        return new CircleImage(Maze.CELL_SIZE / 2 - Maze.CELL_SIZE / 5,
                OutlineMode.SOLID, Color.MAGENTA);
    }

    //EFFECT: Modifies this.loc.
    //Sets this.loc to this.locs top neighbor.
    void moveUp() {
        this.loc = loc.top;
        this.loc.toggleBreadcrumb();

    }

    //EFFECT: Modifies this.loc.
    //Sets this.loc to this.locs bottom neighbor.
    void moveDown() {
        this.loc = loc.bottom;
        this.loc.toggleBreadcrumb();

    }

    //EFFECT: Modifies this.loc.
    //Sets this.loc to this.locs left neighbor.
    void moveLeft() {
        this.loc = loc.left;
        this.loc.toggleBreadcrumb();

    }

    //EFFECT: Modifies this.loc.
    //Sets this.loc to this.locs right neighbor.
    void moveRight() {
        this.loc = loc.right;
        this.loc.toggleBreadcrumb();

    }

    //Returns this players x position.
    int playerX() {
        return loc.x;
    }

    //Returns this players y position.
    int playerY() {
        return loc.y;
    }

}

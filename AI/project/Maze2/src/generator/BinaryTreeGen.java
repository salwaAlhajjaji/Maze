package generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import main.Maze;
import main.MazeGridPanel;
import util.Cell;

public class BinaryTreeGen {

    private final List<Cell> grid;
    private Cell current;
    private int index;
    private final Random r = new Random();

    public BinaryTreeGen(List<Cell> grid, MazeGridPanel panel) {
        this.grid = grid;
        index = grid.size() - 1;//the goal
        current = grid.get(index);//start draw from last cell
        final Timer timer = new Timer(Maze.speed, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!grid.parallelStream().allMatch(c -> c.isVisited())) {//checks if calling stream totally matches to given Predicate
                    carve();
                } else {
                    current = null;
                    Maze.generated = true;
                    timer.stop();
                }
                panel.setCurrent(current);
                panel.repaint();//draw the maze
                timer.setDelay(Maze.speed);
            }
        });
        timer.start();
    }

    public BinaryTreeGen(MazeGridPanel grid, Maze aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void carve() {
        boolean topNeigh = grid.contains(new Cell(current.getX(), current.getY() - 1));//cheack if cell has a top neighbor
        boolean leftNeigh = grid.contains(new Cell(current.getX() - 1, current.getY()));//cheack if cell has a left neighbor
        if (topNeigh && leftNeigh) {//check if ti has top and left neighbor
            carveDirection(r.nextInt(2));//r.nextInt(2) will retrun a integer random number with bound of 2
        } else if (topNeigh) {// only has a top nieghober
            carveDirection(0);
        } else if (leftNeigh) {// only has a left neighbore
            carveDirection(1);
        }

        current.setVisited(true);

        if (index - 1 >= 0) {
            current = grid.get(--index);
        }
        // else we break out of if statement in timer Action Listener and set maze to generated.

    }

   
    private void carveDirection(int dir) {
        if (dir == 0) {//top
            List<Cell> neighs = current.getAllNeighbours(grid);
            for (Cell c : neighs) {
                if (c.getY() + 1 == current.getY()) {
                    current.removeWalls(c);
                }
            }
        } else {//left 
            List<Cell> neighs = current.getAllNeighbours(grid);
            for (Cell c : neighs) {
                if (c.getX() + 1 == current.getX()) {
                    current.removeWalls(c);
                }
            }
        }
    }
}

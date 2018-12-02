package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class BFSSolve {

    private final Queue<Cell> queue = new LinkedList<Cell>(); //use linkedlist to get pointer for next cell
    private Cell current;
    private final List<Cell> grid;

    public BFSSolve(List<Cell> grid, MazeGridPanel panel) { //constructor of BFSSolve
        this.grid = grid;
        current = grid.get(0); //first cell == strat point
        current.setDistance(0);

        queue.offer(current);//insert current cell into queue return false if the inserting violate queue capacity.
        final Timer timer = new Timer(Maze.speed, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!current.equals(grid.get(grid.size() - 1))) {//current cell not equal the goal cell(24,24)
                    flood();// this is the dead end
                } else {// find the goal cell
                    drawPath();
                    Maze.solved = true;
                    timer.stop();
                }
                panel.setCurrent(current);
                panel.repaint();//to draw 
                
            }
        });
        timer.start();
    }

    private void flood() {
        current.setDeadEnd(true);//mark it visited
        current = queue.poll(); //update current value by next cell
            //poll() This method returns the element at the front of the Queue and remove it. It returns null when the Queue is empty
        List<Cell> adjacentCells = current.getValidMoveNeighbours(grid);//list of adjacent cells
        for (Cell c : adjacentCells) {
            if (c.getDistance() == -1) {//check if not visited
                c.setDistance(current.getDistance() + 1);//make adjcent cell list is visited by make its distance (-1+1)=0
                c.setParent(current);//make adjcent cell => child of parent current
                queue.offer(c);//add this cell to queue
            }
        }
    }

    private void drawPath() {
        while (current != grid.get(0)) {
            current.setPath(true);
            current = current.getParent();
        }
    }
}

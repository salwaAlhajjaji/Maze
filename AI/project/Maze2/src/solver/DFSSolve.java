package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

import javax.swing.Timer;

import main.*;
import util.Cell;

// Basically a greedy dijkstra's that follows a path until it hits a dead end instead of prioritising the 
// closest cell to the goal.

public class DFSSolve {

	private final Stack<Cell> path = new Stack<Cell>();
	private Cell current;
	private final List<Cell> grid;

	public DFSSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		current = grid.get(0);// initial state
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!current.equals(grid.get(grid.size() - 1))) {// current != goal if you didnt reach the goal yellow cell then go to path() method
					path();
				} else {// current == goal
					drawPath();//set the path
					Maze.solved = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();//draw the path
				
			}
		});
		timer.start();
	}

	private void path() {
		current.setDeadEnd(true);
		Cell next = current.getPathNeighbour(grid);
		if (next != null) {
			path.push(current); //add current to the path
			current = next; //and put the nighbour as a current node
		} else if (!path.isEmpty()) {//dead-end state: if nighbour is visited then pop it out to backtrack to visit another node
			try {
				current = path.pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void drawPath() {
		while (!path.isEmpty()) { // while path isn't empty then pop every node in path and draw its path on the grid
			try {
				path.pop().setPath(true); // draw the path of slotion with blue color
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
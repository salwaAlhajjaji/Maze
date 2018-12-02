package solver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.Timer;

import main.*;
import util.Cell;

public class DijkstraSolve {
	
	private final Queue<Cell> queue;
	private Cell current;
	private final List<Cell> grid;

	public DijkstraSolve(List<Cell> grid, MazeGridPanel panel) {
		this.grid = grid;
		queue = new PriorityQueue<Cell>(new CellDistanceFromGoalComparator());//creat the queue using compare class
		current = grid.get(0);
		current.setDistance(0);
		queue.offer(current);// add it to the queue
		final Timer timer = new Timer(Maze.speed, null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!current.equals(grid.get(grid.size() - 1))) {//if cureent != goal
					flood();
				} else {// if currnt == goal
					drawPath();
					Maze.solved = true;
					timer.stop();
				}
				panel.setCurrent(current);
				panel.repaint();//draw the path
				
			}
		});
		timer.start();
	}
	
	private void flood() {
		current.setDeadEnd(true);
		current = queue.poll();// remove it, and make next cell as current cell
		List<Cell> adjacentCells = current.getValidMoveNeighbours(grid);
		for (Cell c : adjacentCells) {
			if (c.getDistance() == -1) {// not viseted 
				c.setDistance(current.getDistance() + 1);// visited , -1+1=0
				c.setParent(current);
				queue.offer(c);// add to queue
			}
		}
	}
	
	private void drawPath() {
		while (current != grid.get(0)) {//if current != initial state
			current.setPath(true);
			current = current.getParent();
		}
	}
	
	private class CellDistanceFromGoalComparator implements Comparator<Cell> {// used to compare distance of cell to give the hight priorty to min cell
		Cell goal = grid.get(grid.size() - 1);
		
		@Override
		public int compare(Cell arg0, Cell arg1) {//comparation 
			if (getDistanceFromGoal(arg0) > getDistanceFromGoal(arg1)) {
				return 1;
			} else {
				return getDistanceFromGoal(arg0) < getDistanceFromGoal(arg1) ? -1 : 0;
			}
		}
		
		private double getDistanceFromGoal(Cell c) {// used to check the distance from goal to the current cell
                    // hypot will return sqrt( x2 + y2) cuse dijkstra can't handel nigative valus
			return Math.hypot(c.getX() - goal.getX(), c.getY() - goal.getY());
		}
	}
}
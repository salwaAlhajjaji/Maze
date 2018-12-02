package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import generator.*;
import solver.*;
import util.Cell;

public class MazeGridPanel extends JPanel {

	private final List<Cell> grid = new ArrayList<Cell>();
	private List<Cell> currentCells = new ArrayList<Cell>();

	public MazeGridPanel(int rows, int cols) {//creat the cells
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				grid.add(new Cell(x, y));//add the cells to grid list as X & Y point
			}
		}
	}

	@Override
        /*The Dimension class encapsulates the width and height of a component (in integer precision) in a single
                object. The class is associated with certain properties of components. 
                Several methods defined by the Component class and the LayoutManager interface return a Dimension object.*/
	public Dimension getPreferredSize() {
		// +1 pixel on width and height so bottom and right borders can be drawn.
		return new Dimension(Maze.WIDTH +1, Maze.HEIGHT+1);
	}

	public void generate(int index) {
		// switch statement for gen method read from combobox in Maze.java
		switch (index) {
		case 0:
                        new BinaryTreeGen(grid, this);
			break;
		}
	}

	public void solve(int index) {
		switch (index) {
		case 0:
			new BFSSolve(grid, this);
			break;
		case 1:
                    	new DFSSolve(grid, this);
			break;
		case 2: 
                    	new DijkstraSolve(grid, this);
			break;
		default:
			new DijkstraSolve(grid, this);
			break;
		}
	}
	
	public void resetSolution() {//for reset the maze
		for (Cell c : grid) {
			c.setDeadEnd(false);//make it umarked
			c.setPath(false);//remove it from path set
			c.setDistance(-1);//set it unvisited
			c.setParent(null);
		}
		repaint();// re draw the maze without the solve
	}
	
	public void setCurrent(Cell current) {
		if(currentCells.size() == 0) {// if it's empty
			currentCells.add(current);//add crrent 
		} else {// if tis not empty add it to the top of cell
			currentCells.set(0, current);			
		}
	}
	
	public void setCurrentCells(List<Cell> currentCells) {
		this.currentCells = currentCells;
	}

	@Override
	protected void paintComponent(Graphics g) {// overrid of graphics class used to draw the solve of maze reafrshed by repaint() method
		super.paintComponent(g);
		for (Cell c : grid) {
			c.draw(g);
		}
		for (Cell c : currentCells) {
			if(c != null) c.displayAsColor(g, Color.ORANGE);
		}
		grid.get(0).displayAsColor(g, Color.GREEN); // start cell
		grid.get(grid.size() - 1).displayAsColor(g, Color.YELLOW); //goal cell
	}
}

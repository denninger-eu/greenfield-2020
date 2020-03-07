package eu.k5.game;

import org.junit.Test;

import junit.framework.Assert;

public class GridTest {

	@Test
	public void init() {
		Grid grid = new Grid(10, 15);
		Assert.assertEquals(0, grid.countAliveNeighbors(0, 0));
	}

	@Test
	public void countAliveNeightbors_outOfBounds() {
		Grid grid = new Grid(10, 10);
		Assert.assertEquals(0, grid.countAliveNeighbors(11, 11));
	}

	@Test
	public void setAlive_addingOne_countsAsAliveNeigbor() {
		Grid grid = new Grid(10, 10);
		grid.setAlive(0, 0);
		Assert.assertEquals(1, grid.countAliveNeighbors(1, 1));
	}

	@Test
	public void setALive_addingThree_shouldInfect() {
		Grid grid = new Grid(10, 10);
		grid.setAlive(0, 0);
		grid.setAlive(1, 0);
		grid.setAlive(2, 0);
		grid.nextGeneration();
		Assert.assertTrue(grid.isAlive(1, 1));
	}
}

package eu.k5.game;

import org.junit.Assert;
import org.junit.Test;

public class CellStateTest {

	@Test
	public void reborn_dead_reborn() {
		Assert.assertTrue(CellState.DEAD.rebornRule(3));
	}

	@Test
	public void reborn_dead_dead_tooFewNeigbors() {
		Assert.assertFalse(CellState.DEAD.rebornRule(1));
	}

	@Test
	public void reborn_dead_dead_tooMuchNeigbors() {
		Assert.assertFalse(CellState.DEAD.rebornRule(4));
	}

	@Test
	public void surive_alive_keepsAlive_3neighbors() {
		Assert.assertTrue(CellState.ALIVE.surviveRule(3));
	}

	@Test
	public void survive_alive_keepsAlive_2neighbors() {
		Assert.assertTrue(CellState.ALIVE.surviveRule(2));
	}

	@Test
	public void survive_alive_dies_lonely() {
		Assert.assertFalse(CellState.ALIVE.surviveRule(1));
	}

	@Test
	public void survive_alive_dies_overpopulated() {
		Assert.assertFalse(CellState.ALIVE.surviveRule(4));
	}

	@Test
	public void nextGeneration_alive_dies() {
		Assert.assertEquals(CellState.DIES, CellState.ALIVE.nextGeneration(4));
	}

	@Test
	public void nextGeneration_alive_alive() {
		Assert.assertEquals(CellState.ALIVE, CellState.ALIVE.nextGeneration(2));
	}

	@Test
	public void nextGeneration_dead_born() {
		Assert.assertEquals(CellState.BORN, CellState.DEAD.nextGeneration(3));
	}

	@Test
	public void nextGeneration_dead_dead() {
		Assert.assertEquals(CellState.DEAD, CellState.DEAD.nextGeneration(1));
	}

	@Test
	public void nextGenration_born_survive() {
		Assert.assertEquals(CellState.ALIVE, CellState.BORN.nextGeneration(2));
	}

	@Test
	public void nextGeneration_born_dies() {
		Assert.assertEquals(CellState.DIES, CellState.BORN.nextGeneration(1));
	}

	@Test
	public void nextGeneration_dies_dead() {
		Assert.assertEquals(CellState.DEAD, CellState.DIES.nextGeneration(4));
	}

	@Test
	public void nextGeneration_dies_born() {
		Assert.assertEquals(CellState.BORN, CellState.DIES.nextGeneration(3));
	}

	@Test
	public void wasAlive_born() {
		Assert.assertFalse(CellState.BORN.wasAlive());
	}

	@Test
	public void keepAlive_born() {
		Assert.assertTrue(CellState.BORN.keepAlive());
	}

	@Test
	public void wasAlive_dead() {
		Assert.assertFalse(CellState.DEAD.wasAlive());
	}

	@Test
	public void keepAlive_dead() {
		Assert.assertFalse(CellState.DEAD.keepAlive());
	}

	@Test
	public void wasAlive_dies() {
		Assert.assertTrue(CellState.DIES.wasAlive());
	}

	@Test
	public void keepAlive_dies() {
		Assert.assertFalse(CellState.DIES.keepAlive());
	}

	@Test
	public void wasAlive_alive() {
		Assert.assertTrue(CellState.ALIVE.wasAlive());
	}

	@Test
	public void keepAlive_alive() {
		Assert.assertTrue(CellState.ALIVE.keepAlive());
	}

}

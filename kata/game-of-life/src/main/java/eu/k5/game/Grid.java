package eu.k5.game;

public class Grid {
	private static final int ROW = 0;
	private static final int COLUMN = 1;

	private final CellState[][] universe;
	private final int rows;
	private final int columns;

	public Grid(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		universe = new CellState[rows][columns];
		for (int row = 0; row < rows; row++) {
			universe[row] = new CellState[columns];
			for (int column = 0; column < columns; column++) {
				universe[row][column] = CellState.DEAD;
			}
		}
	}

	boolean isAlive(int row, int column) {
		return universe[row % rows][column % columns].isAlive();
	}

	void setAlive(int row, int column) {
		universe[row % rows][column % columns] = CellState.ALIVE;
	}

	int countAliveNeighbors(int row, int column) {
		int alive = 0;

		for (int[] stencil : new int[][] { { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, 0 },
				{ -1, -1 } }) {

			int actualRow = getActualRow(row, stencil);
			int actualColumn = getActualColumn(column, stencil);

			if (isOldGeneration(row, column, actualRow, actualColumn)) {
				if (universe[actualRow][actualColumn].wasAlive()) {
					alive++;
				}
			} else if (universe[actualRow][actualColumn].keepAlive()) {
				alive++;
			}

		}
		return alive;
	}

	private int getActualColumn(int column, int[] stencil) {
		return (column + stencil[COLUMN] + columns) % columns;
	}

	private int getActualRow(int row, int[] stencil) {
		return (row + stencil[ROW] + rows) % rows;
	}

	private boolean isOldGeneration(int row, int column, int actualRow, int actualColumn) {
		return actualRow < row || actualRow == row && actualColumn < column;
	}

	void nextGeneration(int row, int column) {
		int aliveNeighbors = countAliveNeighbors(row, column);
		universe[row][column] = universe[row][column].nextGeneration(aliveNeighbors);
	}

	public void nextGeneration() {
		for (int row = 0; row < rows; row++) {
			for (int colum = 0; colum < columns; colum++) {
				nextGeneration(row, colum);
			}
		}
	}

}

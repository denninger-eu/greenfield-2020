package eu.k5.game;

enum CellState {
	ALIVE(true, true) {
		@Override
		CellState nextGeneration(int aliveNeighbors) {
			if (surviveRule(aliveNeighbors)) {
				return ALIVE;
			}
			return DIES;
		}
	},
	DEAD(false, false) {
		@Override
		CellState nextGeneration(int aliveNeighbors) {
			if (rebornRule(aliveNeighbors)) {
				return BORN;
			}
			return DEAD;
		}
	},
	DIES(true, false) {
		@Override
		CellState nextGeneration(int aliveNeighbors) {
			if (rebornRule(aliveNeighbors)) {
				return BORN;
			}
			return DEAD;
		}
	},
	BORN(false, true) {
		@Override
		CellState nextGeneration(int aliveNeighbors) {
			if (surviveRule(aliveNeighbors)) {
				return ALIVE;
			}
			return DIES;
		}
	};
	private boolean wasAlive;
	private final boolean keepAlive;

	private CellState(boolean wasAlive, boolean keepAlive) {
		this.wasAlive = wasAlive;
		this.keepAlive = keepAlive;
	}

	public boolean surviveRule(int aliveNeighbors) {
		if (aliveNeighbors == 3 || aliveNeighbors == 2) {
			return true;
		}
		return false;
	}

	public boolean rebornRule(int aliveNeigbbors) {
		if (aliveNeigbbors == 3) {
			return true;
		}
		return false;
	}

	public boolean wasAlive() {
		return wasAlive;
	}

	public boolean keepAlive() {
		return keepAlive;

	}

	abstract CellState nextGeneration(int aliveNeighbors);

	protected boolean isAlive() {
		return this == BORN || this == ALIVE;
	}
}
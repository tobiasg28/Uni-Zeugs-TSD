package frontend;

import entities.GameStep;

public class ActualGameStep {
	private static GameStep gameStep = null;

	public static GameStep getGameStep() {
		return gameStep;
	}

	public static void setGameStep(GameStep gameStep) {
		ActualGameStep.gameStep = gameStep;
	}
}

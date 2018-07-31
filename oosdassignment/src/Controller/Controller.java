package Controller;

import com.google.java.contract.Requires;

import Model.Model;
import View.View;

/**
 * @author Liam
 *(MVC) Processes incoming requests, handles user input and executes
 * application logic.
 */
public class Controller {
	private static Controller instance;

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public Controller() {
		instance = this;
	}

	/**
	 * Called once on application startup, tells the view to launch.
	 */
	public void launchApp() {
		View.getInstance().startLaunch();
	}

	/**
	 * Tells the model to start a game, and switches to the game view.
	 */
	public void startGame() {
		System.out.println("Start game.");
		Model.getInstance().startGame();
		View.getInstance().showGameWindow();
	}

	/**
	 * Will quit the game, and exit the system.
	 */
	public void exitGame() {
		System.out.println("Exit game.");
		System.exit(0);
	}

	@Requires({ "startx > 0", "starty > 0", "endx > 0", "endy >0 ", "startx  < width ", "starty < height",
			"endx < width ", "endy < height", "(startx != endx) && (starty != endy)" })
	public boolean makeMove(int startx, int starty, int endx, int endy) {
		return Model.getInstance().makeMove(startx, starty, endx, endy);
	}

	public void timerElapsed() {
		Model.getInstance().timerElapsed();
	}

}

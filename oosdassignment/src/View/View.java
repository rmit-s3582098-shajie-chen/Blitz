package View;

import java.io.File;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Presents the model in a graphical user interface.
 */
public class View extends Application {
	private static View instance;
	private static Stage stage;
	private GameWindow gameWindow;
	private HelpWindow helpWindow;
	private MenuWindow menuWindow;

	public View() {
		instance = this;
	}

	public static View getInstance() {
		if (instance == null) {
			instance = new View();
		}
		return instance;
	}

	/**
	 * Starts the GUI by launching the javafx application.
	 */
	public void startLaunch() {
		launch();
	}

	/**
	 * Starts a new MainMenu scene.
	 */
	public void showMainMenu() {
		menuWindow = new MenuWindow();
		stage.setScene(menuWindow.generateScene());
		stage.setTitle("Blitz - Main Menu");
		stage.show();

		if (gameWindow != null)
			gameWindow.stopTimer();
	}

	/**
	 * Starts a new GameWindow scene.
	 */
	public void showGameWindow() {
		gameWindow = new GameWindow();
		gameWindow.startMoveTimer();
		refreshGameWindow();
	}

	/**
	 * Starts a new HelpWindow scene.
	 */
	public void showHelpWindow() {
		if (helpWindow == null) {
			helpWindow = new HelpWindow();
		}
		stage.setScene(helpWindow.generateScene());
		stage.setTitle("Blitz - Help");
		stage.show();

		if (gameWindow != null)
			gameWindow.stopTimer();
	}

	/**
	 * Refreshes the existing GameWindow scene. Useful for midgame.
	 */
	public void refreshGameWindow() {
		if (gameWindow == null) {
			gameWindow = new GameWindow();
		}
		stage.setScene(gameWindow.generateScene());
		stage.setTitle("Blitz - Game");
		stage.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		File file = new File("img/icon.png");
		Image image = new Image(file.toURI().toString());
		primaryStage.getIcons().add(image);
		stage = primaryStage;
		showMainMenu();
	}

	public void restartMoveTimer() {
		if (gameWindow != null)
			gameWindow.restartMoveTimer();
	}
}

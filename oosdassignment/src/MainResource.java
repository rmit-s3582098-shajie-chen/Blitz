import Controller.Controller;

public class MainResource {

	/**
	 * Main entry point for the application, calls the Controller to launch.
	 */
	public static void main(String[] args) {
		Controller controller = Controller.getInstance();
		controller.launchApp();
	}
}
 
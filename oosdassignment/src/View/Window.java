package View;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public abstract class Window {

	public enum TileType {
		ASSASSIN(0), MERCHANT(1), KNIGHT(2), WIZARD(3), PIKEMAN(4), GUARD(5), SELECTED(6), GRASS(7), EMPTY(8), TREE(9), DEFENSIVE(10), SHOES(11);

		private final int value;

		private TileType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	protected ArrayList<Image> imageTiles = new ArrayList<Image>();
	
	/**
	 * Tile names to read from file.
	 */
	private static final String[] tileFileNames = {"assassin","merchant", "knight", "wizard", "pikeman", "guard", "selected", "grass", "empty", "tree", "defensive", "shoes"};

	/**
	 * Loads icon images into an ArrayList for later use by subclasses.
	 */
	protected void loadImages() {
		File file;
		
		// Load image tiles.
		for(int i = 0; i < tileFileNames.length; i++) {
			file = new File("img/" + tileFileNames[i] + ".png");
			imageTiles.add(new Image(file.toURI().toString()));
		}
	}

	/**
	 * Generates a scene depending on which (concrete) window class it is.
	 * @return The Scene to display.
	 */
	protected abstract Scene generateScene();
}

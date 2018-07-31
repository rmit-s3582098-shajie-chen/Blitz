package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HelpWindow extends Window {

	@Override
	protected Scene generateScene() {

		Text informationText = new Text(HelpText.text);
		informationText.setWrappingWidth(500);
		informationText.setFont(Font.font("Monospaced", 12));

		// Puts all the text in a centered box.
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.CENTER);
		hbBtn2.getChildren().add(informationText);

		// Back to menu button.
		Button btn = new Button();
		btn.setText("Back to Menu");
		btn.setAlignment(Pos.CENTER);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				View.getInstance().showMainMenu();
			}
		});
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().add(btn);

		// Set the grid layout
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.add(hbBtn2, 0, 0);
		grid.add(hbBtn, 0, 1);

		return new Scene(grid, 600, 500);
	}
}

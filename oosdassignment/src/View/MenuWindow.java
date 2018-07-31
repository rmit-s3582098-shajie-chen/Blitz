package View;

import Command.OpenCommand;
import Controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuWindow extends Window {

	public int titleFontSize = 18;
	public Pos contentsPosition = Pos.CENTER;

	@Override
	protected Scene generateScene() {
		// Title screen heading.
		Text scenetitle = new Text("Welcome to Blitz!");
		scenetitle.setFont(Font.font(null, titleFontSize));
		HBox hbBtn3 = new HBox(10);
		hbBtn3.setAlignment(contentsPosition);
		hbBtn3.getChildren().add(scenetitle);

		// Play game button.
		Button btn = new Button();
		btn.setText("Start Game");
		btn.setAlignment(contentsPosition);
		btn.setMinWidth(100);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Controller.getInstance().startGame();
			}
		});
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(contentsPosition);
		hbBtn.getChildren().add(btn);

		// Exit game button.
		Button btn2 = new Button();
		btn2.setText("Exit");
		btn2.setAlignment(contentsPosition);
		btn2.setMinWidth(100);
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Controller.getInstance().exitGame();
			}
		});
		HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(contentsPosition);
		hbBtn2.getChildren().add(btn2);

		// Help button.
		Button btn3 = new Button();
		btn3.setText("Help");
		btn3.setAlignment(contentsPosition);
		btn3.setMinWidth(100);
		btn3.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				View.getInstance().showHelpWindow();
			}
		});
		HBox hbBtn4 = new HBox(10);
		hbBtn4.setAlignment(contentsPosition);
		hbBtn4.getChildren().add(btn3);
		
		// Open Game Button
		Button openBtn = new Button();
		openBtn.setText("Open Game");
		openBtn.setAlignment(contentsPosition);
		openBtn.setMinWidth(100);
		openBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new OpenCommand());
		HBox hbBtn6 = new HBox(10);
		hbBtn6.setAlignment(contentsPosition);
		hbBtn6.getChildren().add(openBtn);
		
		// Set the grid layout
		GridPane grid = new GridPane();
		grid.setAlignment(contentsPosition);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.add(hbBtn3, 0, 0);
		grid.add(hbBtn, 0, 1);
		grid.add(hbBtn4, 0, 2);
		grid.add(hbBtn6, 0, 3);
		grid.add(hbBtn2, 0, 5);

		return new Scene(grid, 350, 300);
	}
}

package Command;

import View.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class Command implements ICommand, EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent arg0) {
		execute(); 
	}

}

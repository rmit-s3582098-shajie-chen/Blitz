package Command;

import java.util.ArrayList;
import java.util.Optional;

import Model.BoardSnapshot;
import Model.Model;
import PersistentDataStructure.IStack;
import View.View;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;

/**
 * Alternative Universes Command
 * @author Liam
 */
public class AUCommand extends Command {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute() {

		IStack<BoardSnapshot> stack = Model.getInstance().getRootNode();
		ArrayList<BoardSnapshot> snapshots = stack.getStack();
		ArrayList<Integer> depthList = stack.getDepthList();
		ArrayList<Integer> leafList = stack.getLeafList();

		ArrayList<String> stackGraph = new ArrayList<String>();
		ArrayList<String> boardIDs = new ArrayList<String>();

		int previousLeaf = leafList.get(0);
		int column = 0;

		for (int i = 0; i < snapshots.size(); i++) {
			String boardID = String.format("%02d", snapshots.get(i).boardID);
			int depth = depthList.get(i);
			int leaf = leafList.get(i);
			boolean newColumn = false;

			System.out.println(boardID + " " + depth + " " + leaf);

			// Move to the next column when the leaf counter changes (means its on a new
			// tree branch).
			if (leaf != previousLeaf) {
				column++;
				newColumn = true;
				previousLeaf = leaf;
			}

			String line = "";

			if (depth == stackGraph.size()) {
				// We have to add a new row.
				for (int k = 0; k < 3 * column; k++) {
					line += " ";
				}
				stackGraph.add(line + boardID);
			} else {
				// We have to add to an existing row.

				if (newColumn) {
					line = stackGraph.get(depth - 1);
					while (line.length() <= column * 3) {
						line += "-";
					}
					stackGraph.set(depth - 1, line);
				}

				line = stackGraph.get(depth);
				while (line.length() < column * 3) {
					line += " ";
				}
				stackGraph.set(depth, line + boardID);
			}

			boardIDs.add(boardID);
		}

		String header = "";

		for (String line : stackGraph) {
			header += line + "\n";
		}

		ChoiceDialog dialog = new ChoiceDialog("00", boardIDs.toArray());
		dialog.setTitle("Alternative Universes");
		dialog.setHeaderText(header);
		dialog.setContentText("Pick a game to rewind to:");
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("dialogStyle.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {
			Model.getInstance().LoadBoardID(Integer.parseInt(result.get()));
			View.getInstance().refreshGameWindow();
		}
	}

}

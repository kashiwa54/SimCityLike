package application;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

public class TitledPaneWithButton extends TitledPane{
	Button button = new Button("×");
	TitledPaneWithButton()	{
		this("", null);
	}
	TitledPaneWithButton(String title,Node contents)	{
		super(title,contents);
		this.setContentDisplay(ContentDisplay.RIGHT);
		this.setGraphic(button);
		button.getStyleClass().add("delete");
		button.setCancelButton(true);
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setOnAction(ActionEvent -> {
			if(this.getParent() instanceof Pane)	{
				Pane pane = (Pane)this.getParent();
				pane.getChildren().remove(this);
			}else {
				System.out.println("Remove window is fault");
			}
		});
	}
}

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class View {
	
	private Scene mainScene;
	private StackPane stackPane;
	
	private Circle backgroundCircle;
	private Button closeButton;
	
	public View(){
		stackPane = new StackPane();
		stackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		
		mainScene = new Scene(stackPane, 80, 80, Color.TRANSPARENT);
		mainScene.getStylesheets().add(getClass().getResource("theme.css").toExternalForm());
		
		backgroundCircle = new Circle(40, 40, 40);
		backgroundCircle.setFill(Color.GREEN);
		
		closeButton = new Button("Close");
		closeButton.getStyleClass().add("close");
		closeButton.setPrefHeight(50);
		closeButton.setPrefWidth(50);
		
		stackPane.getChildren().addAll(backgroundCircle, closeButton);
	}
	
	public Scene getMainScene(){
		return mainScene;
	}
	public StackPane getStackPane(){
		return stackPane;
	}
	
	public Circle getBackgroundCircle(){
		return backgroundCircle;
	}
	public Button getCloseButton(){
		return closeButton;
	}
	
}

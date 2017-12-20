import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HeaderlessDialog {
	public static void createHeaderlessDialog(String title, String text){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}
}

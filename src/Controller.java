import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.*;

public class Controller implements NativeKeyListener{
	
	private Model model = null;
	private View view = null;
	
	public Controller(Model model, View view){
		this.model = model;
		this.view = view;
		
		Button closeButton = getViewCloseButton();
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
				System.exit(0);
		    }
		});
		
		GlobalScreen.addNativeKeyListener(Controller.this);
	}
	
	// GLOBAL KEY EVENTS
	public void nativeKeyPressed(NativeKeyEvent e){
		if(e.getKeyCode() == e.VC_CAPS_LOCK){
			if(getIsOnline()){
				Platform.runLater(new Runnable(){
					public void run(){
						stop();
						getViewBackgroundCircle().setFill(Color.RED);
						setIsOnline(false);
					}
				});
			}else{
				Platform.runLater(new Runnable(){
					public void run(){
						start();
						getViewBackgroundCircle().setFill(Color.GREEN);
						setIsOnline(true);
					}
				});
			}
		}
	}
	public void nativeKeyReleased(NativeKeyEvent e){}
	public void nativeKeyTyped(NativeKeyEvent e){}
	
	// MODEL
	public void stop(){
		try{
			model.stop();
		}catch(IOException e){
			e.printStackTrace();
			HeaderlessDialog.createHeaderlessDialog("Stopping Error", "There was an error attemping to release your IP.");
			getViewBackgroundCircle().setFill(Color.GREEN);
			setIsOnline(true);
		}
	}
	public void start(){
		try{
			model.start();
		}catch(IOException e){
			e.printStackTrace();
			HeaderlessDialog.createHeaderlessDialog("Starting Error", "There was an error attemping to renew your IP.");
			getViewBackgroundCircle().setFill(Color.RED);
			setIsOnline(false);
		}
	}
	public boolean getIsOnline(){
		return model.getIsOnline();
	}
	public void setIsOnline(boolean status){
		model.setIsOnline(status);
	}
	
	// VIEW
	public Scene getViewMainScene(){
		return view.getMainScene();
	}
	public StackPane getViewStackPane(){
		return view.getStackPane();
	}
	public Circle getViewBackgroundCircle(){
		return view.getBackgroundCircle();
	}
	public Button getViewCloseButton(){
		return view.getCloseButton();
	}
}

import java.awt.Toolkit;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Main extends Application {
	
	private java.awt.Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static VoidDispatchService vds = new VoidDispatchService();
	
	private double initX;
    private double initY;

    private Model model;
    private View view;
	private Controller controller;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			model = new Model();
			view = new View();
			controller = new Controller(model, view);
			
			Scene scene = controller.getViewMainScene();
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.setTitle(getRandomTitle());
			primaryStage.setX((scrnSize.width / 2) - (scene.getWidth() / 2));
			primaryStage.setY((scrnSize.height / 2) - (scene.getHeight() / 2));
			primaryStage.setAlwaysOnTop(true);
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();

			controller.getViewStackPane().setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    initX = me.getScreenX() - primaryStage.getX();
                    initY = me.getScreenY() - primaryStage.getY();
                }
           	});

            //when screen is dragged, translate it accordingly
			controller.getViewStackPane().setOnMouseDragged(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent me) {
					primaryStage.setX(me.getScreenX() - initX);
					primaryStage.setY(me.getScreenY() - initY);
                }
			});
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                	System.exit(0);
                }
            });

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private String getRandomTitle(){
		String[] randomValues = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		Random rand = new Random();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 30; i++){
			builder.append(randomValues[rand.nextInt(randomValues.length)]);
		}
		return builder.toString();
	}

	public static void main(String[] args){
		Main m = new Main();

		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		PrintStream origOut = System.out;

		logger.setLevel(Level.OFF);
		System.setOut(null);

		try{
			GlobalScreen.setEventDispatcher(Main.vds);
			GlobalScreen.registerNativeHook();
		}catch(NativeHookException ex){
			System.out.println(ex.getMessage());
		}

		System.setOut(origOut);

		launch(args);
	}

	public static class VoidDispatchService extends AbstractExecutorService {
        private boolean running = false;

        public VoidDispatchService() {
            running = true;
        }

        public void shutdown() {
            running = false;
        }

        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<Runnable>(0);
        }

        public boolean isShutdown() {
            return !running;
        }

        public boolean isTerminated() {
            return !running;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        public void execute(Runnable r) {
            r.run();
        }
    }

}
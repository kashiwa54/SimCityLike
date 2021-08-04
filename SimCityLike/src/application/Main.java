package application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
	static final int TILE_WIDTH = 32;
	static final int TILE_HEIGHT = 32;
	static final int TILESET_SIZE = 64;
	static final int SHEAR_ANGLE = 45;
	static final int TILE_SIZE = 64;
	static final int WINDOW_WIDTH = 1280;
	static final int WINDOW_HEIGHT = 960;

	static final int CONSOLE_WIDTH = 640;
	static final int CONSOLE_HEIGHT = 480;

	final Canvas canvas = new Canvas(WINDOW_WIDTH,WINDOW_HEIGHT);
	final Timeline timeline = new Timeline();



	@Override
	public void start(Stage primaryStage) {
		try {
			Map map = new Map(TILE_WIDTH,TILE_HEIGHT);

			MainWindowController window = mainWindowSetup(primaryStage,map);
			GraphicsContext gc = window.getGraphicsContext();

			ConsoleWindowController console = consoleWindowSetup(window,map);
			window.paintMainCanvas(gc,map);

			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),new EventHandler<ActionEvent>()	{
		    @Override
		    public void handle(ActionEvent event) {
		        window.repaint();
		    }
			}));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();

			console.write("Start method complete.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public MainWindowController mainWindowSetup(Stage stage,Map map)	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			AnchorPane root =(AnchorPane)loader.load();
			Scene mScene = new Scene(root,WINDOW_WIDTH,WINDOW_HEIGHT);
			mScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			stage.setScene(mScene);

	    	mScene.widthProperty().addListener(new ChangeListener<Number>() {
				@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
					root.setPrefWidth((double) newSceneWidth);
					System.out.println("Width: " + newSceneWidth);
				}
			});
			mScene.heightProperty().addListener(new ChangeListener<Number>() {
				@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
					root.setPrefHeight((double) newSceneHeight);
					System.out.println("Height: " + newSceneHeight);
				}
			});

			stage.show();

			MainWindowController MWController = loader.getController();
			MWController.setMap(map);
			System.out.println("Main window setup success.");
			return MWController;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public ConsoleWindowController consoleWindowSetup(MainWindowController mwc,Map map)		{						//新しいウィンドウをつくる
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsoleWindow.fxml"));
			AnchorPane root =(AnchorPane)loader.load();
			Scene cScene = new Scene(root,CONSOLE_WIDTH,CONSOLE_HEIGHT);
			cScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Stage cStage = new Stage();
			cStage.setScene(cScene);

	    	cScene.widthProperty().addListener(new ChangeListener<Number>() {
				@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
					root.setPrefWidth((double) newSceneWidth);
					System.out.println("Width: " + newSceneWidth);
				}
			});
			cScene.heightProperty().addListener(new ChangeListener<Number>() {
				@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
					root.setPrefHeight((double) newSceneHeight);
					System.out.println("Height: " + newSceneHeight);
				}
			});

			cStage.show();

			ConsoleWindowController CWController = loader.getController();
			GraphicsContext gc = mwc.getGraphicsContext();
			CWController.setMap(map);
			CWController.setGraphicsContext(gc);
			CWController.setMWController(mwc);
			System.out.println("Console Wibdow setup success.");
			return CWController;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static void main(String[] args) {
		launch(args);
	}



}

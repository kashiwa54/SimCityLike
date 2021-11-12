package application;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
	static final Rectangle2D SYSTEM_WINDOW = Screen.getPrimary().getVisualBounds();

	final Canvas canvas = new Canvas(CommonConst.WINDOW_MAX_WIDTH,CommonConst.WINDOW_MAX_HEIGHT);
	final Timeline timeline = new Timeline();

	TimelineProcessingService mainProcess = null;

	static int playerMoney = 100000000;
	Map map = new Map(CommonConst.TILE_WIDTH,CommonConst.TILE_HEIGHT);
	Time worldTime = new Time(CommonConst.DEFAULT_YEAR,CommonConst.DEFAULUT_SEASON,CommonConst.DEFAULT_WEEK,0,0);
	PeopleManager pm = new PeopleManager(worldTime);
	DemandManager dm = null;
	BuildingManager bm = null;
	MoneyManager mm = new MoneyManager(worldTime);

	static TimeFlow timeFlow = TimeFlow.LOW;

	@Override
	public void start(Stage primaryStage) {
		try {
			pm.bindMap(map);

			mainProcess = new TimelineProcessingService(worldTime,map);

			MainWindowController window = mainWindowSetup(primaryStage,map);
			GraphicsContext gc = window.getGraphicsContext();

			ConsoleWindowController console = consoleWindowSetup(window,map);
			window.paintMainCanvas(gc,map);

			dm = new DemandManager(window.getDemand(),pm);
			bm = new BuildingManager(map,dm);
			window.setManager(pm);
			map.bindBuildingManager(bm);
			mainProcess.setManagers(dm,bm,mm);

			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(CommonConst.DEFAULT_DURATION),new EventHandler<ActionEvent>()	{
			    @Override
			    public void handle(ActionEvent event) {
			        window.repaint();
			    }
			}));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();

			mainProcess.setDelay(Duration.millis(CommonConst.DEFAULT_DURATION));
			mainProcess.start();
			window.setTime(worldTime);

			console.write("Start method complete.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public MainWindowController mainWindowSetup(Stage stage,Map map)	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			AnchorPane root =(AnchorPane)loader.load();
			Scene mScene = new Scene(root,CommonConst.WINDOW_MAX_WIDTH,CommonConst.WINDOW_MAX_HEIGHT);
			mScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			stage.setScene(mScene);
			stage.setMaxWidth(CommonConst.WINDOW_MAX_WIDTH);
			stage.setMaxHeight(CommonConst.WINDOW_MAX_HEIGHT);

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
			Scene cScene = new Scene(root,CommonConst.CONSOLE_WIDTH,CommonConst.CONSOLE_HEIGHT);
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

	public static void setTimeFlow(TimeFlow flow)	{
		timeFlow = flow;
	}
	public static TimeFlow getTimeFlow()	{
		return timeFlow;
	}


	public static void main(String[] args) {
		launch(args);
	}



}

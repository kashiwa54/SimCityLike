package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainWindowController {
	private static final double SIZE_RATE = 0.5;
	private static final double SHEAR_ANGLE = CommonConst.SHEAR_ANGLE;
	private static final double TILE_ADJUST_FACTOR_X = ((CommonConst.TILESET_SIZE / 2) / Math.cos(Math.PI * SHEAR_ANGLE / (180 * 2))) / CommonConst.TILE_SIZE;
	private static final double TILE_ADJUST_FACTOR_Y = TILE_ADJUST_FACTOR_X / Math.sqrt(2);
	private static final int TILE_BORDER_WIDTH = 1;
	private static final int CURSOR_WIDTH = 2;

	private static final double IMAGE_OFFSET_Y = -CommonConst.TILESET_SIZE + (CommonConst.TILESET_SIZE * Math.tan(Math.PI * SHEAR_ANGLE / (180 * 2)));

	private static final double SHEAR_X = Math.tan(-1 * Math.PI * SHEAR_ANGLE / 180);
	private static final double ANGLE = SHEAR_ANGLE / 2;

	private final NumberFormat cur = NumberFormat.getCurrencyInstance();

	@FXML
	AnchorPane root;
	@FXML
	ScrollPane pane;
	@FXML
	StackPane stackpane;
	@FXML
	Canvas canvas = new Canvas(CommonConst.WINDOW_MAX_WIDTH,CommonConst.WINDOW_MAX_HEIGHT);
	@FXML
	HBox maintab;
	FlowPane areaTab = new FlowPane();
	FlowPane roadTab = new FlowPane();
	FlowPane specialTab = new FlowPane();
	@FXML
	HBox statusBar;
	@FXML
	Label timeLabel;
	@FXML
	Label textLabel;
	@FXML
	TextFlow timeFlow;
	@FXML
	Text yearText;
	@FXML
	Text seasonText;
	@FXML
	Text timeText;
	@FXML
	Text weekText;
	@FXML
	Label moneyLabel;
	@FXML
	Label populationLabel;
	@FXML
	RadioButton pause;
	@FXML
	RadioButton lowSpeed;
	@FXML
	RadioButton midSpeed;
	@FXML
	RadioButton highSpeed;
	ToggleGroup timeFlowButtons = new ToggleGroup();

	DemandBarChart<String,Number> demand;

	ArrayList<FlowPane> tabList = new ArrayList<FlowPane>(5);
	private Map map;
	private GraphicsContext graphics;
	private double canvasSizeRate = 100;

	private double mouseX = 0;
	private double mouseY = 0;
	Point2D mouse = new Point2D(mouseX,mouseY);
	Point2D mouseOnMap = mouse;
	private PlacableEnum mouseType = OtherTileEnum.DEFAULT;
	private SpreadType spreadType;

	private double zoomX = 1.0;
	private double zoomY = 1.0;
	private double pivotX = 640;
	private double pivotY = 480;
	private double moveX = CommonConst.MOVE_X_DEFAULT;
	private double moveY = CommonConst.MOVE_Y_DEFAULT;

	private Point2D tmpP;
	private Affine affine = new Affine();
	Affine imgAff = new Affine();

	private boolean onDrag = false;
	private Point2D dragStart = mouseOnMap;

	private LinkedList<TileObject> cachePath = null;
	private Point2D cacheStart = null;
	private Point2D cacheEnd = null;

	//TODO 別スレッドに移動
	private EnumMap<ResidentalBuildingEnum,Image> residentalMap = new EnumMap<ResidentalBuildingEnum,Image>(ResidentalBuildingEnum.class);
	private EnumMap<CommercialBuildingEnum,Image> commercialMap = new EnumMap<CommercialBuildingEnum,Image>(CommercialBuildingEnum.class);
	private EnumMap<IndustrialBuildingEnum,Image> industrialMap = new EnumMap<IndustrialBuildingEnum,Image>(IndustrialBuildingEnum.class);
	private EnumMap<WayEnum,EnumMap<DirectionForImage,Image>> wayMap = new EnumMap<WayEnum,EnumMap<DirectionForImage,Image>>(WayEnum.class);

	private Time time = null;
	private PeopleManager pm = null;

	private int residentalDemand = 0;
	private int commercialDemand = 0;
	private int industrialDemand = 0;

	private XYChart.Series<String,Number> residental;
	private XYChart.Series<String,Number> commercial;
	private XYChart.Series<String,Number> industrial;

	private XYChart.Data<String, Number> residentalData;
	private XYChart.Data<String, Number> commercialData;
	private XYChart.Data<String, Number> industrialData;

	@FXML
    public void initialize() {
    	graphics = canvas.getGraphicsContext2D();
    	canvas.setFocusTraversable(true);

    	for(ResidentalBuildingEnum type : ResidentalBuildingEnum.values())	{
			File imageFile = null;
			try {
				imageFile = new File(type.getImagePath());
				residentalMap.put(type,new Image(imageFile.toURI().toString()));
			} catch (IllegalArgumentException e) {
				checkFileError(e,imageFile);
			}
    	}
    	for(CommercialBuildingEnum type : CommercialBuildingEnum.values())	{
			File imageFile = null;
			try {
				imageFile = new File(type.getImagePath());
				commercialMap.put(type,new Image(imageFile.toURI().toString()));
			} catch (IllegalArgumentException e) {
				checkFileError(e,imageFile);
			}
    	}
    	for(IndustrialBuildingEnum type : IndustrialBuildingEnum.values())	{
			File imageFile = null;
			try {
				imageFile = new File(type.getImagePath());
				industrialMap.put(type,new Image(imageFile.toURI().toString()));
			} catch (IllegalArgumentException e) {
				checkFileError(e,imageFile);
			}
    	}
    	for(WayEnum type : WayEnum.values())	{
    		BufferedImage imageSet = null;
			File imageSetFile = null;
			try {
				imageSetFile = new File(type.getImageSetPath());
				imageSet = ImageIO.read(imageSetFile);

	    		EnumMap<DirectionForImage,Image> imageSetMap = new EnumMap<DirectionForImage,Image>(DirectionForImage.class);

	    		int tileX = imageSet.getWidth()/CommonConst.TILESET_SIZE;
	    		int cnt = 0;
	    		for(DirectionForImage d : DirectionForImage.values())	{
	    			imageSetMap.put(d,SwingFXUtils.toFXImage(imageSet.getSubimage(cnt%tileX * CommonConst.TILESET_SIZE, cnt/tileX * CommonConst.TILESET_SIZE, CommonConst.TILESET_SIZE, CommonConst.TILESET_SIZE), null));
	    			cnt++;
	    		}
	    		wayMap.put(type,imageSetMap);
			} catch (IOException e) {
				checkFileError(e,imageSetFile);
			}
    	}

    	creatTab(areaTab,AreaTabEnum.values());
    	tabList.add(areaTab);
    	root.getChildren().add(areaTab);

    	creatTab(roadTab,RoadTabEnum.values());
    	tabList.add(roadTab);
    	root.getChildren().add(roadTab);

    	creatTab(specialTab,SpecialTabs.values());
    	tabList.add(specialTab);
    	root.getChildren().add(specialTab);

    	demand = createDemand();
    	root.getChildren().add(demand);

		pause.setToggleGroup(timeFlowButtons);
		lowSpeed.setToggleGroup(timeFlowButtons);
		midSpeed.setToggleGroup(timeFlowButtons);
		highSpeed.setToggleGroup(timeFlowButtons);
		timeFlowButtons.selectToggle(lowSpeed);

    	System.out.println("Main window initialized.");
    }

	public <T extends TabableEnum> void creatTab(FlowPane tab, T[] tabs)	{
    	tab.setVisible(false);
    	AnchorPane.setTopAnchor(tab, CommonConst.BUTTON_SIZE);
    	AnchorPane.setLeftAnchor(tab, 0.0);
    	AnchorPane.setRightAnchor(tab, CommonConst.BUTTON_SIZE);
    	tab.setPrefSize(CommonConst.WINDOW_MAX_WIDTH, CommonConst.BUTTON_SIZE);
    	tab.setPadding(new Insets(0, 16, 0, 16));
    	Button[] tabButtons = new Button[tabs.length];
    	for(int i = 0;i < tabs.length;i++)	{
    		tabButtons[i] = new Button();
    		tabButtons[i].setMaxSize(CommonConst.BUTTON_SIZE,CommonConst.BUTTON_SIZE);
    		tabButtons[i].setMinSize(CommonConst.BUTTON_SIZE, CommonConst.BUTTON_SIZE);
    		tabButtons[i].setPrefSize(CommonConst.BUTTON_SIZE, CommonConst.BUTTON_SIZE);
    		Text text = new Text((tabs[i]).getDisplay());
    		text.getStyleClass().add("button-label");
    		text.setWrappingWidth(CommonConst.BUTTON_SIZE);
    		tabButtons[i].setGraphic(text);
			tabButtons[i].setStyle("-fx-background-image:url(\"" + tabs[i].getImagePath() + "\");");
			tab.getChildren().add(tabButtons[i]);
			PlacableEnum mouseType = tabs[i].getType();
			SpreadType spread = tabs[i].getSpread();
			tabButtons[i].setOnAction(ActonEvent ->{
				setMouseType(mouseType,spread);
			});
    	}
	}
	public DemandBarChart<String,Number> createDemand()	{
		DemandBarChart<String,Number> demand;
    	CategoryAxis xAxis = new CategoryAxis();
    	NumberAxis yAxis = new NumberAxis(0,CommonConst.DEMAND_AXIS_MAX,20);
    	demand = new DemandBarChart<String,Number>(xAxis,yAxis);
    	AnchorPane.setRightAnchor(demand,0.0);
    	AnchorPane.setBottomAnchor(demand, CommonConst.INFO_BAR_SIZE);
    	demand.setMaxSize(CommonConst.DEMAND_CHART_SIZE, CommonConst.DEMAND_CHART_SIZE);
    	demand.setPrefSize(CommonConst.DEMAND_CHART_SIZE, CommonConst.DEMAND_CHART_SIZE);
    	demand.setMinSize(CommonConst.DEMAND_CHART_SIZE, CommonConst.DEMAND_CHART_SIZE);

		residental = new XYChart.Series<String,Number>();
		commercial = new XYChart.Series<String,Number>();
		industrial = new XYChart.Series<String,Number>();

		residentalData = new XYChart.Data<String, Number>();
		commercialData = new XYChart.Data<String, Number>();
		industrialData = new XYChart.Data<String, Number>();
		residentalData.setXValue("住宅");
		commercialData.setXValue("商業");
		industrialData.setXValue("工業");
		residentalData.setYValue(residentalDemand);
		commercialData.setYValue(commercialDemand);
		industrialData.setYValue(industrialDemand);
		residental.getData().add(residentalData);
		commercial.getData().add(commercialData);
		industrial.getData().add(industrialData);

		demand.getData().add(residental);
		demand.getData().add(commercial);
		demand.getData().add(industrial);

    	return demand;
	}
	public void setMap(Map map)		{
		this.map = map;
	}
	public void setManager(PeopleManager pm)	{
		this.pm = pm;
	}
	public GraphicsContext getGraphicsContext()	{
		return this.graphics;
	}

	public void paintMainCanvas(GraphicsContext gc,Map map)	{
		Canvas canvas = gc.getCanvas();

		gc.setTransform(1,0,0,1,0,0);
		gc.setFill(Color.SKYBLUE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		affine.setToTransform(1,0,0,0,1,0);
		affine.appendRotation(ANGLE,canvas.getWidth()/2,canvas.getHeight()/2);
		affine.appendShear(SHEAR_X,0,1,1);
		affine.appendScale(zoomX * TILE_ADJUST_FACTOR_X,zoomY * TILE_ADJUST_FACTOR_Y,pivotX,pivotY);
		affine.appendTranslation(moveX, moveY);
		gc.setTransform(affine);

		gc.setFill(Color.KHAKI);
		gc.fillRect(0,0, map.getWidth() * CommonConst.TILE_SIZE, map.getHeight() * CommonConst.TILE_SIZE);

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(TILE_BORDER_WIDTH);
		for(int i = 0;i <= map.getWidth();i++)	{
			gc.strokeLine(i * CommonConst.TILE_SIZE,0
					, i * CommonConst.TILE_SIZE, map.getHeight() * CommonConst.TILE_SIZE);
		}
		for(int i = 0;i <= map.getHeight();i++)	{
			gc.strokeLine(0, i * CommonConst.TILE_SIZE
					, map.getWidth() * CommonConst.TILE_SIZE , i * CommonConst.TILE_SIZE);
		}
		for(int i = 0;i < map.getWidth();i++)	{
			for(int j = 0;j < map.getHeight();j++)	{
				PlacableEnum type = map.getTileObject(i, j).type;
				if(type instanceof SiteEnum)	{
					gc.setFill(((SiteEnum) type).getColor());
					gc.fillRect(i * CommonConst.TILE_SIZE,j * CommonConst.TILE_SIZE,CommonConst.TILE_SIZE,CommonConst.TILE_SIZE);
				}else {
					imgAff.setToTransform(1,0,0,0,1,0);
					tmpP = affine.transform(i * CommonConst.TILE_SIZE,j * CommonConst.TILE_SIZE);
					imgAff.appendScale(zoomX,zoomY,tmpP.getX(),tmpP.getY());
					gc.setTransform(imgAff);
					if(type instanceof WayEnum)	{
						Way way = (Way)map.getTileObject(i, j);
						gc.drawImage(wayMap.get(type).get(way.getConnectState()),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
					}else if(type instanceof ResidentalBuildingEnum)	{
						gc.drawImage(residentalMap.get(type),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
					}else if(type instanceof CommercialBuildingEnum)	{
						gc.drawImage(commercialMap.get(type),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
					}else if(type instanceof IndustrialBuildingEnum)	{
						gc.drawImage(industrialMap.get(type),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
					}
					gc.setTransform(affine);
				}

			}
		}

		if(spreadType == SpreadType.DOT)	{
			if ((mouseOnMap.getX() >= 0)&&(mouseOnMap.getY() >= 0)&&(mouseOnMap.getX() <= CommonConst.TILE_WIDTH * CommonConst.TILE_SIZE)&&(mouseOnMap.getY() <= CommonConst.TILE_HEIGHT * CommonConst.TILE_SIZE))	{
				imgAff.setToTransform(1,0,0,0,1,0);
				tmpP = affine.transform(pointingTileF(mouseOnMap.getX()),pointingTileF(mouseOnMap.getY()));
				imgAff.appendScale(zoomX,zoomY,tmpP.getX(),tmpP.getY());
				gc.setTransform(imgAff);

				gc.setGlobalAlpha(0.3);

				if(mouseType instanceof ResidentalBuildingEnum)	{
					gc.drawImage(residentalMap.get(mouseType),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
				}
				if(mouseType instanceof CommercialBuildingEnum)	{
					gc.drawImage(commercialMap.get(mouseType),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
				}
				if(mouseType instanceof IndustrialBuildingEnum)	{
					gc.drawImage(industrialMap.get(mouseType),tmpP.getX() - (CommonConst.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
				}
			gc.setGlobalAlpha(1.0);

			}
		}else {
			if(onDrag)	{
				gc.setTransform(affine);
				gc.setGlobalAlpha(0.3);
				if(spreadType == SpreadType.AREA)	{
					if(mouseType instanceof SiteEnum)	{
						gc.setFill(((SiteEnum) mouseType).getColor());
					}else if(mouseType == OtherTileEnum.REMOVE){
						gc.setFill(Color.RED);
					}else {
						gc.setFill(Color.YELLOW);
					}
					Rectangle rect = spreadSite(dragStart,mouseOnMap);
					gc.fillRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
				}else {
					if(mouseType instanceof WayEnum)	{
						//TODO 画像にする
						gc.setFill(Color.GRAY);
					}else {
						gc.setFill(Color.YELLOW);
					}
					LinkedList<TileObject> path = getLinePath(dragStart,mouseOnMap);
					for(TileObject object:path)	{
						if(object == null)	{
							continue;
						}else {
							gc.fillRect(object.getX() * CommonConst.TILESET_SIZE, object.getY() * CommonConst.TILESET_SIZE, CommonConst.TILESET_SIZE, CommonConst.TILESET_SIZE);
						}
					}
				}
				gc.setGlobalAlpha(1.0);
			}
			gc.setTransform(affine);
			gc.setStroke(Color.YELLOW);
			gc.setLineWidth(CURSOR_WIDTH);
			gc.strokeRect(pointingTileF(mouseOnMap.getX()), pointingTileF(mouseOnMap.getY()), CommonConst.TILE_SIZE, CommonConst.TILE_SIZE);
		}
	}
	public void updateLabels()	{
		if(time == null)	{
			timeLabel.setText("");
		}else {
			yearText.setText(time.getYear() + "年 ");
			seasonText.setFill(time.getSeason().getColor());
			seasonText.setText(time.getSeason().getJp() + " ");
			timeText.setText(time.format());
			weekText.setFill(time.getWeek().getColor());
	    	weekText.setText(" " + time.getWeek().getJp());
		}

		moneyLabel.setText(cur.format(Main.playerMoney));
		populationLabel.setText("人口:" + pm.getPeopleList().size() + "人");
	}
	public void setMouseType(PlacableEnum type,SpreadType spread)	{
		this.mouseType = type;
		this.spreadType = spread;
		if((mouseType.equals(OtherTileEnum.DEFAULT))||(mouseType == null))	{
			canvas.setCursor(Cursor.DEFAULT);
		}else {
			canvas.setCursor(Cursor.CROSSHAIR);
		}
	}
	@FXML
	public void buttonArea(ActionEvent ae)	{
		setMouseType(OtherTileEnum.DEFAULT,SpreadType.DOT);
		switchTabs(tabList,areaTab);
	}
	@FXML
	public void buttonRoad(ActionEvent ae)	{
		setMouseType(OtherTileEnum.DEFAULT,SpreadType.DOT);
		switchTabs(tabList,roadTab);
	}
	@FXML
	public void buttonSpecial(ActionEvent ae)	{
		setMouseType(OtherTileEnum.DEFAULT,SpreadType.DOT);
		switchTabs(tabList,specialTab);
	}
	@FXML
	public void buttonCancel(ActionEvent ae)	{
		setMouseType(OtherTileEnum.DEFAULT,SpreadType.DOT);
		for(FlowPane pane : tabList)	{
			pane.setVisible(false);
		}
	}
	@FXML
	public void buttonRemove(ActionEvent ae)	{
		setMouseType(OtherTileEnum.REMOVE,SpreadType.AREA);
		for(FlowPane pane : tabList)	{
			pane.setVisible(false);
		}
	}
	@FXML
	public void buttonInfo(ActionEvent ae)	{
		setMouseType(OtherTileEnum.INFO,SpreadType.DOT);
		for(FlowPane pane : tabList)	{
			pane.setVisible(false);
		}
	}
	@FXML
	public void buttonPause(ActionEvent ae)	{
		Main.setTimeFlow(TimeFlow.PAUSE);
	}
	@FXML
	public void buttonLowSpeed(ActionEvent ae)	{
		Main.setTimeFlow(TimeFlow.LOW);
	}
	@FXML
	public void buttonMidSpeed(ActionEvent ae)	{
		Main.setTimeFlow(TimeFlow.MID);
	}
	@FXML
	public void buttonHighSpeed(ActionEvent ae)	{
		Main.setTimeFlow(TimeFlow.HIGH);
	}

	@FXML
	public void mouseMove(MouseEvent me)	{
		mouseX = me.getSceneX();
		mouseY = me.getSceneY();
		mouse = new Point2D(mouseX,mouseY);
		try {
			mouseOnMap = affine.inverseTransform(mouse);
		} catch (NonInvertibleTransformException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	@FXML
	public void dragging(MouseEvent me)		{
		if(!onDrag)	{
			onDrag = true;
			dragStart = mouseOnMap;
		}
		mouseMove(me);
	}
	@FXML
	public void mouseRelease(MouseEvent me)	{
		if(spreadType == SpreadType.DOT)	{
			onDrag = false;
			try {
				Point2D tilePos = posToTilePos(mouseOnMap);
				if(mouseType == OtherTileEnum.INFO)	{
					TileObject cursorTile = map.getTileObject((int)tilePos.getX(),(int)tilePos.getY());
					root.getChildren().add(makeInfoWindow(mouse,cursorTile.type.getDisplayName(),cursorTile.getInfo()));
				}else {
					TileObject o = mouseType.getObject(map,(int)tilePos.getX(),(int)tilePos.getY());
					map.place(o, o.getX(), o.getY());
				}
			}catch(Exception e)	{
				e.printStackTrace();
			}
		}else {
			if(onDrag)	{
				onDrag = false;
				if(spreadType == SpreadType.AREA)	{
					Rectangle rect = spreadSite(dragStart,mouseOnMap);
					int startX = (int)rect.getX()/CommonConst.TILE_SIZE;
					int startY = (int)rect.getY()/CommonConst.TILE_SIZE;

					int endX = (int)(rect.getX() + rect.getWidth())/CommonConst.TILE_SIZE;
					int endY = (int)(rect.getY() + rect.getHeight())/CommonConst.TILE_SIZE;
					try {
						for(int i = startX;i < endX;i++)	{
							for(int j = startY;j < endY;j++)	{
								if(mouseType == OtherTileEnum.REMOVE)	{
									map.remove(i, j);
								}else {
									TileObject o = mouseType.getObject(map,i,j);
									map.place(o, o.getX(), o.getY());
								}
							}
						}
					}catch(Exception e)	{
						e.printStackTrace();
					}
				}else {
					LinkedList<TileObject> path = getLinePath(dragStart,mouseOnMap);
						Way previousWay = null;
					for(TileObject tile : path)	{
						if(tile == null)	{
							continue;
						}
						TileObject o = mouseType.getObject(map,tile.getX(),tile.getY());
						if(o instanceof Way)	{
							Way w = (Way)o;
							if(previousWay != null)	{
								w.connect(previousWay);
							}
							TileObject onMap = map.place(w, w.getX(), w.getY());
							if(onMap instanceof Way)	{
								previousWay = (Way) onMap;
							}else	{
								previousWay = null;
							}
						}else {
							map.place(o, o.getX(), o.getY());
						}
					}
//					if(path.get(0) instanceof Road)	{
//						map.createRoadGraph((Road)path.get(0));
//					}

				}
			}
		}
	}
	@FXML
	public void keyPressed(KeyEvent ke)		{
		switch(ke.getCode())	{
		case W:
			moveY += CommonConst.MOVE_RATE;
			break;
		case A:
			moveX += CommonConst.MOVE_RATE;
			break;
		case S:
			moveY -= CommonConst.MOVE_RATE;
			break;
		case D:
			moveX -= CommonConst.MOVE_RATE;
			break;
		default:
			break;
		}
		//repaint();
	}
	@FXML
	public void zoom(ScrollEvent se)	{
		canvasSizeRate += se.getDeltaY() * SIZE_RATE;
		if(canvasSizeRate < CommonConst.MIN_SIZE_RATE)	{
			canvasSizeRate = CommonConst.MIN_SIZE_RATE;
		}else if(canvasSizeRate > CommonConst.MAX_SIZE_RATE){
			canvasSizeRate = CommonConst.MAX_SIZE_RATE;
		}else {
			this.zoomX = canvasSizeRate/100;
			this.zoomY = canvasSizeRate/100;
			this.pivotX = mouse.getX();
			this.pivotY = mouse.getY();
			System.out.println(canvasSizeRate);
			System.out.println(pivotX);
		}
		//repaint();

	}

	public double pointingTileF(double x)	{
		return Math.floor(x / CommonConst.TILE_SIZE) * CommonConst.TILE_SIZE;
	}
	public double pointingTileC(double x)	{
		return Math.ceil(x / CommonConst.TILE_SIZE) * CommonConst.TILE_SIZE;
	}

	public TitledPaneWithButton makeInfoWindow(Point2D pos,String title,String content)	{
		Label infoContent = new Label(content);
		TitledPaneWithButton infoPane = new TitledPaneWithButton(title,infoContent);
		infoPane.setVisible(true);
		infoPane.setLayoutX(pos.getX());
		infoPane.setLayoutY(pos.getY());
		return infoPane;
	}

	public Rectangle spreadSite(Point2D start,Point2D end)	{
		double topleftX;
		double topleftY;
		double width;
		double height;
		if(start.getX() > end.getX())	{
			topleftX = pointingTileF(end.getX());
			width = pointingTileC(start.getX()) - pointingTileF(end.getX());
		}else {
			topleftX = pointingTileF(start.getX());
			width = pointingTileC(end.getX()) - pointingTileF(start.getX());
		}
		if(start.getY() > end.getY())	{
			topleftY = pointingTileF(end.getY());
			height = pointingTileC(start.getY()) - pointingTileF(end.getY());
		}else {
			topleftY = pointingTileF(start.getY());
			height = pointingTileC(end.getY()) - pointingTileF(start.getY());
		}
		return new Rectangle(topleftX,topleftY,width,height);
	}

	public LinkedList<TileObject> getLinePath(Point2D start,Point2D end)	{
		Point2D sTilePos = posToTilePos(start);
		Point2D eTilePos = posToTilePos(end);
		if((cachePath == null)||(!comparePos(sTilePos,cacheStart)||(!comparePos(eTilePos,cacheEnd)))) {
			AStarFinder finder = new AStarFinder(map);
			LinkedList<Node> nodes = finder.pathFind(
					(int)(sTilePos.getX()), (int)(sTilePos.getY()),
					(int)(eTilePos.getX()), (int)(eTilePos.getY()));
			LinkedList<TileObject> path = new LinkedList<TileObject>();
			while(!nodes.isEmpty())	{
				Node tmpNode = nodes.pop();
				path.add(map.getTileObject(tmpNode.x, tmpNode.y));
			}
			cachePath = path;
			cacheStart = sTilePos;
			cacheEnd = eTilePos;
			return path;
		}
		return cachePath;

	}
	public void switchTabs(ArrayList<FlowPane> tabs,FlowPane tab)	{
		for(FlowPane pane :tabs )	{
			pane.setVisible(false);
			if (pane.equals(tab))	{
				pane.setVisible(true);
			}
		}
	}
	public Point2D posToTilePos(Point2D pos)	{
		return new Point2D(Math.floor(pos.getX()/CommonConst.TILESET_SIZE),Math.floor(pos.getY()/CommonConst.TILESET_SIZE));
	}
	public boolean comparePos(Point2D first,Point2D second)	{
		return (first.getX() == second.getX())&&(first.getY() == second.getY());
	}

	public void setTime(Time time)	{
		this.time = time;
	}
	public DemandBarChart<String, Number> getDemand()	{
		return demand;
	}

	public void repaint()	{
		paintMainCanvas(graphics,map);
		updateLabels();
	}

	public void checkFileError(Exception e,File file)	{
		if(file.exists())	{
			if(file.isFile())	{
				if(file.canRead())	{
					System.out.println(file.getAbsolutePath() + " is not image file or other reason.");
				}else {
					System.out.println(file.getAbsolutePath() + " can't read.");
				}
			}else {
				System.out.println(file.getAbsolutePath() + " is not a file.");
			}
		}else {
			System.out.println(file.getAbsolutePath() + " is not found.");
		}
		e.printStackTrace();
	}


}

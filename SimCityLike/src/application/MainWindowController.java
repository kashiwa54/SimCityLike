package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainWindowController {
	private static final double SIZE_RATE = 0.5;
	private static final double MOVE_RATE = 32;
	private static final double MAX_SIZE_RATE = 300;
	private static final double MIN_SIZE_RATE = 30;
	private static final double SHEAR_ANGLE = Main.SHEAR_ANGLE;
	private static final double TILE_ADJUST_FACTOR_X = ((Main.TILESET_SIZE / 2) / Math.cos(Math.PI * SHEAR_ANGLE / (180 * 2))) / Main.TILE_SIZE;
	private static final double TILE_ADJUST_FACTOR_Y = TILE_ADJUST_FACTOR_X / Math.sqrt(2);
	private static final int TILE_BORDER_WIDTH = 1;
	private static final int CURSOR_WIDTH = 2;

	private static final double IMAGE_OFFSET_Y = -Main.TILESET_SIZE + (Main.TILESET_SIZE * Math.tan(Math.PI * SHEAR_ANGLE / (180 * 2)));

	private static final double SHEAR_X = Math.tan(-1 * Math.PI * SHEAR_ANGLE / 180);
	private static final double ANGLE = SHEAR_ANGLE / 2;

	private static final double BUTTON_SIZE = 64.0;

	@FXML
	AnchorPane root;
	@FXML
	ScrollPane pane;
	@FXML
	StackPane stackpane;
	@FXML
	Canvas canvas;
	@FXML
	HBox maintab;
	GridPane areaTab = new GridPane();
	GridPane roadTab = new GridPane();
	GridPane specialTab = new GridPane();

	ArrayList<GridPane> tabList = new ArrayList<GridPane>(5);
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
	private double moveX = 0;
	private double moveY = 0;

	private Point2D tmpP;
	private Affine affine = new Affine();
	Affine imgAff = new Affine();

	private boolean onDrag = false;
	private Point2D dragStart = mouseOnMap;

	private LinkedList<TileObject> cachePath = null;
	private Point2D cacheStart = null;
	private Point2D cacheEnd = null;

	//TODO別スレッドに移動
	private EnumMap<ResidentalBuildingEnum,Image> residentalMap = new EnumMap<ResidentalBuildingEnum,Image>(ResidentalBuildingEnum.class);
	private EnumMap<WayEnum,EnumMap<DirectionForImage,Image>> wayMap = new EnumMap<WayEnum,EnumMap<DirectionForImage,Image>>(WayEnum.class);

	@FXML
    public void initialize() {
    	graphics = canvas.getGraphicsContext2D();
    	canvas.setFocusTraversable(true);
    	
    	for(ResidentalBuildingEnum type : ResidentalBuildingEnum.values())	{
    		residentalMap.put(type,new Image(type.getImagePath()));
    	}
    	for(WayEnum type : WayEnum.values())	{
    		BufferedImage imageSet = null;
			try {
				File file = new File(type.getImageSetPath());
				imageSet = ImageIO.read(file);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
    		EnumMap<DirectionForImage,Image> imageSetMap = new EnumMap<DirectionForImage,Image>(DirectionForImage.class);
    		
    		int tileX = imageSet.getWidth()/Main.TILESET_SIZE;
    		int tileY = imageSet.getHeight()/Main.TILESET_SIZE;
    		int cnt = 0;
    		for(DirectionForImage d : DirectionForImage.values())	{
    			imageSetMap.put(d,SwingFXUtils.toFXImage(imageSet.getSubimage(cnt%tileX * Main.TILESET_SIZE, cnt/tileY * Main.TILESET_SIZE, Main.TILESET_SIZE, Main.TILESET_SIZE), null));
    		}
    		wayMap.put(type,imageSetMap);
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

    	System.out.println("Main window initialized.");
    }

	public <T extends TabableEnum> void creatTab(GridPane tab, T[] tabs)	{
    	tab.setVisible(false);
    	AnchorPane.setTopAnchor(tab, BUTTON_SIZE);
    	AnchorPane.setLeftAnchor(tab, 0.0);
    	AnchorPane.setRightAnchor(tab, BUTTON_SIZE);
    	tab.setPrefSize(Main.WINDOW_WIDTH, BUTTON_SIZE);
    	tab.setPadding(new Insets(0, 16, 0, 16));
    	Button[] tabButtons = new Button[tabs.length];
    	for(int i = 0;i < tabs.length;i++)	{
    		tabButtons[i] = new Button();
    		tabButtons[i].setMaxSize(BUTTON_SIZE,BUTTON_SIZE);
    		tabButtons[i].setMinSize(BUTTON_SIZE, BUTTON_SIZE);
    		tabButtons[i].setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
    		Text text = new Text((tabs[i]).getDisplay());
    		text.getStyleClass().add("button-label");
    		text.setWrappingWidth(BUTTON_SIZE);
    		tabButtons[i].setGraphic(text);
			tabButtons[i].setStyle("-fx-background-image:url(\"" + tabs[i].getImagePath() + "\");");
			tab.add(tabButtons[i], i, 0);
			PlacableEnum mouseType = tabs[i].getType();
			SpreadType spread = tabs[i].getSpread();
			tabButtons[i].setOnAction(ActonEvent ->{
				setMouseType(mouseType,spread);
			});
    	}
	}
	public void setMap(Map map)		{
		this.map = map;
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
		//System.out.println(affine);
		gc.setTransform(affine);

		gc.setFill(Color.KHAKI);
		gc.fillRect(0, 0, map.getWidth() * Main.TILE_SIZE, map.getHeight() * Main.TILE_SIZE);

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(TILE_BORDER_WIDTH);
		for(int i = 0;i <= map.getWidth();i++)	{
			gc.strokeLine(i * Main.TILE_SIZE, 0, i * Main.TILE_SIZE, map.getHeight() * Main.TILE_SIZE);
		}
		for(int i = 0;i <= map.getHeight();i++)	{
			gc.strokeLine(0, i * Main.TILE_SIZE, map.getWidth() * Main.TILE_SIZE, i * Main.TILE_SIZE);
		}
		for(int i = 0;i < map.getWidth();i++)	{
			for(int j = 0;j < map.getHeight();j++)	{
				PlacableEnum type = map.getTileObject(i, j).type;
				if(type instanceof SiteEnum)	{
					gc.setFill(((SiteEnum) type).getColor());
					gc.fillRect(i * Main.TILE_SIZE,j * Main.TILE_SIZE,Main.TILE_SIZE,Main.TILE_SIZE);
				}else if(type instanceof WayEnum)	{
					Way way = (Way)map.getTileObject(i, j);
					DirectionForImage connect = DirectionForImage.setToDirectionForImage(way.getConnect());
					
					imgAff.setToTransform(1,0,0,0,1,0);
					tmpP = affine.transform(i * Main.TILE_SIZE,j * Main.TILE_SIZE);
					imgAff.appendScale(zoomX,zoomY,tmpP.getX(),tmpP.getY());
					gc.setTransform(imgAff);
					gc.drawImage(wayMap.get(type).get(connect),tmpP.getX() - (Main.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
					gc.setTransform(affine);
				}else if(type instanceof ResidentalBuildingEnum)	{
					imgAff.setToTransform(1,0,0,0,1,0);
					tmpP = affine.transform(i * Main.TILE_SIZE,j * Main.TILE_SIZE);
					imgAff.appendScale(zoomX,zoomY,tmpP.getX(),tmpP.getY());
					gc.setTransform(imgAff);
					gc.drawImage(residentalMap.get(type),tmpP.getX() - (Main.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
					gc.setTransform(affine);
				}
				
			}
		}

		if(spreadType == SpreadType.DOT)	{
			if ((mouseOnMap.getX() >= 0)&&(mouseOnMap.getY() >= 0)&&(mouseOnMap.getX() <= Main.TILE_WIDTH * Main.TILE_SIZE)&&(mouseOnMap.getY() <= Main.TILE_HEIGHT * Main.TILE_SIZE))	{
				imgAff.setToTransform(1,0,0,0,1,0);
				tmpP = affine.transform(pointingTileF(mouseOnMap.getX()),pointingTileF(mouseOnMap.getY()));
				imgAff.appendScale(zoomX,zoomY,tmpP.getX(),tmpP.getY());
				gc.setTransform(imgAff);

				gc.setGlobalAlpha(0.3);
				
				if(mouseType instanceof ResidentalBuildingEnum)	{
					gc.drawImage(residentalMap.get(mouseType),tmpP.getX() - (Main.TILESET_SIZE / 2),tmpP.getY() + IMAGE_OFFSET_Y);
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
							gc.fillRect(object.getX() * Main.TILESET_SIZE, object.getY() * Main.TILESET_SIZE, Main.TILESET_SIZE, Main.TILESET_SIZE);
						}
					}
				}
				gc.setGlobalAlpha(1.0);
			}
			gc.setTransform(affine);
			gc.setStroke(Color.YELLOW);
			gc.setLineWidth(CURSOR_WIDTH);
			gc.strokeRect(pointingTileF(mouseOnMap.getX()), pointingTileF(mouseOnMap.getY()), Main.TILE_SIZE, Main.TILE_SIZE);
		}
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
		for(GridPane pane : tabList)	{
			pane.setVisible(false);
		}
	}
	@FXML
	public void buttonRemove(ActionEvent ae)	{
		setMouseType(OtherTileEnum.REMOVE,SpreadType.AREA);
		for(GridPane pane : tabList)	{
			pane.setVisible(false);
		}
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
		//repaint();
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
				
				TileObject o = mouseType.getObject((int)tilePos.getX(),(int)tilePos.getY());
				map.place(o, o.getX(), o.getY());
				
			}catch(Exception e)	{
				e.printStackTrace();
			}
		}else {
			if(onDrag)	{
				onDrag = false;
				if(spreadType == SpreadType.AREA)	{
					Rectangle rect = spreadSite(dragStart,mouseOnMap);
					int startX = (int)rect.getX()/Main.TILE_SIZE;
					int startY = (int)rect.getY()/Main.TILE_SIZE;

					int endX = (int)(rect.getX() + rect.getWidth())/Main.TILE_SIZE;
					int endY = (int)(rect.getY() + rect.getHeight())/Main.TILE_SIZE;
					try {
						for(int i = startX;i < endX;i++)	{
							for(int j = startY;j < endY;j++)	{
								if(mouseType == OtherTileEnum.REMOVE)	{
									map.remove(i, j);
								}else {
									TileObject o = mouseType.getObject(i,j);
									map.place(o, o.getX(), o.getY());
								}
							}
						}
					}catch(Exception e)	{
						e.printStackTrace();
					}
				}else {
					LinkedList<TileObject> path = getLinePath(dragStart,mouseOnMap);
					for(TileObject tile : path)	{
						if(tile == null)	{
							continue;
						}
						TileObject o = mouseType.getObject(tile.getX(),tile.getY());
						map.place(o, o.getX(), o.getY());
					}
				}
			}
		}
	}
	@FXML
	public void keyPressed(KeyEvent ke)		{
		switch(ke.getCode())	{
		case W:
			moveY += MOVE_RATE;
			break;
		case A:
			moveX += MOVE_RATE;
			break;
		case S:
			moveY -= MOVE_RATE;
			break;
		case D:
			moveX -= MOVE_RATE;
			break;
		default:
			break;
		}
		//repaint();
	}
	@FXML
	public void zoom(ScrollEvent se)	{
		canvasSizeRate += se.getDeltaY() * SIZE_RATE;
		if(canvasSizeRate < MIN_SIZE_RATE)	{
			canvasSizeRate = MIN_SIZE_RATE;
		}else if(canvasSizeRate > MAX_SIZE_RATE){
			canvasSizeRate = MAX_SIZE_RATE;
		}else {
			this.zoomX = canvasSizeRate/100;
			this.zoomY = canvasSizeRate/100;
			this.pivotX = mouseX;
			this.pivotY = mouseY;
			System.out.println(canvasSizeRate);
			System.out.println(pivotX);
		}
		//repaint();

	}

	public double pointingTileF(double x)	{
		return Math.floor(x / Main.TILE_SIZE) * Main.TILE_SIZE;
	}
	public double pointingTileC(double x)	{
		return Math.ceil(x / Main.TILE_SIZE) * Main.TILE_SIZE;
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
	public void switchTabs(ArrayList<GridPane> tabs,GridPane tab)	{
		for(GridPane pane :tabs )	{
			pane.setVisible(false);
			if (pane.equals(tab))	{
				pane.setVisible(true);
			}
		}
	}
	public Point2D posToTilePos(Point2D pos)	{
		return new Point2D(Math.floor(pos.getX()/Main.TILESET_SIZE),Math.floor(pos.getY()/Main.TILESET_SIZE));
	}
	public boolean comparePos(Point2D first,Point2D second)	{
		return (first.getX() == second.getX())&&(first.getY() == second.getY());
	}

	public void repaint()	{
		paintMainCanvas(graphics,map);
	}


}

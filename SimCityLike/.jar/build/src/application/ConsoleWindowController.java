package application;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ConsoleWindowController	{
	@FXML
	private AnchorPane root;
	@FXML
	private TextField textbox;
	@FXML
	private TextArea textarea;

	private MainWindowController mwc;

	private Map map;
	private GraphicsContext graphics;

	public void setMap(Map map)	{
		this.map = map;
	}
	public Map getMap()	{
		return this.map;
	}
	public void setGraphicsContext(GraphicsContext gc)	{
		this.graphics = gc;
	}
	public void setMWController(MainWindowController mwc)	{
		this.mwc = mwc;
	}
	@FXML
	public void writeConsole(ActionEvent ae)	{					//イベントハンドラ(ActionEvent)で呼ばれるメソッド
		String input;												//具体的にはコンソールに何か書いてEnterで呼ばれる
		StringBuilder builder = new StringBuilder();
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		builder.append("Console Mode\n");
		builder.append("Please input command.\n");
		input = textbox.getText();
		textbox.setText("");
		String[] arg = input.split(" ");
		for(String value : arg)	{
			builder.append(value + "_");
		}
		builder.append("\n");

		switch(arg[0])	{
		case "end" :
			builder.append("shutdown...\n");
			break;

		case "area" :
			try	{
				x = Integer.parseInt(arg[2]);
				y = Integer.parseInt(arg[3]);
				width = Integer.parseInt(arg[4]);
				height = Integer.parseInt(arg[5]);

			}catch(NumberFormatException e)	{
				builder.append("area command format is\n");
				builder.append("area [building name] [position x] [position y] [width] [height]\n");
			}
			switch(arg[1])	{
			case "residental" :
				for(int i = 0;i <= width;i++)	{
					for(int j = 0;j <= height;j++)	{
						Residental r = new Residental(map,x + i,y + j);
						this.map.place(r, r.getX(),r.getY());
						builder.append("Place residental in (" + r.getX() + "," + r.getY() + ") successful\n");
					}
				}
				break;
			case "commercial" :
				for(int i = 0;i <= width;i++)	{
					for(int j = 0;j <= height;j++)	{
						Commercial r = new Commercial(map,x + i,y + j);
						this.map.place(r, r.getX(),r.getY());
						builder.append("Place commercial in (" + r.getX() + "," + r.getY() + ") successful\n");
					}
				}
				break;
			case "industrial" :
				for(int i = 0;i <= width;i++)	{
					for(int j = 0;j <= height;j++)	{
						Industrial r = new Industrial(map,x + i,y + j);
						this.map.place(r, r.getX(),r.getY());
						builder.append("Place industrial in (" + r.getX() + "," + r.getY() + ") successful\n");
					}
				}
				break;
			default :
				builder.append("Cannot find this area name.\n");
			}
			break;

		case "remove" :
			try	{
				x = Integer.parseInt(arg[1]);
				y = Integer.parseInt(arg[2]);

			}catch(Exception e)	{
				e.printStackTrace();
				builder.append("remove command format is\n");
				builder.append("remove [position x] [position y]\n");
			}
			this.map.remove(x,y);
			break;

		case "place" :
			try	{
				boolean isFind = false;
				x = Integer.parseInt(arg[2]);
				y = Integer.parseInt(arg[3]);
				for(ResidentalBuildingEnum b : ResidentalBuildingEnum.values()) {
					if (b.getObjectClass().getSimpleName().equalsIgnoreCase(arg[1]))	{
						TileObject o = b.getObject(map,x,y);
						this.map.place(o, o.getX(),o.getY());
						builder.append("Place in (" + o.getX() + "," + o.getY() + ") successful\n");
						isFind = true;
						break;
					}
				}
				if (isFind)	break;
				for(WayEnum b : WayEnum.values()) {
					if (b.getObjectClass().getSimpleName().equalsIgnoreCase(arg[1]))	{
						TileObject o = b.getObject(map,x,y);
						this.map.place(o, o.getX(),o.getY());
						builder.append("Place in (" + o.getX() + "," + o.getY() + ") successful\n");
						isFind = true;
						break;
					}
				}
				if (isFind)	break;
				builder.append("Cannot find this building name.\n");
			}catch(Exception e)	{
				e.printStackTrace();
				builder.append("place command format is\n");
				builder.append("place [position x] [position y]\n");
			}
			break;

		case "people" :
			PeopleManager pm = map.getPeopleManager();
			switch(arg[1])	{
			case "list" :
				builder.append("--------------------------------\n");
				for(People p : pm.getPeopleList())	{
					if(p != null)	{
						builder.append(p.toString() + "\n");
					}
				}
				builder.append("--------------------------------\n");
				break;
			case "create" :
				try {
					int age = Integer.parseInt(arg[2]);
					pm.createPeople(age);
				}catch(Exception e)	{
					e.printStackTrace();
					builder.append("people create command format is \n");
					builder.append("people create [age].\n");
				}
				break;
			case "bulk" :
				try {
					int number = Integer.parseInt(arg[2]);
					for(int i = 0; i < number;i++)	{
						pm.createPeople();
					}
				}catch(Exception e)	{
					e.printStackTrace();
					builder.append("people bulk command format is \n");
					builder.append("people bulk [number].\n");
				}
				break;
			case "desire" :
				builder.append("--------------------------------\n");
				for(People p : pm.getPeopleList())	{
					if(p != null)	{
						builder.append(p.getName() + "\n");
						for(Desire d : p.getDesireSet())	{
							builder.append(d + " : " + p.getDesire(d) + "\n");
						}

					}
				}
				builder.append("--------------------------------\n");
				break;
			default :
				builder.append("Available augments of people command are\n");
				builder.append(" [list] [create [age]].\n");
			}
			break;
		case "route":
			try {
				int sx = Integer.parseInt(arg[2]);
				int sy = Integer.parseInt(arg[3]);
				int ex = Integer.parseInt(arg[4]);
				int ey = Integer.parseInt(arg[5]);
				Road start;
				Road end;
				if(!(map.getTileObject(sx, sy) instanceof Road)&&(map.getTileObject(ex, ey) instanceof Road))	{
					builder.append("[" + sx + "," + sy + "] or [" + ex + "," + ey + "] is not Road.");
					break;
				}else {
					start = (Road)map.getTileObject(sx, sy);
					end = (Road)map.getTileObject(ex, ey);
				}
				switch(arg[1])	{
				case "cost":
					int cost = map.getRouteCost(start, end);
					builder.append("[" + sx + "," + sy + "] to [" + ex + "," + ey + "] cost is "+ cost +" .\n");
					break;
				case "node":
					LinkedList<GraphNode> route = map.getRoute(start, end);
					GraphNode node;
					while(!route.isEmpty())	{
						node = route.pop();
						builder.append("[" + node.getRoad().getX() + "," + node.getRoad().getY() + "]\n");
					}
					break;
				}


			}catch(Exception e)	{
				e.printStackTrace();
				builder.append("route command format is \n");
				builder.append("route [node or cost] [startX] [startY] [endX] [endY].\n");
			}
			break;
		case "node":
			try {
				int sx = Integer.parseInt(arg[1]);
				int sy = Integer.parseInt(arg[2]);
				Road start;
				if(!(map.getTileObject(sx, sy) instanceof Road))	{
					builder.append("[" + sx + "," + sy + "] is not Road.");
					break;
				}else {
					start = (Road)map.getTileObject(sx, sy);
				}
				RoadGraph graph = map.createRoadGraph(start);
				LinkedList<GraphNode> list = graph.getNodeList();
				for(GraphNode n : list)	{
					builder.append("[" + n.getRoad().getX() + "," + n.getRoad().getY() + "]\n");
				}
			}catch(Exception e)	{
				e.printStackTrace();
				builder.append("node command format is \n");
				builder.append("node [startX] [startY].\n");
			}
			break;
		default :
			builder.append("cannot find this command.\n");
		}
//		if(arg[0].equals("end"))	{
//			builder.append("shutdown...\n");
//			//break;
//		}else if(arg[0].equals("write"))	{
//			for(int i = 0;i < this.map.getHeight();i++)	{
//				for(int j = 0;j < this.map.getWidth(); j++)	{
//					if(this.map.getTileObject(j,i) instanceof Space)	{
//						builder.append("□");
//					}else if (this.map.getTileObject(j, i)instanceof Cottage){
//						builder.append("△");
//					}else if (this.map.getTileObject(j, i)instanceof Residental){
//						builder.append("住");
//					}
//				}
//				builder.append("\n");
//			}
//		}else if(arg[0].equals("site"))	{
//			try	{
//				x = Integer.parseInt(arg[2]);
//				y = Integer.parseInt(arg[3]);
//				width = Integer.parseInt(arg[4]);
//				height = Integer.parseInt(arg[5]);
//
//			}catch(NumberFormatException e)	{
//				builder.append("site command format is\n");
//				builder.append("site [building name] [position x] [position y] [width] [height]\n");
//			}
//			if(arg[1].equals("residental"))	{
//				for(int i = 0;i <= width;i++)	{
//					for(int j = 0;j <= height;j++)	{
//						Residental r = new Residental(x + i,y + j);
//						this.map.place(r, r.getX(),r.getY());
//						builder.append("Place residental in (" + r.getX() + "," + r.getY() + ") successful\n");
//					}
//				}
//
//			}else {
//				builder.append("Cannot find this site name.\n");
//			}
//		}else if(arg[0].equals("remove"))	{
//			try	{
//				x = Integer.parseInt(arg[1]);
//				y = Integer.parseInt(arg[2]);
//
//			}catch(Exception e)	{
//				builder.append("remove command format is\n");
//				builder.append("remove [position x] [position y]\n");
//			}
//			this.map.remove(x,y);
//		}else if(arg[0].equals("place"))	{
//			try	{
//				x = Integer.parseInt(arg[2]);
//				y = Integer.parseInt(arg[3]);
//				if(arg[1].equals("cottage"))	{
//					Cottage c = new Cottage(x,y);
//					this.map.place(c, c.getX(),c.getY());
//					builder.append("Place cottage in (" + c.getX() + "," + c.getY() + ") successful\n");
//				}else {
//					builder.append("Cannot find this building name.\n");
//				}
//			}catch(Exception e)	{
//				builder.append("place command format is\n");
//				builder.append("place [position x] [position y]\n");
//			}
//
//		}
		textarea.appendText(builder.toString());
		mwc.paintMainCanvas(graphics,map);
	}
	public void write(String str)	{
		textarea.appendText(str);
	}

}

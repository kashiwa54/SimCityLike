package application;

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
		StringBuffer buffer = new StringBuffer();
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		buffer.append("Console Mode\n");
		buffer.append("Please input command.\n");
		input = textbox.getText();
		textbox.setText("");
		String[] arg = input.split(" ");
		for(String value : arg)	{
			buffer.append(value + "_");
		}
		buffer.append("\n");
		if(arg[0].equals("end"))	{
			buffer.append("shutdown...\n");
			//break;
		}else if(arg[0].equals("write"))	{
			for(int i = 0;i < this.map.getHeight();i++)	{
				for(int j = 0;j < this.map.getWidth(); j++)	{
					if(this.map.getTileObject(j,i) instanceof Space)	{
						buffer.append("□");
					}else if (this.map.getTileObject(j, i)instanceof Cottage){
						buffer.append("△");
					}else if (this.map.getTileObject(j, i)instanceof Residental){
						buffer.append("住");
					}
				}
				buffer.append("\n");
			}
		}else if(arg[0].equals("site"))	{
			try	{
				x = Integer.parseInt(arg[2]);
				y = Integer.parseInt(arg[3]);
				width = Integer.parseInt(arg[4]);
				height = Integer.parseInt(arg[5]);

			}catch(NumberFormatException e)	{
				buffer.append("site command format is\n");
				buffer.append("site [building name] [position x] [position y] [width] [height]\n");
			}
			if(arg[1].equals("residental"))	{
				for(int i = 0;i <= width;i++)	{
					for(int j = 0;j <= height;j++)	{
						Residental r = new Residental(x + i,y + j);
						this.map.place(r, r.getX(),r.getY());
						buffer.append("Place residental in (" + r.getX() + "," + r.getY() + ") successful\n");
					}
				}

			}else {
				buffer.append("Cannot find this site name.\n");
			}
		}else if(arg[0].equals("remove"))	{
			try	{
				x = Integer.parseInt(arg[1]);
				y = Integer.parseInt(arg[2]);

			}catch(Exception e)	{
				buffer.append("remove command format is\n");
				buffer.append("remove [position x] [position y]\n");
			}
			this.map.remove(x,y);
		}else if(arg[0].equals("place"))	{
			try	{
				x = Integer.parseInt(arg[2]);
				y = Integer.parseInt(arg[3]);
				if(arg[1].equals("cottage"))	{
					Cottage c = new Cottage(x,y);
					this.map.place(c, c.getX(),c.getY());
					buffer.append("Place cottage in (" + c.getX() + "," + c.getY() + ") successful\n");
				}else {
					buffer.append("Cannot find this building name.\n");
				}
			}catch(Exception e)	{
				buffer.append("place command format is\n");
				buffer.append("place [position x] [position y]\n");
			}

		}
		textarea.appendText(buffer.toString());
		mwc.paintMainCanvas(graphics,map);
	}
	public void write(String str)	{
		textarea.appendText(str);
	}

}

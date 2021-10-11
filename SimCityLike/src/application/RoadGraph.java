package application;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Stack;

public class RoadGraph {
	private GraphNode startNode;
	private LinkedList<Road> nodeRoadList = new LinkedList<Road>();

	public RoadGraph(Road road)	{
		startNode = new GraphNode(road);

		createGraph(startNode);
	}

	private void createGraph(GraphNode startNode)	{
		Stack<GraphNode> stack = new Stack<GraphNode>();
		stack.push(startNode);
		while(!stack.empty())	{
			GraphNode node = stack.pop();
			if(node.getRoad().getConnect().size() >= 2)	{
				for(Direction d : node.getRoad().getConnect())	{
					int dCost = 1;
					Way nextWay = node.getRoad();
					Direction nextDir = d;
					EnumSet<Direction> connection = nextWay.getConnectWay(nextDir).getConnect();
					while(connection.size() == 2)	{
						dCost++;
						nextWay = nextWay.getConnectWay(nextDir);
						for(Direction dir : connection)	{
							if(dir != Direction.reverse(d))	{
								nextDir = dir;
							}
						}
						connection = nextWay.getConnectWay(nextDir).getConnect();
					}
					if(!nodeRoadList.contains((Road)nextWay))	{
						node.addNode(new GraphNode((Road) nextWay), d, dCost);
						nodeRoadList.add((Road)nextWay);
					}
				}
			}
		}
	}


}

class GraphNode {
	private Road road;

	private GraphNode[] edgeTo = new GraphNode[4];
	private int[] edgeCost = new int[4];

	private boolean done;
	private int cost;
	private GraphNode from;

	public GraphNode(Road road)	{
		this.road = road;
		done = false;
		cost = -1;
	}

	public Road getRoad()	{
		return road;
	}
	public GraphNode getFrom()	{
		return from;
	}
	public boolean getDone()	{
		return done;
	}
	public int getCost()	{
		return cost;
	}
	public void addNode(GraphNode node,Direction d,int cost)	{
		int index = convertDirection(d);
		edgeTo[index] = node;
		edgeCost[index] = cost;

		int rIndex = convertDirection(Direction.reverse(d));
		node.edgeTo[rIndex] = this;
		node.edgeCost[rIndex] = cost;
	}

	private int convertDirection(Direction d)	{
		int index = 0;
		switch(d) {
		case NORTH:
			index = 0;
			break;
		case EAST:
			index = 1;
			break;
		case SOUTH:
			index = 2;
			break;
		case WEST:
			index = 3;
			break;
		default:
			break;
		}

		return index;
	}
}

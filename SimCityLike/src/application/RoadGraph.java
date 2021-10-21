package application;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class RoadGraph {
	private GraphNode firstNode;
	private LinkedList<GraphNode> nodeList = new LinkedList<GraphNode>();
	private GraphNode startNode;
	private GraphNode endNode;

	public RoadGraph(Road road)	{
		firstNode = new GraphNode(road);
		nodeList.add(firstNode);
		createGraph(firstNode);
	}

	public LinkedList<GraphNode> getNodeList()	{
		return nodeList;
	}

	public int getRouteCost(Road start,Road end)	{
		LinkedList<GraphNode> path = calcPath(start,end);
		return path.getLast().getCost();
	}

	public LinkedList<GraphNode> calcPath(Road start,Road end)	{
		LinkedList<GraphNode> route = new LinkedList<GraphNode>();
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
		GraphNode doneNode = null;


		addEdgeNode(start,end);
		queue.add(startNode);


		for(GraphNode n : nodeList)	{
			n.setDone(false);
			n.setCost(-1);
			n.setFrom(null);
		}

		queue.peek().setCost(0);
		while(!queue.isEmpty())	{
			doneNode = queue.poll();
			doneNode.setDone(true);
			GraphNode[] edgeTo = doneNode.getEdgeTo();
			int[] edgeCost = doneNode.getEdgeCost();
			for(int i = 0; i < 4; i++)	{
				if((edgeTo[i] != null)&&(!edgeTo[i].getDone()))	{
					int cost = edgeCost[i] + doneNode.getCost();
					if((cost < edgeTo[i].getCost())||(edgeTo[i].getCost() < 0))	{
						edgeTo[i].setCost(cost);
						edgeTo[i].setFrom(doneNode);
						if(!queue.contains(edgeTo[i]))	{
							queue.add(edgeTo[i]);
						}
					}
				}
			}
		}
		GraphNode currentNode = endNode;
		route.addFirst(endNode);
		while(currentNode.getFrom() != null)	{
			currentNode = currentNode.getFrom();
			route.addFirst(currentNode);
		}
		return route;
	}

	public GraphNode[] addNode(GraphNode node)	{
		GraphNode[] newNodes = new GraphNode[4];
		int index = 0;
		boolean dupflg = false;
		if(node.getRoad().getConnect().size() <= 1)	{
			for(GraphNode n : node.getEdgeTo())	{
				if(n != null) break;
			}
		}
		for(Direction d : node.getRoad().getConnect())	{
			dupflg = false;
			int dCost = 1;
			Way nextWay = node.getRoad();
			Direction nextDir = d;
			EnumSet<Direction> connection = nextWay.getConnectWay(nextDir).getConnect();
			while(connection.size() == 2)	{
				boolean loopflg = false;
				dCost++;

				nextWay = nextWay.getConnectWay(nextDir);
				if((startNode != null)&&(endNode != null))	{
					if(startNode.getRoad() == (Road)nextWay){
						dupflg = true;
						node.addEdge(startNode,d,dCost);
						break;
					}else if(endNode.getRoad() == (Road)nextWay)	{
						dupflg = true;
						node.addEdge(endNode,d,dCost);
						break;
					}
				}

				for(GraphNode n : nodeList)	{
					if (n.getRoad() == (Road)nextWay) loopflg = true;
					break;
				}
				if(loopflg) break;
				Direction comeDir = Direction.reverse(nextDir);
				for(Direction dir : connection)	{
					if(dir != comeDir)	{
						nextDir = dir;
					}
				}
				connection = nextWay.getConnectWay(nextDir).getConnect();
			}
			if (nextWay.getConnectWay(nextDir) == null)	{
				System.out.println(nextDir);
				System.out.println(nextWay.getX() + "," + nextWay.getY());
				System.out.println(nextWay.getConnect());
				continue;
			}
			nextWay = nextWay.getConnectWay(nextDir);
			for(GraphNode n : nodeList)	{
				if(n.getRoad() == (Road)nextWay)	{
					dupflg = true;
					node.addEdge(n,d,dCost);
					break;
				}
			}
			if(!dupflg)	{
				GraphNode newNode = new GraphNode((Road) nextWay);
				node.addEdge(newNode, d, dCost);
				nodeList.add(newNode);
				newNodes[index] = newNode;
				index++;
			}
		}
		return newNodes;
	}

	public void addEdgeNode(Road start,Road end)	{
		boolean dupflg = false;
		for(GraphNode n : nodeList)	{
			if(n.getRoad() == start) {
				dupflg = true;
				startNode = n;
				break;
			}
		}
		if(!dupflg)	{
			GraphNode newNode = new GraphNode(start);
			nodeList.add(newNode);
			startNode = newNode;
		}

		dupflg = false;
		for(GraphNode n : nodeList)	{
			if(n.getRoad() == end) {
				dupflg = true;
				endNode = n;
				break;
			}
		}
		if(!dupflg)	{
			GraphNode newNode = new GraphNode(end);
			nodeList.add(newNode);
			endNode = newNode;
		}
		addNode(startNode);
		addNode(endNode);
	}
	public GraphNode[] addNode(Road road)	{
		GraphNode node = new GraphNode(road);
		return addNode(node);
	}

	private void createGraph(GraphNode startNode)	{
		Stack<GraphNode> stack = new Stack<GraphNode>();
		stack.push(startNode);
		while(!stack.empty())	{
			GraphNode node = stack.pop();
			GraphNode[] newNodes = addNode(node);
			for(GraphNode n : newNodes)	{
				if(n != null)	{
					stack.push(n);
				}
			}
		}
	}

}

class GraphNode implements Comparable<GraphNode>{
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
	public GraphNode[] getEdgeTo()	{
		return edgeTo;
	}
	public int[] getEdgeCost()	{
		return edgeCost;
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
	public void setDone(boolean done)	{
		this.done = done;
	}
	public void setCost(int cost)	{
		this.cost = cost;
	}
	public void setFrom(GraphNode node)	{
		this.from = node;
	}
	public void addEdge(GraphNode node,Direction d,int cost)	{
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

	@Override
	public int compareTo(GraphNode o) {
		if(this.cost == o.cost)	{
			return 0;
		}else if(this.cost < o.cost) {
			return -1;
		}else {
			return 1;
		}
	}
}

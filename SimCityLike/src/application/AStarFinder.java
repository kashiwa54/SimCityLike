package application;

import java.util.LinkedList;

public class AStarFinder {
	private LinkedList<Node> open = new LinkedList<Node>();
	private LinkedList<Node> close = new LinkedList<Node>();
	private Map map;

	public AStarFinder()	{

	}
	public AStarFinder(Map map)	{
		this.map = map;
	}

	public void setMap(Map map)	{
		this.map = map;
	}

	public LinkedList<Node> pathFind(int sx,int sy,int ex,int ey)	{
		Node start = new Node();
		Node end = new Node();
		Node current = new Node();
		LinkedList<Node> route = new LinkedList<Node>();
		start.x = sx;
		start.y = sy;
		end.x = ex;
		end.y = ey;

		moveNearPassableNode(start,end);
		moveNearPassableNode(end,start);
		start.realCost = 0;
		start.calcHeuristicCost(end);
		open.add(start);

		while(!open.isEmpty())	{
			current = open.pop();
			close.add(current);
			if(current.equals(end))	{
				//System.out.println("end");
				popParentNode(current,route);
				//System.out.println(route);
				return route;
			}

			Node[] neighbors = getNeighbors(current);
			Node minNode = null;
			for(Node node : neighbors)	{
				if(node == null)	{
					continue;
				}
				openNode(node, current, end);
				if(minNode == null)	{
					minNode = node;
				}else {
					if(node.score < minNode.score)	{
						minNode = node;
					}else if(node.score == minNode.score)	{
						if(node.realCost < minNode.realCost)	{
							minNode = node;
						}
					}
				}
			}

			if (minNode == null)	{
				continue;
			}else {
				minNode.parent = current;
				if(!open.contains(minNode))	{
					if(!close.contains(minNode)) {
						open.addFirst(minNode);
						//System.out.println(minNode + " add first");
					}else {
						Node tmp = close.get(close.indexOf(minNode));
						if(minNode.score <= tmp.score)	{
							close.remove(close.indexOf(minNode));
							open.addFirst(minNode);
							//System.out.println(tmp + " remove");
							//System.out.println(minNode + " add first");
						}
					}
				}else {
					Node tmp = open.get(open.indexOf(minNode));
					if(minNode.score <= tmp.score)	{
						open.remove(open.indexOf(minNode));
						open.addFirst(minNode);
						//System.out.println(tmp + " remove");
						//System.out.println(minNode + " add first");
					}
				}
			}
//			open.sort(new Comparator<Node>()	{
//				@Override
//				public int compare(Node o1, Node o2) {
//					return (int)o1.score - (int)o2.score;
//				}
//			});
		}
		return route;
	}

	private void openNode(Node next,Node current,Node end)	{
		if(!open.contains(next))	{
			if(!close.contains(next)) {
				next.parent = current;
				next.calcScore(end);
				open.add(next);
				//System.out.println(next + " add");
			}else {
				Node tmp = close.get(close.indexOf(next));
				tmp.calcScore(end);
				next.calcScore(end);
				if(next.score < tmp.score)	{
					close.remove(close.indexOf(next));
					next.parent = current;
					open.add(next);
					//System.out.println(tmp + " remove");
					//System.out.println(next + " add");
				}
			}
		}else {
			Node tmp = open.get(open.indexOf(next));
			tmp.calcScore(end);
			next.calcScore(end);
			if(next.score < tmp.score)	{
				open.remove(open.indexOf(next));
				next.parent = current;
				open.add(next);
				//System.out.println(tmp + " remove");
				//System.out.println(next + " add");
			}
		}
	}
	private Node[] getNeighbors(Node node)	{
		Node[] neighbors = new Node[4];
		for(int i = 0;i < neighbors.length;i++)	{
			int x = node.x;
			int y = node.y;
			switch(i)	{
			case 0 :
				y--;
				break;
			case 1 :
				x++;
				break;
			case 2 :
				y++;
				break;
			case 3 :
				x--;
			}
			if((x < 0)||(y < 0)||(x >= map.getWidth())||(y >= map.getHeight()))	{
				//System.out.println("Out of Map" + x + "," + y + "[" + i + "]");
				continue;
			}
			if(!map.getTileObject(x,y).getCanPass())	{
				//System.out.println("Can't pass in " + x + "," + y + "[" + i + "]");
				continue;
			}
			neighbors[i] = new Node(x,y);
			neighbors[i].parent = node;
		}
		return neighbors;
	}
	private void moveNearPassableNode(Node start,Node end)	{
		if(start.equals(end))	{
			return;
		}
		if(isNodeInMap(start))	{
			if(map.getTileObject(start.x, start.y).getCanPass())	{
				return;
			}
			while(!map.getTileObject(start.x, start.y).getCanPass()&&(!start.equals(end)))	{
				if(start.x - end.x < 0)	{
					start.x++;
				}else if(start.x - end.x > 0){
					start.x--;
				}
				if(start.y - end.y < 0)	{
					start.y++;
				}else if(start.y - end.y > 0){
					start.y--;
				}
			}
			return;
		}else {
			while(!isNodeInMap(start)&&(!start.equals(end)))	{
				if(start.x - end.x < 0)	{
					start.x++;
				}else if(start.x - end.x > 0){
					start.x--;
				}
				if(start.y - end.y < 0)	{
					start.y++;
				}else if(start.y - end.y > 0){
					start.y--;
				}
			}
			moveNearPassableNode(start,end);
		}
		return;

	}
	private boolean isNodeInMap(Node node)	{
		if((node.x < 0)||(node.y < 0)||(node.x >= map.getWidth())||(node.y >= map.getHeight()))	{
			//System.out.println("Out of Map" + x + "," + y + "[" + i + "]");
			return false;
		}else {
			return true;
		}
	}

	private void popParentNode(Node node,LinkedList<Node> list)	{
		if(node.parent != null)	{
			list.add(node);
			//System.out.println(node.parent + " pop");
			node = node.parent;
			popParentNode(node,list);
		}else {
			list.add(node);
			return;
		}
	}
}

class Node	{
	public int x;
	public int y;
	public int realCost;
	public double heuristicCost;
	public double score;
	public Node parent;

	public Node()	{
		this(0,0);
	}
	public Node(int x,int y)	{
		this.x = x;
		this.y = y;
		this.realCost = -1;
	}

	public boolean isHere(int x,int y)		{
		return ((this.x == x)&&(this.y == y));
	}
	public void calcRealCost()	{
		if(this.realCost <= -1)	{
			this.realCost = this.parent.realCost + 1;
		}
	}
	public void calcHeuristicCost(Node end)	{
		this.heuristicCost = Math.sqrt((this.x - end.x) * (this.x - end.x) + (this.y - end.y) * (this.y - end.y));
		//this.heuristicCost = Math.max(Math.abs(this.x - end.x), Math.abs(this.y - end.y));
	}
	public boolean calcScore(Node end)	{
		calcRealCost();
		if(this.realCost <= -1)	{
			//System.out.println("didnt initialized");
			return false;
		}
		calcHeuristicCost(end);
		this.score = this.realCost + this.heuristicCost;
		return true;
	}
	public boolean equals(Object obj)	{
		if (obj instanceof Node)	{
			Node node = (Node)obj;
			return ((this.x == node.x)&&(this.y == node.y));
		}else {
			return false;
		}
	}
	@Override
	public String toString()	{
		return "\n[" + x + "," + y + "]\nscore : " + score;
	}
}

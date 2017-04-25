package maze3;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;


public class CreateGraph {
private String input = "src/input";
private int numCon = 0;
private int numVert = 0;
private String line = "";
private char s;
private char e;
private char l;
private Color c;
private char t;
static final int MAX = 140;
private Node[] graph;
private ArrayList<Character> path = new ArrayList<Character>();
private String pathO = "";
private ArrayList<Node> list = new ArrayList<Node>();
public CreateGraph() {
	super();
	graph = new Node[MAX];
	try{
		FileReader reader= new FileReader(input);
		Scanner in = new Scanner(reader);
		String information = in.nextLine();
		String[] info = information.split(" ");
		numCon= Integer.parseInt(info[0]);
		numVert= Integer.parseInt(info[1]);
		numVert = numVert*2 - 1;
		int i = 0;
		while(in.hasNextLine()){
			line = in.nextLine();
			s = line.charAt(0);
			e = line.charAt(2);
			l = line.charAt(4);
			t = line.charAt(6);
			if(l=='R'){
				c = Color.red;
			}
			else if(l=='G'){
				c = Color.green;
			}
			else{
				c = Color.blue;
			}
			graph[i]=new Node(s, e, c, t, i);
			i++;
			graph[i]=new Node(e, s, c, t, i);
			i++;
			//System.out.println(s + " " + e + " " + l + " " + t);
		}
		
		in.close();
	} catch (FileNotFoundException e){
		e.printStackTrace();
	}
}

public void connectGraph(){
	for(int i = 0; i < MAX; i++){
		for (int j = 0; j < MAX; j++){
			if (i == j){
				continue;
			}
			if(graph[i].getEnd() == graph[j].getStart()){
				if(graph[i].getStart() == graph[j].getEnd()){
					continue;
				}
				else if(graph[i].getType() == graph[j].getType()){
					graph[i].setConnect(j);
				}
				else if(graph[i].getLine().equals(graph[j].getLine())){
					graph[i].setConnect(j);
				}
			}
		}
	}
}
public void dfsVisit(Node n, Integer t){
	n.setVisited(Color.gray);
	ArrayList<Integer> adj = n.getConnect();
	for(int k = 0; k < adj.size(); k++){
		if(graph[adj.get(k)].getVisited().equals(Color.white)){
			graph[adj.get(k)].setParent(t);;
			dfsVisit(graph[adj.get(k)], graph[adj.get(k)].getArrNum());
		}
	}
	n.setVisited(Color.black);
}

public void dfs(){
	graph[0].setParent(null);
	for(int i = 0; i < MAX; i++){
		if(graph[i].getVisited().equals(Color.white)){
			dfsVisit(graph[i], graph[i].getArrNum());
		}
	}
}
public void bfs(){
	graph[0].setParent(null);
	list.add(graph[0]);
	while(!list.isEmpty()){
		Node x = list.get(0);
		ArrayList<Integer> adj = x.getConnect();
		for(int i = 0; i < adj.size(); i++){
			if(graph[adj.get(i)].getVisited().equals(Color.white)){
				graph[adj.get(i)].setVisited(Color.gray);
				graph[adj.get(i)].setParent(x.getArrNum());
				list.add(graph[adj.get(i)]);
			}
		}
		list.remove(0);
		x.setVisited(Color.black);
	}
}


public void path(){
	Integer holder = 138;
	while(!holder.equals(0)){
		path.add(graph[holder].getEnd());
		int h = graph[holder].getParent();
		holder = h;
	}
}


public String getPath() {
	for(Character c: path){
		pathO = c + " " + pathO;
	}
	pathO = "A B " + pathO;
	return pathO;
}

public Node[] getGraph() {
	return graph;
}



public int getNumCon() {
	return numCon;
}

public int getNumVert() {
	return numVert;
}

public static void main(String [] args){
	CreateGraph test = new CreateGraph();
	//System.out.println(test.getNumCon() + " " + test.getNumVert());
	test.connectGraph();
	//Node[] g = test.getGraph();
	//test.dfs();
	for(int i = 0; i < MAX; i++){
		//System.out.println(i + ": " +g[i].getParent());
		//System.out.println(i + ": "+g[i].printPath());
	}
	test.bfs();
	test.path();
	System.out.println(test.getPath());
}


}


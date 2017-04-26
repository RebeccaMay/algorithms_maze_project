package maze_project;

import java.awt.*;
import java.util.*;

//A node will be what is drawn by the lines on the map provided
//with the project definition
public class Node {
	
	//arraylist that will be populated with all the the nodes that connect to the current node
	public ArrayList<Integer> connect = new ArrayList<Integer>();
	
	//holds the color of the line represented by the node
	public Color node_color;
	
	//will hold the index in the array of all nodes of the parent 
	public Integer parent;
	
	//start city
	public char start;
	
	//end city
	public char end;
	
	//holds the color of the line that must match for a transfer
	public Color line;
	
	//holds the type of the line that must match for a transfer
	public char type;
	
	//hold a unique ID corresponding to place in array
	public Integer ID;
	
	public Node(char start, char end, Color l, char type, Integer id) {
		this.start = start;
		this.end = end;
		this.line = l;
		this.type = type;
		this.ID = id;
		this.parent = 0;
		this.node_color = Color.white;
	}
}
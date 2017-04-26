package maze_project;

import java.util.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Graph {
	private String filename = "src/data/input";
	
	//will be read in from file later on
	private int num_lines = 0;	
	
	//Used to assign appropriate color to line
	private char color_character;
	
	//Assigns color to line
	private Color col;	
	
	//arraylist of all nodes on the map
	private Node[] nodes_graph;
	
	//will contain the final route taken but backwards
	private ArrayList<Character> solution = new ArrayList<Character>();	
	
	//will be the correct order of solution in string format
	private String return_path = "";	
	
	//keep track of nodes to explore after they have been reached
	private ArrayList<Node> nodes_array = new ArrayList<Node>();	
	
	public Graph() {
		
		//Allocate enough memory for each node to exist going in both directions (70 * 2 = 140)
		nodes_graph = new Node[140];
		
		try{
			//Set up filereader to read in the file
			FileReader reader= new FileReader(filename);
			Scanner in = new Scanner(reader);
			String new_line = in.nextLine();
			
			//string to save the number of lines and set num_lines equal to that value
			//Note: the number of cities does not matter in this algorithm
			String[] info = new_line.split(" ");
			num_lines= Integer.parseInt(info[1]);
			
			//iterator to move through arraylist of nodes as we read each one in
			int iterate = 0;
			while(in.hasNextLine()){
				
				//read in next node
				new_line = in.nextLine();
				
				//get the char that represents the color of the line as displayed on the map
				color_character = new_line.charAt(4);
				if(color_character == 'R'){
					col = Color.red;
				}
				else if(color_character == 'G'){
					col = Color.green;
				}
				else{
					col = Color.blue;
				}
				
				//put the forward version of the node into the arraylist
				nodes_graph[iterate] = new Node(new_line.charAt(0), new_line.charAt(2), col, new_line.charAt(6), iterate);
				
				//put the same node into the next spot in the array but switching end and start city
				//this is done because I am making a directed graph
				//but the lines can be traveresed in either direction
				nodes_graph[iterate + 1] = new Node(new_line.charAt(2), new_line.charAt(0), col, new_line.charAt(6), iterate + 1);
				
				//update the iterator by two so that nodes are not overwritten in the array
				iterate = iterate + 2;										
			}
			
			//close the input file
			in.close();
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//function to fill the adjacency lists and perform the bfs search on the graph
	public void b_search(){
		
		//loop through the entire array of nodes to compare each one to every other node
		for(int i = 0; i < num_lines * 2; i++){
			for (int j = 0; j < num_lines * 2; j++){
				//if i is equal to j, the node is being compared to itself
				//therefore no actions are necessary here
				if (i == j){
					continue;
				}
				//check if the start city is the same as the other nodes end city
				if(nodes_graph[i].end == nodes_graph[j].start){
					
					//if the opposites also match, it is the same line but opposite direction
					//nothing should be added to an adjacency list here
					//this is so that we don't go back the same way we came when making a transfer
					if(nodes_graph[i].start == nodes_graph[j].end){
						continue;
					}
					
					//if types match, a free transfer can be made at this point on the map
					else if(nodes_graph[i].type == nodes_graph[j].type){
						nodes_graph[i].connect.add(j);
					}
					
					//if colors match, a free transfer can be made at this point on the map
					else if(nodes_graph[i].line == nodes_graph[j].line){
						nodes_graph[i].connect.add(j);
					}
				}
			}
		}
		
		//get first node to start the bfs search with
		//set parent to null
		nodes_graph[0].parent = null;
		nodes_array.add(nodes_graph[0]);
		
		//loop through nodes_array until there are no more nodes that need to be explored
		while(!nodes_array.isEmpty()){
			
			//set current node to the first in the list
			Node current = nodes_array.get(0);
			
			//get all nodes that are connected to current
			ArrayList<Integer> adjacent = current.connect;
			
			//loop through all of the connected nodes
			for(int i = 0; i < adjacent.size(); i++){
				
				//if the color is white the node has not yet been explored
				if(nodes_graph[adjacent.get(i)].node_color.equals(Color.white)){
					
					//the current node is then the parent of the found node
					nodes_graph[adjacent.get(i)].parent = current.ID;
					
					//this node needs to be explored and should be added to nodes_array
					nodes_array.add(nodes_graph[adjacent.get(i)]);
				}
			}
			
			//remove the explored node so that it is not revisted later on
			nodes_array.remove(0);
			
			//set the color to black so we know it has been explored
			current.node_color = Color.black;
		}
		
		//set parent_id to the node one before the final node (140 - 2 = 138)
		Integer parent_id = (num_lines * 2) - 2;
		
		//loop until the parent id equals 0
		//0 marks the start node
		while(!parent_id.equals(0)){
			
			//add the current node to the solution
			solution.add(nodes_graph[parent_id].end);
			
			//move to the current nodes parent
			int temp = nodes_graph[parent_id].parent;
			parent_id = temp;
		}
		
		//solution will start at the end and go to the start node
		//therefore the order must be reversed before printing
		for(Character x: solution){
			return_path = x + " " + return_path;
		}
		
		//add first line to the list since this would not have been added before
		//it is missing because its parent node has a value of 0
		System.out.print("A B ");
		System.out.print(return_path);
		
	}
	
	//calls to start the function
	public static void main(String [] args){
		
		//create the new graph called result
		Graph result = new Graph();
		
		//call the search function on this result
		result.b_search();
	}

}
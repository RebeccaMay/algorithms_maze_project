package maze3;

import java.awt.*;
import java.util.*;

public class Node {
static final int MAX = 14;
private char start;
private char end;
private Color line;
private char type;
private ArrayList<Integer> connect;
private Color visited = Color.white;
private ArrayList<Character> path;
private Integer parent = 155;
private Integer arrNum;
public Node(char start, char end, Color line, char type, Integer arrNum) {
	super();
	this.start = start;
	this.end = end;
	this.line = line;
	this.type = type;
	this.arrNum = arrNum;
	connect  = new ArrayList<Integer>();
	path = new ArrayList<Character>();
}
public Integer getArrNum() {
	return arrNum;
}

public ArrayList<Integer> getConnect() {
	return connect;
}
public void setConnect(Integer k) {
	connect.add(k);
}
public char getStart() {
	return start;
}
public char getEnd() {
	return end;
}
public Color getLine() {
	return line;
}
public char getType() {
	return type;
}

public void setParent(Integer parent) {
	this.parent = parent;
}
public Color getVisited() {
	return visited;
}
public void setVisited(Color visited) {
	this.visited = visited;
}

public Integer getParent(){
	return parent;
}
@Override
public String toString() {
	return "Node [start=" + start + ", end=" + end + ", line=" + line + ", type=" + type + "]";
}

public String printConnect(){
	String connections = start + " " + end + ":";
	for(Integer n: connect){
		connections = connections + " " + n.toString();
	}
	return connections;	
}

public int conLen(){
	return connect.size();
}
}
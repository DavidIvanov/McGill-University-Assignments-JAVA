//David Ivanov ID:260806837 collaborator: Marco Guida
import java.io.*;
import java.util.*;

public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/
		
		Stack = dfs(source, destination, Stack, graph);
		
		
		
		return Stack;
	}
	
	private static ArrayList<Integer> dfs(Integer node, Integer destination, ArrayList<Integer> Stack, WGraph graph){
		 if(node == destination) {
			 Stack.add(node);
			 return Stack;
		 }

		 for(int i =0 ; i <graph.getNbNodes(); i ++) {
			 if(graph.getEdge(node, i) != null && !Stack.contains(i) && graph.getEdge(node, i).weight >0) {
				 Stack.add(node);
				 ArrayList<Integer> Stack2= dfs(i , destination, Stack, graph);
				 if(Stack2.get(Stack2.size() -1) != node) {
					 return Stack2;
				 } else {
					 Stack.remove(Stack.size()-1);
				 }
			 }
		 }
		 
		return Stack;
	}
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260806837"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		/* YOUR CODE GOES HERE
		//
		//
		//
		//
		//
		//
		//
		*/
		WGraph ResGraph = new WGraph();
		WGraph MaxflowG = new WGraph(graph);
		
		for(Edge e : graph.getEdges()) { //build maxflow graph starting with empty flow 
			MaxflowG.setEdge(e.nodes[0], e.nodes[1], 0);
		}
		ResGraph = buildRes(MaxflowG, graph); //builds the res graph depending on the maxflow graph
		
		while(!FordFulkerson.pathDFS(source, destination, ResGraph).isEmpty()) {
			//Augment
			ArrayList<Integer> path = FordFulkerson.pathDFS(source, destination, ResGraph);
			//find bottle neck value in ResGraph
			int bottleneck = Integer.MAX_VALUE;
			for(int i = 1; i<path.size(); i++) {
				int weight = ResGraph.getEdge(path.get(i-1), path.get(i)).weight;
				if(weight < bottleneck)
					bottleneck = weight;
			}
			//Augment Path
			//build arraylist of edges
			ArrayList<Edge> augmentingPath = new ArrayList<Edge>();
			for(int i = 1; i< path.size(); i++) {
				augmentingPath.add(ResGraph.getEdge(path.get(i-1), path.get(i)));
			}
			
			//go through maxflow and update the flow
			for(Edge e : augmentingPath) { 
				if(MaxflowG.getEdge(e.nodes[0], e.nodes[1]) != null) { //if it is a forward edge
					MaxflowG.getEdge(e.nodes[0], e.nodes[1]).weight += bottleneck;
				} else { //if it is a backward edge
					MaxflowG.getEdge(e.nodes[1], e.nodes[0]).weight -= bottleneck;
				}
			}
			ResGraph = buildRes(MaxflowG, graph);
			maxFlow +=bottleneck;
		}
		
		graph = MaxflowG;
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	private static WGraph buildRes(WGraph MaxflowG, WGraph graph){ //helper method to build the residual graph
		WGraph ResGraph =  new WGraph();
		for(Edge e : graph.getEdges()) {
			if(MaxflowG.getEdge(e.nodes[0], e.nodes[1]).weight < e.weight) {
				Edge g = new Edge(e.nodes[0],e.nodes[1], e.weight-MaxflowG.getEdge(e.nodes[0], e.nodes[1]).weight);
				ResGraph.addEdge(g);
			}
			if(MaxflowG.getEdge(e.nodes[0], e.nodes[1]).weight >0) {
				Edge g = new Edge(e.nodes[1],e.nodes[0], MaxflowG.getEdge(e.nodes[0], e.nodes[1]).weight);
				ResGraph.addEdge(g);
			}
			
		}
		return ResGraph;
	}
	
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}

// David Ivanov ID: 260806837 Collaborator: Marco Guida
package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
    	DisjointSets p = new DisjointSets(g.getNbNodes());
    	WGraph mst = new WGraph();
    	for(Edge e: g.listOfEdgesSorted()) {
    		if(IsSafe(p, e)) {
    			p.union(e.nodes[0], e.nodes[1]);
    			mst.addEdge(e);
    		}
    	}    	
        /* Fill this method (The statement return null is here only to compile) */
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
    	if(p.find(e.nodes[0]) != p.find(e.nodes[1])) {
    		return true;
    	}else{
    		return false;
    	}
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}

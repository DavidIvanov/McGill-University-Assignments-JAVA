// David Ivanov ID:260806837
import java.io.*;
import java.util.*;

public class islands {
	
	
	public static void main (String[] args) {
		try {
			Scanner f = new Scanner(new File("testIslands.txt"));
			String line = f.nextLine();
			int nbOfProb = Integer.parseInt(line);
			ArrayList<String[][]> problems = new ArrayList<String[][]>();
			for(int i = 0; i<nbOfProb; i++) {
				String[] dim = f.nextLine().split(" ");
				int nbLines = Integer.parseInt(dim[0]);
				String[][] map = new String[nbLines][];
				for(int j = 0; j<nbLines; j++)
					map[j] = f.nextLine().split("");
				problems.add(map);
			}
			f.close();
			ArrayList<Integer> solution = new ArrayList<Integer>();
			
			
			
			
			for(String[][] Land : problems) {
			int count = findIslands(Land, 0, 0);
			solution.add(count);
			}
			
			String t = "";
			for(Integer i: solution)
				t += "" + i +"\n";
			
			File file = new File("testIslands_Solution.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(t);
			output.close();
		} catch(FileNotFoundException e) {
			
		} catch(IOException b) {
			
		}
		
	}
	
	public static int findIslands(String[][] map, int x, int y) {
		if(x<0 || y<0 || y>=map.length || x>=map[0].length) {
			return 0;
		}
		
		if(map[y][x].equals("#") || map[y][x].equals("X")) {
			map[y][x] = "V";
		}
		int found = 0;
		if(map[y][x].equals("-")) {
				found++;
				buildIsland(map, x, y);
			
		}
		int up= 0,down = 0, right = 0, left =0;
		//head to the right
		if(x+1<map[0].length)
			if(!map[y][x+1].equals("V"))	
				right= findIslands(map, x+1,  y);
		
		//head to the left
		if(x-1>0)
			if(!map[y][x-1].equals("V"))	
				left = findIslands(map, x-1,  y);
		
		// head up
		if(y+1<map.length)
			if(!map[y+1][x].equals("V"))	
				up= findIslands(map, x,  y+1);
		
		//head down
		if(y>0)
			if(!map[y-1][x].equals("V"))	
				down= findIslands(map, x,  y-1);
		
		return found+ right+ down+up+left;
	}
	
	public static void buildIsland(String[][] map, int x, int y) {
		if(x<0 || y<0 || y>=map.length || x>=map[0].length) {
			return;
		}
		if(map[y][x].equals("#") || map[y][x].equals("V")) {
			return;
		}else if(map[y][x].equals("X")){
			return;
		}else if(map[y][x].equals("-"))  {
			map[y][x]="X";
		}
		
		//check right	
				buildIsland(map, x+1, y);
		
		//check left
				buildIsland(map, x-1, y);
		
		//check up
				buildIsland(map, x, y+1);
		//check right	
				buildIsland(map, x, y-1);
	}
}


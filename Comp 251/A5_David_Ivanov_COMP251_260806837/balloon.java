// David Ivanov ID:260806837
import java.io.*;
import java.util.*;

public class balloon {
	//
	public static int getFirstAtHeight(ArrayList<Integer> p, int height) {
		int i = 0;
		int safeIndex = -1;
		while(i<p.size()) {
			if(p.get(i)== height) {
				safeIndex= i;
				break;
			}
			i++;
		}
		return safeIndex;
	}
	public static int getPeakHeight(ArrayList<Integer> p) {
		int maxheight =-1;
		for(int i =0; i<p.size(); i++) {
			if(p.get(i)>maxheight) {
				maxheight = p.get(i);
			}
			
		}
		return maxheight;
	}
	
	public static void main(String[] args) {
		try {
			Scanner f = new Scanner(new File("testBalloons.txt"));
			String line = f.nextLine();
			int nbOfProb = Integer.parseInt(line);
			
			String[] line2 = f.nextLine().split(" ");
			int[] nbBalloonPerProb = new int[nbOfProb];
			for(int i = 0 ; i < nbOfProb; i++) {
				nbBalloonPerProb[i] = Integer.parseInt(line2[i]);
			}
			
			String[] probLine = new String[nbOfProb];
			int n = 0;
			while(f.hasNextLine() && n<nbOfProb) {
				probLine[n] = f.nextLine();
				n++;
			}
			f.close();
			ArrayList<ArrayList<Integer>> problems = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i< nbOfProb; i++) {
				ArrayList<Integer> prob = new ArrayList<Integer>();
				String[] balloons = probLine[i].split(" ");
				for(String balloon : balloons) {
					prob.add(Integer.parseInt(balloon));
				}
				problems.add(prob);
			}
			
			ArrayList<Integer> Solution= new ArrayList<Integer>();
			for(ArrayList<Integer> p : problems) {		
				int shots = 0;
				while(!p.isEmpty()) {
					int maxHeight = getPeakHeight(p);
					int first = getFirstAtHeight(p, maxHeight);
					while(first<p.size()) {
						if(p.get(first)==maxHeight) {
							p.remove(first);
							maxHeight--;
						}else {
							first++;
						}
					}
					shots++;
				}
				Solution.add(shots);
			}
			
			String t = "";
			for(int s: Solution)
				t+=""+ s+ "\n";
			File file = new File("testBalloons_Solution.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(t);
			output.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("File does not exist");
		} catch(IOException e) {
			System.out.println("Saving file failed");
		}
		
	}
	
}

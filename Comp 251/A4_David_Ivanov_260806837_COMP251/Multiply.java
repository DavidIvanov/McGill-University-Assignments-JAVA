//David Ivanov ID:260806837 collaborator: Marco Guida
import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
    	int[] results = new int[2];
        int ops = 0;
        //build arrays for x and y
        int[] xValue = new int[size];
        int[] yValue = new int[size];
        for(int i = 0; i < size; i++) {
        	xValue[i] = (int) (x/Math.pow(2, i))%2;
        	yValue[i] = (int) (y/Math.pow(2, i))%2;
        }
        int total = 0;
        
        for(int i = 0; i<size; i++) {
        	int[] xy = new int[size];
        	for(int j = 0 ; j<size; j ++) {
        		xy[j] = xValue[i]*yValue[j];
        		ops++;
        	}
        	for(int j = 0 ; j < size; j++) {
        		total+=xy[j]*((int) Math.pow(2, j+i));
        		ops++;
        	}
        }
        
        results[0]= total;
        results[1]= ops;
        
        return results;
        
    }

    public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
        
        int ops= 0;
        int[] results = new int[2];
        if(size==1) {
        	results[0]= x*y;
        	ops++;
        	results[1]=ops;
        	return results;
        }else {
        	int m =(int) Math.ceil(size/2.0);
        	
        	int a = (int) Math.floor(x/(Math.pow(2, m)));
        	int b =  (x%((int) (Math.pow(2, m))));
        	
        	int c = (int) Math.floor(y/(Math.pow(2, m)));
        	int d =  (y%((int) (Math.pow(2, m))));
        	
        	int[] e = karatsuba(m, a, c);
        	int[] f = karatsuba(m, b, d);
        	
        	int[] g = null;
        	
        	if(a<b && c<d) {
        		g=karatsuba(m, -1*(a-b), -1*(c-d));
        	}else if(a<b) {
        		g=karatsuba(m, -1*(a-b), c-d );
        		g[0] = -1*g[0];
        	}else if(c<d) {
        		g=karatsuba(m, a-b, -1*(c-d));
        		g[0] = -1*g[0];
        	} else {
        		g=karatsuba(m, a-b, c-d);
        	}
        	ops+=6;
        	
        	results[0] = (int) ((Math.pow(2, 2*m)*e[0]) + (Math.pow(2, m)*(e[0]+f[0]-g[0]))+ f[0]);
        	results[1] = e[1]+f[1]+g[1]+ops;
        	return results;
        }
    }
    
    public static void main(String[] args){
        
    	try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   }
}

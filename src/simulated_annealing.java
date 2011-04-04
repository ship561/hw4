import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;


public class simulated_annealing {
	//int[] A = {1,2,3,5};
	//int[] B = {2,2,3,4};
	//int[] C = {1,1,1,2,2,2,2};
	int[] A = { 5509,5626,6527,6766,7233,16841};
	int[] B = { 3526,4878,5643,5804,7421,21230};
	int[] C = { 1120,1868,2564,2752,3240,3526,3758,3775,4669,5509,15721};
	int n;
	
	simulated_annealing() {
		for(int i=0; i<A.length; i++) {
			n+=A[i];
		}
	}

	int[] multiset_C(int[] a, int[] b) {		//generates a new multiset C from sets A and B
		int x = 0, y=0, k=0;
		int Sa=0, Sb=0, Sc=0;
		LinkedList<Integer> c = new LinkedList<Integer>();
		while(x<a.length || y<b.length){
			try {
				if(Sa+a[x] <= Sb+b[y]) {
					Sa += a[x];
					c.add((int)Sa - Sc);
					k++;
					Sc = Sa;
					x++; 
				} else {
					Sb += b[y];
					c.add((int)Sb - Sc);
					k++;
					Sc = Sb;
					y++;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				if (x>=a.length){
					Sb += b[y];
					c.add(Sb - Sc);
					k++;
					Sc = Sb;
					y++;
				} else if (y>=b.length){
					Sa += a[x];
					c.add(Sa - Sc);
					k++;
					Sc = Sa;
					x++; 
				}
			}
		}
		int[] C = new int[c.size()-1];
		for(int i=0; i< C.length; i++) {
			C[i]=c.pop();
		}
		return C;
	}
	
	double fitness(int[] cmu) {		//returns fitness function
		double sum=0;
		for(int i=0; i<cmu.length; i++) {
			sum += Math.pow(cmu[i]-this.C[i], 2)/this.C[i];
		}
		return sum;
	}
	int[] randomN(int[] arr) {		//randomizes the array to produce next states
		int[] tempArr = arr;
		Random r = new Random();
		for (int i=0; i<arr.length; i++) {
		    int randomPosition = r.nextInt(arr.length);
		    int temp = arr[i];
		    arr[i] = arr[randomPosition];
		    arr[randomPosition] = temp;
		}
		return tempArr;
	}
	
	public static void main(String[] args) {
		simulated_annealing sa = new simulated_annealing();
		double best=10000;
		int[] cMS = new int[1];
		int iter=0;
		while(best >0) {					//continues until the minimum is found. when fitness =0
			//int[] mu = sa.randomN(sa.A);
			//int[] sigma = sa. randomN(sa.B);
			int[] mu = sa.A;
			int[] sigma = sa.B;
			cMS=sa.multiset_C(mu,sigma);
			Arrays.sort(cMS);
			double fit = sa.fitness(cMS);
			if(best > fit) {				//stores best fitness score
				sa.A=mu;
				sa.B=sigma;
				best = fit;
			} else {						//moves to next state randomly
				sa.A = sa.randomN(sa.A);
				sa.B = sa. randomN(sa.B);
			}
			iter++;
		}
		System.out.print("iterations: " + iter + " C: ");
		for(int i=0; i<cMS.length; i++) {
			System.out.print(cMS[i] + " ");
		}
		System.out.println("");
		int temp=0;
		for(int i=0; i<sa.A.length; i++){ 
			temp += sa.A[i];
			System.out.print(temp + " ");
		}
		System.out.println("");
		temp=0;
		for(int i=0; i<sa.B.length; i++){
			temp += sa.B[i];
			System.out.print(temp + " ");
		}
			
	}

}

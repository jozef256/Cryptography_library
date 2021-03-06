package Cryptography_library;

// Compilation (CryptoLibTest contains the main-method):
//   javac CryptoLibTest.java
// Running:
//   java CryptoLibTest

public class CryptoLib {

	/**
	 * Returns an array "result" with the values "result[0] = gcd",
	 * "result[1] = s" and "result[2] = t" such that "gcd" is the greatest
	 * common divisor of "a" and "b", and "gcd = a * s + b * t".
	 **/
	public static int[] EEA(int a, int b) {
		// Note: as you can see in the test suite,
		// your function should work for any (positive) value of a and b.
		int gcd = -1;
		int s = -1;
		int t = -1;
		int[] result = new int[3];
		int r0, r1, s0, s1, t0, t1;
		int r_prev, r_curr, r_next, q;   //for the equation
		int s_prev,  s_next ;
		int t_prev,  t_next ;
		
		//ensure that the first term is bigger
		r0=(a>b)?a:b;
		r1=(a>b)?b:a;
		q=r0/r1;     
				
		//needed to enter the loop
		r_next=r0-q*r1;
		r_curr=r1;
		
		//s values
		s0=(a>b)?1:0;
		s1=(a>b)?0:1;
		
		s_prev=s0; 
		s=s1;
		s_next=s_prev-q*s;
		
		//t values
		t0=(a>b)?0:1;
		t1=(a>b)?1:0;		

		t_prev=t0; 
		t=t1;
		t_next=t_prev-q*t;
		
		while(r_next>0 && r_next<Math.abs(r_curr)){				
			r_prev=r_curr;
			r_curr=r_next;
			q=r_prev/r_curr;
			r_next=r_prev-q*r_curr;	
			
			s_prev=s;
			s=s_next;
			s_next=s_prev-q*s;
			
			t_prev=t;
			t=t_next;
			t_next=t_prev-q*t;
		}
	
		gcd=r_curr;
		result[0] = gcd;
		result[1] = s;
		result[2] = t;
		return result;
	}

	/**
	 * Returns Euler's Totient for value "n".
	 **/
	public static int EulerPhi(int n) {
		int totCount=0, i=1;
		if (n<0)
			return 0;
		
		while(i<=n){
			if(EEA(i, n)[0]==1)
				totCount++;
			i++;
		}		
		return totCount;
	}

	/**
	 * Returns the value "v" such that "n*v = 1 (mod m)". Returns 0 if the
	 * modular inverse does not exist.
	 **/
	public static int ModInv(int n, int m) {
		n=(n<0)?m+n:n;   //turn negative to positive		
		int[] eeaResult = EEA(n, m);   //obtain euclidean vlaues
		int gcd=eeaResult[0];
		int v=eeaResult[1]; 
		
		//n and m need to be co-prime or the inverse won't exist.		
		if (gcd==1){
			v=(v<0)?m+v:v;
			return v;
		}		
		return 0;
	}

	/** calculate (a ^ n1) % mod **/
	public static int powerModulo(int a, int n1, int mod){
		int res=1;
		//res^g+1=res*res^g
		for (int i = 0; i < n1; i++){
			res =res * a;
			res =res % mod;
		}
		return res % mod;
	}
	/**
	 * Returns 0 if "n" is a Fermat Prime, otherwise it returns the lowest
	 * Fermat Witness. Tests values from 2 (inclusive) to "n/3" (exclusive).
	 **/
	public static int FermatPT(int n) {
		int  i=0,k=0,lowestwitness=0, a=2, limit=n/3;
		
		while(a<limit){
			//n is probably prime or a is a fermat witness
		  if (powerModulo(a, n-1, n) == 1) {				
			  i++;				 
		  } else{
			  if(k==0){		   //store lowest witness
			      lowestwitness=a;
			  }
			  k++;
		  }		  
		a++;
		}  
		//number that is relatively prime with all n/3
		if(i+2==limit )      
		     return 0;		
		else
			return lowestwitness;
	}

	/**
	 * Returns the probability that calling a perfect hash function with
	 * "n_samples" (uniformly distributed) will give one collision (i.e. that
	 * two samples result in the same hash) -- where "size" is the number of
	 * different output values the hash function can produce.
	 **/
	public static double HashCP(double n_samples, double size) {
		double power=n_samples*n_samples/(2*size);
		
		return 1-Math.exp(-power);
	}

}

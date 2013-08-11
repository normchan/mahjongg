package normchan.mj.app;

public class Test {
	public static int equi ( int[] A ) {
		int currIndex = 0;
		int leftTotal = 0;
		int rightTotal = 0;
		for (int i : A) {
			rightTotal += i;
		}
		for (int j = 0; j < A.length; j++) {
			if (leftTotal == rightTotal)
				return j;

			leftTotal += A[j];
			rightTotal -= A[j];
		}

		return -1;   
	}

	public static void main(String[] args) {
		int[] A = new int[7];
		A[0] = -7;    A[1] =  1;    A[2]=5;
		A[3] =  2;    A[4] = -4;    A[5]=3;
		A[6] =  0;
		
		System.out.println("Answer is: "+equi(A));
	}
}

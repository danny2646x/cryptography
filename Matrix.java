public class Matrix{

	static int cofactor(int p, int q, int n, int[][] M){
		if(n == 1)
			return 1; 

		int[][] N = new int[M.length][M.length];
		for(int i = 0; i < M.length * M.length; ++i)
			N[i/M.length][i%M.length] = M[i/M.length][i%M.length];

		//deactivate row p, column q
		int invalid = -10000;
		for(int i = 0; i < M.length; ++i)
			N[i][q] = invalid;
		for(int j = 0; j < M.length; ++j)
			N[p][j] = invalid;

		int cf = 0;
		
		//expand along the row axis.
		for(int i = 0, count = 0; i < N.length && count < n-1; ++i)
			for(int j = 0; j < N.length; ++j){
				if(N[i][j] == invalid)
					continue;
				cf += N[i][j] * ((count & 1) == 0 ? 1 : -1) * cofactor(i, j, n-1, N);
				++count;
			}

		return cf;
	}

	static int determinant(int[][] M){
		int d = 0;
		for(int j = 0; j < M.length; ++j)
			d += M[0][j] * ((j & 1) == 0 ? 1 : -1) * cofactor(0, j, M.length, M);
		return d;
	}

	static int[][] inverse(int[][] M){
		int[][] MI = new int[M.length][M.length];
		for(int i = 0; i < M.length; ++i)
			for(int j = 0; j < M.length; ++j)
				MI[j][i] = (((i+j) & 1) == 0 ? 1 : -1) * cofactor(i, j, M.length, M);
		return MI;
	}

	public static void main(String[] args){
		//int M[][] = { {17, 17, 5}, {21, 18, 21}, {2, 2, 19} };
		/*int M[][] = { {3, 2, -1, 4, 19}, {2, 1, 5, 7, 9}, 
			      {0, 5, 2, -6, 19}, {-1, 2, 1, 0, 9},
	       		      {19, 9, 19, 9, 19}
		};*/
		int M[][] = { {3, 2, -1, 4}, {2, 1, 5, 7}, {0, 5, 2, -6}, {-1, 2, 1, 0} };
		//int M[][] = { {3, 2, -1, 4}, {2, 1, 5, 7}, {1, 5, 2, -6}, {-1, 2, 1, 6} };
		//int M[][] = { {3, 2}, {2, 1} };
		System.out.print("\nDeterminant = " + determinant(M));	
		int[][] MI = inverse(M);
		for(int i = 0; i < MI.length; ++i){
			for(int j = 0; j < MI.length; ++j)
				System.out.print(MI[i][j] + " ");
			System.out.print("\n");
		}
	}
}

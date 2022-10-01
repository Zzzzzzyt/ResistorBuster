public class EQSolver {
	public static boolean DEBUG=true;
	
	public static Number[] solve(Number[][] mat, Number[][] cst, int n) {
		// Matrix representation
		if(DEBUG) {
			System.out.println("Input Equations:");
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					System.out.print(mat[i][j]+"\t");
				}
				System.out.println(" = " + cst[i][0]);
			}
		}
		// inverse of matrix mat[][]
		Number inv[][] = invert(mat);
		if(DEBUG) {
			System.out.println("Inverse:");
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					System.out.print(inv[i][j] + "\t");
				}
				System.out.println();
			}
		}
		
		// Multiplication of mat inverse and constants
		Number result[][] = new Number[n][1];
		for (int i = 0; i < n; i++) {
			result[i][0] = new Number(0);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 1; j++) {
				for (int k = 0; k < n; k++) {
					result[i][j].add(Number.mul(inv[i][k], cst[k][j]));
				}
			}
		}
		Number ret[] = new Number[n];
		if(DEBUG) {
			System.out.println("Product:");
			for (int i = 0; i < n; i++) {
				ret[i] = result[i][0];
				System.out.println(result[i][0]);
			}
			System.out.println();
		}
		
		return ret;
	}

	public static Number[][] invert(Number a[][]) {
		int n = a.length;
		Number x[][] = new Number[n][n];
		Number b[][] = new Number[n][n];
		int id[] = new int[n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				x[i][j] = new Number(0);
				b[i][j] = new Number(0);
			}
		}
		for (int i = 0; i < n; ++i)
			b[i][i] = new Number(1);

		// Transform the matrix into an upper triangle
		gaussian(a, id);
		
		// Update the matrix b[i][j] with the ratios stored
		for (int i = 0; i < n - 1; ++i)
			for (int j = i + 1; j < n; ++j)
				for (int k = 0; k < n; ++k)
					b[id[j]][k].sub(Number.mul(a[id[j]][i], b[id[i]][k]));

		// Perform backward substitutions
		for (int i = 0; i < n; ++i) {
			x[n - 1][i] = Number.div(b[id[n - 1]][i], a[id[n - 1]][n - 1]);
			for (int j = n - 2; j >= 0; --j) {
				x[j][i] = b[id[j]][i];
				for (int k = j + 1; k < n; ++k) {
					x[j][i].sub(Number.mul(a[id[j]][k], x[k][i]));
				}
				x[j][i].div(a[id[j]][j]);
			}
		}
		return x;
	}

	public static void gaussian(Number a[][], int id[]) {
		int n = id.length;
		Number c[] = new Number[n];
		for (int i = 0; i < n; i++) {
			c[i] = new Number(0);
		}

		// Initialize the index
		for (int i = 0; i < n; ++i)
			id[i] = i;

		// Find the rescaling factors, one from each row
		for (int i = 0; i < n; ++i) {
			Number c1 = new Number(0);
			for (int j = 0; j < n; ++j) {
				Number c0 = a[i][j].abs();
				if (c0.compareTo(c1) > 0)
					c1 = c0;
			}
			c[i] = c1;
		}

		// Search the pivoting element from each column
		int k = 0;
		for (int j = 0; j < n - 1; ++j) {
			Number pi1 = new Number(0);
			for (int i = j; i < n; ++i) {
				Number pi0 = a[id[i]][j].abs();
				pi0.div(c[id[i]]);
				if (pi0.compareTo(pi1) > 0) {
					pi1 = pi0;
					k = i;
				}
			}

			// Interchange rows according to the pivoting order
			int itmp = id[j];
			id[j] = id[k];
			id[k] = itmp;
			for (int i = j + 1; i < n; ++i) {
				Number pj = Number.div(a[id[i]][j], a[id[j]][j]);

				// Record pivoting ratios below the diagonal
				a[id[i]][j] = pj;

				// Modify other elements accordingly
				for (int l = j + 1; l < n; ++l)
					a[id[i]][l].sub(Number.mul(pj, a[id[j]][l]));
			}
		}
	}
}
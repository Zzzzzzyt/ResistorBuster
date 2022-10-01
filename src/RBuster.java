import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RBuster {
	public static final int RESISTOR = 0;
	public static final int POWER_U = 1;
	public static final int POWER_I = 2;

	ArrayList<ArrayList<Edge>> g;
	ArrayList<Edge> edge;
	Number mat[][], cst[][], rst[];
	boolean used[];
	int n, m, q, s;
	int pnt;
	public static Scanner in;

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("===========    Resistor Buster : F*** U Circuits!!!    ===========");
		System.out.println("                         by Zzzyt 2020");
		System.out.println("Plz place input in   in.txt");
		System.out.println("Data format:");
		System.out.println("<node count> <edge count> <query count> <node id where phi=0> ");
		System.out.println("<node1> <node2> <edge type> <param numerator> <param denominator>");
		System.out.println("...");
		System.out.println("<query type> <param1> <param2>");
		System.out.println("...");
		System.out.println();
		System.out.println("edge type:");
		System.out.println("0=resistor");
		System.out.println("1=power source with constant voltage");
		System.out.println("2=power source with constant current");
		System.out.println();
		System.out.println("query type");
		System.out.println("0=resistance between two nodes");
		System.out.println("1=voltage between two nodes");
		
		in = new Scanner(new FileInputStream(new File("in.txt")));
		while (in.hasNext()) {
			RBuster rb = new RBuster();
			rb.solve();
		}
		in.close();
	}

	void solve() {
		System.out.println();
		System.out.println();
		System.out.println();

		n = in.nextInt();
		m = in.nextInt();
		q = in.nextInt();
		s = in.nextInt();
		pnt = 0;

		g = new ArrayList<ArrayList<Edge>>();
		edge = new ArrayList<Edge>();
		for (int i = 0; i < n; i++) {
			g.add(new ArrayList<Edge>());
		}
		mat = new Number[n + m][n + m];
		cst = new Number[n + m][1];
		for (int i = 0; i < n + m; i++) {
			for (int j = 0; j < n + m; j++) {
				mat[i][j] = new Number(0);
			}
			cst[i][0] = new Number(0);
		}
		rst = new Number[n + m];
		used = new boolean[n];
		for (int i = 0; i < m; i++) {
			int x, y, t;
			long r1, r2;
			x = in.nextInt();
			y = in.nextInt();
			t = in.nextInt();
			r1 = in.nextLong();
			r2 = in.nextLong();
			g.get(x).add(new Edge(x, y, i, t, new Number(r1, r2)));
			g.get(y).add(new Edge(x, y, i, t, new Number(r1, r2)));
			edge.add(new Edge(x, y, i, t, new Number(r1, r2)));
			if (t == RESISTOR) {
				mat[pnt][y] = new Number(1);
				mat[pnt][x] = new Number(-1);
				mat[pnt][i + n] = new Number(-r1, r2);
				cst[pnt][0] = new Number(0);
				pnt++;
			} else if (t == POWER_U) {
				mat[pnt][y] = new Number(1);
				mat[pnt][x] = new Number(-1);
				cst[pnt][0] = new Number(-r1, r2);
				pnt++;
			} else if (t == POWER_I) {
				mat[pnt][i + n] = new Number(1);
				cst[pnt][0] = new Number(-r1, r2);
				pnt++;
			}
		}
		for (int x = 1; x < n; x++) {
			for (int i = 0; i < g.get(x).size(); i++) {
				Edge tmp = g.get(x).get(i);
				if (tmp.x == x) {
					mat[pnt][tmp.id + n] = new Number(1);
				} else {
					mat[pnt][tmp.id + n] = new Number(-1);
				}
				cst[pnt][0] = new Number(0);
			}
			pnt++;
		}
		mat[pnt][s] = new Number(1);
		cst[pnt][0] = new Number(0);
		pnt++;

		rst = EQSolver.solve(mat, cst, n + m);
		for (int i = 0; i < n; i++) {
			System.out.println("Phi" + i + "\t = " + rst[i]);
		}
		System.out.println();
		for (int i = 0; i < m; i++) {
			System.out.println("I(" + edge.get(i).x + "->" + edge.get(i).y + ")\t = " + rst[i + n]);
		}

		System.out.println();
		for (int qq = 0; qq < q; qq++) {
			int typ = in.nextInt();
			if (typ == 0) {
				int a = in.nextInt();
				int b = in.nextInt();
				Number is = new Number(0);
				for (int i = 0; i < g.get(a).size(); i++) {
					is.add(rst[g.get(a).get(i).id + n].abs());
				}
				is.div(new Number(2));
				System.out.println(Number.div(Number.sub(rst[b], rst[a]).abs(), is));
			} else if (typ == 1) {
				int a = in.nextInt();
				int b = in.nextInt();
				System.out.println(Number.sub(rst[b], rst[a]));
			}
		}
	}

	class Edge {
		int x, y;
		int id;
		int t;
		Number r;

		public Edge(int x, int y, int id, int t, Number r) {
			this.x = x;
			this.y = y;
			this.id = id;
			this.r = r;
		}
	}
}
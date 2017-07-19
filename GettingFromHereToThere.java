// Copy paste this Java Template and save it as "GettingFromHereToThere.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0136139E
// write your name here: Xu Jun
// write list of collaborators here: Lu Lechuan, Wang Jinyi
// year 2017 hash code: x4gxK7xzMSlNvFsMEUVn (do NOT delete this line)

class GettingFromHereToThere {
  private int V; // number of vertices in the graph (number of rooms in the building)
  private ArrayList < ArrayList < IntegerPair > > AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
  private ArrayList < ArrayList < IntegerTriple > > tmp;
  private ArrayList < ArrayList < IntegerTriple > > MST;
  private int ans;
  private int[] visited;
  private int[] parent;
  private PriorityQueue<IntegerTriple> PQ;
  private boolean[] taken;
  private int[][] res;

  public GettingFromHereToThere() {
    // Write necessary codes during construction;
    //
    // write your answer here



  }

  //create a MST out of AdjList
  void PreProcess() {	
	//convert to graph of IntegerTriples
    tmp = new ArrayList<ArrayList<IntegerTriple>>(V);
	for(int i=0; i<V; i++) {
		tmp.add(new ArrayList<IntegerTriple>());
		for(int j=0; j<AdjList.get(i).size(); j++) {
			tmp.get(i).add(new IntegerTriple(AdjList.get(i).get(j).second(), i, AdjList.get(i).get(j).first())); //(weight, from, to)
		}
	}
    AdjList=null;

	//using Prim's algo, get MST, save as "MST"
	PQ= new PriorityQueue<IntegerTriple>();
    MST= new ArrayList<ArrayList<IntegerTriple>>(V);
    taken= new boolean[V];
    int count=0;

    for(int i=0; i<V; i++) {
        taken[i]=false;
        MST.add(new ArrayList<IntegerTriple>());
    }
    process(0);

    IntegerTriple front;
    while(!(PQ.peek()==null) && count<V-1) {
        front= PQ.poll();
        if(!taken[front.third()]) {
            MST.get(front.second()).add(front);
            MST.get(front.third()).add(new IntegerTriple(front.first(), front.third(), front.second()));
            process(front.third());
            count++;
        }
    }
    tmp=null;

    //convert "MST" back to graph of IntegerPairs, save as AdjList
    AdjList= new ArrayList<ArrayList<IntegerPair>>(V);
    for(int i=0; i<V; i++) {
        AdjList.add(new ArrayList<IntegerPair>());
        for(int j=0; j<MST.get(i).size(); j++) {
            AdjList.get(i).add(new IntegerPair(MST.get(i).get(j).third(), MST.get(i).get(j).first())); //(to, weight)
        }
    }
    create();      //get results for sources 0 to 9
    //for debugging
    /*
    for(int i=0; i<V; i++) {
        for(int j=0; j<AdjList.get(i).size(); j++) {
            IntegerPair p = AdjList.get(i).get(j);
            System.out.print("("+p.first()+","+p.second()+") ");
        }
        System.out.println();
    }
    for(int i=0; i<10; i++) {
        for(int j=0; j<10; j++) {
            System.out.print(res[i][j]+ " ");
        }
        System.out.println();
    }
    */
  }

  void process(int vertex) {
    taken[vertex]=true;
    
    for(int i=0; i<tmp.get(vertex).size(); i++) {
        IntegerTriple u = tmp.get(vertex).get(i);
        if(!taken[u.third()]) {
            PQ.offer(u);
        }
    }
  }

  void create() {
    res= new int[10][V];
    int lim= Math.min(10, V);
    for(int i=0; i<lim; i++) {
        visited= new int[V];
        parent= new int[V];
        DFS(i, i, 0);
        //System.out.println();
    }
  }

  /*
  int MyQuery(int source, int destination) {
    ans = 0;
	visited= new int[V];
	parent= new int[V];
	for(int i=0; i<V; i++) {
		visited[i]=0;
		parent[i]=-1;
	}
	DFS(source);
	backtrack(destination);
    return ans;
  }
  */

  int Query(int source, int destination) {
    return res[source][destination];
  }

  void DFS(int source, int v, int pathMax) {
	visited[v]=1;
	int child;
	for(int i=0; i<AdjList.get(v).size(); i++) {
		child= AdjList.get(v).get(i).first();
		if(visited[child]==0) {
            parent[child]=v;
			int w= weight(v, child);
            if(w>pathMax) {
                res[source][child]=w;
                DFS(source, child, w);
            }
            else {
                res[source][child]=pathMax;
                DFS(source, child, pathMax);
            }
		}
	}
  }
  
  /*
  void backtrack(int u) {
	if(parent[u]==-1)
		return;
	int weight= weight(parent[u], u);
	if(weight>ans)
		ans=weight;
	backtrack(parent[u]);
  }
  */

  int weight(int parent, int child) {
	for(int i=0; i<AdjList.get(parent).size(); i++) {
		if(AdjList.get(parent).get(i).first()==child)
			return AdjList.get(parent).get(i).second();
	}
	return -1;
  }

  void run() throws Exception {
    // do not alter this method
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new ArrayList < ArrayList < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new ArrayList < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    GettingFromHereToThere ps4 = new GettingFromHereToThere();
    ps4.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }
  
  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}



class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}



class IntegerTriple implements Comparable < IntegerTriple > {
  Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else if (!this.second().equals(o.second()))
      return this.second() - o.second();
    else
      return this.third() - o.third();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
  Integer third() { return _third; }
}

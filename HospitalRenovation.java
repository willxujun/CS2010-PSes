// Copy paste this Java Template and save it as "HospitalRenovation.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0136139E
// write your name here: Xu Jun
// write list of collaborators here: Lu Lechuan, Wang Jinyi
// year 2017 hash code: AlaYnzmQ65P4x2Uc559u (do NOT delete this line)

class HospitalRenovation {
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private ArrayList<ArrayList<Integer>> AdjList; // the graph (the hospital)
  private int[] RatingScore; // the weight of each vertex (rating score of each room)

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private int time;
  private int[] disc;
  private int[] low;
  private ArrayList<Integer> ap;
  private int[] parent;
  private int[] visited;
  private final int INF=1000000000;

  public HospitalRenovation() {
    // Write necessary code during construction
    //
    // write your answer here
  }

  int Query() {
    int ans = INF;
    // You have to report the rating score of the critical room (vertex)
    // with the lowest rating score in this hospital
    //
    // or report -1 if that hospital has no critical room
    //
    // write your answer here
    
    DFS(0);
    for(int i=0; i<ap.size(); i++) {
        if(RatingScore[ap.get(i)]<ans) {
            ans=RatingScore[ap.get(i)];
        }
        //System.out.println("node "+ ap.get(i) + " score: " + RatingScore[ap.get(i)]);
    }
    //System.out.println(ap[1]);
    if(ans==INF)
        return -1;
    else
        return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------
  void DFS(int curr) {
    //visit this node. increase time, set discovery time 
    visited[curr]= 1;
    time++;
    disc[curr]=time;
    low[curr]=time;
    int numChildren=0;
    int i, child;
    boolean isCritical=false;

    //System.out.println("At node " + curr);

    //traversal
    for(i=0; i<AdjList.get(curr).size(); i++) {
        child= AdjList.get(curr).get(i);
         numChildren++;
         //if i has not been visited before
         if(visited[child]==0) {
            //set parent of child to be curr
            parent[child]=curr;
            //traverse child
            DFS(child);
            //update low. if child connects to earlier node, curr can do the same through i
            low[curr]=Math.min(low[child], low[curr]);
            //System.out.println("Node " + curr+ "low: "+ low[curr]);
            //check if curr is articulation point, if previously unchecked.
            if(isCritical==false) {
                if(parent[curr]==-1 && numChildren>1) {
                    ap.add(curr);
                    isCritical=true;
                }
                else if(parent[curr]!=-1 && low[child]>=disc[curr]) { //the furthest the child can connect to is curr
                    ap.add(curr);
                    isCritical=true;
                    //System.out.println("node " + curr+ " is critical. Low: "+low[curr]);
                }
            }
          }
        //if i has been visited
        else
            low[curr]=Math.min(low[curr], disc[child]);
    }
    //System.out.println("node "+ curr + " has low: " + low[curr]);
  }

  // --------------------------------------------

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {
      //System.out.println("Round "+TC);
      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];
      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency List
      // clear time and arrays for new test case
      AdjList = new ArrayList<ArrayList<Integer>>(V);
      time=0;
      disc=new int[V];
      ap= new ArrayList<Integer>();
      parent= new int[V];
      low= new int[V];
      visited= new int[V];

      for (int i = 0; i < V; i++) {
        //initialize disc, ap, parent, low arrays
        disc[i]=-1; 
        parent[i]=-1;
        visited[i]=0;
        low[i]= INF;
        AdjList.add(new ArrayList<Integer>());

        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());
        while (k-- > 0) {
          int j = Integer.parseInt(st.nextToken());
          AdjList.get(i).add(j); // edge weight is always 1 (the weight is on vertices now)
        }
      }

      pr.println(Query());
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalRenovation ps3 = new HospitalRenovation();
    ps3.run();
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


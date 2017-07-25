import java.util.*;
import java.io.*;

// write your matric number here:A0136139E
// write your name here:Xu Jun
// write list of collaborators here:Lu Lechuan, Wang Jinyi
// year 2017 hash code: x8DYWsALaAzykZ8dYPZP (do NOT delete this line)

class Bleeding {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private int Q; // number of queries
  private ArrayList < ArrayList < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge
  private int[][] D;
  private final int INF= Integer.MAX_VALUE;
  private TreeSet<IntegerTriple> PQ;
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------



  // --------------------------------------------

  public Bleeding() {
    // Write necessary code during construction
    //
    // write your answer here



  }

  void PreProcess() {
    // Write necessary code to preprocess the graph, if needed
    //
    // write your answer here
    //------------------------------------------------------------------------- 



    //------------------------------------------------------------------------- 
  }

  int Query(int s, int t, int k) {
    int ans = -1;
    int min = INF;
    D= new int[k+1][V];
    PQ= new TreeSet<IntegerTriple> ();
    
    if(s==t)
        return 0;
    for(int i=0; i<k+1; i++) {
        for(int j=0; j<V; j++) {
            if(j==s)
                D[i][j]=0;
            else
                D[i][j]=INF;
        }
    }
    PQ.add(new IntegerTriple(0, 1, s)); //(d, step, to)

    IntegerTriple v; //(d, step, curr)
    IntegerPair u; //(to, weight)
    int curr, step, next;
    while(!PQ.isEmpty()) {
        v=PQ.pollFirst();
        if(v.second()==k) {
            break;
        }
        curr=v.third();
        step=v.second();
        //System.out.println(v.toString());
        if(v.first()==D[step][curr]) {
            for(int i=0; i<AdjList.get(curr).size(); i++) {
                u= AdjList.get(curr).get(i);
                next= u.first();
                if(v.second()==k-1 && u.first()!=t) {
                    //System.out.println("Byassing "+u.toString());
                    continue;
                }

                //relax
                if(D[step+1][next] > D[step][curr]+ u.second()) {
                    D[step+1][next]=D[step][curr]+ u.second();
                    //if reached, update min; otherwise enqueue
                    if(u.first()==t) {
                        if(D[step+1][next]<min)
                            min=D[step+1][next];
                    }
                    else PQ.add(new IntegerTriple(D[step+1][next], v.second()+1, next));
                }
            }
        }
    }
    
    //System.out.println("-----");
    if(min!=INF) {
        ans=min;
    }
    return ans;
  }

  // You can add extra function if needed
  // --------------------------------------------



  // --------------------------------------------

  void run() throws Exception {
    // you can alter this method if you need to do so
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
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
        }
      }

      PreProcess(); // optional

      Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

      if (TC > 0)
        pr.println();
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Bleeding ps5 = new Bleeding();
    ps5.run();
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

  public String toString() {
    return "("+this.first()+""+this.second()+")";
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}

class IntegerQuad implements Comparable < IntegerQuad > {
    Integer _first, _second, _third, _fourth;
    public IntegerQuad(Integer a, Integer b, Integer c, Integer d) {
        _first=a;
        _second=b;
        _third=c;
        _fourth=d;
    }

    public int compareTo(IntegerQuad o) {
        if(!this.first().equals(o.first()))
            return this.first()-o.first();
        else if(!this.second().equals(o.second()))
            return this.second()-o.second();
        else if(!this.third().equals(o.third()))
            return this.third()-o.third();
        else
            return this.fourth()-o.fourth();
    }

    public String toString() {
        return "("+_first+""+_second+""+_third+""+_fourth+")";
    }

    public Integer first() {return _first;}
    public Integer second() {return _second;}
    public Integer third() {return _third;}
    public Integer fourth() {return _fourth;}
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

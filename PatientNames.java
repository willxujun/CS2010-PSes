import java.util.*;
import java.io.*;

// write your matric number here:A0136139E
// write your name here:Xu Jun
// write list of collaborators here:Lu Lechuan, Wang Jinyi
// year 2017 hash code: DZAjKugdE9QiOQKGFbut (do NOT delete this line)

class Patient {
    public String name;
    public int height;
    public int size;
    public Patient parent, left, right;

    public Patient(String name) {
        this.name=name;
        this.height=0;
        this.size=1;
        parent=null;
        left=null;
        right=null;
    }

    public int compareTo(Patient other) {
        return this.name.compareTo(other.name);
    }

    public void updateHeight() {
        int leftHeight, rightHeight;
        if(this.left==null)
            leftHeight=-1;
        else
            leftHeight=this.left.height;
        if(this.right==null)
            rightHeight=-1;
        else
            rightHeight=this.right.height;
        height= Math.max(leftHeight, rightHeight) +1;
    }

    public void updateSize() {
        int leftSize, rightSize;
        if(this.left==null)
            leftSize=0;
        else
            leftSize=this.left.size;
        if(this.right==null)
            rightSize=0;
        else
            rightSize=this.right.size;
        size= leftSize+ rightSize + 1;
    }

    public int bf() {
        int leftHeight, rightHeight;
        if(this.left==null)
            leftHeight=-1;
        else
            leftHeight=this.left.height;
        if(this.right==null)
            rightHeight=-1;
        else
            rightHeight=this.right.height;
        return leftHeight-rightHeight;
    }
}

class AVLTree {
    public Patient root;

    public AVLTree() {
        root=null;
    }
   
    public int size(Patient node) {
        if(node==null)
            return 0;
        return node.size;
    }

    //print methods for debugging
    public void print() {
        print(root);    
    }

    public void print(Patient curr) {
        if(curr==null)
            return;
        else {
            print(curr.left);
            System.out.print(curr.name + " ");
            print(curr.right);
        }
    }

    public void printRoot() {
        System.out.println(root.name);
    }

    //search from the root of subtree
    public Patient search(Patient root, String key) {
        if(root==null) return null;                         //not found
        else if(root.name.equals(key)) return root;   //found
        else if(root.name.compareTo(key)>0) return search(root.left, key);
        else return search(root.right, key);
    }

    //findMin and findMax, from the root of subtree
    public Patient findMin(Patient root) {
        if(root==null) return null;                     //empty subtree
        else if(root.left==null) return root;           //found
        else return findMin(root.left);
    }

    public Patient findMax(Patient root) {
        if(root==null) return null;
        else if(root.right==null) return root;
        else return findMax(root.right);
    }

    //find successor
    public String successor(Patient p) {
        if(p.right!=null)
            return findMin(p.right).name;
        else {
            Patient cur=p;
            Patient par=cur.parent;
            //while current is not null, and cur is par's right child
            while(par!=null && cur==par.right) {
                cur=par;                       //keep moving up
                par=cur.parent;
            }
            if(par==null)                      //root reached, no successor
                return null;                   
            else
                return par.name;          //found
        }
    }

    //rotations
    public Patient rotateRight(Patient p) {
        //System.out.println("rotating right");
        Patient w=p.left;
        w.parent=p.parent;
        p.parent=w;
        p.left=w.right;
        if(w.right!=null)
            w.right.parent=p;
        w.right=p;
        
        p.updateHeight();
        p.updateSize();
        w.updateHeight();
        w.updateSize();

        return w;
    }
    
    public Patient rotateLeft(Patient p) {
        //System.out.println("rotating left");
        Patient w=p.right;
        w.parent=p.parent;
        p.parent=w;
        p.right=w.left;
        if(w.left!=null)
            w.left.parent=p;
        w.left=p;

        p.updateHeight();
        p.updateSize();
        w.updateHeight();
        w.updateSize();

        return w;
    }
    
    //overloaded add functions
    public void add(String name) {
        root=add(root, name);
    }
    
    public Patient add(Patient node, String key) {
        if(node==null) {
            return new Patient(key);
        }
        if(key.compareTo(node.name)>0) {
            node.right=add(node.right, key);
            node.right.parent=node;
        }
        else if(key.compareTo(node.name)==0)
            return null;
        else {
            node.left=add(node.left, key); 
            node.left.parent=node;
        }
        
        //update height and size
        node.updateHeight();
        node.updateSize();
        //System.out.println(node.name + " size: "+ size(node));

        //check balancing factor
        int bf= node.bf();
        if(bf>1 || bf<-1) {
            if(node.name.compareTo(key) >0) {
                if(node.left.name.compareTo(key) >0)
                    //left left case
                    return rotateRight(node);
                else {
                    //left right case
                    node.left= rotateLeft(node.left);
                    return rotateRight(node);
                }
            }
            else {
                if(node.right.name.compareTo(key) <0)
                    //right right case
                    return rotateLeft(node);
                else {
                    //right left case
                    node.right= rotateRight(node.right);
                    return rotateLeft(node);
                }
            }
        }
        //if no rotation occurs
        return node;
    }
    
    //overloaded remove functions
    public void remove(String name) {   //public remove method
        root=remove(root, name);
    }

    public Patient remove(Patient node, String key) {
        if(node==null) return node;          //no such patient
        
        if(node.name.compareTo(key) >0)
            node.left=remove(node.left, key);
        else if(node.name.compareTo(key)<0)
            node.right=remove(node.right, key);
        else {                              //found
            if(node.left==null && node.right==null)
                node=null;
            else if(node.left!=null && node.right==null) {
                node.left.parent=node.parent;
                node=node.left;
            }
            else if(node.left==null && node.right!=null) {
                node.right.parent=node.parent;
                node=node.right;
            }
            else {
                String successorName= successor(node);
                node.name=successorName;
                node.right=remove(node.right, successorName);
            }
        }
        
        if(node!=null) {
            node.updateHeight();
            node.updateSize();
            int bf= node.bf();
            if(bf<-1 || bf>1) {
                if(key.compareTo(node.name) >0) {
                    if(node.left.bf()>=0 && node.left.bf()<=1)
                        //left left case
                        return rotateRight(node);
                    else {
                        //left right case
                        node.left= rotateLeft(node.left);
                        return rotateRight(node);
                    }
                }
                else {
                    if(node.right.bf()>=-1 && node.right.bf()<=0)
                        //right right
                        return rotateLeft(node);
                    else {
                        node.right= rotateRight(node.right);
                        return rotateLeft(node);
                    }
                }
            }
        }
        return node;
    }
    
    //search methods in support of query, returns rank of satisfying node
    public Patient findLarger(Patient node, String high) {
        if(node==null) return null;
        int cmp= node.name.compareTo(high);
        if(cmp<0)                      //node is smaller than high
            return findLarger(node.right, high);        //node invalid, search on right
        
        Patient res= findLarger(node.left, high); //node is larger, see if better candidate exists on the left of node
        if(res!=null) return res;
        else return node;
    }

    public Patient findSmaller(Patient node, String low) {
        if(node==null) return null;
        int cmp= node.name.compareTo(low);
        if(cmp>=0)                   //node is bigger than low, invalid, to the left
            return findSmaller(node.left, low);

        //node is smaller, node could be good. see if larger valid node exists
        Patient res= findSmaller(node.right, low);
        if(res!=null) return res;
        else return node;
    }

    //rank to support query
    public int rank(Patient node, String name) {
        if(node==null) return 0;
        int cmp= node.name.compareTo(name);
        if(cmp==0) {
            //System.out.println("key==node");
            return size(node.left);
        }
        else if(cmp < 0) { //key is bigger than node
            //System.out.println("key >node");
            return 1+ size(node.left) + rank(node.right, name);
        }
        else {
            //System.out.println("key<node");
            return rank(node.left, name);
        }
    }
    public int query(String from, String to) {
        //first find the floor of to and ceiling of from
        Patient floor= findSmaller(root, to);
        Patient ceiling= findLarger(root, from);
        //System.out.println("floor name: " + floor.name+ "ceiling name: " + ceiling.name);
        int rF, rC;
        if(floor!=null && ceiling!=null) {
            rF= rank(root, floor.name);
            rC= rank(root, ceiling.name);
            //System.out.println("Floor rank: " +rF + "Ceiling rank: "+ rC);
            return rF-rC+1;
        }
        //no patient in range
        return 0;
    }
}

class PatientNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  // --------------------------------------------
    private AVLTree men;
    private AVLTree women;
    private HashMap<String, Integer> map;
  // --------------------------------------------

  public PatientNames() {
    // Write necessary code during construction;
    //
    // write your answer here

    // --------------------------------------------
    men= new AVLTree();
    women= new AVLTree();
    map=new HashMap<String, Integer> ();
    // --------------------------------------------
  }

  void AddPatient(String patientName, int gender) {
    // You have to insert the information (patientName, gender)
    // into your chosen data structure
    //
    // write your answer here

    // --------------------------------------------
    if(gender==1) {
        men.add(patientName);
        map.put(patientName, 1);
    }
    else {
        women.add(patientName);
        map.put(patientName, 2);
    }
   // System.out.print("men: "); men.print();
   // System.out.print("women: "); women.print();
    // --------------------------------------------
  }

  void RemovePatient(String patientName) {
    // You have to remove the patientName from your chosen data structure
    //
    // write your answer here
    
    // --------------------------------------------
    int gender= map.get(patientName);
    if(gender==1)
        men.remove(patientName);
    else
        women.remove(patientName);

   // System.out.print("men: "); men.print();
   // System.out.print("women: "); women.print();
    // --------------------------------------------
  }

  int Query(String START, String END, int gender) {
    int ans = 0;

    // You have to answer how many patient name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here

    // --------------------------------------------
    
    if(gender==1) {
        ans= men.query(START,END);
       // men.print();
       // System.out.println("query result(men): " + ans);
    }
    else if(gender==2) {
        ans= women.query(START,END);
       // women.print();
       // System.out.println("query result(women): " + ans);
    }
    else {
        ans= men.query(START,END) + women.query(START, END);
       // System.out.println("query result(all): " + ans);
    }
    // --------------------------------------------
    return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddPatient
        AddPatient(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemovePatient
        RemovePatient(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    PatientNames ps2 = new PatientNames();
    ps2.run();
  }
}

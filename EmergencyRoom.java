import java.util.*;
import java.io.*;

// write your matric number here:A0136139E
// write your name here:Xu Jun
// write list of collaborators here:
// year 2017 hash code: oIxT79fEI2IQdQqvg1rx (do NOT delete this line)

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
    private ArrayList<Patient> patients;
    private HashMap<String, Patient> patientMap;
    private int numPatients;
    private int time;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    patients= new ArrayList<Patient>(200001);
    patients.add(null);
    patientMap=new HashMap<String,Patient>(200000);
    numPatients=0;
    time=1;
  }

  //navigation around heap
  int parent(int index) {return index/2;}
  int left(int index) {return index*2;}
  int right(int index) {return index*2+1;}
  Patient getParent(int index) {
    if(index==1) 
        return null;
    else 
        return patients.get(parent(index));
  }
  Patient getLeft(int index) {
    if(left(index)>numPatients)
        return null;
    else
        return patients.get(left(index));
  }
  Patient getRight(int index) {
    if(right(index)>numPatients)
        return null;
    else
        return patients.get(right(index));
  }

  //operations on heap
  void swap(int i1, int i2) {
    Patient p1, p2;
    p1=patients.get(i1);
    p2=patients.get(i2);
    p1.setIndex(i2);
    p2.setIndex(i1);
    patients.set(i1, p2);
    patients.set(i2, p1);
  }

  void shiftUp(int index) {
    Patient p=patients.get(index);
    Patient parent=getParent(index);
    while(parent!=null && p.isHigherThan(parent)) {
        //System.out.println(1);
        swap(parent(index), index);
        index=parent(index);
        parent=getParent(index);
    }
  }

  void shiftDown(int index) {
    Patient p=patients.get(index);
    Patient left=getLeft(index);
    Patient right=getRight(index);
    Patient max;
    
    if(p==null)
        return;
    while(true) {
        //System.out.println(2);
        max=p;
        if(left!=null && left.isHigherThan(max)) {
            max=left;
        }
        if(right!=null && right.isHigherThan(max)) {
            max=right;
        }
        if(max!=p) {
            swap(index, max.getIndex());
            index=p.getIndex();
            left=getLeft(index);
            right=getRight(index);
        }
        else break;
    }
  }
  
  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    numPatients++;
    Patient p=new Patient(patientName, emergencyLvl, numPatients, time++);
    patientMap.put(patientName, p);
    patients.add(numPatients, p);
    shiftUp(numPatients);
  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here
    Patient p= patientMap.get(patientName);
    int index=p.getIndex();

    p.setLvl(p.getLvl() + incEmergencyLvl); 
    shiftUp(index);
  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here
    Patient p= patientMap.get(patientName);
    int index=p.getIndex();     //old static index of p

    swap(index, numPatients);
    patients.set(numPatients, null);
    numPatients--;
    
    Patient leaf, parent;
    //determine where to shift the leaf node
    if(index==1) {      //the root node is treated,including when root=leaf(1 patient)
        shiftDown(1);   //in shiftDown(1), if root is treated and is null, return
        return;
    }
    else {
        leaf=patients.get(index);
        if(leaf==null) { //the leaf node is treated and is nullified
            return;
        }
        else {         //a node with non-null parent and child is treated
            parent=getParent(index);
            if(leaf.isHigherThan(parent))
                shiftUp(index);
            else
                shiftDown(index);
        }
    }
  }

  String Query() {
    String ans = "The emergency room is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency room is empty"
    //
    // write your answer here
    if(numPatients==0)
        return ans;
    else {
    //    System.out.println(patients.get(1).getName());
        return patients.get(1).getName();
    }
  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateEmergencyLvl(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: Treat(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    EmergencyRoom ps1 = new EmergencyRoom();
    ps1.run();
  }
}

class Patient {
    private String name;
    private int lvl;
    private int index;
    private int time;
    public Patient(String name, int lvl, int index, int time) {
        this.name=name;
        this.lvl=lvl;
        this.index=index;
        this.time=time;
    }
        
    public String getName() {return name;}
    public int getLvl() {return lvl;}
    public int getIndex() {return index;}
    public int getTime() {return time;}

    public boolean isHigherThan(Patient x) {
        if(lvl>x.getLvl())
            return true;
        else if(lvl==x.getLvl() && time<x.getTime())
            return true;
        return false;
    }
    public void setLvl(int lvl) {this.lvl=lvl;}
    public void setIndex(int index) {this.index=index;}
}

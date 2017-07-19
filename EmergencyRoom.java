// Copy paste this Java Template and save it as "EmergencyRoom.java"
import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here:
// year 2017 hash code: oIxT79fEI2IQdQqvg1rx (do NOT delete this line)
class Patient {
    private String name;
    private int lvl;
    private int time;

    public Patient (String name, int lvl, int time) {
        this.name=name;
        this.lvl=lvl;
    }
    public String getName() {
        return name;
    }
    public int getLvl() {
        return lvl;
    }
    public int getTime() {
        return time;
    }
    public boolean isHigherThan(Patient other) {
        if(lvl > other.getLvl())
            return true;
        else if(lvl==other.getLvl() && time < other.getTime())
            return true;
        else
            return false;
    }
}

class EmergencyRoom {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  private ArrayList<Patient> arr;
  private HashMap<String, int> map;
  private time;
  private numPatients;

  public EmergencyRoom() {
    // Write necessary code during construction
    //
    // write your answer here
    arr=new ArrayList<Patient>(200001);
    map=new HashMap<String, int>(200000);
    arr.add(null);
    time=1;
    numPatients=0;
  }

  //navigation in heap
  int parent(int index) {
    return index/2;
  }
  int left(int index) {
    return index*2;
  }
  int right(int index) {
    return index*2+1;
  }
  Patient getParent(int index) {
    return arr.get(parent(index));
  }
  Patient getLeft(int index) {
    if(left(index) > numPatients)
        return null;
    else
        return arr.get(left(indnex));
  }
  Patient getRight(int index) {
    if(right(index) > numPatients)
        return null;
    else
        return arr.get(right(index));
  }
  //heap operations
  void swap(int i1, int i2) {
    Patient tmp=arr.get(i1);

    map.put(arr.get(i1).getName(), i2);
    map.put(arr.get(i2).getName(), i1);
    
    arr.set(i1, arr.get(i2));
    arr.set(i2, tmp);
  }
  void shiftUp(int index) {
    Patient p=arr.get(index);
    Patient parent=arr.get(parent(index));
    while(index>1 && p.compareTo(parent)==1)
        swap(index, parent(index));
        index=parent(index);
        p=arr.get(index);
        parent=arr.get(parent(index));
    }
  }
  void shiftDown(int index) {
    Patient p=arr.get(index);
    Patient left=getLeft(index);
    Patient right=getRight(index);
    
  }
  void ArriveAtHospital(String patientName, int emergencyLvl) {
    // You have to insert the information (patientName, emergencyLvl)
    // into your chosen data structure
    //
    // write your answer here
    


  }

  void UpdateEmergencyLvl(String patientName, int incEmergencyLvl) {
    // You have to update the emergencyLvl of patientName to
    // emergencyLvl += incEmergencyLvl
    // and modify your chosen data structure (if needed)
    //
    // write your answer here



  }

  void Treat(String patientName) {
    // This patientName is treated by the doctor
    // remove him/her from your chosen data structure
    //
    // write your answer here



  }

  String Query() {
    String ans = "The emergency room is empty";

    // You have to report the name of the patient that the doctor
    // has to give the most attention to currently. If there is no more patient to
    // be taken care of, return a String "The emergency room is empty"
    //
    // write your answer here



    return ans;
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

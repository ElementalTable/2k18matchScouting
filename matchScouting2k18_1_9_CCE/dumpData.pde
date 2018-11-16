public class dumpData {
  // Fields
  //JSONArray previousValues = loadJSONArray("data/Entries.json");
  //PrintWriter output = createWriter("Entries.txt");

  int matchNum;
  int teamNum;

  String matchNotes;
  // Constructors
  public dumpData(int m, int t, String mN) {
    this.matchNum = m;
    this.teamNum = t;
    this.matchNotes = mN;
  }

  // Methods
  void outputToFile() {
  }
  void closeFile() {
    println("Output File Closed");
  }
}

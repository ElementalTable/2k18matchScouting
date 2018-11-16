public class checkBoxGroup {
 // Fields
 int bgR, bgG, bgB;
 float x,y;
 ArrayList<checkBox> boxes = new ArrayList<checkBox>();
 int numBoxes;
 int checkedBox=-1;
 
 // Constructors
 public checkBoxGroup (int x, int y,int bgR, int bgG, int bgB, int numBoxes, int checkedBox) {
   this.x = x;
   this.y = y;
   this.bgR = bgR;
   this.bgG = bgG;
   this.bgB = bgB;
   this.numBoxes = numBoxes;
   this.checkedBox = checkedBox;
   boolean checked = false;
   checkBox thisBox;
   x = x+30;
   //numBoxes = numBoxes + 1;
   //println(numBoxes);
   for (int i = 0; i<=numBoxes; i++) {
     //println(i);
     if (i == checkedBox) {
       checked = true;
     } else {
       checked = false;
     }
    
    // Need to set the x,y value of each checkBox
    thisBox = new checkBox(x+(i-1)*120, y, bgR, bgG, bgB, checked, "");
    //println("Adding 1 checkbox");
    boxes.add(thisBox);
      }
    }
 
  //methods
  public void draw() {
    checkBox thisBox;
    for(int i = 0; i<this.numBoxes; i++) {
      thisBox = boxes.get(i);
      thisBox.draw();
      } 
    }
  
  void mousePressed() {
    checkBox thisBox;
    for(int i = 0; i<this.numBoxes; i++) {
      //println(i);
      thisBox = boxes.get(i);
      boolean checkedBefore = thisBox.isChecked;
      thisBox.mousePressed();
      if (checkedBefore != thisBox.isChecked) {
        if (thisBox.isChecked == true) {
          if (checkedBox != -1) {
            //2nd case
            boxes.get(checkedBox).isChecked = false;
            checkedBox = i;
            //println(i+" clicked");
            } else {
            checkedBox = i;
            //println(i+" clicked");
          }
        } else {checkedBox = -1;}
      } 
    }
  }
  
  void setBox(int boxNum) {
    checkedBox = boxNum;
  }
}

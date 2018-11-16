public class transparentButton {
  // Fields
  int bgR, bgG, bgB;
  int count;
  float x, y, w, h;
  int Tr, Tg, Tb;
  String text;
  boolean activated;
  float[] boxCs = {0, 0, 0, 0};
  float FinalTime;
  boolean isPickUp;

  //private static final int kENTER = 10;
  //private static final int kBACKSPACE = 8;

  // Constructors
  public transparentButton (float x, float y, float w, float h, boolean isPickUp) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.boxCs[0] = x;
    this.boxCs[1] = y;
    this.boxCs[2] = w;
    this.boxCs[3] = h;
    this.isPickUp = isPickUp;
  } // end of transparentButton
  // Methods 


  public void draw() {
    noFill();
    strokeWeight(4);
    rect(x, y, w, h);
    //println(x+" "+y);
  }

  void mousePressed() {
    int mX=mouseX;
    int mY=mouseY;

    if (mX>this.boxCs[0] && mY>this.boxCs[1] && mX<this.boxCs[0]+this.boxCs[2] && mY<this.boxCs[1]+this.boxCs[3]) {
      activated = true;
      count ++;
      if (isPickUp) {
        //cycleTimer.startTimer();
      } else {
        //FinalTime = cycleTimer.endTimer();
      }
    } else {
      activated = false;
    }
  } // end of mousePressed


  void moveButton(float newX) {
    x = newX;
    redraw();
    boxCs[0]=x;
  }//end of moveButton
} //end of class

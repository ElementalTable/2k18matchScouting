public class checkBox {
 // Fields
 int bgR, bgG, bgB;
 float x,y;
 boolean isChecked;
 float[] boxCs = {0,0,80,80};
 PImage img = loadImage("checkmark-256.png");
 boolean activated;
 String label;

 // Constructors
 public checkBox (float x, float y,int bgR, int bgG, int bgB,boolean isChecked, String label) {
  this.x = x;
  this.y = y;
  this.bgR = bgR;
  this.bgG = bgG;
  this.bgB = bgB;
  this.isChecked = isChecked;
  this.boxCs[0] = x;
  this.boxCs[1] = y;
  this.label = label;
 }
 
  //methods
  public void updateColor(int bgR, int bgG, int bgB) {
    this.bgR = bgR;
    this.bgG = bgG;
    this.bgB = bgB;
    //update();
  }
  
  public void draw() {
    fill(bgR, bgG, bgB);
    rect(x,y,boxCs[2],boxCs[3]);
    fill(255);
    text(label, x+100, y+50);
    if (isChecked) {
      img.resize(int(boxCs[2]),int(boxCs[3]));
      image(img,x,y);
      //println("Image loaded");
    }
  }
  
  void mousePressed() {
    //println("stuff happened");
    int mX=mouseX;
    int mY=mouseY;
    //println(mX+" "+mY);
    //println("checking if a checkbox was clicked");
    if(mX>this.boxCs[0] && mY>this.boxCs[1] && mX<this.boxCs[0]+this.boxCs[2] && mY<this.boxCs[1]+this.boxCs[3]) {
      //println("checkmark clicked");
      activated = true;
      if (isChecked == true) {isChecked = false;}
      else {isChecked = true;}
        //println(True);
        //return true;
    } else {
     activated = false; 
    }  
  }
}

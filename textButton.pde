public class textButton {
 // Fields
 int bgR, bgG, bgB;
 float x,y,w,h;
 int Tr, Tg, Tb;
 String text;
 boolean activated;
 boolean isChecked;
 float[] boxCs = {0,0,0,0};
 //private static final int kENTER = 10;
 //private static final int kBACKSPACE = 8;
 
 // Constructors
 public textButton (float x, float y, float w, float h, int bgR, int bgG, int bgB, int Tr, int Tg, int Tb, String text,boolean activated) {
  this.x = x;
  this.y = y;
  this.w = w;
  this.h = h;
  this.bgR = bgR;
  this.bgG = bgG;
  this.bgB = bgB;
  this.Tr = Tr;
  this.Tg = Tg;
  this.Tb = Tb;
  this.text = text;
  this.activated = activated;
  this.boxCs[0] = x;
  this.boxCs[1] = y;
  this.boxCs[2] = w;
  this.boxCs[3] = h;
 }
 
  // Methods 
  public void updateColor(int bgR, int bgG, int bgB) {
    this.bgR = bgR;
    this.bgG = bgG;
    this.bgB = bgB;
  }
  
  public void draw() {
    fill(bgR, bgG, bgB);
    rect(x,y,w,h);
    fill(Tr,Tg,Tb);
    text(text, x+10, y+40);
  }
  
  void mousePressed() {
  int mX=mouseX;
  int mY=mouseY;

  if(mX>this.boxCs[0] && mY>this.boxCs[1] && mX<this.boxCs[0]+this.boxCs[2] && mY<this.boxCs[1]+this.boxCs[3]) {
    activated = true;
    updateColor(220, 220, 220);
    } else {
    activated = false; updateColor(200, 200, 200);
    }
  }
}

public class counter {
  int bgR, bgG, bgB;
  float x,y;
  int Tr, Tg, Tb;
  int start = 0;
  boolean activated;
  int[] box1Cs = {0,0,0,0};
  int[] box2Cs = {0,0,0,0};
  int[] box3Cs = {0,0,0,0};
  int[] box4Cs = {0,0,0,0};
  int selectedBox = 0;
  int size;
  int gap;
    
  public counter (float x, float y, int size, int gap, int bgR, int bgG, int bgB, int Tr, int Tg, int Tb, int start, boolean activated) {
    this.x = x;
    this.y = y;
    this.size = size;
    this.gap = gap;
    this.bgR = bgR;
    this.bgG = bgG;
    this.bgB = bgB;
    this.Tr = Tr;
    this.Tg = Tg;
    this.Tb = Tb;
    this.start = start;
    this.activated = activated;
    
    this.box1Cs[0] = int(x);
    this.box1Cs[1] = int(y + size/6);
    
    this.box1Cs[2] = int(x + size);
    this.box1Cs[3] = int(y + size*7/6);
    
    this.box2Cs[0] = int(x + size + gap);
    this.box2Cs[1] = int(y + size/6);
    
    this.box2Cs[2] = int(x + size*2 + gap);
    this.box2Cs[3] = int(y + size*7/6);
    
    this.box3Cs[0] = int(x + size*10/3 + gap*3);
    this.box3Cs[1] = int(y + size/6);
    
    this.box3Cs[2] = int(x + size*13/3 + gap*3);
    this.box3Cs[3] = int(y + size*7/6);
    
    this.box4Cs[0] = int(x + size*13/3 + gap*4);
    this.box4Cs[1] = int(y + size/6);
    
    this.box4Cs[2] = int(x + size*16/3 + gap*4);
    this.box4Cs[3] = int(y + size*7/6);
  }
  public void draw() {
    fill(bgR, bgG, bgB);
    
    rect(box1Cs[0],box1Cs[1],size,size);//-10
    rect(box2Cs[0],box2Cs[1],size,size);//-1
    rect(x+size*2 + gap*2,y,size*4/3,size*4/3);//number
    rect(box3Cs[0],box3Cs[1],size,size);//+1
    rect(box4Cs[0],box4Cs[1],size,size);//+10
    
    float tx, ty; // text x and y position
    
    fill(Tr,Tg,Tb);
    textFont(font,32);
    tx = x + size/2 - 8;
    ty = y + size*2/3 + 8;
    text("-", tx, ty);
    tx = tx + size + gap;
    text("-", tx, ty);
    tx = tx + size*7/6 + gap;
    text(start, tx, ty);
    tx = tx + size*7/6 + gap;
    text("+",tx,ty);
    tx = tx + size + gap;
    text("+",tx,ty); 
  }
  
  void mousePressed() {
    //println("checking if a counter was clicked");
    int mX=mouseX;
    int mY=mouseY;
    if(mX>box1Cs[0] && mY>box1Cs[1] && mX<box1Cs[2] && mY<box1Cs[3]) {
      if (start > 9) {
        start = start - 10;
      } else {start = 0;}
      //println("-10 points");
      //println(start);
      selectedBox = 1;
    } else if(mX>box2Cs[0] && mY>box2Cs[1] && mX<box2Cs[2] && mY<box2Cs[3]) {
      if(start>0) {start = start - 1;}
      //println("-1 point");
      //println(start);
      selectedBox = 2;
    } else if(mX>box3Cs[0] && mY>box3Cs[1] && mX<box3Cs[2] && mY<box3Cs[3]) {
      start = start + 1;
      //println("+1 point");
      //println(start);
      selectedBox = 3;
    } else if(mX>box4Cs[0] && mY>box4Cs[1] && mX<box4Cs[2] && mY<box4Cs[3]) {
      start = start + 10;
      //println("+10 points");
      //println(start);
      selectedBox = 4;
    } else {
      selectedBox = 0;
    }
    //println("selected Box: "+selectedBox);
  }
}

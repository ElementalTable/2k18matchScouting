import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class matchScouting2k18_1_9_CCE extends PApplet {

// setup: (variables and stuff)
PrintWriter output;
PFont font;
PImage field;
PImage blackOnTrans;
PImage flippedField;
//PImage cycleTimesQRCode;
PImage dataOutQRCode;
String Input="";
//int NumOfValsInCycleTimes=0;
int v=110;
int i=0;
int y = 0;
int x = 0;
int t, m = 2, s;
int interval = 150;
int startTime;
int TEAMNUM;
int foulCounter = 0;
int techFoulCounter = 0;
int scout = 0;
int scoutNum = 0;
int disabledOutput = 0;
int POTFOutput = 0;
int flippedOutput = 0;
int attemptedOutput = 0;
int assistedOutput = 0;
int successfulOutput = 0;
int noShowOutput = 0;
int redCardOutput = 0;
int yellowCardOutput = 0;
String time;
String sS = "30";
String alli;
String lastButtonPressed = "";
String printButtonPressed = "";
boolean run = false;
boolean matchEnded;
boolean timing = false;
boolean generated = false;
boolean firstTime= true;
boolean allia = false;

//Counter Variables
int[] paths = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

//Display Color for text in chatbox:
int red = 0;
int green = 0;
int purple = 0;

int page = 1;

JSONArray values;
JSONObject pathTimes = new JSONObject();

//timers:
timer cycleTimer;

// To add textboxes type: textBox (unique variable name);
textBox teamNumber;
textBox teamMember;
textBox alliance;
textButton end;
textBox times;
textBox portalCount;

// To add textButtons type: textButton (unique variable name);
textButton startMatchButton;
textButton loadMatchButton;

//Timer Ending Text Buttons (unique variable name);
timerEndingTextButton droppedBox;

// to add transparentButtons: transparentButton (unique variable name);
transparentButton portal;
transparentButton cubePileZone;
transparentButton cubeLineZone;
transparentButton opponentCubeLineZone;
transparentButton allianceSwitch;
transparentButton scale;
transparentButton opponentSwitch;
transparentButton exchangeDropoff;
transparentButton exchangePickup;

// To add counters type: counter (unique variable name);
counter matchNumber;
counter foul;
counter techFoul;
counter Points;

// To add checkBoxGroups type: checkBoxGroup (unique variable name);
checkBoxGroup rankingPoints;
checkBoxGroup rating;
checkBoxGroup defenseRating;
checkBoxGroup offenseRating;
checkBoxGroup pageSelect;
checkBoxGroup tournament;

// To add check Boxes type: checkBox (unique variable name);
checkBox disabled;
checkBox potf;
checkBox flippedOver;
checkBox attemptedClimb;
checkBox successfulClimb;
checkBox noShow;
checkBox yellowCard;
checkBox redCard;
checkBox techFoul1;
checkBox foul1;
checkBox assistedClimb;
checkBox flipField;

// To add text button groups type: textButtonGroup (unique variable name);
textButtonGroup nameSelect;

boolean mousePos;
int mX = mouseX;
int mY = mouseY;
int matchNumberOut = 1;
int teamNumberOut = 1111;
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

//StringList cycleTimes;
//String currentCycleTime;
String currentMatchData;
String matchNotesOut = "Notes";
String activeBox = "";

ChildApplet child;

public void settings() {
  fullScreen();
  //smooth();
}

public void setup () {
  frameRate(10);
  //cycleTimes = new StringList();
  //output = createWriter("cycleTimes.txt");
  
  field = loadImage("field.PNG");
  flippedField = loadImage("fieldFlipped.png");
  blackOnTrans = loadImage("blackOnTrans.png");
  surface.setTitle("Match Scouting 2018");
  // this is where the team number is chosen via search and choose
  //cycleTimer = new timer();

  child = new ChildApplet();
  font = createFont("Arial", 16, true);

  // All of the variables above NEED to be declared here. If they are not nothing will work and it WILL CRASH!!!!
  // Also please put them in the right places. not gonna crash if you dont but please do.F

  //on all pages
  alliance = new textBox(1550, 10, 80, 80, 0, 0, 255, 0, 0, 0, "", false, false, true);
  
  //page 1
  tournament = new checkBoxGroup(140, 500, 200, 200, 200, 2, 0);
  pageSelect = new checkBoxGroup(825, 10, 200, 200, 200, 3, 0);
  nameSelect = new textButtonGroup(600, 200, 200, 200, 200, 5, 5, 0);
  matchNumber = new counter(10, 900, 120, 80, 200, 200, 200, 0, 0, 0, 1, false);
  teamNumber = new textBox(10, 200, 500, 60, 200, 200, 200, 0, 0, 0, "Team Number: ", false, true, false);
  teamMember = new textBox(10, 300, 500, 60, 200, 200, 200, 0, 0, 0, "Scout  Name: ", false, true, false);
  flipField = new checkBox(140, 700, 200, 200, 200, false, "Flip Field");
  //page 2
  
  times = new textBox(1650, 10, 200, 60, 200, 200, 200, 0, 0, 0, "Time: ", false, false, false);
  startMatchButton = new textButton(1650, 80, 200, 60, 200, 200, 200, 0, 0, 0, "Start Match", false);
  loadMatchButton = new textButton(390, 1000, 200, 60, 200, 200, 200, 0, 0, 0, "Load Match", false);  
  //println(scoutNum);
  
           //new checkBox(float x, float y,int bgR, int bgG, int bgB,boolean isChecked)
  disabled = new checkBox(600, 870, 200, 200, 200, false, "Disabled");
  potf = new checkBox(600, 960, 200, 200, 200, false, "Parts On the Field");
  flippedOver = new checkBox(600, 1050, 200, 200, 200, false, "Flipped Over");
  attemptedClimb = new checkBox(1000, 870, 200, 200, 200, false, "Attempted Climb");
  successfulClimb = new checkBox(1000, 960, 200, 200, 200, false, "Successful Climb");
  noShow = new checkBox(70, 970, 200, 200, 200, false, "No Show"); //1000, 1050
  yellowCard = new checkBox(1600, 870, 200, 200, 200, false, "Yellow Card");
  redCard = new checkBox(1600, 960, 200, 200, 200, false, "Red Card");
  techFoul1 = new checkBox(1600, 1050, 200, 200, 200, false, "Tech Foul");
  foul1 = new checkBox(1400, 1050, 200, 200, 200, false, "Foul");
  assistedClimb = new checkBox(1000, 1050, 200, 200, 200, false, "Assisted Climb");//70, 970
  droppedBox = new timerEndingTextButton(70, 880, 225, 70, 200, 200, 200, 0, 0, 0, "Dropped Box", false); 
  
  //transparent buttons on page 2:
  exchangeDropoff = new transparentButton(75, 160, 235, 630, false);
  exchangePickup = new transparentButton(310, 160, 120, 630, true);
  cubePileZone = new transparentButton(430, 160, 120, 630, true);
  allianceSwitch = new transparentButton(550, 160, 150, 630, false);
  cubeLineZone = new transparentButton(700, 160, 130, 630, true);
  scale = new transparentButton(830, 160, 240, 630, false);
  opponentCubeLineZone = new transparentButton(1070, 160, 130, 630, true);
  opponentSwitch = new transparentButton(1200, 160, 150, 630, false);
  portal = new transparentButton(1590, 160, 235, 630, true);

  //page 3
  Points = new counter(440, 240, 80, 20, 200, 200, 200, 0, 0, 0, 0, false); 
  rankingPoints = new checkBoxGroup(400, 430, 200, 200, 200, 4, -1);
  rating = new checkBoxGroup(620, 620, 200, 200, 200, 5, -1);
  defenseRating =  new checkBoxGroup(410, 810, 200, 200, 200, 5, -1);
  offenseRating =  new checkBoxGroup(410, 1000, 200, 200, 200, 5, -1);
  end = new textButton(1700, 950, 200, 80, 30, 255, 30, 0, 0, 0, "End Match", false);
}

public void mousePressed() {
  activeBox = "";
       // boolean AUTO = false;
       // if (m == 2 && s >= 15) {
       //   AUTO = true;
       // }
         
  // in correct page number, all variables that need to register clicks need to be declared here.
  // ex. var.mousePressed();
  
  if (page == 1) {
    matchNumber.mousePressed();
    teamNumber.mousePressed();
    teamMember.mousePressed();
    if (teamMember.activated == true) {
      activeBox = "teamMember";
    }
    nameSelect.mousePressed();
    loadMatchButton.mousePressed();
    if (loadMatchButton.activated) {
      loadJSON(matchNumber.start);
    }
    tournament.mousePressed();
    flipField.mousePressed();
  } 
  if (page == 2) {
    disabled.mousePressed();
    if (disabled.activated == true) {
      lastButtonPressed = "disabled";
      printButtonPressed = "Disabled";
      red = 0;
      green = 0;
      purple = 1;
    }
    potf.mousePressed();
    if (potf.activated == true) {
      lastButtonPressed = "partOnTheField";
      printButtonPressed = "Part On The Field";
      red = 0;
      green = 0;
      purple = 1;
    }
    flippedOver.mousePressed();
    if (flippedOver.activated == true) {
      lastButtonPressed = "flippedOver";
      printButtonPressed = "Flipped Over";
      red = 0;
      green = 0;
      purple = 1;
    }
    attemptedClimb.mousePressed();
    if (attemptedClimb.activated == true) {
      lastButtonPressed = "attemptedClimb";
      printButtonPressed = "Attempted Climb";
      red = 0;
      green = 0;
      purple = 1;
    }
    successfulClimb.mousePressed();
    if (successfulClimb.activated == true) {
      attemptedClimb.isChecked = true;
    }
    if (successfulClimb.activated == true) {
      lastButtonPressed = "successfulClimb";
      printButtonPressed = "Successful Climb";
      red = 0;
      green = 0;
      purple = 1;
    }
    noShow.mousePressed();
     if (noShow.activated == true) {
      lastButtonPressed = "noShow";
      printButtonPressed = "No Show";
      red = 0;
      green = 0;
      purple = 1;
     }
     yellowCard.mousePressed();
    if (yellowCard.activated == true) {
      lastButtonPressed = "yellowCard";
      printButtonPressed = "Yellow Card";
      red = 0;
      green = 0;
      purple = 1;
    }
     assistedClimb.mousePressed();
     if (assistedClimb.activated == true) {
      lastButtonPressed = "assistedClimb";
      printButtonPressed = "Assisted Climb";
      red = 0;
      green = 0;
      purple = 1;
     }
     //println(printButtonPressed);
     redCard.mousePressed();
    if (redCard.activated == true){
      lastButtonPressed = "redCard";
      printButtonPressed = "Red Card";
      
      red = 0;
      green = 0;
      purple = 1;
    }
    //Pickup Buttons:
    portal.mousePressed();//Pickup
    if (portal.activated == true) {
      lastButtonPressed = "portal";
      printButtonPressed = "Portal";
      red = 1;
      green = 0;
      purple = 0;
    }
    opponentCubeLineZone.mousePressed();//Pickup
    if (opponentCubeLineZone.activated == true) {
      lastButtonPressed = "opponentCubeLineZone";
      printButtonPressed = "Opponent Cube Line Zone";
      red = 1;
      green = 0;
      purple = 0;
    }
    cubeLineZone.mousePressed();//Pickup
    if (cubeLineZone.activated == true) {
      lastButtonPressed = "cubeLineZone";
      printButtonPressed = "Cube Line Zone";
      red = 1;
      green = 0;
      purple = 0;
    }
    exchangePickup.mousePressed();//Pickup
    if (exchangePickup.activated == true) {
      lastButtonPressed = "exchangePickup";
      printButtonPressed = "Exchange Pickup"; 
      red = 1;
      green = 0;
      purple = 0;
    }
    cubePileZone.mousePressed();//Pickup
    if (cubePileZone.activated == true) {
      lastButtonPressed = "cubePileZone";
      printButtonPressed = "Cube Pile Zone";
      red = 1;
      green = 0;
      purple = 0;
    }
    techFoul1.mousePressed();
    if (techFoul1.activated == true){
      lastButtonPressed = "techFoul1";
      printButtonPressed = "Tech Foul";
      red = 0;
      green = 0;
      purple = 1;
      
    }
    foul1.mousePressed();
    if (foul1.activated == true){
      lastButtonPressed = "foul1"; 
      printButtonPressed = "Foul";
      red = 0;
      green = 0;
      purple = 1;
    }

    //Dropoff Buttons:
    opponentSwitch.mousePressed();
    if (opponentSwitch.activated == true) {
                                        //if (lastButtonPressed=="portal")
                                        //{
                                        //  paths[1] = paths[1]+1;
                                        //  //saveCycleTime(2, opponentSwitch.FinalTime, paths[1],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="exchangePickup")
                                        //{
                                        //  paths[6] = paths[6]+1;
                                        //  //saveCycleTime(7, opponentSwitch.FinalTime, paths[6],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="cubePileZone")
                                        //{
                                        //  paths[11] = paths[11]+1;
                                        //  //saveCycleTime(12, opponentSwitch.FinalTime, paths[11],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="cubeLineZone")
                                        //{
                                        //  paths[16] = paths[16]+1;
                                        //  //println("path 17 count: "+paths[16]);
                                        //  //saveCycleTime(17, opponentSwitch.FinalTime, paths[16],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="opponentCubeLineZone")
                                        //{
                                        //  paths[21] = paths[21]+1;
                                        //  //saveCycleTime(22, opponentSwitch.FinalTime, paths[21],AUTO,true);
                                        //}
      lastButtonPressed = "opponentSwitch";
      printButtonPressed = "Opponent Switch";
      red = 0;
      green = 1;
      purple = 0;
    }
    scale.mousePressed();
    if (scale.activated == true) {
                                //if (lastButtonPressed=="portal")
                                //{
                                //  paths[2] = paths[2]+1;
                                //  //saveCycleTime(3, scale.FinalTime, paths[2],AUTO,true);
                                //}
                                //if (lastButtonPressed=="exchangePickup")
                                //{
                                //  paths[7] = paths[7]+1;
                                //  //saveCycleTime(8, scale.FinalTime, paths[7],AUTO,true);
                                //}
                                //if (lastButtonPressed=="cubePileZone")
                                //{
                                //  paths[12] = paths[12]+1;
                                //  //saveCycleTime(13, scale.FinalTime, paths[12],AUTO,true);
                                //}
                                //if (lastButtonPressed=="cubeLineZone")
                                //{
                                //  paths[17] = paths[17]+1;
                                //  //saveCycleTime(18, scale.FinalTime, paths[17],AUTO,true);
                                //}
                                //if (lastButtonPressed=="opponentCubeLineZone")
                                //{
                                //  paths[22] = paths[22]+1;
                                //  //saveCycleTime(23, scale.FinalTime, paths[22],AUTO,true);
                                //}
      lastButtonPressed = "scale";
      printButtonPressed = "Scale";
      red = 0;
      green = 1;
      purple = 0;
    }
    allianceSwitch.mousePressed();
    if (allianceSwitch.activated == true) {
                                        //if (lastButtonPressed=="portal")
                                        //{
                                        //  paths[0] = paths[0]+1;
                                        //  //saveCycleTime(1, allianceSwitch.FinalTime, paths[0],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="exchangePickup")
                                        //{
                                        //  paths[5] = paths[5]+1;
                                        //  //saveCycleTime(6, allianceSwitch.FinalTime, paths[5],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="cubePileZone")
                                        //{
                                        //  paths[10] = paths[10]+1;
                                        //  //saveCycleTime(11, allianceSwitch.FinalTime, paths[10],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="cubeLineZone")
                                        //{
                                          
                                        //  paths[15] = paths[15]+1;
                                        //  //saveCycleTime(16, allianceSwitch.FinalTime, paths[15],AUTO,true);
                                        //}
                                        //if (lastButtonPressed=="opponentCubeLineZone")
                                        //{
                                        //  paths[20] = paths[20]+1;
                                        //  //saveCycleTime(21, allianceSwitch.FinalTime, paths[20],AUTO,true);
                                        //}
      lastButtonPressed = "allianceSwitch";
      printButtonPressed = "Alliance Switch";
      red = 0;
      green = 1;
      purple = 0;
    }
    exchangeDropoff.mousePressed();
    if (exchangeDropoff.activated == true) {
                                          //if (lastButtonPressed=="portal")
                                          //{
                                          //  paths[3] = paths[3]+1;
                                          //  //saveCycleTime(4, exchangeDropoff.FinalTime, paths[3],AUTO,true);
                                          //}
                                          //if (lastButtonPressed=="exchangePickup")
                                          //{
                                          //  paths[8] = paths[8]+1;
                                          //  //saveCycleTime(9, exchangeDropoff.FinalTime, paths[8],AUTO,true);
                                          //}
                                          //if (lastButtonPressed=="cubePileZone")
                                          //{
                                          //  paths[13] = paths[13]+1;
                                          //  //saveCycleTime(14, exchangeDropoff.FinalTime, paths[13],AUTO,true);
                                          //}
                                          //if (lastButtonPressed=="cubeLineZone")
                                          //{
                                          //  paths[18] = paths[18]+1;
                                          //  //saveCycleTime(19, exchangeDropoff.FinalTime, paths[18],AUTO,true);
                                          //}
                                          //if (lastButtonPressed=="opponentCubeLineZone")
                                          //{
                                          //  paths[23] = paths[23]+1;
                                          //  //saveCycleTime(24, exchangeDropoff.FinalTime, paths[23],AUTO,true);
                                          //}
      lastButtonPressed = "exchangeDropoff";
      printButtonPressed = "Exchange Dropoff";
      red = 0;
      green = 1;
      purple = 0;
    }
    droppedBox.mousePressed();
    if (droppedBox.activated == true) {
                                      //if (lastButtonPressed=="portal")
                                      //{
                                      //  paths[4] = paths[4]+1;
                                      //  //saveCycleTime(5, droppedBox.FinalTime, paths[4],AUTO,true);
                                      //}
                                      //if (lastButtonPressed=="exchangePickup")
                                      //{
                                      //  paths[9] = paths[9]+1;
                                      //  //saveCycleTime(10, droppedBox.FinalTime, paths[9],AUTO,true);
                                      //}
                                      //if (lastButtonPressed=="cubePileZone")
                                      //{
                                      //  paths[14] = paths[14]+1;
                                      //  //saveCycleTime(15, droppedBox.FinalTime, paths[14],AUTO,true);
                                      //}
                                      //if (lastButtonPressed=="cubeLineZone")
                                      //{
                                      //  paths[19] = paths[19]+1;
                                      //  //saveCycleTime(20, droppedBox.FinalTime, paths[19],AUTO,true);
                                      //}
                                      //if (lastButtonPressed=="opponentCubeLineZone")
                                      //{
                                      //  paths[24] = paths[24]+1;
                                      //  //saveCycleTime(25, droppedBox.FinalTime, paths[24],AUTO,true);
                                      //}
      lastButtonPressed = "droppedBox";
      printButtonPressed = "Dropped Box";
      red = 0;
      green = 1;
      purple = 0;
    }
    
    startMatchButton.mousePressed();

    if (startMatchButton.activated == true) {
      startMatchButton.updateColor(0, 0, 255);
      run = true;
      startTime = millis();
    }
    
  }
  if (page == 3) {
    end.mousePressed();
    if (end.activated == true) {
      end.updateColor(30, 255, 30);
      println ("End Maatch");
      activeBox = "end";
      run = false;
      startMatchButton.updateColor(200, 200, 200);
      pageSelect.boxes.get(2).isChecked = false;
      pageSelect.boxes.get(3).isChecked = true;
      pageSelect.setBox(3);
      saveJSON();
      //saveCycleTime(0, 0.0, 1, false, false);
      matchNumber.start = matchNumber.start + 1;
      clearEntries();
      //cycleTimes.clear();
      loadJSON(matchNumber.start);
    }
    defenseRating.mousePressed();
    offenseRating.mousePressed();
    rankingPoints.mousePressed();
    rating.mousePressed();
    Points.mousePressed();
  }

  // this is the places where things stay on all pages.
  pageSelect.mousePressed();
  page = pageSelect.checkedBox+1;
  //println(page);
    
}

public void draw() {
  
  background(0);
    
  tint(105);
  blackOnTrans.resize(1920,1518);
  image(blackOnTrans, 0, 0);
  noTint();
    
  fill(255);
  rect(10, 10, 420, 80);
  //title
  textFont(font, 40);
  fill(0);
  text("Match Scouting 2018!", 20, 60);
  alliance.draw();
  text("Alliance (Blue/Red)", 1200, 60);

  pageSelect.draw();
    
  if (page == 1) {
    textFont(font, 32);
 
    matchNumber.draw();
    teamNumber.draw();
    teamMember.draw();
    nameSelect.draw();
    loadMatchButton.draw();

   
    tournament.draw();
    flipField.draw();
    fill(255);
    text("Match Number:", 10, 850);
    text("Tournament",130, 480);
    text("DO NOT PRESS FLIP FIELD UNLESS ABSOLUTELY NESSCESSARY", 400, 750);
    
  } else if (page == 2) {

    line(1920,0,1920,1200);
    fill(255);

    textFont(font, 32);
    text("Team Number: "+ teamNumber.input, 440, 60);
    text("Autonomous", 490, 180);
    text("Teleop", 1420, 180);
    
    if (flipField.isChecked){
      flippedField.resize(1800,650);
      image(flippedField, 50, 150);
     
    }else{
      //field.resize(1800, 650);
      image(field, 50, 150);
    }
    
    //text(cycleTimer.currTime(), 900, 850);

    timer();// draws timer. should never change.
    times.draw();
    times.start = "Time: "+m+":"+sS;
    startMatchButton.draw();

    disabled.draw();
    potf.draw();
    flippedOver.draw();
    techFoul1.draw();
    foul1.draw();
    attemptedClimb.draw();
    successfulClimb.draw();
    noShow.draw();
    yellowCard.draw();
    redCard.draw();
    
    portal.draw();
    opponentSwitch.draw();
    scale.draw();
    allianceSwitch.draw();
    opponentCubeLineZone.draw();
    cubeLineZone.draw();
    exchangeDropoff.draw();
    exchangePickup.draw();
    cubePileZone.draw();
    
    if(flipField.isChecked == true && child.alliance == 1) { // flipped scouting red
      exchangeDropoff.moveButton(1590);
      exchangePickup.moveButton(1470);
      cubePileZone.moveButton(1350);
      allianceSwitch.moveButton(1200);
      cubeLineZone.moveButton(1070);
      opponentCubeLineZone.moveButton(700);
      opponentSwitch.moveButton(550);
      portal.moveButton(75);
    } else if(flipField.isChecked == true && child.alliance == 2) {// flipped scouting blue
      exchangeDropoff.moveButton(75);
      exchangePickup.moveButton(310);
      cubePileZone.moveButton(310);
      allianceSwitch.moveButton(550);
      cubeLineZone.moveButton(700);
      opponentCubeLineZone.moveButton(1070);
      opponentSwitch.moveButton(1200);
      portal.moveButton(1590);
    } else if(flipField.isChecked == false && child.alliance == 1) {// not flipped scouting red
      exchangeDropoff.moveButton(75);
      exchangePickup.moveButton(310);
      cubePileZone.moveButton(430);
      allianceSwitch.moveButton(550);
      cubeLineZone.moveButton(700);
      opponentCubeLineZone.moveButton(1070);
      opponentSwitch.moveButton(1200);
      portal.moveButton(1590);
    } else if(flipField.isChecked == false && child.alliance == 2) {// not flipped scouting blue
      exchangeDropoff.moveButton(1590);
      exchangePickup.moveButton(1470);
      cubePileZone.moveButton(1350);
      allianceSwitch.moveButton(1200);
      cubeLineZone.moveButton(1070);
      opponentCubeLineZone.moveButton(700);
      opponentSwitch.moveButton(550);
      portal.moveButton(75);
    }
   
    droppedBox.draw();
    assistedClimb.draw();
    
    //print(printButtonPressed);
    fill(0);
    rect(0,1140,800,60);
    
     if (red == 1) {
     fill( 255, 0, 0); 
    }
    if(green == 1) {
     fill(0, 255, 127); 
    }
    if(purple == 1) {
     fill (138, 43, 226); 
    }
    
    text( printButtonPressed, 0, 1165);
    
  } if(page==3) {
    fill(255);
    text("Ending Alliance Points:", 10, 300);
    text("Ranking Points:", 10, 480);
    text("Overall Performance Rating:", 10, 670);
    text("Defense Rating:", 10, 860);
    text("Offense Rating:", 10, 1050);
    textFont(font, 32);
    text("Team Number: "+ teamNumber.input, 440, 60);

    Points.draw();
    rankingPoints.draw();
    rating.draw();
    defenseRating.draw();
    offenseRating.draw();
    end.draw();
    font = createFont("Lucida Console", 32, true);
    textFont(font, 32);
    font = createFont("Arial", 32, true);
    textFont(font, 32);

    fill(255);
    text("1           2           3             4",             350, 425);
    text("1            2           3            4           5", 560, 615);
    text("1            2           3            4           5", 350, 805);
    text("1            2           3            4           5", 350, 995);
    
  }
}

public void updateAlli() {
  if (child.scout < 4) {
    alliance.updateColor(255, 0, 0);
  }
  if (child.scout > 3) {
    alliance.updateColor(0, 0, 255);
  }
}

public void loadJSON(int MATCH) {
  values = loadJSONArray("dataIn.json");
  //println("match: " + MATCH);
  //println("scout: " + child.scout);

  int i = MATCH;
  println(MATCH);
  //i = i+child.scout;
  i--;

  JSONObject match = values.getJSONObject(i); 
  JSONObject alliances = match.getJSONObject("alliances");
  JSONObject redAlli = alliances.getJSONObject("red");
  JSONArray redAllia = redAlli.getJSONArray("team_keys");
  String[] red = redAllia.getStringArray();
  JSONObject blueAlli = alliances.getJSONObject("blue");
  JSONArray blueAllia = blueAlli.getJSONArray("team_keys");
  String[] blue = blueAllia.getStringArray();
  String TEAMNUMBER = "0000";
  
  if(child.scout == 1) {
    alli = "blue";
    TEAMNUMBER = red[0].replaceAll("[\\D]", "");
  } if(child.scout == 2) {
    alli = "blue";
    TEAMNUMBER = red[1].replaceAll("[\\D]", "");
  } if(child.scout == 3) {
    alli = "blue";
    TEAMNUMBER = red[2].replaceAll("[\\D]", "");
  } if(child.scout == 4) {
    alli = "blue";
    TEAMNUMBER = blue[0].replaceAll("[\\D]", "");
  } if(child.scout == 5) {
    alli = "blue";
    TEAMNUMBER = blue[1].replaceAll("[\\D]", "");
  } if(child.scout == 6) {
    alli = "blue";
    TEAMNUMBER = blue[2].replaceAll("[\\D]", "");
  }
   

  //String tournament = match.getString("Tournament");
  //alli = match.getString("alliance");

  //int MATCHNUM = match.getInt("Match #");
  //int TEAMNUM = match.getInt("Team #");

  //String TEAMNUMs = str(TEAMNUM);

  //println(i + ", " + alli + ", " + MATCHNUM + ", " + TEAMNUM + ", " + tournament);
  //println(alli);
  updateAlli(); 
  teamNumber.input = TEAMNUMBER;
}



public void clearEntries() {
  disabled.isChecked = false;
  disabledOutput = 0;
  potf.isChecked = false;
  POTFOutput = 0;
  flippedOver.isChecked = false;
  flippedOutput = 0;
  attemptedClimb.isChecked = false;
  attemptedOutput = 0;
  successfulClimb.isChecked = false;
  successfulOutput = 0;
  noShow.isChecked = false;
  noShowOutput = 0;
  assistedClimb.isChecked = false;
  assistedOutput = 0;
  redCard.isChecked = false;
  yellowCard.isChecked = false;
  
  defenseRating.checkedBox = 0;
  offenseRating.checkedBox = 0;
  rankingPoints.checkedBox = 0;
  rating.checkedBox = 0;
  Points.start = 0;
  portal.count =  0;
  cubePileZone.count = 0;
  opponentCubeLineZone.count = 0;
  allianceSwitch.count = 0;
  scale.count = 0;
  opponentSwitch.count = 0;
  exchangeDropoff.count = 0;
  exchangePickup.count = 0;
  cubeLineZone.count = 0; 
  
}

public void keyPressed() {
  //println(key+" "+activeBox);
  if (activeBox=="teamNumber") {
    teamNumber.update(key, keyCode);
  }
  if (activeBox=="teamMember") {
    teamMember.update(key, keyCode);
  }
}

public void timer() {
  if (run) {
    t = interval+(startTime/1000)-PApplet.parseInt(millis()/1000);
    m = t/60;
    s = t%60;
    sS = nf(s, 2);
    time = nf(t, 3);
    if (t == 0) {
      println("GAME OVER");
      run = false;
    }
  }
}

 public void saveCycleTime(int Path, float seconds, int currPathCount, boolean isAuto, boolean cycle){

  /*String outputFileName = "";
 

  if (cycle) {
    outputFileName = "cycleTimes.txt";
    println(cycleTimes.size());
    if (cycleTimes.size() == 0) {
      currentCycleTime = "[{\"Match #\":"+matchNumber.start+",\"Team #\":"+teamNumber.input+",\"P"+Path+":"+currPathCount+"\":"+seconds+",\"P"+Path+"\":"+currPathCount+",\"Auto?\":"+str(isAuto)+"}";
    } else {
      currentCycleTime = "{\"Match #\":"+matchNumber.start+",\"Team #\":"+teamNumber.input+",\"P"+Path+":"+currPathCount+"\":"+seconds+",\"P"+Path+"\":"+currPathCount+",\"Auto?\":"+str(isAuto)+"}";
    }
    
    cycleTimes.append(currentCycleTime);
    String[] cycleTimesA = cycleTimes.array();
    saveStrings("data/"+matchNumber.start+outputFileName, cycleTimesA);
    println(loadStrings("data/"+matchNumber.start+outputFileName));
  
    currentCycleTime = "";
    */
    
    if(disabled.isChecked == true){disabledOutput=1;}else{disabledOutput=0;}
    if(potf.isChecked == true){POTFOutput=1;}else{POTFOutput=0;}
    if(flippedOver.isChecked == true){flippedOutput=1;}else{flippedOutput=0;}
    if(attemptedClimb.isChecked == true){attemptedOutput=1;}else{attemptedOutput=0;}
    if(successfulClimb.isChecked == true){successfulOutput=1;}else{successfulOutput=0;}
    if(assistedClimb.isChecked == true){assistedOutput=1;}else{assistedOutput=0;}
    if(noShow.isChecked == true){noShowOutput=1;}else{noShowOutput=0;}
    if(redCard.isChecked == true){
    
   {
 int id = (matchNumber.start-1)*6;
   id = id+child.scout;
   String outputFileName = "";
    String[] matchData = {};
    outputFileName = "matchData.txt";
    println(currentMatchData);
    currentMatchData = "";
    currentMatchData = currentMatchData + "[{\"FactId\":" + id;
    currentMatchData = currentMatchData + ",\"Match #\":" + matchNumber.start;
    currentMatchData = currentMatchData + ",\"Team #\":" + teamNumber.input;
    currentMatchData = currentMatchData + ",\"Scout Name\":\"" + teamMember.input+"\"";
    currentMatchData = currentMatchData + ",\"Scout Number\":" + child.scout;
    
    currentMatchData = currentMatchData + ",\"Disabled\":" + disabledOutput;
    currentMatchData = currentMatchData + ",\"POTF\":" + POTFOutput;
    currentMatchData = currentMatchData + ",\"Flip\":" + flippedOutput;
    currentMatchData = currentMatchData + ",\"Attempted Climb\":" + attemptedOutput;
    currentMatchData = currentMatchData + ",\"Successful Climb\":" + successfulOutput;
    currentMatchData = currentMatchData + ",\"Assisted Climb\":" + assistedOutput;
    currentMatchData = currentMatchData + ",\"No Show\":" + noShow.isChecked;
    currentMatchData = currentMatchData + ",\"Tournament\":" + tournament.checkedBox;
    currentMatchData = currentMatchData + ",\"Yellow Card\":" + yellowCard.isChecked;
    currentMatchData = currentMatchData + ",\"Red Card\":" + redCard.isChecked;
    currentMatchData = currentMatchData + ",\"Tech Foul\":" + techFoul1.isChecked;
    currentMatchData = currentMatchData + ",\"Foul\":" + foul1.isChecked;
    currentMatchData = currentMatchData + ",\"Dropped Box\":" + droppedBox.count;
    
    currentMatchData = currentMatchData + "}";
    matchData = append(matchData, currentMatchData);
    saveStrings("data/"+matchNumber.start+outputFileName, matchData);
    //println(loadStrings("data/"+matchNumber.start+outputFileName));
    currentMatchData = "";}}
  
}

public void saveJSON() {
  JSONArray values1;
  if (matchNumber.start > 1) {
    values1 = loadJSONArray("dataOut.json");
  } else {
    values1 = new JSONArray();
  }

  int id = (matchNumber.start-1)*6;
  id = id+child.scout;
  id--;

  i = matchNumber.start;
  JSONObject match = new JSONObject();//(i);

  match.setInt("FactId", id);
  match.setInt("Match #", matchNumber.start);
  match.setInt("Team #", PApplet.parseInt(teamNumber.input));
  match.setInt("Portal", portal.count);
  //print(portal.count);
  //println("portal count");
  match.setInt("Cube Pile Zone", cubePileZone.count);
  match.setInt("Opponent Cube Line Zone", opponentCubeLineZone.count);
  match.setInt("Alliance Switch", allianceSwitch.count);
  match.setInt("Scale", scale.count);
  match.setInt("Opponent Switch", opponentSwitch.count);
  match.setInt("Exchange Drop Off", exchangeDropoff.count);
  match.setInt("Exchange Pick Up", exchangePickup.count);
  match.setInt("Cube Line Zone", cubeLineZone.count);
  match.setString("Scout Name", teamMember.input);
  match.setInt("Scout Number", child.scout);

  match.setBoolean("Disabled", disabled.isChecked);
  match.setBoolean("Parts on the Field", potf.isChecked);
  match.setBoolean("Flip", flippedOver.isChecked);
  match.setBoolean("Attempted Climb", attemptedClimb.isChecked);
  match.setBoolean("Successful Climb", successfulClimb.isChecked);
  match.setBoolean("No Show", noShow.isChecked);
  match.setInt("Tournament", tournament.checkedBox); 
  match.setBoolean("Yellow Card", yellowCard.isChecked);
  match.setBoolean("Red Card", redCard.isChecked);
  match.setBoolean("Tech Foul", techFoul1.isChecked);
  match.setBoolean("Foul", foul1.isChecked);
  match.setInt( "Dropped Box", droppedBox.count);
  match.setInt( "Foul",foulCounter);
  match.setInt( "Tech Foul", techFoulCounter);
  match.setBoolean("Assisted Climb", assistedClimb.isChecked);

  values1.setJSONObject(i-1, match);
  saveJSONArray(values1, "data/dataOut.json");
}
class ChildApplet extends PApplet {
  int scout = 0;
  int alliance = 0;
  int box1fill = 200;
  int box2fill = 200;
  int box3fill = 200;
  int box4fill = 200;
  int box5fill = 200;
  int box6fill = 200;
  
  public void setup() {
    surface.setTitle("Child Sketch");    
    noLoop();
  }
  
  public void settings(){
    size(600,100);

  }
  
  public ChildApplet() {
    super();
    PApplet.runSketch(new String[]{this.getClass().getName()}, this);
  }
  
  public void draw() {
    font = createFont("Arial", 32, true);
    background(255, 255, 255);
    textFont(font, 32);
    fill(box1fill);
    rect(10,10,80,80);
    fill(box2fill);
    rect(110,10,80,80);
    fill(box3fill);
    rect(210,10,80,80);
    fill(box4fill);
    rect(310,10,80,80);
    fill(box5fill);
    rect(410,10,80,80);
    fill(box6fill);
    rect(510,10,80,80);
    
    fill(0);
    text("1", 40,60);
    text("2", 140, 60);
    text("3", 240, 60);
    text("4", 340, 60);
    text("5", 440, 60);
    text("6", 540, 60);
  }
  
  public void mousePressed() {
    int x = mouseX;
    int y = mouseY;
      if (y > 10 && y < 90)   {
      if (x > 10 && x < 110)  {scoutNum = 1;scout = 1; alliance = 1; box1fill = 220; box2fill = 200; box3fill = 200; box4fill = 200; box5fill = 200; box5fill = 200;}
      if (x > 110 && x < 190) {scoutNum = 2;scout = 2; alliance = 1; box1fill = 200; box2fill = 220; box3fill = 200; box4fill = 200; box5fill = 200; box6fill = 200;}
      if (x > 210 && x < 290) {scoutNum = 3;scout = 3; alliance = 1; box1fill = 200; box2fill = 200; box3fill = 220; box4fill = 200; box5fill = 200; box6fill = 200;}
      if (x > 310 && x < 390) {scoutNum = 4;scout = 4; alliance = 2; box1fill = 200; box2fill = 200; box3fill = 200; box4fill = 220; box5fill = 200; box6fill = 200;}
      if (x > 410 && x < 490) {scoutNum = 5;scout = 5; alliance = 2; box1fill = 200; box2fill = 200; box3fill = 200; box4fill = 200; box5fill = 220; box6fill = 200;}
      if (x > 510 && x < 590) {scoutNum = 6;scout = 6; alliance = 2; box1fill = 200; box2fill = 200; box3fill = 200; box4fill = 200; box5fill = 200; box6fill = 220;}
      redraw();
    }
    
    //println("Box: " + scout + " x: " + x + " y: " + y);
    //int matchNum = 1;
    //int factId = scout+((matchNum-1)*6);
    //println(factId);
  } 
}
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
      img.resize(PApplet.parseInt(boxCs[2]),PApplet.parseInt(boxCs[3]));
      image(img,x,y);
      //println("Image loaded");
    }
  }
  
  public void mousePressed() {
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
  
  public void mousePressed() {
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
  
  public void setBox(int boxNum) {
    checkedBox = boxNum;
  }
}
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
    
    this.box1Cs[0] = PApplet.parseInt(x);
    this.box1Cs[1] = PApplet.parseInt(y + size/6);
    
    this.box1Cs[2] = PApplet.parseInt(x + size);
    this.box1Cs[3] = PApplet.parseInt(y + size*7/6);
    
    this.box2Cs[0] = PApplet.parseInt(x + size + gap);
    this.box2Cs[1] = PApplet.parseInt(y + size/6);
    
    this.box2Cs[2] = PApplet.parseInt(x + size*2 + gap);
    this.box2Cs[3] = PApplet.parseInt(y + size*7/6);
    
    this.box3Cs[0] = PApplet.parseInt(x + size*10/3 + gap*3);
    this.box3Cs[1] = PApplet.parseInt(y + size/6);
    
    this.box3Cs[2] = PApplet.parseInt(x + size*13/3 + gap*3);
    this.box3Cs[3] = PApplet.parseInt(y + size*7/6);
    
    this.box4Cs[0] = PApplet.parseInt(x + size*13/3 + gap*4);
    this.box4Cs[1] = PApplet.parseInt(y + size/6);
    
    this.box4Cs[2] = PApplet.parseInt(x + size*16/3 + gap*4);
    this.box4Cs[3] = PApplet.parseInt(y + size*7/6);
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
  
  public void mousePressed() {
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
  public void outputToFile() {
    
  }
  public void closeFile() {
    println("Output File Closed");
  }  
}
public class textBox {
 // Fields
 int bgR, bgG, bgB;
 float x,y,w,h;
 int Tr, Tg, Tb;
 String start;
 String input = "";
 boolean activated;
 float[] boxCs = {0,0,0,0};
 //private static final int kENTER = 10;
 private static final int kBACKSPACE = 8;
 boolean keyboard;
 boolean colorToggle;
 boolean drawn = false;
 
 
 // Constructors
 public textBox (float x, float y, float w, float h, int bgR, int bgG, int bgB, int Tr, int Tg, int Tb, String start, boolean activated, boolean keyboard, boolean colorToggle) {
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
  this.start = start;
  this.activated = activated;
  this.boxCs[0] = x;
  this.boxCs[1] = y;
  this.boxCs[2] = w;
  this.boxCs[3] = h;
  this.keyboard = keyboard;
  this.colorToggle = colorToggle;
  }
  
  // Methods
  public void update(char k,int kc) {
    int inpLen = 0;
    inpLen = input.length();
    //println(kc);
    if(kc==kBACKSPACE) {
     if(inpLen > 0) {
       input = input.substring(0, inpLen-1);
     }
    } else {input = input + k;}
    //println(inpLen);
    if(inpLen==35 && kc != kBACKSPACE) { 
     input = input + "\n";
    }
    if(inpLen==72 && kc != kBACKSPACE) { 
     input = input + "\n";
    }
    if(inpLen==109 && kc != kBACKSPACE) { 
     input = input + "\n";
    }
    if(inpLen==146 && kc != kBACKSPACE) { 
     input = input + "\n";
    }
    if(inpLen==183 && kc != kBACKSPACE) { 
     input = input + "\n";
    }
    if(inpLen==220 && kc != kBACKSPACE) { 
     input = input + "\n";
    }
    input = String.valueOf(input);
    //println(input);
    //writeText(Input);
  }
 
  public void updateColor(int bgR, int bgG, int bgB) {
    this.bgR = bgR;
    this.bgG = bgG;
    this.bgB = bgB;
  }
  
  public void draw() {
    fill(bgR, bgG, bgB);
    rect(x,y,w,h);
    fill(Tr,Tg,Tb);
    text(start+input, x+10, y+40);
    drawn = true;
  }
  
  public void mousePressed() {
    
    int mX=mouseX;
    int mY=mouseY;
    if (drawn) {
      if(mX>this.boxCs[0] && mY>this.boxCs[1] && mX<this.boxCs[0]+this.boxCs[2] && mY<this.boxCs[1]+this.boxCs[3]) {
        activated = true;
        updateColor(220, 220, 220);
        //println(keyboard);
        if (keyboard) {
          launch("C:/Program Files/Common Files/microsoft shared/ink/tabtip.exe");
        }
      } else {
      activated = false; if (colorToggle ==false) {
      updateColor(200, 200, 200);
        }
      }
    }
  }
}
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
 public textButton (int x, int y, int w, int h, int bgR, int bgG, int bgB, int Tr, int Tg, int Tb, String text,boolean activated) {
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
  
  public void mousePressed() {
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
public class textButtonGroup {
  // Fields
  int bgR, bgG, bgB;
  float x, y;
  ArrayList<ArrayList<textButton>> buttons = new ArrayList<ArrayList<textButton>>();
  int numButtonsX;
  int numButtonsY;
  int checkedBox=-1;
  int activatedButton;
  int currY;
  String[][] names = {
    {"Chris", "Nick", "Ryan", "Rubaiyet", "Jesse"}, 
    {"Michael", "Thomas", "Daniel", "Leo", "Luke"}, 
    {"Ethan", "Dakota", "Aditya", "Brayden", "Brianna"}, 
    {"Josh", "Ben", "Daven", "Madison", "Maya"}, 
    {"Keenan", "Melanie", "Yash", "Shean", "Other/Mentor"}
  };

  // Constructors
  public textButtonGroup (int x, int y, int bgR, int bgG, int bgB, int numButtonsX, int numButtonsY, int activatedButton) {
    this.x = x;
    this.y = y;
    this.bgR = bgR;
    this.bgG = bgG;
    this.bgB = bgB;
    this.numButtonsX = numButtonsX;
    this.numButtonsY = numButtonsY;
    this.activatedButton = activatedButton;
    boolean activated = false;
    textButton thisButton;
    x = x+250;
    currY = y;
    for (int l = 0; l<numButtonsY; l++) {
      ArrayList<textButton> sub = new ArrayList<textButton>();
      for (int i = 0; i<numButtonsX; i++) {

        //println(i);
        if (i == activatedButton) {
          activated = true;
        } else {
          activated = false;
        }

        // Need to set the x,y value of each checkBox
        //println(names[l][i]);

        thisButton = new textButton(x+(i-1)*250, currY, 200, 80, 200, 200, 200, 0, 0, 0, names[i][l], activated);
        //println("Adding 1 checkbox");
        sub.add(thisButton);
      }
      buttons.add(sub);
      currY = currY+100;
      i = 0;
      //println("starting a new line of boxes. Y value is now: "+currY);
    }
  }

  //methods
  public void draw() {
    textButton thisButton;
    for (int l = 0; l<this.numButtonsY; l++) {
      for (int i = 0; i<this.numButtonsX; i++) {
        thisButton = buttons.get(i).get(l);
        thisButton.draw();
        //println("drawing a box");
      }
      i=0;
    }
  }

  public void mousePressed() {
    textButton thisButton;
    for (int l = 0; l<this.numButtonsY; l++) {
      for (int i = 0; i<this.numButtonsX; i++) {
        //println(i);
        thisButton = buttons.get(l).get(i);
        thisButton.mousePressed();
        if (thisButton.activated == true) {
          teamMember.input = names[i][l];
        }
      }
    }
  }
}
public class timer {
 // Fields
 float startTime=0;
 boolean timing;
 // Constructors
 public timer () {
  timing = false;
  startTime = 0;
  }
  
  // Methods
  // set the starting time to millis()
  public void startTimer() {
    startTime = millis();
    //println("Started timer");
    timing = true;
  }
  
  //return final time in seconds as a float:
  public float endTimer() {
    float endTime = (millis()-startTime)/1000;
    //println("EndTime: "+endTime+" ");
    timing = false;
    startTime = 0;
    return endTime;
    //reset automatically
  }
  
  //return current time
  public float currTime() {
    if (timing) {
      return (millis()-startTime)/1000;
    } else {
      return 0;
    }   
  }
}
public class timerEndingTextButton {
 // Fields
 int bgR, bgG, bgB;
 float x,y,w,h;
 int Tr, Tg, Tb, count;
 String text;
 boolean activated;
 float[] boxCs = {0,0,0,0};
 float FinalTime;
 //private static final int kENTER = 10;
 //private static final int kBACKSPACE = 8;
 
 // Constructors
 public timerEndingTextButton (int x, int y, int w, int h, int bgR, int bgG, int bgB, int Tr, int Tg, int Tb, String text,boolean activated) {
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
  public void mousePressed() {
  int mX=mouseX;
  int mY=mouseY;

  if(mX>this.boxCs[0] && mY>this.boxCs[1] && mX<this.boxCs[0]+this.boxCs[2] && mY<this.boxCs[1]+this.boxCs[3]) {
    activated = true;
    updateColor(220, 220, 220);
    count ++;
    //FinalTime = cycleTimer.endTimer();
    } else {
    activated = false; updateColor(200, 200, 200);
    }
  }
}
public class transparentButton {
 // Fields
 int bgR, bgG, bgB;
 int count;
 float x,y,w,h;
 int Tr, Tg, Tb;
 String text;
 boolean activated;
 float[] boxCs = {0,0,0,0};
 float FinalTime;
 boolean isPickUp;
 
 //private static final int kENTER = 10;
 //private static final int kBACKSPACE = 8;
 
 // Constructors
 public transparentButton (int x, int y, int w, int h, boolean isPickUp) {
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
    rect(x,y,w,h);
    //println(x+" "+y);
  }
  
  public void mousePressed() {
    int mX=mouseX;
    int mY=mouseY;
  
    if(mX>this.boxCs[0] && mY>this.boxCs[1] && mX<this.boxCs[0]+this.boxCs[2] && mY<this.boxCs[1]+this.boxCs[3]) {
      activated = true;
      count ++;
      if(isPickUp) {
        //cycleTimer.startTimer();
      } else {
        //FinalTime = cycleTimer.endTimer();
      }      
      } else {
      activated = false;
      }
  
  
  } // end of mousePressed
  
  
  public void moveButton(int newX){
    x = newX;
    redraw();
    boxCs[0]=x;
    
  }//end of moveButton 
} //end of class

 
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "matchScouting2k18_1_9_CCE" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

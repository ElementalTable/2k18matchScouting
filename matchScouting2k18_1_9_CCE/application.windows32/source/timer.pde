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
  void startTimer() {
    startTime = millis();
    //println("Started timer");
    timing = true;
  }
  
  //return final time in seconds as a float:
  float endTimer() {
    float endTime = (millis()-startTime)/1000;
    //println("EndTime: "+endTime+" ");
    timing = false;
    startTime = 0;
    return endTime;
    //reset automatically
  }
  
  //return current time
  float currTime() {
    if (timing) {
      return (millis()-startTime)/1000;
    } else {
      return 0;
    }   
  }
}

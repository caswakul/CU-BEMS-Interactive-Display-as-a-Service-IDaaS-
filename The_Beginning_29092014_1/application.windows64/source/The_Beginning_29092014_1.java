import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import SimpleOpenNI.*; 
import java.net.*; 
import java.io.*; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class The_Beginning_29092014_1 extends PApplet {

//import ddf.minim.*;





//Minim minim_canvas1, minim_canvas2, minim_welcome, minim_goodbye;
//Minim swipe; 

//AudioPlayer gameBackground, swipeSound, welcome;
SimpleOpenNI kinect;

//--------------------------- PARAMETER INITAILIZATION KINECT & CLOCK -----------------------//
int sec, min, hour, day, weekNumber, month, year; 
PFont clock_font, calendar_font, fetch_font, fetch_value_font;

PVector rightHand = new PVector(); 
PVector convertedrightHand = new PVector();
float preecha_previousMouseX, preecha_previousMouseY, trigger_time;
float hand_x_position, hand_y_position, xMapped, yMapped;
int   [] userList;
float [] userLastTimeSeen = {
  0, 0, 0, 0, 0, 0
} 
;
int controlUserId = 1;

String[] outList_of_foundImageFiles ;

int menuHeight_margin = 150;
int userCount     = 0;
int canvas_number = 0;    // STart at CanvasNumber 0
int canvas_number_prev;
//int counter_canvas = 0;
PImage canvas_Selection1, canvas_Selection1over, canvas_Selection2, canvas_Selection2over, canvas_Selection3, canvas_Selection3over, canvas_Selection4, canvas_Selection4over;
PImage preecha_bgCanvas1, background_Screensaver, canvas2_background, hand_black, hand_white;

// ---------------------------------------- canvasMenu -------------------------------------------------
int alreadyRunInitialisationCodeInCurrentCanvas;
int preecha_timeovercanvas_selected = 2000;      // set time at 2 second
boolean overPicture1, overPicture2, overPicture3, overPicture4, overClock = false;
int preecha_resetSelect_icon;

// ---------------------------   CANVAS 0 (PHOTO COLLECTIONS >>>>> SCREEN_SAVER) ------------------------
int preecha_resetIntervakcheck_canvas0;
ArrayList<PImage>preecha_allImages_canvas0 ;
String preecha_pictureFolder_canvas0 = "/Users/CU-EE/DropBox/CU_EE_fl12/Image/";

// --------------------------------   CANVAS 1 (PICTURE TRANSLATION) ------------------------------------
ArrayList<PImage> preecha_allImages;
int    preecha_showPic = 0;         // Basis Picture
int    preecha_showPicLeft, preecha_showPicRight;
int    currentImage = 0;
float  preecha_imageShift_xpos = 0; 
float  preecha_imageShift_ypos = 0;
float  preecha_imageShift_Target = 0;
float  preecha_easingRatio = 0.35f;    // speed to change to next Picture
String preecha_pictureFolder_canvas1 = "/Users/CU-EE/Desktop/The_Beginning_29092014_1/healthpad_eeinformation/";

//---------------------------  CANVAS 2  (ENERGY  GAME)  --------------------------------------//
String fetchGame_res = "";
String [] fetchGame_value;
float  [] ValueGame;
String [] PointGame;
int    [] located;

String [] pointID_Game = {
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/department\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/ee/f2\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/ee/f3\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/ee/f4\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/ee/f5\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/eng4/f12\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/eng4/f13\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/energy_consumption/highvoltage\" attrName=\"time\" select=\"maximum\" />"
};

String [] Building_name = {
  "EE department", 
  "EE Building fl2", 
  "EE Building fl3", 
  "EE Building fl4", 
  "EE Building fl5", 
  "Building4 fl12", 
  "Building4 fl13", 
  "High Voltage"
};

PImage [] icon_Image = {
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eedepartment.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eebulidingfl2.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eebulidingfl3.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eebulidingfl4.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eebulidingfl5.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eng4_12.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/eng4_13.jpg"), 
  loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/gameFolder/HVbuilding.jpg")
  };

  int cak_numrows = 3; //cak//
int cak_basis = 1; //cak2//
int[][] b = new int[cak_numrows][cak_numrows];
int pad = PApplet.parseInt(70), bs = PApplet.parseInt(230), len = pad*(b.length+1)+bs*b.length, score = 0, dead = 1;
String heighest_score [] ;
int savedScore;

int max_t = 1; // keeps the max value of score t achieved so far in already-displayed boards
int cak_rightMarginWidth = abs(len-width);

int icon_size  = 100;
int cak_x_icon =   1100;
int cak_y_icon1   = len ; 
int cak_y_icon2   = cak_y_icon1 - (icon_size+10);
int cak_y_icon4   = cak_y_icon2 - (icon_size+10);
int cak_y_icon8   = cak_y_icon4 - (icon_size+10);
int cak_y_icon16  = cak_y_icon8 - (icon_size+10); 
int cak_y_icon32  = cak_y_icon16 - (icon_size+10);
int cak_y_icon64  = cak_y_icon32 - (icon_size+10);
int cak_y_icon128 = cak_y_icon64 - (icon_size+10);

PImage icon1_image, icon2_image, icon4_image, icon8_image, icon16_image, icon32_image, icon64_image, icon128_image;

// --------------------------------   CANVAS 3 (EE Information) ------------------------------------

ArrayList<PImage> preecha_allImages_canvas3 ;
int    preecha_showPic_canvas3 = 0;
int    preecha_showPicLeft_canvas3, preecha_showPicRight_canvas3;
int    currentImage_canvas3 = 0;
float  preecha_imageShift_xpos_canvas3 = 0; 
float  preecha_imageShift_ypos_canvas3 = 0;
float  preecha_imageShift_Target_canvas3 = 0;
float  preecha_easingRatio_canvas3 = 0.35f;    // speed to change to next Picture
String preecha_pictureFolder_canvas3 = "/Users/CU-EE/Dropbox/CU_EE_fl12/EE_information/";

//--------------------------------   CANVAS  4 (Alarm & Alert)  -------------------------------------
PFont alarm, textwarn, textBuilding;

int zone_index = 5;

int percent;
int [] target;
int saturday, sunday;
float radiusOfEnergyConsumed;
float radius, fontBD, percentFontRatio;
  String [] tarketQuata  = loadStrings("/Users/CU-EE/Dropbox/tarketQouta.csv");

String [] Building_name_alarm = {
  "\"Electical Engineering Department\"", 
  "\"EE Building floor 2\"", 
  "\"EE Building floor 3\"", 
  "\"EE Building floor 4\"", 
  "\"EE Building floor 5\"", 
  "\"Building 4 floor 12\"", 
  "\"Building 4 floor 13\"", 
  "\"High Voltage Building\""
};


// ---------------------------------------- Back-end -------------------------------------------------
String fetch_res = "";
String [] fetch_value;
ArrayList<String>  PointID;
String [] timeStamp_cv = {
};
String [] canvas_num = {
};
String [] cummSwipe = {
};

ArrayList<Float> Value;
int noSwipe, noUser = 0;
String write_sec, write_min, write_hour, write_day, write_month, write_year; 
String [] pointID = {
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/co2_reduction\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/coal_reduction\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/consumption\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/electricity_saved\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/energy_saved\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/load_factor\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/peak_saved\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/renewable_energy\" attrName=\"time\" select=\"maximum\" />", 
  "<key id=\"bems.ee.eng.chula.ac.th/ee_health_pad/tree_saved\" attrName=\"time\" select=\"maximum\" />"
};

String[] target_healthpad = {
  "10", "594.4", "0.8", "10,000", "1000", "99", "50", "n/a", "50"
};
String[] target_healthpad_unit = {
  "Tons CO2", "kg", "kWh/m^2", "baht", "kWh", "%", "kW", "", "trees"
};



public void setup() {
  size(displayWidth, displayHeight);

  // user Trackig SET UP //  
  kinect = new SimpleOpenNI(this);
  kinect.enableDepth();
  kinect.enableRGB();
  kinect.enableUser();

  // for loading music background in canvas 2
//  minim_canvas2   = new Minim(this); 
//  gameBackground = minim_canvas2.loadFile("bgm.mp3", 2048);

  clock_font       = loadFont("CordiaUPC-Bold-200.vlw");
  fetch_font       = loadFont("Calibri-Light-200.vlw");
  fetch_value_font = loadFont("Calibri-Bold-200.vlw");
  calendar_font    = loadFont("Microsoft-Yi-Baiti-48.vlw");
  alarm            = loadFont("ArialRoundedMTBold-200.vlw");
  textwarn         = loadFont("CordiaUPC-100.vlw");
  textBuilding     = loadFont("CordiaUPC-Bold-150.vlw");


  canvas_Selection1     = loadImage("/center/HealthPad.png");
  canvas_Selection1over = loadImage("/center/HealthPad_click.png");
  canvas_Selection2     = loadImage("/center/EnergyGame1.png");
  canvas_Selection2over = loadImage("/center/EnergyGame.png");
  canvas_Selection3     = loadImage("/center/EEinf.png"); 
  canvas_Selection3over = loadImage("/center/EEinf_click.png");
  canvas_Selection4     = loadImage("/center/Alarm.png"); 
  canvas_Selection4over = loadImage("/center/Alarm_click.png");

  hand_black = loadImage("/center/hand_black.png");   
  hand_black.resize(100, 100);
  hand_white  = loadImage("/center/hand_white.png");   
  hand_white.resize(100, 100);

  canvas2_background = loadImage("/Users/CU-EE/Desktop/The_Beginning_29092014_1/canvas2/canvas2_background.jpg"); 
  canvas2_background.resize(width, height-menuHeight_margin);
  ieee1888_fetch();
  ieee1888_fetch_Game();
  background_Screensaver =loadImage("background_canvas0.jpg"); 
  image(background_Screensaver, 0, 0);
  noCursor();
}

public void draw() {

  /////////////////////////////////////////////////////
  // keep tracking and check user status from kinect 
  //

  trackingUser();
  hand_x_position = detect_hand_x_position();   
  hand_y_position = detect_hand_y_position();


  //////////////////////////////////////////////
  // check if canvas number has been changed
  //

  if (canvas_number_prev != canvas_number) {  

    // write canvas_number_prev, canvas_number, noSwipe, userList.length, time
    ieee1888_write();


    // reset all per-canvas counters //
    noSwipe = 0;
    alreadyRunInitialisationCodeInCurrentCanvas = 0;

    // stop playing background sound of previous canvas //
//    gameBackground.close();
//    gameBackground = minim_canvas2.loadFile("bgm.mp3", 2048);

    //    welcome.close();
    //    minim_welcome.stop();
  }

  canvas_number_prev = canvas_number;

  // no users detected so reset our screen to canvas number 0 and reset swipe counter //
  if (userCount == 0) {
    canvas_number = 0;
    zone_index = 5;
  }

  // fresh user has just entered so switch from canvas numbers 0 to 1 //  
  if ( (userCount > 0) && (canvas_number_prev == 0) ) {    
    //    counter_canvas = counter_canvas+1;
    //    if(counter_canvas >4){
    //      counter_canvas =1;
    //    }
    //    canvas_number = counter_canvas;
    canvas_number = 4;
    noUser +=1;

    // load and play sound to welcome user who is in front of the kinect
    //    minim_welcome = new Minim(this); 
    //    welcome = minim_welcome.loadFile("welcome.wav", 2048);
    //    welcome.play();

    // fetch data from data storage to use in each canvas 
    ieee1888_fetch();
    ieee1888_fetch_Game();
  }


  if (canvas_number == 0) {
    canVas0();
  } 

  if (canvas_number == 1) {
    canVas_Menu();
    canVas1();
    checkMouseCanVas1(PApplet.parseInt(hand_x_position), PApplet.parseInt(hand_y_position));
  } 

  if (canvas_number == 2) {
    canVas_Menu();
    canVas2();
    checkMouseCanVas2(PApplet.parseInt(hand_x_position), PApplet.parseInt(hand_y_position));
  }

  if (canvas_number == 3) {
    canVas_Menu();
    canVas3();
    checkMouseCanVas3(PApplet.parseInt(hand_x_position), PApplet.parseInt(hand_y_position));
  }

  if (canvas_number == 4) {
    canVas_Menu();
    canVas4();
    checkMouseCanVas4(PApplet.parseInt(hand_x_position), PApplet.parseInt(hand_y_position));
  }

  println("no of swipe" +noSwipe);
  println("NUmber of user" + userCount);
}

//----------------------------------------------------------------------------------------------------------
//-------------------------------          IEEE 1888 FETCH           ---------------------------------------
//----------------------------------------------------------------------------------------------------------

public void ieee1888_fetch() {
  try {
    String xmldata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
      "<soapenv:Envelope " + 
      "xmlns:soapenv="+
      "\"http://schemas.xmlsoap.org/soap/envelope/\">"+
      "<soapenv:Body>"+
      "<ns2:queryRQ xmlns:ns2=\"http://soap.fiap.org/\">"+
      "<transport xmlns=\"http://gutp.jp/fiap/2009/11/\">"+
      "<header>"+
      "<query id=\"9eed9de4-1c48-4b08-a41d-dac067fc1c0d\" type=\"storage\">"+        
      pointID[0]+pointID[1]+pointID[2]+pointID[3]+pointID[4]+
      pointID[5]+pointID[6]+pointID[7]+pointID[8]+
      "</query></header></transport></ns2:queryRQ>"+
      "</soapenv:Body></soapenv:Envelope>";

    //Create socket
    String hostname = "161.200.90.122";
    int port = 80;
    InetAddress  addr = InetAddress.getByName(hostname);
    Socket sock = new Socket(addr, port);

    //Send header
    String path = "/axis2/services/FIAPStorage";
    BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));

    // You can use "UTF8" for compatibility with the Microsoft virtual machine.
    wr.write("POST " + path + " HTTP/1.0\r\n");
    wr.write("Host: 161.200.90.122\r\n");
    wr.write("Content-Length: " + xmldata.length() + "\r\n");
    wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
    wr.write("SOAPAction: http://soap.fiap.org/query\r\n"); 
    wr.write("\r\n");

    //Send data
    wr.write(xmldata);
    wr.flush();

    // Response
    BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    String line;
    while ( (line = rd.readLine ()) != null) {
      fetch_res = fetch_res+line;
    }
  } 

  catch (Exception e) {
    e.printStackTrace();
  }

  String [] fetch_body = split(fetch_res, "<body>");
  String [] fetch_pointID = split(fetch_body[1], "</value></point>");
  Value = new ArrayList<Float>();
  PointID = new ArrayList<String>();
  for (int i = 0; i < fetch_pointID.length-1; i++) {
    String [] fetch_value = split(fetch_pointID[i], "+07:00\">");

    PointID.add(fetch_value[0]);
    Value.add(PApplet.parseFloat(fetch_value[1]));

  }
}

//----------------------------------------------------------------------------------------------------------
//---------------------------    IEEE 1888 FETCH FOR ENERGY GAME && ALARM   --------------------------------
//----------------------------------------------------------------------------------------------------------
public void ieee1888_fetch_Game() {

  try {
    String xmldata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
      "<soapenv:Envelope " + 
      "xmlns:soapenv="+
      "\"http://schemas.xmlsoap.org/soap/envelope/\">"+
      "<soapenv:Body>"+
      "<ns2:queryRQ xmlns:ns2=\"http://soap.fiap.org/\">"+
      "<transport xmlns=\"http://gutp.jp/fiap/2009/11/\">"+
      "<header>"+
      "<query id=\"9eed9de4-1c48-4b08-a41d-dac067fc1c0d\" type=\"storage\">"+        
      pointID_Game[0]+pointID_Game[1]+pointID_Game[2]+pointID_Game[3]+pointID_Game[4]+pointID_Game[5]+pointID_Game[6]+
      pointID_Game[7]+
      "</query></header></transport></ns2:queryRQ>"+
      "</soapenv:Body></soapenv:Envelope>";

    //Create socket
    String hostname = "161.200.90.122";
    int port = 80;
    InetAddress  addr = InetAddress.getByName(hostname);
    Socket sock = new Socket(addr, port);

    //Send header
    String path = "/axis2/services/FIAPStorage";
    BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));

    // You can use "UTF8" for compatibility with the Microsoft virtual machine.
    wr.write("POST " + path + " HTTP/1.0\r\n");
    wr.write("Host: 161.200.90.122\r\n");
    wr.write("Content-Length: " + xmldata.length() + "\r\n");
    wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
    wr.write("SOAPAction: http://soap.fiap.org/query\r\n"); 
    wr.write("\r\n");

    //Send data
    wr.write(xmldata);
    wr.flush();

    // Response
    BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    String line;
    while ( (line = rd.readLine ()) != null) {
      fetchGame_res = fetchGame_res+line;
    }
  } 

  catch (Exception e) {
    e.printStackTrace();
  }

  String [] fetch_body = split(fetchGame_res, "<body>");
  String [] fetch_pointID = split(fetch_body[1], "</value></point>");

  ValueGame = new float[pointID_Game.length];
  PointGame = new String[pointID_Game.length];


  for (int i = 0; i < fetch_pointID.length-1; i++) {
    String [] fetchGame_value = split(fetch_pointID[i], "+07:00\">"); 

    ValueGame[i] = PApplet.parseFloat(fetchGame_value[1]);
    PointGame[i] = fetchGame_value[0];
  }

  float[] copyValueGame = new float[ValueGame.length];
  arrayCopy(ValueGame, copyValueGame);

  located = new int[copyValueGame.length];
  for (int i = 0; i < copyValueGame.length; i++) {
    float check_Max = max(copyValueGame);
    int minIndex = 0;
    for (int j = 0; j < copyValueGame.length; j++) {
      if (check_Max > copyValueGame[j]) {
        minIndex = j;
        check_Max = copyValueGame[j];
      }
    }
    copyValueGame[minIndex] = max(copyValueGame);
    located[i] = minIndex;
  }
}


//----------------------------------------------------------------------------------------------------------
//-------------------------------          IEEE 1888 WRITE           ---------------------------------------
//----------------------------------------------------------------------------------------------------------

public void ieee1888_write() {

  write_year  = ""+year();   // 2003, 2004, 2005, etc.  
  if (month() < 10) {
    write_month = "0"+month();
  } else {
    write_month = ""+month();
  }
  if (day() < 10) {
    write_day = "0"+day();
  } else {
    write_day = ""+day();
  }

  if (hour() < 10) {
    write_hour = "0"+hour();
  } else {
    write_hour = ""+hour();
  }  
  if (minute() < 10) {
    write_min = "0"+minute();
  } else {
    write_min = ""+minute();
  }
  if (second() < 10) {
    write_sec = "0"+second();
  } else {
    write_sec = ""+second();
  }

  try {
    String xmldata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
      "<soapenv:Envelope " + 
      "xmlns:soapenv="+
      "\"http://schemas.xmlsoap.org/soap/envelope/\">"+
      "<soapenv:Body>"+
      "<ns2:dataRQ xmlns:ns2=\"http://soap.fiap.org/\">"+
      "<transport xmlns=\"http://gutp.jp/fiap/2009/11/\">"+
      "<body>"+
      "<pointSet id=\"http://bems.ee.eng.chula.ac.th/eng4/fl12/corridor/elevatorfront/kinect\">"+
      "<point id=\"http://bems.ee.eng.chula.ac.th/eng4/fl12/corridor/elevatorfront/kinect/num_user\">"+
      "<value time=\""+write_year+"-"+write_month+"-"+write_day+"T"+write_hour+":"+write_min+":"+write_sec+".000+07:00\">"+noUser+"</value>"+
      "</point>"+
     "<point id=\"http://bems.ee.eng.chula.ac.th/eng4/fl12/corridor/elevatorfront/kinect/canvas_num\">"+
     "<value time=\""+write_year+"-"+write_month+"-"+write_day+"T"+write_hour+":"+write_min+":"+write_sec+".000+07:00\">"+"canvas"+canvas_number+"</value>"+
      "</point>"+  
     "<point id=\"http://bems.ee.eng.chula.ac.th/eng4/fl12/corridor/elevatorfront/kinect/num_swipe\">"+
     "<value time=\""+write_year+"-"+write_month+"-"+write_day+"T"+write_hour+":"+write_min+":"+write_sec+".000+07:00\">"+"swipe"+noSwipe+"</value>"+
      "</point>"+        
      "</pointSet>"+"</body>"+
      "</transport></ns2:dataRQ>"+
      "</soapenv:Body></soapenv:Envelope>";

    //Create socket
    String hostname = "161.200.90.122";
    int port = 80;
    InetAddress  addr = InetAddress.getByName(hostname);
    Socket sock = new Socket(addr, port);

    //Send header
    String path = "/axis2/services/FIAPStorage";
    BufferedWriter  wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));

    // You can use "UTF8" for compatibility with the Microsoft virtual machine.
    wr.write("POST " + path + " HTTP/1.0\r\n");
    wr.write("Host: 161.200.90.122\r\n");
    wr.write("Content-Length: " + xmldata.length() + "\r\n");
    wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
    wr.write("SOAPAction: http://soap.fiap.org/data\r\n"); 
    wr.write("\r\n");

    //Send data
    wr.write(xmldata);
    wr.flush();

    // Response
    BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    String line;
    while ( (line = rd.readLine ()) != null) {
    }
  } 

  catch (Exception e) {
    e.printStackTrace();
  }
}






public void canVas0() {

  if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

    // run the following canvas-initialisation code only //
    // once per each time of entering this canvas        //
    canvas0_init(); 
    alreadyRunInitialisationCodeInCurrentCanvas = 1;
  }

  try {
    int preecha_passTimecanvas0 = millis()- preecha_resetIntervakcheck_canvas0 ;  
    if (preecha_passTimecanvas0 > 3000 ) {

      float imageRotation = random(-50, 50);
      float imageX = random(250, width-250); 
      float imageY = random(200, height-200);
      // Draw, Rotate + Scale Image
      imageMode(CENTER);
      pushMatrix();
      translate(imageX, imageY);
      rotate(radians(imageRotation));
      filter(BLUR, .8f);
      filter(GRAY);
      filter(INVERT);
      currentImage +=1;
      image(preecha_allImages_canvas0.get(currentImage%preecha_allImages_canvas0.size()), 0, 0);
      popMatrix();
      preecha_resetIntervakcheck_canvas0 = millis();
    }
  }
  catch (Exception e){
    preecha_allImages_canvas0 = new ArrayList<PImage>(); 
    preecha_checkImgfiles(preecha_pictureFolder_canvas0);
  }
}

public void canvas0_init() {   
  translate(0, 0);
  imageMode(CORNER);
  image(background_Screensaver, 0, 0);
}

public void canVas1() {

try{
    pushMatrix();
    translate(preecha_imageShift_xpos, preecha_imageShift_ypos+150);  // 150 is the header of application
    if (preecha_showPic == 0) {
      preecha_showPicLeft = (preecha_allImages.size()) - 1;
    } 
    else {
      preecha_showPicLeft = preecha_showPic - 1;
    }

    if (preecha_showPic == (preecha_allImages.size()) - 1 ) {
      preecha_showPicRight = 0;
    } 
    else {
      preecha_showPicRight = preecha_showPic + 1;
    }

    PImage picLeft = preecha_allImages.get(preecha_showPicLeft);
    PImage pic = preecha_allImages.get(preecha_showPic);
    PImage picRight = preecha_allImages.get(preecha_showPicRight);

    image (picLeft, -width, 0);
    image (pic, 0, 0);
    image (picRight, width, 0);
    fill(255);
    textFont(fetch_font, 90);
    textAlign(LEFT);
    text("TARGET", (width/2)+100, 150);
    text("CURRENT", (width/2)+100, 500);
    textFont(fetch_value_font, 120);
    textAlign(LEFT);
    text(target_healthpad[preecha_showPic]+" "+target_healthpad_unit[preecha_showPic], (width/2)+100, 300);    
    text(String.format("%.1f", Value.get(preecha_showPic))+" "+target_healthpad_unit[preecha_showPic], (width/2)+100, 650);
  }
  catch (Exception e) {
    preecha_allImages = new ArrayList<PImage>(); 
    preecha_checkImgfiles(preecha_pictureFolder_canvas1);
  }
  preecha_imageShift_xpos += (preecha_imageShift_Target - preecha_imageShift_xpos)*preecha_easingRatio;
  popMatrix();
}


public void checkMouseCanVas1(int hand_x_position, int hand_y_position) {
  mouseX = hand_x_position ;
  mouseY = hand_y_position ;

  preecha_previousMouseX = lerp(preecha_previousMouseX, mouseX, 0.09f);

  float current_time = millis();
  int dx;

  if (current_time - trigger_time > 2500 ) {
    image(hand_black, mouseX, mouseY);


    dx=(preecha_previousMouseX-mouseX)>=150 ? -1 : ((preecha_previousMouseX-mouseX)<=-150 ? 1 : 0);

    if (dx == -1) {
      noSwipe +=1;
      preecha_showPic = (preecha_showPic+1)%(preecha_allImages.size());
      preecha_imageShift_xpos = width;
    }
    if (dx == 1) {
      noSwipe +=1;
      if (preecha_showPic == 0) {
        preecha_showPic = (preecha_allImages.size())-1;
      }
      else {
        preecha_showPic = (preecha_showPic-1);
      }
      preecha_imageShift_xpos = -width;
    }
    if (dx != 0) {
      trigger_time = millis();
    }
  } 
  else {
    image(hand_white, mouseX, mouseY);
    dx = 0;
  }
}

public void canvas1_init(){  
}
public void canVas2() {
  
    if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

    // run the following canvas-initialisation code only //
    // once per each time of entering this canvas        //
    alreadyRunInitialisationCodeInCurrentCanvas = 1;
  }
  noStroke();
  String heighest_score [] = loadStrings("score.txt");
  int savedScore = PApplet.parseInt(heighest_score[0]);  //  image(happyness_spinner_image, len-10, 75 );

  cak_icondisp_background(max_t); //cak3: displaying board up to max_t score//

  for (int j = 0; j < b.length; j++) 
    for (int i = 0; i < b[j].length; i++) {
      fill(200);
      rect((pad+(pad+bs)*i)+pad, (pad+(pad+bs)*j)+menuHeight_margin-20, bs, bs, 30);
    }
  for (int j = 0; j < b.length; j++) 
    for (int i = 0; i < b[j].length; i++) {
      float x = (pad+(pad+bs)*i)+pad, y=(pad+(pad+bs)*j)+menuHeight_margin-20;
      if (b[j][i] > 0) {
        float p = log(b[j][i])/log(2);
        cak_icondisp(b[j][i], x, y, bs); //cak2//
        max_t = max(max_t, b[j][i]);     //cak3: remember max value of t so far achievable//
      }
    }

  noStroke();
  fill(51, 102, 204);
  rect(0, menuHeight_margin, width, pad-30);
  textt("SCORE : "+score, pad, menuHeight_margin+30, color(255), calendar_font, pad/1.9f, LEFT);
  textt("kWh (from first day to yesterday of this month)", width/2, menuHeight_margin+30, color(255), calendar_font, pad/1.9f, CENTER);
  textt("BEST : "+savedScore, width - pad, menuHeight_margin+30, color(255), calendar_font, pad/1.9f, RIGHT);
  noFill();

  if (dead>0) {
    fill(200);
    cak_icondisp_gameover(); //cak3: displaying all boards in grey//
    cak_icondisp_background(max_t);

    fill(51, 102, 204);
    rect(pad+60, menuHeight_margin+(2*pad)+bs-30, (3*bs)+(2*pad)+40, bs, 15);
    textt("GAME OVER", ((pad+60)+((3*bs)+(2*pad)+110))/2, menuHeight_margin+(3*pad)+bs, color(255), fetch_value_font, 60, CENTER);
    textt("Hold on an \"Energy Game\" icon  3 seconds to restart", ((pad+60)+((3*bs)+(2*pad)+110))/2, menuHeight_margin+(3*pad)+bs+70, color(255), calendar_font, pad/1.9f, CENTER);


    if (overPicture(((0+360)/2)-100, 0, 100, menuHeight_margin)) {
      overPicture1 = true;
      image(canvas_Selection1over, 0, 10);
      image(canvas_Selection2, 360, 6);
      image(canvas_Selection3, 720, 8);
      image(canvas_Selection4, 1080, 8);
    } else if (overPicture(((360+720)/2)-100, 0, 100, menuHeight_margin)) {
      overPicture2 = true;
      image(canvas_Selection1, 0, 10);
      image(canvas_Selection2over, 360, 6);
      image(canvas_Selection3, 720, 8);
      image(canvas_Selection4, 1080, 8);
    } else if (overPicture(((720+1080)/2)-100, 0, 100, menuHeight_margin)) {
      overPicture3 = true;
      image(canvas_Selection1, 0, 10);
      image(canvas_Selection2, 360, 6);
      image(canvas_Selection3over, 720, 8);
      image(canvas_Selection4, 1080, 8);
    } else if (overPicture(((1080+1440)/2)-100, 0, 100, menuHeight_margin)) {
      overPicture3 = true;
      image(canvas_Selection1, 0, 10);
      image(canvas_Selection2, 360, 6);
      image(canvas_Selection3, 720, 8);
      image(canvas_Selection4over, 1080, 8);
    } else {
      overPicture1 = overPicture2 = overPicture3 = overPicture4 = false;
      image(canvas_Selection1, 0, 10);
      image(canvas_Selection2, 360, 6);
      image(canvas_Selection3, 720, 8);
      image(canvas_Selection4, 1080, 8);
    }

    fill(255, 0, 0);
    ellipse(PApplet.parseInt(hand_x_position), PApplet.parseInt(hand_y_position), 20, 20);
    noStroke();
    noFill();

    int newScore = score;
    if (newScore > savedScore ) {

      savedScore = newScore;  
      String scoreVal = ""+savedScore;
      String [] scoreFile = {
        scoreVal
      };
      saveStrings("score.txt", scoreFile);
    }
  }
  ellipse(PApplet.parseInt(hand_x_position), PApplet.parseInt(hand_y_position), 20, 20);
}



public void textt(String t, float x, float y, int c, PFont ff, float s, int align) {
  fill(c); 
  textAlign(align); 
  textFont(ff, s);
  text(t, x, y);
}

public void restart() {
  b = new int[cak_numrows][cak_numrows];
  spawn();
  score = dead = 0;
}

public void spawn() {
  ArrayList<Integer> xs = new ArrayList<Integer>(), ys = new ArrayList<Integer>();
  for (int j = 0; j < b.length; j++) for (int i = 0; i < b[j].length; i++) if (b[j][i]==0) {
    xs.add(i);
    ys.add(j);
  }
  int rnd = (int)random(0, xs.size()), y = ys.get(rnd), x = xs.get(rnd);
  b[y][x] = random(0, 1) < .9f ? cak_basis : 2*cak_basis;
}

public void cak_icondisp(int t, float x, float y, int s) {

  if (t == 1) { 
    image(icon_Image[located[0]], x, y, s, s);
  }
  if (t == 2) { 
    image(icon_Image[located[1]], x, y, s, s);
  }
  if (t == 4) { 
    image(icon_Image[located[2]], x, y, s, s);
  }
  if (t == 8) { 
    image(icon_Image[located[3]], x, y, s, s);
  }
  if (t == 16) { 
    image(icon_Image[located[4]], x, y, s, s);
  }
  if (t == 32) { 
    image(icon_Image[located[5]], x, y, s, s);
  }
  if (t == 64) { 
    image(icon_Image[located[6]], x, y, s, s);
  }
  if (t == 128) { 
    image(icon_Image[located[7]], x, y, s, s);
  }
}

public void cak_icondisp_background(int max_t) {
  // display icon pyramid on the right margin of game board up to board numbered max_t

  if (max_t >= 1) { 
    image(icon_Image[located[0]], cak_x_icon, cak_y_icon1, icon_size, icon_size);
    textt(Building_name[located[0]], cak_x_icon+100, cak_y_icon1+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon1+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[0]])), cak_x_icon+530, cak_y_icon1+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 2) {
    image(icon_Image[located[1]], cak_x_icon, cak_y_icon2, icon_size, icon_size);
    textt(Building_name[located[1]], cak_x_icon+100, cak_y_icon2+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon2+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[1]])), cak_x_icon+530, cak_y_icon2+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 4) { 
    image(icon_Image[located[2]], cak_x_icon, cak_y_icon4, icon_size, icon_size);
    textt(Building_name[located[2]], cak_x_icon+100, cak_y_icon4+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon4+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[2]])), cak_x_icon+530, cak_y_icon4+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 8) { 
    image(icon_Image[located[3]], cak_x_icon, cak_y_icon8, icon_size, icon_size);
    textt(Building_name[located[3]], cak_x_icon+100, cak_y_icon8+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon8+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[3]])), cak_x_icon+530, cak_y_icon8+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 16) { 
    image(icon_Image[located[4]], cak_x_icon, cak_y_icon16, icon_size, icon_size);
    textt(Building_name[located[4]], cak_x_icon+100, cak_y_icon16+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon16+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[4]])), cak_x_icon+530, cak_y_icon16+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 32) { 
    image(icon_Image[located[5]], cak_x_icon, cak_y_icon32, icon_size, icon_size);
    textt(Building_name[located[5]], cak_x_icon+100, cak_y_icon32+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon32+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[5]])), cak_x_icon+530, cak_y_icon32+65, color(102), fetch_value_font, 60, RIGHT);
  }

  if (max_t >= 64) { 
    image(icon_Image[located[6]], cak_x_icon, cak_y_icon64, icon_size, icon_size);
    textt(Building_name[located[6]], cak_x_icon+100, cak_y_icon64+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon64+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[6]])), cak_x_icon+530, cak_y_icon64+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 128) { 
    image(icon_Image[located[7]], cak_x_icon, cak_y_icon128, icon_size, icon_size);
    textt(Building_name[located[7]], cak_x_icon+100, cak_y_icon128+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon128+65, color(160), fetch_font, 40, LEFT);
    textt(str(PApplet.parseInt(ValueGame[located[7]])), cak_x_icon+530, cak_y_icon128+65, color(102), fetch_value_font, 60, RIGHT);
  }
}

public void cak_icondisp_gameover() {
  // display icon pyramid on the right margin of game board up to board numbered max_t

  image(icon_Image[located[0]], cak_x_icon, cak_y_icon1, icon_size, icon_size);
  image(icon_Image[located[1]], cak_x_icon, cak_y_icon2, icon_size, icon_size);
  image(icon_Image[located[2]], cak_x_icon, cak_y_icon4, icon_size, icon_size);
  image(icon_Image[located[3]], cak_x_icon, cak_y_icon8, icon_size, icon_size);
  image(icon_Image[located[4]], cak_x_icon, cak_y_icon16, icon_size, icon_size);
  image(icon_Image[located[5]], cak_x_icon, cak_y_icon32, icon_size, icon_size);
  image(icon_Image[located[6]], cak_x_icon, cak_y_icon64, icon_size, icon_size);
  image(icon_Image[located[7]], cak_x_icon, cak_y_icon128, icon_size, icon_size);

  textAlign(LEFT);

  textFont(fetch_font, 40);
  text(Building_name[located[0]], cak_x_icon+100, cak_y_icon1+65);
  text(Building_name[located[1]], cak_x_icon+100, cak_y_icon2+65);
  text(Building_name[located[2]], cak_x_icon+100, cak_y_icon4+65);
  text(Building_name[located[3]], cak_x_icon+100, cak_y_icon8+65);
  text(Building_name[located[4]], cak_x_icon+100, cak_y_icon16+65);
  text(Building_name[located[5]], cak_x_icon+100, cak_y_icon32+65);
  text(Building_name[located[6]], cak_x_icon+100, cak_y_icon64+65);
  text(Building_name[located[7]], cak_x_icon+100, cak_y_icon128+65);


  text("kWh", cak_x_icon+550, cak_y_icon1+65);
  text("kWh", cak_x_icon+550, cak_y_icon2+65);
  text("kWh", cak_x_icon+550, cak_y_icon4+65);
  text("kWh", cak_x_icon+550, cak_y_icon8+65);
  text("kWh", cak_x_icon+550, cak_y_icon16+65);
  text("kWh", cak_x_icon+550, cak_y_icon32+65);
  text("kWh", cak_x_icon+550, cak_y_icon64+65);
  text("kWh", cak_x_icon+550, cak_y_icon128+65);

  textAlign(RIGHT);
  textFont(fetch_value_font, 60);
  text(PApplet.parseInt(ValueGame[located[0]]), cak_x_icon+530, cak_y_icon1+65);
  text(PApplet.parseInt(ValueGame[located[1]]), cak_x_icon+530, cak_y_icon2+65);
  text(PApplet.parseInt(ValueGame[located[2]]), cak_x_icon+530, cak_y_icon4+65);
  text(PApplet.parseInt(ValueGame[located[3]]), cak_x_icon+530, cak_y_icon8+65);
  text(PApplet.parseInt(ValueGame[located[4]]), cak_x_icon+530, cak_y_icon16+65);
  text(PApplet.parseInt(ValueGame[located[5]]), cak_x_icon+530, cak_y_icon32+65);
  text(PApplet.parseInt(ValueGame[located[6]]), cak_x_icon+530, cak_y_icon64+65);
  text(PApplet.parseInt(ValueGame[located[7]]), cak_x_icon+530, cak_y_icon128+65);

  filter(GRAY);
}

public boolean gameover() {
  int[] dx = {
    1, -1, 0, 0
  }
  , dy = {
    0, 0, 1, -1
  };
  boolean out = true;
  for (int i = 0; i < 4; i++) if (go(dy[i], dx[i], false) != null) out = false;
  return out;
}

public int[][] go(int dy, int dx, boolean updatescore) {
  int[][] bak = new int[cak_numrows][cak_numrows];
  for (int j = 0; j < cak_numrows; j++) for (int i = 0; i < cak_numrows; i++) bak[j][i] = b[j][i];
  boolean moved = false; 
  if (dx != 0 || dy != 0) {
    int d =  dx != 0 ? dx : dy;
    for (int perp = 0; perp < b.length; perp++) 
      for (int tang = (d > 0 ? b.length - 2 : 1); 
      tang != (d > 0 ? -1 : b.length); 
      tang-=d) {
        int y = dx != 0 ? perp : tang, x = dx != 0 ? tang : perp, ty = y, tx = x;
        if (bak[y][x]==0) continue;
        for (int i= (dx != 0 ? x : y)+d; 
        i!= (d > 0 ? b.length : -1); 
        i+=d) {
          int r = dx != 0 ? y : i, c = dx != 0 ? i : x;
          if (bak[r][c] != 0 && bak[r][c] != bak[y][x]) break;
          if (dx != 0) tx = i; 
          else ty = i;
        }
        if ( (dx != 0 && tx == x) || (dy != 0 && ty == y)) continue;
        else if (bak[ty][tx]==bak[y][x]) {
          bak[ty][tx] *= 2;
          if (updatescore) score += bak[ty][tx];
          moved = true;
        } else if ( (dx != 0 && tx != x) || (dy != 0 && ty != y)) {
          bak[ty][tx] = bak[y][x];
          moved = true;
        }
        if (moved) bak[y][x] = 0;
      }
  }
  return moved ? bak : null;
}


public void checkMouseCanVas2(int hand_x_position, int hand_y_position) {
  mouseX = hand_x_position ;
  mouseY = hand_y_position ;

  if (dead == 0) {
    preecha_previousMouseX = lerp(preecha_previousMouseX, mouseX, 0.09f);
    preecha_previousMouseY = lerp(preecha_previousMouseY, mouseY, 0.09f);

    float current_time = millis();
    int dx, dy;


    if (current_time - trigger_time > 1500 ) {
      fill(0, 255, 0);
      ellipse(mouseX, mouseY, 20, 20);
      image(hand_black, mouseX, mouseY);

      dy = (preecha_previousMouseY-mouseY) >= 150 ? -1 : (preecha_previousMouseY-mouseY)<=-150 ? 1 : 0;
      dx=(preecha_previousMouseX-mouseX)>=150 ? -1 : ((preecha_previousMouseX-mouseX)<=-150 ? 1 : 0);
      if ((dx != 0) || (dy != 0)) {
        noSwipe+=1;
        trigger_time = millis();
      }
    } else {
      fill(255, 0, 0);
      ellipse(mouseX, mouseY, 20, 20);
      image(hand_white, mouseX, mouseY);
      dy = 0; 
      dx = 0;
    }
    int[][] newb = go(dy, dx, true);
    if (newb != null) {
      b = newb;
      spawn();
    }
    if (gameover()) {
      dead = 1;
    }
  }
}

public void canvas2_init() {
  max_t = 1; 
  restart();
//  gameBackground.play();
}

public void canVas3() {
  try {
    pushMatrix();
    translate(preecha_imageShift_xpos_canvas3, preecha_imageShift_ypos_canvas3+150);  // 150 is the header of application
    if (preecha_showPic_canvas3 == 0) {
      preecha_showPicLeft_canvas3 = (preecha_allImages_canvas3.size()) - 1;
    } else {
      preecha_showPicLeft_canvas3 = preecha_showPic_canvas3 - 1;
    }

    if (preecha_showPic_canvas3 == (preecha_allImages_canvas3.size()) - 1 ) {
      preecha_showPicRight_canvas3 = 0;
    } else {
      preecha_showPicRight_canvas3 = preecha_showPic_canvas3 + 1;
    }

    PImage picLeft = preecha_allImages_canvas3.get(preecha_showPicLeft_canvas3);
    PImage pic = preecha_allImages_canvas3.get(preecha_showPic_canvas3);
    PImage picRight = preecha_allImages_canvas3.get(preecha_showPicRight_canvas3);

    image (picLeft, -width, 0);
    image (pic, 0, 0);
    image (picRight, width, 0);
  }
  
  catch (Exception e) {
    preecha_allImages_canvas3 = new ArrayList<PImage>(); 
    preecha_checkImgfiles(preecha_pictureFolder_canvas3);
  }
  preecha_imageShift_xpos_canvas3 += (preecha_imageShift_Target_canvas3 - preecha_imageShift_xpos_canvas3)*preecha_easingRatio_canvas3;
  popMatrix();
}


public void checkMouseCanVas3(int hand_x_position, int hand_y_position) {
  mouseX = hand_x_position ;
  mouseY = hand_y_position ;

  preecha_previousMouseX = lerp(preecha_previousMouseX, mouseX, 0.09f);

  float current_time = millis();
  int dx;

  if (current_time - trigger_time > 2500 ) {
    image(hand_black, mouseX, mouseY);


    dx=(preecha_previousMouseX-mouseX)>=150 ? -1 : ((preecha_previousMouseX-mouseX)<=-150 ? 1 : 0);

    if (dx == -1) {
      noSwipe +=1;
      preecha_showPic_canvas3 = (preecha_showPic_canvas3+1)%(preecha_allImages_canvas3.size());
      preecha_imageShift_xpos_canvas3 = width;
    }
    if (dx == 1) {
      noSwipe +=1;
      if (preecha_showPic_canvas3 == 0) {
        preecha_showPic_canvas3 = (preecha_allImages_canvas3.size())-1;
      } else {
        preecha_showPic_canvas3 = (preecha_showPic_canvas3-1);
      }
      preecha_imageShift_xpos_canvas3 = -width;
    }
    if (dx != 0) {
      trigger_time = millis();
    }
  } else {
    image(hand_white, mouseX, mouseY);
    dx = 0;
  }
}

public void canvas3_init(){  
}
public void canVas4() {

  int startday   = (checkFromcalendar(1, month(), year()))+1;
  int weekend = floor((8-startday)/7)+floor((startday+day()-1)/7)+floor((startday+day()-2)/7); 

  if ((weekend%2)==0) {
    saturday = weekend/2;
    sunday   = weekend/2;
  } else {
    saturday = floor(weekend/2)+1;
    sunday   = floor(weekend/2);
  }
  int weekday  = day()- weekend;


  String [] targetdepartment = split(tarketQuata[1], ",");
  String [] targetEEfl2      = split(tarketQuata[2], ",");
  String [] targetEEfl3      = split(tarketQuata[3], ",");
  String [] targetEEfl4      = split(tarketQuata[4], ",");
  String [] targetEEfl5      = split(tarketQuata[5], ",");
  String [] targetENG4fl12   = split(tarketQuata[6], ",");
  String [] targetENG4fl13   = split(tarketQuata[7], ",");
  String [] targetHV         = split(tarketQuata[8], ",");

  target = new int [8];
  target[0] = PApplet.parseInt((weekday*PApplet.parseInt(targetdepartment[1]))+(saturday*PApplet.parseInt(targetdepartment[2]))+(sunday*PApplet.parseInt(targetdepartment[3]))); 
  target[1] = PApplet.parseInt((weekday*PApplet.parseInt(targetEEfl2[1]))+(saturday*PApplet.parseInt(targetEEfl2[2]))+(sunday*PApplet.parseInt(targetEEfl2[3]))); 
  target[2] = PApplet.parseInt((weekday*PApplet.parseInt(targetEEfl3[1]))+(saturday*PApplet.parseInt(targetEEfl3[2]))+(sunday*PApplet.parseInt(targetEEfl3[3]))); 
  target[3] = PApplet.parseInt((weekday*PApplet.parseInt(targetEEfl4[1]))+(saturday*PApplet.parseInt(targetEEfl4[2]))+(sunday*PApplet.parseInt(targetEEfl4[3]))); 
  target[4] = PApplet.parseInt((weekday*PApplet.parseInt(targetEEfl5[1]))+(saturday*PApplet.parseInt(targetEEfl5[2]))+(sunday*PApplet.parseInt(targetEEfl5[3]))); 
  target[5] = PApplet.parseInt((weekday*PApplet.parseInt(targetENG4fl12[1]))+(saturday*PApplet.parseInt(targetENG4fl12[2]))+(sunday*PApplet.parseInt(targetENG4fl12[3]))); 
  target[6] = PApplet.parseInt((weekday*PApplet.parseInt(targetENG4fl13[1]))+(saturday*PApplet.parseInt(targetENG4fl13[2]))+(sunday*PApplet.parseInt(targetENG4fl13[3]))); 
  target[7] = PApplet.parseInt((weekday*PApplet.parseInt(targetHV[1]))+(saturday*PApplet.parseInt(targetHV[2]))+(sunday*PApplet.parseInt(targetHV[3]))); 
  
  percent = PApplet.parseInt(ValueGame[zone_index])*100/target[zone_index]; 

  radiusOfEnergyConsumed = (percent * 800)/100;
  if (radiusOfEnergyConsumed >= 800) {
    radiusOfEnergyConsumed = 800;
  }
  radius = lerp(radius, radiusOfEnergyConsumed, 0.05f);
  fontBD = lerp(fontBD, 100, 0.1f);
  percentFontRatio = lerp(percentFontRatio, 200, 0.05f);

  //  background(255);

  fill(51, 51, 255, 220);
  rect(10, height-200, 450, 150, 30);

  fill(100);
  textFont(textBuilding, fontBD);
  textAlign(LEFT, TOP);
  text(Building_name_alarm[zone_index], 20, 180);

  textFont(textwarn, 70);
  textAlign(LEFT, BOTTOM);
  text("Energy Limit Target", 0+20, height-210);
  textAlign(RIGHT, BOTTOM);
  text("Energy Consumed", width-20, height-210);

  textFont(textwarn, 50);
  textAlign(LEFT, BOTTOM);
  text("accumulate daily target (1st - today) of this month", 0+10, height-10);
  textAlign(RIGHT, BOTTOM);
  text("accumulate daily consumption (1st - today) of this month", width-10, height-10);

  fill(255);
  textFont(textwarn, 150);
  textAlign(LEFT, BOTTOM);
  text(target[zone_index], 0+30, height-70);

  textFont(textwarn, 100);
  textAlign(RIGHT, BOTTOM);
  text("kWh", 460, height-80);
  text("kWh", width-10, height-80);


  fill(51, 51, 255, 200);
  pushMatrix();
  translate(width/2, (height+200)/2);
  for (int i= 0; i<60; i++) {
    rotate(TWO_PI/60);
    ellipse(400, 0, 10, 10);
  }
  popMatrix();
  noFill();

  if ((percent >= 70)&&(percent<90)) {
    ellipseMode(CENTER); 
    fill(252, 204, 13);
    noStroke();
    ellipse(width/2, (height+200)/2, radius, radius);
    rect(width-460, height-200, 450, 150, 30);
    fill(252, 223, 81);
    textFont(alarm, 100);
    textAlign(LEFT, TOP);
    text("WARNING !!", 10, 300);
  }
  if (percent >= 90) {
    ellipseMode(CENTER); 
    fill(255, 0, 0, 200);
    noStroke();
    ellipse(width/2, (height+200)/2, radius, radius);
    rect(width-460, height-200, 450, 150, 30);
    fill(255, 0, 0);
    textFont(alarm, 100);
    textAlign(LEFT, TOP);
    text("!! ALERT !!", 10, 300);
  }
  if (percent < 70) {
    ellipseMode(CENTER); 
    fill(0, 255, 0, 200);
    noStroke();
    ellipse(width/2, (height+200)/2, radius, radius);
    rect(width-460, height-200, 450, 150, 30);
    fill(0, 255, 0);
    textFont(alarm, 100);
    textAlign(LEFT, TOP);
    text("Normal :)", 10, 300);
  }
  if (percent >= 60) {
    fill(255);
    textFont(alarm, percentFontRatio);
    textAlign(CENTER, CENTER);
    text(percent+" %", width/2, (height+200)/2);
  }

  if (percent < 60) {
    fill(100);
    textFont(alarm, percentFontRatio);
    textAlign(CENTER, CENTER);
    text(percent+" %", width/2, (height+200)/2);
  }

  fill(255);
  textFont(textwarn, 150);
  textAlign(LEFT, BOTTOM);
  text(PApplet.parseInt(ValueGame[zone_index]), width-460+20, height-70);

  textFont(textwarn, 100);
  textAlign(RIGHT, BOTTOM);
  text("kWh", width-10, height-80);
}



public void checkMouseCanVas4(int hand_x_position, int hand_y_position) {
  mouseX = hand_x_position ;
  mouseY = hand_y_position ;

  preecha_previousMouseX = lerp(preecha_previousMouseX, mouseX, 0.09f);

  float current_time = millis();
  int dx;

  if (current_time - trigger_time > 2500 ) {
    image(hand_black, mouseX, mouseY);

    dx=(preecha_previousMouseX-mouseX)>=150 ? -1 : ((preecha_previousMouseX-mouseX)<=-150 ? 1 : 0);

    if (dx == -1) {
      noSwipe +=1;
      zone_index = (zone_index+1)%8;
    }
    if (dx == 1) {
      noSwipe +=1;
      if (zone_index == 0) {
        zone_index = 7;
      } else {
        zone_index = (zone_index-1);
      }
    }
    if (dx != 0) {
      trigger_time = millis();
      fontBD = 0;
      percentFontRatio = 0;
    }
  } else {
    image(hand_white, mouseX, mouseY);
    dx = 0;
  }
}

public void canvas4_init(){  
}
public void canVas_Menu() {
  background(255);
  imageMode(CORNER);

  clock(1680, 85, 1680, 140);


  if (overPicture(((0+360)/2)-100, 0, 100, menuHeight_margin)) {
    overPicture1 = true;
    image(canvas_Selection1over, 0, 10);
    image(canvas_Selection2, 360, 6);
    image(canvas_Selection3, 720, 8);
    image(canvas_Selection4, 1080, 8);
  } else if (overPicture(((360+720)/2)-100, 0, 100, menuHeight_margin)) {
    overPicture2 = true;
    image(canvas_Selection1, 0, 10);
    image(canvas_Selection2over, 360, 6);
    image(canvas_Selection3, 720, 8);
    image(canvas_Selection4, 1080, 8);
  } else if (overPicture(((720+1080)/2)-100, 0, 100, menuHeight_margin)) {
    overPicture3 = true;
    image(canvas_Selection1, 0, 10);
    image(canvas_Selection2, 360, 6);
    image(canvas_Selection3over, 720, 8);
    image(canvas_Selection4, 1080, 8);
  } else if (overPicture(((1080+1440)/2)-100, 0, 100, menuHeight_margin)) {
    overPicture4 = true;
    image(canvas_Selection1, 0, 10);
    image(canvas_Selection2, 360, 6);
    image(canvas_Selection3, 720, 8);
    image(canvas_Selection4over, 1080, 8);
  } else {
    overPicture1 = overPicture2 = overPicture3 = overPicture4 = false;
    image(canvas_Selection1, 0, 10);
    image(canvas_Selection2, 360, 6);
    image(canvas_Selection3, 720, 8);
    image(canvas_Selection4, 1080, 8);
  }

  ///////////////////////////////////////////////////
  // now hand is NOT over any pictures of menu icon
  //

  if (!(overPicture1) && !(overPicture2) && !(overPicture3) && !(overPicture4)) {

    // assign the most recent time that hand is not over any menu icon //
    // pictures to variable <preecha_resetSelect_icon>                 //

    preecha_resetSelect_icon = millis();

    // assign temporary variable //


  }


  ////////////////////////////////
  // now hand is over Picture 1
  //

  if (overPicture1) {

    // compute how long the hand has touched this picture //    
    int preecha_passTimecanvasMenu = millis() - preecha_resetSelect_icon;

    // check if the hand has touched this picture for long enough time //
    if (preecha_passTimecanvasMenu >= preecha_timeovercanvas_selected) { 

      // now hand has touched this picture long enough //
      // let us now activate the canvas                //

      // assign canvas number according to the touched picture //
      canvas_number = 1;

      if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

        // run the following canvas-initialisation code only //
        // once per each time of entering this canvas        //

        alreadyRunInitialisationCodeInCurrentCanvas = 1;
        //        canvas1_init();
      }
    }
  }


  ////////////////////////////////
  // now hand is over Picture 2
  //

  if (overPicture2) {

    // compute how long the hand has touched this picture //    
    int preecha_passTimecanvasMenu = millis() - preecha_resetSelect_icon;

    // check if the hand has touched this picture for long enough time //
    if (preecha_passTimecanvasMenu >= preecha_timeovercanvas_selected) { 

      // now hand has touched this picture long enough //
      // let us now activate the canvas                //

      // assign canvas number according to the touched picture //
      canvas_number = 2;

      if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

        // run the following canvas-initialisation code only //
        // once per each time of entering this canvas        //

        alreadyRunInitialisationCodeInCurrentCanvas = 1;
        canvas2_init();
      }
    }
  }


  ////////////////////////////////
  // now hand is over Picture 3
  //

  if (overPicture3) {

    // compute how long the hand has touched this picture //    
    int preecha_passTimecanvasMenu = millis() - preecha_resetSelect_icon;

    // check if the hand has touched this picture for long enough time //
    if (preecha_passTimecanvasMenu >= preecha_timeovercanvas_selected) { 

      // now hand has touched this picture long enough //
      // let us now activate the canvas                //

      // assign canvas number according to the touched picture //
      canvas_number = 3;

      if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

        // run the following canvas-initialisation code only //
        // once per each time of entering this canvas        //
        //        canvas3_init();
        alreadyRunInitialisationCodeInCurrentCanvas = 1;
      }
    }
  }


  ////////////////////////////////
  // now hand is over Picture 4
  //

  if (overPicture4) {

    // compute how long the hand has touched this picture //    
    int preecha_passTimecanvasMenu = millis() - preecha_resetSelect_icon;

    // check if the hand has touched this picture for long enough time //
    if (preecha_passTimecanvasMenu >= preecha_timeovercanvas_selected) { 

      // now hand has touched this picture long enough //
      // let us now activate the canvas                //

      // assign canvas number according to the touched picture //
      canvas_number = 4;

      if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

        // run the following canvas-initialisation code only //
        // once per each time of entering this canvas        //
        //        canvas4_init();
        alreadyRunInitialisationCodeInCurrentCanvas = 1;
      }
    }
  }
}



public boolean overPicture(int x, int y, int sizeX, int sizeY) {
  if (mouseX >= x && mouseX <= x+sizeX && mouseY >= y && mouseY <= y+sizeY) {  
    return true;
  } else {
    return false;
  }
}

public void preecha_checkImgfiles(String preecha_pictureFolder) {
  String[] outList_of_foundImageFiles = {
  };
  String[] list_of_imageFileSuffixes = {
    "jpg", "png"
  };
  File file = new File(preecha_pictureFolder);
  String[] names = file.list();

  for (int numberImage =0; numberImage < names.length; numberImage++ ) {
    String[] curr_filenameSplit = splitTokens( names[numberImage], "." );

    for ( int fileSuffix_i = 0; fileSuffix_i < list_of_imageFileSuffixes.length; fileSuffix_i++ ) { 
      String examinedFile_filesuffix = curr_filenameSplit[1] ;
      String listOfValid_fileSuffixed = list_of_imageFileSuffixes[fileSuffix_i] ;

      if ( examinedFile_filesuffix.equals( listOfValid_fileSuffixed ) ) {

        outList_of_foundImageFiles = append( outList_of_foundImageFiles, preecha_pictureFolder+names[numberImage] );
      }
    }
  }



  for (int i =0; i < outList_of_foundImageFiles.length; i++) {
    if (preecha_pictureFolder == preecha_pictureFolder_canvas1) {
      preecha_allImages.add(loadImage(outList_of_foundImageFiles[i]));
      (preecha_allImages.get(i)).resize(width, height - menuHeight_margin);
    } 
    if (preecha_pictureFolder == preecha_pictureFolder_canvas3) {
      preecha_allImages_canvas3.add(loadImage(outList_of_foundImageFiles[i]));
      (preecha_allImages_canvas3.get(i)).resize(width, height - menuHeight_margin);
    } 
    if (preecha_pictureFolder == preecha_pictureFolder_canvas0) {
      preecha_allImages_canvas0.add(loadImage(outList_of_foundImageFiles[i]));
      (preecha_allImages_canvas0.get(i)).resize(640, 400);
    }
  }
}

public void clock(int clock_xPosition, int clock_yPosition, int calendar_xPosition, int calendar_yPosition ) {
  String[] dayName = {
    "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
  };
  String[] monthName = {
    "January", "February", "March", "April", "May", "June", 
    "July", "August", "September", "October", "November", "December"
  };

  // Draw current time (Draw Hour/Minute/AM or PM)
  day = day();    // Values from 1 - 31
  month = month();  // Values from 1 - 12
  year = year();   // 2003, 2004, 2005, etc.
  sec = second();
  min = minute();
  hour = hour();

  int christYEAR = year - 543;
  textFont(clock_font, 150);
  textAlign(CENTER);
  
  // Define Minute (fixes 10 minute bug)
  if (min<10) {
    fill(102, 102, 102, 70);
    noStroke();
    rect(1440, 0, width, 150);
    noFill();
    fill(102, 102, 102);
    text(hour+":0"+min, clock_xPosition, clock_yPosition);
    textFont(calendar_font,40);
    textAlign(CENTER);
    text(dayName[weekNumber]+"  "+day+"  "+monthName[month-1]+"  "+year, calendar_xPosition, calendar_yPosition);
  }
  else {
    fill(102, 102, 102, 70);
    noStroke();
    rect(1440, 0, width, 150);
    noFill();
    fill(102, 102, 102);
    text(hour+":"+min, clock_xPosition, clock_yPosition);
    textFont(calendar_font,40);
    textAlign(CENTER);
    text(dayName[weekNumber]+"  "+day+"  "+monthName[month-1]+"  "+year, calendar_xPosition, calendar_yPosition);
  }
  weekNumber = checkFromcalendar(day, month, year);
}

public int checkFromcalendar(int d, int m, int y) {
  if (m < 3) {
    m += 12;
    y--;
  }
  return (d + PApplet.parseInt((m+1)*2.6f) +  y + PApplet.parseInt(y/4) + 6*PApplet.parseInt(y/100) + PApplet.parseInt(y/400) + 6) % 7;
}

public void trackingUser() { 
  kinect.update();
  kinect.getUsers();
  preecha_getVisibleUsers();

  if (userList.length == 0) {
//    println("Nouser");
  } else {
    //    printArray(userList);
  }

  if (userList.length > 0) {
    if (userList.length == 1) {
      controlUserId = userList[0];
    }
    if (kinect.isTrackingSkeleton(controlUserId)) {
      kinect.getJointPositionSkeleton(controlUserId, SimpleOpenNI.SKEL_RIGHT_HAND, rightHand);
      kinect.convertRealWorldToProjective(rightHand, convertedrightHand);

      xMapped = map(convertedrightHand.x, 0, 640, 0, width);
      yMapped = map(convertedrightHand.y, 0, 480, 0, height);
    }
  }
}


//------------------- SimpleOpenNI event --------------------------

public void onNewUser(SimpleOpenNI curContext, int userId) {
  curContext.startTrackingSkeleton(userId);
  userCount += 1;
}

public void preecha_getVisibleUsers() {

  userList = new int[0];

  for (int i = 1; i <= 6; i++) {
    if ( 60*60*hour() + 60*minute() + second() - userLastTimeSeen[i-1] < 0.2f) {   
      userList = append(userList, i);
    }
  }
}

public void onVisibleUser(SimpleOpenNI curContext, int userId) {
  userLastTimeSeen[userId-1] = 60*60*hour() + 60*minute() + second();
}

public void onLostUser(SimpleOpenNI curContext, int userId) {
  userCount -= 1;
  
  if (userId == controlUserId) {
    controlUserId = userList[0];
  }
}


public float detect_hand_x_position() {
  return xMapped;
}
public float detect_hand_y_position() {
  return yMapped;
}



  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "The_Beginning_29092014_1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

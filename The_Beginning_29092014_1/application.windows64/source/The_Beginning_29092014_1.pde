//import ddf.minim.*;
import SimpleOpenNI.*;
import java.net.*;
import java.io.*;
import java.util.*;

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
float  preecha_easingRatio = 0.35;    // speed to change to next Picture
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
int pad = int(70), bs = int(230), len = pad*(b.length+1)+bs*b.length, score = 0, dead = 1;
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
float  preecha_easingRatio_canvas3 = 0.35;    // speed to change to next Picture
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



void setup() {
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

void draw() {

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
    checkMouseCanVas1(int(hand_x_position), int(hand_y_position));
  } 

  if (canvas_number == 2) {
    canVas_Menu();
    canVas2();
    checkMouseCanVas2(int(hand_x_position), int(hand_y_position));
  }

  if (canvas_number == 3) {
    canVas_Menu();
    canVas3();
    checkMouseCanVas3(int(hand_x_position), int(hand_y_position));
  }

  if (canvas_number == 4) {
    canVas_Menu();
    canVas4();
    checkMouseCanVas4(int(hand_x_position), int(hand_y_position));
  }

  println("no of swipe" +noSwipe);
  println("NUmber of user" + userCount);
}


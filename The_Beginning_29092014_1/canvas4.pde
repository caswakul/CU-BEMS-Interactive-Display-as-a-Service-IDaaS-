void canVas4() {

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
  target[0] = int((weekday*int(targetdepartment[1]))+(saturday*int(targetdepartment[2]))+(sunday*int(targetdepartment[3]))); 
  target[1] = int((weekday*int(targetEEfl2[1]))+(saturday*int(targetEEfl2[2]))+(sunday*int(targetEEfl2[3]))); 
  target[2] = int((weekday*int(targetEEfl3[1]))+(saturday*int(targetEEfl3[2]))+(sunday*int(targetEEfl3[3]))); 
  target[3] = int((weekday*int(targetEEfl4[1]))+(saturday*int(targetEEfl4[2]))+(sunday*int(targetEEfl4[3]))); 
  target[4] = int((weekday*int(targetEEfl5[1]))+(saturday*int(targetEEfl5[2]))+(sunday*int(targetEEfl5[3]))); 
  target[5] = int((weekday*int(targetENG4fl12[1]))+(saturday*int(targetENG4fl12[2]))+(sunday*int(targetENG4fl12[3]))); 
  target[6] = int((weekday*int(targetENG4fl13[1]))+(saturday*int(targetENG4fl13[2]))+(sunday*int(targetENG4fl13[3]))); 
  target[7] = int((weekday*int(targetHV[1]))+(saturday*int(targetHV[2]))+(sunday*int(targetHV[3]))); 
  
  percent = int(ValueGame[zone_index])*100/target[zone_index]; 

  radiusOfEnergyConsumed = (percent * 800)/100;
  if (radiusOfEnergyConsumed >= 800) {
    radiusOfEnergyConsumed = 800;
  }
  radius = lerp(radius, radiusOfEnergyConsumed, 0.05);
  fontBD = lerp(fontBD, 100, 0.1);
  percentFontRatio = lerp(percentFontRatio, 200, 0.05);

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
  text(int(ValueGame[zone_index]), width-460+20, height-70);

  textFont(textwarn, 100);
  textAlign(RIGHT, BOTTOM);
  text("kWh", width-10, height-80);
}



void checkMouseCanVas4(int hand_x_position, int hand_y_position) {
  mouseX = hand_x_position ;
  mouseY = hand_y_position ;

  preecha_previousMouseX = lerp(preecha_previousMouseX, mouseX, 0.09);

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


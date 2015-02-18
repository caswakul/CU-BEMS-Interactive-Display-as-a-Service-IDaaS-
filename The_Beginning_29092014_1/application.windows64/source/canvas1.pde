void canVas1() {

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


void checkMouseCanVas1(int hand_x_position, int hand_y_position) {
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


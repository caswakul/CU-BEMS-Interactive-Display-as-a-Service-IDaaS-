void canVas3() {
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


void checkMouseCanVas3(int hand_x_position, int hand_y_position) {
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


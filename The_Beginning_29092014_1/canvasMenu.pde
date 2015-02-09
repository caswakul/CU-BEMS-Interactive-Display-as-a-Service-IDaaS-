void canVas_Menu() {
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



boolean overPicture(int x, int y, int sizeX, int sizeY) {
  if (mouseX >= x && mouseX <= x+sizeX && mouseY >= y && mouseY <= y+sizeY) {  
    return true;
  } else {
    return false;
  }
}


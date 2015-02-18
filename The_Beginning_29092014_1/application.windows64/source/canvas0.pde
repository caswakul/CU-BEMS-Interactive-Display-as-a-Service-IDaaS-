void canVas0() {

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
      filter(BLUR, .8);
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


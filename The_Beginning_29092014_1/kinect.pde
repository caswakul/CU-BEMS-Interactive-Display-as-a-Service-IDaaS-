void trackingUser() { 
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

void onNewUser(SimpleOpenNI curContext, int userId) {
  curContext.startTrackingSkeleton(userId);
  userCount += 1;
}

void preecha_getVisibleUsers() {

  userList = new int[0];

  for (int i = 1; i <= 6; i++) {
    if ( 60*60*hour() + 60*minute() + second() - userLastTimeSeen[i-1] < 0.2) {   
      userList = append(userList, i);
    }
  }
}

void onVisibleUser(SimpleOpenNI curContext, int userId) {
  userLastTimeSeen[userId-1] = 60*60*hour() + 60*minute() + second();
}

void onLostUser(SimpleOpenNI curContext, int userId) {
  userCount -= 1;
  
  if (userId == controlUserId) {
    controlUserId = userList[0];
  }
}


float detect_hand_x_position() {
  return xMapped;
}
float detect_hand_y_position() {
  return yMapped;
}




void canVas2() {
  
    if (alreadyRunInitialisationCodeInCurrentCanvas == 0) {     

    // run the following canvas-initialisation code only //
    // once per each time of entering this canvas        //
    alreadyRunInitialisationCodeInCurrentCanvas = 1;
  }
  noStroke();
  String heighest_score [] = loadStrings("score.txt");
  int savedScore = int(heighest_score[0]);  //  image(happyness_spinner_image, len-10, 75 );

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
  textt("SCORE : "+score, pad, menuHeight_margin+30, color(255), calendar_font, pad/1.9, LEFT);
  textt("kWh (from first day to yesterday of this month)", width/2, menuHeight_margin+30, color(255), calendar_font, pad/1.9, CENTER);
  textt("BEST : "+savedScore, width - pad, menuHeight_margin+30, color(255), calendar_font, pad/1.9, RIGHT);
  noFill();

  if (dead>0) {
    fill(200);
    cak_icondisp_gameover(); //cak3: displaying all boards in grey//
    cak_icondisp_background(max_t);

    fill(51, 102, 204);
    rect(pad+60, menuHeight_margin+(2*pad)+bs-30, (3*bs)+(2*pad)+40, bs, 15);
    textt("GAME OVER", ((pad+60)+((3*bs)+(2*pad)+110))/2, menuHeight_margin+(3*pad)+bs, color(255), fetch_value_font, 60, CENTER);
    textt("Hold on an \"Energy Game\" icon  3 seconds to restart", ((pad+60)+((3*bs)+(2*pad)+110))/2, menuHeight_margin+(3*pad)+bs+70, color(255), calendar_font, pad/1.9, CENTER);


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
    ellipse(int(hand_x_position), int(hand_y_position), 20, 20);
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
  ellipse(int(hand_x_position), int(hand_y_position), 20, 20);
}



void textt(String t, float x, float y, color c, PFont ff, float s, int align) {
  fill(c); 
  textAlign(align); 
  textFont(ff, s);
  text(t, x, y);
}

void restart() {
  b = new int[cak_numrows][cak_numrows];
  spawn();
  score = dead = 0;
}

void spawn() {
  ArrayList<Integer> xs = new ArrayList<Integer>(), ys = new ArrayList<Integer>();
  for (int j = 0; j < b.length; j++) for (int i = 0; i < b[j].length; i++) if (b[j][i]==0) {
    xs.add(i);
    ys.add(j);
  }
  int rnd = (int)random(0, xs.size()), y = ys.get(rnd), x = xs.get(rnd);
  b[y][x] = random(0, 1) < .9 ? cak_basis : 2*cak_basis;
}

void cak_icondisp(int t, float x, float y, int s) {

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

void cak_icondisp_background(int max_t) {
  // display icon pyramid on the right margin of game board up to board numbered max_t

  if (max_t >= 1) { 
    image(icon_Image[located[0]], cak_x_icon, cak_y_icon1, icon_size, icon_size);
    textt(Building_name[located[0]], cak_x_icon+100, cak_y_icon1+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon1+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[0]])), cak_x_icon+530, cak_y_icon1+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 2) {
    image(icon_Image[located[1]], cak_x_icon, cak_y_icon2, icon_size, icon_size);
    textt(Building_name[located[1]], cak_x_icon+100, cak_y_icon2+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon2+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[1]])), cak_x_icon+530, cak_y_icon2+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 4) { 
    image(icon_Image[located[2]], cak_x_icon, cak_y_icon4, icon_size, icon_size);
    textt(Building_name[located[2]], cak_x_icon+100, cak_y_icon4+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon4+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[2]])), cak_x_icon+530, cak_y_icon4+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 8) { 
    image(icon_Image[located[3]], cak_x_icon, cak_y_icon8, icon_size, icon_size);
    textt(Building_name[located[3]], cak_x_icon+100, cak_y_icon8+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon8+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[3]])), cak_x_icon+530, cak_y_icon8+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 16) { 
    image(icon_Image[located[4]], cak_x_icon, cak_y_icon16, icon_size, icon_size);
    textt(Building_name[located[4]], cak_x_icon+100, cak_y_icon16+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon16+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[4]])), cak_x_icon+530, cak_y_icon16+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 32) { 
    image(icon_Image[located[5]], cak_x_icon, cak_y_icon32, icon_size, icon_size);
    textt(Building_name[located[5]], cak_x_icon+100, cak_y_icon32+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon32+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[5]])), cak_x_icon+530, cak_y_icon32+65, color(102), fetch_value_font, 60, RIGHT);
  }

  if (max_t >= 64) { 
    image(icon_Image[located[6]], cak_x_icon, cak_y_icon64, icon_size, icon_size);
    textt(Building_name[located[6]], cak_x_icon+100, cak_y_icon64+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon64+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[6]])), cak_x_icon+530, cak_y_icon64+65, color(102), fetch_value_font, 60, RIGHT);
  }
  if (max_t >= 128) { 
    image(icon_Image[located[7]], cak_x_icon, cak_y_icon128, icon_size, icon_size);
    textt(Building_name[located[7]], cak_x_icon+100, cak_y_icon128+65, color(160), fetch_font, 40, LEFT);
    textt("kWh", cak_x_icon+550, cak_y_icon128+65, color(160), fetch_font, 40, LEFT);
    textt(str(int(ValueGame[located[7]])), cak_x_icon+530, cak_y_icon128+65, color(102), fetch_value_font, 60, RIGHT);
  }
}

void cak_icondisp_gameover() {
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
  text(int(ValueGame[located[0]]), cak_x_icon+530, cak_y_icon1+65);
  text(int(ValueGame[located[1]]), cak_x_icon+530, cak_y_icon2+65);
  text(int(ValueGame[located[2]]), cak_x_icon+530, cak_y_icon4+65);
  text(int(ValueGame[located[3]]), cak_x_icon+530, cak_y_icon8+65);
  text(int(ValueGame[located[4]]), cak_x_icon+530, cak_y_icon16+65);
  text(int(ValueGame[located[5]]), cak_x_icon+530, cak_y_icon32+65);
  text(int(ValueGame[located[6]]), cak_x_icon+530, cak_y_icon64+65);
  text(int(ValueGame[located[7]]), cak_x_icon+530, cak_y_icon128+65);

  filter(GRAY);
}

boolean gameover() {
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

int[][] go(int dy, int dx, boolean updatescore) {
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


void checkMouseCanVas2(int hand_x_position, int hand_y_position) {
  mouseX = hand_x_position ;
  mouseY = hand_y_position ;

  if (dead == 0) {
    preecha_previousMouseX = lerp(preecha_previousMouseX, mouseX, 0.09);
    preecha_previousMouseY = lerp(preecha_previousMouseY, mouseY, 0.09);

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


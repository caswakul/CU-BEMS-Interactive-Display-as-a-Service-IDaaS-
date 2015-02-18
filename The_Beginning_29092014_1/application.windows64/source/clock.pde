void clock(int clock_xPosition, int clock_yPosition, int calendar_xPosition, int calendar_yPosition ) {
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

int checkFromcalendar(int d, int m, int y) {
  if (m < 3) {
    m += 12;
    y--;
  }
  return (d + int((m+1)*2.6) +  y + int(y/4) + 6*int(y/100) + int(y/400) + 6) % 7;
}


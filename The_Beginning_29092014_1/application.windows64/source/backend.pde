//----------------------------------------------------------------------------------------------------------
//-------------------------------          IEEE 1888 FETCH           ---------------------------------------
//----------------------------------------------------------------------------------------------------------

void ieee1888_fetch() {
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
    Value.add(float(fetch_value[1]));

  }
}

//----------------------------------------------------------------------------------------------------------
//---------------------------    IEEE 1888 FETCH FOR ENERGY GAME && ALARM   --------------------------------
//----------------------------------------------------------------------------------------------------------
void ieee1888_fetch_Game() {

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

    ValueGame[i] = float(fetchGame_value[1]);
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

void ieee1888_write() {

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







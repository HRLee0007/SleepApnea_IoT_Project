#include <Wire.h>
#include <SoftwareSerial.h>


String testC;
SoftwareSerial esp(2, 3);  //TX,RX
String SSID = "KDY"; //wifi ssid

String PASSWORD = "2018111658"; //wifi password

String username = "gusfh"; // user id


#define analogPinForRV    1   // change to pins you the analog pins are using
#define analogPinForTMP   0


int no_breath_time = 0; // 무호흡 감지할 때마다 + 1,  값 1 -- > 0.1초
int no_breath_count = 0; // 수면시간동안의 총 무호흡 횟수
float wind_no_breath_value = 4.8; // 무호흡이라고 판단할 기준 값
int first_no_breath = 0; // 무호흡 총 횟수 측정을 위한 첫 무호흡 여부확인.


//바람 센서 
int windValue[200] = {0,};
const float zeroWindAdjustment =  .2; // negative numbers yield smaller wind speeds and vice versa.
int TMP_Therm_ADunits;  //temp termistor value from wind sensor
float RV_Wind_ADunits;  //RV output from wind sensor
float RV_Wind_Volts;
unsigned long lastMillis;
unsigned long httpMillis = 0;
int TempCtimes100;
float zeroWind_ADunits;
float zeroWind_volts;
float WindSpeed_MPH;



//장력 센서 초기화
float rubberValue[200] = {0,};
float rubber_no_breath_value = 999;

void connectWifi() {

  String cmd = "AT+CWMODE=3";

  esp.println(cmd);
  
  cmd = "AT+CWJAP=\"" + SSID + "\",\"" + PASSWORD + "\"";

  while(1){

    esp.println(cmd);


    if (esp.find("OK")) {

      Serial.println("Wifi connected");
      break;

    } else {

      Serial.println(" Cannot connect to Wifi" + esp);
    }
  }
}

void httpGet(String server, String uri) {

  String connect_server_cmd = "AT+CIPSTART=\"TCP\",\"" + server + "\",8080";

  esp.print("AT+CIPSTART=\"TCP\",\"52.79.222.91\",8080");
  esp.println();


  String httpCmd = "GET " + uri;

  int length = httpCmd.length() + 2;
  String cmd = "AT+CIPSEND="+String(length);

  esp.print(cmd);
  esp.println();

  esp.print(httpCmd);
  esp.println();
  
}

float rubberMax = -999;
float rubberMin = 999;


void setup() {

 
 // wifi 연결;
  esp.begin(9600);

  Serial.begin(9600);

  connectWifi();



  for(int k = 0; k < 200; k++){ // 장력 센서 초기 값 ( 20초 )
    rubberValue[k] = analogRead(A5);

    
    Serial.println(rubberValue[k]);

    // TMP_Therm_ADunits = analogRead(analogPinForTMP);
    // RV_Wind_ADunits = analogRead(analogPinForRV);
    // RV_Wind_Volts = (RV_Wind_ADunits *  0.0048828125);


    // TempCtimes100 = (0.005 *((float)TMP_Therm_ADunits * (float)TMP_Therm_ADunits)) - (16.862 * (float)TMP_Therm_ADunits) + 9075.4;  
    // zeroWind_ADunits = -0.0006*((float)TMP_Therm_ADunits * (float)TMP_Therm_ADunits) + 1.0727 * (float)TMP_Therm_ADunits + 47.172;  //  13.0C  553  482.39
    // zeroWind_volts = (zeroWind_ADunits * 0.0048828125) - zeroWindAdjustment;
 
    // windValue[i] =  pow(((RV_Wind_Volts - zeroWind_volts) /.2300) , 2.7265);   
    delay(100);
  }
  
  // int windMax = -999;
  // int windMin = 999;

  for(int i = 0; i< 200; i++){
    if(rubberValue[i] > rubberMax) rubberMax = rubberValue[i];
    if(rubberValue[i] < rubberMin) rubberMin = rubberValue[i];
    // if(windValue[i] > windMax) windMax = windValue[i];
    // if(windValue[i] < windMin) windMin = windValue[i];

  }


  rubber_no_breath_value = (rubberMax - rubberMin) * (4/10);
  // wind_no_breath_value = (windMax - windMin) * (4/10);

  Serial.print("Serial start");

}

char result;
char sign_result;
unsigned long now_time;
float rubber10Max = -999;
float rubber10Min = 999;

float rubber15Max = -999;
float rubber15Min = 999;

float rubber20Max = -999;
float rubber20Min = 999;

float wind10Max = -999;
int wind10Min = 999;

int wind15Max = -999;
int wind15Min = 999;

int wind20Max = -999;
int wind20Min = 999;

int vibrateWARNING = 0;
int soundWARNING = 0;
int familyWARNING = 0;
int i;

int loop_first = 1;

void loop() {


  now_time = millis();
 if (now_time - httpMillis > 20000){
   httpMillis = millis();      // check status every 20000 ms
  while(1){
      Serial.print(".");
      
      httpGet("52.79.222.91","/api/v1/user?username=" + username );
      if(esp.find("+IPD,1:")){

      testC= esp.readString();
      result = testC.charAt(0);
      // Serial.println(testC);

      break;

      }
    }
 }

  if (now_time - lastMillis > 100){

    lastMillis = millis();
    

    if (result == '2') { // waiting start sign from server

    while(1){
        httpGet("52.79.222.91","/api/v1/userSign?sign=1&username="+ username);
          if(esp.find("+IPD,1:")){

          testC= esp.readString();
          sign_result = testC.charAt(0);
          // for(int j = 0; j < 10; j ++)
          // Serial.print(sign_result);

          break;

          }
      }      

      Serial.println("Waiting....");
      httpMillis = -99999;
      }
    else if (result == '1') { // start

    //  Serial.println("START....");
     // read every 100 ms - printing slows this down further
    

    // TMP_Therm_ADunits = analogRead(analogPinForTMP);
    // RV_Wind_ADunits = analogRead(analogPinForRV);
    // RV_Wind_Volts = (RV_Wind_ADunits *  0.0048828125);


    // TempCtimes100 = (0.005 *((float)TMP_Therm_ADunits * (float)TMP_Therm_ADunits)) - (16.862 * (float)TMP_Therm_ADunits) + 9075.4;  
    // zeroWind_ADunits = -0.0006*((float)TMP_Therm_ADunits * (float)TMP_Therm_ADunits) + 1.0727 * (float)TMP_Therm_ADunits + 47.172;  //  13.0C  553  482.39
    // zeroWind_volts = (zeroWind_ADunits * 0.0048828125) - zeroWindAdjustment;
 
 

    

    // WindSpeed_MPH =  pow(((RV_Wind_Volts - zeroWind_volts) /.2300) , 2.7265);   

    // Serial.print("   WindSpeed MPH ");
    // Serial.println((float)WindSpeed_MPH);

      // if(WindSpeed_MPH < wind_no_breath_value){
      //   no_breath_time++; // 무호흡 지속시간 갱신
      // }
      // else no_breath_time = 0;                              // 호흡 발견 시 무호흡 지속시간 0으로 초기화.



      for(int i = 0; i < 200; i++){ // 
        rubberValue[i] = rubberValue[i+1];
        // windValue[i] = windValue[i-1];
      }

      rubberValue[199] = analogRead(A5); //  장력센서 최근 값 갱신
      // windValue[0] = WindSpeed_MPH;

      // Serial.println("Time now : " + millis());

    // Serial.print("rubberValue = ");
    // Serial.println(rubberValue[0]);



    

    for(i = 100; i < 200; i++){
      if(rubberValue[i] > rubber10Max) rubber10Max = rubberValue[i];
      if(rubberValue[i] < rubber10Min) rubber10Min = rubberValue[i];

      // if(windValue[i] > wind10Max) wind10Max = windValue[i];
      // if(windValue[i] < wind10Min) wind10Min = windValue[i];

    }

    for(i = 50; i < 200; i++){
      if(rubberValue[i] > rubber15Max) rubber15Max = rubberValue[i];
      if(rubberValue[i] < rubber15Min) rubber15Min = rubberValue[i];

      // if(windValue[i] > wind15Max) wind15Max = windValue[i];
      // if(windValue[i] < wind15Min) wind15Min = windValue[i];
    }

    for(i = 0; i < 200; i++){
      if(rubberValue[i] > rubber20Max) rubber20Max = rubberValue[i];
      if(rubberValue[i] < rubber20Min) rubber20Min = rubberValue[i];

      // if(windValue[i] > wind20Max) wind20Max = windValue[i];
      // if(windValue[i] < wind20Min) wind20Min = windValue[i];
    }





    if(rubber10Max - rubber10Min < rubber_no_breath_value) vibrateWARNING = 1;
    // else if(wind10Max - wind10Min < wind_no_breath_value) vibrateWARNING = 1;
    else vibrateWARNING = 0;

    if(rubber15Max - rubber15Min < rubber_no_breath_value) soundWARNING = 1;
    // else if(wind15Max - wind15Min < wind_no_breath_value) soundWARNING = 1;
    else soundWARNING = 0;

    if(rubber20Max - rubber20Min < rubber_no_breath_value) familyWARNING = 1;
    // else if(wind20Max - wind20Min < wind_no_breath_value) familyWARNING = 1;
    else familyWARNING = 0;

    Serial.println("---------------");

Serial.println(rubber10Max);
Serial.println(rubber10Min);
Serial.println(rubber_no_breath_value);
Serial.println("---------------");
Serial.println(vibrateWARNING);
Serial.println("---------------");


////////////////////
    if (vibrateWARNING == 1){ 
      
      // 무호흡 10초 이상 OR 장력센서 무반응 10초 이상 ==> 진동 ON.
        if(first_no_breath == 0){
          first_no_breath = 1;
          no_breath_count++;
        }
        while(1){
        httpGet("52.79.222.91","/api/v1/userSign?sign=1&username="+ username);
          if(esp.find("+IPD,1:")){

          testC= esp.readString();
          sign_result = testC.charAt(0);
          // for(int j = 0; j < 10; j ++)
          // Serial.print(sign_result);

          break;

          }
        }
        // Serial.println("VIBRATE ON");
      
    }
    else if(first_no_breath != 0){
      while(1){
        httpGet("52.79.222.91","/api/v1/userSign?sign=0&username="+ username);
          if(esp.find("+IPD,1:")){

          testC= esp.readString();
          sign_result = testC.charAt(0);
          // for(int j = 0; j < 10; j ++)
          // Serial.print(sign_result);

          break;

          }
      }      
      first_no_breath = 0;
    }

    if(soundWARNING == 1){ // 무호흡 15초 이상 OR 장력센서 무반응 15초 이상 ==> 소리 ON. (진동은 OFF)
    // if(no_breath_time > 150){ 
    
      //  Serial.println("SOUND ON");
      while(1){
        httpGet("52.79.222.91","/api/v1/userSign?sign=2&username="+ username);
        if(esp.find("+IPD,1:")){

        testC= esp.readString();
        sign_result = testC.charAt(0);
        // for(int j = 0; j < 10; j ++)
        // Serial.print(sign_result);

        break;
        }
      }
    }
    else{ // 15초 사이에 호흡 발생 시 소리 OFF

    }

    if(familyWARNING == 1){// 무호흡 20초 이상 OR 장력센서 무반응 20초 이상
    // if(no_breath_time > 200){ 
      // 보호자 문자 알림 서비스;
          while(1){
            httpGet("52.79.222.91","/api/v1/userSign?sign=3&username="+ username);
            if(esp.find("+IPD,1:")){

            testC= esp.readString();
            sign_result = testC.charAt(0);
            // for(int j = 0; j < 10; j ++)
            // Serial.print(sign_result);

            break;

            }
          }
      
      // Serial.println("WARNING!!! FAMILY_CALL");
    }

 

 }
  




  else if (result == '0') { // end

      Serial.println("FINISH....");


      while(1){
          // Serial.print(";");
          httpGet("52.79.222.91","/api/v1/infoSave?count=" + String(no_breath_count) + "&username="+ username);
          // Serial.print("#");
          if(esp.find("+IPD,1:")){

            testC= esp.readString();  
            sign_result = testC.charAt(0);
            // Serial.println("result = " + sign_result);

            if(sign_result == '1'){
              Serial.println("SUCCESSFUL SEND");

              Serial.println("Total no_breath_count = " + no_breath_count);
              Serial.println("Program exit");

              break;
            }
          } 
        }
      //총 무호흡 횟수 http.begin(~~~~);
      Serial.println("EXIT");
      exit(0);

      }
  }
  
}
 

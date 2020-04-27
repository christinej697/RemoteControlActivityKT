//BLUETOOTH PASSWORD EITHER 1234 or maybe 0000

#include <Servo.h>
#include <SoftwareSerial.h>
#include <DualTB9051FTGMotorShield.h>

DualTB9051FTGMotorShield md;

SoftwareSerial MyBlue(0,1); //RX | TX
String input = "";
int speed = 0;

void stopIfFault()
{
  if (md.getM1Fault())
  {
    Serial.println("M1 fault");
    while (1);
  }
  if (md.getM2Fault())
  {
    Serial.println("M2 fault");
    while (1);
  }
}

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println("Dual TB9051FTG Motor Shield");
  md.init();

  //Open serial communications to bluetooth
  MyBlue.begin(9600);
}


void loop() {
  // put your main code here, to run repeatedly:
  md.enableDrivers();
  delay(1);  //wait for drivers to be enabled so fault pins are no longer low
  Serial.println("begin loop");
  MyBlue.println("bluetooth: begin loop")
  if(M 

  
  if(MyBlue.available()){
    String inChar = MyBlue.readString();
    Serial.println(inChar);
    //if(isDigit(inChar)){
    //  input += (char)inChar;
    //}
    MyBlue.println(input);

    if(input == "F"){            //move forward(all motors rotate in forward direction)
      Serial.println("Going FORWARD");
      md.setM1Speed(200);
      if (speed <= 400){
        speed = speed + 50;
        md.setM1Speed(speed);
      }
      else{
        Serial.println("AT MAX SPEED");
      }
      delay(2);
    }
     
    else if(input == "B"){      //move reverse (all motors rotate in reverse direction)
      Serial.println("Going BACKWARD");
      if (speed >= -400){
        speed = speed - 10;
        md.setM1Speed(speed);
      }
      else{
        Serial.println("AT MAX SPEED");
      }
      delay(2);
    }
     
    else if(input == "R"){      //turn right (left side motors rotate in forward direction, right side motors doesn't rotate)
      Serial.println("Turning RIGHT");
    }
     
    else if(input == "L"){      //turn left (right side motors rotate in forward direction, left side motors doesn't rotate)
      Serial.println("Turning LEFT");
    }
    
    else if(input == "S"){      //STOP (all motors stop)
      Serial.println("It's time to STOP");
      while (speed != 0){
        if (speed <= 400){
          speed = speed + 10;
          md.setM1Speed(speed);
        }
        else if (speed >= -400){
          speed = speed - 10;
          md.setM1Speed(speed);
        }
        else{
          Serial.println("AT MAX SPEED");
        }
      }
    input = "";
    delay(10);
    }
  }
  delay(100);
}

//------------------------------------------------------//
//--Functions----------------------------/

void dejavu()
{
    tone(3,261,162.5);
delay(162.5);
tone(3,293,162.5);
delay(162.5);
//
tone(3,311,387.5); 
delay(387.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,293,162.5); 
delay(162.5);
tone(3,261,162.5); 
delay(162.5);
tone(3,233,162.5); 
delay(162.5);
//
tone(3,233,325); 
delay(325);
tone(3,261,162.5); 
delay(162.5);
tone(3,261,325); 
delay(325);
tone(3,392,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,293,162.5); 
delay(162.5);
//
tone(3,311,325); 
delay(325);
tone(3,311,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
//
tone(3,339,325); 
delay(325);
tone(3,392,162.5); 
delay(162.5);
tone(3,392,325); 
delay(325);
delay(162.5);
tone(3,261,162.5); 
delay(162.5);
tone(3,261,162.5); 
delay(162.5);
tone(3,293,162.5); 
delay(162.5);
//
tone(3,311,325); 
delay(325);
tone(3,311,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,293,162.5); 
delay(162.5);
tone(3,261,162.5); 
delay(162.5);
tone(3,233,162.5); 
delay(162.5);
//
tone(3,233,325); 
delay(325);
tone(3,261,162.5); 
delay(162.5);
tone(3,261,325); 
delay(325);
tone(3,392,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,293,162.5); 
delay(162.5);
//
tone(3,311,325); 
delay(325);
tone(3,311,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
tone(3,311,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
//
tone(3,339,325); 
delay(325);
tone(3,392,162.5); 
delay(162.5);
tone(3,392,162.5); 
delay(162.5);
tone(3,339,162.5); 
delay(162.5);
//delay(387.5);
tone(3,392,1625); 
delay(1625);
}

package com.example.keerbot;

import ioio.lib.api.exception.ConnectionLostException;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Path;
import android.util.Log;


public class KeerBot {
	private static final String TAG = "KeerBot";
	
	private StepperMotor leftMotor;
	private StepperMotor rightMotor;
	
	private float topLengthMeter;	
	private PointF wireLengthsMeter;
	
	private double meterPerStep;
	private double maxStepsPerSeconds;
	
	 public KeerBot(StepperMotor left,StepperMotor right,double leftWireLength,double rightWireLength,double newTopLength,double new_meterPerStep,double new_maxStepsPerSeconds) {
		 leftMotor = left;
		 rightMotor = right;
		 
		 wireLengthsMeter = new PointF((float)leftWireLength,(float)rightWireLength);		 
		 topLengthMeter = (float)newTopLength; //length of upper edge, going horizontally from left pivot point to the right pivot point
		 
		 meterPerStep = new_meterPerStep;
		 maxStepsPerSeconds = new_maxStepsPerSeconds;
	 }
	 
	 public void stop() {		 
		 leftMotor.stop();
		 rightMotor.stop();
	 }	
	 
	 public void gotoxy(PointF destination) {
		 
		 //PointF destination = new PointF(P.x,P.y); was needed when had origin
		 
		 if ((destination.x < 0.0) || (destination.x > topLengthMeter)) {
			 return;
		 }
		 
		 if ((destination.y < 0.0) || (destination.y > 300)) {
			 return;
		 }
		 		 
		 PointF newWireLengths = gotoxy(destination,topLengthMeter);
		 
		 // Calculate how long will take the motors to move to the new location
		 
		 double d1 = newWireLengths.x - wireLengthsMeter.x;
		 double d2 = newWireLengths.y - wireLengthsMeter.y;
		 
		 double maxLengthMeter = Math.max(Math.abs(d1),Math.abs(d2));		 		 
		 
		 double maxTimeSec = maxLengthMeter/(meterPerStep*maxStepsPerSeconds); // meter*(steps/meter)/(steps/time) == time
		 
		 double leftMotorSpeed = (d1/maxTimeSec)/meterPerStep;
		 double rightMotorSpeed = (d2/maxTimeSec)/meterPerStep;
		 
		 // Activate the motors with speeds that will bring them to the destination
		 // at the same time, moving at top speed
		 // TBD : add more clever methods for smoother movement (trapezoid/s interpolation)
		 
		 try {
			leftMotor.setSpeed((int)Math.round(leftMotorSpeed));
			rightMotor.setSpeed((int)Math.round(rightMotorSpeed));
		} catch (ConnectionLostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}	
					 
		 
		 // Sleep for the duration of the expected movements
		 
		 try {			 
			 double maxTimeMSec = maxTimeSec*1000.0;
			Thread.sleep((long)maxTimeMSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 
		 stop();
		  
		 wireLengthsMeter = newWireLengths;
		
	 }	 
	 
	 
	 public void up(double stepSize) {
		 Log.v("UP","start");
		 PointF currentLocation = getxy();
		 gotoxy(new PointF(currentLocation.x,currentLocation.y-(float)stepSize));
	 }
	 
	 public void down(double stepSize) {
		 PointF currentLocation = getxy();
		 gotoxy(new PointF(currentLocation.x,currentLocation.y+(float)stepSize));	 
	 }
	 
	 public void left(double stepSize) {
		 PointF currentLocation = getxy();
		 gotoxy(new PointF(currentLocation.x-(float)stepSize,currentLocation.y));		 
	 }
	 
	 public void right (double stepSize) {
		 PointF currentLocation = getxy();
		 gotoxy(new PointF(currentLocation.x+(float)stepSize,currentLocation.y));		 
	 }	  	
	 
	 public void makePath(Path path) {
		 
			PathMeasure pm = new PathMeasure(path,false);
			
			float point[] = {0f,0f};
			
			for (float alpha = (float) 0.0; alpha < pm.getLength(); alpha += 0.01) {				
				pm.getPosTan(pm.getLength() * alpha, point, null);
				PointF newP = new PointF(point[0],point[1]);
				Log.i(TAG,Double.valueOf(point[0]).toString()+"," + Double.valueOf(point[1]).toString());
				gotoxy(newP);
			}
				
	 }
	 
	 public PointF getxy() {
		 PointF tmp = getxy(wireLengthsMeter,topLengthMeter);		 
		 //return new PointF(tmp.x - origin.x,tmp.y - origin.y);
		 return new PointF(tmp.x,tmp.y);
	 }

	 public void setLeftWireLength(double length) {
		 wireLengthsMeter.x = (float)length;		
		 //origin = getxy(wireLengthsMeter,topLengthMeter);
	 }
	 
	 public void setRightWireLength(double length) {
		 wireLengthsMeter.y = (float)length;
		 //origin = getxy(wireLengthsMeter,topLengthMeter);
	 }

	 public void setTopWireLength(double length) {
		 topLengthMeter = (float)length;
		 //origin = getxy(wireLengthsMeter,topLengthMeter);
	 }
	 
	 public void setMeterPerStep(double value) {
		 meterPerStep = (float)value;
	 }
	 
	 public void setMaxSpeed(double value) {
		 maxStepsPerSeconds = value;
	 }
	 
	 private PointF getxy(PointF L,float c)
	 {		 		 		 
		 float x = (L.x*L.x + c*c - L.y*L.y)/((float)2.0*c);
		 if (Math.abs(x) > Math.abs(L.x)) {
			 return new PointF((float)-1.0,(float)-1.0);
		 }
		 
		 float y = (float)Math.sqrt((double) L.x*L.x - x*x);
		    
		 return new PointF(x,y);		 
	 }	
		
	private PointF gotoxy(PointF P,double c) {
				
		// calculate distance from left pivot point
		float l1 = (float)Math.sqrt(P.x*P.x + P.y*P.y);
		
		// calculate distance from right pivot point
		float l2 = (float)Math.sqrt((P.x-c)*(P.x-c) + P.y*P.y);
		
		return new PointF(l1,l2);			
	}
}

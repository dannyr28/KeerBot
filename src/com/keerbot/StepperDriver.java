package com.keerbot;

//import android.util.Log;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;


//import ioio.lib.api.IOIO;


public class StepperDriver {
	
	//private static final String TAG = "StepperDriver";
	
	private final IOIO ioio_;
	private final int dirPin_;
    private final int stpPin_;
    
    private DigitalOutput dir_;
    private PwmOutput stp_;
    
    private float dutyCycle_;
    
    
    public StepperDriver(IOIO ioio,int dirPin, int stpPin) { 
    	//Log.i(TAG,"Stepper Driver :" + dirPin + stpPin);
    	ioio_ = ioio;
    	
    	dirPin_ = dirPin;
    	stpPin_ = stpPin; 
    	
    	dir_ = null;
    	stp_ = null;  
    	
    	dutyCycle_ = (float)0.5;
    }
    
    public void setSpeed(int stepsPerSecond) throws ConnectionLostException {
    	if (ioio_ == null) { return; }
    	
    	if (stepsPerSecond == 0) { return;}
    	
    	// open the direction pin with value true or false according to the sign of the speed 
    	dir_ = ioio_.openDigitalOutput(dirPin_,stepsPerSecond > 0);

    	// open the step pin with the frequency set to the speed value
    	//stp_ = ioio_.openPwmOutput(new DigitalOutput.Spec(stpPin_, Mode.OPEN_DRAIN), Math.abs(stepsPerSecond));
    	stp_ = ioio_.openPwmOutput(stpPin_, Math.abs(stepsPerSecond));
    	stp_.setDutyCycle(dutyCycle_);    	    	    	
    }             
    
    public void stop()
    {
    	if (ioio_ == null) {
    		
    		return; 
    	}
    	
    	if (dir_ != null) {
    		dir_.close();    
    		dir_ = null;
    	}
    	
    	if (stp_ != null) {
    		stp_.close();
    		stp_ = null;
    	}    	
    }
    
    public void setDutyCycle(double duty) {
    	dutyCycle_ = (float)duty;
    }          

}

package com.keerbot;

//import android.util.Log;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.DigitalOutput.Spec.Mode;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;


//import ioio.lib.api.IOIO;


public class StepperMotor {
	
	//private static final String TAG = "StepperMotor";
	
	private final IOIO ioio_;
	private final int dirPin_;
    private final int stpPin_;
    
    
    private DigitalOutput dir_;
    private PwmOutput stp_;
    private boolean openDrain_;
    
    private float dutyCycle_; 
   
    public StepperMotor(IOIO ioio,int dirPin, int stpPin,boolean openDrain) { 
    	//Log.i(TAG,"Stepper Motor :" + dirPin + stpPin);
    	ioio_ = ioio;
    	
    	dirPin_ = dirPin;
    	stpPin_ = stpPin;
    	
    	openDrain_ = openDrain;
    	dir_ = null;
    	stp_ = null;  
    	
    	dutyCycle_ = (float)0.5;
    }

    public StepperMotor(IOIO ioio,int dirPin, int stpPin) {
    	this(ioio,dirPin,stpPin,false);
    }
    
    public void setOpenDrain(boolean b) {
    	openDrain_ = b;
    }
    
    public void setSpeed(int stepsPerSecond) throws ConnectionLostException {
    	if (ioio_ == null) { return; }
    	
    	if (stepsPerSecond == 0) {
    		return;
    	}
    	

    	try {
    		// open the direction pin with value true or false according to the sign of the speed 
    		dir_ = ioio_.openDigitalOutput(dirPin_,stepsPerSecond > 0);

    		// open the step pin with the frequency set to the speed value
    		if (openDrain_) {
    			stp_ = ioio_.openPwmOutput(new DigitalOutput.Spec(stpPin_, Mode.OPEN_DRAIN), Math.abs(stepsPerSecond));
    		} else {
    			stp_ = ioio_.openPwmOutput(stpPin_,Math.abs(stepsPerSecond));
    		}

    		stp_.setPulseWidth(10);
    		stp_.setDutyCycle(dutyCycle_);
    	} catch (ConnectionLostException e) {	
    		stop();
    		throw new ConnectionLostException(e);
    	} 
    			
    }             
    
    public void stop()
    {
    	
    	if (ioio_ == null) { return; }
    	
    	try {
    	if (dir_ != null) {
    		dir_.close();   
    		dir_ = null;
    	}
    	
    	if (stp_ != null) {
    		stp_.close();
    		stp_ = null;
    	}    
    	} catch (Exception e) {
    		return;
    	}
    }
    
    public void setDutyCycle(double duty) {
    	if (duty == 0) {
    		return;    				
    	}
    		
    	dutyCycle_ = (float)duty;
    }          

}

package com.keerbot;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.keerbot.R;


/**
 * This is the main activity of the HelloIOIO example application.
 * 
 * It displays a toggle button on the screen, which enables control of the
 * on-board LED. This example shows a very simple usage of the IOIO, by using
 * the {@link IOIOActivity} class. For a more advanced use case, see the
 * HelloIOIOPower example.
 */
public class MainActivity extends IOIOActivity {
	//private static final String TAG = "MainActivity";

	private ToggleButton ledButton;

	private Button leftMotorUp;
	private Button leftMotorDown;
	private Button rightMotorUp;
	private Button rightMotorDown;


	private SeekBar leftSpeed;
	private SeekBar rightSpeed;
	private SeekBar leftDutyCycle;
	private SeekBar rightDutyCycle;	

	private TextView statusView;	

	/*
	private Button Up;
	private Button Down;
	private Button Right;
	private Button Left;

	private Button drawShape;

	double stepSizeMeter;

	private SeekBar topWireBar;
	private SeekBar leftWireBar;
	private SeekBar rightWireBar;

	private TextView topWireText;
	private TextView leftWireText;
	private TextView rightWireText;

	public Switch openDrain;

	private Path path;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);	    
	    return super.onCreateOptionsMenu(menu);

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			openSettings();			
		    Intent intent = new Intent(this, SettingsActivity.class);
		    startActivity(intent);
		} else  {			
			return super.onOptionsItemSelected(item);
		}
		return true;
	}




	public boolean openSettings() {
		statusView.setText("Settings");
		return true;
	}

	 */


	/**
	 * Called when the activity is first created. Here we normally initialize
	 * our GUI.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		// Create a straight line

		ledButton = (ToggleButton) findViewById(R.id.ledButton);
		/*
		path = new Path();
		path.moveTo((float)0.10, (float)0.70);

		path.lineTo((float)0.20,(float) 0.70);
		path.lineTo((float)0.20,(float) 0.90);
		path.lineTo((float)0.10,(float) 0.90);
		path.lineTo((float)0.10,(float) 0.70);

		//SVG svg = SVGParser.getSVGFromResource(getResources(), R.drawable.spiral);
		//svg.

		 */

		statusView = (TextView) findViewById(R.id.statusView);	


		leftMotorUp = (Button) findViewById(R.id.leftUp);
		leftMotorDown = (Button) findViewById(R.id.leftDown);
		rightMotorUp = (Button) findViewById(R.id.rightUp);
		rightMotorDown = (Button) findViewById(R.id.rightDown);


		leftSpeed = (SeekBar) findViewById(R.id.leftSpeed); 
		rightSpeed = (SeekBar) findViewById(R.id.rightSpeed); 
		leftDutyCycle = (SeekBar) findViewById(R.id.leftDutyCycle);
		rightDutyCycle = (SeekBar) findViewById(R.id.rightDutyCycle);

		/*
		Up = (Button) findViewById(R.id.up);
		Down = (Button) findViewById(R.id.down);
		Left = (Button) findViewById(R.id.left);
		Right = (Button) findViewById(R.id.right);
		stepSizeMeter = 0.01; // 1 cm

		topWireBar  = (SeekBar) findViewById(R.id.TopWireBar);
		leftWireBar = (SeekBar) findViewById(R.id.LeftWireBar);
		rightWireBar = (SeekBar) findViewById(R.id.RightWireBar);

		topWireText = (TextView) findViewById(R.id.TopWireText);
		leftWireText = (TextView) findViewById(R.id.LeftWireText);
		rightWireText = (TextView) findViewById(R.id.RightWireText);

		topWireText.setText("Top Wire " + Double.valueOf((double)topWireBar.getProgress()/100.0).toString() + "m");
		leftWireText.setText("Left Wire " + Double.valueOf((double)leftWireBar.getProgress()/100.0).toString() + "m");
		rightWireText.setText("Right Wire " + Double.valueOf((double)rightWireBar.getProgress()/100.0).toString() + "m");

		//drawShape = (Button) findViewById(R.id.drawButton);		

		//Settings Activity

		openDrain = (Switch) findViewById(R.id.openDrain);
		//openDrain.setActivated(false);


		 */

	}

	/**
	 * This is the thread on which all the IOIO activity happens. It will be run
	 * every time the application is resumed and aborted when it is paused. The
	 * method setup() will be called right after a connection with the IOIO has
	 * been established (which might happen several times!). Then, loop() will
	 * be called repetitively until the IOIO gets disconnected.
	 */
	class Looper extends BaseIOIOLooper {

		private DigitalOutput led;

		private StepperMotor leftMotor;
		private StepperMotor rightMotor;
		/*



		private KeerBot keerBot;

		private double topWireMeter;
		private double leftWireMeter;
		private double rightWireMeter;
		private double meterPerStep;
		private double maxStepsPerSecond;

		 */
		/**
		 * Called every time a connection with IOIO has been established.
		 * Typically used to open pins.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#setup()
		 */
		@Override
		protected void setup() throws ConnectionLostException {		
			//statusView.setText("setup : Running");

			led = ioio_.openDigitalOutput(0, true);


			leftMotor  = new StepperMotor(ioio_,42,3);	
			leftMotor.setDutyCycle((float)leftDutyCycle.getProgress()/100.0);
			//leftMotor.setOpenDrain(false);

			rightMotor = new StepperMotor(ioio_,43,4);	
			rightMotor.setDutyCycle((float)rightDutyCycle.getProgress()/100.0);
			//rightMotor.setOpenDrain(false); //openDrain.isChecked()

			//topWireMeter = 0.45-0.10; //distance between the upper pivot points minus the distance between the keerbot pivot points. 
			/*
			topWireMeter = (double)topWireBar.getProgress()/100.0;  // length of top wire
			leftWireMeter = (double)leftWireBar.getProgress()/100.0; //length of left wire
			rightWireMeter = (double)rightWireBar.getProgress()/100.0; // length of right wire

			double stepsPerRevolution = 200.0;
			double spindleRadiusMeter = 0.00375;

			meterPerStep = (spindleRadiusMeter*2.0*Math.PI)/stepsPerRevolution; 
			maxStepsPerSecond = 100.0;

			keerBot = new KeerBot(leftMotor,rightMotor,leftWireMeter,rightWireMeter,topWireMeter,meterPerStep,maxStepsPerSecond);
			/*
			openDrain.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					//Log.v("Switch State=", ""+isChecked);
					leftMotor.setOpenDrain(isChecked);
					rightMotor.setOpenDrain(isChecked);
				}       

			});


			topWireBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					topWireText.setText("TopWire " + Integer.valueOf(progress).toString() + "m");
					keerBot.setTopWireLength((double)progress/100.0);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

			});

			leftWireBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					leftWireText.setText(Integer.valueOf(progress).toString());
					keerBot.setLeftWireLength((double)progress/100.0);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

			});

			rightWireBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					rightWireText.setText(Integer.valueOf(progress).toString());
					keerBot.setRightWireLength((double)progress/100.0);					
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

			});		

			Up.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("Up");
					Double step = stepSizeMeter;

					switch (event.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						statusView.setText("Up : " + step.toString());
						keerBot.up(step);
						PointF loc = keerBot.getxy();
						statusView.setText("loc : " + Double.valueOf(loc.x).toString() + "," + Double.valueOf(loc.y).toString() );
						break;						
					}			
					return false;
				}
			});	

			Down.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("Down");
					Double step = stepSizeMeter;

					switch (event.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						statusView.setText("Down : " + step.toString());
						keerBot.down(step);	
						PointF loc = keerBot.getxy();
						statusView.setText("loc : " + Double.valueOf(loc.x).toString() + "," + Double.valueOf(loc.y).toString() );
						break;						
					}			
					return false;
				}
			});	

			Left.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("Left");
					Double step = stepSizeMeter;

					switch (event.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						statusView.setText("Left : " + step.toString());
						keerBot.left(step);		
						PointF loc = keerBot.getxy();
						statusView.setText("loc : " + Double.valueOf(loc.x).toString() + "," + Double.valueOf(loc.y).toString() );
						break;						
					}			
					return false;
				}
			});		

			Right.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("Right");
					Double step = stepSizeMeter;

					switch (event.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						statusView.setText("Right : " + step.toString());
						keerBot.right(step);	
						PointF loc = keerBot.getxy();
						statusView.setText("loc : " + Double.valueOf(loc.x).toString() + "," + Double.valueOf(loc.y).toString() );
						break;						
					}			
					return false;
				}
			});	

			drawShape.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("drawShape");				

					switch (event.getAction())
					{
					case MotionEvent.ACTION_DOWN:
						statusView.setText("drawing shape : ongoing...");
						keerBot.makePath(path);	

						statusView.setText("drawing shape : done");

						break;						
					}			
					return false;
				}
			});	


			 */
			leftMotorUp.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("pressed");
					try {
						Integer speed = -Math.abs(leftSpeed.getProgress());
						switch (event.getAction())
						{
						case MotionEvent.ACTION_DOWN:
							statusView.setText("LeftMotorUp : " + speed.toString());
							leftMotor.setSpeed(speed);												
							break;						

						case MotionEvent.ACTION_UP:
							statusView.setText("LeftMotorUp : Stopped");
							leftMotor.stop();							
							break;							
						}			

					} catch (ConnectionLostException e) {	
						statusView.setText("LeftMotorUp : Exception - " + e.toString());
						e.printStackTrace();						
					}

					return false;
				}
			});

			leftMotorDown.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("pressed");
					try {
						Integer speed = Math.abs(leftSpeed.getProgress());
						switch (event.getAction())
						{
						case MotionEvent.ACTION_DOWN:
							statusView.setText("LeftMotorDown : "  + speed.toString());
							leftMotor.setSpeed(speed);												
							break;						

						case MotionEvent.ACTION_UP:
							statusView.setText("LeftMotorDown : Stopped");
							leftMotor.stop();							
							break;							
						}			

					} catch (ConnectionLostException e) {	
						statusView.setText("LeftMotorDown : Exception - " + e.toString());
						e.printStackTrace();						
					}

					return false;
				}
			});

			rightMotorUp.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("pressed");
					try {
						Integer speed = -Math.abs(rightSpeed.getProgress());
						switch (event.getAction())
						{
						case MotionEvent.ACTION_DOWN:
							statusView.setText("rightMotorUp : " + speed.toString());
							rightMotor.setSpeed(speed);												
							break;						

						case MotionEvent.ACTION_UP:
							statusView.setText("rightMotorUp : Stopped");
							rightMotor.stop();							
							break;							
						}			

					} catch (ConnectionLostException e) {	
						statusView.setText("rightMotorUp : Exception - " + e.toString());
						e.printStackTrace();
					}

					return false;
				}
			});

			rightMotorDown.setOnTouchListener(new View.OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("pressed");
					try {
						Integer speed = Math.abs(rightSpeed.getProgress());
						switch (event.getAction())
						{
						case MotionEvent.ACTION_DOWN:
							statusView.setText("rightMotorDown : " + speed.toString());
							rightMotor.setSpeed(speed);												
							break;						

						case MotionEvent.ACTION_UP:
							statusView.setText("rightMotorDown : Stopped");
							rightMotor.stop();							
							break;							
						}			

					} catch (ConnectionLostException e) {	
						statusView.setText("rightMotorDown : Exception - " + e.toString());
						e.printStackTrace();
					}

					return false;
				}
			});

			leftDutyCycle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					leftMotor.setDutyCycle((double)progress/100.0);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

			});

			rightDutyCycle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				@Override
				public void onProgressChanged(SeekBar arg0, int progress,
						boolean arg2) {
					rightMotor.setDutyCycle((double)progress/100.0);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
				}

			});		

		}

		/**
		 * Called repetitively while the IOIO is connected.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#loop()
		 */
		@Override
		public void loop() throws ConnectionLostException {
			led.write(!ledButton.isChecked());
			try {

				Thread.sleep(100);
			} catch (InterruptedException e) {
				//statusView.setText("ioi loop() : Exception - " + e.toString());
				e.printStackTrace();
			}
		}
	}

	/**
	 * A method to create our IOIO thread.
	 * 
	 * @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
	 */
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}
}
package me.varunon9.smartcontrol;

import java.io.PrintWriter;
import java.net.Socket;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TouchPad extends ActionBarActivity {

	String ipAddress, portNumber;
	public static PrintWriter out;
	public static Socket clientSocket;
	TextView touchPad;
	public static boolean isConnected = false;
	boolean mouseMoved = false, moultiTouch = false;
	private int initX, initY, disX, disY;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_pad);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		ipAddress = bundle.getString("ipAddress");
		int portNumber = Integer.parseInt(bundle.getString("portNumber"));
		//makeConnection(ipAddress, portNumber);
		new MakeConnection(ipAddress, portNumber, this).execute();
		touchPad = (TextView) findViewById(R.id.touchPad);
		touchPad.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(isConnected && out!=null){
                    switch(event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_DOWN:
                            //save X and Y positions when user touches the TextView
                            initX = (int) event.getX();
                            initY = (int) event.getY();
                            mouseMoved=false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(moultiTouch == false) {
                            	disX = (int) event.getX()- initX; //Mouse movement in x direction
                                disY = (int) event.getY()- initY; //Mouse movement in y direction
                                /*set init to new position so that continuous mouse movement
                                is captured*/
                                initX = (int) event.getX();
                                initY = (int) event.getY();
                                if(disX !=0|| disY !=0){
                                	out.println("4");
                                    //send mouse movement to server
                                	out.println(disX);
                                	out.println(disY);
                                }
                            }
                            else {
                            	disY = (int) event.getY()- initY; //Mouse movement in y direction
                            	disY = (int) disY / 2;//to scroll by less amount
                            	initY = (int) event.getY();
                            	if(disY != 0) {
                            		out.println("3");
                            		out.println(disY);
                            	}
                            }
                            mouseMoved=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            //consider a tap only if usr did not move mouse after ACTION_DOWN
                            if(!mouseMoved){
                                out.println("1");
                            }
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN: 
                        	initY = (int) event.getY();
                        	mouseMoved = false;
                        	moultiTouch = true;
                        	break;
                        case MotionEvent.ACTION_POINTER_UP:
                        	if(!mouseMoved) {
                        		out.println("1");
                        	}
                        	moultiTouch = false;
                        	break;
                        	
                    }
                }
				return true;
			}
		});
	}
    /*private void makeConnection(final String ipAddress, final int portNumber) {
    	Toast.makeText(getApplicationContext(), "connecting...", Toast.LENGTH_SHORT).show();
    	new Thread() {
			public void run() {
				try {
		    		clientSocket = new Socket(ipAddress, portNumber);
		    		out = new PrintWriter(clientSocket.getOutputStream(),true);
		    		isConnected = true;
		    	}
		    	catch(Exception e) {
		    		System.out.println("Connection error");
		    		e.printStackTrace();
		    	}
			}
		}.start();
    	
    }*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.touch_pad, menu);
		return true;
	}
    protected void onDestroy() {
    	super.onDestroy();
    	try {
    		clientSocket.close();
    		Toast.makeText(this, "connection closed", Toast.LENGTH_SHORT).show();
    	}
    	catch(Exception e) {
    		
    	}
    }
	public void leftClick(View view) {
		if(isConnected && out != null) {
			out.println("1");
		}
	}
    public void rightClick(View view) {
    	if(isConnected && out != null) {
			out.println("2");
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

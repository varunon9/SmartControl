package me.varunon9.smartcontrol;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class MakeConnection extends AsyncTask <String, Void, Boolean>{
    String ipAddress;
    int portNumber;
    Context context;
	public MakeConnection(String ipAddress, int portNumber, Context context) {
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.context = context;
	}
	@Override
	protected Boolean doInBackground(String... params) {
		try {
    		//TouchPad.clientSocket = new Socket(ipAddress, portNumber);
    		SocketAddress socketAddress = new InetSocketAddress(ipAddress, portNumber);
    		TouchPad.clientSocket = new Socket();
    		//3s is timeout
    		TouchPad.clientSocket.connect(socketAddress, 3000);
    		TouchPad.out = new PrintWriter(TouchPad.clientSocket.getOutputStream(),true);
    		TouchPad.isConnected = true;
    	}
    	catch(Exception e) {
    		System.out.println("Connection error");
    		e.printStackTrace();
    		TouchPad.isConnected = false;
    	}
		return TouchPad.isConnected;
	}
	@Override
    protected void onPostExecute(Boolean result) {
        boolean isConnected = result;
        if(isConnected == false) {
        	Toast.makeText(context, "connection error", Toast.LENGTH_LONG).show();
        }
        else {
        	Toast.makeText(context, "connected", Toast.LENGTH_LONG).show();
        }
    }

}

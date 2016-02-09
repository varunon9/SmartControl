package me.varunon9.smartcontrol;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    Button connectButton;
    EditText ipAddressText, portNumberText;
    Bundle bundle;
    ListView connectionHistoryListView;
    String connectionHistoryArray[];
    SharedPreferences sharedPreferences;
    int connectionHistoryArrayLength;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		connectButton = (Button) findViewById(R.id.connectButton);
		ipAddressText = (EditText) findViewById(R.id.ipAddressText);
		portNumberText = (EditText) findViewById(R.id.portNumberText);
		portNumberText.setText("4500");//default port Number
		connectionHistoryListView = (ListView) findViewById(R.id.connectionHistory);
		TextView connectionHistoryListViewHeader = new TextView(getApplicationContext());
    	connectionHistoryListViewHeader.setText("Connection History");
    	connectionHistoryListView.addHeaderView(connectionHistoryListViewHeader);
	}
	protected void onResume() {
		populateConnectionHistory();
		super.onResume();
	}
    private void populateConnectionHistory() {
    	sharedPreferences = getSharedPreferences("connectionHistory", 0);
    	connectionHistoryArrayLength = sharedPreferences.getInt("connectionHistoryArrayLength", 0);
    	connectionHistoryArray = new String[connectionHistoryArrayLength];
    	for(int i = 0; i < connectionHistoryArrayLength; i++) {
			connectionHistoryArray[i] = sharedPreferences.getString("history" + i, null);
		}
    	ArrayAdapter <String> arrayAdapter = new ArrayAdapter <String>(
    	    this, 
    	    android.R.layout.simple_list_item_1, 
    	    connectionHistoryArray);
    	connectionHistoryListView.setAdapter(arrayAdapter);
    	connectionHistoryListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 String item =  (String) parent.getAdapter().getItem(position);
				 ipAddressText.setText(item);
			}
    		
    	});
    	//to delete sharedPreferences
    	//SharedPreferences.Editor spe = sharedPreferences.edit();spe.clear().commit();
    }
	public void connect(View v) {
		String ipAddress = ipAddressText.getText().toString();
		String portNumber = portNumberText.getText().toString();
		ValidateIPAndPort validateIPAndPort = new ValidateIPAndPort();
		if(validateIPAndPort.validateIP(ipAddress) == true) {
			if(validateIPAndPort.validatePort(portNumber) == true) {
				//making this connection as the latest connection
				updateSharedPreferences(ipAddress);
				/*int i;
				boolean newConnection = true;
				for(i = 0; i < connectionHistoryArrayLength; i++) {
					if(ipAddress.equals(connectionHistoryArray[i])) {
						newConnection = false;
						break;
					}
				}
				if(newConnection == true) {
					updateSharedPreferences(ipAddress);
				}*/
				Intent intent = new Intent(this, TouchPad.class);
				bundle = new Bundle();
				bundle.putString("ipAddress", ipAddress);
				bundle.putString("portNumber", portNumber);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			else {
				Toast.makeText(getApplicationContext(), "Port number must be greater than 1023", Toast.LENGTH_LONG).show();
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Enter correct IP Address", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	private void updateSharedPreferences(String enteredIPAddress){
		SharedPreferences.Editor spe = sharedPreferences.edit();
		connectionHistoryArrayLength = connectionHistoryArray.length;
		spe.putString("history0", enteredIPAddress);
		spe.putInt("connectionHistoryArrayLength", connectionHistoryArrayLength + 1);
		int i, j=1;
		for(i = 1; i < connectionHistoryArrayLength + 1; i++) {
			if(!enteredIPAddress.equals(connectionHistoryArray[i - 1])) {
				spe.putString("history" + j, connectionHistoryArray[i - 1]);
				j++;
			}
		}
		spe.commit();
	}
}

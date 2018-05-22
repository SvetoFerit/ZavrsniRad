package com.example.sveto.zavrsnirad.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sveto.zavrsnirad.R;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectFitbitActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btnConnectFitbit)
    Button btnConnectFitbit;
    @BindView(R.id.lvDevices)
    ListView lvDevices;

    private BluetoothAdapter bluetoothAdapter;
    
    public static final int DISCOVERY_REQUEST = 1;


    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            switch (state) {
                case (BluetoothAdapter.STATE_TURNING_ON): {
                    Toast.makeText(ConnectFitbitActivity.this, "Bluetooth is turning ON", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (BluetoothAdapter.STATE_ON): {
                    Toast.makeText(ConnectFitbitActivity.this, "Bluetooth is ON", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (BluetoothAdapter.STATE_TURNING_OFF): {
                    Toast.makeText(ConnectFitbitActivity.this, "Bluetooth is turning OFF", Toast.LENGTH_SHORT).show();
                    break;
                }
                case (BluetoothAdapter.STATE_OFF): {
                    Toast.makeText(ConnectFitbitActivity.this, "Bluetooth is OFF", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_fitbit);
        ButterKnife.bind(this);
        btnConnectFitbit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnectFitbit:
                connectToFitbit();

        }
    }


    public void connectToFitbit() {

        Log.e("List", "List");
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String scanModeChanged = BluetoothAdapter.ACTION_SCAN_MODE_CHANGED;
        String beDiscoverable = BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
        IntentFilter filter = new IntentFilter(scanModeChanged);
        registerReceiver(bluetoothState, filter);
        startActivityForResult(new Intent(beDiscoverable), DISCOVERY_REQUEST);

        Log.e("ispiši", "ispiši");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DISCOVERY_REQUEST) {
            Toast.makeText(this, "Discovery in progress", Toast.LENGTH_SHORT).show();
            findDevices();
        }
    }

    private void findDevices() {
        BluetoothDevice remoteDevice = null;
        String lastUsedRemoteDevice = getLastUsedRemoteBTDevice();
        if (lastUsedRemoteDevice != null) {
            Toast.makeText(this, "Checking for known paired devices,namely: " + lastUsedRemoteDevice, Toast.LENGTH_SHORT).show();
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            for (BluetoothDevice pairedDevice : pairedDevices) {
                if (pairedDevice.getAddress().equals(lastUsedRemoteDevice)) {
                    Toast.makeText(this, "Found device: " + pairedDevice.getName() + "@" + lastUsedRemoteDevice, Toast.LENGTH_SHORT).show();
                    remoteDevice = pairedDevice;
                }
            }
        }
        if (remoteDevice == null) {
            Toast.makeText(this, "Starting discovery for remote devices...", Toast.LENGTH_SHORT).show();
            if (bluetoothAdapter.startDiscovery()) {
                Toast.makeText(this, "Discovery thread started...Scanning for devices", Toast.LENGTH_SHORT).show();
                registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        }

    }

    BroadcastReceiver discoveryResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String remoteDeviceName = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
            BluetoothDevice remoteDevice;
            remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Toast.makeText(ConnectFitbitActivity.this, "Discovered: " + remoteDeviceName, Toast.LENGTH_SHORT).show();
        }
    };


    private String getLastUsedRemoteBTDevice() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String result = prefs.getString("LAST_REMOTE_DEVICE_ADDRESS", null);
        return result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothState);
    }


}

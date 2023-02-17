package com.example.apnea_android.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.apnea_android.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ConnectWifiActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    private EditText usernameEditText;
    private EditText ssidEditText;
    private EditText passwordEditText;
    private Button sendButton;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    private List<BluetoothDevice> pairedDevicesList;

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_wifi_page);

        Intent intent = getIntent();

        username = intent.getStringExtra("username");

        Toast.makeText(this, "유저네임, WiFi ID, WiFi 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();


        // Initialize the UI elements
//        usernameEditText = findViewById(R.id.username_edit_text);
        ssidEditText = findViewById(R.id.ssid_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        sendButton = findViewById(R.id.send_button);

        // Get the Bluetooth adapter and check if it's available
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Request the user's permission to enable Bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
        }

        // Set up the click listener for the button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the data from the input fields
//                String username = usernameEditText.getText().toString();
                String ssid = ssidEditText.getText().toString();
                String password = passwordEditText.getText().toString();



                if (ContextCompat.checkSelfPermission(ConnectWifiActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    pairedDevicesList = new ArrayList<>(bluetoothAdapter.getBondedDevices());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ConnectWifiActivity.this, android.R.layout.simple_list_item_1);
                    for (BluetoothDevice device : pairedDevicesList) {
                        adapter.add("[" + device.getName() + "] 기기에 연결");
                    }
                    ListView deviceList = findViewById(R.id.device_list);
                    deviceList.setAdapter(adapter);

                    deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Get the selected device
                            BluetoothDevice device = pairedDevicesList.get(position);

                           Toast.makeText(ConnectWifiActivity.this, device.getName() + "에 연결을 시도합니다.", Toast.LENGTH_SHORT).show();

                            // Attempt to connect to the device
                            connectToDevice(device);
                        }
                    });


                    // Permission is already granted, run your code that requires the permission
                    // Send the data to Arduino via Bluetooth
                    try {
                        // Get the paired devices
                        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                        // Find the Arduino device in the paired devices
                        BluetoothDevice arduinoDevice = null;
                        for (BluetoothDevice device : pairedDevices) {
                            if (device.getName().equals("Arduino")) {
                                arduinoDevice = device;
                                break;
                            }
                        }

                        // Connect to the Arduino device
                        if (arduinoDevice != null) {
                            bluetoothSocket = arduinoDevice.createRfcommSocketToServiceRecord(MY_UUID);
                            bluetoothSocket.connect();
                            outputStream = bluetoothSocket.getOutputStream();

                            // Send the data to Arduino
                            String data = username + "," + ssid + "," + password;
                            outputStream.write(data.getBytes());
                        } else {
                            Toast.makeText(ConnectWifiActivity.this, "주변 기기를 검색합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(ConnectWifiActivity.this, "Error sending data to Arduino", Toast.LENGTH_SHORT).show();
                    } finally {
                        // Close the Bluetooth socket
                        if (bluetoothSocket != null) {
                            try {
                                bluetoothSocket.close();
                            } catch (IOException e) {
                                // Do nothing
                            }
                        }
                    }
                } else {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(ConnectWifiActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                }



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of the Bluetooth enable request
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                // Bluetooth has been enabled, do nothing
            } else {
                Toast.makeText(this, "Bluetooth must be enabled to use this app", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void sendBluetoothData(BluetoothDevice device, String data) {
        try {
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to connect to the selected device
    private void connectToDevice(BluetoothDevice device) {
        // Start a new thread to connect to the device in the background
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Connect to the device
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    socket.connect();

                    // Send the data to the device
//                    String username = ((EditText) findViewById(R.id.username_edit_text)).getText().toString();
                    String ssid = ((EditText) findViewById(R.id.ssid_edit_text)).getText().toString();
                    String password = ((EditText) findViewById(R.id.password_edit_text)).getText().toString();
                    String data = username + "," + ssid + "," + password + "\n";
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(data.getBytes());

                    // Close the socket when finished
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



}

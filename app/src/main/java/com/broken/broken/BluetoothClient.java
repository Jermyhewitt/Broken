package com.broken.broken;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;

public class BluetoothClient extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //new bluesClient().start();

        ListView listView=(ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==1)
                {
                    blueMessage();
                }
                else
                {
                    blueCall();
                }
            }
        });

    }

/*
    private class bluesClient extends Thread
    {

        public void run()
        {

            Log.w("START", "started the bluetooth");
            BluetoothAdapter adapt = BluetoothAdapter.getDefaultAdapter();
            adapt.enable();
            Set<BluetoothDevice> paired=adapt.getBondedDevices();
            for(BluetoothDevice device:paired)
            {
                try {

                    if(device.getName().contains("DIGICEL")) {
                        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(new UUID(1234,5678));
                        Log.w("Socket", "Created");
                        socket.connect();
                        Log.w("Socket", "Connected");
                        PrintWriter writer= new PrintWriter(socket.getOutputStream());
                        BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer.println("I am sending this messager through bluetoth a visible");
                        writer.flush();
                        Log.w(">>>", "message sent");
                        String response=reader.readLine();
                        Log.w("response",response);

                    }

                }
                catch(Exception e)

                {
                    Log.w("SOCKETERROR",e.toString());
                }
            }
        }

    }
    */

    public void blueCall()
    {
        startActivity(new Intent(this,BlueCall.class));
    }


    public void blueMessage()
    {
        startActivity(new Intent(this,BlueMessage.class));

    }









}

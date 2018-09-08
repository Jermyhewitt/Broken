package com.broken.broken;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;

public class BlueCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EditText editText=(EditText) findViewById(R.id.editText8);
        editText.setImeActionLabel("CALL", EditorInfo.IME_ACTION_SEND);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_SEND)
                {
                    call();
                    return true;
                }
                return false;
            }
        });
    }


    String number;
    public void call()
    {
        EditText texts=(EditText)findViewById(R.id.editText8);
        number="CALL:"+texts.getText().toString();
        new bluesClient().start();
        texts.setText("");
        CharSequence text = "Calling ";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();

    }

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
                        Log.w(">>sent message",number);
                        writer.println(number);
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

}

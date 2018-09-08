package com.broken.broken;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Wifi_Client extends AppCompatActivity {

    static final String PORTNAME = "com.broken.broken";
    static final String HOSTNAME = "com.broken.broken";
    String address;
    public static String connectInfo;
    Socket client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi__client);
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
    }



    @Override
    protected void onStop() {
        super.onStop();
        try {
            client.close();
        }
        catch(Exception e)
        {
            TextView win = (TextView) findViewById(R.id.textView2);
            win.setText(e.toString());
        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SendMessage.class);
        intent.putExtra(PORTNAME, 12347);
        intent.putExtra(HOSTNAME,"192.165.0.8");
        startActivity(intent);

    }


    public void makeCall(View view) {
        Intent intent = new Intent(this, MakeCall.class);
        intent.putExtra(HOSTNAME,connectInfo);
        startActivity(intent);
    }

    public void findConnection(View views)
    {
        new SendData().execute("process");
    }
    private class SendData extends AsyncTask<String,String,String>
    {

        boolean found = false;

        TextView view = (TextView) findViewById(R.id.textView);
        TextView win = (TextView) findViewById(R.id.textView2);


        protected String doInBackground(String... val)
        {
            String hostt="";

            publishProgress(" Background Started");
            try
            {
                WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                int ip = wifiInfo.getIpAddress();
                String ipAddress = Formatter.formatIpAddress(ip);
                while (true) {
                    publishProgress(" Above Socket");
                    client = new Socket("192.168.11.101", 9000);
                    publishProgress(" Below Socket");
                    Wifi_Client.connectInfo="192.169.11.101";


                    publishProgress(" connected");
                    PrintWriter printer = new PrintWriter(client.getOutputStream(),true);
                    publishProgress(" Sent data");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    printer.println(ipAddress);
                    hostt=reader.readLine();
                    publishProgress(hostt);

                }
            }
            catch (Exception e)
            {
                publishProgress(e.toString());

            }

            return hostt;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            TextView view=(TextView)findViewById(R.id.textView);
            StringBuilder builder=new StringBuilder();
            view.setText(view.getText()+values[0]);

        }


        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            view.setText("ended"+s);
        }
    }






}

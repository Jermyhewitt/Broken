package com.broken.broken;

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

public class MakeCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_call);
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
                    Socket client = new Socket("192.168.11.101", 9000);
                    publishProgress(" Below Socket");


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

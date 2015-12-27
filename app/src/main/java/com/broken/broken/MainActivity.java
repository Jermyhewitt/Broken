package com.broken.broken;

import android.content.Intent;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class MainActivity extends AppCompatActivity  {
    static final String PORTNAME = "com.broken.broken";
    static final String HOSTNAME = "com.broken.broken";
    String address;
    public static String connectInfo;
    Socket client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                         MainActivity.connectInfo="192.169.11.101";


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

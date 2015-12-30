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
import android.widget.EditText;
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


       public void startCall(View screen)
       {
           EditText numb= (EditText)findViewById(R.id.editText2);
           new SendData().execute(numb.getText().toString());
       }
    private class SendData extends AsyncTask<String,String,String>
    {

        boolean found = false;

        EditText numb= (EditText)findViewById(R.id.editText2);



        protected String doInBackground(String... val)
        {
            String hostt="";

            publishProgress(" Background Started");
            try
            {


                    publishProgress(" \nAbove Socket");
                    Socket client = new Socket("192.168.11.101", 9000);
                    publishProgress(" \nconnected");
                    PrintWriter printer = new PrintWriter(client.getOutputStream(),true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    printer.println("CALL:"+val[0]+"number of strings ");
                    publishProgress("\nSent messsage");
                    String response=reader.readLine();
                    publishProgress("\nresponse: "+response);



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
            EditText upda= (EditText) findViewById(R.id.editText5);
            upda.setText(upda.getText()+values[0]);


        }


        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            numb.setText("1");
        }
    }
}

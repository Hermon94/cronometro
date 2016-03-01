package com.example.hermon.cronometro;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Debug;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    boolean corre;
    long timeWhenStopped = 0;
    boolean cheRes;
    ArrayList<String> ar = new ArrayList<String>();
    public void empezar (View view){
        Button bot = (Button) findViewById(R.id.start_button);
        String val = bot.getText().toString();
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        ListView tiempo = (ListView)findViewById(R.id.tiempos);
        if (val.equals("Start") ){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            corre = true;
            cheRes = true;
            bot.setText("Reset");
        }else if(val.equals("Reset") && cheRes == true){
            String tem = chronometer.getText().toString();
            ar.add(tem);
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, ar);
            tiempo.setAdapter(myAdapter);
            chronometer.stop();
            bot.setText("Start");
            corre = false;
        }
    }



    public void parar (View view){
        Button bot = (Button) findViewById(R.id.stop_button);
        String val = bot.getText().toString();
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);

        if (val.equals("Stop") && corre == true){
            chronometer.stop();
            timeWhenStopped = SystemClock.elapsedRealtime() - chronometer.getBase();
            bot.setText("Resume");
            cheRes = false;
            corre = false;
        }else if (val.equals("Resume") && corre == false){
            chronometer.setBase(SystemClock.elapsedRealtime() - timeWhenStopped);
            chronometer.start();
            cheRes = true;
            corre = true;
            bot.setText("Stop");
        }

    }


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
        Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setText("00:00:00");
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer chronometer) {
                CharSequence text = chronometer.getText();
                if (text.length()  == 5) {
                    chronometer.setText("00:"+text);
                } else if (text.length() == 7) {
                    chronometer.setText("0"+text);
                }
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
}

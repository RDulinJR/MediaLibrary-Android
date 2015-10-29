package com.rdulinjr.medialibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = (TextView) findViewById(R.id.textView_activity_about);

        String receivedAction = getIntent().getAction();
        if(receivedAction != null) {
            // action detected == Omplicit intent from another app that wants to use activity
            String receivedActionType = getIntent().getType();

            if(receivedActionType.startsWith("text/")) {
                String data = getIntent().getStringExtra(Intent.EXTRA_TEXT);
                tv.setText(data);
            }
        }
        else{
            //no action detected == explicit internal request
            // receiving data from activity
            Bundle extras = getIntent().getExtras();
            tv.setText(extras.getString("Message"));
        }
    }

}

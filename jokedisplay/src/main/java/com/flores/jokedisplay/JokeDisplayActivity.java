package com.flores.jokedisplay;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE_DISPLAY = "com.flores.jokedisplay.joke_display";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        String joke = getIntent().getStringExtra(EXTRA_JOKE_DISPLAY);

        ((TextView) findViewById(R.id.tv_joke_display)).setText(joke);
    }
}
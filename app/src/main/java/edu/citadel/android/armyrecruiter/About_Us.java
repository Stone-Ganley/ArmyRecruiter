package edu.citadel.android.armyrecruiter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class About_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

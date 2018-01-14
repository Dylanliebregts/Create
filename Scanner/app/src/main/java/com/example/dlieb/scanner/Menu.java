package com.example.dlieb.scanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {   //extends allows one class to "inherit" the properties of another class.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void btnShowGallery_Click(View v) { //Button for the gallery screen
        Intent i = new Intent(Menu.this, ImageListActivity.class);
        startActivity(i);
    }

    public void btnShowShare_Click(View v) { //button for the upload screen
        Intent i = new Intent(Menu.this, Upload.class);
        startActivity(i);
    }

    public void btnShowCamera_Click(View v){ //button for the camera screen
        Intent i = new Intent(Menu.this, Camera2.class);
        startActivity(i);
    }
}

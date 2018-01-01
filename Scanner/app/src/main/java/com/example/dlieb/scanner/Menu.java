package com.example.dlieb.scanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void btnShowGallery_Click(View v) {
        Intent i = new Intent(Menu.this, ImageListActivity.class);
        startActivity(i);
    }

    public void btnShowShare_Click(View v) {
        Intent i = new Intent(Menu.this, Upload.class);
        startActivity(i);
    }
}

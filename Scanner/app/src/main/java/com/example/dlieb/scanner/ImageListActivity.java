package com.example.dlieb.scanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {  //extends   allows one class to "inherit" the properties of another class.
//variables
    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);   //layout van de activity_main xml
        imgList = new ArrayList<>();    //list for images to load in
        lv = findViewById(R.id.listViewImage);  //lv is the listview in the xml
        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();
        //Database init
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(Upload.FB_DATABASE_PATH);    //firebase instance,
        mDatabaseRef.addValueEventListener(new ValueEventListener() {       //keeps listening incoming values
            @Override // When data is coming in
            public void onDataChange(DataSnapshot dataSnapshot) { //when data changes
                 progressDialog.dismiss();
                 //Fetch image data from firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){   //datasnapshot is data from the database
                    //ImageUpload class require default constructor         // so when data is changed it gets added to the list
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }
                //Init adapter
                adapter = new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imgList);
                //Set adapter for listview
                lv.setAdapter(adapter);
            }
//if the action gets cancelled
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }


        });

    }
//Bottom of the screen menu
    public void btnHome_Click(View v)
    {
        Intent i = new Intent(ImageListActivity.this, Menu.class);
        startActivity(i);
    }

    public void btnUpload_Click(View v)
    {
        Intent i = new Intent(ImageListActivity.this, Upload.class);
        startActivity(i);
    }

    public void btnGallery_Click(View v)
    {
        Intent i = new Intent(ImageListActivity.this, ImageListActivity.class);
        startActivity(i);
    }

}

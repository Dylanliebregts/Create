package com.example.dlieb.scanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class Camera extends Activity {

    //variables
    Button button;
    ImageView imageView;
    static final int CAM_REQUEST = 1; //can use different request so i specify which one this is.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        button = (Button)findViewById(R.id.button); //button in xml
        imageView = (ImageView)findViewById(R.id.image_view);   //imageview in xml

        //take the picture
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //intent ACTION IMAGE CAPTURE gives me the purpose to take a picture
                if (takePictureIntent.resolveActivity(getPackageManager())!=null){ //if there is a picture taken
                    startActivityForResult(takePictureIntent,CAM_REQUEST);          //gets result for activity
                }

            }
        });
    }

    //create a path for the app in your phone where the photos are stored
    private File getFile () {
        File folder = new File(Environment.getExternalStorageDirectory(),"Scanner");

        if(!folder.exists()) {
            folder.mkdir();
        }

        File image_file = new File(folder,"Create_Image.jpg");
        return image_file;
    }

    @Override        //Set the camera picture in the right path you created
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/Scanner/Create_Image.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }

    public void btnHome_Click(View v)
    {
        Intent i = new Intent(Camera.this, Menu.class);
        startActivity(i);
    }

    public void btnUpload_Click(View v)
    {
        Intent i = new Intent(Camera.this, Upload.class);
        startActivity(i);
    }

    public void btnGallery_Click(View v)
    {
        Intent i = new Intent(Camera.this, ImageListActivity.class);
        startActivity(i);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/camera_app/cam_image.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }
    */
}

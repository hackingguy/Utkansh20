package com.rocketapp.utkansh20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EventCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_card);
    }

    public void download(View view) {
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        File file = saveBitMap(this, relativeLayout);    //which view you want to pass that view as parameter
        if (file != null) {
            Toast.makeText(this, "Entry card has been saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Entry Card cannot be saved", Toast.LENGTH_SHORT).show();
        }
    }



    private File saveBitMap(Context context, View drawView){
        File pictureFileDir = new File(String.valueOf(Environment.getExternalStorageDirectory())+File.separator+"Utkansh");
        pictureFileDir.mkdir();
        System.out.println("--------------------------------->"+pictureFileDir);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                20);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(this, "Permission Not Granted.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Cannot save card", Toast.LENGTH_SHORT).show();
            return null;
        }


        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if (!isDirectoryCreated) {
                Toast.makeText(context, "Cannot create directory", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        System.out.println("--------------------------------------->Creating file");
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }
    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        System.out.println("--------------------------------------->Converting to BITMAP");
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void share(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
    }
}

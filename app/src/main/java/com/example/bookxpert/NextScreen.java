package com.example.bookxpert;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class NextScreen extends AppCompatActivity {
    ImageView camImage ;
    Button btnCapture;
    Bitmap photo;
    Intent intent;
    private boolean boolean_save = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_screen);
        btnCapture = findViewById(R.id.btn_capture);
        camImage = findViewById(R.id.camImage);
        ActivityResultLauncher<Intent> camResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                             intent = result.getData();
                             photo = (Bitmap) intent.getExtras().get("data");
                            camImage.setImageBitmap(photo);
                        }
                        createPdf();
                    }
                });
        btnCapture.setOnClickListener(v -> {

                 Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 camResult.launch(camIntent);

        });


    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(photo.getWidth(), photo.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);



        photo = Bitmap.createScaledBitmap(photo, photo.getWidth(), photo.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(photo, 0, 0 , null);
        document.finishPage(page);


        // write the document content
      //  String targetPdf = "/sdcard/test.pdf";
        /*File mediaStorageDir1 = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File filePath = new File(mediaStorageDir1 + "/example.pdf");*/
        String dirPath = this.getExternalFilesDir(null).getAbsolutePath();
        String filePath1 = dirPath + "/PDF";
        File filePath = new File(filePath1);
        try {
            if(filePath.exists()) {
                document.writeTo(new FileOutputStream(filePath));
                //btn_convert.setText("Check PDF");
                boolean_save = true;
            }
            else{
                filePath.mkdir();
                Toast.makeText(this, "file not exist", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
           // Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[] {  Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 42);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 42){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(this, "Please provide request", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[] {  Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 42);

            }
        }

    }

}


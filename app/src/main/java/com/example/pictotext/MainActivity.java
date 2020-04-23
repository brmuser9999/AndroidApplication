package com.example.pictotext;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button captureImageBtn, detectTextBtn, back;
    private ImageView imageview;
    private TextView textView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {//onCreate is where you initialize your activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        captureImageBtn = findViewById(R.id.capture_image);
        detectTextBtn = findViewById(R.id.detect_text_image);
        imageview = findViewById(R.id.image_view);
        textView = findViewById(R.id.text_display);

        back = findViewById(R.id.back_btn);

        captureImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                textView.setText("");
            }
        });
        detectTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectTextFromImage();

            }
        });
    }
    public void Back_to_options(View view) {
        Intent intent = new Intent(MainActivity.this, LaunchActivity.class);
        startActivity(intent);
        finish();
        // startActivity(new Intent(LaunchActivity.this,MainActivity.class));
    }

    private void dispatchTakePictureIntent()
    {
        //storage
        //_fileUri=uri.fromFile(_photofile);
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //takePictureIntent.putextra(MediaStore.Extra_Output,_fileUri);
        //startActivityForResult(takePictureIntent,Take_Picture);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            //requires result from second activity and in this case we need to override the onAcivityResult method
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override

    //this is invoked immediately when the second activity returns result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");

            imageview.setImageBitmap(imageBitmap);

        }
    }

    private void detectTextFromImage()
    {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        //to get instance of text detector
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
        firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText)
            {
                displayTextFromImage(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "ERROR........" + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("ERROR....", e.getMessage());


            }
        });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.Block> blocklist = firebaseVisionText.getBlocks();
        if (blocklist.size() == 0) {

            Toast.makeText(MainActivity.this, "NO TEXT FOUND IN IMAGE....", Toast.LENGTH_LONG).show();

        } else {
            for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
                String text = block.getText();
                textView.setText(text.toString());

            }
        }

    }



}
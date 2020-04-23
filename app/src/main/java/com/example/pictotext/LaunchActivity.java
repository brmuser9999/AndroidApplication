package com.example.pictotext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {

    Button imageBtn ,voiceBtn,backbtn;
    public void onStart()
    {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        imageBtn=findViewById(R.id.buttonImage);
        voiceBtn=findViewById(R.id.buttonspeech);
        backbtn=findViewById(R.id.button2);
    }
    public void onClick1(View v)
    {
        Intent intent=new Intent(LaunchActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
       // startActivity(new Intent(LaunchActivity.this,MainActivity.class));
    }
    public void onClick2(View v)
    {
        Intent intent=new Intent(LaunchActivity.this,SpeechActivity.class);
        startActivity(intent);
        finish();
      //  startActivity(new Intent(LaunchActivity.this,SpeechActivity.class));
    }
    public void onClick3(View v)
    {
        Intent intent=new Intent(LaunchActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
        //  startActivity(new Intent(LaunchActivity.this,SpeechActivity.class));
    }
}